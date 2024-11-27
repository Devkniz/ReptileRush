import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import oriane.terence.paul.BodyFactory;

public class BodyFactoryTest {
  private World mockWorld;
    private BodyFactory bodyFactory;

    @BeforeEach
    public void setUp() {
        mockWorld = mock(World.class);
        bodyFactory = BodyFactory.getInstance(mockWorld);
    }

    @Test
    public void testMakeCirclePolyBody_createsBodyWithCorrectProperties() {
        // Arrange
        float posx = 10f, posy = 15f, radius = 5f;
        int material = BodyFactory.STEEL;
        BodyType bodyType = BodyDef.BodyType.DynamicBody;

        Body mockBody = mock(Body.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        // Act
        Body result = bodyFactory.makeCirclePolyBody(posx, posy, radius, material, bodyType);

        // Assert
        assertNotNull(result);
        verify(mockWorld).createBody(argThat(def ->
            def.type == bodyType &&
            def.position.x == posx &&
            def.position.y == posy &&
            !def.fixedRotation
        ));
    }

    @Test
    public void testMakeBoxPolyBody_createsBodyWithCorrectProperties() {
        // Arrange
        float posx = 10f, posy = 20f, width = 4f, height = 8f;
        int material = BodyFactory.WOOD;
        BodyType bodyType = BodyDef.BodyType.StaticBody;

        Body mockBody = mock(Body.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        // Act
        Body result = bodyFactory.makeBoxPolyBody(posx, posy, width, height, material, bodyType);

        // Assert
        assertNotNull(result);
        verify(mockWorld).createBody(argThat(def ->
            def.type == bodyType &&
            def.position.x == posx &&
            def.position.y == posy &&
            !def.fixedRotation
        ));
    }

    @Test
    public void testMakePolygonShapeBody_createsBodyWithVertices() {
        // Arrange
        Vector2[] vertices = {
            new Vector2(0, 0),
            new Vector2(1, 0),
            new Vector2(1, 1),
            new Vector2(0, 1)
        };
        float posx = 5f, posy = 5f;
        int material = BodyFactory.RUBBER;
        BodyType bodyType = BodyDef.BodyType.DynamicBody;

        Body mockBody = mock(Body.class);
        when(mockWorld.createBody(any(BodyDef.class))).thenReturn(mockBody);

        // Act
        Body result = bodyFactory.makePolygonShapeBody(vertices, posx, posy, material, bodyType);

        // Assert
        assertNotNull(result);
        verify(mockWorld).createBody(argThat(def ->
            def.type == bodyType &&
            def.position.x == posx &&
            def.position.y == posy
        ));
    }

    @Test
    public void testMakeAllFixturesSensors_setsAllFixturesToSensors() {
        // Arrange
        Body mockBody = mock(Body.class);
        Fixture mockFixture1 = mock(Fixture.class);
        Fixture mockFixture2 = mock(Fixture.class);

        when(mockBody.getFixtureList()).thenReturn((Array<Fixture>) java.util.Arrays.asList(mockFixture1, mockFixture2));

        // Act
        bodyFactory.makeAllFixturesSensors(mockBody);

        // Assert
        verify(mockFixture1).setSensor(true);
        verify(mockFixture2).setSensor(true);
    }

    @Test
    public void testMakeConeSensor_createsConeShape() {
        // Arrange
        Body mockBody = mock(Body.class);
        float size = 10f;

        // Act
        bodyFactory.makeConeSensor(mockBody, size);

        // Assert
        verify(mockBody).createFixture(argThat(fixtureDef ->
            fixtureDef.shape instanceof PolygonShape
        ));
    }
}
