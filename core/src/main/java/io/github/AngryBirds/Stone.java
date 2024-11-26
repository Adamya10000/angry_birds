package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Stone extends Block {
    public Stone(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation) {
        super(texture, 150f, 15f, physicsManager, x, y, rotation);
        // Additional Wood-specific initialization
    }
}
