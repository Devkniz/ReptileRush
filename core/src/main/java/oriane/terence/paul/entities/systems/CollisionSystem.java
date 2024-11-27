package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import oriane.terence.paul.entities.components.CollisionComponent;
import oriane.terence.paul.entities.components.HealthComponent;
import oriane.terence.paul.entities.components.PlayerComponent;
import oriane.terence.paul.entities.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
	ComponentMapper<CollisionComponent> cm;
	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<HealthComponent> hm;

	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

		cm = ComponentMapper.getFor(CollisionComponent.class);
		pm = ComponentMapper.getFor(PlayerComponent.class);
		hm = ComponentMapper.getFor(HealthComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = cm.get(entity);

		Entity collidedEntity = cc.collisionEntity;
		if (collidedEntity != null) {
			TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
			HealthComponent healthC = hm.get(entity);
			if (type != null) {
				switch (type.type) {
					case TypeComponent.ENEMY:
						// do player hit enemy thing
						healthC.currHealth = healthC.currHealth - (Integer) (1) <= 0 ? 0 : healthC.currHealth - 1;
						System.out.println(healthC.currHealth);
						System.out.println("player hit enemy");
						break;
					case TypeComponent.SCENERY:
						// do player hit scenery thing
						System.out.println("player hit scenery");
						break;
					case TypeComponent.OTHER:
						// do player hit other thing
						System.out.println("player hit other");
						break; // technically this isn't needed
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}

	}

}
