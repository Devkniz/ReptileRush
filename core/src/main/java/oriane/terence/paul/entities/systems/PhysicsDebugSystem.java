package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsDebugSystem extends EntitySystem {
 
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private OrthographicCamera camera;
 
    public PhysicsDebugSystem(World world, OrthographicCamera camera){
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true , true);
        this.world = world;
        this.camera = camera;
    }
 
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world, camera.combined);
        
    }
 
}