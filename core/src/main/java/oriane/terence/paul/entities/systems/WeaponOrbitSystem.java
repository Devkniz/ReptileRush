package oriane.terence.paul.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import oriane.terence.paul.entities.components.*;

public class WeaponOrbitSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<WeaponFireballComponent> wm = ComponentMapper.getFor(WeaponFireballComponent.class);
    private ComponentMapper<ParentComponent> pm = ComponentMapper.getFor(ParentComponent.class);

    public WeaponOrbitSystem() {
        super(Family.all(WeaponFireballComponent.class).get());
    }

    @Override
    public void processEntity(Entity weapon, float deltaTime) {

        WeaponFireballComponent weaponComp = wm.get(weapon);
        TransformComponent weaponTrans = tm.get(weapon);
        ParentComponent parentComp = pm.get(weapon);

        Entity player = parentComp.parentEntity;
        TransformComponent playerTrans = tm.get(player);

        System.out.println("Weapon Offset: " + weaponComp.offset);
        System.out.println("Weapon Position: " + weaponTrans.position);
        System.out.println("Player Position: " + playerTrans.position);

        if (playerTrans != null) {
            // Update the orbit angle
            float angle = (float) (deltaTime * weaponComp.orbitSpeed * Math.PI / 180);
            weaponComp.offset.rotateRad(angle);

            // Update the weapon position based on player's position
            RRBodyComponent rrbody = weapon.getComponent(RRBodyComponent.class);
            if (rrbody != null) {
                rrbody.body.setTransform(
                        playerTrans.position.x + weaponComp.offset.x,
                        playerTrans.position.y + weaponComp.offset.y,
                        0);
            }

            // weaponTrans.position.set(
            // playerTrans.position.x + weaponComp.offset.x,
            // playerTrans.position.y + weaponComp.offset.y,
            // 0);
        }
    }

}
