package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable{
    public Vector3 position = new Vector3();
    public Vector3 prevPosition = new Vector3();
    public Vector2 scale = new Vector2(0.25f, 0.25f);
    public float rotation = 0.0f;
    public boolean isHidden = false;

    @Override
    public void reset() {
      position = new Vector3();
      prevPosition = new Vector3();
      scale = new Vector2(0.25f, 0.25f);
      rotation = 0.0f;
      isHidden = false;
    }
}
