package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.swing.*;

public class RedBird extends Bird{
    private Image red;
    private TextureRegion texture;

    public RedBird(TextureRegion texture, PhysicsManager physicsManager, float x, float y, Stage stage, Box2DCollisionManager collisionManager) {
        super(texture, BirdType.RED, physicsManager, x, y, stage, collisionManager);
    }
}
