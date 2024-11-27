package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class RRBodyComponent implements Component, Pool.Poolable{
	public Body body;

  @Override
  public void reset() {
      // body = null;
  }

}
