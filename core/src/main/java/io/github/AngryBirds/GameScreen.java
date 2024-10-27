package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Main game;
    private Texture gameScreen;
    private Texture pauseButton;
    private Texture blockSprites;
    private Texture pigSprites;
    private TextureRegion block1,block2,block3,block4,pig1,pig2;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

    private void doubler(Image image){
        image.setSize(image.getWidth()*2, image.getHeight()*2);
    }

    private void doublerH(Image image){
        image.setSize((image.getWidth()*4)/3, image.getHeight()*2);
    }

    public GameScreen(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        gameScreen = new Texture("gamescreen.jpg");
        pauseButton = new Texture("pauseButton.png");
        blockSprites = new Texture("blockSprites.png");
        pigSprites = new Texture("pigSprites.png");

        pig1 = new TextureRegion(pigSprites,255,640,99,98);
        pig2 = new TextureRegion(pigSprites,255,740,99,98);

        block1 = new TextureRegion(blockSprites,320,627,204,18);
        block2 = new TextureRegion(blockSprites,490,715,167,18);
        block3 = new TextureRegion(blockSprites,806,504,83,40);
        block4 = new TextureRegion(blockSprites,161,857,42,40);

        Wood longWood = new Wood(block1);
        longWood.getBlock().setPosition(1400,277);
        doubler(longWood.getBlock());
        Wood longWood1 = new Wood(block1);
        longWood1.getBlock().setPosition(1808,277);
        doubler(longWood1.getBlock());
        Wood longWood2 = new Wood(block1);
        longWood2.getBlock().setPosition(1400,535);
        doubler(longWood2.getBlock());
        Wood longWood3 = new Wood(block1);
        longWood3.getBlock().setPosition(1808,535);
        doubler(longWood3.getBlock());
        Wood longWood4 = new Wood(block1);
        longWood4.getBlock().setPosition(1604,793);
        doubler(longWood4.getBlock());

        Wood medWood = new Wood(block2);
        medWood.getBlock().setRotation(90);
        medWood.getBlock().setPosition(1436,313);
        doublerH(medWood.getBlock());
        Wood medWood1 = new Wood(block2);
        medWood1.getBlock().setRotation(90);
        medWood1.getBlock().setPosition(1826,313);
        doublerH(medWood1.getBlock());
        Wood medWood2 = new Wood(block2);
        medWood2.getBlock().setRotation(90);
        medWood2.getBlock().setPosition(2216,313);
        doublerH(medWood2.getBlock());
        Wood medWood3 = new Wood(block2);
        medWood3.getBlock().setRotation(90);
        medWood3.getBlock().setPosition(1678,571);
        doublerH(medWood3.getBlock());
        Wood medWood4 = new Wood(block2);
        medWood4.getBlock().setRotation(90);
        medWood4.getBlock().setPosition(1974,571);
        doublerH(medWood4.getBlock());


        Stone medStone = new Stone(block3);
        medStone.getBlock().setPosition(2048,196);
        doubler(medStone.getBlock());
        Stone medStone1 = new Stone(block3);
        medStone1.getBlock().setPosition(1724,196);
        doubler(medStone1.getBlock());
        Stone medStone2 = new Stone(block3);
        medStone2.getBlock().setPosition(1400,196);
        doubler(medStone2.getBlock());

        Stone smallStone = new Stone(block4);
        smallStone.getBlock().setPosition(1400,571);
        doubler(smallStone.getBlock());
        Stone smallStone1 = new Stone(block4);
        smallStone1.getBlock().setPosition(2132,571);
        doubler(smallStone1.getBlock());

        Pig noTeethPig = new Pig(pig1);
        noTeethPig.getPig().setPosition(1554,313);
        Pig noTeethPig1 = new Pig(pig1);
        noTeethPig1.getPig().setPosition(1962,313);

        Pig teethPig = new Pig(pig2);
        teethPig.getPig().setPosition(1758,829);



        Image mainscreen = new Image(gameScreen);
        mainscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        Catapult catapult = new Catapult();
        Image catapult1 = catapult.getCatapult1();
        catapult1.setPosition(200, 200);
        Image catapult2 = catapult.getCatapult2();
        catapult2.setPosition(178, 310);


        Image pause = new Image(pauseButton);
        pause.setSize(180, 160);
        pause.setPosition(0,VIRTUAL_HEIGHT - pause.getHeight());


        RedBird redBird = new RedBird();
        redBird.getRed().setPosition(140,390);

        pause.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenu(game, viewport, camera, gameScreen ));
            }
        });

        stage.addActor(mainscreen);
        stage.addActor(catapult1);
        stage.addActor(redBird.getRed());
        stage.addActor(catapult2);
        stage.addActor(pause);

        stage.addActor(longWood.getBlock());
        stage.addActor(longWood1.getBlock());
        stage.addActor(longWood2.getBlock());
        stage.addActor(longWood3.getBlock());
        stage.addActor(longWood4.getBlock());

        stage.addActor(medWood.getBlock());
        stage.addActor(medWood1.getBlock());
        stage.addActor(medWood2.getBlock());
        stage.addActor(medWood3.getBlock());
        stage.addActor(medWood4.getBlock());

        stage.addActor(medStone.getBlock());
        stage.addActor(medStone1.getBlock());
        stage.addActor(medStone2.getBlock());

        stage.addActor(smallStone.getBlock());
        stage.addActor(smallStone1.getBlock());

        stage.addActor(noTeethPig.getPig());
        stage.addActor(noTeethPig1.getPig());
        stage.addActor(teethPig.getPig());
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new WinScreen(game));
        }

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
