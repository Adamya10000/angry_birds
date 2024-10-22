package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Main game;
    private Texture gameScreen;
    private Texture firstCatapult;
    private Texture secondCatapult;
    private Texture bird;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

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
        firstCatapult = new Texture("catapult_part1.png");
        bird = new Texture("redbird.png");
        secondCatapult = new Texture("catapult_part2.png");

        Image mainscreen = new Image(gameScreen);
        mainscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        Image catapult1 = new Image(firstCatapult);
        float divide = (float) 4/3;
        catapult1.setSize(catapult1.getWidth()/divide, catapult1.getHeight()/divide);
        catapult1.setPosition(100, 200);

        divide = (float) 3/2;
        Image catapult2 = new Image(secondCatapult);
        catapult2.setSize(catapult2.getWidth()/divide , catapult2.getHeight()/divide);
        catapult2.setPosition(78, 310);

        divide = (float) 14/4;
        Image redBird = new Image(bird);
        redBird.setSize(redBird.getWidth()/divide , redBird.getHeight()/divide);
        redBird.setPosition(40,350);

        stage.addActor(mainscreen);
        stage.addActor(catapult1);
        stage.addActor(redBird);
        stage.addActor(catapult2);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
