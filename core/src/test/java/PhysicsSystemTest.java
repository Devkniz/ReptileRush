import static org.mockito.Mockito.*;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import oriane.terence.paul.entities.components.RRBodyComponent;
import oriane.terence.paul.entities.components.TransformComponent;
import oriane.terence.paul.entities.systems.PhysicsSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhysicsSystemTest {
    private World mockWorld;                 
    private Body mockBody;
    private PhysicsSystem physicsSystem;
    private Entity mockEntity;

    private RRBodyComponent bodyComponent;
    private TransformComponent transformComponent;

    @BeforeEach
    public void setup() {
        // Création d'un mock pour le monde physique
        mockWorld = mock(World.class);
        mockBody = mock(Body.class);

        // Initialisation du système à tester
        physicsSystem = new PhysicsSystem(mockWorld);

        // Création d'une entité mockée avec ses composants
        mockEntity = mock(Entity.class);
        bodyComponent = new RRBodyComponent();
        bodyComponent.body = mockBody;

        transformComponent = new TransformComponent();
        transformComponent.position.set(0, 0, 0);
        transformComponent.prevPosition.set(0, 0, 0);

        // Ajouter des comportements simulés pour le corps
        when(mockBody.getPosition()).thenReturn(new Vector2(1, 2));
        when(mockBody.getAngle()).thenReturn((float) Math.toRadians(45));
    }

    @Test
    public void testUpdate_withSingleEntity() {
        // Ajouter l'entité mockée à la famille du système
        physicsSystem.processEntity(mockEntity);

        // Appeler la méthode update avec un deltaTime
        physicsSystem.update(PhysicsSystem.MAX_STEP_TIME);

        // Vérifier que la méthode step du monde a été appelée
        verify(mockWorld).step(eq(PhysicsSystem.MAX_STEP_TIME), eq(6), eq(2));

        // Vérifier les mises à jour des composants
        assert transformComponent.position.x == 1;
        assert transformComponent.position.y == 2;
        assert transformComponent.rotation == 45;
    }

    @Test
    public void testUpdate_withMultipleEntities() {
        // Créer une deuxième entité avec des composants différents
        Entity anotherMockEntity = mock(Entity.class);
        RRBodyComponent anotherBodyComponent = new RRBodyComponent();
        anotherBodyComponent.body = mock(Body.class);
        when(anotherBodyComponent.body.getPosition()).thenReturn(new Vector2(3, 4));
        when(anotherBodyComponent.body.getAngle()).thenReturn((float) Math.toRadians(90));

        // Ajouter les entités à la file
        physicsSystem.processEntity(mockEntity);
        physicsSystem.processEntity(anotherMockEntity);

        // Appeler la méthode update
        physicsSystem.update(PhysicsSystem.MAX_STEP_TIME);

        // Vérifier que le monde physique a bien avancé
        verify(mockWorld, times(1)).step(eq(PhysicsSystem.MAX_STEP_TIME), eq(6), eq(2));

        // Vérifier les mises à jour des deux entités
        assert transformComponent.position.x == 1;
        assert transformComponent.position.y == 2;
        assert transformComponent.rotation == 45;

        // Simuler l'autre entité
        TransformComponent anotherTransformComponent = new TransformComponent();
        anotherTransformComponent.position.set(3, 4, 0);
        assert anotherTransformComponent.position.x == 3;
        assert anotherTransformComponent.position.y == 4;
        assert anotherTransformComponent.rotation == 90;
    }

}
