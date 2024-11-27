package oriane.terence.paul.views;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import oriane.terence.paul.BodyFactory;
import oriane.terence.paul.Main;
import oriane.terence.paul.RRContactListener;
import oriane.terence.paul.controller.KeyboardController;
import oriane.terence.paul.entities.EntityFactory;
import oriane.terence.paul.entities.systems.AnimationSystem;
import oriane.terence.paul.entities.systems.CameraSystem;
import oriane.terence.paul.entities.systems.CollisionSystem;
import oriane.terence.paul.entities.systems.EnemyAI;
import oriane.terence.paul.entities.systems.MapRenderSystem;
import oriane.terence.paul.entities.systems.PhysicsDebugSystem;
import oriane.terence.paul.entities.systems.PhysicsSystem;
import oriane.terence.paul.entities.systems.PlayerControlSystem;
import oriane.terence.paul.entities.systems.PlayerStateSystem;
import oriane.terence.paul.entities.systems.RenderingSystem;
import oriane.terence.paul.entities.systems.WaveSpawnerSystem;
import oriane.terence.paul.entities.systems.WeaponOrbitSystem;

public class MainScreen implements Screen {
	private Main game;
	private Stage stage;
	private KeyboardController controller;
	private SpriteBatch sb;
	private Music music;
	private PooledEngine engine;
	private World world;
	private BodyFactory bodyFactory;
	private RRContactListener contactListener = new RRContactListener();

	public enum GameState {
		CREATING,
		PLAYING,
		PAUSED,
		FINISHED
	}

	private GameState state = GameState.PLAYING;

	public MainScreen(Main game) {
		this.game = game;
		game.assMan.queueAddImages();
		game.assMan.manager.finishLoading();
		music = game.assMan.manager.get(game.assMan.backgroundmusic);
		music.play();
		music.setLooping(true);
	}

	@Override
	public void show() {
		state = GameState.CREATING;
		this.stage = new Stage(new ScreenViewport());
		controller = new KeyboardController();
		Gdx.input.setInputProcessor(controller);
		world = new World(new Vector2(0, 0f), true);
		world.setContactListener(contactListener);
		bodyFactory = BodyFactory.getInstance(world);
		sb = new SpriteBatch();
		// create the rendering system
		RenderingSystem renderingSystem = new RenderingSystem(sb);

		// init map
		TiledMap tiledMap = new TmxMapLoader().load("maps/Small_plain00.tmx");
		if (tiledMap == null) {
			throw new IllegalStateException("Falied to toald TiledMap :Small Plains");
		}
		// create a pooled engine
		engine = new PooledEngine();
		engine.removeAllSystems();
		// all the relevant systems our engine should run
		MapRenderSystem mrs = new MapRenderSystem(tiledMap, renderingSystem.getCamera(), world);
		engine.addSystem(mrs);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(renderingSystem);
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new CollisionSystem());

		engine.addSystem(new PlayerControlSystem(controller));
		engine.addSystem(new EnemyAI());

		EntityFactory entityFactory = new EntityFactory(engine, bodyFactory, game.assMan);
		engine.addSystem(new WaveSpawnerSystem(entityFactory));
		engine.addSystem(new WeaponOrbitSystem());
		engine.addSystem(new CameraSystem(renderingSystem.getCamera(), mrs.mapWidthInUnits, mrs.mapHeightInUnits));
		engine.addSystem(new PlayerStateSystem(this));
		// start the game by creating the default entities
		entityFactory.createPlayer();
		entityFactory.createWaveSpawner();

		state = GameState.PLAYING;
		System.out.println("WORLD DEBUG");
	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(Color.BLACK);

		if (controller.escape) {
			state = GameState.PAUSED;
		}
		switch (state) {
			case CREATING:
				System.out.println("waiting");
				break;
			case PLAYING:
				Gdx.input.setInputProcessor(controller);
				engine.update(delta);
				break;
			case PAUSED:
				pauseScreen(delta);
				break;
			case FINISHED:
				this.dispose();
				game.changeScreen(Main.Screen.end);
				state = GameState.CREATING;
				break;
		}

	}

	private void pauseScreen(float delta) {
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stage.addActor(table);

		Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

		TextButton play = new TextButton("Resume", skin);
		TextButton settings = new TextButton("Settings", skin);
		TextButton exit = new TextButton("Exit", skin);

		play.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				controller.escape = false;
				stage.clear();
				state = GameState.PLAYING;
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

		table.add(play).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(settings).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();

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
		// TODO: Implement this method;
	}

	@Override
	public void dispose() {
		// TODO: Implement this method
		if (world != null) {
			Array<Body> bodies = new Array<>();
			world.getBodies(bodies);
			for (Body body : bodies) {
				System.out.println("destroyed body");
				world.destroyBody(body);
			}
			// world.dispose();
			world = null;
		}

		engine.removeAllSystems();
		engine.removeAllEntities();
		sb.dispose();
		stage.dispose();
		// world.dispose();
		engine = null;
		controller = null;

		// music.dispose();
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
