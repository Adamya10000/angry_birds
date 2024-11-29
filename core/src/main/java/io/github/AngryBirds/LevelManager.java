package io.github.AngryBirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.reflect.Array;
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

    private void level1() {
        ArrayList<LevelData.GameObj> level1Objects = new ArrayList<>();

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 2000, 196, 0, blockTextures[4]));

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 2000, 360, 0, blockTextures[5]
        ));

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.GLASS, 2000, 520, 0, blockTextures[6]
        ));

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG2, 2000, 680, 0, pigTextures[1]
        ));


        levels.put(1, new LevelData(1, level1Objects, 1000, 3));

    }

    private void level2() {
        ArrayList<LevelData.GameObj> level2Objects = new ArrayList<>();

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1400, 280, 0, blockTextures[4]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1732, 280, 0, blockTextures[4]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 2060, 280, 0, blockTextures[4]));

        // Medium Stone Blocks
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1400, 450, 0, blockTextures[2]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1732, 450, 0, blockTextures[2]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 2060, 450, 0, blockTextures[2]));

        // Pigs
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG1, 1400, 500, 0, pigTextures[0]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG2, 1730, 500, 0, pigTextures[1]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG1, 2060, 500, 0, pigTextures[0]));

        // Birds
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.REDBIRD, 170, 360, 0, birdTextures[0]));

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.YELLOWBIRD, 100, 200, 0, birdTextures[1]));

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.BLACKBIRD, 30, 200, 0, birdTextures[2]));


        levels.put(2, new LevelData(2, level2Objects, 1000, 3));
    }

    private void level3() {
        ArrayList<LevelData.GameObj> level3Objects = new ArrayList<>();

        // Long Wood Blocks
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1511, 430, 0, blockTextures[0]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1715, 430, 0, blockTextures[0]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1604, 535, 0, blockTextures[0]));

        // Medium glass Blocks
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.GLASS, 1436, 360, 90, blockTextures[6]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1612, 370, 90, blockTextures[4]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.GLASS, 1790, 360, 90, blockTextures[6]));


        // Small Stone Blocks
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1530, 490, 0, blockTextures[3]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1690, 490, 0, blockTextures[3]));

        // Pigs
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG3, 1612, 700, 0, pigTextures[2]));

        // Birds
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.REDBIRD, 170, 360, 0, birdTextures[0]));

        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.YELLOWBIRD, 100, 200, 0, birdTextures[1]));

        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.BLACKBIRD, 30, 200, 0, birdTextures[2]));


        levels.put(3, new LevelData(3, level3Objects, 1000, 3));
    }
    private void initializeLevels() {
        level1();
        level2();
        level3();
    }

    public LevelData getLevel(int levelNumber) {
        return levels.get(levelNumber);
    }
}
