package io.github.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

    public void create() {
        setScreen(new MainMenu(this));
    }

}
