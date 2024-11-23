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
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private LevelManager levelManager;
    private int currentLevel;

    // Textures
    private Texture gameScreen;
    private Texture pauseButton;
    private Texture blockSprites;
    private Texture pigSprites;
    private TextureRegion block1, block2, block3, block4, pig1, pig2;

    // Constants
    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

    // Level state
    private int currentScore;
    private int birdsRemaining;

    public GameScreen(Main game, int level) {
        this.game = game;
        this.currentLevel = level;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    private void loadTextures() {
        gameScreen = new Texture("gamescreen.jpg");
        pauseButton = new Texture("pauseButton.png");
        blockSprites = new Texture("blockSprites.png");
        pigSprites = new Texture("pigSprites.png");

        // Initialize texture regions
        pig1 = new TextureRegion(pigSprites, 255, 640, 99, 98);
        pig2 = new TextureRegion(pigSprites, 255, 740, 99, 98);

        block1 = new TextureRegion(blockSprites, 320, 627, 204, 18);
        block2 = new TextureRegion(blockSprites, 490, 715, 167, 18);
        block3 = new TextureRegion(blockSprites, 806, 504, 83, 40);
        block4 = new TextureRegion(blockSprites, 161, 857, 42, 40);
    }

    private void setupUI() {
        Image mainscreen = new Image(gameScreen);
        mainscreen.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        Image pause = new Image(pauseButton);
        pause.setSize(180, 160);
        pause.setPosition(0, VIRTUAL_HEIGHT - pause.getHeight());
        pause.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenu(game, viewport, camera, gameScreen));
            }
        });

        stage.addActor(mainscreen);
        stage.addActor(pause);
    }

    private void setupCatapult() {
        Catapult catapult = new Catapult();
        Image catapult1 = catapult.getCatapult1();
        catapult1.setPosition(200, 200);
        Image catapult2 = catapult.getCatapult2();
        catapult2.setPosition(178, 310);

        RedBird redBird = new RedBird();
        redBird.getRed().setPosition(140, 390);

        stage.addActor(catapult1);
        stage.addActor(redBird.getRed());
        stage.addActor(catapult2);
    }

    private void doubler(Image image) {
        image.setSize(image.getWidth() * 2, image.getHeight() * 2);
    }

    private void doublerH(Image image) {
        image.setSize((image.getWidth() * 4) / 3, image.getHeight() * 2);
    }

    @Override
    public void show() {
        loadTextures();
        setupUI();
        setupCatapult();

        // Initialize level manager with loaded textures
        TextureRegion[] blockTextures = {block1, block2, block3, block4};
        TextureRegion[] pigTextures = {pig1, pig2};
        levelManager = new LevelManager(blockTextures, pigTextures);

        // Load and build current level
        LevelData currentLevelData = levelManager.getLevel(currentLevel);
        if (currentLevelData != null) {
            birdsRemaining = currentLevelData.getAvailableBirds();
            buildLevel(currentLevelData);
        }
    }

    private void buildLevel(LevelData levelData) {
        for (LevelData.GameObject obj : levelData.getObjects()) {
            switch (obj.getType()) {
                case WOOD:
                    Wood wood = new Wood(obj.getTexture());
                    Image woodImage = wood.getBlock();
                    woodImage.setPosition(obj.getX(), obj.getY());
                    woodImage.setRotation(obj.getRotation());
                    if (obj.getTexture() == block1) {
                        doubler(woodImage);
                    } else {
                        doublerH(woodImage);
                    }
                    stage.addActor(woodImage);
                    break;

                case STONE:
                    Stone stone = new Stone(obj.getTexture());
                    Image stoneImage = stone.getBlock();
                    stoneImage.setPosition(obj.getX(), obj.getY());
                    stoneImage.setRotation(obj.getRotation());
                    doubler(stoneImage);
                    stage.addActor(stoneImage);
                    break;

                case GLASS:
                    Glass glass = new Glass(obj.getTexture());
                    Image glassImage = glass.getBlock();
                    glassImage.setPosition(obj.getX(), obj.getY());
                    glassImage.setRotation(obj.getRotation());
                    doubler(glassImage);
                    stage.addActor(glassImage);
                    break;

                case PIG:
                    Pig pig = new Pig(obj.getTexture());
                    Image pigImage = pig.getPig();
                    pigImage.setPosition(obj.getX(), obj.getY());
                    stage.addActor(pigImage);
                    break;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Debug: Press ENTER to simulate winning
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new WinScreen(game));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        gameScreen.dispose();
        pauseButton.dispose();
        blockSprites.dispose();
        pigSprites.dispose();
    }
}
