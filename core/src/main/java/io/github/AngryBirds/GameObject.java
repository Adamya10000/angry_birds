package io.github.AngryBirds;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public interface GameObject {
    void takeDamage(float impactForce);
    boolean isDestroyed();
    Image getImage();
    float getHealth();
    float getMass();
    Body getBody();
}
