package oriane.terence.paul.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class RRAssetManager {
  public final AssetManager manager = new AssetManager();
  public final String placeholder = "images/placeholder.png";
  public final String snake = "images/snake.png";
  public final String dragon = "images/dragon.png";
  public final String backgroundmusic = "sound/mysterious.mp3";
  public final String fireball = "images/fireball.png";

  public void queueAddImages() {
    manager.load(placeholder, Texture.class);
    manager.load(snake, Texture.class);
    manager.load(dragon, Texture.class);
    manager.load(backgroundmusic, Music.class);
    manager.load(fireball, Texture.class);
  }

}
