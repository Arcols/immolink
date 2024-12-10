package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import modele.PageAccueil;
import modele.PageBaux;
import modele.PageNouveauBienImmobilier;
import modele.PageNouveauBienImmobilier;
import modele.PageNouveauLocataire;
import modele.PageProfil;

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
			case "Profil":
				this.frame.dispose();
				PageProfil pageProfil = new PageProfil();
				pageProfil.main(null);
				break;
			case "Mes baux":
				this.frame.dispose();
				PageBaux pageBaux = new PageBaux();
				pageBaux.main(null);
				break;
			case "Locataires":
				this.frame.dispose();
				PageNouveauLocataire PageNouveauLocataire = new PageNouveauLocataire();
				PageNouveauLocataire.main(null);
				break;
			case "Mes Biens":
				this.frame.dispose();
				PageNouveauBienImmobilier pageNouveauBienImmobilier = new PageNouveauBienImmobilier();
				pageNouveauBienImmobilier.main(null);
				break;
			default:
				break;
		}
	}

}