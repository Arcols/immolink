package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Facture;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FactureDAO implements DAO.FactureDAO{

    @Override
    public void create(Facture facture, int id_charge) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO facture (numero,type,date,montant,id_charge) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, facture.getNumero());
            pstmt.setString(2, facture.getType());
            pstmt.setDate(3, facture.getDate());
            pstmt.setDouble(4,facture.getMontant());
            pstmt.setInt(5, id_charge);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Facture> getAllByAnnee(Date annee, int id_charge) {
        List<Facture> factures = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT numero, type, date, montant FROM facture WHERE YEAR(date) = YEAR(?) AND id_charge = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDate(1, annee);
            pstmt.setInt(2, id_charge);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String numero = rs.getString("numero");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                double montant = rs.getDouble("montant");
                factures.add(new Facture(numero,type,date,montant));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return factures;
    }

    @Override
    public List<Facture> getAll(int id_charge) {
        List<Facture> liste_facture = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT numero, type, date, montant FROM facture WHERE id_charge = ? ORDER BY date DESC";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_charge);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String numero = rs.getString("numero");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                double montant = rs.getDouble("montant");
                liste_facture.add(new Facture(numero,type,date,montant));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_facture;
        }

    @Override
    public void delete(Integer id_facture) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "DELETE FROM facture WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_facture);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getAllId(Integer id_charge) throws DAOException {
        List<Integer> list_id = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM facture WHERE id_charge = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_charge);
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
