package modele;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.GarageDAO;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import classes.Diagnostic;
import classes.Garage;
import enumeration.TypeLogement;
import ihm.PageMesBiens;
import ihm.PageMonBien;
import ihm.PageNouveauTravaux;
import ihm.PageUnTravail;
import ihm.PopUpLieGarageMonBien;
import ihm.PopUpLierGarageAuBien;

public class ModelePageMonBien {

    private PageMonBien page_mon_bien;

    public ModelePageMonBien(PageMonBien page_mon_bien){
        this.page_mon_bien = page_mon_bien;
    }

    public static DefaultTableModel loadDataTravauxToTable(Integer id, TypeLogement type_logement) throws SQLException, DAOException {
        // Liste des colonnes
        String[] nom_colonne = { "Devis","Montant", "Nature", "Type" };

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(nom_colonne, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        List<Devis> devis;
        if(type_logement.estBienLouable()){
            DAO.BienLouableDAO bien_louable_DAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bien_louable = bien_louable_DAO.readId(id);

            // Récupération des Travaux
            DevisDAO devis_DAO = new DevisDAO();
            devis = devis_DAO.getAllDevisFromABien(bien_louable.getNumeroFiscal(),bien_louable_DAO.getTypeFromId(id));
        }
        else{
            BatimentDAO batiment_DAO = new DAO.jdbc.BatimentDAO();
            Batiment batiment = batiment_DAO.readId(id);

            // Récupération des Travaux
            DevisDAO devis_DAO = new DevisDAO();
            devis = devis_DAO.getAllDevisFromABien(batiment.getNumeroFiscal(),TypeLogement.BATIMENT);
        }
        // Remplissage du modèle avec les données des locataires
        for (Devis d : devis) {
            Object[] rowData = {
                    d.getNumDevis(),
                    d.getMontantTravaux(),
                    d.getNature(),
                    d.getType()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }

    public void chargerDonneesBien() throws DAOException {
        try {
            if (page_mon_bien.getType_logement() == TypeLogement.BATIMENT) {
                BatimentDAO batiment_DAO = new DAO.jdbc.BatimentDAO();
                Batiment batiment = batiment_DAO.readId(page_mon_bien.getId_bien());
                if (batiment != null) {
                    // Mise à jour des labels avec les informations du bien
                    page_mon_bien.getAffichageNumeroFiscal(batiment.getNumeroFiscal());
                    page_mon_bien.getAffichageVille().setText(batiment.getVille());
                    page_mon_bien.getAffichageAdresse().setText(batiment.getAdresse());
                    page_mon_bien.getAffichageComplement().setText("");
                    DevisDAO devisDAO = new DevisDAO();
                    page_mon_bien.getAffichageCoutTravaux().setText(devisDAO.getMontantTotalTravaux(batiment.getNumeroFiscal(), page_mon_bien.getType_logement()) + " €");
                }
            } else {
                // Récupération des informations du bien via le DAO
                BienLouableDAO bien_louable_DAO = new DAO.jdbc.BienLouableDAO();
                BienLouable bien_louable = bien_louable_DAO.readId(page_mon_bien.getId_bien());
                if (bien_louable != null) {
                    // Mise à jour des labels avec les informations du bien
                    page_mon_bien.getAffichageNumeroFiscal(bien_louable.getNumeroFiscal());
                    page_mon_bien.getAffichageVille().setText(bien_louable.getVille());
                    page_mon_bien.getAffichageAdresse().setText(bien_louable.getAdresse());
                    page_mon_bien.getAffichageComplement().setText(bien_louable.getComplementAdresse());
                    DevisDAO devisDAO = new DevisDAO();
                    page_mon_bien.getAffichageCoutTravaux().setText(devisDAO.getMontantTotalTravaux(bien_louable.getNumeroFiscal(), page_mon_bien.getType_logement()) + " €");
                }
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }

    public ActionListener openDiag(String reference) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BienLouableDAO bien_louable_DAO = new BienLouableDAO();
                    String num_fisc = bien_louable_DAO.readId(page_mon_bien.getId_bien()).getNumeroFiscal();
                    DiagnosticDAO diagnostic_DAO = new DiagnosticDAO();
                    Diagnostic diag = diagnostic_DAO.read(num_fisc, refDiagnosticSansDate(reference));
                    if (diag != null) {
                        diag.ouvrirPdf();
                    }
                } catch (DAOException e1) {
                    e1.printStackTrace();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public ActionListener ouvrirPageNouveauTravaux() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    page_mon_bien.getFrame().dispose();
                    int x=page_mon_bien.getFrame().getX();
                    int y=page_mon_bien.getFrame().getY();

                    new PageNouveauTravaux(page_mon_bien.getId_bien(), page_mon_bien.getType_logement(),x,y);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
    }

    public String refDiagnosticSansDate(String ref) {
        String ref_sans_date = "";
        while (ref.length() > 0 && ref.charAt(0) != '-') {
            ref_sans_date += ref.charAt(0);
            ref = ref.substring(1);
        }
        return ref_sans_date;
    }

    public ActionListener quitterPage() {
        return e -> {
            page_mon_bien.getFrame().dispose();
            int x=page_mon_bien.getFrame().getX();
            int y=page_mon_bien.getFrame().getY();

            new PageMesBiens(x,y);
        };
    }

    public ActionListener delierGarage() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BienLouableDAO bien_louable_DAO = new BienLouableDAO();
                    bien_louable_DAO.delierGarage(page_mon_bien.getId_bien());
                    page_mon_bien.getFrame().dispose();
                    int x=page_mon_bien.getFrame().getX();
                    int y=page_mon_bien.getFrame().getY();
                    page_mon_bien = new PageMonBien(page_mon_bien.getId_bien(), page_mon_bien.getType_logement(),x,y);

                    new PageMesBiens(x,y);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public ActionListener getTelechargerPDFButton(String diagnostic) {
        return e -> {
            String diag_sans_date = refDiagnosticSansDate(diagnostic);
            // Créer un JFileChooser pour permettre de sélectionner un fichier
            JFileChooser choix_fichier = new JFileChooser();
            choix_fichier.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");
            Date date;
            // Ouvrir le dialogue de sélection de fichier
            int valeur_retour = choix_fichier.showOpenDialog(null);

            if (valeur_retour == JFileChooser.APPROVE_OPTION) {
                // Obtenir le fichier sélectionné
                File fichier_selectionne = choix_fichier.getSelectedFile();
                date = setDateDiag();
                JDialog dialog = new JDialog(page_mon_bien.getFrame(), "Modifier le diagnostic", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir modifier votre diagnostic ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton bouton_valider = new JButton("Valider");
                bouton_valider.setBounds(90, 100, 100, 30);
                dialog.add(bouton_valider);
                Date date_finale = date;
                JButton bouton_annuler = new JButton("Annuler");
                bouton_annuler.setBounds(210, 100, 100, 30);
                dialog.add(bouton_annuler);
                bouton_annuler.addActionListener(event -> dialog.dispose());
                bouton_valider.addActionListener(event -> {
                    try {
                        new DiagnosticDAO().updateDate(new Diagnostic(diag_sans_date, fichier_selectionne.getAbsolutePath(), date_finale), new BienLouableDAO().readId(page_mon_bien.getId_bien()).getNumeroFiscal(), date_finale);
                        JOptionPane.showMessageDialog(dialog,
                                "La date de péremption du diagnostic a été mis à jour à " + date_finale + ".",
                                "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (DAOException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    dialog.dispose();
                });
                dialog.setLocationRelativeTo(page_mon_bien.getFrame());
                dialog.setVisible(true);
                JButton btn = (JButton) e.getSource();
                if (!btn.getText().contains("\u2705")) {
                    btn.setText(btn.getText() + " \u2705");
                }
                try {
                    page_mon_bien.getFrame().dispose();
                    int x=page_mon_bien.getFrame().getX();
                    int y=page_mon_bien.getFrame().getY();
                    page_mon_bien = new PageMonBien(page_mon_bien.getId_bien(), page_mon_bien.getType_logement(),x,y);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public Date setDateDiag() {
        AtomicReference<Date> date = new AtomicReference<>();
        JDialog dialog = new JDialog((Frame) null, "Saisir la date de péremption du diagnostic ", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Date de péremption du diagnostic :");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JDateChooser seuil = new JDateChooser();
        seuil.setPreferredSize(new Dimension(100, 22));
        seuil.setBounds(220, 30, 100, 25);

        dialog.add(seuil);

        JButton bouton_valider = new JButton("Valider");
        bouton_valider.setBounds(150, 100, 100, 30);
        dialog.add(bouton_valider);

        bouton_valider.addActionListener(event -> {
            try {
                java.sql.Date sqlDate = new java.sql.Date(seuil.getDate().getTime());
                date.set(sqlDate);
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

    public MouseAdapter doubleClick(JTable table_travaux) {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Vérifier s'il s'agit d'un double-clic
                if (evt.getClickCount() == 2) {
                    // Obtenir l'index de la ligne sélectionnée
                    int selectedRow = table_travaux.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer l'ID du travail correspondant depuis le modèle de table
                        String numDevis = (String) table_travaux.getValueAt(selectedRow, 0);
                        int id_travail = 0;
                        try {
                            id_travail = new DAO.jdbc.DevisDAO().getId(new DAO.jdbc.DevisDAO().read(numDevis));
                        } catch (DAOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            // Ouvrir la page correspondante
                            page_mon_bien.getFrame().dispose();
                            int x=page_mon_bien.getFrame().getX();
                            int y=page_mon_bien.getFrame().getY();

                            new PageUnTravail(page_mon_bien.getId_bien(), page_mon_bien.getType_logement(), id_travail,x,y);
                        } catch (DAOException e) {
                            JOptionPane.showMessageDialog(page_mon_bien.getFrame(),
                                    "Erreur lors de l'ouverture de la page du travail : " + e.getMessage(),
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public ActionListener deleteBienLouable() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(page_mon_bien.getFrame(), "Suppression du bien", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir supprimer votre bien louable ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton bouton_valider = new JButton("Valider");
                bouton_valider.setBounds(90, 100, 100, 30);
                dialog.add(bouton_valider);
                JButton bouton_annuler = new JButton("Annuler");
                bouton_annuler.setBounds(210, 100, 100, 30);
                dialog.add(bouton_annuler);
                bouton_annuler.addActionListener(event -> dialog.dispose());
                bouton_valider.addActionListener(event -> {
                    try {
                        switch (page_mon_bien.getType_logement()) {
                            case BATIMENT:
                                new BatimentDAO().delete(new BatimentDAO().readId(page_mon_bien.getId_bien()).getNumeroFiscal());
                                break;
                            case APPARTEMENT:
                            case MAISON:
                                new BienLouableDAO().delete(page_mon_bien.getId_bien());
                                break;
                            case GARAGE_ASSOCIE:
                            case GARAGE_PAS_ASSOCIE:
                                new GarageDAO().delete(page_mon_bien.getId_bien(), page_mon_bien.getType_logement());
                                break;
                        }
                        JOptionPane.showMessageDialog(dialog,
                                "Votre bien a été supprimé",
                                "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (DAOException ex) {
                        throw new RuntimeException(ex);
                    }
                    dialog.dispose();
                    page_mon_bien.getFrame().dispose();
                    int x=page_mon_bien.getFrame().getX();
                    int y=page_mon_bien.getFrame().getY();

                    new PageMesBiens(x,y);
                });
                dialog.setLocationRelativeTo(page_mon_bien.getFrame());
                dialog.setVisible(true);
            }
        };
    }

    public boolean isMaisonOuAppartement() {
        return (new BienLouableDAO().getTypeFromId(page_mon_bien.getId_bien()).equals(TypeLogement.MAISON)
                || new BienLouableDAO().getTypeFromId(page_mon_bien.getId_bien()).equals(TypeLogement.APPARTEMENT));
    }

    public boolean aUnGarage() {
        return new BienLouableDAO().haveGarage(page_mon_bien.getId_bien());
    }

    public Garage getGarage() throws DAOException {
        return new GarageDAO().read(new GarageDAO().readIdGarageFromBien(page_mon_bien.getId_bien()));
    }

    public String getAddresse() throws DAOException {
        if(page_mon_bien.getType_logement().equals(TypeLogement.BATIMENT)){
            return  " - " + new DAO.jdbc.BatimentDAO().readId(page_mon_bien.getId_bien()).getAdresse();
        }
        else{
            return  " - " + new BienLouableDAO().readId(page_mon_bien.getId_bien()).getAdresse();
        }
    }

    public List<Diagnostic> getListDiagnostics() throws DAOException {
        return new DiagnosticDAO().readAllDiag(page_mon_bien.getId_bien());
    }

    public WindowListener fermerFenetre(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform on application close
                performCloseAction();
            }
        };
    }
    private void performCloseAction() {
        ConnectionDB.destroy(); // fermeture de la connection
        page_mon_bien.getFrame().dispose();
    }

    public void showGaragePopup() {
        PopUpLieGarageMonBien popup = new PopUpLieGarageMonBien(page_mon_bien, page_mon_bien.getId_bien());
        popup.getFrame().setVisible(true);
    }

    public void showLierGarageAuBienPopup() {
        PopUpLierGarageAuBien popup = new PopUpLierGarageAuBien(page_mon_bien, page_mon_bien.getId_bien());
        popup.getFrame().setVisible(true);
    }

   public ActionListener ajouterGarage(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGaragePopup();
            }
        };
   }
   public ActionListener lierGarage(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLierGarageAuBienPopup();
            }
        };
   }
}
