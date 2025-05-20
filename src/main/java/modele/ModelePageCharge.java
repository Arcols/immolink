package modele;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.ChargeDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static classes.PdfGenerator.generateChargesPdf;
import static classes.PdfGenerator.ouvrirPdf;

public class ModelePageCharge {

    private final PageCharge page_charge;

    public ModelePageCharge(PageCharge page_charge) {
        this.page_charge = page_charge;
    }

    public ActionListener ouvrirPageFacture() {
        return e -> {
            page_charge.getFrame().dispose();
            int x=page_charge.getFrame().getX();
            int y=page_charge.getFrame().getY();

            new PageFacture(page_charge.getId_bail(),x,y);
        };
    }
    public ActionListener quitterPage() {
        return e -> {
            page_charge.getFrame().dispose();
            Bail bail = new BailDAO().getBailFromId(page_charge.getId_bail());
            int x=page_charge.getFrame().getX();
            int y=page_charge.getFrame().getY();

            new PageUnBail(bail,x,y);
        };
    }
    public ActionListener Archivage(){
        return e->{
            page_charge.getFrame().dispose();
            int x=page_charge.getFrame().getX();
            int y=page_charge.getFrame().getY();
            new PageArchivesFactures(page_charge.getId_bail(),x,y);
        };
    }
    public ActionListener Regularisation(){
        return e -> {
            Map<Integer, List<Integer>> map_all_baux = new DAO.jdbc.LouerDAO().getAllLocatairesDesBeaux();
            List<Integer> liste_id_loc = map_all_baux.get(page_charge.getId_bail());
            String annee_actuelle = choix_annee();

            for (int id : liste_id_loc) {
                Locataire l = new DAO.jdbc.LocataireDAO().getLocFromId(id);
                Bail bail = new DAO.jdbc.BailDAO().getBailFromId(page_charge.getId_bail());

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
                    Date date_debut_periode = Date.valueOf(annee_actuelle + "-01-01");
                    int qt = new DAO.jdbc.LouerDAO().getQuotite(page_charge.getId_bail(), id);
                    double quotite=qt/100.0;
                    int id_charge = new DAO.jdbc.ChargeDAO().getId("Eau", page_charge.getId_bail());
                    double prix_eau = new DAO.jdbc.ChargeDAO().getMontant(date_debut_periode, id_charge);
                    id_charge = new DAO.jdbc.ChargeDAO().getId("Ordures", page_charge.getId_bail());
                    double prix_ordure = new DAO.jdbc.ChargeDAO().getMontant(date_debut_periode, id_charge);
                    id_charge = new DAO.jdbc.ChargeDAO().getId("Entretien", page_charge.getId_bail());
                    double prix_entretien = new DAO.jdbc.ChargeDAO().getMontant(date_debut_periode, id_charge);
                    id_charge = new DAO.jdbc.ChargeDAO().getId("Electricité", page_charge.getId_bail());
                    double prix_electricite = new DAO.jdbc.ChargeDAO().getMontant(date_debut_periode, id_charge);
                    double provision_total = bail.getCharge() * quotite * 12;

                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("fr", "FR"));

                    if (bail.getDateDebut().getYear() - 1 == Integer.valueOf(annee_actuelle)-1) {
                        date_debut_periode = bail.getDateDebut();
                    }
                    generateChargesPdf("pdfgeneres/Régularisation des charges " + l.getNom() + " " + l.getPrenom()
                                    +" "+(annee_actuelle)+".pdf",
                            "M Thierry MILLAN",
                            "18, rue des Lilas\n31000 TOULOUSE",
                            "05 xx xx xx xx",
                            genre + l.getNom() + " " + l.getPrenom(),
                            bien.getAdresse(),
                            formatter.format(Date.valueOf(LocalDate.now())),
                            formatter.format(date_debut_periode),
                            formatter.format(Date.valueOf(annee_actuelle+ "-12-31")),
                            prix_eau * quotite,
                            prix_ordure * quotite,
                            prix_entretien * quotite,
                            prix_electricite * quotite,
                            provision_total,
                            l.getGenre());
                    try {
                        JOptionPane.showMessageDialog(null, "Régularisation effectué sur votre bien !", "Information", JOptionPane.INFORMATION_MESSAGE);
                        ouvrirPdf("pdfgeneres/Régularisation des charges " + l.getNom() + " " + l.getPrenom()
                                +" "+annee_actuelle+".pdf");
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

        JButton bouton_valider = new JButton("Valider");
        bouton_valider.setBounds(150, 100, 100, 30);
        dialog.add(bouton_valider);

        bouton_valider.addActionListener(event -> dialog.dispose());

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return choix_annee.getText();
    }

    public double getPrixDe(String type_charge,int annee){
        ChargeDAO charge = new DAO.jdbc.ChargeDAO();
        double prix = 0.0d;
        Date date_actuelle = Date.valueOf(annee + "-01-01");
        try {
            prix = charge.getMontant(date_actuelle,charge.getId(type_charge, page_charge.getId_bail()));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return prix;
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
        page_charge.getFrame().dispose();
    }
}
