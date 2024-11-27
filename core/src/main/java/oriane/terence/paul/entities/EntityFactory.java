package oriane.terence.paul.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import oriane.terence.paul.BodyFactory;
import oriane.terence.paul.entities.components.CollisionComponent;
import oriane.terence.paul.entities.components.EnemyComponent;
import oriane.terence.paul.entities.components.HealthComponent;
import oriane.terence.paul.entities.components.ParentComponent;
import oriane.terence.paul.entities.components.PlayerComponent;
import oriane.terence.paul.entities.components.RRBodyComponent;
import oriane.terence.paul.entities.components.StateComponent;
import oriane.terence.paul.entities.components.TextureComponent;
import oriane.terence.paul.entities.components.TransformComponent;
import oriane.terence.paul.entities.components.TypeComponent;
import oriane.terence.paul.entities.components.WaveSpawnerComponent;
import oriane.terence.paul.entities.components.WeaponFireballComponent;
import oriane.terence.paul.entities.components.XPComponent;
import oriane.terence.paul.entities.systems.RenderingSystem;
import oriane.terence.paul.loader.RRAssetManager;

public class EntityFactory {
    private Engine engine;
    private BodyFactory bodyFactory;
    private Texture playerTex;
    private Texture snakeTex;
    private Texture dragonTex;
    private RRAssetManager assMan;
    private Texture fireballTex;

    public EntityFactory(Engine engine, BodyFactory bodyFactory, RRAssetManager assMan) {
        this.engine = engine;
        this.bodyFactory = bodyFactory;
        this.assMan = assMan;
        this.playerTex = assMan.manager.get(assMan.placeholder);
        this.snakeTex = assMan.manager.get(assMan.snake);
        this.dragonTex = assMan.manager.get(assMan.dragon);
        this.fireballTex = assMan.manager.get(assMan.fireball);
    }

    public void createPlayer() {
        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();

        RRBodyComponent rrbody = engine.createComponent(RRBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);
        HealthComponent healthComp = engine.createComponent(HealthComponent.class);
        XPComponent xpComp = engine.createComponent(XPComponent.class);

        // Calculate the center of the camera's view
        float centerX = RenderingSystem.FRUSTUM_WIDTH / 2f;
        float centerY = RenderingSystem.FRUSTUM_HEIGHT / 2f;

        // Create Box2D body and set its position
        rrbody.body = bodyFactory.makeBoxPolyBody(centerX, centerY, 2, 2, BodyFactory.RUBBER, BodyType.DynamicBody,
                false);
        rrbody.body.setLinearVelocity(new Vector2(0, 0));
        // Set position to camera center (x, y, z)
        position.position.set(centerX, centerY, 10);

        texture.region = new TextureRegion(playerTex);
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        healthComp.maxHealth = 100;
        healthComp.currHealth = 100;
        xpComp.maxXP = 100;
        xpComp.currXP = 10;

        rrbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(rrbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);
        entity.add(healthComp);
        entity.add(xpComp);

        // add the entity to the engine
        engine.addEntity(entity);
        createFireballWeapon(entity);
    }

    public void createFireballWeapon(Entity player) {
        Entity weapon = engine.createEntity();

        WeaponFireballComponent weaponComp = engine.createComponent(WeaponFireballComponent.class);
        RRBodyComponent rrbody = engine.createComponent(RRBodyComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        TextureComponent textureComp = engine.createComponent(TextureComponent.class);
        ParentComponent parent = engine.createComponent(ParentComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        // Set weapon properties
        weaponComp.orbitRadius = 1; // Adjust as needed
        weaponComp.orbitSpeed = 200; // 90 degrees per second
        weaponComp.damage = 10; // Damage dealt on collision
        weaponComp.offset.set(3, 0); // Start at the orbit radius

        float centerX = RenderingSystem.FRUSTUM_WIDTH / 2f;
        float centerY = RenderingSystem.FRUSTUM_HEIGHT / 2f;

        // Set initial position and parent    
        parent.parentEntity = player;
        

        rrbody.body = bodyFactory.makeCirclePolyBody(centerX,centerY, weaponComp.orbitRadius, BodyFactory.RUBBER, BodyType.KinematicBody);
        // bodyFactory.makeAllFixturesSensors(rrbody.body);
        rrbody.body.setUserData(weapon);
        transform.position.set(centerX, centerY, 0);
        transform.scale.set(0.05f, 0.05f);
        textureComp.region = new TextureRegion(fireballTex);
        // Assign type
        type.type = TypeComponent.OTHER;

        // Add components
        weapon.add(weaponComp);
        weapon.add(transform);
        weapon.add(parent);
        weapon.add(textureComp);
        weapon.add(rrbody);
        weapon.add(collision);
        weapon.add(type);

        // Add to engine
        engine.addEntity(weapon);
    }

    public Vector2 createEnemy() {
        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        RRBodyComponent rrbody = engine.createComponent(RRBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        EnemyComponent enemyComp = engine.createComponent(EnemyComponent.class);
        // CollisionComponent colComp =
        // engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        // Create Box2D body and set its position
        Vector2 randPos = RandomPositionGenerator.getRandomPositionAtExtremities();
        rrbody.body = bodyFactory.makeBoxPolyBody(randPos.x, randPos.y, 2, 2, BodyFactory.RUBBER,
                BodyType.KinematicBody,
                false);

        // Link position to body
        position.position.set(randPos.x, randPos.y, 0);
        texture.region = new TextureRegion(snakeTex);
        type.type = TypeComponent.ENEMY;
        stateCom.set(StateComponent.STATE_NORMAL);
        rrbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(rrbody);
        entity.add(position);
        entity.add(texture);
        entity.add(enemyComp);
        // entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        // add the entity to the engine
        engine.addEntity(entity);
        return randPos;
    }

    public void createWaveSpawner() {
        Entity waveSpawner = new Entity();
        WaveSpawnerComponent waveComponent = new WaveSpawnerComponent();
        waveComponent.currentWave = 1; // Start at wave 1
        waveComponent.enemiesToSpawn = 3; // Number of enemies in the first wave
        waveComponent.waveActive = true; // Start spawning immediately
        waveSpawner.add(waveComponent);
        engine.addEntity(waveSpawner);
    }
}
