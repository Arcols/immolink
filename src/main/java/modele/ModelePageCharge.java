package modele;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static classes.PdfGenerator.generateChargesPdf;
import static classes.PdfGenerator.ouvrirPdf;

public class ModelePageCharge {

    private PageCharge pageCharge;

    public ModelePageCharge(PageCharge pageCharge) {
        this.pageCharge = pageCharge;
    }

    public ActionListener ouvrirPageFacture() {
        return e -> {
            pageCharge.getFrame().dispose();
            new PageFacture(pageCharge.getId_bail());
        };
    }
    public ActionListener quitterPage() {
        return e -> {
            pageCharge.getFrame().dispose();
            Bail bail = new BailDAO().getBailFromId(pageCharge.getId_bail());
            new PageUnBail(bail);
        };
    }
    public ActionListener Archivage(int id_charge){
        return e->{
            pageCharge.getFrame().dispose();
            new PageArchivesFactures(id_charge);
        };
    }
    public ActionListener Regularisation(){
        return e -> {
            Map<Integer, List<Integer>> mapallbaux = new DAO.jdbc.LouerDAO().getAllLocatairesDesBeaux();
            List<Integer> listidloc = mapallbaux.get(pageCharge.getId_bail());
            String currentYear = choix_annee();

            for (int id : listidloc) {
                Locataire l = new DAO.jdbc.LocataireDAO().getLocFromId(id);
                Bail bail = new DAO.jdbc.BailDAO().getBailFromId(pageCharge.getId_bail());

                String genre = "";
                switch (l.getGenre()) {
                    case "H":
                        genre = "M ";
                        break;
                    case "F":
                        genre = "Mme ";
                        break;
                    case "C":
                        genre = "Mme, M ";
                        break;
                }

                try {
                    BienLouable bien = new DAO.jdbc.BienLouableDAO().readFisc(bail.getFiscBien());
                    Date datedebutperiode = Date.valueOf(currentYear + "-01-01");
                    int qt = new DAO.jdbc.LouerDAO().getQuotité(pageCharge.getId_bail(), id);
                    double quotite=qt/100.0;
                    int idcharge = new DAO.jdbc.ChargeDAO().getId("Eau", pageCharge.getId_bail());
                    double prixEau = new DAO.jdbc.ChargeDAO().getMontant(datedebutperiode, idcharge);
                    idcharge = new DAO.jdbc.ChargeDAO().getId("Ordures", pageCharge.getId_bail());
                    double prixOrdures = new DAO.jdbc.ChargeDAO().getMontant(datedebutperiode, idcharge);
                    idcharge = new DAO.jdbc.ChargeDAO().getId("Entretien", pageCharge.getId_bail());
                    double prixEntretien = new DAO.jdbc.ChargeDAO().getMontant(datedebutperiode, idcharge);
                    idcharge = new DAO.jdbc.ChargeDAO().getId("Electricité", pageCharge.getId_bail());
                    double prixElectricite = new DAO.jdbc.ChargeDAO().getMontant(datedebutperiode, idcharge);
                    double provisiontotal = bail.getCharge() * quotite * 12;

                    SimpleDateFormat fullFormatter = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("fr", "FR"));

                    if (bail.getDateDebut().getYear() - 1 == Integer.valueOf(currentYear)-1) {
                        datedebutperiode = bail.getDateDebut();
                    }
                    generateChargesPdf("pdfgeneres/Régularisation des charges " + l.getNom() + " " + l.getPrénom()
                                    +" "+(currentYear)+".pdf",
                            "M Thierry MILLAN",
                            "18, rue des Lilas\n31000 TOULOUSE",
                            "05 xx xx xx xx",
                            genre + l.getNom() + " " + l.getPrénom(),
                            bien.getAdresse(),
                            fullFormatter.format(Date.valueOf(LocalDate.now())),
                            fullFormatter.format(datedebutperiode),
                            fullFormatter.format(Date.valueOf(currentYear+ "-12-31")),
                            prixEau * quotite,
                            prixOrdures * quotite,
                            prixEntretien * quotite,
                            prixElectricite * quotite,
                            provisiontotal,
                            l.getGenre());
                    try {
                        ouvrirPdf("pdfgeneres/Régularisation des charges " + l.getNom() + " " + l.getPrénom()
                                +" "+currentYear+".pdf");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public String choix_annee() {
        JDialog dialog = new JDialog((Frame) null, "Saisir année de la régularisation des charges ", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Année de la régularisation :");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JTextField choix_annee = new JTextField();
        choix_annee.setBounds(220, 30, 100, 25);
        dialog.add(choix_annee);

        JButton validerButton = new JButton("Valider");
        validerButton.setBounds(150, 100, 100, 30);
        dialog.add(validerButton);

        validerButton.addActionListener(event -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return choix_annee.getText();
    }
}
