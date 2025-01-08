package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Charge;
import classes.Facture;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        for (Facture f : factureList){
            montant += f.getMontant();
        }
        return  montant;
    }
}
