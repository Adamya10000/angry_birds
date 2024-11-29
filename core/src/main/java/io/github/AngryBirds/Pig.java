package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pig implements GameObject {
    private Body pigBody;
    private Image pigImage;
    private float health;
    private boolean destroyed;
    private PhysicsManager physicsManager;
    private Box2DCollisionManager collisionManager;
    private boolean markedForRemoval = false;
    private float damage;

    public enum PigType {
        SMALL(2f, 5f),
        TEETH(3f, 10f),
        BIG(20f, 15f);

        private final float maxHealth;
        private final float damageMultiplier;

        PigType(float health, float damage) {
            this.maxHealth = health;
            this.damageMultiplier = damage;
        }

        public float getMaxHealth() {
            return maxHealth;
        }

        public float getDamageMultiplier() {
            return damageMultiplier;
        }
    }

    private PigType type;

    public Pig(TextureRegion texture, PigType type, PhysicsManager physicsManager, float x, float y, Box2DCollisionManager collisionManager) {
        this.pigImage = new Image(texture);
        this.type = type;
        this.health = type.getMaxHealth();
        this.damage = type.getDamageMultiplier();
        this.destroyed = false;
        this.physicsManager = physicsManager;
        this.collisionManager = collisionManager;

        // Create body using PhysicsManager method
        float pigWidth = pigImage.getWidth();
        float pigHeight = pigImage.getHeight();
        pigBody = physicsManager.createPigBody(x, y, pigWidth/2, pigHeight/2);
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        float damageMultiplier = 1.0f;

        if (obj instanceof Bird) {
            Bird bird = (Bird) obj;
            damageMultiplier = bird.calculateDamageToTarget(this, impactForce);
        }

        float effectiveDamage = impactForce * damageMultiplier;
        health -= effectiveDamage;

        if (health <= 0) {
            removeFromGame(obj);
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public Image getImage() {
        return pigImage;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getMass() {
        switch (type) {
            case SMALL: return 5f;
            case TEETH: return 10f;
            case BIG: return 15f;
            default: return 10f;
        }
    }

    @Override
    public Body getBody() {
        return pigBody;
    }

    @Override
    public void removeFromGame(GameObject obj) {
        if (destroyed) return;

        destroyed = true;
        collisionManager.removeFromGameObject(obj);
        markedForRemoval = true;
    }

    @Override
    public void safeRemoveFromPhysicsWorld() {
        if (!markedForRemoval) return;

        if (pigBody != null) {
            pigBody.setActive(false);
        }

        pigImage.remove();
    }

    public float getDamage() {
        return damage;
    }

    public PigType getType() {
        return type;
    }


}
