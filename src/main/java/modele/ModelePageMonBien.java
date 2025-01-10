package modele;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.tools.Diagnostic;

import DAO.DAOException;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.DiagnosticDAO;
import classes.*;
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
            devis = devisDAO.getAllDevisFromABien(bienLouable.getNumero_fiscal(),bienLouableDAO.getTypeFromId(id));
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
                    page.getAffichageNumeroFiscal(bienLouable.getNumero_fiscal());
                    page.getAffichageVille().setText(bienLouable.getVille());
                    page.getAffichageAdresse().setText(bienLouable.getAdresse());
                    page.getAffichageComplement().setText(bienLouable.getComplement_adresse());
                    DevisDAO devisDAO = new DevisDAO();
                    page.getAffichageCoutTravaux().setText(String.valueOf(devisDAO.getMontantTotalTravaux(bienLouable.getNumero_fiscal(), typeLogement))+" €");
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
                    String num_fisc = bienLouableDAO.readId(idBien).getNumero_fiscal();
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
}
