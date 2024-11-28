package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Glass extends Block {
    public Glass(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation) {
        super(texture, 200f, 20f, physicsManager, x, y, rotation);

    }
}
