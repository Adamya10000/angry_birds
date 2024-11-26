// LevelData.java
package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class LevelData {
    private ArrayList<GameObj> objects;
    private int levelNumber;
    private int requiredScore;
    private int availableBirds;

    public static class GameObj {
        public enum Type {
            WOOD, STONE, GLASS, PIG, REDBIRD, YELLOWBIRD, BLACKBIRD
        }

        private Type type;
        private float x;
        private float y;
        private float rotation;
        private TextureRegion texture;

        public GameObj(Type type, float x, float y, float rotation, TextureRegion texture) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.texture = texture;
        }

        // Getters
        public Type getType() { return type; }
        public float getX() { return x; }
        public float getY() { return y; }
        public float getRotation() { return rotation; }
        public TextureRegion getTexture() { return texture; }
    }

    public LevelData(int levelNumber, ArrayList<GameObj> objects, int requiredScore, int availableBirds) {
        this.levelNumber = levelNumber;
        this.objects = objects;
        this.requiredScore = requiredScore;
        this.availableBirds = availableBirds;
    }

    public ArrayList<GameObj> getObjects() { return objects; }
    public int getLevelNumber() { return levelNumber; }
    public int getRequiredScore() { return requiredScore; }
    public int getAvailableBirds() { return availableBirds; }
}
