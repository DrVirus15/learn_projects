package forExam.improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The main class for counting silhouettes in an image.
 * It loads the image, processes it to find and count silhouettes,
 * and prints the result to the console.
 */
public class Assignment13Part1 {
    /**
     * Default image name if none is provided via command line arguments
     */
    private static final String DEFAULT_IMAGE_NAME = "test.jpg";

    /**
     * The main method to run the silhouette counting program.
     *
     * @param args - command line arguments, where the first argument can be the image file path
     */
    public static void main(String[] args) {
        String filePath = getFilePathFromArgs(args);
        BufferedImage image = null;
        try {
            image = new ImageLoader().load(filePath);
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        if (image != null) {
            System.out.println(new SilhouettesFinder().countSilhouettes(image));
        }
    }

    /**
     * Retrieves the file path from command line arguments or returns the default path.
     *
     * @param args - command line arguments.
     * @return - the image file path.
     */
    private static String getFilePathFromArgs(String[] args) {
        boolean isValidArg = args != null &&
                args.length > 0 &&
                args[0] != null &&
                !args[0].isEmpty();
        return isValidArg ? args[0] : DEFAULT_IMAGE_NAME;
    }

}
