package modele;

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
import javax.swing.border.LineBorder;

import ihm.Charte;
import ihm.Menu;
import ihm.ResizedImage;
import javax.swing.JCheckBox;

public class PageBaux {

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
					PageBaux window = new PageBaux();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Create the application.
	 */
	public PageBaux() {
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

		Menu m = new Menu(this.frame);

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(Charte.ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);
		b_accueil.addActionListener(m);

		JButton b_profil = new JButton("Profil");
		b_profil.setBorderPainted(false);
		b_profil.setBackground(Charte.ENTETE.getCouleur());
		b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_profil);
		menu_bouttons.add(b_profil);
		b_profil.addActionListener(m);

		JButton b_baux = new JButton("Mes baux");
		b_baux.setBorderPainted(false);
		b_baux.setBackground(Charte.ENTETE.getCouleur());
		b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_baux);
		menu_bouttons.add(b_baux);

		JButton b_loca = new JButton("Locataires");
		b_loca.setBorderPainted(false);
		b_loca.setBackground(Charte.ENTETE.getCouleur());
		b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_loca);
		menu_bouttons.add(b_loca);
		b_loca.addActionListener(m);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(Charte.ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);
		menu_bouttons.add(b_biens);
		
		b_biens.addActionListener(m);

		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageBaux.this.frame, PageBaux.this.logo, 3,
						8);
				int frameWidth = PageBaux.this.frame.getWidth();
				int frameHeight = PageBaux.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_loca.setFont(resizedFont);
				b_baux.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_profil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});

	}
}
