package ihm;

import DAO.DAOException;
import DAO.LogementDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.LocataireDAO;
import classes.*;
import enumeration.TypeLogement;
import modele.PageMesBiens;
import modele.PageMonBien;
import modele.PageNouveauTravaux;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ModelePageMonBien {

    private PageMonBien pageMonBien;

    public ModelePageMonBien(PageMonBien pageMonBien){
        this.pageMonBien = pageMonBien;
    }
    public static DefaultTableModel loadDataTravauxToTable(Integer id) throws SQLException, DAOException {
        // Liste des colonnes
        String[] columnNames = { "Devis","Montant", "Nature", "Type" };

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        DAO.BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
        BienLouable bienLouable = bienLouableDAO.readId(id);

        // Récupération des Travaux
        DevisDAO devisDAO = new DevisDAO();
        List<Devis> devis = devisDAO.getAllDevisFromABien(bienLouable.getNumero_fiscal(),TypeLogement.APPARTEMENT);

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

    public void chargerDonneesBien(int idBien, PageMonBien page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(idBien);

            DevisDAO devisDAO =new DevisDAO();

            if (bienLouable != null) {
                // Mise à jour des labels avec les informations du bien
                page.setAffichageNumeroFiscal(bienLouable.getNumero_fiscal());
                page.getAffichageVille().setText(bienLouable.getVille());
                page.getAffichageAdresse().setText(bienLouable.getAdresse());
                page.getAffichageComplement().setText(bienLouable.getComplement_adresse());
                page.getAffichageCoutTravaux().setText(String.valueOf(devisDAO.getMontantTotalTravaux(bienLouable.getNumero_fiscal(), TypeLogement.APPARTEMENT))+" €");
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

    public ActionListener ouvrirPageNouveauTravaux(Integer idBien){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pageMonBien.getFrame().dispose();
                    new PageNouveauTravaux(idBien);
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
}
