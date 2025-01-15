package modele;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.*;
import classes.*;
import com.toedter.calendar.JDateChooser;
import enumeration.TypeLogement;
import ihm.PageMesBiens;
import ihm.PageMonBien;
import ihm.PageNouveauTravaux;

public class ModelePageMonBien {

    private PageMonBien pageMonBien;

    public ModelePageMonBien(PageMonBien pageMonBien){
        this.pageMonBien = pageMonBien;
    }
    public static DefaultTableModel loadDataTravauxToTable(Integer id,TypeLogement typeLogement) throws SQLException, DAOException {
        // Liste des colonnes
        String[] columnNames = { "Devis","Montant", "Nature", "Type" };

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        List<Devis> devis = null;
        if(typeLogement.estBienLouable()){
            DAO.BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(id);

            // Récupération des Travaux
            DevisDAO devisDAO = new DevisDAO();
            devis = devisDAO.getAllDevisFromABien(bienLouable.getNumeroFiscal(),bienLouableDAO.getTypeFromId(id));
        }
        else{
            BatimentDAO batimentDAO = new DAO.jdbc.BatimentDAO();
            Batiment batiment = batimentDAO.readId(id);

            // Récupération des Travaux
            DevisDAO devisDAO = new DevisDAO();
            devis = devisDAO.getAllDevisFromABien(batiment.getNumeroFiscal(),TypeLogement.BATIMENT);
        }
        // Remplissage du modèle avec les données des locataires
        for (Devis devi : devis) {
            Object[] rowData = {
                    devi.getNumDevis(),
                    devi.getMontantTravaux(),
                    devi.getNature(),
                    devi.getType()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }

    public void chargerDonneesBien(int idBien,TypeLogement typeLogement, PageMonBien page) throws DAOException {
        try {
            if(typeLogement == TypeLogement.BATIMENT){
                BatimentDAO batimentDAO = new DAO.jdbc.BatimentDAO();
                Batiment batiment = batimentDAO.readId(idBien);
                if (batiment != null) {
                    // Mise à jour des labels avec les informations du bien
                    page.getAffichageNumeroFiscal(batiment.getNumeroFiscal());
                    page.getAffichageVille().setText(batiment.getVille());
                    page.getAffichageAdresse().setText(batiment.getAdresse());
                    page.getAffichageComplement().setText("");
                    DevisDAO devisDAO = new DevisDAO();
                    page.getAffichageCoutTravaux().setText(String.valueOf(devisDAO.getMontantTotalTravaux(batiment.getNumeroFiscal(), typeLogement))+" €");
                }
            } else {
                // Récupération des informations du bien via le DAO
                BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
                BienLouable bienLouable = bienLouableDAO.readId(idBien);
                if (bienLouable != null) {
                    // Mise à jour des labels avec les informations du bien
                    page.getAffichageNumeroFiscal(bienLouable.getNumeroFiscal());
                    page.getAffichageVille().setText(bienLouable.getVille());
                    page.getAffichageAdresse().setText(bienLouable.getAdresse());
                    page.getAffichageComplement().setText(bienLouable.getComplementAdresse());
                    DevisDAO devisDAO = new DevisDAO();
                    page.getAffichageCoutTravaux().setText(String.valueOf(devisDAO.getMontantTotalTravaux(bienLouable.getNumeroFiscal(), typeLogement))+" €");
                }
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }

    public ActionListener openDiag(String reference,Integer idBien) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BienLouableDAO bienLouableDAO = new BienLouableDAO();
                    String num_fisc = bienLouableDAO.readId(idBien).getNumeroFiscal();
                    DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
                    Diagnostic diag = diagnosticDAO.read(num_fisc,refDiagnosticSansDate(reference));
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

    public ActionListener ouvrirPageNouveauTravaux(Integer idBien,TypeLogement typeLogement){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pageMonBien.getFrame().dispose();
                    new PageNouveauTravaux(idBien,typeLogement);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
    }

    public String refDiagnosticSansDate(String ref) {
        String refSansDate = "";
        while (ref.length() > 0 && ref.charAt(0) != '-') {
            refSansDate += ref.charAt(0);
            ref = ref.substring(1);
        }
        return refSansDate;
    }
    public ActionListener quitterPage(){
        return e -> {
            pageMonBien.getFrame().dispose();
            PageMesBiens pageMesBiens = new PageMesBiens();
            PageMesBiens.main(null);
        };
    }

    public ActionListener delierGarage(int idBien,TypeLogement type_logement) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BienLouableDAO bienLouableDAO = new BienLouableDAO();
                    bienLouableDAO.délierGarage(idBien);
                    pageMonBien.getFrame().dispose();
                    pageMonBien = new PageMonBien(idBien,type_logement);
                    new PageMesBiens();
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public ActionListener getTelechargerPDFButton(JFrame parentFrame, String diagnostic,Integer idBien,TypeLogement type_logement) {
        return e -> {
            String diagSansDate = refDiagnosticSansDate(diagnostic);
            // Créer un JFileChooser pour permettre de sélectionner un fichier
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");
            Date date = null;
            // Ouvrir le dialogue de sélection de fichier
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Obtenir le fichier sélectionné
                File selectedFile = fileChooser.getSelectedFile();
                date = setDateDiag();
                JDialog dialog = new JDialog(parentFrame, "Modifier le diagnostic", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir modifier votre diagnostic ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton validerButton = new JButton("Valider");
                validerButton.setBounds(90, 100, 100, 30);
                dialog.add(validerButton);
                Date finalDate = date;
                JButton annulerButton = new JButton("Annuler");
                annulerButton.setBounds(210, 100, 100, 30);
                dialog.add(annulerButton);
                annulerButton.addActionListener(event -> dialog.dispose());
                validerButton.addActionListener(event -> {
                    try {
                        new DiagnosticDAO().updateDate(new Diagnostic(diagSansDate, selectedFile.getAbsolutePath(), finalDate),new BienLouableDAO().readId(idBien).getNumeroFiscal(), finalDate);
                        JOptionPane.showMessageDialog(dialog,
                                "La date de péremption du diagnostic a été mis à jour à " + finalDate + ".",
                                "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (DAOException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    dialog.dispose();
                });
                dialog.setLocationRelativeTo(parentFrame);
                dialog.setVisible(true);
                JButton btn = (JButton) e.getSource();
                if (!btn.getText().contains("\u2705")) {
                    btn.setText(btn.getText() + " \u2705");
                }
                try {
                    pageMonBien.getFrame().dispose();
                    pageMonBien = new PageMonBien(idBien,type_logement);
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

    public ActionListener deleteBienLouable(JFrame parentFrame, Integer idBien, TypeLogement type_logement) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(parentFrame, "Suppression du bien", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir supprimer votre bien louable ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton validerButton = new JButton("Valider");
                validerButton.setBounds(90, 100, 100, 30);
                dialog.add(validerButton);
                JButton annulerButton = new JButton("Annuler");
                annulerButton.setBounds(210, 100, 100, 30);
                dialog.add(annulerButton);
                annulerButton.addActionListener(event -> dialog.dispose());
                validerButton.addActionListener(event -> {
                    try {
                        switch (type_logement) {
                            case BATIMENT:
                                new BatimentDAO().delete(new BatimentDAO().readId(idBien).getNumeroFiscal());
                                break;
                            case APPARTEMENT:
                            case MAISON:
                                new BienLouableDAO().delete(idBien);
                                break;
                            case GARAGE_ASSOCIE:
                            case GARAGE_PAS_ASSOCIE:
                                new GarageDAO().delete(idBien, type_logement);
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
                    pageMonBien.getFrame().dispose();
                    PageMesBiens pageMesBiens = new PageMesBiens();
                    pageMesBiens.main(null);
                });
                dialog.setLocationRelativeTo(parentFrame);
                dialog.setVisible(true);
            }
        };
    }
};
