package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Facture;

import java.sql.*;
import java.util.LinkedList;
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
        List<Facture> liste_facturee = new FactureDAO().getAllByAnnee(annee, id);
        double montant = 0.0;
        if (!liste_facturee.isEmpty()) {
            for (Facture f : liste_facturee) {
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

    @Override
    public void delete(Integer id_charge) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            cn.setAutoCommit(false); // Start transaction

            // Get all invoice IDs associated with the charge
            FactureDAO factureDAO = new FactureDAO();
            List<Integer> factureIds = factureDAO.getAllId(id_charge);

            // Delete each invoice
            for (Integer id_facture : factureIds) {
                factureDAO.delete(id_facture);
            }

            // Delete the charge
            String query = "DELETE FROM charges WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_charge);
            pstmt.executeUpdate();
            pstmt.close();

            cn.commit(); // Commit transaction
        } catch (SQLException | DAOException e) {
            try {
                ConnectionDB.getInstance().rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionDB.getInstance().setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Integer> getAllId(Integer id_bail) throws DAOException {
        List<Integer> list_id = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM charges WHERE id_bail = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_bail);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list_id.add(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list_id;
    }

}
