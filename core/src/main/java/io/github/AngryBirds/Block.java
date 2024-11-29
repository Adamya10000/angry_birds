package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Block implements GameObject {
    protected Body blockBody;
    protected Image blockImage;
    protected float health;
    protected float damage;
    protected boolean destroyed;
    protected float mass;
    protected PhysicsManager physicsManager;
    protected Box2DCollisionManager collisionManager;
    protected boolean markedForRemoval = false;

    public Block(TextureRegion texture, float maxHealth, float mass, PhysicsManager physicsManager, float x, float y, float rotation, float damage, Box2DCollisionManager collisionManager) {
        this.blockImage = new Image(texture);

        this.blockBody = physicsManager.createBlockBody(x , y , blockImage.getWidth(), blockImage.getHeight(), rotation);

        this.blockImage.setRotation(rotation);

        this.health = maxHealth;
        this.damage = damage;
        this.mass = mass;
        this.destroyed = false;
        this.physicsManager = physicsManager;
        this.collisionManager =  collisionManager;
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        // Significantly more aggressive damage calculation
        float damageMultiplier = 1.0f;

        // Hyper-aggressive damage from birds
        if (obj instanceof Bird) {
            Bird bird = (Bird) obj;
            damageMultiplier = bird.calculateDamageToTarget(this, impactForce);
        }

        // Apply massive damage
        float effectiveDamage = impactForce * damageMultiplier;
        health -= effectiveDamage;

        // Ensure block is removed with much lower health threshold
        if (health <= 0.1f) {
            removeFromGame(obj);
        }
    }

    @Override
    public void removeFromGame(GameObject obj) {
        if (destroyed) return;
        collisionManager.removeFromGameObject(obj);
        destroyed = true;
        markedForRemoval = true;


        collisionManager.removeFromGameObject(obj);
    }

    @Override
    public void safeRemoveFromPhysicsWorld() {
        if(!markedForRemoval) return;

        if(blockBody != null){
            blockBody.setActive(false);
        }

        blockImage.remove();
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

    public float getDamage() {
        return damage;
    }
}
