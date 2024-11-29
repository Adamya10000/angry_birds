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

        float impactForce = calculateImpactForce(contact);

        handleCollision(fixtureA, fixtureB, impactForce);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private float calculateImpactForce(Contact contact) {

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        Vector2 velocityA = bodyA.getLinearVelocity();
        Vector2 velocityB = bodyB.getLinearVelocity();

        Vector2 relativeVelocity = new Vector2(velocityA).sub(velocityB);
        float velocityMagnitude = relativeVelocity.len();

        float massA = bodyA.getMass();
        float massB = bodyB.getMass();

        return 0.5f * (massA + massB) * velocityMagnitude * velocityMagnitude;
    }

    private void handleCollision(Fixture fixtureA, Fixture fixtureB, float impactForce) {
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        if (isGroundCollision(bodyA, bodyB)) {
            handleGroundCollision(bodyA, bodyB, impactForce);
            return;
        }

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
        return (bodyA.getType() == BodyDef.BodyType.StaticBody && bodyA.getUserData() == "ground") ||
            (bodyB.getType() == BodyDef.BodyType.StaticBody && bodyB.getUserData() == "ground");
    }

    private void handleGroundCollision(Body impactBody, Body groundBody, float impactForce) {
        GameObject impactObject = findGameObject(impactBody);

        if (impactObject != null) {
            float groundImpactDamage = calculateGroundImpactDamage(impactForce);
            impactObject.takeDamage(groundImpactDamage, impactObject);

        }
    }

    private float calculateGroundImpactDamage(float impactForce) {
        float baseDamage = 20f;
        float damageMultiplier = 0.1f;

        return Math.max(0, baseDamage + (impactForce * damageMultiplier));
    }


    private void handleBirdPigCollision(GameObject obj1, GameObject obj2, float impactForce) {
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
