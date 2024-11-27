package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import oriane.terence.paul.entities.components.RRBodyComponent;
import oriane.terence.paul.entities.components.TransformComponent;
import oriane.terence.paul.entities.components.TypeComponent;
import oriane.terence.paul.entities.components.PlayerComponent;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemyAI extends IteratingSystem {
    ComponentMapper<RRBodyComponent> rm;
    ComponentMapper<TransformComponent> tm;
    ComponentMapper<TypeComponent> ty;
    ComponentMapper<PlayerComponent> pm;

    private Entity player;

    public EnemyAI() {
        // Look for entities that are enemies and have a Transform and RRBody
        super(Family.all(RRBodyComponent.class, TransformComponent.class, TypeComponent.class).get());
        rm = ComponentMapper.getFor(RRBodyComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        ty = ComponentMapper.getFor(TypeComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Make sure we have the player entity
        if (ty.get(entity).type == TypeComponent.PLAYER) {
            this.player = entity;
        }

        // If there's no player, skip
        if (player == null) return;

        int typeC = ty.get(entity).type;
        if (typeC != TypeComponent.ENEMY) {
            return;
        }
        // Get the enemy's transform and body
        TransformComponent transform = tm.get(entity);
        RRBodyComponent rrbody = rm.get(entity);

        // Get the player's position
        TransformComponent playerTransform = tm.get(player); 
        Vector3 playerPos = playerTransform.position;

        // Get the enemy's position
        Vector3 enemyPos = transform.position;

        // Calculate the direction to the player
        Vector2 direction = new Vector2(playerPos.x - enemyPos.x, playerPos.y - enemyPos.y);

        // Normalize the direction (to make movement speed consistent)
        direction.nor();

        // Move the enemy toward the player by applying a velocity to the body
        if (rrbody != null && rrbody.body != null) {
            // Apply the velocity toward the player
            Body body = rrbody.body;
            float speed = 3.0f;  // Set the speed at which the enemy moves

            body.setLinearVelocity(direction.x * speed, direction.y * speed);
        } else {
            // If the enemy doesn't have a Body component, move directly via TransformComponent
            float speed = 3.0f;
            transform.position.add(new Vector3(direction.x * speed * deltaTime, direction.y * speed * deltaTime, 0));
        }
    }
}
