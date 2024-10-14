package projet.ihm;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ResizedImage {

	public static void resizeImage(String imagePath, JFrame frame, JLabel label, int width, int height) {

		if (width > 0 && height > 0) {

			Image im = null;
			try {
				im = ImageIO.read(ResizedImage.class.getResource(imagePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Image resizedImage = im.getScaledInstance(frame.getWidth() / width, frame.getHeight() / height,
					Image.SCALE_SMOOTH);

			label.setIcon(new ImageIcon(resizedImage));
		}

	}
}