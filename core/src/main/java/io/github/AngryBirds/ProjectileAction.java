package io.github.AngryBirds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class ProjectileAction extends Action {
    private float velocityX, velocityY;
    private float gravity;
    private float time;
    private boolean finished;
    private Vector2 initialPosition;

    public ProjectileAction(float velocityX, float velocityY, float gravity) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.gravity = gravity;
        this.time = 0;
        this.finished = false;
    }

    @Override
    public boolean act(float delta) {
        if (finished) return true;

        time += delta;

        // Calculate new position based on time, velocity, and gravity
        float dx = velocityX * time;
        float dy = velocityY * time - 0.5f * gravity * time * time;

        if (initialPosition == null) {
            initialPosition = new Vector2(actor.getX(), actor.getY());
        }

        float newX = initialPosition.x + dx;
        float newY = initialPosition.y + dy;

        // Set the new position of the actor
        actor.setPosition(newX, newY);

        // Check if the actor has hit the ground or left the screen
        if (newY <= 0 || newX < 0 || newX > 2560) {
            finished = true;
            return true; // End the action
        }

        return false; // Continue the action
    }
}
