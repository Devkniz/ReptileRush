package oriane.terence.paul.entities.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import oriane.terence.paul.entities.ZComparator;
import oriane.terence.paul.entities.components.HealthComponent;
import oriane.terence.paul.entities.components.TextureComponent;
import oriane.terence.paul.entities.components.TransformComponent;
import oriane.terence.paul.entities.components.TypeComponent;
import oriane.terence.paul.entities.components.XPComponent;

public class RenderingSystem extends SortedIteratingSystem {


  public static final float PPM = 32.0f; // sets the amount of pixels each metre of box2d objects contains

  // this gets the height and width of our camera frustrum based off the width and
  // height of the screen and our pixel per meter ratio
  public static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
  public static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

  public static final float PIXELS_TO_METRES = 1.0f / PPM; // get the ratio for converting pixels to metres

  // static method to get screen width in metres
  private static Vector2 meterDimensions = new Vector2();
  private static Vector2 pixelDimensions = new Vector2();

  public static Vector2 getScreenSizeInMeters() {
    meterDimensions.set(Gdx.graphics.getWidth() * PIXELS_TO_METRES,
        Gdx.graphics.getHeight() * PIXELS_TO_METRES);
    return meterDimensions;
  }

  // static method to get screen size in pixels
  public static Vector2 getScreenSizeInPixesl() {
    pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    return pixelDimensions;
  }

  // convenience method to convert pixels to meters
  public static float PixelsToMeters(float pixelValue) {
    return pixelValue * PIXELS_TO_METRES;
  }

  private SpriteBatch batch; // a reference to our spritebatch
  private Array<Entity> renderQueue; // an array used to allow sorting of images allowing us to draw images on top of
  private Comparator<Entity> comparator; // a comparator to sort images based on the z position of the
  private OrthographicCamera cam; // a reference to our camera

  // component mappers to get components from entities
  private ComponentMapper<TextureComponent> textureM;
  private ComponentMapper<TransformComponent> transformM;
  private ComponentMapper<HealthComponent> healthM;
  private ComponentMapper<TypeComponent> typeM;
  private ComponentMapper<XPComponent> xpM;
  private ShapeRenderer shapeRenderer;
  private ShapeRenderer shapeRendererXP;

    public RenderingSystem(SpriteBatch batch) {
      // gets all entities with a TransofmComponent and TextureComponent
      super(
          Family.all(TransformComponent.class, TextureComponent.class, TypeComponent.class)
              .get(),
          new ZComparator());

      comparator = new ZComparator();
      // creates out componentMappers
      textureM = ComponentMapper.getFor(TextureComponent.class);
      transformM = ComponentMapper.getFor(TransformComponent.class);
      healthM = ComponentMapper.getFor(HealthComponent.class);
      typeM = ComponentMapper.getFor(TypeComponent.class);
      xpM = ComponentMapper.getFor(XPComponent.class);

      // create the array for sorting entities
      renderQueue = new Array<Entity>();
      shapeRenderer = new ShapeRenderer();
      shapeRendererXP = new ShapeRenderer();

      this.batch = batch; // set our batch to the one supplied in constructor

        // set up the camera to match our screen size
        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
        cam.update();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Calculate the alpha for interpolation based on the physics delta time
        // float alpha = PhysicsSystem.accumulator / PhysicsSystem.MAX_STEP_TIME;

        renderQueue.sort(comparator);

        // Update camera and sprite batch
        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);
            TransformComponent tfm = transformM.get(entity);
            HealthComponent healthC = healthM.get(entity);
            TypeComponent typeC = typeM.get(entity);
            XPComponent xpC = xpM.get(entity);

            if (typeC.type == TypeComponent.PLAYER) {
                playerHealth(tfm, healthC);
                playerXP(tfm, xpC);
            }
            
            if (tex.region == null || tfm.isHidden) {
                
                continue;
            }

            // Interpolate position between the current and previous position
            // float interpolatedX = tfm.prevPosition.x * (1 - alpha) + tfm.position.x * alpha;
            // float interpolatedY = tfm.prevPosition.y * (1 - alpha) + tfm.position.y * alpha;

            float interpolatedX = tfm.position.x;
            float interpolatedY = tfm.position.y;
            // Prepare rendering details
            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width / 2f;
            float originY = height / 2f;

            // Draw with interpolated position
            batch.draw(tex.region, interpolatedX - originX, interpolatedY - originY, originX, originY, width, height,
                    PixelsToMeters(tfm.scale.x), PixelsToMeters(tfm.scale.y), tfm.rotation);
        }

        batch.end();
        shapeRenderer.end();
        shapeRendererXP.end();
        renderQueue.clear();
    //   // set up the camera to match our screen size
    //   cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
    //   cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    renderQueue.add(entity);
  }

  // convenience method to get camera
  public OrthographicCamera getCamera() {
    return cam;
  }

  public void playerHealth(TransformComponent transform, HealthComponent healthC) {
    if (transform == null || healthC == null) {
      return;
    }

    shapeRenderer.setProjectionMatrix(
        new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    // Interpolated player position
    float barWidth = Gdx.graphics.getWidth() * 0.2f; // 20% of screen width
    float barHeight = Gdx.graphics.getHeight() * 0.03f; // 3% of screen height
    float barX = Gdx.graphics.getWidth() - barWidth - 20; // 20px margin from the right
    float barY = Gdx.graphics.getHeight() - barHeight - 20; // 20px margin from the top
    float healthPercentage = (float) healthC.currHealth / healthC.maxHealth;

    // Draw with ShapeRenderer
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    // Background bar (gray)
    shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1f);
    shapeRenderer.rect(barX, barY, barWidth, barHeight);

    // Foreground bar (red)
    shapeRenderer.setColor(1f, 0f, 0f, 1f);
    shapeRenderer.rect(barX, barY, barWidth * healthPercentage, barHeight);

    // table.add(healthPoints);
    // float textX = barX + barWidth + 10; // Slightly to the right of the bar
    // float textY = barY + barHeight - 5; // Centered vertically with the bar

  }

  public void playerXP(TransformComponent transform, XPComponent xpc) {
    if (transform == null || xpc == null) {
      return;
    }

    shapeRendererXP.setProjectionMatrix(
        new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    // Interpolated player position
    float barWidth = Gdx.graphics.getWidth() * 0.2f; // 20% of screen width
    float barHeight = Gdx.graphics.getHeight() * 0.03f; // 3% of screen height
    float barX = 20; // 20px margin from the left
    float barY = Gdx.graphics.getHeight() - 0.5f * barHeight - 30;
    // 20px margin from the top
    float XPPercentage = (float) xpc.currXP / xpc.maxXP;

    // Draw with ShapeRenderer
    shapeRendererXP.begin(ShapeRenderer.ShapeType.Filled);

    // Background bar (gray)
    shapeRendererXP.setColor(0.5f, 0.5f, 0.5f, 1f);
    shapeRendererXP.rect(barX, barY, barWidth, barHeight);

    // Foreground bar (red)
    shapeRendererXP.setColor(4, 59, 92, 1);
    shapeRendererXP.rect(barX, barY, barWidth * XPPercentage, barHeight);

  }
}
