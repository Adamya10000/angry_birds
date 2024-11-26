package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird implements GameObject {
    private Image birdImage;
    private float health;
    private boolean destroyed;
    private float damage;
    private static final float MAX_HEALTH = 50f;

    public enum BirdType {
        RED(20f),     // Base damage
        YELLOW(75f),  // Higher damage
        BLACK(100f);  // Highest damage

        private final float damageMultiplier;

        BirdType(float damage) {
            this.damageMultiplier = damage;
        }

        public float getDamageMultiplier() {
            return damageMultiplier;
        }
    }

    private BirdType type;

    public Bird(TextureRegion texture, BirdType type) {
        this.birdImage = new Image(texture);
        this.type = type;
        this.health = MAX_HEALTH;
        this.damage = type.getDamageMultiplier();
        this.destroyed = false;
    }

    @Override
    public void takeDamage(float impactForce) {
        float damage = impactForce * 0.2f;
        health -= damage;

        if (health <= 0) {
            destroyed = true;
            birdImage.remove();
        }
    }

    public float getDamage() {
        return damage;
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

    public BirdType getType() {
        return type;
    }
}
