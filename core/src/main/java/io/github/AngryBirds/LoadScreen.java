package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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

    private SaveGame loadSavedGameFromFile(int currentLevel) {
        try {
            FileHandle file = Gdx.files.local("saveGameState" + currentLevel + ".dat");

            // Check if the file exists before attempting to load
            if (!file.exists()) {
                System.out.println("No saved game found!");
                return null;
            }

            // Read the saved file and deserialize the object
            ObjectInputStream in = new ObjectInputStream(file.read());
            SaveGame savedState = (SaveGame) in.readObject();  // Deserialize the saved game state
            in.close();  // Close the stream

            System.out.println("Game state loaded successfully from binary file!");
            return savedState;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load game state.");
            return null;
        }
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

        Image loadLevel1 = new Image(new Texture("loadBlock.png"));
        loadLevel1.setPosition(loadLevel1.getWidth() / 2, 0);
        loadLevel1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {

                    SaveGame save = loadSavedGameFromFile(1);
                    game.setScreen(new GameScreen(game, 1, true, save));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(loadscreen);
        stage.addActor(back);
        stage.addActor(loadGame);
        stage.addActor(loadLevel1);
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
