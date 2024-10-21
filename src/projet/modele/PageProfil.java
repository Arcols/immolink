package projet.modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import projet.ihm.Charte;
import projet.ihm.ResizedImage;

public class PageProfil {

	private JFrame frame;
	private JLabel logo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageProfil window = new PageProfil();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PageProfil() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initialisation du JFrame
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 750, 400);
		this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel d'entête pour le logo et le nom de l'appli
		JPanel entete = new JPanel();
		this.frame.getContentPane().add(entete, BorderLayout.NORTH);
		entete.setLayout(new BorderLayout(0, 0));

		entete.setBackground(Charte.ENTETE.getCouleur());
		entete.setBorder(new LineBorder(Color.BLACK, 2));
		// Label pour le logo (Image)
		this.logo = new JLabel("");
		entete.add(this.logo, BorderLayout.WEST);

		JPanel menu_bouttons = new JPanel();

		entete.add(menu_bouttons, BorderLayout.CENTER);
		menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
		menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(Charte.ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);

		JButton b_profil = new JButton("Profil");
		b_profil.setBorderPainted(false);
		b_profil.setBackground(Charte.ENTETE.getCouleur());
		b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_profil);
		menu_bouttons.add(b_profil);

		JButton b_bails = new JButton("Mes bails");
		b_bails.setBorderPainted(false);
		b_bails.setBackground(Charte.ENTETE.getCouleur());
		b_bails.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_bails);
		menu_bouttons.add(b_bails);

		JButton b_loca = new JButton("Locataires");
		b_loca.setBorderPainted(false);
		b_loca.setBackground(Charte.ENTETE.getCouleur());
		b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_loca);
		menu_bouttons.add(b_loca);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(Charte.ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);
		menu_bouttons.add(b_biens);

		JLabel titrepage_1 = new JLabel("Mes bails");
		titrepage_1.setVerticalAlignment(SwingConstants.TOP);
		titrepage_1.setHorizontalAlignment(SwingConstants.CENTER);
		this.frame.getContentPane().add(titrepage_1, BorderLayout.CENTER);

		JPanel panel_gauche = new JPanel();
		this.frame.getContentPane().add(panel_gauche, BorderLayout.WEST);
		panel_gauche.setLayout(new BorderLayout(0, 0));

		JPanel panel_profil = new JPanel();
		panel_profil.setBackground(new Color(248, 235, 224));
		panel_gauche.add(panel_profil, BorderLayout.NORTH);
		panel_profil.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel imgprofil = new JLabel("");
		imgprofil.setVerticalAlignment(SwingConstants.TOP);
		imgprofil.setHorizontalAlignment(SwingConstants.LEFT);
		panel_profil.add(imgprofil);

		JLabel nom_proprio = new JLabel("Larry Bambelle");
		panel_profil.add(nom_proprio);

		JPanel panel_contact = new JPanel();
		panel_gauche.add(panel_contact, BorderLayout.CENTER);
		panel_contact.setLayout(new BorderLayout(0, 0));
		panel_contact.setBackground(Charte.FOND.getCouleur());

		JPanel sous_panel_contact = new JPanel();
		panel_contact.add(sous_panel_contact, BorderLayout.NORTH);
		sous_panel_contact.setLayout(new GridLayout(0, 1, 0, 0));
		sous_panel_contact.setBackground(Charte.FOND.getCouleur());

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblEmail.setToolTipText("");
		sous_panel_contact.add(lblEmail);

		JLabel exemple_mail = new JLabel("· exemple@mail.fr");
		sous_panel_contact.add(exemple_mail);

		JLabel lblTelephone = new JLabel("Télélphone");
		lblTelephone.setFont(new Font("Tahoma", Font.BOLD, 10));
		sous_panel_contact.add(lblTelephone);

		JLabel exemple_tel = new JLabel("· 0678954236");
		exemple_tel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sous_panel_contact.add(exemple_tel);

		JButton btnNewButton = new JButton("New button");
		panel_gauche.add(btnNewButton, BorderLayout.SOUTH);
		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageProfil.this.frame, PageProfil.this.logo,
						3, 8);
				int frameWidth = PageProfil.this.frame.getWidth();
				int frameHeight = PageProfil.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_loca.setFont(resizedFont);
				b_bails.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_profil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});

	}
}
