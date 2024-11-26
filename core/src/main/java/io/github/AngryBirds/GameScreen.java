package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Physics-related managers
    private PhysicsManager physicsManager;
    private Box2DCollisionManager collisionManager;

    private LevelManager levelManager;
    private int currentLevel;

    private List<Bird> birdObjects = new ArrayList<>();
    private List<GameObject> levelGameObjects = new ArrayList<>();

    // Textures
    private Texture gameScreen;
    private Texture pauseButton;
    private Texture blockSprites;
    private Texture pigSprites;
    private Texture birdSprites;
    private TextureRegion block1, block2, block3, block4, square1, square2, square3, pig1, pig2, red, yellow, black;

    // Constants
    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

    // Catapult and bird sequence
    private float catapultX = 170; // Catapult's X position
    private float catapultY = 360; // Catapult's Y position
    private Image[] birds; // Array to hold the bird images in sequence
    private int currentBirdIndex = 0; // Tracks the currently active bird
    private Image redImage, yellowImage, blackImage;

    public GameScreen(Main game, int level) {
        this.game = game;
        this.currentLevel = level;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        this.stage = new Stage(viewport);

        // Initialize Physics Managers
        this.physicsManager = new PhysicsManager();
        this.collisionManager = new Box2DCollisionManager(physicsManager.world);

        Gdx.input.setInputProcessor(stage);
    }

    private void loadTextures() {
        gameScreen = new Texture("gamescreen.jpg");
        pauseButton = new Texture("pauseButton.png");
        blockSprites = new Texture("blockSprites.png");
        pigSprites = new Texture("pigSprites.png");
        birdSprites = new Texture("birdSprites.png");

        // Initialize texture regions (same as before)
        pig1 = new TextureRegion(pigSprites, 255, 640, 99, 98);
        pig2 = new TextureRegion(pigSprites, 255, 740, 99, 98);

        block1 = new TextureRegion(blockSprites, 320, 627, 202, 17);
        block2 = new TextureRegion(blockSprites, 490, 715, 111, 17);
        block3 = new TextureRegion(blockSprites, 806, 504, 83, 39);
        block4 = new TextureRegion(blockSprites, 161, 857, 42, 39);
        square3 = new TextureRegion(blockSprites, 845, 200, 80, 80);
        square1 = new TextureRegion(blockSprites, 725, 0, 80, 80);
        square2 = new TextureRegion(blockSprites, 2, 450, 80, 80);

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
                game.setScreen(new PauseMenu(game, viewport, camera, gameScreen, currentLevel));
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
        // Create Bird objects with their respective types
        Bird redBird = new Bird(red, Bird.BirdType.RED, physicsManager, catapultX, catapultY);
        Bird yellowBird = new Bird(yellow, Bird.BirdType.YELLOW, physicsManager, -100, -100);
        Bird blackBird = new Bird(black, Bird.BirdType.BLACK, physicsManager, -100, -100);

        // Position birds initially off-screen, only the first bird is on the catapult
        redImage = redBird.getImage();
        redImage.setPosition(catapultX, catapultY);
        redImage.setSize(redImage.getWidth()*2 , redImage.getHeight() *2);
        System.out.println("Red bird added at catapult position: " + catapultX + ", " + catapultY);

        yellowImage = yellowBird.getImage();
        yellowImage.setPosition(-100, -100);
        yellowImage.setSize(yellowImage.getWidth() , yellowImage.getHeight() );
        System.out.println("Yellow bird added off-screen at: -100, -100");

        blackImage = blackBird.getImage();
        blackImage.setPosition(-100, -100);
        blackImage.setSize(blackImage.getWidth() , blackImage.getHeight() );
        System.out.println("Black bird added off-screen at: -100, -100");

        // Add birds to lists and stage
        birds = new Image[]{redImage, yellowImage, blackImage};
        birdObjects = Arrays.asList(redBird, yellowBird, blackBird);

        for (int i = 0; i < birds.length; i++) {
            stage.addActor(birds[i]);
            System.out.println("Bird " + i + " added to stage.");
            enableDragAndDrop(birds[i], i);

            // Add to collision manager
            collisionManager.addGameObject(birdObjects.get(i));
            System.out.println("Bird " + i + " added to collision manager.");
        }
    }


    // Create Bird objects specific to Box2D
        //    Bird redBird = new Bird(red, Bird.BirdType.RED, physicsManager, catapultX, catapultY);
        //    Bird yellowBird = new Bird(yellow, Bird.BirdType.YELLOW, physicsManager, -100, -100);
        //    Bird blackBird = new Bird(black, Bird.BirdType.BLACK, physicsManager, -100, -100);

    private void enableDragAndDrop(Image birdImage, int birdIndex) {
        birdImage.addListener(new InputListener() {
            private boolean isDragging = false;
            private Bird currentBird;
            private Vector2 originalPosition;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Only allow dragging the current bird
                if (birdIndex != currentBirdIndex) {
                    return false;
                }

                currentBird = birdObjects.get(birdIndex);

                // Ensure bird hasn't been launched yet
                if (currentBird.isLaunched()) {
                    return false;
                }

                isDragging = true;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (!isDragging) return;

                // Convert screen coordinates to world coordinates
                Vector2 touchPos = stage.screenToStageCoordinates(
                    new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );

                // Limit dragging to a reasonable range around the catapult
                float maxDragDistance = 200f;
                Vector2 catapultVector = new Vector2(catapultX, catapultY);
                Vector2 dragVector = touchPos.sub(catapultVector);

                // Clamp drag distance
                if (dragVector.len() > maxDragDistance) {
                    dragVector.nor().scl(maxDragDistance);
                }

                // Update bird position visually
                Body birdBody = currentBird.getBody();
                birdBody.setTransform(
                    new Vector2(
                        (catapultX + dragVector.x) * 0.005f,
                        (catapultY + dragVector.y) * 0.005f
                    ),
                    birdBody.getAngle()
                );
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isDragging) return;

                isDragging = false;

                // Convert screen coordinates to world coordinates
                Vector2 touchPos = stage.screenToStageCoordinates(
                    new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );

                // Calculate launch vector (from catapult to touch point)
                Vector2 catapultVector = new Vector2(catapultX, catapultY);
                Vector2 launchVector = catapultVector.sub(touchPos);

                // Limit launch distance
                float maxLaunchDistance = 200f;
                if (launchVector.len() > maxLaunchDistance) {
                    launchVector.nor().scl(maxLaunchDistance);
                }
                // Launch the bird with correct velocity
                currentBird.launch(new Vector2(
                    launchVector.x * 0.04f,  // Increased multiplier for more power
                    launchVector.y * 0.04f
                ));

                // Prepare next bird
                currentBirdIndex++;
                if (currentBirdIndex < birdObjects.size()) {
                    // Position next bird at catapult
                    birds[currentBirdIndex].setPosition(catapultX, catapultY);
                }
            }
        });
    }


    private void buildLevel(LevelData levelData) {
        // Clear previous level objects
        levelGameObjects.clear();

        for (LevelData.GameObj obj : levelData.getObjects()) {
            GameObject gameObject = null;

            switch (obj.getType()) {
                case WOOD:
                    gameObject = new Wood(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation());
                    break;
                case STONE:
                    gameObject = new Stone(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation());
                    break;
                case GLASS:
                    gameObject = new Glass(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation());
                    break;
                case PIG:
                    gameObject = new Pig(obj.getTexture(), physicsManager, obj.getX(), obj.getY());
                    Image objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() / 2, objectImage.getHeight() / 2);
                    break;
                case REDBIRD:
                case YELLOWBIRD:
                case BLACKBIRD:
                    // Skip bird creation here as it's handled in setupBirds()
                    continue;
                default:
                    continue;
            }

            if (gameObject != null) {
                // Add image to stage with doubled size and precise positioning
                Image objectImage = gameObject.getImage();

                // Adjust positioning logic to center the image
                float imageWidth = objectImage.getWidth();
                float imageHeight = objectImage.getHeight();
                objectImage.setPosition(
                    obj.getX() - imageWidth / 2,
                    obj.getY() - imageHeight / 2
                );
                objectImage.setRotation(obj.getRotation());
                stage.addActor(objectImage);

                // Add to game objects list and collision manager
                levelGameObjects.add(gameObject);
                collisionManager.addGameObject(gameObject);
            }
        }
    }

    @Override
    public void show() {
        loadTextures();
        setupUI();
        setupCatapult();

        // Initialize level manager with textures
        TextureRegion[] blockTextures = {block1, block2, block3, block4, square1, square2, square3};
        TextureRegion[] pigTextures = {pig1, pig2};
        TextureRegion[] birdTextures = {red, yellow, black};
        levelManager = new LevelManager(blockTextures, pigTextures, birdTextures);

        // Get current level data and build level
        LevelData currentLevelData = levelManager.getLevel(currentLevel);
        if (currentLevelData != null) {
            buildLevel(currentLevelData);
            setupBirds();
        }
        if(currentLevel != 0){
            physicsManager.setWorldGravity(new Vector2(0, -7f));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update physics simulation
        physicsManager.updatePhysics(delta);
        physicsManager.renderDebug();

        // Update stage and actors
        stage.act(delta);

        // Update object positions based on physics bodies
        updateObjectPositions();

        // Draw stage
        stage.draw();

    }

    private void updateObjectPositions() {
        // Update bird positions
        for (Bird bird : birdObjects) {
            Body birdBody = bird.getBody();
            Image birdImage = bird.getImage();

            float radius = birdBody.getFixtureList().get(0).getShape().getRadius() * PhysicsManager.BOX_TO_WORLD;

            // Center the bird image on the circular physics body
            birdImage.setOrigin(birdImage.getWidth() / 2, birdImage.getHeight() / 2); // Set rotation origin to the center
            birdImage.setPosition(
                birdBody.getPosition().x * PhysicsManager.BOX_TO_WORLD - radius,
                birdBody.getPosition().y * PhysicsManager.BOX_TO_WORLD - radius
            );
            birdImage.setSize(radius * 2, radius * 2);
            birdImage.setRotation((float) Math.toDegrees(birdBody.getAngle())); // Apply rotation
        }

        // Update block positions
        for (GameObject obj : levelGameObjects) {
            Body objBody = obj.getBody();
            Image objectImage = obj.getImage();

            float width = objectImage.getWidth();
            float height = objectImage.getHeight();

            // Center the block image on the rectangular physics body
            objectImage.setOrigin(width / 2, height / 2); // Set rotation origin to the center
            objectImage.setPosition(
                objBody.getPosition().x * PhysicsManager.BOX_TO_WORLD - width / 2,
                objBody.getPosition().y * PhysicsManager.BOX_TO_WORLD - height / 2
            );
            objectImage.setRotation((float) Math.toDegrees(objBody.getAngle())); // Apply rotation
        }
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
        physicsManager.dispose();

        // Dispose textures
        gameScreen.dispose();
        pauseButton.dispose();
        blockSprites.dispose();
        pigSprites.dispose();
        birdSprites.dispose();
    }
}
