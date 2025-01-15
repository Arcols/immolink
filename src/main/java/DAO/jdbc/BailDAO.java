package DAO.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.BienLouable;

public class BailDAO implements DAO.BailDAO {

    @Override
    public void create(Bail bail) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO bail (solde_de_compte,id_bien_louable, loyer,charges, depot_garantie,date_debut,date_fin,icc,index_eau,date_dernier_anniversaire ) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            if(bail.isSoldeDeCompte()){
                pstmt.setInt(1, 1);
            } else {
                pstmt.setInt(1, 0);
            }
            Integer id = new BienLouableDAO().getId(bail.getFiscBien());
            pstmt.setInt(2, id);
            pstmt.setDouble(3, bail.getLoyer());
            pstmt.setDouble(4, bail.getCharge());
            pstmt.setDouble(5,bail.getDepotGarantie());
            pstmt.setDate(6,bail.getDateDebut());
            pstmt.setDate(7,bail.getDateFin());
            pstmt.setDouble(8,bail.getIcc());
            pstmt.setInt(9,bail.getIndexEau());
            pstmt.setDate(10,bail.getDernierAnniversaire());
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
            Integer id = new BienLouableDAO().getId(bail.getFiscBien());
            pstmt.setDate(1, bail.getDateDebut());
            pstmt.setDate(2, bail.getDateFin());
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
            String query = "SELECT solde_de_compte, id_bien_louable, loyer, charges, depot_garantie, date_debut, date_fin, icc, index_eau, date_dernier_anniversaire  FROM bail";
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
                Double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date date_dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                baux.add(new Bail((solde_de_compte==1),new LogementDAO().read(id_bien_louable).getNumeroFiscal(),loyer,charges,depot_garantie,date_debut,date_fin,icc,index_eau,date_dernier_anniversaire));
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
    public Integer getIdBienLouable(int idBail) {
        Integer idBienLouable = 0;
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
                Double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date date_dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                bail = new Bail((solde_de_compte==1),new LogementDAO().read(id_bien_louable).getNumeroFiscal(),loyer,charges,depot_garantie,date_debut,date_fin,icc,index_eau,date_dernier_anniversaire);
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
    public Bail getBailFromBienEtDate(BienLouable bien, Date date_debut_bail) {
        Bail bail = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bail WHERE id_bien_louable = ? AND date_debut = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,new BienLouableDAO().getId(bien.getNumeroFiscal()));
            pstmt.setDate(2,date_debut_bail);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Integer solde_de_compte = rs.getInt("solde_de_compte");
                Double loyer = rs.getDouble("loyer");
                Double charges = rs.getDouble("charges");
                Double depot_garantie = rs.getDouble("depot_garantie");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                Double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date date_dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                bail = new Bail((solde_de_compte==1),bien.getNumeroFiscal(),loyer,charges,depot_garantie,date_debut,date_fin,icc,index_eau,date_dernier_anniversaire);
            }
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

    @Override
    public void updateICC(int idBail, double icc) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bail SET icc = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDouble(1, icc);
            pstmt.setInt(2, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDateDernierAnniversaire(int idBail, Date date_dernier_anniv) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bail SET date_dernier_anniversaire = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDate(1, date_dernier_anniv);
            pstmt.setInt(2, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProvisionPourCharge(int idBail, double previsionPourCharge) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bail SET charges = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDouble(1, previsionPourCharge);
            pstmt.setInt(2, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getBauxNouvelICC() {
        List<String> lNotifs = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT bat.adresse, bat.ville ,bl.complement_adresse, bail.loyer, bail.ICC, bail.date_dernier_anniversaire " +
                    "FROM bail , bienlouable bl, batiment bat " +
                    "WHERE bail.id_bien_louable = bl.id " +
                    "AND bl.idBat = bat.id " +
                    "AND bail.date_dernier_anniversaire < CURDATE() - INTERVAL 1 YEAR";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Double loyer = rs.getDouble("loyer");
                String adresse= rs.getString("adresse");
                String ville= rs.getString("ville");
                String complement= rs.getString("complement_adresse");
                Date date_dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                lNotifs.add("<html>Le loyer du logement <b>"+adresse+ " "+ville+" " +complement + "</b> peut etre modifié, il est actuellement à "+ loyer+ "son anniversaire était le <b>"+date_dernier_anniversaire+"</b></html>");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lNotifs;
    }


    @Override
    public void updateIndexeEau(int idBail, int nouvelIndexe) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bail SET index_eau = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, nouvelIndexe);
            pstmt.setInt(2, idBail);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

