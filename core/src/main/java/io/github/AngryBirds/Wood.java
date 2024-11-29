package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wood extends Block {
    public Wood(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation, Box2DCollisionManager collisionManager) {
        super(texture, 5f, 22.5f, physicsManager, x, y,rotation, 10f, collisionManager);
    }
}
