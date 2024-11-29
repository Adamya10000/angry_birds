package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class YellowBird extends Bird{
    private Image yellow;
    private TextureRegion texture;

    public YellowBird(TextureRegion texture, PhysicsManager physicsManager, float x, float y, Stage stage, Box2DCollisionManager collisionManager) {
        super(texture, BirdType.YELLOW,physicsManager,x,y, stage, collisionManager);
    }
}
