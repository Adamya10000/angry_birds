package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private LevelManager levelManager;
    private int currentLevel;
    private CollisionManager collisionManager;
    private List<Bird> birdObjects = new ArrayList<>();

    // Textures
    private Texture gameScreen;
    private Texture pauseButton;
    private Texture blockSprites;
    private Texture pigSprites;
    private Texture birdSprites;
    private TextureRegion block1, block2, block3, block4, pig1, pig2, red, yellow, black;

    // Constants
    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

    public static final float GRAVITY = -500f; // Gravity constant (pixels/sec^2)
    public static final float GROUND_Y = 0;   // Ground level Y position

    // Catapult and bird sequence
    private float catapultX = 170; // Catapult's X position
    private float catapultY = 360; // Catapult's Y position
    private Image[] birds; // Array to hold the bird images in sequence
    private int currentBirdIndex = 0; // Tracks the currently active bird

    // Level state
    private int currentScore;

    public GameScreen(Main game, int level) {
        this.game = game;
        this.currentLevel = level;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        this.collisionManager = new CollisionManager(stage);
    }

    private void loadTextures() {
        gameScreen = new Texture("gamescreen.jpg");
        pauseButton = new Texture("pauseButton.png");
        blockSprites = new Texture("blockSprites.png");
        pigSprites = new Texture("pigSprites.png");
        birdSprites = new Texture("birdSprites.png");

        // Initialize texture regions
        pig1 = new TextureRegion(pigSprites, 255, 640, 99, 98);
        pig2 = new TextureRegion(pigSprites, 255, 740, 99, 98);

        block1 = new TextureRegion(blockSprites, 320, 627, 204, 18);
        block2 = new TextureRegion(blockSprites, 490, 715, 167, 18);
        block3 = new TextureRegion(blockSprites, 806, 504, 83, 40);
        block4 = new TextureRegion(blockSprites, 161, 857, 42, 40);

        red = new TextureRegion(birdSprites, 300, 520, 65, 62);
        yellow = new TextureRegion(birdSprites, 270, 370, 73, 71);
        black = new TextureRegion(birdSprites, 1, 378, 65, 84);
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

        stage.addActor(catapult1);
        stage.addActor(catapult2);
    }

    private void setupBirds() {
        Image redImage = new Image(red);
        Image yellowImage = new Image(yellow);
        Image blackImage = new Image(black);

        // Create Bird objects with their respective types
        RedBird redBird = new RedBird(red);
        YellowBird yellowBird = new YellowBird(yellow);
        BlackBird blackBird = new BlackBird(black);

        // Position birds initially off-screen, only the first bird is on the catapult
        redImage = redBird.getImage();
        redImage.setPosition(catapultX, catapultY);
        redImage.setSize(redImage.getWidth() * 2, redImage.getHeight() * 2);

        yellowImage = yellowBird.getImage();
        yellowImage.setPosition(-100, -100);
        yellowImage.setSize(yellowImage.getWidth() * 2, yellowImage.getHeight() * 2);

        blackImage = blackBird.getImage();
        blackImage.setPosition(-100, -100);
        blackImage.setSize(blackImage.getWidth() * 2, blackImage.getHeight() * 2);

        // Add birds to lists and stage
        birds = new Image[]{redImage, yellowImage, blackImage};
        birdObjects = Arrays.asList(redBird, yellowBird, blackBird);

        for (int i = 0; i < birds.length; i++) {
            stage.addActor(birds[i]);
            enableDragAndDrop(birds[i], i);

            // Add to collision manager
            collisionManager.addBird(birdObjects.get(i));
        }
    }

    private void enableDragAndDrop(Image birdImage, int birdIndex) {
        birdImage.addListener(new DragListener() {
            private float releaseX, releaseY; // Declare releaseX and releaseY here

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                if (birdIndex != currentBirdIndex) {
                    cancel(); // Only allow dragging the current bird
                }
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                birdImage.moveBy(x - birdImage.getWidth() / 2, y - birdImage.getHeight() / 2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                // Get the release position
                releaseX = birdImage.getX();
                releaseY = birdImage.getY();

                // Calculate initial velocity based on drag distance from catapult
                float dx = catapultX - releaseX;
                float dy = catapultY - releaseY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                float maxDistance = 200; // Maximum pull distance

                // Scale velocity based on drag distance, capped at max distance
                float velocityScale = Math.min(distance / maxDistance, 1.0f);
                float velocityX = dx * velocityScale * 0.5f; // Adjust multiplier for speed
                float velocityY = dy * velocityScale * 0.5f;

                // Apply projectile motion
                Bird currentBird = birdObjects.get(birdIndex);
                birdImage.addAction(new ProjectileAction(velocityX, velocityY,200f) {
                    @Override
                    public boolean act(float delta) {
                        boolean finished = super.act(delta);

                        // Trigger collision check when bird is in motion
                        if (!finished) {
                            collisionManager.checkCollisions();
                        }

                        return finished;
                    }
                });

                // Set next bird on the catapult
                currentBirdIndex++;
                if (currentBirdIndex < birds.length) {
                    birds[currentBirdIndex].setPosition(catapultX, catapultY);
                }
            }
        });
    }


    private void doubler(Image image) {
        image.setSize(image.getWidth() * 2, image.getHeight() * 2);
    }

    private void doublerH(Image image) {
        image.setSize((image.getWidth() * 4) / 3, image.getHeight() * 2);
    }

    private void buildLevel(LevelData levelData) {
        for (LevelData.GameObject obj : levelData.getObjects()) {
            GameObject gameObject = null;
            Image objectImage = null;

            switch (obj.getType()) {
                case WOOD:
                    gameObject = new Wood(obj.getTexture());
                    objectImage = gameObject.getImage();
                    if (obj.getTexture() == block1) {
                        objectImage.setSize(objectImage.getWidth() * 2, objectImage.getHeight() * 2);
                    } else {
                        objectImage.setSize((objectImage.getWidth() * 4) / 3, objectImage.getHeight() * 2);
                    }
                    break;

                case STONE:
                    gameObject = new Stone(obj.getTexture());
                    objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() * 2, objectImage.getHeight() * 2);
                    break;

                case GLASS:
                    gameObject = new Glass(obj.getTexture());
                    objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() * 2, objectImage.getHeight() * 2);
                    break;

                case PIG:
                    gameObject = new Pig(obj.getTexture());
                    objectImage = gameObject.getImage();
                    break;

                default:
                    continue;
            }

            // Set position and rotation
            objectImage.setPosition(obj.getX(), obj.getY());
            objectImage.setRotation(obj.getRotation());

            // Add to stage
            stage.addActor(objectImage);

            // Add to collision manager
            if (gameObject != null) {
                collisionManager.addGameObject(gameObject);
            }
        }
    }

    @Override
    public void show() {
        loadTextures();
        setupUI();
        setupCatapult();
        setupBirds();

        TextureRegion[] blockTextures = {block1, block2, block3, block4};
        TextureRegion[] pigTextures = {pig1, pig2};
        TextureRegion[] birdTextures = {red, yellow, black};
        levelManager = new LevelManager(blockTextures, pigTextures, birdTextures);

        LevelData currentLevelData = levelManager.getLevel(currentLevel);
        if (currentLevelData != null) {
            buildLevel(currentLevelData);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        // Check collisions every frame
        collisionManager.checkCollisions();

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
