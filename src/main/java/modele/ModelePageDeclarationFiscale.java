package modele;

import DAO.DAOException;
import DAO.jdbc.*;
import classes.*;
import enumeration.TypeLogement;
import ihm.PageAccueil;
import ihm.PageDeclarationFiscale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
                    List<Devis> travauxapt =  devDAO.getAllDevisFromABienAndDate(apt.getNumeroFiscal(),TypeLogement.APPARTEMENT,Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
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
                    List<Devis> travauxgar =  devDAO.getAllDevisFromABienAndDate(gar.getNumeroFiscal(),TypeLogement.GARAGE_PAS_ASSOCIE,Date.valueOf(this.pageDeclarationFiscale.getAnnee()+"-01-01"));
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

    public void OuvrirDetailTravaux(List<Devis> listdevis) {

        String[][] data = new String[listdevis.size()][];
        String[] ligne;
        int i = 0;
        for(Devis d : listdevis) {
            ligne= new String[]{
                    d.getNature(),
                    d.getNomEntreprise()+", "+d.getAdresseEntreprise(),
                    String.valueOf(d.getDateFacture()),
                    String.valueOf(d.getMontantTravaux())
            };
            data[i] = ligne;
            i++;
        }
        String[] columns = { "Nature des travaux","Nom et adresse entreprise", "Date de la facture", "Montant"};
        // Créer le modèle de table avec les données
        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        JTable tab = new JTable(tableModel);
        TableColumnModel columnModel = tab.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(125); // Nature
        columnModel.getColumn(1).setPreferredWidth(250); // Nom et adresse
        columnModel.getColumn(2).setPreferredWidth(125); // Date facture
        columnModel.getColumn(3).setPreferredWidth(100); // Montant
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ScrollPane pour la table
        JScrollPane scrollPanePopUp = new JScrollPane(tab);

        // Création d'une fenêtre popup
        JFrame popupFrame = new JFrame("Sélectionner un locataire");
        popupFrame.setSize(600, 400);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(scrollPanePopUp, BorderLayout.CENTER);
        popupFrame.setLocationRelativeTo(this.pageDeclarationFiscale.getFrame());

        JButton validerButton = new JButton("Quitter");
        validerButton.setBounds(150, 100, 100, 30);
        popupFrame.add(validerButton,BorderLayout.SOUTH);

        validerButton.addActionListener(event -> {
            popupFrame.dispose();
        });


        // Afficher la fenêtre popup
        popupFrame.setVisible(true);
    }

    public ActionListener quitterPage(){
        return e -> {
            pageDeclarationFiscale.getFrame().dispose();
            PageAccueil PageAccueil = new PageAccueil();
            PageAccueil.main(null);
        };
    }
}
