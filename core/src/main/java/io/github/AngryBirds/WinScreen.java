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

public class WinScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture loadscreenTexture;
    private Texture levelclear;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 2400;
    private static final int VIRTUAL_HEIGHT = 1500;

    public WinScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        loadscreenTexture = new Texture("gamescreen.jpg");
        levelclear = new Texture("levelClear.png");

        Image loadscreen = new Image(loadscreenTexture);
        loadscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        Image clear = new Image(levelclear);
        clear.setSize(levelclear.getWidth()*2, levelclear.getHeight()*2);
        clear.setPosition(((float) VIRTUAL_WIDTH /2) - clear.getWidth()/2,((float) VIRTUAL_HEIGHT /2) - clear.getHeight()/2);
        clear.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game));
            }
        });

        stage.addActor(loadscreen);
        stage.addActor(clear);
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
        levelclear.dispose();
    }
}
