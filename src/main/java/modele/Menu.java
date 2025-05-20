package modele;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.DiagnosticDAO;
import ihm.PageAccueil;
import ihm.PageBaux;
import ihm.PageMesBiens;
import ihm.PageNotifications;

public class Menu implements ActionListener {
	private final JFrame frame;

	public Menu(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
        String texte_bouton = b.getText();
        
        // Fermer la fenêtre actuelle
        this.frame.dispose();
		int x=this.frame.getX();
		int y=this.frame.getY();
        
        // Créer et afficher la nouvelle fenêtre
        if (texte_bouton.equals("Accueil")) {
            PageAccueil page_accueil = new PageAccueil(x,y);
            page_accueil.getFrame().setVisible(true);
        } else if (texte_bouton.equals("Mes baux")) {
            PageBaux page_baux = new PageBaux(x,y);
            page_baux.getFrame().setVisible(true);
        } else if (texte_bouton.equals("Mes Biens")) {
            PageMesBiens page_mes_biens = new PageMesBiens(x,y);
            page_mes_biens.getFrame().setVisible(true);
        } else if (texte_bouton.contains("Notifications")) {
            new PageNotifications(x,y);
        }
	}

	public Integer getNbNotifs() throws DAOException {
		DiagnosticDAO diagnosctic_DAO= new DiagnosticDAO();
		BailDAO bail_DAO =new BailDAO();
		return diagnosctic_DAO.readDiagPerimes().size()+bail_DAO.getBauxNouvelICC().size();
	}

	/**
	 * Change la taille de l'image et de la police d'écriture des boutons de
	 * l'entête en fonction de la taille de la frame donnée en argument
	 * @param b_baux bouton vers la page Mes baux
	 * @param b_accueil bouton vers la page accueil
	 * @param  b_biens bouton vers la page Mes biens
	 * @param b_notifs bouton vers la page Notifications
	 * @param logo image de l'application
	 * @param frame JFrame pour récupérer sa taille
	 **/
	private ComponentListener addResize(JButton b_baux, JButton b_accueil, JButton b_biens,JButton b_notifs ,JLabel logo, JFrame frame){
		ComponentListener  compnent_listener = new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
						ResizedImage res = new ResizedImage();
						ResizedImage.resizeImage("logo+nom.png", frame,
								logo, 3, 8);
						int frameWidth = frame.getWidth();
						int frameHeight = frame.getHeight();

						int newFontSize = Math.min(frameWidth, frameHeight) / 30;

						// Appliquer la nouvelle police au bouton
						Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
						b_notifs.setFont(resizedFont);
						b_baux.setFont(resizedFont);
						b_accueil.setFont(resizedFont);
						b_biens.setFont(resizedFont);
					}

					@Override
					public void componentMoved(ComponentEvent e) {

					}

					@Override
					public void componentShown(ComponentEvent e) {

					}

					@Override
					public void componentHidden(ComponentEvent e) {

					}

		};
		return  compnent_listener;

    }
	/**
	 * Créer le menu pour se déplacer dans l'application (boutons, image)
	 * ajoute tous les actionListener pour que les boutons soient fonctionnels
	**/
	public void creerEntete(){
		JPanel entete = new JPanel();
		this.frame.getContentPane().add(entete, BorderLayout.NORTH);
		entete.setLayout(new BorderLayout(0, 0));
		this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

		entete.setBackground(Charte.ENTETE.getCouleur());
		entete.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel logo = new JLabel("");
		entete.add(logo, BorderLayout.WEST);

		JPanel menu_bouttons = new JPanel();

		entete.add(menu_bouttons, BorderLayout.CENTER);
		menu_bouttons.setLayout(new GridLayout(0, 4, 0, 0));
		menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(Charte.ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);
		b_accueil.addActionListener(this);

		JButton b_baux = new JButton("Mes baux");
		b_baux.setBorderPainted(false);
		b_baux.setBackground(Charte.ENTETE.getCouleur());
		b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_baux);
		b_baux.addActionListener(this);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(Charte.ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);
		b_biens.addActionListener(this);

		JButton b_notifs = null;
		try {
			b_notifs = new JButton("Notifications ("+this.getNbNotifs()+")");
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}
		b_notifs.setBorderPainted(false);
		b_notifs.setBackground(Charte.ENTETE.getCouleur());
		b_notifs.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_notifs);
		b_notifs.addActionListener(this);

		this.frame.addComponentListener(this.addResize(b_baux,b_accueil,b_biens,b_notifs,logo,frame));
	}
}