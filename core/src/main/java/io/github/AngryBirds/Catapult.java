package io.github.AngryBirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Catapult{
    private static Texture firstCatapult = new Texture("catapult_part1.png");
    private static Texture secondCatapult = new Texture("catapult_part2.png");
    private static Texture catapult = new Texture("catapult.png");

    private static Image catapult1;
    private static Image catapult2;

    public Catapult() {
        this.catapult1 = new Image(firstCatapult);
        catapult1.setSize((catapult1.getWidth()*3)/4, (catapult1.getHeight()*3)/4);
        this.catapult2 = new Image(secondCatapult);
        catapult2.setSize((catapult2.getWidth()*2)/3, (catapult2.getHeight()*2)/3);

    }


    //getter
    public Image getCatapult1() {
        return catapult1;
    }
    public Image getCatapult2() {
        return catapult2;
    }
}

