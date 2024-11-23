package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BlackBird extends Bird{
    private Image black;
    private TextureRegion texture;

    public BlackBird(TextureRegion texture) {
        this.texture = texture;
        this.black = new Image(texture);
        //yellow.setSize((yellow.getWidth()*4)/14, (yellow.getHeight()*4)/14);
    }

    public Image getBlack() {
        return black;
    }
}
