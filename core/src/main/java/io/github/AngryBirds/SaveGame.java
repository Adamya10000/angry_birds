package io.github.AngryBirds;

import java.io.Serializable;
import java.util.List;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L;

    public int currentLevel;
    public List<BirdState> birds;
    public List<BlockState> blocks;
    public List<PigState> pigs;

    // Inner classes for Bird, Block, and Pig states
    public static class BirdState implements Serializable {
        private static final long serialVersionUID = 1L;
        public float x, y;
        public String type;
        public boolean isLaunched;
    }

    public static class BlockState implements Serializable {
        private static final long serialVersionUID = 1L;
        public float x, y, rotation;
        public String type;
    }

    public static class PigState implements Serializable {
        private static final long serialVersionUID = 1L;
        public float x, y;
        public boolean isDestroyed;
        public String type;
    }
}
