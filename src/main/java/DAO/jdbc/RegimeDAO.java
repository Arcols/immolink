package DAO.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.db.ConnectionDB;

public class RegimeDAO implements DAO.RegimeDAO {

    @Override
    public Float getValeur() {
        Float valeur = 0F;
        Connection cn;
        try {
            cn = ConnectionDB.getInstance();
            PreparedStatement pstmt = cn.prepareStatement("SELECT valeur FROM regimemicrofoncier WHERE id = ?");
            pstmt.setInt(1, idRegime);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                valeur = rs.getFloat("valeur");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la valeur du régime microfoncier.", e);
        }
        return valeur;
    }

    @Override
    public void updateValeur(Float nouvelleValeur) {
        Connection cn;
        try {
            cn = ConnectionDB.getInstance();
            PreparedStatement pstmt = cn.prepareStatement("UPDATE regimemicrofoncier SET valeur = ? WHERE id = ?");
            pstmt.setFloat(1, nouvelleValeur);
            pstmt.setInt(2, idRegime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la valeur du régime microfoncier.", e);
        }
    }
}
