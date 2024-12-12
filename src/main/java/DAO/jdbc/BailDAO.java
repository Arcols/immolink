package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BailDAO implements DAO.BailDAO {
    @Override
    public void create(Bail bail) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO bail (solde_de_compte,id_bien_louable, loyer,charges, depot_garantie,date_debut,date_fin) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            if(bail.isSolde_de_compte()){
                pstmt.setInt(1, 1);
            } else {
                pstmt.setInt(1, 0);
            }
            Integer id = new BienLouableDAO().getId(bail.getFisc_bien());
            pstmt.setInt(2, id);
            pstmt.setDouble(3, bail.getLoyer());
            pstmt.setDouble(4, bail.getCharge());
            pstmt.setDouble(5,bail.getDepot_garantie());
            pstmt.setDate(6,bail.getDate_debut());
            pstmt.setDate(7,bail.getDate_fin());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public double getAllLoyer() {
        double resultat = 0.0;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT SUM(loyer) FROM bail";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                resultat = rs.getDouble("SUM(loyer)");
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultat;
    }

    @Override
    public int getId(Bail bail){
        Integer idBail = (Integer) null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM bail WHERE date_debut = ? AND date_fin = ? AND id_bien_louable = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            Integer id = new BienLouableDAO().getId(bail.getFisc_bien());
            pstmt.setDate(1, bail.getDate_debut());
            pstmt.setDate(2, bail.getDate_fin());
            pstmt.setInt(3, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                idBail = rs.getInt("id");
            }
            pstmt.close();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idBail;
    }
}

