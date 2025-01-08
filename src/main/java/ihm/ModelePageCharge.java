package ihm;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import modele.*;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static classes.PdfGenerator.generateChargesPdf;

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

    public ActionListener Regularisation(){
        return e -> {
            Map<Integer, List<Integer>> mapallbaux = new DAO.jdbc.LouerDAO().getAllLocatairesDesBeaux();
            List<Integer> listidloc = mapallbaux.get(pageCharge.getId_bail());
            for (int id : listidloc) {
                Locataire l = new DAO.jdbc.LocataireDAO().getLocFromId(id);
                Bail bail = new DAO.jdbc.BailDAO().getBailFromId(pageCharge.getId_bail());
                Date currentDate = Date.valueOf(LocalDate.now());

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
                    BienLouable bien = new DAO.jdbc.BienLouableDAO().readFisc(bail.getFisc_bien());
                    Date datedebutperiode = Date.valueOf(currentDate.getYear() +1900-1 + "-01-01");
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

                    if (bail.getDate_debut().getYear() - 1 == currentDate.getYear() - 2) {
                        datedebutperiode = bail.getDate_debut();
                    }
                    generateChargesPdf("pdfgeneres/Régularisation des charges " + l.getNom() + " " + l.getPrénom()
                                    +" "+(currentDate.getYear()+1900-1)+".pdf",
                            "M Thierry MILLAN",
                            "18, rue des Lilas\n31000 TOULOUSE",
                            "05 xx xx xx xx",
                            genre + l.getNom() + " " + l.getPrénom(),
                            bien.getAdresse(),
                            fullFormatter.format(currentDate),
                            fullFormatter.format(datedebutperiode),
                            fullFormatter.format(Date.valueOf(currentDate.getYear()+1900 - 1 + "-12-31")),
                            prixEau * quotite,
                            prixOrdures * quotite,
                            prixEntretien * quotite,
                            prixElectricite * quotite,
                            provisiontotal,
                            l.getGenre());
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
    }
}
