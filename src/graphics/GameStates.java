package graphics;

/**
 * The {@code GameStates} enum represents the different states that the game can be in.
 * Each enum constant corresponds to a specific state, defining the various phases or screens
 * the game can transition between.
 *
 * <p>
 * The possible game states are:
 * <ul>
 *     <li>{@code TITLE}: The initial state when the game starts, displaying the title screen.</li>
 *     <li>{@code SETTINGS}: The state for adjusting game settings or configurations.</li>
 *     <li>{@code PAUSED}: The state when the game is temporarily halted or paused.</li>
 *     <li>{@code GAMEPLAY}: The main gameplay state where the player interacts with the game.</li>
 *     <li>{@code GAMEOVER}: The state indicating the end of the game with a game over screen.</li>
 *     <li>{@code SHIP_PLACEMENT}: The state for placing ships on the game board before gameplay.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Set the initial game state to TITLE
 * GameStates currentState = GameStates.TITLE;
 *
 * // Check the current game state and perform actions accordingly
 * switch (currentState) {
 *     case TITLE:
 *         // Display the title screen
 *         break;
 *     case SETTINGS:
 *         // Display and handle game settings
 *         break;
 *     // ... (other cases)
 * }
 * }
 * </pre>
 * </p>
 *
 * @see GamePanel
 */
public enum GameStates {
    /**
     * The initial state when the game starts, displaying the title screen.
     */
    TITLE,

    /**
     * The state for adjusting game settings or configurations.
     */
    SETTINGS,

    /**
     * The state when the game is temporarily halted or paused.
     */
    PAUSED,

    /**
     * The main gameplay state where the player interacts with the game.
     */
    GAMEPLAY,

    /**
     * The state indicating the end of the game with a game over screen.
     */
    GAMEOVER,

    /**
     * The state for placing ships on the game board before gameplay.
     */
    SHIP_PLACEMENT
}