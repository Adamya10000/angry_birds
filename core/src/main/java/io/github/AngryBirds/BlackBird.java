package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BlackBird extends Bird{
    private Image black;
    private TextureRegion texture;

    public BlackBird(TextureRegion texture) {
        super(texture,BirdType.BLACK);
    }
}
