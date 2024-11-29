package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Glass extends Block {
    public Glass(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation, Box2DCollisionManager collisionManager) {
        super(texture, 2.5f, 15f, physicsManager, x, y, rotation, 5f, collisionManager);

    }
}
