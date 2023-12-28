package graphicsManager;

import graphics.GamePanel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * The {@code AnimationHandler} class manages the display and update of animations by
 * handling an array of BufferedImage frames.
 *
 * <p>
 * This class provides methods to construct an animation with a given set of frames,
 * draw the current frame at a specified position, update the animation to the next frame,
 * and retrieve a specific frame by its index.
 * </p>
 *
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an array of BufferedImage frames
 * BufferedImage[] animationFrames = // ... (initialize frames)
 *
 * // Create an AnimationHandler with the frames and refresh rate
 * AnimationHandler animation = new AnimationHandler(animationFrames, 10);
 *
 * // Inside the game loop:
 * // Draw the animation at a specified position
 * animation.draw(graphics2D, new Point(x, y));
 *
 * // Update the animation to advance to the next frame
 * animation.update();
 * }
 * </pre>
 * </p>
 *
 * @see BufferedImage
 * @see Graphics2D
 * @see Point
 * @see GamePanel
 */
public class AnimationHandler {

    /** The array of frames that constitute the animation. */
    private final BufferedImage[] frames;

    /** The rate at which the animation should be refreshed (updated). */
    private final int refreshRate;

    /** Counter to keep track of the number of refreshes. */
    private int refreshCounter = 0;

    /** Index of the current frame being displayed. */
    private int currentFrame = 0;

    /**
     * Constructs an AnimationHandler with the specified frames and refresh rate.
     *
     * @param frames      The array of BufferedImage frames for the animation.
     * @param refreshRate The rate at which the animation should be refreshed, in refresh cycles.
     */
    public AnimationHandler(BufferedImage[] frames, int refreshRate) {
        this.frames = frames;
        this.refreshRate = refreshRate;
    }

    /**
     * Draws the current frame of the animation at the specified position using
     * the provided Graphics2D object.
     *
     * @param g2  The Graphics2D object used for drawing.
     * @param pos The position where the top-left corner of the animation should be drawn.
     */
    public void draw(Graphics2D g2, Point pos) {
        g2.drawImage(frames[currentFrame],
                (int) pos.getX(), (int) pos.getY(),
                GamePanel.scaledTileSize, GamePanel.scaledTileSize,
                null);
    }

    /**
     * Updates the animation by advancing to the next frame based on the refresh rate.
     * The update method should be called regularly to ensure a smooth animation.
     */
    public void update() {
        refreshCounter++;
        if (refreshCounter > refreshRate) {
            // Check if the current frame is the last frame, if true, reset to the first frame.
            currentFrame = (currentFrame == frames.length - 1) ? 0 : (currentFrame + 1);
            refreshCounter = 0; // Reset the refresh counter after updating the frame.
        }
    }
    
    public void updateReverse() {
        refreshCounter++;
        if (refreshCounter > refreshRate) {
            // Check if the current frame is the last frame, if true, reset to the first frame.
            currentFrame = (currentFrame == 0) ? frames.length - 1 : (currentFrame - 1);
            refreshCounter = 0; // Reset the refresh counter after updating the frame.
        }
    }

    /**
     * Retrieves a specific frame from the animation based on the provided index.
     *
     * @param f The index of the frame to retrieve.
     * @return The BufferedImage representing the specified frame.
     */
    public BufferedImage getFrame(int f) {
        return frames[f];
    }
    
    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
    
    public int getCurrentFrame(int numberFrame) {
        return currentFrame;
    }
    
    public int getMaxFrames() {
        return frames.length;
    }
}