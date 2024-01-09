package utilities;

import java.awt.*;
/**
 * This class contains common utility methods related to project.
 */
public class Utils {

    /**
     * Retrieves the current screen size.
     *
     * @return A {@code Dimension} object representing the screen size.
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Retrieves the width of the screen.
     *
     * @return The width of the screen in pixels.
     */
    public static int getScreenWidth() {
        Dimension screenSize = getScreenSize();
        return (int)screenSize.getWidth();
    }

    /**
     * Retrieves the height of the screen.
     *
     * @return The height of the screen in pixels.
     */
    public static int getScreenHeight() {
        Dimension screenSize = getScreenSize();
        return (int)screenSize.getHeight();
    }


}
