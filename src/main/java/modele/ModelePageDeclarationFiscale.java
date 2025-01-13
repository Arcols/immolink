package modele;

import DAO.DAOException;
import DAO.jdbc.*;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageDeclarationFiscale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelePageDeclarationFiscale {
    private PageDeclarationFiscale pageDeclarationFiscale;
    private Map<Integer,List<Devis>> lignedevis;
    public ModelePageDeclarationFiscale(PageDeclarationFiscale page){
        this.pageDeclarationFiscale=page;
        this.lignedevis = new HashMap<>();
    }

    public DefaultTableModel LoadToDataPageDeclarationFiscaleToTable(){
        BatimentDAO batDAO = new BatimentDAO();
        DevisDAO devDAO=new DevisDAO();
        List<Batiment> listbat= batDAO.findAll();
        String[][] data = new String[listbat.size()][];
        String[] ligne;
        int i = 0;
        for (Batiment b : listbat) {
            int totallocal;
            Float totaltravaux = 0f;
            double totalcharge = 0;
            List<String> travauxdetails = new LinkedList<>();
            try {
                List<Integer> allapt = batDAO.getBienTypeBat(batDAO.getIdBat(b.getVille(), b.getAdresse()), TypeLogement.APPARTEMENT);
                List<Integer> allgar = batDAO.getBienTypeBat(batDAO.getIdBat(b.getVille(), b.getAdresse()), TypeLogement.GARAGE_PAS_ASSOCIE);
                totallocal = allgar.size() + allapt.size();

                List<Devis> travauxbat= devDAO.getAllDevisFromABienAndDate(b.getNumeroFiscal(),TypeLogement.BATIMENT, Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
                for(Integer idapt : allapt){
                    BienLouable apt = new BienLouableDAO().readId(idapt);
                    List<Devis> travauxapt =  devDAO.getAllDevisFromABienAndDate(apt.getNumero_fiscal(),TypeLogement.APPARTEMENT,Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
                    for(Devis d:travauxapt){
                        travauxbat.add(d);
                    }

                    Bail bail = new BienLouableDAO().getBailFromBienAndDate(apt,Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
                    Integer bail_id = new BailDAO().getId(bail);
                    Integer eau = new ChargeDAO().getId("Eau",bail_id);
                    Integer electricité = new ChargeDAO().getId("Electricité",bail_id);
                    Integer entretien = new ChargeDAO().getId("Entretien",bail_id);
                    totalcharge+= new ChargeDAO().getMontant(Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"),eau);
                    totalcharge+= new ChargeDAO().getMontant(Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"),electricité);
                    totalcharge+= new ChargeDAO().getMontant(Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"),entretien);
                }
                for(Integer idgar : allgar){
                    BienLouable gar = new BienLouableDAO().readId(idgar);
                    List<Devis> travauxgar =  devDAO.getAllDevisFromABienAndDate(gar.getNumero_fiscal(),TypeLogement.GARAGE_PAS_ASSOCIE,Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
                    for(Devis d:travauxgar){
                        travauxbat.add(d);
                    }
                }

                this.lignedevis.putIfAbsent(i,travauxbat);
                for(Devis d:travauxbat){
                    totaltravaux+=d.getMontantTravaux();
                    travauxdetails.add(d.getNature()+" "+
                            d.getNomEntreprise()+" "+
                            d.getAdresseEntreprise()+" "+
                            String.valueOf(d.getDateFacture())+" "+
                            d.getMontantTravaux());
                }
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            String touslestravaux="";
            for(String t:travauxdetails){
                touslestravaux+=t+" ";
            }
            ligne = new String[]{
                    b.getAdresse(),
                    String.valueOf(totallocal),
                    String.valueOf(totallocal*20),
                    String.valueOf(totaltravaux),
                    String.valueOf(totalcharge),
                    touslestravaux
            };
            data[i] = ligne;
            i++;
        }
        String[] columns = { "Adresse du batiment","Nombre de local", "222", "224", "227", "400"};
        // Créer le modèle de table avec les données
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        return tableModel;
    }

    public Map<Integer, List<Devis>> getLignedevis() {
        return lignedevis;
    }

    public ActionListener OuvrirDetailTravaux(List<Devis> listdevis) {
        return e -> {
            JDialog dialog = new JDialog((Frame) null, "Détails travaux ", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            for(Devis d : listdevis) {
                JLabel label = new JLabel(d.getNature()+" "+
                        d.getNomEntreprise()+" "+
                        d.getAdresseEntreprise()+" "+
                        String.valueOf(d.getDateFacture())+" "+
                        d.getMontantTravaux()+"\n");
                label.setBounds(20, 30, 200, 25);
                dialog.add(label);
            }

            JButton validerButton = new JButton("Quitter");
            validerButton.setBounds(150, 100, 100, 30);
            dialog.add(validerButton);

            validerButton.addActionListener(event -> {
                dialog.dispose();
            });

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        };
    }
}
