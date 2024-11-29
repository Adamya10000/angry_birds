package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Block implements GameObject {
    protected Body blockBody;
    protected Image blockImage;
    protected float health;
    protected float damage;
    protected boolean destroyed;
    protected float mass;
    protected PhysicsManager physicsManager;
    protected Stage stage;
    protected Box2DCollisionManager collisionManager;
    protected boolean markedForRemoval = false;

    public Block(TextureRegion texture, float maxHealth, float mass, PhysicsManager physicsManager, float x, float y, float rotation, float damage, Stage stage, Box2DCollisionManager collisionManager) {
        this.blockImage = new Image(texture);

        // Create Box2D body first
        this.blockBody = physicsManager.createBlockBody(x , y , blockImage.getWidth(), blockImage.getHeight(), rotation);

        // Set image rotation to match body rotation
        this.blockImage.setRotation(rotation);

        this.health = 1f;
        this.damage = damage;
        this.mass = mass;
        this.destroyed = false;
        this.physicsManager = physicsManager;
        this.stage = stage;
        this.collisionManager =  collisionManager;
    }

    @Override
    public void takeDamage(float impactForce, GameObject obj) {
        float damage = impactForce * 0.3f;
        health -= damage;
        System.out.println("Remaining health of BLOCK: " + health);

        if (health <= 0) {
            removeFromGame(obj);
        }
    }

    @Override
    public void removeFromGame(GameObject obj) {
        if (destroyed) return;
        collisionManager.removeFromGameObject(obj);
        destroyed = true;
        markedForRemoval = true; // Mark for safe removal


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
