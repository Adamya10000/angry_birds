package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelManager {
    private Map<Integer, LevelData> levels;
    private TextureRegion[] blockTextures;
    private TextureRegion[] pigTextures;
    private TextureRegion[] birdTextures;


    public LevelManager(TextureRegion[] blockTextures, TextureRegion[] pigTextures, TextureRegion[] birdTextures) {
        this.levels = new HashMap<>();
        this.blockTextures = blockTextures;
        this.pigTextures = pigTextures;
        this.birdTextures = birdTextures;
        initializeLevels();
    }

    private void initializeLevels() {
        // Level 1
        List<LevelData.GameObject> level1Objects = new ArrayList<>();

        // Long Wood Blocks
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1400, 277, 0, blockTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1808, 277, 0, blockTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1400, 535, 0, blockTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1808, 535, 0, blockTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1604, 793, 0, blockTextures[0]));

        // Medium Wood Blocks
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1436, 313, 90, blockTextures[1]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1826, 313, 90, blockTextures[1]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 2216, 313, 90, blockTextures[1]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1678, 571, 90, blockTextures[1]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.WOOD, 1974, 571, 90, blockTextures[1]));

        // Medium Stone Blocks
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.STONE, 2048, 196, 0, blockTextures[2]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.STONE, 1724, 196, 0, blockTextures[2]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.STONE, 1400, 196, 0, blockTextures[2]));

        // Small Stone Blocks
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.STONE, 1400, 571, 0, blockTextures[3]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.STONE, 2132, 571, 0, blockTextures[3]));

        // Pigs
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.PIG, 1554, 313, 0, pigTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.PIG, 1962, 313, 0, pigTextures[0]));
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.PIG, 1758, 829, 0, pigTextures[1]));

        // Birds
        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.REDBIRD, 170, 360, 0, birdTextures[0]));

        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.YELLOWBIRD, 100, 200, 0, birdTextures[1]));

        level1Objects.add(new LevelData.GameObject(
            LevelData.GameObject.Type.BLACKBIRD, 30, 200, 0, birdTextures[2]));


        levels.put(1, new LevelData(1, level1Objects, 1000, 3));
    }

    public LevelData getLevel(int levelNumber) {
        return levels.get(levelNumber);
    }
}
