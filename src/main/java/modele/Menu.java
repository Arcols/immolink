package modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


import DAO.jdbc.BailDAO;
import ihm.*;

import DAO.DAOException;
import DAO.jdbc.DiagnosticDAO;

public class Menu implements ActionListener {
	private JFrame frame;

	public Menu(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
        String textButton = b.getText();
        if (textButton=="Accueil") {
			this.frame.dispose();
			PageAccueil pageAccueil = new PageAccueil();
			pageAccueil.main(null);
		} else if (textButton=="Mes baux") {
			this.frame.dispose();
			PageBaux pageBaux = new PageBaux();
			pageBaux.main(null);
		} else if (textButton=="Mes Biens") {
			this.frame.dispose();
			PageMesBiens pageMesBiens = new PageMesBiens();
			PageMesBiens.main(null);
		} else if (textButton.contains("Notifications")) {
			this.frame.dispose();
			PageNotifications pageNotifications = new PageNotifications();
			PageNotifications.main(null);
		}
	}

	public Integer getNbNotifs() throws DAOException {
		DiagnosticDAO diagnosticDAO= new DiagnosticDAO();
		BailDAO bailDAO =new BailDAO();
		return diagnosticDAO.readDiagPerimes().size()+bailDAO.getBauxNouvelICC().size();
	}

}