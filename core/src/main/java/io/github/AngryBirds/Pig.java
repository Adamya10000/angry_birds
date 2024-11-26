package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pig implements GameObject {
    private Image pigImage;
    private float health;
    private boolean destroyed;
    private static final float MAX_HEALTH = 100f;
    private static final float MASS = 10f;

    public Pig(TextureRegion texture) {
        pigImage = new Image(texture);
        health = MAX_HEALTH;
        destroyed = false;
    }

    @Override
    public void takeDamage(float impactForce) {
        // Damage calculation based on impact force
        float damage = impactForce * 0.5f; // Adjust multiplier as needed
        health -= damage;

        if (health <= 0) {
            destroyed = true;
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
        return MASS;
    }

}
