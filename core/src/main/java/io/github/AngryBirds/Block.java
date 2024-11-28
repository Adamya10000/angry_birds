package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Block implements GameObject {
    protected Body blockBody;
    protected Image blockImage;
    protected float health;
    protected boolean destroyed;
    protected float mass;
    protected PhysicsManager physicsManager;

    public Block(TextureRegion texture, float maxHealth, float mass, PhysicsManager physicsManager, float x, float y, float rotation) {
        this.blockImage = new Image(texture);

        // Create Box2D body first
        this.blockBody = physicsManager.createBlockBody(x , y , blockImage.getWidth(), blockImage.getHeight(), rotation);

        // Set image rotation to match body rotation
        this.blockImage.setRotation(rotation);

        this.health = maxHealth;
        this.mass = mass;
        this.destroyed = false;
        this.physicsManager = physicsManager;
    }

    @Override
    public void takeDamage(float impactForce) {
        float damage = impactForce * 0.3f;
        health -= damage;

        if (health <= 0) {
            destroyed = true;
            blockBody.setActive(false);
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public Image getImage() {
        return blockImage;
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
        return blockBody;
    }
}
