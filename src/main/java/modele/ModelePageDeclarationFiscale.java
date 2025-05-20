package modele;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.DevisDAO;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageAccueil;
import ihm.PageDeclarationFiscale;

public class ModelePageDeclarationFiscale {
    private final PageDeclarationFiscale page_declaration_fiscale;
    private final Map<Integer,List<Devis>> ligne_devis;
    public ModelePageDeclarationFiscale(PageDeclarationFiscale page){
        this.page_declaration_fiscale=page;
        this.ligne_devis = new HashMap<>();
    }

    public DefaultTableModel LoadToDataPageDeclarationFiscaleToTable(){
        BatimentDAO bat_DAO = new BatimentDAO();
        DevisDAO dev_DAO=new DevisDAO();
        List<Batiment> list_batiment= bat_DAO.findAll();
        String[][] data = new String[list_batiment.size()][];
        String[] ligne;
        int i = 0;
        for (Batiment b : list_batiment) {
            int total_local;
            Float total_travaux = 0f;
            double total_charge = 0;
            List<String> travaux_details = new LinkedList<>();
            try {
                List<Integer> tout_appartement = bat_DAO.getBienTypeBat(bat_DAO.getIdBat(b.getVille(), b.getAdresse()), TypeLogement.APPARTEMENT);
                List<Integer> tout_garage = bat_DAO.getBienTypeBat(bat_DAO.getIdBat(b.getVille(), b.getAdresse()), TypeLogement.GARAGE_PAS_ASSOCIE);
                total_local = tout_garage.size() + tout_appartement.size();

                List<Devis> travaux_batiment= dev_DAO.getAllDevisFromABienAndDate(b.getNumeroFiscal(),TypeLogement.BATIMENT, Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"));
                for(Integer id_appartement : tout_appartement){
                    BienLouable appartement = new BienLouableDAO().readId(id_appartement);
                    List<Devis> travaux_appartement =  dev_DAO.getAllDevisFromABienAndDate(appartement.getNumeroFiscal(),TypeLogement.APPARTEMENT,Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"));
                    for(Devis d:travaux_appartement){
                        travaux_batiment.add(d);
                    }

                    Bail bail = new BienLouableDAO().getBailFromBienAndDate(appartement,Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"));
                    int bail_id = new BailDAO().getId(bail);
                    int eau = new ChargeDAO().getId("Eau",bail_id);
                    int electricite = new ChargeDAO().getId("Electricité",bail_id);
                    int entretien = new ChargeDAO().getId("Entretien",bail_id);
                    total_charge+= new ChargeDAO().getMontant(Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"),eau);
                    total_charge+= new ChargeDAO().getMontant(Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"),electricite);
                    total_charge+= new ChargeDAO().getMontant(Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"),entretien);
                }
                for(Integer id_garage : tout_garage){
                    BienLouable gar = new BienLouableDAO().readId(id_garage);
                    List<Devis> travaux_garage =  dev_DAO.getAllDevisFromABienAndDate(gar.getNumeroFiscal(),TypeLogement.GARAGE_PAS_ASSOCIE,Date.valueOf(this.page_declaration_fiscale.getAnnee()+"-01-01"));
                    for(Devis d:travaux_garage){
                        travaux_batiment.add(d);
                    }
                }

                this.ligne_devis.putIfAbsent(i,travaux_batiment);
                for(Devis d:travaux_batiment){
                    total_travaux+=d.getMontantTravaux();
                    travaux_details.add(d.getNature()+" "+
                            d.getNomEntreprise()+" "+
                            d.getAdresseEntreprise()+" "+
                            d.getDateFacture() +" "+
                            d.getMontantTravaux());
                }
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            String touslestravaux="";
            for(String t:travaux_details){
                touslestravaux+=t+" ";
            }
            ligne = new String[]{
                    b.getAdresse(),
                    String.valueOf(total_local),
                    String.valueOf(total_local*20),
                    String.valueOf(total_travaux),
                    String.valueOf(total_charge),
                    touslestravaux
            };
            data[i] = ligne;
            i++;
        }
        String[] colonne = { "Adresse du batiment","Nombre de local", "222", "224", "227", "400"};
        // Créer le modèle de table avec les données
        DefaultTableModel table_modele = new DefaultTableModel(data, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        return table_modele;
    }

    public Map<Integer, List<Devis>> getLignedevis() {
        return ligne_devis;
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
        String[] colonne = { "Nature des travaux","Nom et adresse entreprise", "Date de la facture", "Montant"};
        // Créer le modèle de table avec les données
        DefaultTableModel table_modele = new DefaultTableModel(data, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        JTable tab = new JTable(table_modele);
        TableColumnModel columnModel = tab.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(125); // Nature
        columnModel.getColumn(1).setPreferredWidth(250); // Nom et adresse
        columnModel.getColumn(2).setPreferredWidth(125); // Date facture
        columnModel.getColumn(3).setPreferredWidth(100); // Montant
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ScrollPane pour la table
        JScrollPane scrollPane_pop_up = new JScrollPane(tab);

        // Création d'une fenêtre popup
        JFrame popupFrame = new JFrame("Sélectionner un locataire");
        popupFrame.setSize(600, 400);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setLayout(new BorderLayout());
        popupFrame.add(scrollPane_pop_up, BorderLayout.CENTER);
        popupFrame.setLocationRelativeTo(this.page_declaration_fiscale.getFrame());

        JButton bouton_valider = new JButton("Quitter");
        bouton_valider.setBounds(150, 100, 100, 30);
        popupFrame.add(bouton_valider,BorderLayout.SOUTH);

        bouton_valider.addActionListener(event -> {
            popupFrame.dispose();
        });

        // Afficher la fenêtre popup
        popupFrame.setVisible(true);
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
        page_declaration_fiscale.getFrame().dispose();
    }

    public ActionListener quitterPage(){
        return e -> {
            int x=page_declaration_fiscale.getFrame().getX();
            int y=page_declaration_fiscale.getFrame().getY();
            page_declaration_fiscale.getFrame().dispose();
            new PageAccueil(x,y);
        };
    }

    public MouseAdapter mouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = page_declaration_fiscale.getTable().getSelectedRow();
                    OuvrirDetailTravaux(getLignedevis().get(row));
                }
            }
        };
    }
}
