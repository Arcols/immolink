package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Facture;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FactureDAO implements DAO.FactureDAO{

    @Override
    public void create(Facture facture, int id_charge) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO facture (numero,type,date,montant,id_charges) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, facture.getNumero());
            pstmt.setString(2, facture.getType());
            pstmt.setDate(3, facture.getDate());
            pstmt.setDouble(4,facture.getMontant());
            pstmt.setInt(5,id_charge);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Facture> getAllAnnee(Date annee) throws DAOException {
        List<Facture> factures = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT numero, type, date, montant FROM facture";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int numero = rs.getInt("numero");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                Double montant = rs.getDouble("montant");
                factures.add(new Facture(numero,type,date,montant));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return factures;
    }
}
