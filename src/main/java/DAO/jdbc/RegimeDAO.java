package DAO.jdbc;

import DAO.db.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegimeDAO implements DAO.RegimeDAO {

    @Override
    public Float getValeur() {
        ConnectionDB cn;
        Float valeur = 0F;

        try {
            cn = new ConnectionDB();
            try (Connection conn = cn.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT valeur FROM regimemicrofoncier WHERE id = 1");
                 ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    valeur = rs.getFloat("valeur");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la récupération de la valeur du régime microfoncier.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données.", e);
        }

        return valeur;
    }

    @Override
    public void updateValeur(Float nouvelleValeur) {
        ConnectionDB cn;

        try {
            cn = new ConnectionDB();
            try (Connection conn = cn.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE regimemicrofoncier SET valeur = ? WHERE id = 1")) {

                pstmt.setFloat(1, nouvelleValeur);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la mise à jour de la valeur du régime microfoncier.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données.", e);
        }
    }
}
