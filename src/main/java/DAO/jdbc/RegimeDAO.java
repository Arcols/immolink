package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;

import java.sql.*;

public class RegimeDAO implements DAO.RegimeDAO{
    private Connection cn;
    @Override
    public Float getValeur() {
        ConnectionDB db;
        Connection cn = null;
        Float valeur = 0F;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "SELECT valeur FROM regimemicrofoncier WHERE id = 1";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                valeur = rs.getFloat("valeur");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la valeur du régime microfoncier.", e);
        }
        return valeur;
    }

    @Override
    public void updateValeur(Float nouvelleValeur) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "UPDATE regimemicrofoncier SET valeur = ? WHERE id = 1";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setFloat(1, nouvelleValeur);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la valeur du régime microfoncier.", e);
        }
    }

}
