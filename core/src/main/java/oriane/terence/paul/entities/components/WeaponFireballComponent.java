package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class WeaponFireballComponent implements Component{
    public float orbitRadius; // Distance from the player
    public float orbitSpeed;  // Rotation speed in degrees per second
    public Vector2 offset = new Vector2(); // Current offset from the player
    public float damage = 10; // Amount of damage the weapon inflicts
    
}
