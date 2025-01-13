package modele;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.Locataire;
import ihm.PageBaux;
import ihm.PageNouveauBail;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ModelePageNouveauBail {

    private PageNouveauBail pageNouveauBail;
    private List<Locataire> Locataireselected=new LinkedList<Locataire>();
    private List<Integer> ListQuotite = new LinkedList<Integer>();
    private int quotite_actuelle;

    public ModelePageNouveauBail(PageNouveauBail pageNouveauBail) {
        this.pageNouveauBail = pageNouveauBail;
        this.quotite_actuelle=0;
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
                            setQuotite(quotite);
                            ListQuotite.add(quotite);
                            pageNouveauBail.getTableModel().addRow(new String[]{prenom,nom, telephone,String.valueOf(quotite)+"%"});
                            checkFields();

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

    public ActionListener supprimerLocataire(){
            return e -> {
                // Données fictives pour les locataires
                String[][] locataires = new String[Locataireselected.size()][];
                String[] ligne;
                int i = 0;
                for (Locataire l : Locataireselected) {
                        ligne = new String[]{l.getNom(), l.getPrénom(), l.getTéléphone()};
                        locataires[i] = ligne;
                        i++;
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

                                Locataireselected.remove(new DAO.jdbc.LocataireDAO().getLocataireByNomPrénomTel(nom,prenom,telephone));
                                int num_ligne_suppr = getLigneByValue(new String[]{nom, prenom, telephone});
                                //Récupère la en INT le pourcentage de quotité du baileur supprimé
                                int quotite_du_loc = Integer.parseInt(pageNouveauBail.getTable().getValueAt(num_ligne_suppr,3).toString().replace("%", ""));

                                //Retire la quotité du locataire supprimer (valeur récupérée sur la table de pageNouveauBail)
                                ListQuotite.remove((Object) quotite_du_loc);

                                setQuotite(-quotite_du_loc);
                                pageNouveauBail.getTableModel().removeRow(num_ligne_suppr);
                                checkFields();

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
        quotiteSpinner.setModel(new SpinnerNumberModel(100-this.quotite_actuelle, 0, 100-this.quotite_actuelle, 1));

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

    public void setQuotite(int remove){
        this.quotite_actuelle +=remove;
    }

    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };
    }
    public FocusListener getFocus() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                removeMoins();
            }
        };
    }
    public void removeMoins() {
        String text_loyer = pageNouveauBail.getChoix_loyer().getText();
        String text_prevision = pageNouveauBail.getChoix_prevision().getText();
        String text_depot = pageNouveauBail.getChoix_depot_garantie().getText();
        // Vérifier si le premier caractère est un '-'
        if (!pageNouveauBail.getChoix_loyer().getText().trim().isEmpty() && text_loyer.charAt(0) == '-') {
            // Supprimer le premier caractère
            pageNouveauBail.getChoix_loyer().setText(text_loyer.substring(1));
        }
        if (!pageNouveauBail.getChoix_prevision().getText().trim().isEmpty() && text_prevision.charAt(0) == '-') {
            // Supprimer le premier caractère
            pageNouveauBail.getChoix_prevision().setText(text_prevision.substring(1));
        }
        if (!pageNouveauBail.getChoix_depot_garantie().getText().trim().isEmpty() && text_depot.charAt(0) == '-') {
            // Supprimer le premier caractère
            pageNouveauBail.getChoix_depot_garantie().setText(text_depot.substring(1));
        }
    }
    public void checkFields() {
        boolean isFilled;

        isFilled = !pageNouveauBail.getChoix_loyer().getText().trim().isEmpty()
                && !pageNouveauBail.getChoix_prevision().getText().trim().isEmpty()
                && !pageNouveauBail.getChoix_depot_garantie().getText().trim().isEmpty()
                && pageNouveauBail.getChoix_date_debut().getDate()!=null
                && pageNouveauBail.getChoix_date_fin().getDate()!=null
                &&!(pageNouveauBail.getTable().getRowCount()==0)
                &&pageNouveauBail.getChoixIcc().getText().trim().length()!=0
                &&pageNouveauBail.getChoixIndexEau().getText().trim().length()!=0;

        // Active ou désactive le bouton "Valider"
        pageNouveauBail.getValider().setEnabled(isFilled);
    }
    public ActionListener CreationBail(){
        return e -> {
            if(this.quotite_actuelle==100) {
                String ville = (String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
                String adresse = (String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
                String compl = (String) this.pageNouveauBail.getChoix_complement().getSelectedItem();
                String numfisc = new DAO.jdbc.BienLouableDAO().getFiscFromCompl(ville, adresse, compl);
                java.sql.Date sqlDateDebut = new java.sql.Date(pageNouveauBail.getChoix_date_debut().getDate().getTime());
                java.sql.Date sqlDateFin = new java.sql.Date(pageNouveauBail.getChoix_date_fin().getDate().getTime());
                if (sqlDateDebut.after(sqlDateFin) || sqlDateDebut.equals(sqlDateFin)) {
                    JOptionPane.showMessageDialog(null, "Vos dates ne sont pas correctes, veuillez les vérifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    Bail bail = new Bail(this.pageNouveauBail.getSolde_tout_compte().isSelected(),
                            numfisc,
                            Double.parseDouble(this.pageNouveauBail.getChoix_loyer().getText()),
                            Double.parseDouble(this.pageNouveauBail.getChoix_prevision().getText()),
                            Double.parseDouble(this.pageNouveauBail.getChoix_depot_garantie().getText()),
                            sqlDateDebut,
                            sqlDateFin,
                            Double.parseDouble(this.pageNouveauBail.getChoixIcc().getText()),
                            Integer.parseInt(this.pageNouveauBail.getChoixIndexEau().getText()),
                            sqlDateDebut);
                    try {
                        new BailDAO().create(bail);
                        int id_bail = new BailDAO().getId(bail);
                        ChargeDAO chargesBail = new ChargeDAO();
                        chargesBail.create("Eau",id_bail);
                        chargesBail.create("Electricité",id_bail);
                        chargesBail.create("Entretien",id_bail);
                        chargesBail.create("Ordures",id_bail);
                        for (int i = 0; i < Locataireselected.size(); i++) {
                            new LouerDAO().create(Locataireselected.get(i), bail, ListQuotite.get(i));
                        }
                        JOptionPane.showMessageDialog(null, "Le Bail a été ajouté et lié à vos locataires !", "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        refreshPage(e);
                    } catch (DAOException | SQLException | RuntimeException ex) {
                        if(ex.getMessage().contains("chevauchement de dates pour ce bien louable")){
                            JOptionPane.showMessageDialog(null, "Il y a un chevauchement de dates entre les différents bien de ce bien louable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du bail.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La quotité du bail n'est pas à 100% !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    private void refreshPage(ActionEvent e) throws SQLException {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageNouveauBail nouvellePage = new PageNouveauBail();
        nouvellePage.getFrame().setVisible(true);
    }

    public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
        return e -> {
            String selectedVille = (String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
            if (!mapVillesAdresses.containsKey(selectedVille)) {
                this.pageNouveauBail.getChoix_adresse().setModel(new DefaultComboBoxModel());
            } else {
                this.pageNouveauBail.getChoix_adresse().setModel(
                        new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
            }

            Map<String, List<String>> mapAdresseCompl = new BienLouableDAO().getAllComplBail();
            String selectedAdresse = (String) this.pageNouveauBail.getChoix_adresse().getItemAt(0);
            if(!mapAdresseCompl.containsKey(selectedAdresse)){
                this.pageNouveauBail.getChoix_complement().setModel(new DefaultComboBoxModel());
            } else {
                this.pageNouveauBail.getChoix_complement().setModel(
                        new DefaultComboBoxModel(mapAdresseCompl.get(selectedAdresse).toArray(new String[0]))
                );
            }
        };
    }

    public ActionListener getAdresseActionListener(Map<String, List<String>> mapAdresseCompl){
        return e ->{
            String selectedAdresse = (String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
            if(!mapAdresseCompl.containsKey(selectedAdresse)){
                this.pageNouveauBail.getChoix_complement().setModel(new DefaultComboBoxModel());
            } else {
                this.pageNouveauBail.getChoix_complement().setModel(
                        new DefaultComboBoxModel(mapAdresseCompl.get(selectedAdresse).toArray(new String[0]))
                );
            }
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            pageNouveauBail.getFrame().dispose();
            PageBaux PageBaux = new PageBaux();
            PageBaux.main(null);
        };
    }

    public int getLigneByValue(String[] value){
        for (int ligne = 0; ligne < pageNouveauBail.getTable().getRowCount(); ligne++) {
            boolean trouve = false;
            // Comparer les colonnes avec les valeurs recherchées
            for (int col = 0; col < 3; col++) {
                if (pageNouveauBail.getTable().getValueAt(ligne, col).equals(value[col])) {
                    trouve = true;
                } else {
                    trouve = false;
                }
            }
            if (trouve) {
                return ligne; // Retourne le numéro de la ligne correspondante
            }
        }
        // Si aucune correspondance trouvée, retourner -1
        return -1;
    }
}

