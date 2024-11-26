package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wood extends Block {
    public Wood(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation) {
        super(texture, 100f, 10f, physicsManager, x, y,rotation);
    }
}
