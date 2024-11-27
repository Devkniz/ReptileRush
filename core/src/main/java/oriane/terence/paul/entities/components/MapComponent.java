package oriane.terence.paul.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import oriane.terence.paul.entities.systems.RenderingSystem;


public class MapComponent implements Component {
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;

    public MapComponent(TiledMap map){
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(map, 1 / RenderingSystem.PPM);
    }
}
