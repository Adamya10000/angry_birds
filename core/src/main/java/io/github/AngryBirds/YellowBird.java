package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class YellowBird extends Bird{
    private Image yellow;
    private TextureRegion texture;

    public YellowBird(TextureRegion texture) {
        this.texture = texture;
        this.yellow = new Image(texture);
        //yellow.setSize((yellow.getWidth()*4)/14, (yellow.getHeight()*4)/14);
    }

    public Image getYellow() {
        return yellow;
    }
}
