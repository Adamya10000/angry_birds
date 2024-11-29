package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird implements GameObject {
    private Body birdBody;
    private Image birdImage;
    private float health;
    private boolean destroyed;
    private float mass;
    private static final float MAX_HEALTH = 3f;
    private boolean isLaunched = false;
    private Box2DCollisionManager collisionManager;
    private boolean markedForRemoval = false;

    // Enhanced Bird Types with Damage Multipliers
    public enum BirdType {
        RED(4f, 50.0f),
        YELLOW(6f, 75.0f),
        BLACK(8f, 100.0f);

        private final float mass;
        private final float damageMultiplier;

        BirdType(float mass, float damageMultiplier) {
            this.mass = mass;
            this.damageMultiplier = damageMultiplier;
        }

        public float getM() {
            return mass;
        }

        public float getDamageMultiplier() {
            return damageMultiplier;
        }
    }


    private BirdType type;

    public Bird(TextureRegion texture, BirdType type, PhysicsManager physicsManager, float x, float y, Box2DCollisionManager collisionManager) {
        this.birdImage = new Image(texture);
        this.type = type;
        this.health = MAX_HEALTH;
        this.mass = type.getM();
        this.destroyed = false;
        this.collisionManager = collisionManager;

        // Create body using PhysicsManager method
        float birdRadius = birdImage.getWidth() * 0.5f;
        birdBody = physicsManager.createBirdBody(x, y, birdRadius);
    }

    public void launch(Vector2 launchVelocity) {
        if (!isLaunched) {
            birdBody.setType(BodyDef.BodyType.DynamicBody);
            birdBody.setLinearVelocity(launchVelocity);
            isLaunched = true;
        }
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        float damage = impactForce * 0.2f;
        health -= damage;
    }

    public float calculateDamageToTarget(GameObject target, float impactForce) {
        // Massive base damage calculation
        float baseDamage = impactForce ; // Dramatically increased base damage

        if (target instanceof Pig) {
            // Extremely high damage to pigs
            return baseDamage * type.getDamageMultiplier() ;
        } else if (target instanceof Block) {
            // Massive block destruction potential
            return baseDamage * type.getDamageMultiplier() * 10;
        }

        return baseDamage;
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
        return mass;
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

}
