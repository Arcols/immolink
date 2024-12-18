package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            throw new RuntimeException(e);
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
        Integer idBail = -1;
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
    public List<Integer> getIDBeaux(Integer id_bien) {
        List<Integer> idBaux = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM bail WHERE id_bien_louable = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_bien);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idBaux.add(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idBaux;
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
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return baux;
    }

    @Override
    public void delete(int idBail) {
        List<Integer> idLocataires = new LouerDAO().getIdLoc(idBail);
        try {
            for(int idLocataire : idLocataires){
                new LouerDAO().delete(idBail,idLocataire);
            }
            Connection cn = ConnectionDB.getInstance();
            String query = "DELETE FROM bail WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIdBienLouable(int idBail) {
        int idBienLouable = 0;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id_bien_louable FROM bail WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                idBienLouable = rs.getInt("id_bien_louable");
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idBienLouable;
    }

    @Override
    public Bail getBailFromId(int idBail) {
        Bail bail = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bail WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int solde_de_compte = rs.getInt("solde_de_compte");
                int id_bien_louable = rs.getInt("id_bien_louable");
                Double loyer = rs.getDouble("loyer");
                Double charges = rs.getDouble("charges");
                Double depot_garantie = rs.getDouble("depot_garantie");
                java.sql.Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                bail = new Bail((solde_de_compte==1),new LogementDAO().read(id_bien_louable).getNumero_fiscal(),loyer,charges,depot_garantie,date_debut,date_fin);
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return bail;
    }

    @Override
    public void updateLoyer(int idBail, double loyer) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bail SET loyer = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDouble(1, loyer);
            pstmt.setInt(2, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

