package modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ihm.*;

public class Menu implements ActionListener {
	private JFrame frame;

	public Menu(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		switch (b.getText()) {
			case "Accueil":
				this.frame.dispose();
				PageAccueil pageAccueil = new PageAccueil();
				pageAccueil.main(null);
				break;
			case "Mes baux":
				this.frame.dispose();
				PageBaux pageBaux = new PageBaux();
				pageBaux.main(null);
				break;
			case "Mes Biens":
				this.frame.dispose();
				PageMesBiens pageMesBiens = new PageMesBiens();
				PageMesBiens.main(null);
				break;
			default:
				break;
		}
	}

}