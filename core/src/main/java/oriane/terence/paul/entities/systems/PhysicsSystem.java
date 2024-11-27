package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import oriane.terence.paul.entities.components.RRBodyComponent;
import oriane.terence.paul.entities.components.TransformComponent;

public class PhysicsSystem extends IntervalIteratingSystem {
    public static final float MAX_STEP_TIME = 1 / 60f; // 60fps physics step
    public static float accumulator = 0f;

    private World world;
    private Array<Entity> bodiesQueue;
    private ComponentMapper<RRBodyComponent> bm = ComponentMapper.getFor(RRBodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public PhysicsSystem(World world) {
        super(Family.all(RRBodyComponent.class, TransformComponent.class).get(), MAX_STEP_TIME);
        this.world = world;
        this.bodiesQueue = new Array<Entity>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);  // Avoid too much time passed
        accumulator += frameTime;

        if (accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            for (Entity entity : bodiesQueue) {
                TransformComponent tfm = tm.get(entity);
                RRBodyComponent bodyComp = bm.get(entity);

                // Store previous position for interpolation
                tfm.prevPosition.set(tfm.position);

                // Update the current position based on the Box2D body position
                Vector2 position = bodyComp.body.getPosition();
                tfm.position.set(position.x, position.y, tfm.position.z);
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }

        bodiesQueue.clear();
    }

    @Override
    public void processEntity(Entity entity) {
        bodiesQueue.add(entity);
    }
}
