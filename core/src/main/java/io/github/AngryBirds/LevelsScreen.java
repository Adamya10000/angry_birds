package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

public class LevelsScreen implements Screen {
    private Main game;
    private Texture levelsScreen;
    private Texture level;
    private Texture backButton;
    private Texture loadButton;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 1200;
    private static final int VIRTUAL_HEIGHT = 633;

    public LevelsScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        levelsScreen = new Texture("levelScreen.jpg");
        level = new Texture("level1.png");
        backButton = new Texture("back.png");
        loadButton = new Texture("load.png");

        Image mainscreen = new Image(levelsScreen);
        mainscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        Image level1 = new Image(level);
        level1.setColor(1, 1, 1, 0);
        level1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game,1));
            }
        });
        level1.setSize(80, 75);
        level1.setPosition(60,510);

        Image load = new Image(loadButton);
        load.setSize(100,95);
        load.setPosition(VIRTUAL_WIDTH- 110, 7);

        load.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadScreen(game));
            }
        });

        Image level2 = new Image(level);
        level2.setSize(80, 75);
        level2.setPosition(160, 510);
        level2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game,2));
            }
        });

        Image back = new Image(backButton);
        back.setSize(100, 90);
        back.setPosition(6,7);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });


        stage.addActor(mainscreen);
        stage.addActor(level1);
        stage.addActor(level2);
        stage.addActor(back);
        stage.addActor(load);
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
