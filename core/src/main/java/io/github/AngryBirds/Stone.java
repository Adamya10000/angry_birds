package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Stone extends Block {
    public Stone(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation, Box2DCollisionManager collisionManager) {
        super(texture, 10f, 30f, physicsManager, x, y, rotation, 15f, collisionManager);
    }
}
