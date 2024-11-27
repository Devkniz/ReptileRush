package oriane.terence.paul.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import oriane.terence.paul.entities.systems.RenderingSystem;

public class RandomPositionGenerator {

    public static Vector2 getRandomPositionAtExtremities() {
        float screenWidth = Gdx.graphics.getWidth() / RenderingSystem.PPM - 2;
        float screenHeight = Gdx.graphics.getHeight() / RenderingSystem.PPM - 2;

        // Randomly choose an edge: 0 = top, 1 = bottom, 2 = left, 3 = right
        int edge = MathUtils.random(3);

        float x = 2, y = 2;
        switch (edge) {
            case 0: // Top edge
                x = MathUtils.random(0, screenWidth);
                y = screenHeight;
                break;
            case 1: // Bottom edge
                x = MathUtils.random(0, screenWidth);
                break;
            case 2: // Left edge
                y = MathUtils.random(0, screenHeight);
                break;
            case 3: // Right edge
                x = screenWidth;
                y = MathUtils.random(0, screenHeight);
                break;
        }

        return new Vector2(x, y);
    }
}

