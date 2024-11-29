package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Timer;


import java.util.*;

import static io.github.AngryBirds.PhysicsManager.WORLD_TO_BOX;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Physics-related managers
    private PhysicsManager physicsManager;
    private Box2DCollisionManager collisionManager;

    private ShapeRenderer shapeRenderer;
    private Vector2 currentDragVector = null;
    private Vector2 startPoint = null;

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
    private TextureRegion block1, block2, block3, block4, square1, square2, square3, pig1, pig2, pig3, red, yellow, black;

    // Constants
    private static final int VIRTUAL_WIDTH = 2560;
    private static final int VIRTUAL_HEIGHT = 1440;

    // Catapult and bird sequence
    private float catapultX = 270;
    private float catapultY = 460;
    private Image[] birds;
    private int currentBirdIndex = 0;
    private Image redImage, yellowImage, blackImage;

    //Lists of objects
    private Bird redBird, yellowBird, blackBird;
    private ArrayList<Bird> birdArrayList;
    private ArrayList<Block> blockArrayList;
    private ArrayList<Pig> pigArrayList;


    public GameScreen(Main game, int level) {
        this.game = game;
        this.currentLevel = level;
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        this.stage = new Stage(viewport);

        // Initialize Physics Managers
        this.physicsManager = new PhysicsManager();
        this.collisionManager = new Box2DCollisionManager(physicsManager.world);
        this.birdArrayList = new ArrayList<>();
        this.blockArrayList = new ArrayList<>();
        this.pigArrayList = new ArrayList<>();
        Gdx.input.setInputProcessor(stage);
    }

    private void loadTextures() {
        gameScreen = new Texture("gamescreen.jpg");
        pauseButton = new Texture("pauseButton.png");
        blockSprites = new Texture("blockSprites.png");
        pigSprites = new Texture("pigSprites.png");
        birdSprites = new Texture("birdSprites.png");

        pig1 = new TextureRegion(pigSprites, 255, 640, 99, 98);
        pig2 = new TextureRegion(pigSprites, 255, 740, 99, 98);
        pig3 = new TextureRegion(pigSprites, 131,0,125,110);

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
        GameScreen save = this;
        pause.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenu(game, viewport, camera, gameScreen, currentLevel, save));
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
        redBird = new Bird(red, Bird.BirdType.RED, physicsManager, catapultX, catapultY, collisionManager);
        yellowBird = new Bird(yellow, Bird.BirdType.YELLOW, physicsManager, 150, 220, collisionManager);
        blackBird = new Bird(black, Bird.BirdType.BLACK, physicsManager, 60, 220, collisionManager);

        birdArrayList.add(redBird);
        birdArrayList.add(yellowBird);
        birdArrayList.add(blackBird);

        redImage = redBird.getImage();
        redImage.setPosition(catapultX, catapultY);
        redImage.setSize(redImage.getWidth()*2 , redImage.getHeight() *2);

        yellowImage = yellowBird.getImage();
        yellowImage.setSize(yellowImage.getWidth() , yellowImage.getHeight() );

        blackImage = blackBird.getImage();
        blackImage.setSize(blackImage.getWidth() , blackImage.getHeight() );

        birds = new Image[]{redImage, yellowImage, blackImage};
        birdObjects = Arrays.asList(redBird, yellowBird, blackBird);

        for (int i = 0; i < birds.length; i++) {
            stage.addActor(birds[i]);
            enableDragAndDrop(birds[i], i);

            collisionManager.addGameObject(birdObjects.get(i));
        }
    }

    private void enableDragAndDrop(Image birdImage, int birdIndex) {
        birdImage.addListener(new InputListener() {
            private boolean isDragging = false;
            private Bird currentBird;;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (birdIndex != currentBirdIndex) {
                    return false;
                }
                currentBird = birdObjects.get(birdIndex);
                if (currentBird.isLaunched()) {
                    return false;
                }

                isDragging = true;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (!isDragging) return;

                Vector2 touchPos = stage.screenToStageCoordinates(
                    new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );

                Vector2 birdPosition = new Vector2(
                    currentBird.getBody().getPosition().x * PhysicsManager.BOX_TO_WORLD,
                    currentBird.getBody().getPosition().y * PhysicsManager.BOX_TO_WORLD
                );

                float maxDragDistance = 200f;
                Vector2 catapultVector = new Vector2(catapultX, catapultY);
                Vector2 dragVector = touchPos.sub(catapultVector);

                if (dragVector.len() > maxDragDistance) {
                    dragVector.nor().scl(maxDragDistance);
                }

                Body birdBody = currentBird.getBody();
                birdBody.setTransform(
                    new Vector2(
                        (catapultX + dragVector.x) * 0.005f,
                        (catapultY + dragVector.y) * 0.005f
                    ),
                    birdBody.getAngle()
                );
                currentDragVector = dragVector;
                startPoint = birdPosition;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isDragging) return;

                isDragging = false;

                currentDragVector = null;
                startPoint = null;

                // Calculate launch vector (from catapult to touch point)
                Vector2 touchPos = stage.screenToStageCoordinates(
                    new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );

                Vector2 catapultVector = new Vector2(catapultX, catapultY);
                Vector2 launchVector = catapultVector.sub(touchPos);

                // Limit launch distance
                float maxLaunchDistance = 200f;
                if (launchVector.len() > maxLaunchDistance) {
                    launchVector.nor().scl(maxLaunchDistance);
                }

                // Launch the bird with correct velocity
                currentBird.launch(new Vector2(
                    launchVector.x * 0.045f,  // Adjust multiplier for more power
                    launchVector.y * 0.045f
                ));

                // Schedule the next bird to appear after a delay
                currentBirdIndex++;
                if (currentBirdIndex < birdObjects.size()) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            setBirdOnCatapult(currentBirdIndex);
                        }
                    }, 0.5f); // Delay in seconds
                }
            }


        });
    }

    private void setBirdOnCatapult(int birdIndex) {
        Bird nextBird = birdObjects.get(birdIndex);
        Body birdBody = nextBird.getBody();

        // Set the bird's body position to the catapult
        birdBody.setTransform(
            catapultX * WORLD_TO_BOX,
            catapultY * WORLD_TO_BOX,
            0
        );

        // Ensure the bird's body is static until launched
        birdBody.setType(BodyDef.BodyType.StaticBody);

        // Update the bird's image position
        nextBird.getImage().setPosition(catapultX, catapultY);
    }



    private void buildLevel(LevelData levelData) {
        // Clear previous level objects
        levelGameObjects.clear();

        for (LevelData.GameObj obj : levelData.getObjects()) {
            GameObject gameObject = null;
            Image objectImage;

            switch (obj.getType()) {
                case WOOD:
                    gameObject = new Wood(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation(), collisionManager);
                    blockArrayList.add((Block) gameObject);
                    break;
                case STONE:
                    gameObject = new Stone(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation(), collisionManager);
                    blockArrayList.add((Block) gameObject);
                    break;
                case GLASS:
                    gameObject = new Glass(obj.getTexture(), physicsManager, obj.getX(), obj.getY(), obj.getRotation(), collisionManager);
                    blockArrayList.add((Block) gameObject);
                    break;
                case PIG1:
                    gameObject = new Pig(obj.getTexture(), Pig.PigType.SMALL, physicsManager, obj.getX(), obj.getY(),  collisionManager);
                    pigArrayList.add((Pig) gameObject);
                    objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() / 2, objectImage.getHeight() / 2);
                    break;
                case PIG2:
                    gameObject = new Pig(obj.getTexture(), Pig.PigType.TEETH, physicsManager, obj.getX(), obj.getY(),  collisionManager);
                    pigArrayList.add((Pig) gameObject);
                    objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() / 1.7f, objectImage.getHeight() / 1.7f);
                    break;
                case PIG3:
                    gameObject = new Pig(obj.getTexture(), Pig.PigType.BIG, physicsManager, obj.getX(), obj.getY(),  collisionManager);
                    pigArrayList.add((Pig) gameObject);
                    objectImage = gameObject.getImage();
                    objectImage.setSize(objectImage.getWidth() / 1.3f  , objectImage.getHeight() /1.3f );
                    break;
                case REDBIRD:
                case YELLOWBIRD:
                case BLACKBIRD:
                default:
                    continue;
            }

            objectImage = gameObject.getImage();

            float imageWidth = objectImage.getWidth();
            float imageHeight = objectImage.getHeight();
            objectImage.setPosition(
                obj.getX() - imageWidth / 2,
                obj.getY() - imageHeight / 2
            );
            objectImage.setRotation(obj.getRotation());
            stage.addActor(objectImage);

            levelGameObjects.add(gameObject);
            collisionManager.addGameObject(gameObject);
        }
    }

    private boolean isInitialized = false;
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if(isInitialized) return;
        isInitialized = true;

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        loadTextures();
        setupUI();
        setupCatapult();

        TextureRegion[] blockTextures = {block1, block2, block3, block4, square1, square2, square3};
        TextureRegion[] pigTextures = {pig1, pig2, pig3};
        TextureRegion[] birdTextures = {red, yellow, black};
        levelManager = new LevelManager(blockTextures, pigTextures, birdTextures);

        LevelData currentLevelData = levelManager.getLevel(currentLevel);
        if (currentLevelData != null) {
            buildLevel(currentLevelData);
            setupBirds();
        }
        physicsManager.setWorldGravity(new Vector2(0, -7f));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        physicsManager.updatePhysics(delta);
        physicsManager.renderDebug();

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        stage.act(delta);

        updateObjectPositions();

        stage.draw();

        if (currentDragVector != null && startPoint != null) {
            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            drawProjectileTrajectory(currentDragVector, startPoint);
        }

        for(Bird bird : birdArrayList){
            bird.safeRemoveFromPhysicsWorld();
        }

        for(Block block : blockArrayList){
            block.safeRemoveFromPhysicsWorld();
        }

        for(Pig pig : pigArrayList){
            pig.safeRemoveFromPhysicsWorld();
        }

        checkLevelCompletion();
    }

    private boolean winScreenDisplayed = false;
    private boolean loseScreenDisplayed = false;
    private void checkLevelCompletion() {
        // Check if all birds have been launched
        boolean allPigsDestroyed = pigArrayList.stream().allMatch(pig -> pig.isDestroyed());
        boolean allBirdsLaunched = currentBirdIndex >= birdObjects.size();

        if(allBirdsLaunched){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if(!allPigsDestroyed && !winScreenDisplayed && !loseScreenDisplayed){
                        loseScreenDisplayed = true;
                        game.setScreen(new LoseScreen(game, viewport, camera, gameScreen, currentLevel));
                    }
                }
            }, 4f);
        }

        if(allPigsDestroyed){
            winScreenDisplayed = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game.setScreen(new WinScreen(game, viewport, camera, gameScreen, currentLevel));
                }
            }, 2f);
        }
    }

    private void drawProjectileTrajectory(Vector2 dragVector, Vector2 startPoint) {
        Gdx.gl.glLineWidth(6f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1); // Black color

        // Invert the dragVector to get the correct launch direction
        Vector2 invertedDragVector = dragVector.cpy().scl(-1);

        // Initial velocity components from inverted dragVector
        float initialVelocityX = invertedDragVector.x * 0.045f;
        float initialVelocityY = invertedDragVector.y * 0.045f;

        // Gravity constant (simulated)
        float gravity = -9.8f * 0.005f;  // Adjust gravity strength as necessary

        // Time step for trajectory calculations
        float timeStep = 0.1f;  // Small time steps for smoother trajectory
        float maxDistance = 600f; // Extend trajectory to 1500 pixels

        Vector2 currentPoint = new Vector2(startPoint);
        float totalDistance = 0;
        float time = 0f;

        // Dotted line parameters
        float segmentLength = 30f;   // Longer line segments
        float gapLength = 15f;       // Shorter gaps
        boolean drawSegment = true;  // Flag to alternate between drawing and skipping

        while (totalDistance < maxDistance) {
            // Update time
            time += timeStep;

            // Calculate new position based on projectile motion equations
            float x = initialVelocityX * time + startPoint.x;
            float y = (initialVelocityY * time) + (0.5f * gravity * time * time) + startPoint.y;

            Vector2 nextPoint = new Vector2(x, y);

            // Calculate distance between current and next point
            float segmentDistance = currentPoint.dst(nextPoint);
            float remainingDistance = segmentDistance;
            Vector2 direction = nextPoint.cpy().sub(currentPoint).nor();

            // Draw dotted line
            while (remainingDistance > 0) {
                if (drawSegment) {
                    // Determine the end of this segment
                    float drawLength = Math.min(remainingDistance, segmentLength);
                    Vector2 segmentEnd = currentPoint.cpy().add(direction.cpy().scl(drawLength));

                    shapeRenderer.line(currentPoint.x, currentPoint.y, segmentEnd.x, segmentEnd.y);

                    currentPoint.set(segmentEnd);
                    remainingDistance -= drawLength;
                } else {
                    // Skip this segment
                    float gapDrawLength = Math.min(remainingDistance, gapLength);
                    currentPoint.add(direction.cpy().scl(gapDrawLength));
                    remainingDistance -= gapDrawLength;
                }

                // Toggle between drawing and skipping
                drawSegment = !drawSegment;
            }

            // Update total distance
            totalDistance += segmentDistance;
            currentPoint.set(nextPoint);

            // Stop rendering if the total distance exceeds 1500 pixels or hits the ground
            if (currentPoint.y < 0 || totalDistance >= maxDistance) break;
        }

        shapeRenderer.end();
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
    public void pause() {
    }

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
        shapeRenderer.dispose();

    }

//    public SaveGame getCurrentSaveGame() {
//        SaveGame state = new SaveGame();
//        state.currentLevel = currentLevel;
//
//        // Save birds
//        state.birds = new ArrayList<>();
//        for (Bird bird : birdArrayList) {
//            if(!bird.isLaunched()){
//                SaveGame.BirdState birdState = new SaveGame.BirdState();
//                birdState.x = bird.getBody().getPosition().x * PhysicsManager.BOX_TO_WORLD;
//                birdState.y = bird.getBody().getPosition().y * PhysicsManager.BOX_TO_WORLD;
//                birdState.isLaunched = bird.isLaunched();
//                state.birds.add(birdState);
//            }
//        }
//
//        // Save blocks
//        state.blocks = new ArrayList<>();
//        for (Block block : blockArrayList) {
//            SaveGame.BlockState blockState = new SaveGame.BlockState();
//            blockState.x = block.getBody().getPosition().x * PhysicsManager.BOX_TO_WORLD;
//            blockState.y = block.getBody().getPosition().y * PhysicsManager.BOX_TO_WORLD;
//            blockState.rotation = block.getBody().getAngle();
//            if ((GameObject) block instanceof Wood){
//                blockState.type = "WOOD";
//            } else if((GameObject) block instanceof Stone){
//                blockState.type = "STONE";
//            } else if ((GameObject) block instanceof Glass) {
//                blockState.type = "GLASS";
//            }
//            state.blocks.add(blockState);
//        }
//
//        // Save pigs
//        state.pigs = new ArrayList<>();
//        for (Pig pig : pigArrayList) {
//            SaveGame.PigState pigState = new SaveGame.PigState();
//            pigState.x = pig.getBody().getPosition().x * PhysicsManager.BOX_TO_WORLD;
//            pigState.y = pig.getBody().getPosition().y * PhysicsManager.BOX_TO_WORLD;
//            pigState.isDestroyed = pig.isDestroyed();
//            state.pigs.add(pigState);
//        }
//
//        return state;
//    }
//
//    public void saveGameStateToFile(SaveGame state) {
//        com.badlogic.gdx.utils.Json json = new com.badlogic.gdx.utils.Json();
//        String jsonString = json.toJson(state);
//
//        // Save to a file
//        if(currentLevel == 1){
//            FileHandle file = Gdx.files.local("saveGameState1.json");
//            file.writeString(jsonString, false);
//        } else if (currentLevel == 2) {
//            FileHandle file = Gdx.files.local("saveGameState2.json");
//            file.writeString(jsonString, false);
//        } else {
//            FileHandle file = Gdx.files.local("saveGameState3.json");
//            file.writeString(jsonString, false);
//        }
//    }
//
//    public SaveGame loadSavedGameFromFile() {
//        FileHandle file;
//        if(currentLevel == 1){
//            file = Gdx.files.local("saveGameState1.json");
//        } else if (currentLevel == 2) {
//            file = Gdx.files.local("saveGameState2.json");
//        } else {
//            file = Gdx.files.local("saveGameState3.json");
//        }
//
//        // Check if the save file exists
//        if (file.length() == 0) {
//            System.out.println("No saved game found!");
//            return null; // Handle gracefully
//        }
//
//        // Read the JSON file
//        String jsonString = file.readString();
//
//        // Deserialize into GameState
//        com.badlogic.gdx.utils.Json json = new com.badlogic.gdx.utils.Json();
//        return json.fromJson(SaveGame.class, jsonString);
//    }

}
