package improved.src.com.shpp.p2p.cs.akoskovtsev.assignment13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A utility class for loading images from the file system.
 */
public class ImageLoader {
    /**
     * Loads the image from the specified file path.
     * @return - the loaded BufferedImage
     * @throws IOException - if the file cannot be read or is not a valid image
     */
    public BufferedImage load(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        if (image == null) {
            throw new IOException("Unsupported image format or corrupted file: " + filePath);
        }
        return image;
    }
}
