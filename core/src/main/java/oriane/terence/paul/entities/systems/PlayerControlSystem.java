package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import oriane.terence.paul.controller.KeyboardController;
import oriane.terence.paul.entities.components.PlayerComponent;
import oriane.terence.paul.entities.components.RRBodyComponent;
import oriane.terence.paul.entities.components.StateComponent;
 
public class PlayerControlSystem extends IteratingSystem{
 
	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<RRBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	KeyboardController controller;
	float maxSpeed = 5f;
	
	public PlayerControlSystem(KeyboardController keyCon) {
		super(Family.all(PlayerComponent.class).get());
		controller = keyCon;
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(RRBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		RRBodyComponent rrbody = bodm.get(entity);
		
		Vector2 velocity = new Vector2(0, 0);
        if(controller.left){     
            velocity.x += -maxSpeed;
        }
		if(controller.right){
            velocity.x += maxSpeed;
        }
		if(controller.up){
            velocity.y += maxSpeed;
        }
		if(controller.down){
            velocity.y += -maxSpeed;
		}
		if (velocity.len() > 0) {
			velocity.nor().scl(maxSpeed);  // Normalize the velocity and scale by maxSpeed
		}
        rrbody.body.setLinearVelocity(velocity);

	}
}
