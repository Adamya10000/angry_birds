package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Block {
    private TextureRegion texture;
    private Vector2 size;

    private Image block;

    public Block(TextureRegion texture) {
        this.texture = texture;
        this.block = new Image(texture);
    }

    public Image getBlock() {
        return block;
    }
}
