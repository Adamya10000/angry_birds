package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pig {
    private TextureRegion texture;
    private Vector2 size;

    private Image pig;

    public Pig(TextureRegion texture) {
        this.texture = texture;
        this.pig = new Image(texture);
    }

    public Image getPig() {
        return pig;
    }
}
