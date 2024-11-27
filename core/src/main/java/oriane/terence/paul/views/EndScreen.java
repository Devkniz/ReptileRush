package oriane.terence.paul.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import oriane.terence.paul.Main;

public class EndScreen implements Screen {

  private Main game;
  private Stage stage;
  private Music music;
  private Label titleLabel;

  public EndScreen(Main game) {
    this.game = game;
    this.stage = new Stage(new ScreenViewport());
    this.music = game.assMan.manager.get(game.assMan.backgroundmusic);
    game.assMan.queueAddImages();
    game.assMan.manager.finishLoading();
    music = game.assMan.manager.get(game.assMan.backgroundmusic);
    music.play();
    music.setLooping(true);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
    stage.addActor(table);

    Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    titleLabel = new Label("GAME OVER", skin);

    TextButton newGame = new TextButton("New Game", skin);
    TextButton settings = new TextButton("Settings", skin);
    TextButton exit = new TextButton("Exit", skin);

    newGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Main.Screen.main);
      }
    });

    settings.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.changeScreen(Main.Screen.settings);
      }
    });

    exit.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        Gdx.app.exit();
      }
    });
    table.add(titleLabel).colspan(2);
    table.row().pad(10, 0, 0, 10);
    table.add(newGame).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);
    table.add(settings).fillX().uniformX();
    table.row();
    table.add(exit).fillX().uniformX();
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.GREEN);
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
    // TODO: Implement this method
    stage.dispose();
  }
}
