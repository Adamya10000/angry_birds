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

    private void level2() {

        // Level 1
        ArrayList<LevelData.GameObj> level2Objects = new ArrayList<>();

//        // Long Wood Blocks
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1400, 200, 0, blockTextures[0]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1808, 200, 0, blockTextures[0]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1400, 500, 0, blockTextures[0]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1808, 500, 0, blockTextures[0]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1604, 750, 0, blockTextures[0]));

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1400, 280, 0, blockTextures[4]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 1732, 280, 0, blockTextures[4]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 2060, 280, 0, blockTextures[4]));
//        // Medium Wood Blocks
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1436, 250, 90, blockTextures[1]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1826, 250, 90, blockTextures[1]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 2216, 250, 90, blockTextures[1]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1678, 550, 90, blockTextures[1]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1974, 550, 90, blockTextures[1]));
//
//        // Medium Stone Blocks
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.STONE, 2048, 250, 0, blockTextures[2]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.STONE, 1724, 250, 0, blockTextures[2]));
//        level1Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.STONE, 1400, 250, 0, blockTextures[2]));
//
        // Small Stone Blocks
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1400, 500, 0, blockTextures[2]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1732, 500, 0, blockTextures[2]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 2060, 500, 0, blockTextures[2]));

        // Pigs
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 1400, 600, 0, pigTextures[0]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 1730, 600, 0, pigTextures[0]));
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 2060, 600, 0, pigTextures[0]));

        // Birds
        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.REDBIRD, 170, 360, 0, birdTextures[0]));

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.YELLOWBIRD, 100, 200, 0, birdTextures[1]));

        level2Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.BLACKBIRD, 30, 200, 0, birdTextures[2]));


        levels.put(2, new LevelData(2, level2Objects, 1000, 3));
    }

    private void level1() {
        ArrayList<LevelData.GameObj> level1Objects = new ArrayList<>();

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.WOOD, 2048, 196, 0, blockTextures[4]));

        level1Objects.add(new LevelData.GameObj(
           LevelData.GameObj.Type.STONE, 2048, 360, 0, blockTextures[5]
        ));

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.GLASS, 2048, 520, 0, blockTextures[6]
        ));

        level1Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 2048, 680, 0, pigTextures[1]
        ));


        levels.put(1, new LevelData(1, level1Objects, 1000, 3));

    }

    private void level3() {
        ArrayList<LevelData.GameObj> level3Objects = new ArrayList<>();

//        // Long Wood Blocks
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1400, 277, 0, blockTextures[0]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1808, 277, 0, blockTextures[0]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1400, 535, 0, blockTextures[0]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1808, 535, 0, blockTextures[0]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1604, 793, 0, blockTextures[0]));

//        // Medium Wood Blocks
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1436, 313, 90, blockTextures[1]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1826, 313, 90, blockTextures[1]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 2216, 313, 90, blockTextures[1]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1678, 571, 90, blockTextures[1]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.WOOD, 1974, 571, 90, blockTextures[1]));

        // Medium Stone Blocks
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 2048, 216, 0, blockTextures[2]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1724, 216, 0, blockTextures[2]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.STONE, 1400, 216, 0, blockTextures[2]));

//        // Small Stone Blocks
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.STONE, 1400, 571, 0, blockTextures[3]));
//        level3Objects.add(new LevelData.GameObj(
//            LevelData.GameObj.Type.STONE, 2132, 571, 0, blockTextures[3]));

        // Pigs
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 1554, 313, 0, pigTextures[0]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 1962, 313, 0, pigTextures[0]));
        level3Objects.add(new LevelData.GameObj(
            LevelData.GameObj.Type.PIG, 1758, 829, 0, pigTextures[1]));

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
