package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RedBird extends Image{
    public RedBird(Texture texture, float width, float height) {
        super(texture);
        setSize(width, height);
    }
}
