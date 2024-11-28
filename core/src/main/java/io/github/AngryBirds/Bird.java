package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird implements GameObject {
    private Body birdBody;
    private Image birdImage;
    private float health;
    private boolean destroyed;
    private float damage;
    private PhysicsManager physicsManager;
    private static final float MAX_HEALTH = 50f;
    private boolean isLaunched = false;

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

    public Bird(TextureRegion texture, BirdType type, PhysicsManager physicsManager, float x, float y) {
        this.birdImage = new Image(texture);
        this.type = type;
        this.health = MAX_HEALTH;
        this.damage = type.getDamageMultiplier();
        this.destroyed = false;
        this.physicsManager = physicsManager;

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
    public void takeDamage(float impactForce) {
        float damage = impactForce * 0.2f;
        health -= damage;

        if (health <= 0) {
            destroyed = true;
            birdBody.setActive(false);
        }
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
}
