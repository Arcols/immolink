package ihm;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modele.ModelePageCharge;

public class PageCharge extends PageAbstraite {
    private ModelePageCharge modele;
    private final int id_bail;
    private JPanel contenu;

    public PageCharge(Integer id_bail,int x,int y) {
        super(x,y);
        this.id_bail = id_bail;
        modele=new ModelePageCharge(this);
        this.CreerSpecific();
    }

    public void CreerSpecific(){
        JPanel titre = new JPanel();
        panel_body.add(titre, BorderLayout.NORTH);

        JLabel label_titre = new JLabel("Liste des charges", SwingConstants.CENTER);
        label_titre.setFont(new Font("Arial", Font.BOLD, 16));
        panel_body.add(label_titre, BorderLayout.NORTH);

        contenu = new JPanel();
        panel_body.add(contenu, BorderLayout.CENTER);
        contenu.setLayout(new BorderLayout(0, 0));

        int annee_actuelle=LocalDate.now().getYear();

        createInfoCharge(annee_actuelle,BorderLayout.CENTER);
        createInfoCharge(annee_actuelle-1,BorderLayout.SOUTH);

        JPanel panel_bouton = new JPanel();
        panel_body.add(panel_bouton, BorderLayout.SOUTH);

        JButton archivage = new JButton("Historique des factures");
        panel_bouton.add(archivage);
        archivage.addActionListener(modele.Archivage());
        JButton facture = new JButton("Ajouter une facture");
        panel_bouton.add(facture);
        facture.addActionListener(modele.ouvrirPageFacture());

        JButton regularisation = new JButton("Régularisation des charges");
        regularisation.setHorizontalTextPosition(SwingConstants.LEFT);
        regularisation.setVerticalTextPosition(SwingConstants.TOP);
        regularisation.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(regularisation, BorderLayout.EAST);
        regularisation.addActionListener(modele.Regularisation());

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());

        frame.addWindowListener(modele.fermerFenetre());
    }

    public int getId_bail() {
        return id_bail;
    }

    public void createInfoCharge(int annee, String border){
        JPanel champ = new JPanel();
        champ.setLayout(new BorderLayout(0,0));
        contenu.add(champ, border);

        JLabel titrecontent = new JLabel("Charges de l'année "+annee);
        titrecontent.setHorizontalAlignment(SwingConstants.CENTER);
        champ.add(titrecontent,BorderLayout.CENTER);

        JPanel donnee = new JPanel();
        champ.add(donnee,BorderLayout.SOUTH);
        GridBagLayout gbl_donnee = new GridBagLayout();
        gbl_donnee.columnWidths = new int[] {30, 30, 30, 30};
        gbl_donnee.rowHeights = new int[] {0, 30, 0, 30, 0, 30};
        gbl_donnee.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        gbl_donnee.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        donnee.setLayout(gbl_donnee);


        double prix = modele.getPrixDe("Eau",annee);
        JLabel Eau = new JLabel("Eau : ");
        GridBagConstraints gbc_Eau = new GridBagConstraints();
        gbc_Eau.insets = new Insets(0, 0, 5, 5);
        gbc_Eau.gridx = 0;
        gbc_Eau.gridy = 1;
        donnee.add(Eau, gbc_Eau);

        JLabel prixEau = new JLabel(prix + " €");
        GridBagConstraints gbc_prixEau = new GridBagConstraints();
        gbc_prixEau.insets = new Insets(0, 0, 5, 5);
        gbc_prixEau.gridx = 1;
        gbc_prixEau.gridy = 1;
        donnee.add(prixEau, gbc_prixEau);

        prix = modele.getPrixDe("Electricité",annee);
        JLabel Electricite = new JLabel("Electricité : ");
        GridBagConstraints gbc_Electricite = new GridBagConstraints();
        gbc_Electricite.insets = new Insets(0, 0, 5, 5);
        gbc_Electricite.gridx = 2;
        gbc_Electricite.gridy = 1;
        donnee.add(Electricite, gbc_Electricite);


        JLabel prixElectricite = new JLabel(prix + " €");
        GridBagConstraints gbc_prixElectricite = new GridBagConstraints();
        gbc_prixElectricite.insets = new Insets(0, 0, 5, 5);
        gbc_prixElectricite.gridx = 3;
        gbc_prixElectricite.gridy = 1;
        donnee.add(prixElectricite, gbc_prixElectricite);

        prix = modele.getPrixDe("Ordures",annee);

        JLabel Ordure = new JLabel("Ordures : ");
        GridBagConstraints gbc_Ordure = new GridBagConstraints();
        gbc_Ordure.insets = new Insets(0, 0, 5, 5);
        gbc_Ordure.gridx = 0;
        gbc_Ordure.gridy = 3;
        donnee.add(Ordure, gbc_Ordure);

        JLabel prixOrdures = new JLabel(prix + " €");
        GridBagConstraints gbc_prixOrdures = new GridBagConstraints();
        gbc_prixOrdures.insets = new Insets(0, 0, 5, 5);
        gbc_prixOrdures.gridx = 1;
        gbc_prixOrdures.gridy = 3;
        donnee.add(prixOrdures, gbc_prixOrdures);

        prix = modele.getPrixDe("Entretien",annee);
        JLabel Entretien = new JLabel("Entretien : ");
        GridBagConstraints gbc_Entretien = new GridBagConstraints();
        gbc_Entretien.insets = new Insets(0, 0, 5, 5);
        gbc_Entretien.gridx = 2;
        gbc_Entretien.gridy = 3;
        donnee.add(Entretien, gbc_Entretien);

        JLabel prixEntretien = new JLabel(prix + " €");
        GridBagConstraints gbc_prixEntretien = new GridBagConstraints();
        gbc_prixEntretien.insets = new Insets(0, 0, 5, 5);
        gbc_prixEntretien.gridx = 3;
        gbc_prixEntretien.gridy = 3;
        donnee.add(prixEntretien, gbc_prixEntretien);

    }
}
