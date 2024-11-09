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
import javax.swing.border.LineBorder;

import projet.ihm.Charte;
import projet.ihm.ResizedImage;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class PageBienImmobilier {

	private JFrame frame;
	private JLabel logo;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageBienImmobilier window = new PageBienImmobilier();
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
	public PageBienImmobilier() {
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
		
		JPanel body = new JPanel();
		frame.getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new BorderLayout(0, 0));
		
		JPanel titre = new JPanel();
		FlowLayout fl_titre = (FlowLayout) titre.getLayout();
		body.add(titre, BorderLayout.NORTH);
		
		JLabel titrePage = new JLabel("Mon Bien Immobilier");
		titrePage.setAlignmentY(0.0f);
		titrePage.setAlignmentX(0.5f);
		titre.add(titrePage);
		
		JPanel panel_1 = new JPanel();
		body.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{184, 184, 0};
		gbl_panel_2.rowHeights = new int[]{315, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel_3 = new JPanel();
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_3.add(lblNewLabel);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_2.add(panel_3, gbc_panel_3);
		
		JPanel panel_4 = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_4.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		panel_4.add(textField_1);
		textField_1.setColumns(1);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_2.add(panel_4, gbc_panel_4);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		
		table = new JTable();
		panel.add(table);
		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageBienImmobilier.this.frame,
						PageBienImmobilier.this.logo, 3, 8);
				int frameWidth = PageBienImmobilier.this.frame.getWidth();
				int frameHeight = PageBienImmobilier.this.frame.getHeight();

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
