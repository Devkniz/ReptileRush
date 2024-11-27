# Reptile Rush

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project is a 2D game where players control an andventurer exploring a detailed map filled with obstacles and challenges. Built using an Entity Component System (ECS) architecture with Ashley ECS and leveraging Box2D for physics-based interactions, the game takes inspiration in titles like Vampire Survivors and Tiny Survivors.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

To run the game:
`./gradlew lwjgl3:run`

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Features

- Physics-based Gameplay: Smooth player movement and realistic collisions using Box2D.
- ECS Architecture: Efficient game logic separation using Ashley ECS.
- Rich Map Design: Tiled map integration for dynamic and visually appealing levels.
- Obstacles and Challenges: Rrocks, bigger rocks and other entities blocking paths and forcing strategic gameplay.
- Future Enhancements: Plans for enemy AI, collectibles, and dynamic scoring systems.

## Installation

Follow these steps to set up and run ReptileRush on your machine:

Clone the repository:
- `git clone https://github.com/your-repo/reptile-rush.git`
-  `cd reptile-rush`

Build the project:
- `./gradlew build`

Run the game:
- `./gradlew lwjgl3:run`

  ## Gameplay

  Controls:
  - `Arrow keys`: Move the player.
  - `Espace` Pause the game.
  - Attacks are performed automatically.
 
  Objective:
  - Navigate the map while avoiding obstacles.
  - Survive as long as possible.
  - [Future] Find the most powerfull upgrades and synergies.

## Project Structure

The project follows a modular structure, separating logic, rendering, and platform-specific implementations.
![image](https://github.com/user-attachments/assets/54bcfaa3-97d6-4f9c-8466-4ada6ec76ddf)
For a complete representation of the structure, please refer to `PROJECT TREE`.



## Technical Details 

### Dependencies 
The following libraries are used in the project:
- LibGDX: Core game development framework.
- Ashley ECS: Entity Component System for game logic.
- Box2D: Physics engine for realistic movements and interactions.
- Tiled Maps: Integration for designing levels with Tiled Editor
### Tiled Map Integration:
- Each level is designed using Tiled (.tmx format).
- Obstacles and objects are defined in separate layers (Obstacles, Decorations).
- Physics bodies are automatically generated from map objects for collision detection.

## Known Issues 

- Some minor rendering discrepancies for specific map objects.
- Scaling differences may occur for certain assets during rendering.

## Future Enhancements 

- Add enemy AI for dynamic challenges.
- Introduce collectible items and power-ups.
- Implement a scoreboard for competitive gameplay.
- Optimize rendering performance for larger maps.

## Contributors 
- Oriane: UI and sound implementation, experience and health systems.
- Paul: ECS development and physics systems.
- Terence: Tiled map and camera integration, collision systems.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more information.

