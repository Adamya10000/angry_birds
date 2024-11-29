package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Wood extends Block {
    public Wood(TextureRegion texture, PhysicsManager physicsManager, float x, float y, float rotation, Stage stage, Box2DCollisionManager collisionManager) {
        super(texture, 100f, 10f, physicsManager, x, y,rotation, 10f, stage, collisionManager);
    }
}
