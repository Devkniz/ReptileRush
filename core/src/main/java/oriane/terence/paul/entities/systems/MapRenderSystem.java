package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MapRenderSystem extends EntitySystem {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private float unitScale = 1 / RenderingSystem.PPM;
    public float mapWidthInUnits;
    public float mapHeightInUnits;

    public MapRenderSystem(TiledMap tiledMap, OrthographicCamera camera, World world) {
        this.camera = camera;
        this.tiledMap = tiledMap;
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        // Collision layer
        createCollisionEntities(tiledMap, world);
        
        System.out.println("MapRenderSystem initialized with camera at position: " + camera.position);

        int mapWidth = tiledMap.getProperties().get("width", Integer.class);
        int mapHeight = tiledMap.getProperties().get("height", Integer.class);
        int tilePixelWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        int tilePixelHeight = tiledMap.getProperties().get("tileheight", Integer.class);

        mapWidthInUnits = mapWidth * tilePixelWidth * unitScale;
        mapHeightInUnits = mapHeight * tilePixelHeight * unitScale;

        camera.position.set(mapWidthInUnits / 2f, mapHeightInUnits / 2f, 0);
        camera.viewportWidth = mapWidthInUnits - 5;
        camera.viewportHeight = mapHeightInUnits  - 5;
        camera.update();
        mapRenderer.setView(camera);
        System.out.println("Camera centered at: " + camera.position);
        System.out.println("MapRenderer active: " + tiledMap);
        System.out.println("Camera configured for map=" + tiledMap);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void createCollisionEntities(TiledMap tiledMap, World world) {
        MapLayer collisionLayer = tiledMap.getLayers().get("Obstacles");

        if (collisionLayer == null) {
            throw new IllegalStateException("Obstacle layer not found in the map!");
        }

        for (MapObject object : collisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                // Convert rectangle to Box2D body
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(
                        (rectangle.x + rectangle.width / 2) / RenderingSystem.PPM,
                        (rectangle.y + rectangle.height / 2) / RenderingSystem.PPM);

                Body body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(
                        rectangle.width / 2 / RenderingSystem.PPM,
                        rectangle.height / 2 / RenderingSystem.PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.isSensor = false;

                body.createFixture(fixtureDef);
                shape.dispose();
            }
        }
    }

    public void dispose() {
        mapRenderer.dispose();
        tiledMap.dispose();
    }
}
