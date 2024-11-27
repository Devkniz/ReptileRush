package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import oriane.terence.paul.entities.components.HealthComponent;
import oriane.terence.paul.entities.components.PlayerComponent;
import oriane.terence.paul.views.MainScreen;
import oriane.terence.paul.views.MainScreen.GameState;


public class PlayerStateSystem extends IteratingSystem {

  private ComponentMapper<PlayerComponent> stateM;
  private ComponentMapper<HealthComponent> healthM;
  // private ComponentMapper<TypeComponent> typeM;
  private MainScreen ms;

  public PlayerStateSystem(MainScreen ms) {
    super(Family.all(PlayerComponent.class, HealthComponent.class).get());
    this.ms = ms;
    stateM = ComponentMapper.getFor(PlayerComponent.class);
    healthM = ComponentMapper.getFor(HealthComponent.class);
    // typeM = ComponentMapper.getFor(TypeComponent.class);

  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    PlayerComponent stateC = stateM.get(entity);
    HealthComponent healthC = healthM.get(entity);
    // TypeComponent typeC = typeM.get(entity);

    if (healthC.currHealth <= 0) {
      stateC.isDead = true;
      System.out.println("player is dead");
    }
    if (stateC.isDead){
      // game.is
      ms.setState(GameState.FINISHED);
    }
    
  }

}
