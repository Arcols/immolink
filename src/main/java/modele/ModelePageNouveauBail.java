package modele;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.Locataire;
import ihm.PageBaux;
import ihm.PageNouveauBail;

public class ModelePageNouveauBail {

    private final PageNouveauBail page_nouveau_bail;
    private final List<Locataire> locataire_choisi=new LinkedList<>();
    private final List<Integer> liste_quotite = new LinkedList<>();
    private int quotite_actuelle;

    public ModelePageNouveauBail(PageNouveauBail page_nouveau_bail) {
        this.page_nouveau_bail = page_nouveau_bail;
        this.quotite_actuelle=0;
    }

    public ActionListener getAjouterLocataire() {
        return e -> {
            // Données fictives pour les locataires
            List<Locataire> liste_locataires = new DAO.jdbc.LocataireDAO().getAllLocataire();
            String[][] locataires = new String[liste_locataires.size()-locataire_choisi.size()][];
            String[] ligne;
            int i = 0;
            for (Locataire l : liste_locataires) {
                if (!locataire_choisi.contains(l)) {
                    ligne = new String[]{l.getNom(), l.getPrenom(), l.getTelephone()};
                    locataires[i] = ligne;
                    i++;
                }
            }
            // Colonnes de la table
            String[] colonnes = {"Nom", "Prénom", "Téléphone"};

            // Modèle pour la table
            DefaultTableModel model = new DefaultTableModel(locataires, colonnes){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Toutes les cellules sont non éditables
                }
            };
            JTable table_selection = new JTable(model);
            table_selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // ScrollPane pour la table
            JScrollPane scrollPane_pop_up = new JScrollPane(table_selection);

            // Création d'une fenêtre popup
            JFrame page_pop_up = new JFrame("Sélectionner un locataire");
            page_pop_up.setSize(400, 300);
            page_pop_up.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            page_pop_up.add(scrollPane_pop_up);
            page_pop_up.setLocationRelativeTo(this.page_nouveau_bail.getFrame());

            // Ajout d'un MouseListener pour détecter le double-clic
            table_selection.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-clic
                        int ligne_selectionne = table_selection.getSelectedRow();
                        if (ligne_selectionne >= 0) {
                            // Récupérer les données du locataire sélectionné
                            String nom = model.getValueAt(ligne_selectionne, 0).toString();
                            String prenom = model.getValueAt(ligne_selectionne, 1).toString();
                            String telephone = model.getValueAt(ligne_selectionne, 2).toString();

                            // Ajouter ces données dans la table principale

                            locataire_choisi.add(new DAO.jdbc.LocataireDAO().getLocataireByNomPrenom(nom,prenom,telephone));
                            int quotite=setQuotite();
                            setQuotite(quotite);
                            liste_quotite.add(quotite);
                            page_nouveau_bail.getTableModel().addRow(new String[]{prenom,nom, telephone, quotite +"%"});
                            checkFields();

                            // Fermer la fenêtre popup
                            page_pop_up.dispose();
                        }
                    }
                }
            });
            // Afficher la fenêtre popup
            page_pop_up.setVisible(true);
        };
    }

    public ActionListener supprimerLocataire(){
            return e -> {
                // Données fictives pour les locataires
                String[][] locataires = new String[locataire_choisi.size()][];
                String[] ligne;
                int i = 0;
                for (Locataire l : locataire_choisi) {
                        ligne = new String[]{l.getNom(), l.getPrenom(), l.getTelephone()};
                        locataires[i] = ligne;
                        i++;
                    }
                // Colonnes de la table
                String[] colonnes = {"Nom", "Prénom", "Téléphone"};

                // Modèle pour la table
                DefaultTableModel model = new DefaultTableModel(locataires, colonnes){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Toutes les cellules sont non éditables
                    }
                };
                JTable table_selection = new JTable(model);
                table_selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                // ScrollPane pour la table
                JScrollPane scrollPane_pop_up = new JScrollPane(table_selection);

                // Création d'une fenêtre popup
                JFrame page_pop_up = new JFrame("Sélectionner un locataire");
                page_pop_up.setSize(400, 300);
                page_pop_up.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                page_pop_up.add(scrollPane_pop_up);
                page_pop_up.setLocationRelativeTo(this.page_nouveau_bail.getFrame());

                // Ajout d'un MouseListener pour détecter le double-clic
                table_selection.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) { // Double-clic
                            int ligne_selectionne = table_selection.getSelectedRow();
                            if (ligne_selectionne >= 0) {
                                // Récupérer les données du locataire sélectionné
                                String nom = model.getValueAt(ligne_selectionne, 0).toString();
                                String prenom = model.getValueAt(ligne_selectionne, 1).toString();
                                String telephone = model.getValueAt(ligne_selectionne, 2).toString();

                                // Ajouter ces données dans la table principale

                                locataire_choisi.remove(new DAO.jdbc.LocataireDAO().getLocataireByNomPrenom(nom,prenom,telephone));
                                int num_ligne_suppr = getLigneByValue(new String[]{nom, prenom, telephone});
                                //Récupère la en INT le pourcentage de quotité du baileur supprimé
                                int quotite_du_loc = Integer.parseInt(page_nouveau_bail.getTable().getValueAt(num_ligne_suppr,3).toString().replace("%", ""));

                                //Retire la quotité du locataire supprimer (valeur récupérée sur la table de page_nouveau_bail)
                                liste_quotite.remove((Object) quotite_du_loc);

                                setQuotite(-quotite_du_loc);
                                page_nouveau_bail.getTableModel().removeRow(num_ligne_suppr);
                                checkFields();

                                // Fermer la fenêtre popup
                                page_pop_up.dispose();
                            }
                        }
                    }
                });
                // Afficher la fenêtre popup
                page_pop_up.setVisible(true);
            };
        }

    public int setQuotite() {
        JDialog dialog = new JDialog((Frame) null, "Saisir la quotité du locataire sélectionné ", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Quotité en % :");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JSpinner spinner_quotite = new JSpinner();
        spinner_quotite.setBounds(220, 30, 100, 25);
        spinner_quotite.setModel(new SpinnerNumberModel(100-this.quotite_actuelle, 0, 100-this.quotite_actuelle, 1));

        dialog.add(spinner_quotite);

        JButton bouton_valider = new JButton("Valider");
        bouton_valider.setBounds(150, 100, 100, 30);
        dialog.add(bouton_valider);

        bouton_valider.addActionListener(event -> {
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return (int) (Integer) spinner_quotite.getValue();
    }
    public ActionListener getSurfaceEtPiece() {
        return e -> {
            String ville=(String) this.page_nouveau_bail.getChoix_ville().getSelectedItem();
            String adresse=(String) this.page_nouveau_bail.getChoix_adresse().getSelectedItem();
            String compl=(String) this.page_nouveau_bail.getChoix_complement().getSelectedItem();
            Double surface=new DAO.jdbc.BienLouableDAO().getSurfaceFromCompl(ville,adresse,compl);
            this.page_nouveau_bail.getChoix_surface().setText(surface.toString()+" m²");
            Integer nb_piece=new DAO.jdbc.BienLouableDAO().getNbPieceFromCompl(ville,adresse,compl);
            this.page_nouveau_bail.getChoix_nb_piece().setText(nb_piece.toString());
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
        String text_loyer = page_nouveau_bail.getChoix_loyer().getText();
        String text_prevision = page_nouveau_bail.getChoix_prevision().getText();
        String text_depot = page_nouveau_bail.getChoix_depot_garantie().getText();
        // Vérifier si le premier caractère est un '-'
        if (!page_nouveau_bail.getChoix_loyer().getText().trim().isEmpty() && text_loyer.charAt(0) == '-') {
            // Supprimer le premier caractère
            page_nouveau_bail.getChoix_loyer().setText(text_loyer.substring(1));
        }
        if (!page_nouveau_bail.getChoix_prevision().getText().trim().isEmpty() && text_prevision.charAt(0) == '-') {
            // Supprimer le premier caractère
            page_nouveau_bail.getChoix_prevision().setText(text_prevision.substring(1));
        }
        if (!page_nouveau_bail.getChoix_depot_garantie().getText().trim().isEmpty() && text_depot.charAt(0) == '-') {
            // Supprimer le premier caractère
            page_nouveau_bail.getChoix_depot_garantie().setText(text_depot.substring(1));
        }
    }
    public void checkFields() {
        boolean isFilled;

        isFilled = !page_nouveau_bail.getChoix_loyer().getText().trim().isEmpty()
                && !page_nouveau_bail.getChoix_prevision().getText().trim().isEmpty()
                && !page_nouveau_bail.getChoix_depot_garantie().getText().trim().isEmpty()
                && page_nouveau_bail.getChoix_date_debut().getDate()!=null
                && page_nouveau_bail.getChoix_date_fin().getDate()!=null
                &&!(page_nouveau_bail.getTable().getRowCount()==0)
                &&page_nouveau_bail.getChoixIcc().getText().trim().length()!=0
                &&page_nouveau_bail.getChoixIndexEau().getText().trim().length()!=0;

        // Active ou désactive le bouton "Valider"
        page_nouveau_bail.getValider().setEnabled(isFilled);
    }
    public ActionListener CreationBail(){
        return e -> {
            if(this.quotite_actuelle==100) {
                String ville = (String) this.page_nouveau_bail.getChoix_ville().getSelectedItem();
                String adresse = (String) this.page_nouveau_bail.getChoix_adresse().getSelectedItem();
                String compl = (String) this.page_nouveau_bail.getChoix_complement().getSelectedItem();
                String num_fisc = new DAO.jdbc.BienLouableDAO().getFiscFromCompl(ville, adresse, compl);
                java.sql.Date sql_date_debut = new java.sql.Date(page_nouveau_bail.getChoix_date_debut().getDate().getTime());
                java.sql.Date sql_date_fin = new java.sql.Date(page_nouveau_bail.getChoix_date_fin().getDate().getTime());
                if (sql_date_debut.after(sql_date_fin) || sql_date_debut.equals(sql_date_fin)) {
                    JOptionPane.showMessageDialog(null, "Vos dates ne sont pas correctes, veuillez les vérifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double loyer = Double.parseDouble(this.page_nouveau_bail.getChoix_loyer().getText());
                        double prevision_pour_charge = Double.parseDouble(this.page_nouveau_bail.getChoix_prevision().getText());
                        double depot_garantie = Double.parseDouble(this.page_nouveau_bail.getChoix_depot_garantie().getText());
                        double icc = Double.parseDouble(this.page_nouveau_bail.getChoixIcc().getText());
                        int index_eau = Integer.parseInt(this.page_nouveau_bail.getChoixIndexEau().getText());

                        Bail bail = new Bail(this.page_nouveau_bail.getSolde_tout_compte().isSelected(),
                                num_fisc,
                                loyer,
                                prevision_pour_charge,
                                depot_garantie,
                                sql_date_debut,
                                sql_date_fin,
                                icc,
                                index_eau,
                                sql_date_debut);
                        try {
                            new BailDAO().create(bail);
                            int id_bail = new BailDAO().getId(bail);
                            ChargeDAO liste_charges_bail = new ChargeDAO();
                            liste_charges_bail.create("Eau",id_bail);
                            liste_charges_bail.create("Electricité",id_bail);
                            liste_charges_bail.create("Entretien",id_bail);
                            liste_charges_bail.create("Ordures",id_bail);
                            for (int i = 0; i < locataire_choisi.size(); i++) {
                                new LouerDAO().create(locataire_choisi.get(i), bail, liste_quotite.get(i));
                            }
                            JOptionPane.showMessageDialog(null, "Le Bail a été ajouté et lié à vos locataires !", "Succès",
                                    JOptionPane.INFORMATION_MESSAGE);
                            refreshPage(e);
                        } catch (DAOException | SQLException | RuntimeException ex) {
                            if(ex.getMessage().contains("chevauchement de dates pour ce bien louable")){
                                popUpError("Il y a un chevauchement de dates entre les différents bien de ce bien louable.");
                            }else{
                                popUpError("Une erreur est survenue lors de la création du bail.");
                            }
                        }
                    } catch (NumberFormatException event) {
                        popUpError("Veuillez saisir des nombres pour les champs loyer, prévision pour charges et dépôt de garantie.");
                    }
                }
            } else {
                popUpError("La quotité du bail n'est pas à 100% !");
            }
        };
    }

    private void popUpError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void refreshPage(ActionEvent e) throws SQLException {
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienne_fenetre.dispose();
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();

        PageNouveauBail nouvelle_page = new PageNouveauBail(x,y);
        nouvelle_page.getFrame().setVisible(true);
    }

    public ActionListener getVilleActionListener(Map<String, List<String>> map_ville_adresse) {
        return e -> {
            String ville_selectionnee = (String) this.page_nouveau_bail.getChoix_ville().getSelectedItem();
            if (!map_ville_adresse.containsKey(ville_selectionnee)) {
                this.page_nouveau_bail.getChoix_adresse().setModel(new DefaultComboBoxModel());
            } else {
                this.page_nouveau_bail.getChoix_adresse().setModel(
                        new DefaultComboBoxModel(map_ville_adresse.get(ville_selectionnee).toArray(new String[0])));
            }

            Map<String, List<String>> map_adresse_complement = new BienLouableDAO().getAllComplBail();
            String adresse_selectionnee = (String) this.page_nouveau_bail.getChoix_adresse().getItemAt(0);
            if(!map_adresse_complement.containsKey(adresse_selectionnee)){
                this.page_nouveau_bail.getChoix_complement().setModel(new DefaultComboBoxModel());
            } else {
                this.page_nouveau_bail.getChoix_complement().setModel(
                        new DefaultComboBoxModel(map_adresse_complement.get(adresse_selectionnee).toArray(new String[0]))
                );
            }
        };
    }

    public ActionListener getAdresseActionListener(Map<String, List<String>> map_adresse_complement){
        return e ->{
            String adresse_selectionnee = (String) this.page_nouveau_bail.getChoix_adresse().getSelectedItem();
            if(!map_adresse_complement.containsKey(adresse_selectionnee)){
                this.page_nouveau_bail.getChoix_complement().setModel(new DefaultComboBoxModel());
            } else {
                this.page_nouveau_bail.getChoix_complement().setModel(
                        new DefaultComboBoxModel(map_adresse_complement.get(adresse_selectionnee).toArray(new String[0]))
                );
            }
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            int x=page_nouveau_bail.getFrame().getX();
            int y=page_nouveau_bail.getFrame().getY();
            page_nouveau_bail.getFrame().dispose();
            PageBaux page_baux = new PageBaux(x,y);
        };
    }

    public int getLigneByValue(String[] valeur){
        for (int ligne = 0; ligne < page_nouveau_bail.getTable().getRowCount(); ligne++) {
            boolean trouve = false;
            // Comparer les colonnes avec les valeurs recherchées
            for (int col = 0; col < 3; col++) {
                trouve = page_nouveau_bail.getTable().getValueAt(ligne, col).equals(valeur[col]);
            }
            if (trouve) {
                return ligne; // Retourne le numéro de la ligne correspondante
            }
        }
        // Si aucune correspondance trouvée, retourner -1
        return -1;
    }

    public Map<String, List<String>> getVIlles() throws SQLException {
        return new BatimentDAO().searchAllBatimentsWithCompl();
    }

    public Map<String, List<String>> getComplement() throws SQLException {
        return new BienLouableDAO().getAllcomplements();
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
        page_nouveau_bail.getFrame().dispose();
    }
}

