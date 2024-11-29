package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird implements GameObject {
    private Body birdBody;
    private Image birdImage;
    private float health;
    private boolean destroyed;
    private float damage;
    private PhysicsManager physicsManager;
    private static final float MAX_HEALTH = 3f;
    private boolean isLaunched = false;
    private Stage stage;
    private Box2DCollisionManager collisionManager;
    private boolean markedForRemoval = false;

    public enum BirdType {
        RED(50f), YELLOW(75f), BLACK(100f);

        private final float damageMultiplier;

        BirdType(float damage) {
            this.damageMultiplier = damage;
        }

        public float getDamageMultiplier() {
            return damageMultiplier;
        }
    }

    private BirdType type;

    public Bird(TextureRegion texture, BirdType type, PhysicsManager physicsManager, float x, float y, Stage stage, Box2DCollisionManager collisionManager)     {
        this.birdImage = new Image(texture);
        this.type = type;
        this.health = MAX_HEALTH;
        this.damage = type.getDamageMultiplier();
        this.destroyed = false;
        this.physicsManager = physicsManager;
        this.stage = stage;
        this.collisionManager = collisionManager;

        // Create body using PhysicsManager method
        float birdRadius = birdImage.getWidth() * 0.5f;
        birdBody = physicsManager.createBirdBody(x, y, birdRadius);
    }

    public void launch(Vector2 launchVelocity) {
        if (!isLaunched) {
            // Change body type to dynamic when launched
            birdBody.setType(BodyDef.BodyType.DynamicBody);
            birdBody.setLinearVelocity(launchVelocity);
            isLaunched = true;
        }
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        float damage = impactForce * 0.2f;
        health -= damage;
        System.out.println("Remaining health of BIRD " + type + ": " + health);

        if (health <= 0) {
            System.out.println("here0");
            removeFromGame(obj);
            System.out.println("here - 1");

        }
    }

    @Override
    public void removeFromGame(GameObject obj) {
        if (destroyed) return;
        destroyed = true;
        markedForRemoval = true; // Mark for safe removal
        collisionManager.removeFromGameObject(obj);
    }

    public void safeRemoveFromPhysicsWorld() {
        if (!markedForRemoval) return;

        if (birdBody != null) {
            birdBody.setActive(false);
        }

        // Remove image from stage
        birdImage.remove();

    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public Image getImage() {
        return birdImage;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getMass() {
        return 5f;
    }

    @Override
    public Body getBody() {
        return birdBody;
    }


    public BirdType getType() {
        return type;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public float getDamage() {
        return damage;
    }
}
