package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

abstract class Block implements GameObject {
    protected Image blockImage;
    protected float health;
    protected boolean destroyed;
    protected float mass;

    public Block(TextureRegion texture, float maxHealth, float mass) {
        blockImage = new Image(texture);
        this.health = maxHealth;
        this.mass = mass;
        this.destroyed = false;
    }

    @Override
    public void takeDamage(float impactForce) {
        float damage = impactForce * 0.3f; // Adjust multiplier based on block type
        health -= damage;

        if (health <= 0) {
            destroyed = true;
            blockImage.remove(); // Remove from stage
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
}
