package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Catapult extends Image {
    public Catapult(Texture texture, float width, float height) {
        super(texture);
        setSize(width, height);
    }
}

