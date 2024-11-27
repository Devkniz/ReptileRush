package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable{
  public boolean isDead = false;
  @Override
  public void reset() {
    isDead = false;

  }

}
