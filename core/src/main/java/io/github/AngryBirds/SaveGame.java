package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;
import java.util.ArrayList;


public class SaveGame {
    public int currentLevel;
    public List<BirdState> birds;
    public List<BlockState> blocks;
    public List<PigState> pigs;

    public static class BirdState {
        public float x, y;
        public boolean isLaunched;
    }

    public static class BlockState {
        public float x, y, rotation;
        public String type;
    }

    public static class PigState {
        public float x, y;
        public boolean isDestroyed;
    }


}
