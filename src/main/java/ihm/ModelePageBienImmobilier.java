package ihm;

import classes.Batiment;
import classes.Diagnostic;
import classes.Garage;
import classes.Logement;
import enumeration.TypeLogement;
import modele.PageNouveauBienImmobilier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ModelePageBienImmobilier {

	private PageNouveauBienImmobilier pageNouveauBienImmobilier;
	private static double SURFACE_MINIMALE = 9;

	public ModelePageBienImmobilier(PageBienImmobilier pageBienImmobilier) {
		this.pageBienImmobilier = pageBienImmobilier;
	}

	public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
		return e -> {
			String selectedVille = (String) this.pageNouveauBienImmobilier.getChoix_ville().getSelectedItem();
			if (!mapVillesAdresses.containsKey(selectedVille)) {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel());
			} else {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
			}
		};
	}

	public ActionListener getCheckFieldsActionListener() {
		return e -> pageNouveauBienImmobilier.checkFields();
	}

	public ActionListener getValidateActionListener() {
		return e -> {
			TypeLogement selectedType = TypeLogement.values()[pageNouveauBienImmobilier.getChoix_type_de_bien().getSelectedIndex()];

			switch (selectedType) {
				case APPARTEMENT:
					Boolean bool = false;
					try {
						if (pageNouveauBienImmobilier.getCheck_garage().isSelected()) {
							bool = true;
							new Garage(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
									(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
									(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
									pageNouveauBienImmobilier.getChoix_complement_adresse().getText());
						}
						new Logement((Integer) pageNouveauBienImmobilier.getChoix_nb_piece().getValue(),
								((Double) pageNouveauBienImmobilier.getChoix_surface().getValue() + this.SURFACE_MINIMALE),
								pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
								(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
								(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
								pageNouveauBienImmobilier.getChoix_complement_adresse().getText(),
								pageNouveauBienImmobilier.getListe_diagnostic(),
								pageNouveauBienImmobilier.getCheck_garage().isSelected());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case BATIMENT:
					new Batiment
							(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
							(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
							(String) pageNouveauBienImmobilier.getChoix_adresse()
									.getSelectedItem());
					try {
						new Batiment(pageBienImmobilier.getChoix_num_fiscal().getText(),
								(String) pageBienImmobilier.getTexte_ville().getText(),
								(String) pageBienImmobilier.getTexte_adresse().getText());
						JOptionPane.showMessageDialog(null, "Le bien a été ajouté !", "Succès",
								JOptionPane.INFORMATION_MESSAGE);
						// Fermer l'ancienne page
						JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
						ancienneFenetre.dispose();

						// Ouvrir une nouvelle instance de la même page
						PageBienImmobilier nouvellePage = new PageNouveauBienImmobilier(); // Remplacez par le
																							// constructeur de votre
																							// page
						nouvellePage.getFrame().setVisible(true);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.",
								"Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case BATIMENT:
<<<<<<< HEAD
					new Batiment(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
							(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
							(String) pageNouveauBienImmobilier.getChoix_adresse()
									.getSelectedItem());
=======
					try {
						new Batiment(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
								(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
								(String) pageNouveauBienImmobilier.getChoix_adresse()
										.getSelectedItem(),
								pageNouveauBienImmobilier.getTexte_code_postal().getText());
						JOptionPane.showMessageDialog(null, "Le bien a été ajouté !", "Succès",
								JOptionPane.INFORMATION_MESSAGE);
						// Fermer l'ancienne page
						JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
						ancienneFenetre.dispose();

						// Ouvrir une nouvelle instance de la même page
						PageNouveauBienImmobilier nouvellePage = new PageNouveauBienImmobilier(); // Remplacez par le
						// constructeur de votre
						// page
						nouvellePage.getFrame().setVisible(true);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.",
								"Erreur", JOptionPane.ERROR_MESSAGE);
					}
>>>>>>> 28a2249 (Modele update pour codepostal)
					break;
				case GARAGE:
					if (pageNouveauBienImmobilier.getCheck_garage().isSelected()) {
						try {
							new Garage(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
									(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
									(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
									pageNouveauBienImmobilier.getChoix_complement_adresse().getText());
						} catch (SQLException ex) {
							throw new RuntimeException(ex);
						}
					}
					break;
			}
		};
	}

	public DocumentListener getTextFieldDocumentListener() {
		return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}
		};
	}

	public ActionListener getChoixTypeBienListener() {
		return e -> {
			String selectedType = (String) this.pageNouveauBienImmobilier.getChoix_type_de_bien().getSelectedItem();
			boolean isAppartement = "Appartement".equals(selectedType);
			boolean isBatiment = "Bâtiment".equals(selectedType);

			// Gérer la visibilité des composants
			this.pageNouveauBienImmobilier.getDiagnostics().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getTableau_diagnostic().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getSurface().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getChoix_surface().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getNombre_piece().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getChoix_nb_piece().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getComplement_adresse().setVisible(!isBatiment);
			this.pageNouveauBienImmobilier.getChoix_complement_adresse().setVisible(!isBatiment);
			this.pageNouveauBienImmobilier.getCheck_garage().setVisible(isAppartement);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique().remove(this.pageNouveauBienImmobilier.getChoix_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique().add(this.pageNouveauBienImmobilier.getTexte_ville(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique().remove(this.pageNouveauBienImmobilier.getTexte_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique().add(this.pageNouveauBienImmobilier.getChoix_ville(), gbc);
			}

			// Adresse
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique().remove(this.pageNouveauBienImmobilier.getChoix_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique().add(this.pageNouveauBienImmobilier.getTexte_adresse(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique().remove(this.pageNouveauBienImmobilier.getTexte_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique().add(this.pageNouveauBienImmobilier.getChoix_adresse(), gbc);
			}

			if (isBatiment) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getTexte_code_postal(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getTexte_code_postal());

			}

			if (isBatiment) {
				gbc.gridx = 0;
				gbc.gridy = 4;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getCode_postalLabel(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getCode_postalLabel());

			}

			if (isBatiment) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getTexte_code_postal(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getTexte_code_postal());

			}

			if (isBatiment) {
				gbc.gridx = 0;
				gbc.gridy = 4;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getCode_postalLabel(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getCode_postalLabel());

			}

			if (isBatiment) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_code_postal(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_code_postal());
			}

			if (isBatiment) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getCode_postalLabel(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getCode_postalLabel());
			}


			// Rafraîchir l'interface
			this.pageNouveauBienImmobilier.getPanel_caracteristique().revalidate();
			this.pageNouveauBienImmobilier.getPanel_caracteristique().repaint();
		};
    }

	public ActionListener getTelechargerPDFButton(String diagnostic){
		return e -> {
			// Créer un JFileChooser pour permettre de sélectionner un fichier
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");

			// Ouvrir le dialogue de sélection de fichier
			int returnValue = fileChooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// Obtenir le fichier sélectionné
				File selectedFile = fileChooser.getSelectedFile();
				try {
					this.pageNouveauBienImmobilier.getListe_diagnostic()
							.add(new Diagnostic(diagnostic, fileChooser.getSelectedFile().getAbsolutePath()));
					System.out.println("Rajouté !");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } else {
				System.out.println("Aucun fichier sélectionné.");
			}
		};
	}
}
