package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Facture;

import java.sql.*;
import java.util.List;

public class ChargeDAO implements DAO.ChargeDAO{
    @Override
    public void create(String type, int id_bail) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO charges (type, id_bail) VALUES (?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1,type);
            pstmt.setInt(2, id_bail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getMontant(Date annee, int id) throws DAOException {
        List<Facture> factureList = new FactureDAO().getAllByAnnee(annee, id);
        double montant = 0.0;
        if (!factureList.isEmpty()) {
            for (Facture f : factureList) {
                montant += f.getMontant();
            }
        }
        return  montant;
    }

    @Override
    public int getId(String type, int id_bail) throws DAOException {
        int id = -1;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM charges WHERE type = ? AND id_bail = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1,type);
            pstmt.setInt(2,id_bail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}
