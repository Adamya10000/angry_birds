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

public class LoadScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture loadscreenTexture;
    private Texture backButton;
    private Texture loadgame;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 2400;
    private static final int VIRTUAL_HEIGHT = 1500;

    public LoadScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        loadscreenTexture = new Texture("loadScreen.jpg");
        backButton = new Texture("back.png");
        loadgame = new Texture("loadgame.png");

        Image loadGame = new Image(loadgame);
        loadGame.setPosition(650, 1100);
        loadGame.setSize(loadgame.getWidth()*3, loadgame.getHeight()*3);

        Image loadscreen = new Image(loadscreenTexture);
        loadscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        Image back = new Image(backButton);
        back.setSize(150, 155);
        back.setPosition(20,20);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game));
            }
        });

        stage.addActor(loadscreen);
        stage.addActor(back);
        stage.addActor(loadGame);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        stage.dispose();
        loadscreenTexture.dispose();
        backButton.dispose();
    }
}
