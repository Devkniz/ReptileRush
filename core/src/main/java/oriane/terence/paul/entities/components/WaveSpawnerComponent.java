
package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;

public class WaveSpawnerComponent implements Component {
    public int currentWave = 0; // Current wave number
    public float timeBetweenSpawns = 1f; // Time delay between enemy spawns
    public int enemiesToSpawn = 0; // Total enemies to spawn in this wave
    public float spawnTimer = 0f; // Timer to control enemy spawning
    public boolean waveActive = false; // Is the wave currently active?
}
