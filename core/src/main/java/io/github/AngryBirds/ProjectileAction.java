package io.github.AngryBirds;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ProjectileAction extends Action {
    private float velocityX;
    private float velocityY;
    private float gravity = -5f; // Adjusted gravity for slower descent
    private float time = 0f;

    public ProjectileAction(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public boolean act(float delta) {
        time += delta;

        // Adjust the scaling factor to slow down the movement
        float newX = actor.getX() + velocityX * delta * 15;  // Slower horizontal motion
        float newY = actor.getY() + velocityY * delta * 15 + 0.5f * gravity * (delta * 15) * (delta * 15); // Slower vertical motion

        // Update vertical velocity due to gravity
        velocityY += gravity * delta * 15;

        actor.setPosition(newX, newY);

        // Stop action if the actor goes below the ground level (0 Y position)
        if (newY <= 0) {
            actor.setPosition(newX, 0);
            return true; // Action is finished
        }
        return false; // Continue the action
    }
}
