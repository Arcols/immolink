package ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modele.Charte;
import modele.Menu;
import java.awt.Dimension;
import java.awt.Toolkit;


public abstract class PageAbstraite {
    public JFrame frame;
    public JPanel panel_body;
    public JPanel bas_de_page;

    public PageAbstraite(){
        this(getCentreX(), getCentreY());
    }

    public  PageAbstraite(int x, int y) {
        // Initialisation standard du frame
        this.frame = new JFrame();
        this.frame.setBounds(x, y, 900, 500);
        //this.frame.setLocationRelativeTo(null);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Création du menu
        Menu m = new Menu(this.frame);
        m.creerEntete();
        
        // Structure de base
        CreerLayout();
        
        // Bas de page standard
        CreerBasPage();


        frame.setVisible(true);
    }

    public void CreerLayout() {
        panel_body = new JPanel();
        frame.getContentPane().add(panel_body, BorderLayout.CENTER);
        panel_body.setLayout(new BorderLayout(0, 0));
    }
    
    public void CreerBasPage() {
        bas_de_page = new JPanel();
        frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));
    }
    
    public abstract void CreerSpecific();
    
    public JFrame getFrame() {
        return frame;
    }

    private static int getCentreX() {
        Dimension taille_ecran = Toolkit.getDefaultToolkit().getScreenSize();
        return (taille_ecran.width - 900) / 2;
    }

    private static int getCentreY() {
        Dimension taille_ecran = Toolkit.getDefaultToolkit().getScreenSize();
        return (taille_ecran.height - 500) / 2;
    }
}