package ihm;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DAO.DAOException;
import com.formdev.flatlaf.FlatLightLaf;
import modele.Menu;
import modele.ModelePageNotifications;


public class PageNotifications extends PageAbstraite {

    private JTable table;

    /**
     * Create the application.
     */
    public PageNotifications(int x, int y) {
        super(x,y);
        this.CreerSpecific();
    }

    @Override
    public void CreerSpecific(){
        JPanel panel_titre = new JPanel();
        panel_body.add(panel_titre, BorderLayout.NORTH);

        JLabel label_notifications = new JLabel("Notifications");
        panel_titre.add(label_notifications);

        JScrollPane scrollPane = new JScrollPane();
        panel_body.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);
        DefaultTableModel model;
        try {
            model = ModelePageNotifications.getNotifications();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        table.setModel(model);
        TableColumnModel colonne_modele = table.getColumnModel();
        colonne_modele.getColumn(0).setMaxWidth(120);
        colonne_modele.getColumn(0).setMinWidth(120);
    }
}
