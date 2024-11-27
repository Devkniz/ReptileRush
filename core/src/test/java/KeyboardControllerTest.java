import static org.junit.Assert.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.badlogic.gdx.Input.Keys;

import oriane.terence.paul.controller.KeyboardController;

public class KeyboardControllerTest {
  private KeyboardController controller;

    @BeforeEach
    public void setup() {
        controller = new KeyboardController();
    }

    @Test
    public void testKeyDown() {
        // Simuler l'appui des touches et vérifier les flags
        controller.keyDown(Keys.LEFT);
        assertTrue("Left key should be active", controller.left);
        controller.keyDown(Keys.RIGHT);
        assertTrue("Right key should be active", controller.right);
        controller.keyDown(Keys.UP);
        assertTrue("Up key should be active", controller.up);
        controller.keyDown(Keys.DOWN);
        assertTrue("Down key should be active", controller.down);
        controller.keyDown(Keys.ESCAPE);
        assertTrue("Escape key should be active", controller.escape);
    }

    @Test
    public void testKeyUp() {
        // Simuler l'appui des touches pour activer les flags
        controller.keyDown(Keys.LEFT);
        controller.keyDown(Keys.RIGHT);
        controller.keyDown(Keys.UP);
        controller.keyDown(Keys.DOWN);
        controller.keyDown(Keys.ESCAPE);

        // Simuler le relâchement des touches et vérifier les flags
        controller.keyUp(Keys.LEFT);
        assertFalse("Left key should be inactive", controller.left);
        controller.keyUp(Keys.RIGHT);
        assertFalse("Right key should be inactive", controller.right);
        controller.keyUp(Keys.UP);
        assertFalse("Up key should be inactive", controller.up);
        controller.keyUp(Keys.DOWN);
        assertFalse("Down key should be inactive", controller.down);
        controller.keyUp(Keys.ESCAPE);
        assertFalse("Escape key should be inactive", controller.escape);
    }

    @Test
    public void testKeyDownAndUpCombination() {
        // Simuler une séquence de pressions et de relâchements
        controller.keyDown(Keys.LEFT);
        assertTrue("Left key should be active", controller.left);
        controller.keyUp(Keys.LEFT);
        assertFalse("Left key should be inactive after keyUp", controller.left);

        controller.keyDown(Keys.UP);
        assertTrue("Up key should be active", controller.up);
        controller.keyUp(Keys.UP);
        assertFalse("Up key should be inactive after keyUp", controller.up);
    }
}
