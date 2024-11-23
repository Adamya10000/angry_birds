package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RedBird extends Bird{
    private Image red;
    private TextureRegion texture;

    public RedBird(TextureRegion texture) {
        this.texture = texture;
        this.red = new Image(texture);
        //red.setSize((red.getWidth()*4)/14, (red.getHeight()*4)/14);
    }

    public Image getRed() {
        return red;
    }
}
