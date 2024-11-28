package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RedBird extends Bird{
    private Image red;
    private TextureRegion texture;

    public RedBird(TextureRegion texture,PhysicsManager physicsManager, float x, float y) {
        super(texture, BirdType.RED, physicsManager, x, y);
    }
}
