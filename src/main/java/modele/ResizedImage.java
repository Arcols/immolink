package modele;

import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ResizedImage {

	public static void resizeImage(String imagePath, JFrame frame, JLabel label, int width, int height) {

		if (width > 0 && height > 0) {

			ImageIcon imic = new ImageIcon("src/resource/images/"+imagePath);

            Image im = imic.getImage();

			Image resizedImage = im.getScaledInstance(frame.getWidth() / width, frame.getHeight() / height,
					Image.SCALE_SMOOTH);

			label.setIcon(new ImageIcon(resizedImage));
		}

	}
}