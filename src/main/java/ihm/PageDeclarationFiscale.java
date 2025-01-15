package ihm;

import DAO.DAOException;
import modele.*;
import modele.Menu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PageDeclarationFiscale {

    private JFrame frame;
    private JLabel logo;
    private String annee;
    private JPanel contenu;
    private JTable table;

    public PageDeclarationFiscale(String annee) {
        this.annee = annee;
        this.initialize(annee);
    }

    private void initialize(String annee) {
        this.frame = new JFrame();

        ModelePageDeclarationFiscale modele=new ModelePageDeclarationFiscale(this);

        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        Menu m = new Menu(this.frame);

        JPanel menu_bouttons = new JPanel();

        entete.add(menu_bouttons, BorderLayout.CENTER);
        menu_bouttons.setLayout(new GridLayout(0, 4, 0, 0));
        menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(Charte.ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);
        b_accueil.addActionListener(m);

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(Charte.ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);
        menu_bouttons.add(b_baux);
        b_baux.addActionListener(m);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(Charte.ENTETE.getCouleur());
        b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_biens);
        menu_bouttons.add(b_biens);
        b_biens.addActionListener(m);

        JButton b_notifs = null;
        try {
            b_notifs = new JButton("Notifications ("+m.getNbNotifs()+")");
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        b_notifs.setBorderPainted(false);
        b_notifs.setBackground(Charte.ENTETE.getCouleur());
        b_notifs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_notifs);
        menu_bouttons.add(b_notifs);
        b_notifs.addActionListener(m);

        JPanel body = new JPanel();
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Déclaration fiscale de "+annee, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        body.add(titleLabel, BorderLayout.NORTH);

        // Créer la table avec ce modèle
        table = new JTable(modele.LoadToDataPageDeclarationFiscaleToTable());
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // Adresse
        columnModel.getColumn(1).setPreferredWidth(150); // Nb local
        columnModel.getColumn(2).setPreferredWidth(50); // frais gestion
        columnModel.getColumn(3).setPreferredWidth(75); // total travaux
        columnModel.getColumn(4).setPreferredWidth(75); // taxes foncière
        columnModel.getColumn(5).setPreferredWidth(200); // détails travaux
        // Ajouter la table dans un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(table);
        body.add(scrollPane, BorderLayout.CENTER);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageDeclarationFiscale.this.frame,
                        PageDeclarationFiscale.this.logo, 3, 8);
                int frameWidth = PageDeclarationFiscale.this.frame.getWidth();
                int frameHeight = PageDeclarationFiscale.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Vérifier s'il s'agit d'un double-clic
                if (evt.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = table.getSelectedRow();
                    modele.OuvrirDetailTravaux(modele.getLignedevis().get(row));
                }
            }
        });
        quitter.addActionListener(modele.quitterPage());
        this.frame.setVisible(true);
    }

    public Frame getFrame() {
        return this.frame;
    }

    public JTable getTable() {
        return table;
    }

    public String getAnnee() {
        return annee;
    }
}

