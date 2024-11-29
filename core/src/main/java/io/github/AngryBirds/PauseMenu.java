package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseMenu implements Screen {
    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Stage stage;
    private Texture restartButton;
    private Texture gameScreenTexture;
    private Texture backButton;
    private Texture resumeButton;
    private Texture saveButton;
    private int level;
    private GameScreen gameScreen;
    private SaveGame state;

    public PauseMenu(Main game, Viewport viewport, OrthographicCamera camera, Texture gameScreenTexture, int level, GameScreen gamescreen) {
        this.viewport = viewport;
        this.camera = camera;
        this.game = game;
        this.gameScreenTexture = gameScreenTexture;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.stage = new Stage(viewport);
        this.level = level;
        Gdx.input.setInputProcessor(stage);
        this.gameScreen = gamescreen;
    }

    @Override
    public void show() {
        restartButton = new Texture("restart.png");
        backButton = new Texture("back.png");
        resumeButton = new Texture("resume.png");
        saveButton = new Texture("save.png");

        Image restartImage = new Image(restartButton);
        restartImage.setSize(200, 190);
        restartImage.setPosition(300, 850);

        restartImage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level));
            }
        });

        Image saveImage = new Image(saveButton);
        saveImage.setSize(200, 190);
        saveImage.setPosition(300, 600);

        saveImage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                state = gameScreen.getCurrentSaveGame(); // Assuming you passed gameScreen to PauseMenu
                gameScreen.saveGameStateToFile(state);
                System.out.println("File path: " + Gdx.files.local("saveGameState.dat").file().getAbsolutePath());
                System.out.println("Game state saved!");
            }
        });

        Image resume = new Image(resumeButton);
        resume.setSize(200, 190);
        resume.setPosition(750, 600);

        resume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                dispose();
                game.setScreen(gameScreen);
            }
        });

        Image back = new Image(backButton);
        back.setColor(1, 1, 1, 1);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelsScreen(game));
            }
        });
        back.setSize(200, 190);
        back.setPosition(300,350);


        stage.addActor(restartImage);
        stage.addActor(saveImage);
        stage.addActor(back);
        stage.addActor(resume);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(gameScreenTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


        shapeRenderer.setProjectionMatrix(camera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.4431f, 0.1f, 0.9706f, 1);
        shapeRenderer.rect(0, 0, (float) Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0.9f, 0.2f, 1);
        shapeRenderer.rect((float) Gdx.graphics.getWidth() / 3, 0, 10, Gdx.graphics.getHeight());
        shapeRenderer.end();


        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Update viewport
        stage.getViewport().update(width, height, true); // Update stage viewport
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        restartButton.dispose();
        stage.dispose();
    }
}
