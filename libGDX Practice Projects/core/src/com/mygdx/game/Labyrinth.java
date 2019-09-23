package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class Labyrinth extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture img1;
	private Texture cntrImg;
	private Texture treasureTest;
	private OrthographicCamera camera;
	private Rectangle treasureRect;
	private Tile testTile;
    private Skin skin;
    private Skin skin1;
    private Stage gameStage;
    private BitmapFont font;
    private TextButton buttonText;
    private TextButton.TextButtonStyle textButtonStyle;
    private ImageButton tileButton;
    private ImageButton.ImageButtonStyle imageButtonStyle;
    private Board GameBoard;
    private Tile[][] TileArray;
    private Music music;
    private Label labelMessage;
    private String networkLabel;
    private TextButton networkButton;
    private TextButton.TextButtonStyle style;



	@Override
	public void create () {
        batch = new SpriteBatch();
        img = new Texture("Card_Base.png");
        img1 = new Texture(Gdx.files.internal("Icon_Helmet.png"));
        cntrImg = new Texture(Gdx.files.internal("Piece_Corner_Blank.png"));
        treasureTest = new Texture(Gdx.files.internal("Icon_Skull.png"));
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        batch = new SpriteBatch();
        GameBoard = new Board(15, 15);
        TileArray = GameBoard.getBoard();





        gameStage = new Stage();
        Gdx.input.setInputProcessor(gameStage);
        skin = new Skin();
        skin.add("Icon", img);
        skin.add("Tile", new Texture(Gdx.files.internal("Piece_Straight_Blank.png")));
        style = new TextButton.TextButtonStyle();
        font = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("Icon");
        textButtonStyle.down = skin.getDrawable("Icon");
        textButtonStyle.checked = skin.getDrawable("Icon");
        buttonText = new TextButton("Test Button", textButtonStyle);
        buttonText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("TEST BUTTON PRESSED. PERFORMING TEST.");
                System.out.println("TEST SUCCESSFUL");
            }
        });
        //gameStage.addActor(buttonText);

        //
        skin1 = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin1.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin1.add("default", new BitmapFont());
        style = new TextButton.TextButtonStyle();
        style.up = skin1.newDrawable("white", Color.DARK_GRAY);
        style.down = skin1.newDrawable("white", Color.DARK_GRAY);
        style.checked = skin1.newDrawable("white", Color.BLUE);
        style.over = skin1.newDrawable("white", Color.LIGHT_GRAY);
        style.font = skin1.getFont("default");
        networkButton = new TextButton("Send message", style);
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
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        System.out.println(ipAddress);
        networkLabel = "Connected to network... \n" + ipAddress;
        new Thread(new Runnable(){
            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                serverSocketHint.acceptTimeout = 0;
                ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9021, serverSocketHint);
                while(true){
                    Socket socket = serverSocket.accept(null);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        String s = buffer.readLine();

                    } catch (IOException e) {
                        System.out.println("ERROR");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        SocketHints socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        final String textToSend = "HELLO WORLD";
        socketHints.connectTimeout = 4000;
        final Socket socket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 9021, socketHints);
        networkButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                try {
                    socket.getOutputStream().write(textToSend.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.getDrawable("Tile");
        imageButtonStyle.down = skin.getDrawable("Tile");
        tileButton = new ImageButton(imageButtonStyle);
        gameStage.addActor(networkButton);
        networkButton.setPosition(10, 700);
        tileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tileButton.moveBy(5, 5);
            }
        });


        treasureRect = new Rectangle();
        treasureRect.x = 15;
        treasureRect.y = 25;
        treasureRect.width = 47;
        treasureRect.height = 47;

        testTile = new Tile(0, 20, 1, 1, 0);


		/*batch.begin();

		batch.draw(treasureTest, treasureRect.x, treasureRect.y);

		batch.draw(testTile.getTilePng(), testTile.getTilePosition().x, testTile.getTilePosition().y);
		batch.draw(testTile.getTreasurePng(), testTile.getTreasurePosition().x, testTile.getTreasurePosition().y);

		batch.end();

		gameStage.draw();*/

	}

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        music.play();

        /*Sound sound = Gdx.audio.newSound(Gdx.files.internal("Startup_Sound.wav"));
        sound.play();*/
        /*batch.draw(img1, 100, 100);*/
        for (int i=0; i<15; i++){
            for (int j=0; j<15; j++){
                Tile tile = GameBoard.getBoard() [i][j];
                Texture tex = tile.getTilePng();
                batch.draw(tex, 100*i, 100*j);
            }

        }

        /*batch.draw(img, 0, 0);
        batch.draw(cntrImg, 400, 400);*/

        batch.draw(img, 0, 0);
        batch.draw(cntrImg, 400, 400);
        font.draw(batch, networkLabel, 10, 780);
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            treasureRect.x = touchPos.x;
            treasureRect.y = 800- touchPos.y;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed((Input.Keys.A))) {
            treasureRect.x -= 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed((Input.Keys.D))){
            treasureRect.x += 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed((Input.Keys.W))){
            treasureRect.y += 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed((Input.Keys.S))){
            treasureRect.y -= 200 * Gdx.graphics.getDeltaTime();
        }

        batch.draw(treasureTest, treasureRect.x, treasureRect.y);

        batch.draw(testTile.getTilePng(), testTile.getTilePosition().x, testTile.getTilePosition().y);
        batch.draw(testTile.getTreasurePng(), testTile.getTreasurePosition().x, testTile.getTreasurePosition().y);

        gameStage.draw();
        batch.end();

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        music.dispose();
    }


    public Object[] getAllArrays(){
        Object[] array = new Object[6];
        array[0]=img;
        array[1]=cntrImg;
        array[2]=treasureTest;
        array[3]=treasureRect;
        array[4]=testTile;
        array[5]=GameBoard;
        return array;
    }

}