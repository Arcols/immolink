package modele;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LocataireDAO;
import DAO.jdbc.LogementDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import classes.Logement;
import ihm.PageBaux;
import ihm.PageCharge;
import ihm.PageUnBail;

public class ModelePageUnBail {
    private final PageUnBail page_un_bail;
    private final List<Locataire> locataires_choisis = new LinkedList<>();
    private int quotite_actuelle;
    private final List<Integer> quotites = new LinkedList<>();
    private List<JSpinner> liste_spinner;
    private final List<JSpinner> spinners_selec = new ArrayList<>();
    private final int id_bail;

    public ModelePageUnBail(PageUnBail page_un_bail) {
        this.page_un_bail = page_un_bail;
        this.quotite_actuelle = 100;
        this.id_bail = new BailDAO().getId(page_un_bail.getBail());
    }

    public void chargerDonneesBail(int id_bail, PageUnBail page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BailDAO bail_DAO = new DAO.jdbc.BailDAO();
            Bail bail = bail_DAO.getBailFromId(id_bail);

            BienLouableDAO bien_louable_DAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bien_louable_DAO.readId(bail_DAO.getIdBienLouable(id_bail));

            LogementDAO logement_DAO = new DAO.jdbc.LogementDAO();
            Logement logement = logement_DAO.read(bail_DAO.getIdBienLouable(id_bail));

            if (bail != null) {
                // Mise à jour des labels avec les informations du bien
                page.getAffichageVille().setText(bienLouable.getVille());
                page.getAffichageAdresse().setText(bienLouable.getAdresse());
                page.getAffichageComplement().setText(bienLouable.getComplementAdresse());
                if(logement.getSurface()==0.0){
                    page.getAffichageSurface().setText("0.0");
                } else {
                    page.getAffichageSurface().setText(String.valueOf(logement.getSurface()));
                }
                page.getAffichageNbPieces().setText(String.valueOf(logement.getNbPiece()));
                page.getAffichageLoyer().setText(String.valueOf(bail.getLoyer()));
                page.getAffichageProvision().setText(String.valueOf(bail.getCharge()));
                page.getAffichageGarantie().setText(String.valueOf(bail.getDepotGarantie()));
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }

    public void modifierLoyer(JFrame fenetre_mere,int id_bail){
        BailDAO bail_DAO = new DAO.jdbc.BailDAO();
        Bail bail = bail_DAO.getBailFromId(id_bail);

        JDialog dialog = new JDialog(fenetre_mere, "Modifier le loyer", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Voulez-vous modifier le loyer du bail ? \n"+
                "Attention, cette action ne peut-être éffectuée qu'une fois par année");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JButton bouton_valider = new JButton("Valider");
        bouton_valider.setBounds(80, 100, 100, 30);
        dialog.add(bouton_valider);

        JButton bouton_quitter = new JButton("Quitter");
        bouton_quitter.setBounds(220, 100, 100, 30);
        dialog.add(bouton_quitter);

        bouton_valider.addActionListener(event -> {
            JDialog dialog2 = new JDialog(dialog, "Modifier le loyer", true);
            dialog2.setSize(400, 200);
            dialog2.setLayout(null);

            JLabel label2 = new JLabel("Entrez le nouveau loyer :");
            label2.setBounds(20, 30, 200, 25);
            dialog2.add(label2);

            JTextField champ_loyer = new JTextField();
            champ_loyer.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeur_actuelle = bail.getLoyer();
            champ_loyer.setText(String.valueOf(valeur_actuelle));

            dialog2.add(champ_loyer);

            JButton bouton_valider2 = new JButton("Valider");
            bouton_valider2.setBounds(150, 100, 100, 30);
            dialog2.add(bouton_valider2);

            bouton_valider2.addActionListener(event2 -> {
                try {
                    double loyer = Double.parseDouble(champ_loyer.getText());

                    // Vérification : le loyer ne peut pas être négatif
                    if (loyer < 0) {
                        JOptionPane.showMessageDialog(dialog2,
                                "Le loyer ne peut pas être une valeur négative.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la valeur est négative
                    }

                    bail_DAO.updateLoyer(id_bail, loyer);  // Met à jour le loyer dans la base
                    JOptionPane.showMessageDialog(dialog2,
                            "Le loyer a été mis à jour à " + loyer + " €.",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog2.dispose();
                    dialog.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog2,
                            "Veuillez entrer un nombre valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog2.setLocationRelativeTo(fenetre_mere);
            dialog2.setVisible(true);
        });

        bouton_quitter.addActionListener(event -> {
            dialog.dispose();
        } );

        dialog.setLocationRelativeTo(fenetre_mere);
        dialog.setVisible(true);
    }

    public ActionListener getModifierICC(JFrame fenetre_mere, int id_bail) {
        return e -> {
            BailDAO bail_DAO = new DAO.jdbc.BailDAO();
            Bail bail = bail_DAO.getBailFromId(id_bail);

            JDialog dialog = new JDialog(fenetre_mere, "Modifier l'ICC", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Entrez le nouvel ICC :");
            label.setBounds(20, 30, 200, 25);
            dialog.add(label);

            JTextField champ_ICC = new JTextField();
            champ_ICC.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeur_actuelle = bail.getIcc();
            champ_ICC.setText(String.valueOf(valeur_actuelle));

            dialog.add(champ_ICC);

            JButton bouton_valider = new JButton("Valider");
            bouton_valider.setBounds(150, 100, 100, 30);
            dialog.add(bouton_valider);
            bouton_valider.addActionListener(event -> {
                try {
                    double icc = Double.parseDouble(champ_ICC.getText());

                    // Vérification : le loyer ne peut pas être négatif
                    if (icc < 0) {
                        JOptionPane.showMessageDialog(dialog,
                                "L'ICC ne peut pas être une valeur négative.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la valeur est négative
                    }

                    bail_DAO.updateICC(id_bail, icc);  // Met à jour l'ICC dans la base
                    bail_DAO.updateDateDernierAnniversaire(id_bail,nouvelleDateAnniversaire(bail.getDernierAnniversaire()));
                    JOptionPane.showMessageDialog(dialog,
                            "L'ICC a été mis à jour à " + icc + " €.",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    modifierLoyer(fenetre_mere,id_bail);
                    refreshPage(e, id_bail);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Veuillez entrer un nombre valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setLocationRelativeTo(fenetre_mere);
            dialog.setVisible(true);
        };
    }

    public ActionListener getUpdateProvisionPourCharge(int id_bail){
        return e -> {
            JDialog dialog = new JDialog(page_un_bail.getFrame(), "Modifier provision pour charger", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Entrez la nouvelle provision \n pour charges :");
            label.setBounds(20, 30, 250, 25);
            dialog.add(label);

            JTextField champ_PPC = new JTextField();
            champ_PPC.setBounds(260, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeur_actuelle = Double.parseDouble(page_un_bail.getAffichageProvision().getText());
            champ_PPC.setText(String.valueOf(valeur_actuelle));

            dialog.add(champ_PPC);

            JButton bouton_valider = new JButton("Valider");
            bouton_valider.setBounds(210, 100, 100, 30);
            dialog.add(bouton_valider);
            bouton_valider.addActionListener(event -> {
                new BailDAO().updateProvisionPourCharge(id_bail, Double.parseDouble(champ_PPC.getText()));
                JOptionPane.showMessageDialog(dialog,
                        "Votre provision pour charge a bien été supprimée",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                page_un_bail.getFrame().dispose();
                int x=page_un_bail.getFrame().getX();
                int y=page_un_bail.getFrame().getY();

                new PageUnBail(new BailDAO().getBailFromId(id_bail),x,y);
            });
            JButton bouton_annuler = new JButton("Annuler");
            bouton_annuler.setBounds(90, 100, 100, 30);
            dialog.add(bouton_annuler);
            bouton_annuler.addActionListener(event -> dialog.dispose());
            dialog.setLocationRelativeTo(page_un_bail.getFrame());
            dialog.setVisible(true);
        };

    }

    public ActionListener getAjouterLocataire(int id_bail) {
        return e -> {

            BailDAO bail_DAO = new DAO.jdbc.BailDAO();
            bail_DAO.getBailFromId(id_bail);
            String ville = page_un_bail.getAffichageVille().getText();
            String adresse = page_un_bail.getAffichageAdresse().getText();
            String complement = page_un_bail.getAffichageComplement().getText();


            try {
                String num_fiscal = new BienLouableDAO().getFiscFromCompl(ville, adresse, complement);
                new BienLouableDAO().readFisc(num_fiscal);
                Bail bail_actu = new BailDAO().getBailFromId(id_bail);
                List<Integer> idLocBail = new LouerDAO().getIdLoc(new BailDAO().getId(bail_actu));

                for (int id : idLocBail) {
                    locataires_choisis.add(new LocataireDAO().getLocFromId(id));
                    quotites.add(new LouerDAO().getQuotite(new BailDAO().getId(bail_actu), id));
                }
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }

            // Données fictives pour les locataires
            locatairesDuBail(id_bail);
            List<Locataire> liste_locataires = new DAO.jdbc.LocataireDAO().getAllLocataire();
            String[][] locataires = new String[liste_locataires.size() - locataires_choisis.size()][];
            String[] ligne;
            int i = 0;
            for (Locataire l : liste_locataires) {
                if (!locataires_choisis.contains(l)) {
                    ligne = new String[]{l.getNom(), l.getPrenom(), l.getTelephone()};
                    locataires[i] = ligne;
                    i++;
                }
            }
            // Colonnes de la table
            String[] colonnes = {"Nom", "Prénom", "Téléphone"};

            // Modèle pour la table
            DefaultTableModel model = new DefaultTableModel(locataires, colonnes) {
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
            JFrame popupFrame = new JFrame("Sélectionner un locataire");
            popupFrame.setSize(400, 300);
            popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popupFrame.add(scrollPane_pop_up);
            popupFrame.setLocationRelativeTo(this.page_un_bail.getFrame());

            // Ajout d'un MouseListener pour détecter le double-clic
            table_selection.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) { // Double-clic
                        int ligne_selectionnee = table_selection.getSelectedRow();
                        if (ligne_selectionnee >= 0) {
                            // Récupérer les données du locataire sélectionné
                            String nom = model.getValueAt(ligne_selectionnee, 0).toString();
                            String prenom = model.getValueAt(ligne_selectionnee, 1).toString();
                            String telephone = model.getValueAt(ligne_selectionnee, 2).toString();
                            locataires_choisis.add(new DAO.jdbc.LocataireDAO().getLocataireByNomPrenom(nom, prenom, telephone));
                            setQuotite(id_bail);
                            popupFrame.dispose();
                        }
                    }
                }
            });
            popupFrame.addWindowListener(whenClosed(e, id_bail));

            // Afficher la fenêtre popup
            popupFrame.setVisible(true);
        };
    }

    public void setQuotite(int id_bail) {
        JDialog dialog = new JDialog((Frame) null, "Saisir la quotité des locataires", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        LocataireDAO locataire_DAO = new DAO.jdbc.LocataireDAO();

        // Panel contenant les locataires et les quotités
        JPanel panel_locataires = new JPanel();
        panel_locataires.setLayout(new BoxLayout(panel_locataires, BoxLayout.Y_AXIS));
        // Liste des locataires avec champs de quotité
        List<Locataire> locataires = locataires_choisis;  // Liste des locataires sélectionnés
        Map<Locataire, JSpinner> map_quotite = new HashMap<>();  // Map pour associer chaque locataire à sa quotité

        for (Locataire locataire : locataires) {
            JPanel panel_locataire = new JPanel();
            panel_locataire.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Afficher le nom du locataire
            JLabel label_nom_locataire = new JLabel(locataire.getNom() + " " + locataire.getPrenom());
            panel_locataire.add(label_nom_locataire);

            // Créer un JSpinner pour la quotité de ce locataire
            JSpinner spinner_quotite = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            spinner_quotite.setValue(0);  // Valeur initiale de 100% (modifiable)
            panel_locataire.add(spinner_quotite);

            // Ajouter le locataire et son spinner dans la map
            map_quotite.put(locataire, spinner_quotite);

            // Ajouter au panel
            panel_locataires.add(panel_locataire);
        }

        this.liste_spinner = new ArrayList<>(map_quotite.values());
        for (JSpinner j : liste_spinner) {
            j.addChangeListener(e -> limitQuotite(e));
        }

        JScrollPane scrollPane = new JScrollPane(panel_locataires);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bouton de validation
        JButton bouton_valider = new JButton("Valider");
        dialog.add(bouton_valider, BorderLayout.SOUTH);

        bouton_valider.addActionListener(event -> {
            if (quotite_actuelle > 0) {
                JOptionPane.showMessageDialog(dialog,
                        "100% de la quotité doit être répartie sur les différents locataires",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Récupérer les quotités pour chaque locataire et les mettre à jour dans la base
                LouerDAO louer_DAO = new LouerDAO();  // Création de l'instance du DAO pour la mise à jour
                for (Locataire locataire : locataires) {
                    int quotite = (Integer) map_quotite.get(locataire).getValue();  // Récupérer la quotité pour ce locataire

                    // Mettre à jour la quotité dans la base de données
                    if (louer_DAO.locInBail(locataire_DAO.getId(locataire), id_bail)){
                        louer_DAO.updateQuotite(id_bail, new LocataireDAO().getId(locataire), quotite);
                    } else {
                        try {
                            louer_DAO.create(locataire,new BailDAO().getBailFromId(id_bail),quotite);
                        } catch (DAOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                dialog.dispose();
            }
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


    public void limitQuotite(ChangeEvent e) {
        Object source = e.getSource();
        JSpinner spinner_last_mod = (JSpinner) source;

        // Ajouter le spinner modifié à la liste des sélectionnés
        if (!spinners_selec.contains(spinner_last_mod)) {
            spinners_selec.add(spinner_last_mod);
        }

        // Mettre à jour la quotité actuelle en fonction de la nouvelle valeur
        spinner_last_mod.getValue();
        quotite_actuelle = 100 - spinners_selec.stream()
                .mapToInt(spinner -> (Integer) spinner.getValue())
                .sum();

        // Vérifier que la quotité reste valide
        if (quotite_actuelle < 0) {
            JOptionPane.showMessageDialog(null,
                    "La somme des quotités ne peut pas dépasser 100.",
                    "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE);
            spinner_last_mod.setValue(0);
            return;
        }

        // Mettre à jour les modèles des autres JSpinner
        for (JSpinner j : liste_spinner) {
            if (!j.equals(spinner_last_mod)) {
                SpinnerNumberModel model = (SpinnerNumberModel) j.getModel();
                int currentValue = (Integer) j.getValue();

                // Ajuster la borne supérieure en fonction de la quotité restante
                model.setMaximum(quotite_actuelle + currentValue);
            }
        }
    }

    private void locatairesDuBail(int id_bail){
        this.locataires_choisis.clear();
        List<Integer> idLocs =new LouerDAO().getIdLoc(id_bail);
        for (Integer idLoc : idLocs){
            this.locataires_choisis.add(new LocataireDAO().getLocFromId(idLoc));
        }
    }

    public ActionListener supprimerLoc(){
        return e->{
            if(page_un_bail.getTableau_locataire().getComponentCount()/2 >1) {
                // Récupérer l'ID du locataire depuis l'ActionCommand
                JButton bouton = (JButton) e.getSource();
                int locataireId = Integer.parseInt(bouton.getActionCommand());

                // Récupérer l'ID du bail
                int bailId = id_bail;

                JDialog dialog = new JDialog((Frame) null, "Redistribuez la quotité sur les locataires restants", true);
                dialog.setSize(350, 200);
                dialog.setLayout(new BorderLayout());

                // Panel contenant les locataires et les quotités
                JPanel panel_locataires = new JPanel();
                panel_locataires.setLayout(new BoxLayout(panel_locataires, BoxLayout.Y_AXIS));

                // Liste des locataires avec champs de quotité
                locatairesDuBail(id_bail);
                List<Locataire> locataires = locataires_choisis;  // Liste des locataires sélectionnés
                Map<Locataire, JSpinner> map_quotite = new HashMap<>();  // Map pour associer chaque locataire à sa quotité

                for (Locataire locataire : locataires) {
                    int loc_restant = new LocataireDAO().getId(locataire);
                    if( loc_restant != locataireId){
                    JPanel panel_locataire = new JPanel();
                    panel_locataire.setLayout(new FlowLayout(FlowLayout.LEFT));

                    // Afficher le nom du locataire
                    JLabel label_nom_locataire = new JLabel(locataire.getNom() + " " + locataire.getPrenom());
                    panel_locataire.add(label_nom_locataire);

                    // Créer un JSpinner pour la quotité de ce locataire
                    JSpinner spinner_quotite = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
                    spinner_quotite.setValue(0);  // Valeur initiale de 100% (modifiable)
                    panel_locataire.add(spinner_quotite);

                    // Ajouter le locataire et son spinner dans la map
                    map_quotite.put(locataire, spinner_quotite);

                    // Ajouter au panel
                    panel_locataires.add(panel_locataire);
                    }
                }

                this.liste_spinner = new ArrayList<>(map_quotite.values());
                for (JSpinner j:liste_spinner){
                    j.addChangeListener( y-> limitQuotite(y));
                }

                JScrollPane scrollPane = new JScrollPane(panel_locataires);
                dialog.add(scrollPane, BorderLayout.CENTER);

                // Bouton de validation
                JButton bouton_valider = new JButton("Valider");
                dialog.add(bouton_valider, BorderLayout.SOUTH);

                bouton_valider.addActionListener(event -> {
                    if (quotite_actuelle > 0) {
                        JOptionPane.showMessageDialog(dialog,
                                "100% de la quotité doit être répartie sur les différents locataires",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        // Arrête l'exécution si la quotité n'est pas entierement repartie
                    } else {
                        // Récupérer les quotités pour chaque locataire et les mettre à jour dans la base
                        LouerDAO louer_DAO = new LouerDAO();// Création de l'instance du DAO pour la mise à jour
                        Locataire loc_to_remove = new LocataireDAO().getLocFromId(locataireId);
                        locataires.remove(loc_to_remove);
                        locataires_choisis.remove(loc_to_remove);
                        for (Locataire locataire : locataires) {
                            int quotite = (Integer) map_quotite.get(locataire).getValue();  // Récupérer la quotité pour ce locataire

                            // Mettre à jour la quotité dans la base de données
                            louer_DAO.updateQuotite(bailId, new LocataireDAO().getId(locataire), quotite);
                    }
                    new LouerDAO().delete(bailId, locataireId);
                    dialog.dispose();
                    refreshPage(e,id_bail);
                    }
                });
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Il ne reste qu'un seul locataire sur ce bail, veuillez supprimer le bail ou ajouter un nouveau locataire", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };

    }

    private void refreshPage(ActionEvent e, int id_bail) {
        BailDAO bail_DAO = new DAO.jdbc.BailDAO();
        Bail bail = bail_DAO.getBailFromId(id_bail);

        // Obtenez la fenêtre actuelle à partir de l'événement
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        SwingUtilities.getWindowAncestor(ancienne_fenetre);

        // Assurez-vous de fermer l'ancienne fenêtre avant de créer une nouvelle page
        ancienne_fenetre.dispose();  // Ferme l'ancienne fenêtre
        // Créez une nouvelle page avec les données du bail
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();

        PageUnBail nouvelle_page = new PageUnBail(bail,x,y);
        nouvelle_page.getFrame().setVisible(true);  // Affiche la nouvelle page
    }

    public ActionListener BtnPageCharge(){
        return e-> {
            int id_bail = new DAO.jdbc.BailDAO().getId(page_un_bail.getBail());
            page_un_bail.getFrame().dispose();
            int x=page_un_bail.getFrame().getX();
            int y=page_un_bail.getFrame().getY();
            new PageCharge(id_bail,x,y);
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            page_un_bail.getFrame().dispose();
            int x=page_un_bail.getFrame().getX();
            int y=page_un_bail.getFrame().getY();
            new PageBaux(x,y);
        };
    }

    public WindowListener whenClosed(ActionEvent e, int id_bail) {
            return (new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {
                    // Appeler refreshPage lorsque la fenêtre est fermée
                    refreshPage(e, id_bail);
                }
            });
        }

    public java.sql.Date nouvelleDateAnniversaire(java.sql.Date date) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(date);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearsToAdd = currentYear - calendrier.get(Calendar.YEAR);
        calendrier.add(Calendar.YEAR, yearsToAdd);
        return new Date(calendrier.getTimeInMillis());
    }


    public ActionListener deleteBail(Integer id_bail) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(page_un_bail.getFrame(), "Suppression du bail", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir supprimer votre bail ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton bouton_valider = new JButton("Valider");
                bouton_valider.setBounds(210, 100, 100, 30);
                dialog.add(bouton_valider);
                JButton bouton_annuler = new JButton("Annuler");
                bouton_annuler.setBounds(90, 100, 100, 30);
                dialog.add(bouton_annuler);
                bouton_annuler.addActionListener(event -> dialog.dispose());
                bouton_valider.addActionListener(event -> {
                    try {
                        new BailDAO().delete(id_bail);
                    } catch (DAOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(dialog,
                            "Votre bien a été supprimé",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    page_un_bail.getFrame().dispose();
                    int x=page_un_bail.getFrame().getX();
                    int y=page_un_bail.getFrame().getY();
                    PageBaux page_mes_baux = new PageBaux(x,y);
                });
                dialog.setLocationRelativeTo(page_un_bail.getFrame());
                dialog.setVisible(true);
            }
        };
    }

    public List<Integer> getListIdLoc() {
        return new LouerDAO().getIdLoc(new DAO.jdbc.BailDAO().getId(page_un_bail.getBail()));
    }

    public Locataire getLocataire(int id_loc){
        return  new LocataireDAO().getLocFromId(id_loc);
    }

    public int getIdBail(){
       return new BailDAO().getId(page_un_bail.getBail());
    }
    public void creerTableauLocataire(){
        int row = 0; // Initialiser le compteur de ligne pour GridBagLayout
        String[] nom_locataires= getNomsLocataire();
        List<Integer> id_locataires = getListIdLoc();
        for (int j = 0; j < nom_locataires.length; j++) {
            // Récupérer l'ID du locataire correspondant
            int locataire_id = id_locataires.get(j);

            // Créer le label pour chaque locataire
            JLabel label_loc = new JLabel(nom_locataires[j]);
            page_un_bail.getGbc_loc().gridx = 0; // Première colonne pour le label
            page_un_bail.getGbc_loc().gridy = row;
            page_un_bail.getTableau_locataire().add(label_loc, page_un_bail.getGbc_loc());

            // Créer le bouton "Supprimer" pour chaque locataire
            JButton supprimer = new JButton("Supprimer");
            page_un_bail.getGbc_loc().gridx = 1; // Deuxième colonne pour le bouton
            page_un_bail.getTableau_locataire().add(supprimer, page_un_bail.getGbc_loc());

            // Associer l'ID du locataire au bouton
            supprimer.setActionCommand(String.valueOf(locataire_id));

            // Ajouter l'ActionListener
            supprimer.addActionListener(supprimerLoc());
            row++; // Incrémenter la ligne pour le prochain locataire
        }
    }
    public String[] getNomsLocataire() {
        List<Integer>  id_locataires = getListIdLoc();
        String[] nom_locataires = new String[id_locataires.size()];
        int i = 0;
        for (int id : id_locataires) {
            Locataire loc = getLocataire(id);
            nom_locataires[i] = loc.getNom();
            i++;
        }
        return  nom_locataires;
    }

    public java.sql.Date enleveUneAnneeADate(java.sql.Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,-1);
        return new java.sql.Date(calendar.getTimeInMillis());
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
        page_un_bail.getFrame().dispose();
    }

}
