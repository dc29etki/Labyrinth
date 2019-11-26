package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.List;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;


public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;
    private Music music;
    private Tile[] tilePaths;
    private GameRunner runEnv;
    private MoveButtons buttons;
    private Skin skin;
    private Label labelDetails;
    private Label labelMessage;
    private TextButton button;
    private TextArea textIPAddress;
    private TextArea textMessage;

    public GameScreen(Game game) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        board = new Board();
        gameStage = new Stage();
        this.game = game; // Store this to call game.setScreen(new MenuScreen(game)) to return to the menu
        runEnv = new GameRunner(board, batch);
        runEnv.getPlayers()[0].swapSprite();
        runEnv.dealCards(0);
        runEnv.changeVolume(0.5f);

        Gdx.input.setInputProcessor(gameStage);

        buttons = new MoveButtons(-2, board, runEnv);
        buttons.disableMove();
        for(Button button : buttons.getInsButtons()) {
            gameStage.addActor(button);
        }
        for(Button[] row : buttons.getButtons()) {
            for (Button button : row) {
                gameStage.addActor(button);
            }
        }
        for(Button button : buttons.getRotateButtons()) {
            gameStage.addActor(button);
        }


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }

        // Now setupt our scene UI

        // Vertical group groups contents vertically.  I suppose that was probably pretty obvious
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();//.space(2).pad(5).fill();//.space(3).reverse().fill();
        // Set the bounds of the group to the entire virtual display
        vg.setBounds(500, 0, 1200, 1000);

        // Create our controls
        labelDetails = new Label(ipAddress,skin);
        labelMessage = new Label("Hello world",skin);
        button = new TextButton("Send message",skin);
        textIPAddress = new TextArea("",skin);
        textMessage = new TextArea("",skin);

        // Add them to scene
        vg.addActor(labelDetails);
        vg.addActor(labelMessage);
        vg.addActor(textIPAddress);
        vg.addActor(textMessage);
        vg.addActor(button);

        // Add scene to stage
        gameStage.addActor(vg);

        new Thread(new Runnable(){

            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                // 0 means no timeout.  Probably not the greatest idea in production!
                serverSocketHint.acceptTimeout = 0;

                // Create the socket server using TCP protocol and listening on 9021
                // Only one app can listen to a port at a time, keep in mind many ports are reserved
                // especially in the lower numbers ( like 21, 80, etc )
                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);

                // Loop forever
                while(true){
                    // Create a socket
                    Socket socket = serverSocket.accept(null);

                    // Read data from the socket into a BufferedReader
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                        labelMessage.setText(buffer.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); // And, start the thread running

        // Wire up a click listener to our button
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                // When the button is clicked, get the message text or create a default string value
                String textToSend = new String();
                if(textMessage.getText().length() == 0)
                    textToSend = "Doesn't say much but likes clicking buttons\n";
                else
                    textToSend = textMessage.getText() + ("\n"); // Brute for a newline so readline gets a line

                SocketHints socketHints = new SocketHints();
                // Socket will time our in 4 seconds
                socketHints.connectTimeout = 4000;
                //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
                Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, textIPAddress.getText(), 9021, socketHints);
                try {
                    // write our entered message to the stream
                    socket.getOutputStream().write(textToSend.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Put sprites and effects here

        batch.begin();
        board.draw(batch);

        runEnv.draw(batch);

        //Draw empty sprite to update all other drawings
        Sprite green = new Sprite();
        green.setTexture(new Texture(Gdx.files.internal("Blank_Icon.png")));
        green.setPosition(-10, -10);
        green.draw(batch);

        gameStage.draw();

        //Music Disabled For Testing Ease
        //Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        /*for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                Tile tile = gameBoard.getBoard() [i][j];
                tile.draw(batch);
            }

        }*/

        batch.end();
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}