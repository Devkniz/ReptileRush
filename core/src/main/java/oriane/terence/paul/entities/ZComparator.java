package oriane.terence.paul.entities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import java.util.Comparator;
import oriane.terence.paul.entities.components.TransformComponent;
 
 
public class ZComparator implements Comparator<Entity> {
    private ComponentMapper<TransformComponent> cmTrans;
 
    public ZComparator(){
        cmTrans= ComponentMapper.getFor(TransformComponent.class);
    }
 
    @Override
    public int compare(Entity entityA, Entity entityB) {
        if (entityA == null || entityB == null) {
            System.out.println("an entity is null");
            return 0;
        }

        TransformComponent tA = cmTrans.get(entityA);
        TransformComponent tB = cmTrans.get(entityB);

        if (tA == null || tB == null) {
            System.out.println("a transform component is null");
            return 0;
        }

        // Compare Z values
        return Float.compare(tA.position.z, tB.position.z);
    }
}
