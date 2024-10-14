package projet.modele;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PageAccueil {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageAccueil window = new PageAccueil();
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
	public PageAccueil() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		this.frame.getContentPane().add(scrollPane, BorderLayout.WEST);

		JPanel panel = new JPanel();
		this.frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("New button");
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("New button");
		panel.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("New button");
		panel.add(btnNewButton_4);

		JPanel entete = new JPanel();
		this.frame.getContentPane().add(entete, BorderLayout.NORTH);
		entete.setLayout(new BorderLayout(0, 0));

		JLabel immolink = new JLabel("");
		immolink.setIcon(new ImageIcon(PageAccueil.class.getResource("/ressources/images/nom_appli.png")));
		entete.add(immolink, BorderLayout.CENTER);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(PageAccueil.class.getResource("/ressources/images/logo_appli.png")));
		entete.add(logo, BorderLayout.WEST);
	}

}
