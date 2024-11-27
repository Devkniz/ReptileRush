package oriane.terence.paul.views;

import com.badlogic.gdx.Screen;

import oriane.terence.paul.Main;

public class LoadingScreen implements Screen {

    private Main game;

    public LoadingScreen(Main game) {
        this.game = game;
    }

	@Override
	public void show() {
		// TODO: Implement this method
	}

	@Override
	public void render(float delta) {
		game.changeScreen(Main.Screen.menu);
    
	}

	@Override
	public void resize(int width, int height) {
		// TODO: Implement this method
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
	}
}