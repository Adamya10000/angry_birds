package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pig implements GameObject {
    private Body pigBody;
    private Image pigImage;
    private float health;
    private boolean destroyed;
    private PhysicsManager physicsManager;
    private static final float MAX_HEALTH = 1f;
    private static final float PIG_MASS = 10f;
    private Stage stage;
    private Box2DCollisionManager collisionManager;
    private boolean markedForRemoval = false;
    private float damage;


    public Pig(TextureRegion texture, PhysicsManager physicsManager, float x, float y, Stage stage, Box2DCollisionManager collisionManager) {
        this.pigImage = new Image(texture);
        this.health = MAX_HEALTH;
        this.destroyed = false;
        this.physicsManager = physicsManager;
        this.stage = stage;
        this.damage = 10f;
        // Create body using PhysicsManager method
        float pigWidth = pigImage.getWidth();
        float pigHeight = pigImage.getHeight();
        pigBody = physicsManager.createPigBody(x, y, pigWidth/2, pigHeight/2);
        this.collisionManager = collisionManager;
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        // Damage calculation based on impact force
        float damage = impactForce * 0.5f; // Adjust multiplier as needed
        health -= damage;
        System.out.println("Remaining health of PIG: " + health);

        if (health <= 0) {
            removeFromGame(obj); // Remove from stage
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
        return PIG_MASS;
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
        // Remove from physics world
        markedForRemoval = true; // Mark for safe removal
    }

    @Override
    public void safeRemoveFromPhysicsWorld() {
        if (!markedForRemoval) return;

        if (pigBody != null) {
            pigBody.setActive(false);
        }

        // Remove image from stage
        pigImage.remove();
    }

    public float getDamage() {
        return damage;
    }
}
