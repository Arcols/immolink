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
import DAO.jdbc.*;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import classes.Logement;
import ihm.PageBaux;
import ihm.PageCharge;
import ihm.PageUnBail;

public class ModelePageUnBail {
    private PageUnBail pageUnBail;
    private List<Locataire> Locataireselected = new LinkedList<Locataire>();
    private int quotite_actuelle;
    private List<Integer> quotites = new LinkedList<>();
    private List<JSpinner> listSpinner;
    private List<JSpinner> spinners_selec = new ArrayList<>();
    private int idBail;
    private Boolean validation_quotités;

    public ModelePageUnBail(PageUnBail pageUnBail) {
        this.pageUnBail = pageUnBail;
        this.quotite_actuelle = 100;
        this.idBail = new BailDAO().getId(pageUnBail.getBail());
    }


    public void chargerDonneesBail(int idBail, PageUnBail page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);

            BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(bailDAO.getIdBienLouable(idBail));

            LogementDAO logementDAO = new DAO.jdbc.LogementDAO();
            Logement logement = logementDAO.read(bailDAO.getIdBienLouable(idBail));

            DevisDAO devisDAO = new DevisDAO();

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

    public void modifierLoyer(JFrame parentFrame,int idBail){
        BailDAO bailDAO = new DAO.jdbc.BailDAO();
        Bail bail = bailDAO.getBailFromId(idBail);

        JDialog dialog = new JDialog(parentFrame, "Modifier le loyer", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Voulez-vous modifier le loyer du bail ? \n"+
                "Attention, cette action ne peut-être éffectuée qu'une fois par année");
        label.setBounds(20, 30, 200, 25);
        dialog.add(label);

        JButton validerButton = new JButton("Valider");
        validerButton.setBounds(80, 100, 100, 30);
        dialog.add(validerButton);

        JButton quitterButton = new JButton("Quitter");
        quitterButton.setBounds(220, 100, 100, 30);
        dialog.add(quitterButton);

        validerButton.addActionListener(event -> {
            JDialog dialog2 = new JDialog(dialog, "Modifier le loyer", true);
            dialog2.setSize(400, 200);
            dialog2.setLayout(null);

            JLabel label2 = new JLabel("Entrez le nouveau loyer :");
            label2.setBounds(20, 30, 200, 25);
            dialog2.add(label2);

            JTextField loyerField = new JTextField();
            loyerField.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeurActuelle = bail.getLoyer();
            if (valeurActuelle != null) {
                loyerField.setText(String.valueOf(valeurActuelle));
            }

            dialog2.add(loyerField);

            JButton validerButton2 = new JButton("Valider");
            validerButton2.setBounds(150, 100, 100, 30);
            dialog2.add(validerButton2);

            validerButton2.addActionListener(event2 -> {
                try {
                    double loyer = Double.parseDouble(loyerField.getText());

                    // Vérification : le loyer ne peut pas être négatif
                    if (loyer < 0) {
                        JOptionPane.showMessageDialog(dialog2,
                                "Le loyer ne peut pas être une valeur négative.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la valeur est négative
                    }

                    bailDAO.updateLoyer(idBail, loyer);  // Met à jour le loyer dans la base
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

            dialog2.setLocationRelativeTo(parentFrame);
            dialog2.setVisible(true);
        });

        quitterButton.addActionListener(event -> {
            dialog.dispose();
        } );

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public ActionListener getModifierICC(JFrame parentFrame, int idBail) {
        return e -> {
            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);

            JDialog dialog = new JDialog(parentFrame, "Modifier l'ICC", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Entrez le nouvel ICC :");
            label.setBounds(20, 30, 200, 25);
            dialog.add(label);

            JTextField iccField = new JTextField();
            iccField.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeurActuelle = bail.getIcc();
            if (valeurActuelle != null) {
                iccField.setText(String.valueOf(valeurActuelle));
            }

            dialog.add(iccField);

            JButton validerButton = new JButton("Valider");
            validerButton.setBounds(150, 100, 100, 30);
            dialog.add(validerButton);
            validerButton.addActionListener(event -> {
                try {
                    double icc = Double.parseDouble(iccField.getText());

                    // Vérification : le loyer ne peut pas être négatif
                    if (icc < 0) {
                        JOptionPane.showMessageDialog(dialog,
                                "L'ICC ne peut pas être une valeur négative.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la valeur est négative
                    }

                    bailDAO.updateICC(idBail, icc);  // Met à jour l'ICC dans la base
                    bailDAO.updateDateDernierAnniversaire(idBail,nouvelleDateAnniversaire(bail.getDernierAnniversaire()));
                    JOptionPane.showMessageDialog(dialog,
                            "L'ICC a été mis à jour à " + icc + " €.",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    modifierLoyer(parentFrame,idBail);
                    refreshPage(e, idBail);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Veuillez entrer un nombre valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        };
    }

    public ActionListener getUpdateProvisionPourCharge(int idBail){
        return e -> {
            JDialog dialog = new JDialog(pageUnBail.getFrame(), "Modifier provision pour charger", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Entrez la nouvelle provision \n pour charges :");
            label.setBounds(20, 30, 250, 25);
            dialog.add(label);

            JTextField PPCField = new JTextField();
            PPCField.setBounds(260, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeurActuelle = Double.parseDouble(pageUnBail.getAffichageProvision().getText());
            if (valeurActuelle != null) {
                PPCField.setText(String.valueOf(valeurActuelle));
            }

            dialog.add(PPCField);

            JButton validerButton = new JButton("Valider");
            validerButton.setBounds(210, 100, 100, 30);
            dialog.add(validerButton);
            validerButton.addActionListener(event -> {
                new BailDAO().updateProvisionPourCharge(idBail, Double.parseDouble(PPCField.getText()));
                JOptionPane.showMessageDialog(dialog,
                        "Votre provision pour charge a bien été supprimée",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                pageUnBail.getFrame().dispose();
                PageUnBail pageUnBail = new PageUnBail(new BailDAO().getBailFromId(idBail));
            });
            JButton annulerButton = new JButton("Annuler");
            annulerButton.setBounds(90, 100, 100, 30);
            dialog.add(annulerButton);
            annulerButton.addActionListener(event -> dialog.dispose());
            dialog.setLocationRelativeTo(pageUnBail.getFrame());
            dialog.setVisible(true);
        };

    }

    public ActionListener getAjouterLocataire(int idBail) {
        return e -> {

            LouerDAO louerDAO = new DAO.jdbc.LouerDAO();

            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);
            String ville = pageUnBail.getAffichageVille().getText();
            String adresse = pageUnBail.getAffichageAdresse().getText();
            String complement = pageUnBail.getAffichageComplement().getText();


            try {
                String numFisc = new BienLouableDAO().getFiscFromCompl(ville, adresse, complement);
                BienLouable bien = new BienLouableDAO().readFisc(numFisc);
                Bail bail_actu = new BailDAO().getBailFromId(idBail);
                List<Integer> idLocBail = new LouerDAO().getIdLoc(new BailDAO().getId(bail_actu));

                for (int id : idLocBail) {
                    Locataireselected.add(new LocataireDAO().getLocFromId(id));
                    quotites.add(new LouerDAO().getQuotité(new BailDAO().getId(bail_actu), id));
                }
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }

            // Données fictives pour les locataires
            locatairesDuBail(idBail);
            List<Locataire> listlocataires = new DAO.jdbc.LocataireDAO().getAllLocataire();
            String[][] locataires = new String[listlocataires.size() - Locataireselected.size()][];
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
            DefaultTableModel model = new DefaultTableModel(locataires, columns) {
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
            popupFrame.setLocationRelativeTo(this.pageUnBail.getFrame());

            // Ajout d'un MouseListener pour détecter le double-clic
            selectionTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) { // Double-clic
                        int selectedRow = selectionTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            // Récupérer les données du locataire sélectionné
                            String nom = model.getValueAt(selectedRow, 0).toString();
                            String prenom = model.getValueAt(selectedRow, 1).toString();
                            String telephone = model.getValueAt(selectedRow, 2).toString();
                            Locataireselected.add(new DAO.jdbc.LocataireDAO().getLocataireByNomPrénomTel(nom, prenom, telephone));
                            setQuotite(idBail);
                            popupFrame.dispose();
                        }
                    }
                }
            });
            popupFrame.addWindowListener(whenClosed(e, idBail));

            // Afficher la fenêtre popup
            popupFrame.setVisible(true);
        };
    }

    public void setQuotite(int idBail) {
        validation_quotités = false;//Est vrai si la quotité des locataires a été mise à jour
        JDialog dialog = new JDialog((Frame) null, "Saisir la quotité des locataires", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        LocataireDAO locataireDAO = new DAO.jdbc.LocataireDAO();

        // Panel contenant les locataires et les quotités
        JPanel panelLocataires = new JPanel();
        panelLocataires.setLayout(new BoxLayout(panelLocataires, BoxLayout.Y_AXIS));
        // Liste des locataires avec champs de quotité
        List<JSpinner> quotiteSpinners = new LinkedList<>();
        List<Locataire> locataires = Locataireselected;  // Liste des locataires sélectionnés
        Map<Locataire, JSpinner> quotiteMap = new HashMap<>();  // Map pour associer chaque locataire à sa quotité

        for (Locataire locataire : locataires) {
            JPanel locatairePanel = new JPanel();
            locatairePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Afficher le nom du locataire
            JLabel nomLocataireLabel = new JLabel(locataire.getNom() + " " + locataire.getPrénom());
            locatairePanel.add(nomLocataireLabel);

            // Créer un JSpinner pour la quotité de ce locataire
            JSpinner quotiteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            quotiteSpinner.setValue(0);  // Valeur initiale de 100% (modifiable)
            locatairePanel.add(quotiteSpinner);

            // Ajouter le locataire et son spinner dans la map
            quotiteMap.put(locataire, quotiteSpinner);

            // Ajouter au panel
            panelLocataires.add(locatairePanel);
        }

        this.listSpinner = new ArrayList<>(quotiteMap.values());
        for (JSpinner j : listSpinner) {
            j.addChangeListener(e -> limitQuotité(e));
        }

        JScrollPane scrollPane = new JScrollPane(panelLocataires);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bouton de validation
        JButton validerButton = new JButton("Valider");
        dialog.add(validerButton, BorderLayout.SOUTH);

        validerButton.addActionListener(event -> {
            if (quotite_actuelle > 0) {
                JOptionPane.showMessageDialog(dialog,
                        "100% de la quotité doit être répartie sur les différents locataires",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return; // Arrête l'exécution si la quotité n'est pas entierement repartie
            } else {
                // Récupérer les quotités pour chaque locataire et les mettre à jour dans la base
                LouerDAO louerDAO = new LouerDAO();  // Création de l'instance du DAO pour la mise à jour
                for (Locataire locataire : locataires) {
                    int quotite = (Integer) quotiteMap.get(locataire).getValue();  // Récupérer la quotité pour ce locataire

                    // Mettre à jour la quotité dans la base de données
                    if (louerDAO.locInBail(locataireDAO.getId(locataire), idBail)){
                        louerDAO.updateQuotite(idBail, new LocataireDAO().getId(locataire), quotite);
                        validation_quotités = true;
                    } else {
                        try {
                            louerDAO.create(locataire,new BailDAO().getBailFromId(idBail),quotite);
                            validation_quotités = true;
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


    public void limitQuotité(ChangeEvent e) {
        Object source = e.getSource();
        JSpinner spinner_last_mod = (JSpinner) source;

        // Ajouter le spinner modifié à la liste des sélectionnés
        if (!spinners_selec.contains(spinner_last_mod)) {
            spinners_selec.add(spinner_last_mod);
        }

        // Mettre à jour la quotité actuelle en fonction de la nouvelle valeur
        int valeur_spinner = (Integer) spinner_last_mod.getValue();
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
        for (JSpinner j : listSpinner) {
            if (!j.equals(spinner_last_mod)) {
                SpinnerNumberModel model = (SpinnerNumberModel) j.getModel();
                int currentValue = (Integer) j.getValue();

                // Ajuster la borne supérieure en fonction de la quotité restante
                model.setMaximum(quotite_actuelle + currentValue);
            }
        }
    }

    private void locatairesDuBail(int idBail){
        this.Locataireselected.clear();
        List<Integer> idLocs =new LouerDAO().getIdLoc(idBail);
        for (Integer idLoc : idLocs){
            this.Locataireselected.add(new LocataireDAO().getLocFromId(idLoc));
        }
    }

    public ActionListener supprimerLoc(){
        return e->{
            if(pageUnBail.getTableau_locataire().getComponentCount()/2 >1) {
                // Récupérer l'ID du locataire depuis l'ActionCommand
                JButton bouton = (JButton) e.getSource();
                int locataireId = Integer.parseInt(bouton.getActionCommand());

                // Récupérer l'ID du bail
                int bailId = idBail;

                JDialog dialog = new JDialog((Frame) null, "Redistribuez la quotité sur les locataires restants", true);
                dialog.setSize(350, 200);
                dialog.setLayout(new BorderLayout());

                LocataireDAO locataireDAO = new DAO.jdbc.LocataireDAO();

                // Panel contenant les locataires et les quotités
                JPanel panelLocataires = new JPanel();
                panelLocataires.setLayout(new BoxLayout(panelLocataires, BoxLayout.Y_AXIS));

                // Liste des locataires avec champs de quotité
                locatairesDuBail(idBail);
                List<Locataire> locataires = Locataireselected;  // Liste des locataires sélectionnés
                Map<Locataire, JSpinner> quotiteMap = new HashMap<>();  // Map pour associer chaque locataire à sa quotité

                for (Locataire locataire : locataires) {
                    int loc_restant = new LocataireDAO().getId(locataire);
                    if( loc_restant != locataireId){
                    JPanel locatairePanel = new JPanel();
                    locatairePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                    // Afficher le nom du locataire
                    JLabel nomLocataireLabel = new JLabel(locataire.getNom() + " " + locataire.getPrénom());
                    locatairePanel.add(nomLocataireLabel);

                    // Créer un JSpinner pour la quotité de ce locataire
                    JSpinner quotiteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
                    quotiteSpinner.setValue(0);  // Valeur initiale de 100% (modifiable)
                    locatairePanel.add(quotiteSpinner);

                    // Ajouter le locataire et son spinner dans la map
                    quotiteMap.put(locataire, quotiteSpinner);

                    // Ajouter au panel
                    panelLocataires.add(locatairePanel);
                    }
                }

                this.listSpinner = new ArrayList<>(quotiteMap.values());
                for (JSpinner j:listSpinner){
                    j.addChangeListener( y-> limitQuotité(y));
                }

                JScrollPane scrollPane = new JScrollPane(panelLocataires);
                dialog.add(scrollPane, BorderLayout.CENTER);

                // Bouton de validation
                JButton validerButton = new JButton("Valider");
                dialog.add(validerButton, BorderLayout.SOUTH);

                validerButton.addActionListener(event -> {
                    if (quotite_actuelle > 0) {
                        JOptionPane.showMessageDialog(dialog,
                                "100% de la quotité doit être répartie sur les différents locataires",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la quotité n'est pas entierement repartie
                    } else {
                        // Récupérer les quotités pour chaque locataire et les mettre à jour dans la base
                        LouerDAO louerDAO = new LouerDAO();// Création de l'instance du DAO pour la mise à jour
                        Locataire loc_to_remove = new LocataireDAO().getLocFromId(locataireId);
                        locataires.remove(loc_to_remove);
                        Locataireselected.remove(loc_to_remove);
                        for (Locataire locataire : locataires) {
                            int quotite = (Integer) quotiteMap.get(locataire).getValue();  // Récupérer la quotité pour ce locataire

                            // Mettre à jour la quotité dans la base de données
                            louerDAO.updateQuotite(bailId, new LocataireDAO().getId(locataire), quotite);
                    }
                        new LouerDAO().delete(bailId, locataireId);
                        dialog.dispose();
                        refreshPage(e,idBail);
                    }
                });
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Il ne reste qu'un seul locataire sur ce bail, veuillez supprimer le bail ou ajouter un nouveau locataire", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };

    }

    private void refreshPage(ActionEvent e, int idBail) {
        BailDAO bailDAO = new DAO.jdbc.BailDAO();
        Bail bail = bailDAO.getBailFromId(idBail);

        // Obtenez la fenêtre actuelle à partir de l'événement
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        JFrame ancetre = (JFrame) SwingUtilities.getWindowAncestor(ancienneFenetre);

        // Assurez-vous de fermer l'ancienne fenêtre avant de créer une nouvelle page
        if (ancienneFenetre != null) {
            ancienneFenetre.dispose();  // Ferme l'ancienne fenêtre
        }
        // Créez une nouvelle page avec les données du bail
        PageUnBail nouvellePage = new PageUnBail(bail);
        nouvellePage.getFrame().setVisible(true);  // Affiche la nouvelle page
    }

    public ActionListener BtnPageCharge(){
        return e-> {
            int id_bail = new DAO.jdbc.BailDAO().getId(pageUnBail.getBail());
            pageUnBail.getFrame().dispose();
            PageCharge pagecharge = new PageCharge(id_bail);
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            pageUnBail.getFrame().dispose();
            PageBaux pageMesBiens = new PageBaux();
            PageBaux.main(null);
        };
    }

    public WindowListener whenClosed(ActionEvent e, int idBail) {
            return (new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {
                    // Appeler refreshPage lorsque la fenêtre est fermée
                    refreshPage(e, idBail);
                }
            });
        }

    public java.sql.Date nouvelleDateAnniversaire(java.sql.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearsToAdd = currentYear - calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, yearsToAdd);
        return new Date(calendar.getTimeInMillis());
    }


    public ActionListener deleteBail(Integer idBail) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(pageUnBail.getFrame(), "Suppression du bail", true);
                dialog.setSize(400, 200);
                dialog.setLayout(null);

                JLabel label = new JLabel("Etes-vous sur de vouloir supprimer votre bail ?");
                label.setBounds(20, 30, 400, 25);
                dialog.add(label);

                JButton validerButton = new JButton("Valider");
                validerButton.setBounds(210, 100, 100, 30);
                dialog.add(validerButton);
                JButton annulerButton = new JButton("Annuler");
                annulerButton.setBounds(90, 100, 100, 30);
                dialog.add(annulerButton);
                annulerButton.addActionListener(event -> dialog.dispose());
                validerButton.addActionListener(event -> {
                    new BailDAO().delete(idBail);
                    JOptionPane.showMessageDialog(dialog,
                            "Votre bien a été supprimé",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    pageUnBail.getFrame().dispose();
                    PageBaux pageMesBaux = new PageBaux();
                    pageMesBaux.main(null);
                });
                dialog.setLocationRelativeTo(pageUnBail.getFrame());
                dialog.setVisible(true);
            }
        };
    }

}
