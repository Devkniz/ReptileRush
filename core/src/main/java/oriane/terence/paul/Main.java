package oriane.terence.paul;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;

import oriane.terence.paul.loader.RRAssetManager;
import oriane.terence.paul.views.EndScreen; // Adjust the package name as needed
import oriane.terence.paul.views.LoadingScreen;
import oriane.terence.paul.views.MainScreen;
import oriane.terence.paul.views.MenuScreen;
import oriane.terence.paul.views.SettingsScreen;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
  private LoadingScreen loadingScreen;
  private MenuScreen menuScreen;
  private MainScreen mainScreen;
  private SettingsScreen settingsScreen;
  private EndScreen endScreen;
  private Music music;
  public RRAssetManager assMan = new RRAssetManager();
  private Object game;
  public Object gameState;
  public enum Screen {
    loading, main, menu, settings, end
  }

  @Override
  public void create() {
    loadingScreen = new LoadingScreen(this);
    menuScreen = new MenuScreen(this);
    mainScreen = new MainScreen(this);
    settingsScreen = new SettingsScreen(this);
    endScreen = new EndScreen(this);
    setScreen(loadingScreen);
  }

  public void changeScreen(Screen screen) {
    switch (screen) {
      case loading:
        setScreen(loadingScreen);
        break;

      case menu:
        setScreen(menuScreen);
        break;

      case main:
        setScreen(mainScreen);
        break;

      case settings:
        setScreen(settingsScreen);
        break;

      case end:
        setScreen(endScreen);
        break;

      default:
        break;
    }
  }
}
