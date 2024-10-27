package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RedBird extends Bird{
    private static Texture bird = new Texture("redbird.png");

    private Image red;

    public RedBird() {
        this.red = new Image(bird);
        red.setSize((red.getWidth()*4)/14, (red.getHeight()*4)/14);
    }

    public Image getRed() {
        return red;
    }
}
