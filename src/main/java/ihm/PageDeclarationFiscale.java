package ihm;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import modele.ModelePageDeclarationFiscale;

public class PageDeclarationFiscale extends PageAbstraite {
    private ModelePageDeclarationFiscale modele;
    private final String annee;
    private JTable table;

    public PageDeclarationFiscale(String annee, int x, int y) {
        super(x,y);
        this.modele = new ModelePageDeclarationFiscale(this);
        this.annee = annee;
        CreerSpecific();
    }

    @Override
    public void CreerSpecific() {
        JPanel titre = new JPanel();
        panel_body.add(titre, BorderLayout.NORTH);

        JLabel label_titre = new JLabel("Déclaration fiscale de "+annee, SwingConstants.CENTER);
        label_titre.setFont(new Font("Arial", Font.BOLD, 16));
        panel_body.add(label_titre, BorderLayout.NORTH);

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
        panel_body.add(scrollPane, BorderLayout.CENTER);

        JPanel bas_de_page = new JPanel();
        frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        table.addMouseListener(modele.mouseAdapter());

        quitter.addActionListener(modele.quitterPage());

        frame.addWindowListener(modele.fermerFenetre());
    }



    public JTable getTable() {
        return table;
    }

    public String getAnnee() {
        return annee;
    }
}

