package io.github.AngryBirds;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private Stage stage;
    private List<GameObject> gameObjects;
    private List<Bird> birds;

    public CollisionManager(Stage stage) {
        this.stage = stage;
        this.gameObjects = new ArrayList<>();
        this.birds = new ArrayList<>();
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public void checkCollisions() {
        // Check bird collisions with other game objects
        for (Bird bird : birds) {
            Image birdImage = bird.getImage();

            // Skip if bird is destroyed
            if (bird.isDestroyed()) continue;

            for (GameObject obj : gameObjects) {
                Image objImage = obj.getImage();

                // Skip if object is already destroyed
                if (obj.isDestroyed()) continue;

                // Check for collision using bounding rectangles
                if (checkRectangleCollision(birdImage, objImage)) {
                    // Apply bird-specific damage
                    float birdDamage = bird.getDamage();
                    obj.takeDamage(birdDamage);
                    bird.takeDamage(10f); // Bird takes some damage on impact
                }
            }
        }

        // Remove destroyed objects
        gameObjects.removeIf(GameObject::isDestroyed);
        birds.removeIf(Bird::isDestroyed);
    }

    private boolean checkRectangleCollision(Image image1, Image image2) {
        Rectangle rect1 = new Rectangle(
            image1.getX(),
            image1.getY(),
            image1.getWidth(),
            image1.getHeight()
        );
        Rectangle rect2 = new Rectangle(
            image2.getX(),
            image2.getY(),
            image2.getWidth(),
            image2.getHeight()
        );
        return Intersector.overlaps(rect1, rect2);
    }
}
