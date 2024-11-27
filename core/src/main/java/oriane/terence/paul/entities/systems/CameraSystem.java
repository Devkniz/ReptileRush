package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

import oriane.terence.paul.entities.components.PlayerComponent;
import oriane.terence.paul.entities.components.TransformComponent;

public class CameraSystem extends IteratingSystem {

    private OrthographicCamera camera;
    private float mapWidth, mapHeight;
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public CameraSystem(OrthographicCamera camera, float mapWidth, float mapHeight) {
        super(Family.all(PlayerComponent.class).get());
        this.camera = camera;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }  

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);
        updateCamera(tfm.position.x, tfm.position.y);
    }

    private void updateCamera(float playerX, float playerY) {
        float cameraHalfWidth = camera.viewportWidth / 2f;
        float cameraHalfHeight = camera.viewportHeight / 2f;

        // Clamp the camera's position to stay within the map bounds
        float cameraX = MathUtils.clamp(playerX, cameraHalfWidth, mapWidth - cameraHalfWidth);
        float cameraY = MathUtils.clamp(playerY, cameraHalfHeight, mapHeight - cameraHalfHeight);

        // Set the camera's position
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

}
