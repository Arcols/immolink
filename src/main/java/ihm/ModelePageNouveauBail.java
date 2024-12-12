package ihm;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import modele.PageBaux;
import modele.PageNouveauBail;
import modele.PageNouveauBienImmobilier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ModelePageNouveauBail {

    private PageNouveauBail pageNouveauBail;
    private List<Locataire> Locataireselected=new LinkedList<Locataire>();
    private List<Integer> ListQuotite = new LinkedList<Integer>();

    public ModelePageNouveauBail(PageNouveauBail pageNouveauBail) {
        this.pageNouveauBail = pageNouveauBail;
    }

    public ActionListener getAjouterLocataire() {
        return e -> {
            // Données fictives pour les locataires
            List<Locataire> listlocataires = new DAO.jdbc.LocataireDAO().getAllLocataire();
            String[][] locataires = new String[listlocataires.size()-Locataireselected.size()][];
            String[] ligne;
            int i = 0;
            for (Locataire l : listlocataires) {
                if (!Locataireselected.contains(l)) {
                    ligne = new String[]{l.getNom(), l.getPrénom(), l.getTéléphone()};
                    locataires[i] = ligne;
                    i++;
                }
            }
            // Colonnes de la table
            String[] columns = {"Nom", "Prénom", "Téléphone"};

            // Modèle pour la table
            DefaultTableModel model = new DefaultTableModel(locataires, columns){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Toutes les cellules sont non éditables
                }
            };
            JTable selectionTable = new JTable(model);
            selectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // ScrollPane pour la table
            JScrollPane scrollPanePopUp = new JScrollPane(selectionTable);

            // Création d'une fenêtre popup
            JFrame popupFrame = new JFrame("Sélectionner un locataire");
            popupFrame.setSize(400, 300);
            popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popupFrame.add(scrollPanePopUp);
            popupFrame.setLocationRelativeTo(this.pageNouveauBail.getFrame());

            // Ajout d'un MouseListener pour détecter le double-clic
            selectionTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-clic
                        int selectedRow = selectionTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            // Récupérer les données du locataire sélectionné
                            String nom = model.getValueAt(selectedRow, 0).toString();
                            String prenom = model.getValueAt(selectedRow, 1).toString();
                            String telephone = model.getValueAt(selectedRow, 2).toString();

                            // Ajouter ces données dans la table principale

                            Locataireselected.add(new DAO.jdbc.LocataireDAO().getLocataireByNomPrénomTel(nom,prenom,telephone));
                            int quotite=setQuotite();
                            ListQuotite.add(quotite);
                            pageNouveauBail.getTableModel().addRow(new String[]{prenom,nom, telephone,String.valueOf(quotite)+"%"});
                            pageNouveauBail.checkFields();

                            // Fermer la fenêtre popup
                            popupFrame.dispose();
                        }
                    }
                }
            });

            // Afficher la fenêtre popup
            popupFrame.setVisible(true);
        };
    }

    public int setQuotite() {
        JDialog dialog = new JDialog((Frame) null, "Saisir la quotité du locataire sélectionné ", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Quotité en % :");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JSpinner quotiteSpinner = new JSpinner();
        quotiteSpinner.setBounds(220, 30, 100, 25);
        quotiteSpinner.setModel(new SpinnerNumberModel(100, 0, 100, 1));

        dialog.add(quotiteSpinner);


        JButton validerButton = new JButton("Valider");
        validerButton.setBounds(150, 100, 100, 30);
        dialog.add(validerButton);

        validerButton.addActionListener(event -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        int i = (Integer) quotiteSpinner.getValue();
        return i;
    }

    public ActionListener getSurfaceEtPiece() {
        return e -> {
            String ville=(String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
            String adresse=(String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
            String compl=(String) this.pageNouveauBail.getChoix_complement().getSelectedItem();
            Double surface=new DAO.jdbc.BienLouableDAO().getSurfaceFromCompl(ville,adresse,compl);
            this.pageNouveauBail.getChoix_surface().setText(surface.toString()+" m²");
            Integer nbpiece=new DAO.jdbc.BienLouableDAO().getNbPieceFromCompl(ville,adresse,compl);
            this.pageNouveauBail.getChoix_nb_piece().setText(nbpiece.toString());
        };
    }
    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }
        };
    }

    public ActionListener CreationBail(){
        return e -> {
            String ville=(String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
            String adresse=(String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
            String compl=(String) this.pageNouveauBail.getChoix_complement().getSelectedItem();
            String numfisc=new DAO.jdbc.BienLouableDAO().getFiscFromCompl(ville,adresse,compl);
            java.sql.Date sqlDateDebut = java.sql.Date.valueOf(pageNouveauBail.getChoix_date_debut().getText());
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(pageNouveauBail.getChoix_date_fin().getText());
            Bail bail=new Bail(this.pageNouveauBail.getSolde_tout_compte().isSelected(),
                    numfisc,
                    Double.parseDouble(this.pageNouveauBail.getChoix_loyer().getText()),
                    Double.parseDouble(this.pageNouveauBail.getChoix_prevision().getText()),
                    Double.parseDouble(this.pageNouveauBail.getChoix_depot_garantie().getText()),
                    sqlDateDebut,
                    sqlDateFin);
            try {
                new BailDAO().create(bail);
                for(int i=0;i<Locataireselected.size();i++) {
                    new LouerDAO().create(Locataireselected.get(i),bail,ListQuotite.get(i));
                }
                JOptionPane.showMessageDialog(null, "Le Bail a été ajouté et lié à vos locataires !", "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e);
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du bail.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    private void refreshPage(ActionEvent e) {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageNouveauBail nouvellePage = new PageNouveauBail();
        nouvellePage.getFrame().setVisible(true);
    }

    public ActionListener quitterPage(){
        return e -> {
            pageNouveauBail.getFrame().dispose();
            PageBaux PageBaux = new PageBaux();
            PageBaux.main(null);
        };
    }
}

