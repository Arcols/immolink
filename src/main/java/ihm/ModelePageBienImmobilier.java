package ihm;

import classes.Batiment;
import classes.Diagnostic;
import classes.Garage;
import classes.Logement;
import enumeration.TypeLogement;
import modele.PageBienImmobilier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ModelePageBienImmobilier {

	private PageBienImmobilier pageBienImmobilier;
	private static double SURFACE_MINIMALE = 9;

	public ModelePageBienImmobilier(PageBienImmobilier pageBienImmobilier) {
		this.pageBienImmobilier = pageBienImmobilier;
	}

	public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
		return e -> {
			String selectedVille = (String) this.pageBienImmobilier.getChoix_ville().getSelectedItem();
			if (!mapVillesAdresses.containsKey(selectedVille)) {
				this.pageBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel());
			} else {
				this.pageBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
			}
		};
	}

	public ActionListener getCheckFieldsActionListener() {
		return e -> pageBienImmobilier.checkFields();
	}

	public ActionListener getValidateActionListener() {
		return e -> {
			TypeLogement selectedType = TypeLogement.values()[pageBienImmobilier.getChoix_type_de_bien().getSelectedIndex()];

			switch (selectedType) {
				case APPARTEMENT:
					Boolean bool = false;
					try {
						if (pageBienImmobilier.getCheck_garage().isSelected()) {
							bool = true;
							new Garage(pageBienImmobilier.getChoix_num_fiscal().getText(),
									(String) pageBienImmobilier.getChoix_ville().getSelectedItem(),
									(String) pageBienImmobilier.getChoix_adresse().getSelectedItem(),
									pageBienImmobilier.getChoix_complement_adresse().getText());
						}
						new Logement((Integer) pageBienImmobilier.getChoix_nb_piece().getValue(),
								((Double) pageBienImmobilier.getChoix_surface().getValue() + this.SURFACE_MINIMALE),
								pageBienImmobilier.getChoix_num_fiscal().getText(),
								(String) pageBienImmobilier.getChoix_ville().getSelectedItem(),
								(String) pageBienImmobilier.getChoix_adresse().getSelectedItem(),
								pageBienImmobilier.getChoix_complement_adresse().getText(),
								pageBienImmobilier.getListe_diagnostic()
								);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case BATIMENT:
					try {
						new Batiment(pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
								(String) pageNouveauBienImmobilier.getTexte_ville().getText(),
								(String) pageNouveauBienImmobilier.getTexte_adresse().getText());
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
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.",
								"Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
				case GARAGE:
					if (pageBienImmobilier.getCheck_garage().isSelected()) {
                        try {
                            new Garage(pageBienImmobilier.getChoix_num_fiscal().getText(),
                                    (String) pageBienImmobilier.getChoix_ville().getSelectedItem(),
                                    (String) pageBienImmobilier.getChoix_adresse().getSelectedItem(),
                                    pageBienImmobilier.getChoix_complement_adresse().getText());
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
				pageBienImmobilier.checkFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pageBienImmobilier.checkFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pageBienImmobilier.checkFields();
			}
		};
	}

	public ActionListener getChoixTypeBienListener() {
		return e -> {
			String selectedType = (String) this.pageBienImmobilier.getChoix_type_de_bien().getSelectedItem();
			boolean isAppartement = "Appartement".equals(selectedType);
			boolean isBatiment = "Bâtiment".equals(selectedType);

			// Gérer la visibilité des composants
			this.pageBienImmobilier.getDiagnostics().setVisible(isAppartement);
			this.pageBienImmobilier.getTableau_diagnostic().setVisible(isAppartement);
			this.pageBienImmobilier.getSurface().setVisible(isAppartement);
			this.pageBienImmobilier.getChoix_surface().setVisible(isAppartement);
			this.pageBienImmobilier.getNombre_piece().setVisible(isAppartement);
			this.pageBienImmobilier.getChoix_nb_piece().setVisible(isAppartement);
			this.pageBienImmobilier.getComplement_adresse().setVisible(!isBatiment);
			this.pageBienImmobilier.getChoix_complement_adresse().setVisible(!isBatiment);
			this.pageBienImmobilier.getCheck_garage().setVisible(isAppartement);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (isBatiment) {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getChoix_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getTexte_ville(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getTexte_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getChoix_ville(), gbc);
			}

			// Adresse
			if (isBatiment) {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getChoix_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getTexte_adresse(), gbc);
			} else {
				this.pageBienImmobilier.getPanel_caracteristique().remove(this.pageBienImmobilier.getTexte_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageBienImmobilier.getPanel_caracteristique().add(this.pageBienImmobilier.getChoix_adresse(), gbc);
			}

			// Rafraîchir l'interface
			this.pageBienImmobilier.getPanel_caracteristique().revalidate();
			this.pageBienImmobilier.getPanel_caracteristique().repaint();
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
					this.pageBienImmobilier.getListe_diagnostic()
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
