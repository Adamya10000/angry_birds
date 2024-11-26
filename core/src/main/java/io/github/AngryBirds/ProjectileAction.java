package io.github.AngryBirds;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ProjectileAction extends Action {
    private float velocityX;
    private float velocityY;
    private float groundLevel;
    private float gravity = -5f; // Adjusted gravity for slower descent
    private float time = 0f;

    public ProjectileAction(float velocityX, float velocityY, float groundLevel) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.groundLevel = groundLevel;
    }

    @Override
    public boolean act(float delta) {
        time += delta;

        float newX = actor.getX() + velocityX * delta * 15;
        float newY = actor.getY() + velocityY * delta * 15 + 0.5f * gravity * (delta * 15) * (delta * 15);

        velocityY += gravity * delta * 15;

        actor.setPosition(newX, newY);

        // Stop action if the actor goes below the ground level
        if (newY <= groundLevel) {
            actor.setPosition(newX, groundLevel);
            return true; // Action is finished
        }
        return false; // Continue the action
    }
}
