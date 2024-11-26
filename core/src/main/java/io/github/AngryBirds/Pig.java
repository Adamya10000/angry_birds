package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pig implements GameObject {
    private Body pigBody;
    private Image pigImage;
    private float health;
    private boolean destroyed;
    private PhysicsManager physicsManager;
    private static final float MAX_HEALTH = 100f;
    private static final float PIG_MASS = 10f;

    public Pig(TextureRegion texture, PhysicsManager physicsManager, float x, float y) {
        this.pigImage = new Image(texture);
        this.health = MAX_HEALTH;
        this.destroyed = false;
        this.physicsManager = physicsManager;

        // Create body using PhysicsManager method
        float pigWidth = pigImage.getWidth();
        float pigHeight = pigImage.getHeight();
        pigBody = physicsManager.createPigBody(x, y, pigWidth/2, pigHeight/2);
    }

    @Override
    public void takeDamage(float impactForce) {
        // Damage calculation based on impact force
        float damage = impactForce * 0.5f; // Adjust multiplier as needed
        health -= damage;

        if (health <= 0) {
            destroyed = true;
            pigBody.setActive(false);
            pigImage.remove(); // Remove from stage
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
}
