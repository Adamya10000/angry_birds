package io.github.AngryBirds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.ArrayList;
import java.util.List;

public class Box2DCollisionManager implements ContactListener {
    private World world;
    private List<GameObject> gameObjects;

    public Box2DCollisionManager(World world) {
        this.world = world;
        this.gameObjects = new ArrayList<>();

        // Set this as the contact listener for the world
        world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Calculate impact force
        float impactForce = calculateImpactForce(contact);

        // Handle collision between objects
        handleCollision(fixtureA, fixtureB, impactForce);
    }

    @Override
    public void endContact(Contact contact) {
        // Optional: Add end contact logic if needed
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optional: Pre-solve collision handling
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optional: Post-solve collision handling
    }

    private float calculateImpactForce(Contact contact) {
        WorldManifold worldManifold = contact.getWorldManifold();

        // Get bodies involved in the collision
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        // Calculate relative velocity
        Vector2 velocityA = bodyA.getLinearVelocity();
        Vector2 velocityB = bodyB.getLinearVelocity();

        // Calculate velocity difference
        Vector2 relativeVelocity = new Vector2(velocityA).sub(velocityB);
        float velocityMagnitude = relativeVelocity.len();

        // Combine masses
        float massA = bodyA.getMass();
        float massB = bodyB.getMass();

        // More sophisticated impact force calculation
        // Use kinetic energy: 0.5 * m * v^2
        float impactForce = 0.5f * (massA + massB) * velocityMagnitude * velocityMagnitude;

        return impactForce;
    }

    private void handleCollision(Fixture fixtureA, Fixture fixtureB, float impactForce) {
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        // Attempt to find corresponding game objects
        GameObject objA = findGameObject(bodyA);
        GameObject objB = findGameObject(bodyB);

        // Apply damage to objects
        if (objA != null) {
            objA.takeDamage(impactForce);
        }
        if (objB != null) {
            objB.takeDamage(impactForce);
        }

        // Special collision handling
        if ((objA instanceof Bird && objB instanceof Pig) ||
                (objA instanceof Pig && objB instanceof Bird)) {
            handleBirdPigCollision(objA, objB, impactForce);
        }
    }

    private void handleBirdPigCollision(GameObject obj1, GameObject obj2, float impactForce) {
        // Determine which is the bird and which is the pig
        Bird bird = (obj1 instanceof Bird) ? (Bird)obj1 : (Bird)obj2;
        Pig pig = (obj1 instanceof Pig) ? (Pig)obj1 : (Pig)obj2;

        // More nuanced damage calculation
        float damageFactor = Math.max(0, Math.min(impactForce / 100f, 1f));
        pig.takeDamage(damageFactor * 100);  // Scale damage between 0-100
    }

    private GameObject findGameObject(Body body) {
        // Search through game objects to find matching body
        for (GameObject obj : gameObjects) {
            if (obj instanceof Bird && ((Bird)obj).getBody() == body) {
                return obj;
            }
            // Add similar checks for other game object types
            // This might need to be expanded based on your specific implementation
        }
        return null;
    }


    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void clearGameObjects() {
        gameObjects.clear();
    }
}
