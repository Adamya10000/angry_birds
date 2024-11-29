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

        //System.out.println(impactForce);

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

        // Check for ground collision
        if (isGroundCollision(bodyA, bodyB)) {
            handleGroundCollision(bodyA, bodyB, impactForce);
            return;
        }

        // Attempt to find corresponding game objects
        GameObject objA = findGameObject(bodyA);
        GameObject objB = findGameObject(bodyB);

        // Apply damage to objects
        if (objA != null) {
            objA.takeDamage(impactForce, objA);
        }
        if (objB != null) {
            objB.takeDamage(impactForce, objB);
        }

        // Special collision handling
        if ((objA instanceof Bird && objB instanceof Pig) ||
            (objA instanceof Pig && objB instanceof Bird)) {
            handleBirdPigCollision(objA, objB, impactForce);
        }
    }

    private boolean isGroundCollision(Body bodyA, Body bodyB) {
        // Assuming the ground body is a static body created in PhysicsManager
        // You might need to adjust this based on how your ground is specifically implemented
        return (bodyA.getType() == BodyDef.BodyType.StaticBody && bodyA.getUserData() == "ground") ||
            (bodyB.getType() == BodyDef.BodyType.StaticBody && bodyB.getUserData() == "ground");
    }

    private void handleGroundCollision(Body impactBody, Body groundBody, float impactForce) {
        GameObject impactObject = findGameObject(impactBody);

        if (impactObject != null) {
            // Calculate ground impact damage
            float groundImpactDamage = calculateGroundImpactDamage(impactForce);

            // Apply damage to the object
            impactObject.takeDamage(groundImpactDamage, impactObject);

            // Optional: Add logging or additional ground impact logic
            System.out.println("Object impacted ground with force: " + impactForce +
                ", Damage dealt: " + groundImpactDamage);
        }
    }

    private float calculateGroundImpactDamage(float impactForce) {
        // Implement a damage calculation based on impact force
        // You can adjust this formula as needed
        float baseDamage = 20f; // Base damage for ground impact
        float damageMultiplier = 0.1f; // Scaling factor for impact force

        // Calculate damage, ensuring it doesn't go below 0
        return Math.max(0, baseDamage + (impactForce * damageMultiplier));
    }


    private void handleBirdPigCollision(GameObject obj1, GameObject obj2, float impactForce) {
        // Determine which is the bird and which is the pig
        System.out.println("Entered Pig BIrd Collision");
        GameObject bird = (obj1 instanceof Bird) ? obj1 : obj2;
        GameObject pig = (obj1 instanceof Pig) ? obj1 : obj2;

        // More nuanced damage calculation
        float damageFactor = Math.max(0, Math.min(impactForce / 100f, 1f));
        pig.takeDamage(damageFactor * 100, pig);
        bird.takeDamage(damageFactor * 100, bird);// Scale damage between 0-100
    }

    private GameObject findGameObject(Body body) {
        for (GameObject obj : gameObjects) {
            if (obj.getBody() == body) {
                return obj;
            }
        }
        return null;
    }


    public void removeFromGameObject (GameObject gameObject){
        gameObjects.remove(gameObject);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void clearGameObjects() {
        gameObjects.clear();
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
