package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<Bail> getAllBaux() {
        List<Bail> baux = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT solde_de_compte, id_bien_louable, loyer, charges, depot_garantie, date_debut, date_fin FROM bail";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int solde_de_compte = rs.getInt("solde_de_compte");
                int id_bien_louable = rs.getInt("id_bien_louable");
                Double loyer = rs.getDouble("loyer");
                Double charges = rs.getDouble("charges");
                Double depot_garantie = rs.getDouble("depot_garantie");
                java.sql.Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                baux.add(new Bail((solde_de_compte==1),new LogementDAO().read(id_bien_louable).getNumero_fiscal(),loyer,charges,depot_garantie,date_debut,date_fin));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return baux;
    }
}

