package io.github.AngryBirds;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public interface GameObject {
    void takeDamage(float impactForce, GameObject obj);
    boolean isDestroyed();
    Image getImage();
    float getHealth();
    float getMass();
    Body getBody();

    void removeFromGame(GameObject obj);

    void safeRemoveFromPhysicsWorld();
}
