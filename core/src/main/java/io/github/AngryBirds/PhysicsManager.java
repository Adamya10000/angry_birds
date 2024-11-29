package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsManager {
    public static final float WORLD_TO_BOX = 0.005f; // Reduced conversion to match original scaling
    public static final float BOX_TO_WORLD = 200f;   // Increased to match

    World world;
    private Body groundBody;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public PhysicsManager() {
        Vector2 gravity = new Vector2(0, 0f);
        world = new World(gravity, true);

        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX,
            Gdx.graphics.getHeight() * WORLD_TO_BOX);
        camera.position.set(5,3.3f , 0);
        createGroundBody();
    }

    private void createGroundBody() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, 0.95f); // Set ground level to 200f

        groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-2560, 0), new Vector2(2560, 0)); // Extend ground width

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 1.2f;
        fixtureDef.restitution = 0.1f;

        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
    }

    public Body createBlockBody(float x, float y, float width, float height, float rotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the body first
        Body body = world.createBody(bodyDef);

        // Create shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            width * WORLD_TO_BOX * 0.5f,  // Half width
            height * WORLD_TO_BOX * 0.5f  // Half height
        );

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1.2f;
        fixtureDef.restitution = 0.3f;

        // Create fixture
        body.createFixture(fixtureDef);
        shape.dispose();

        // Use setTransform to properly position and rotate the body
        body.setTransform(
            new Vector2(x * WORLD_TO_BOX, y * WORLD_TO_BOX),  // Position
            rotation * MathUtils.degreesToRadians  // Rotation in radians
        );

        return body;
    }

    public Body createBirdBody(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Initially static, will be changed to dynamic when launched

        // Create the body
        Body body = world.createBody(bodyDef);

        // Create circle shape
        CircleShape shape = new CircleShape();
        shape.setRadius(radius * WORLD_TO_BOX);

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1.2f;
        fixtureDef.restitution = 0.5f;

        // Create fixture
        body.createFixture(fixtureDef);
        shape.dispose();

        // Set initial position
        body.setTransform(
            new Vector2(x * WORLD_TO_BOX, y * WORLD_TO_BOX),
            0
        );

        return body;
    }

    public Body createPigBody(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the body
        Body body = world.createBody(bodyDef);

        // Create shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            width * WORLD_TO_BOX * 0.5f,  // Half width
            height * WORLD_TO_BOX * 0.5f  // Half height
        );

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Create fixture
        body.createFixture(fixtureDef);
        shape.dispose();

        // Set initial position
        body.setTransform(
            new Vector2(x * WORLD_TO_BOX, y * WORLD_TO_BOX),
            0
        );

        return body;
    }

    public void updatePhysics(float deltaTime) {
        world.step(deltaTime, 8, 3);
    }

    public void renderDebug() {
        // Adjust the camera's viewport to fit the screen dimensions
        camera.viewportWidth = Gdx.graphics.getWidth() * WORLD_TO_BOX;
        camera.viewportHeight = Gdx.graphics.getHeight() * WORLD_TO_BOX;
        camera.update();

        // Render physics bodies using the debug renderer
        debugRenderer.render(world, camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
    }

    public Vector2 toWorldCoordinates(Body body) {
        return new Vector2(
                body.getPosition().x * BOX_TO_WORLD,
                body.getPosition().y * BOX_TO_WORLD
        );
    }

    public void setWorldGravity(Vector2 gravity) {
        this.world.setGravity(gravity);
    }
}
