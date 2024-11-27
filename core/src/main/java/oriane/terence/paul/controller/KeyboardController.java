package oriane.terence.paul.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyboardController implements InputProcessor {

    public boolean left, right, up, down, escape;

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Keys.A:
            case Keys.LEFT: // if keycode is the same as Keys.LEFT a.k.a 21
                left = true; // do this
                keyProcessed = true;
                return true; // we have reacted to a keypress so stop checking for more
            case Keys.D:
            case Keys.RIGHT: // if keycode is the same as Keys.LEFT a.k.a 22
                right = true; // do this
                keyProcessed = true;
                return true; // we have reacted to a keypress so stop checking for more
            case Keys.W:
            case Keys.UP: // if keycode is the same as Keys.LEFT a.k.a 19
                up = true; // do this
                keyProcessed = true;
                return true; // we have reacted to a keypress so stop checking for more
            case Keys.S:
            case Keys.DOWN: // if keycode is the same as Keys.LEFT a.k.a 20
                down = true; // do this
                keyProcessed = true;
                return true; // we have reacted to a keypress so stop checking for more
            case Keys.ESCAPE:
                escape = true;
                keyProcessed = true;
        }

        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Keys.A:
            case Keys.LEFT: // if keycode is the same as Keys.LEFT a.k.a 21
                left = false; // do this
                keyProcessed = true;
                return false; // we have reacted to a keypress so stop checking for more
            case Keys.D:
            case Keys.RIGHT: // if keycode is the same as Keys.LEFT a.k.a 22
                right = false; // do this
                keyProcessed = true;
                return false; // we have reacted to a keypress so stop checking for more
            case Keys.W:
            case Keys.UP: // if keycode is the same as Keys.LEFT a.k.a 19
                up = false; // do this
                keyProcessed = true;
                return false; // we have reacted to a keypress so stop checking for more
            case Keys.S:
            case Keys.DOWN: // if keycode is the same as Keys.LEFT a.k.a 20
                down = false; // do this
                keyProcessed = true;
                return false; // we have reacted to a keypress so stop checking for more
            case Keys.ESCAPE:
                escape = false;
                keyProcessed = true;
        }

        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // throw new UnsupportedOperationException("Unimplemented method 'touchCancelled'");
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // throw new UnsupportedOperationException("Unimplemented method 'scrolled'");
        return false;
    }
}
