package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Glass extends Block {
    public Glass(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation, Stage stage, Box2DCollisionManager collisionManager) {
        super(texture, 200f, 20f, physicsManager, x, y, rotation, 10f, stage, collisionManager);

    }
}
