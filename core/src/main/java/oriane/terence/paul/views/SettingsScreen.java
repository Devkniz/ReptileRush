package oriane.terence.paul.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import oriane.terence.paul.AppPreferences;
import oriane.terence.paul.Main;

public class SettingsScreen implements Screen {

  private Main game;
  private Stage stage;

  private Label titleLabel;
  private Label volumeMusicLabel;
  private Label volumeSoundLabel;
  private Label musicOnOffLabel;
  private Label soundOnOffLabel;
  private Label fullScreen;
  private Label windowScreen;
  private Label smallScreen;
  private Music music;
  private Monitor monitor;
  private DisplayMode displayMode;

  public SettingsScreen(Main game) {
    this.game = game;
    this.stage = new Stage(new ScreenViewport());
    this.music = game.assMan.manager.get(game.assMan.backgroundmusic);
  }

  @Override
  public void show() {
    stage.clear();
    Gdx.input.setInputProcessor(stage);
    // Create a table that fills the screen. Everything else will go inside this
    // table.
    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
    stage.addActor(table);

    Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

    // music enabling
    final CheckBox musicCheckbox = new CheckBox(null, skin);
    musicCheckbox.setChecked(AppPreferences.musicIsPlaying.isEnabled());
    musicCheckbox.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        if (event instanceof ChangeListener.ChangeEvent) {
          boolean enabled = musicCheckbox.isChecked();

          if (music != null) {
            if (enabled) {
              music.play();
            } else {
              music.stop();
            }
          } else {
            System.err.println("Music object is null!");
          }

          if (AppPreferences.musicIsPlaying != null) {
            AppPreferences.musicIsPlaying.setPreferences(enabled);
          } else {
            System.err.println("AppPreferences.musicIsPlaying is null!");
          }
        }
        return false;
      }

    });

    // full Screen enabling
    final CheckBox fullScreenCheckbox = new CheckBox(null, skin);
    fullScreenCheckbox.setChecked(AppPreferences.fullScreenIsEnabled.isEnabled());
    fullScreenCheckbox.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        boolean enabled = fullScreenCheckbox.isChecked();
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        if (enabled) {
          // Passer en mode plein écran
          if (!Gdx.graphics.setFullscreenMode(displayMode)) {
            System.err.println("Impossible de passer en plein écran !");
            fullScreenCheckbox.setChecked(false); // Réinitialiser l'état si l'opération échoue
          }
        } else {
          // Passer en mode fenêtré
          Gdx.graphics.setWindowedMode(800, 600);
        }

        // Mettre à jour les préférences utilisateur
        if (AppPreferences.fullScreenIsEnabled != null) {
          AppPreferences.fullScreenIsEnabled.setPreferences(enabled);
        } else {
          System.err.println("AppPreferences.fullScreenIsEnabled est null !");
        }
      }
    });

    final CheckBox smallScreenCheckbox = new CheckBox(null, skin);
    smallScreenCheckbox.setChecked(AppPreferences.smallScreenIsEnabled.isEnabled());
    smallScreenCheckbox.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        boolean enabled = smallScreenCheckbox.isChecked();
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        if (enabled) {
          // Passer en mode plein écran
          if (Gdx.graphics.setWindowedMode(400, 500)) {
            System.err.println("Impossible de passer en petit écran !");
            fullScreenCheckbox.setChecked(false); // Réinitialiser l'état si l'opération échoue
          } else if (Gdx.graphics.setFullscreenMode(displayMode)) {
            System.err.println("Impossible de passer en petit écran !");
            fullScreenCheckbox.setChecked(false); // Réinitialiser l'état si l'opération échoue
          }
        } else {
          // Passer en mode fenêtré
          Gdx.graphics.setWindowedMode(800, 600);
        }

        // Mettre à jour les préférences utilisateur
        if (AppPreferences.smallScreenIsEnabled != null) {
          AppPreferences.smallScreenIsEnabled.setPreferences(enabled);
        } else {
          System.err.println("AppPreferences.fullScreenIsEnabled est null !");
        }
      }
    });

    // Back button
    final TextButton backButton = new TextButton("Back", skin, "small");
    backButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Main.Screen.menu);
      }
    });

    // organize table
    titleLabel = new Label("Preferences", skin);
    volumeMusicLabel = new Label("Music Volume", skin);
    volumeSoundLabel = new Label("Sound Volume", skin);
    musicOnOffLabel = new Label("Music On/Off", skin);
    soundOnOffLabel = new Label("Sound effects", skin);
    fullScreen = new Label("Full Screen", skin);
    windowScreen = new Label("Windowed Screen", skin);
    smallScreen = new Label("Small Screen", skin);

    table.add(titleLabel).colspan(2);
    table.row().pad(10, 0, 0, 10);
    // table.add(volumeMusicLabel).left();
    // table.add(volumeMusicSlider);
    // table.row().pad(10,0,0,10);
    table.add(musicOnOffLabel).left();
    table.add(musicCheckbox);
    table.row().pad(10, 0, 0, 10);
    // table.add(volumeSoundLabel).left();
    // table.add(volumeSoundSlider);
    // table.row().pad(10,0,0,10);
    // table.add(soundOnOffLabel).left();
    // table.add(soundCheckbox);
    table.add(fullScreen).left();
    table.add(fullScreenCheckbox);
    table.row().pad(10, 0, 0, 10);

    // table.row().pad(10,0,0,10);

    // table.add(windowScreen).left();
    // table.add(windowScreenCheckbox);
    // table.row().pad(10,0,0,10);

    table.add(smallScreen).left();
    table.add(smallScreenCheckbox);
    table.row().pad(10, 0, 0, 10);
    table.add(backButton).colspan(2);
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLUE);
    stage.act(Math.min(delta, 1 / 60f));
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
    // TODO: Implement this method
  }

  @Override
  public void resume() {
    // TODO: Implement this method
  }

  @Override
  public void hide() {
    // TODO: Implement this method
  }

  @Override
  public void dispose() {
    stage.dispose();
  }
}
