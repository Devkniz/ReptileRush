package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import oriane.terence.paul.entities.EntityFactory;
import oriane.terence.paul.entities.components.EnemyComponent;
import oriane.terence.paul.entities.components.WaveSpawnerComponent;

public class WaveSpawnerSystem extends IteratingSystem {

    private ComponentMapper<WaveSpawnerComponent> wm;
    private EntityFactory entityFactory;

    public WaveSpawnerSystem(EntityFactory factory) {
        super(Family.all(WaveSpawnerComponent.class).get());
        wm = ComponentMapper.getFor(WaveSpawnerComponent.class);
        this.entityFactory = factory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WaveSpawnerComponent waveSpawner = wm.get(entity);
        if (!waveSpawner.waveActive && allEnemiesDefeated()) {
            prepareNextWave(waveSpawner);
        }

        if (waveSpawner.waveActive) {
            waveSpawner.spawnTimer -= deltaTime;

            if (waveSpawner.spawnTimer <= 0 && waveSpawner.enemiesToSpawn > 0) {
                spawnEnemy();
                waveSpawner.enemiesToSpawn--;
                waveSpawner.spawnTimer = waveSpawner.timeBetweenSpawns;
            }

            if (waveSpawner.enemiesToSpawn == 0) {
                waveSpawner.waveActive = false;
            }
        }
    }

    private void spawnEnemy() {
        Vector2 randomPosition = entityFactory.createEnemy();
        System.out.println("Enemy spawned at: " + randomPosition);
    }

    private void prepareNextWave(WaveSpawnerComponent waveSpawner) {
        waveSpawner.currentWave++;
        waveSpawner.enemiesToSpawn = 1 + waveSpawner.currentWave * 2; // Example wave scaling
        waveSpawner.timeBetweenSpawns = Math.max(0.5f, waveSpawner.timeBetweenSpawns - 0.1f); // Example scaling
        waveSpawner.waveActive = true; // Start the next wave
        System.out.println("Wave " + waveSpawner.currentWave + " is starting!");
    }

    private boolean allEnemiesDefeated() {
        Family enemyFamily = Family.all(EnemyComponent.class).get();
        ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(enemyFamily);
        return enemies.size() == 0;
    }
}
