package modele;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ResizedImage {

	public static void resizeImage(String chemin_image, JFrame frame, JLabel label, int largeur, int hauteur) {
		if (largeur > 0 && hauteur > 0) {
			ImageIcon imic = new ImageIcon(ResizedImage.class.getResource("/images/" + chemin_image));
			Image im = imic.getImage();
			Image image_ajustee = im.getScaledInstance(frame.getWidth() / largeur, frame.getHeight() / hauteur, Image.SCALE_SMOOTH);
			label.setIcon(new ImageIcon(image_ajustee));
		}
	}
}