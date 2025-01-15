package modele;

import DAO.DAOException;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.GarageDAO;
import DAO.jdbc.LogementDAO;
import DAO.jdbc.BatimentDAO;
import classes.*;
import com.toedter.calendar.JDateChooser;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import ihm.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ModelePageNouveauBienImmobilier {

	private PageNouveauBienImmobilier pageNouveauBienImmobilier;

	public ModelePageNouveauBienImmobilier(PageNouveauBienImmobilier pageNouveauBienImmobilier) {
		this.pageNouveauBienImmobilier = pageNouveauBienImmobilier;
	}

	public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
		return e -> {
			String selectedVille = (String) this.pageNouveauBienImmobilier.getChoix_ville().getSelectedItem();
			if (!mapVillesAdresses.containsKey(selectedVille)) {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel());
			} else {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(
						new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
			}
		};
	}

	public ActionListener getCheckFieldsActionListener() {
		return e -> pageNouveauBienImmobilier.checkFields();
	}

	public ActionListener getValidateActionListener() {
		return e -> {
			String stringTypeBien = (String) pageNouveauBienImmobilier.getChoix_type_de_bien().getSelectedItem();
			TypeLogement selectedType = TypeLogement.fromString(stringTypeBien);
			try {
				switch (selectedType) {
					case APPARTEMENT:
						handleAppartementCreation(e);
						break;
					case BATIMENT:
						handleBatimentCreation(e);
						break;
					case GARAGE_PAS_ASSOCIE:
						handleGarageCreation(e);
						break;
					case MAISON:
						handleMaisonCreation(e);
						break;
				}
			} catch (Exception ex) {
				if (ex.getMessage().contains("numero_fiscal")){
					JOptionPane.showMessageDialog(null, "Le numéro fiscal est déjà utilisé pour un autre bien louable.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else{
					JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du bien immobilier.\n "+ex.getMessage(),
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	private void addDiagnostics(String numero_fiscal) {
		DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
		for (Diagnostic d : pageNouveauBienImmobilier.getMap_diagnostic().values()) {
			try {
				if(d == null){
					continue;
				}
				diagnosticDAO.create(d, numero_fiscal);
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Problème lors de l'ajout d'un de vos diagnostics", "Erreur",
						JOptionPane.INFORMATION_MESSAGE);
				throw new RuntimeException();
			}
		}
		JOptionPane.showMessageDialog(null, "Vos diagnostics ont été ajoutés !", "Succès",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void handleAppartementCreation(ActionEvent e) throws Exception {
		if(isGarageLinkedToSameFiscalNumber()){
			return;
		}
		Logement logement = new Logement(
				(Integer) pageNouveauBienImmobilier.getChoix_nb_piece().getValue(),
				((Double) pageNouveauBienImmobilier.getChoix_surface().getValue()),
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
				(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
				pageNouveauBienImmobilier.getChoix_complement_adresse().getText(),
				pageNouveauBienImmobilier.getListe_diagnostic(),
				TypeLogement.APPARTEMENT);
		LogementDAO logementDAO = new LogementDAO();
		logementDAO.create(logement,TypeLogement.APPARTEMENT);
		addDiagnostics(logement.getNumeroFiscal());
		if (!(pageNouveauBienImmobilier.getGarageLie()).equals(new Garage("            ", "", "", "", TypeLogement.NONE))) {
			logementDAO.lierUnGarageAuBienLouable(logement, pageNouveauBienImmobilier.getGarageLie(), TypeLogement.APPARTEMENT);
			JOptionPane.showMessageDialog(null, "L'appartement ainsi que son garage ont été ajoutés !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "L'appartement a été ajouté !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		}
		refreshPage(e);
	}

	private void handleMaisonCreation(ActionEvent e) throws DAOException {
		if(isGarageLinkedToSameFiscalNumber()){
			return;
		}
		Logement logement = new Logement(
				(Integer) pageNouveauBienImmobilier.getChoix_nb_piece().getValue(),
				((Double) pageNouveauBienImmobilier.getChoix_surface().getValue()),
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
				(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
				pageNouveauBienImmobilier.getChoix_complement_adresse().getText(),
				pageNouveauBienImmobilier.getListe_diagnostic(),
				TypeLogement.MAISON);
		LogementDAO logementDAO = new LogementDAO();
		logementDAO.create(logement,TypeLogement.MAISON);
		addDiagnostics(logement.getNumeroFiscal());
		if (!(pageNouveauBienImmobilier.getGarageLie()).equals(new Garage("            ", "", "", "", TypeLogement.NONE))) {
			logementDAO.lierUnGarageAuBienLouable(logement, pageNouveauBienImmobilier.getGarageLie(), TypeLogement.MAISON);
			JOptionPane.showMessageDialog(null, "La maison ainsi que son garage ont été ajoutés !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "La maison a été ajouté !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		}
		refreshPage(e);
	}

	private void handleBatimentCreation(ActionEvent e) throws Exception {
		Batiment batiment = new Batiment(
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				pageNouveauBienImmobilier.getTexte_ville().getText(),
				pageNouveauBienImmobilier.getTexte_adresse().getText(),
				pageNouveauBienImmobilier.getTexte_code_postal().getText());
		BatimentDAO batimentDAO = new BatimentDAO();
		batimentDAO.create(batiment);
		JOptionPane.showMessageDialog(null, "Le Bâtiment a été ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void handleGarageCreation(ActionEvent e) throws Exception {
		Garage garage = new Garage(
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
				(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
				pageNouveauBienImmobilier.getChoix_complement_adresse().getText(),
				TypeLogement.GARAGE_PAS_ASSOCIE);
		GarageDAO garageDAO = new GarageDAO();
		garageDAO.create(garage);
		JOptionPane.showMessageDialog(null, "Le Garage a été crée !", "Succès",
				JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void refreshPage(ActionEvent e) {
		JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
		ancienneFenetre.dispose();
		PageNouveauBienImmobilier nouvellePage = new PageNouveauBienImmobilier();
		nouvellePage.getFrame().setVisible(true);
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

			boolean isAppartement = "Appartement".equals(selectedType) || "Maison".equals(selectedType);
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
			this.pageNouveauBienImmobilier.getAddGarageButton().setVisible(isAppartement);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getChoix_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_ville(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getChoix_ville(), gbc);
			}

			// Adresse
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getChoix_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_adresse(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getChoix_adresse(), gbc);
			}

			// Code Postal
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
				gbc.gridx = 0;
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

	public ActionListener getTelechargerPDFButton(String diagnostic) {
		return e -> {
			// Créer un JFileChooser pour permettre de sélectionner un fichier
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");
			Date date = null;
			// Ouvrir le dialogue de sélection de fichier
			int returnValue = fileChooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// Obtenir le fichier sélectionné
				File selectedFile = fileChooser.getSelectedFile();
				try {
					if (diagnostic == NomsDiags.PERFORMANCE_ENERGETIQUE.getDescription()
							|| diagnostic == NomsDiags.ELECTRICITE.getDescription()
							|| diagnostic == NomsDiags.GAZ.getDescription()) {
						date = setDateDiag();
					}
					// Ajouter le diagnostic à la map
					NomsDiags diag = NomsDiags.fromDescription(diagnostic);
					this.pageNouveauBienImmobilier.getMap_diagnostic().put(diag.name(),
							new Diagnostic(diagnostic, selectedFile.getAbsolutePath(), date));
					if (isMapDiagnosticFull()) {
						pageNouveauBienImmobilier.checkFields();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JButton btn = (JButton) e.getSource();
				if (!btn.getText().contains("\u2705")) {
					btn.setText(btn.getText() + " \u2705");
				}
			}
		};
	}

	public boolean isMapDiagnosticFull() {
		for (Map.Entry<String, Diagnostic> entry : this.pageNouveauBienImmobilier.getMap_diagnostic().entrySet()) {
			if (!entry.getKey().equals("GAZ") && entry.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	public Date setDateDiag() {
		AtomicReference<Date> date = new AtomicReference<>();
		JDialog dialog = new JDialog((Frame) null, "Saisir la date de péremption du diagnostic ", true);
		dialog.setSize(400, 200);
		dialog.setLayout(null);

		JLabel label = new JLabel("Date de péremption du diagnostic :");
		label.setBounds(20, 30, 200, 25);
		dialog.add(label);

		JDateChooser seuilField = new JDateChooser();
		seuilField.setPreferredSize(new Dimension(100, 22));
		seuilField.setBounds(220, 30, 100, 25);

		dialog.add(seuilField);

		JButton validerButton = new JButton("Valider");
		validerButton.setBounds(150, 100, 100, 30);
		dialog.add(validerButton);

		validerButton.addActionListener(event -> {
			try {
				java.sql.Date sqlDate = new java.sql.Date(seuilField.getDate().getTime());
				date.set(sqlDate);
				JOptionPane.showMessageDialog(dialog,
						"La date de péremption du diagnostic a été mis à jour à " + date + ".",
						"Confirmation",
						JOptionPane.INFORMATION_MESSAGE);
				dialog.dispose();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(dialog,
						"Veuillez entrer une date valide sous le format yyyy-mm-dd.",
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return date.get();
	}

	public ActionListener quitterPage(){
		return e -> {
			pageNouveauBienImmobilier.getFrame().dispose();
			PageMesBiens pageMesBiens = new PageMesBiens();
			PageMesBiens.main(null);
		};
	}

	private boolean isGarageLinkedToSameFiscalNumber() {
		if (pageNouveauBienImmobilier.getGarageLie().getNumeroFiscal().equals(pageNouveauBienImmobilier.getChoix_num_fiscal().getText())) {
			JOptionPane.showMessageDialog(null, "Un garage ne peut pas être lié avec le même numéro fiscal qu'un bien louable", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

}
