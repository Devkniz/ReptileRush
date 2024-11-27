package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
 
public class AnimationComponent implements Component {
    public IntMap<Animation<TextureRegion>> animations = new IntMap<Animation<TextureRegion>>();
}
