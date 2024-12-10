package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import classes.Garage;
import enumeration.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BienLouableDAO implements DAO.BienLouableDAO {

    @Override
    public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface){
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, idBat, garage_assoc) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, bien.getNumero_fiscal());
            pstmt.setString(2, bien.getComplement_adresse());
            pstmt.setInt(3, type.getValue());
            pstmt.setInt(4, nb_piece);
            pstmt.setDouble(5,surface);
            pstmt.setInt(6,new BatimentDAO().getIdBat(bien.getVille(), bien.getAdresse()));
            pstmt.setNull(7, java.sql.Types.INTEGER);  // si on veut ajouter un garagon on utilisera ajouterUnGarageAuBienLouable par la suite
            pstmt.executeUpdate();
            pstmt.close();
            

        } catch (SQLException | DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void lierUnGarageAuBienLouable(BienLouable bien, Garage garage) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bienlouable SET garage_assoc = ? WHERE numero_fiscal = ? AND type_logement = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            GarageDAO garageDAO = new GarageDAO();
            pstmt.setInt(1, garageDAO.getIdGarage(garage.getNumero_fiscal()));
            pstmt.setString(2, bien.getNumero_fiscal());
            pstmt.setInt(3, TypeLogement.APPARTEMENT.getValue());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BienLouable readFisc(String num_fiscal) throws DAOException {
        BienLouable bien = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id,numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, garage_assoc, idBat FROM bienlouable WHERE numero_fiscal = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                Integer id = rs.getInt("id");
                String complement = rs.getString("complement_adresse");
                Integer type_logement = rs.getInt("type_logement");
                Integer nb_piece = rs.getInt("Nombre_pieces");
                Double surface = rs.getDouble("surface");
                Integer idGarage = rs.getInt("garage_assoc");
                Integer idBat = rs.getInt("idBat");
                Batiment bat = new BatimentDAO().readId(idBat);
                List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(id);
                GarageDAO garageDAO = new GarageDAO();
                bien = new BienLouable(num_fiscal, bat.getVille(), bat.getAdresse(), complement,diags,idGarage);
            }
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bien;
    }

    @Override
    public BienLouable readId(int id) throws DAOException {
        BienLouable bienLouable = null;
        try{
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable WHERE id = ? AND type_logement = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setInt(2,TypeLogement.APPARTEMENT.getValue());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String numero_fiscal = rs.getString("numero_fiscal");
                String complement_d_adresse = rs.getString("complement_adresse");
                BatimentDAO batDAO = new BatimentDAO();
                Batiment bat = batDAO.readId(rs.getInt("idBat"));
                String ville = bat.getVille();
                String adresse = bat.getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(id);
                GarageDAO garageDAO = new GarageDAO();
                bienLouable = new BienLouable(numero_fiscal,ville,adresse,complement_d_adresse,lDiags,garageDAO.getIdGarage(numero_fiscal));
            }
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bienLouable;
    }

    @Override
    public Integer getId(String num_fiscal) throws DAOException {
        Integer id = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ? AND type_logement = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            pstmt.setInt(2,TypeLogement.APPARTEMENT.getValue());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                id = rs.getInt("id");
            }
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id;
    }


    @Override
    public void delete(int id) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "DELETE FROM bienlouable WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BienLouable> findAll() throws DAOException {
        List<BienLouable> Allbien = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String num_fisc = rs.getString("numero_fiscal");
                String compl = rs.getString("complement_adresse");
                Integer id_bat = rs.getInt("IdBat");
                String ville = new BatimentDAO().readId(id_bat).getVille();
                String adresse = new BatimentDAO().readId(id_bat).getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                GarageDAO garageDAO = new GarageDAO();
                Allbien.add(new BienLouable(num_fisc,ville,adresse,compl,lDiags,garageDAO.getIdGarage(num_fisc)));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Allbien;
    }

    @Override
    public Integer getTypeFromCompl(String ville, String adresse, String complement) {
        Integer type = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBat);
            pstmt.setString(2,complement);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                type = rs.getInt("type_logement");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    @Override
    public Integer getNbPieceFromCompl(String ville, String adresse, String complement) {
        Integer nb_pieces = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBat);
            pstmt.setString(2,complement);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                nb_pieces = rs.getInt("Nombre_pieces");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nb_pieces;
    }

    @Override
    public Double getSurfaceFromCompl(String ville, String adresse, String complement) {
        Double surface = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBat);
            pstmt.setString(2,complement);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                surface = rs.getDouble("surface");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return surface;
    }

    @Override
    public String getFiscFromCompl(String ville, String adresse, String complement) {
        String fisc = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBat);
            pstmt.setString(2,complement);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                fisc = rs.getString("numero_fiscal");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fisc;
    }

    @Override
    public Map<String, List<String>> getAllcomplements() throws SQLException {
        Map<String, List<String>> adresses = new HashMap<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT adresse, id FROM batiment";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String adresse = rs.getString("adresse");
                String idBat = rs.getString("id");
                adresses.putIfAbsent(adresse, new ArrayList<>());
                String query2 = "SELECT complement_adresse FROM bienlouable WHERE idBat = ?";
                PreparedStatement pstmt2 = cn.prepareStatement(query2);
                pstmt2.setString(1,idBat);
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()){
                    String compl = rs2.getString("complement_adresse");
                    adresses.get(adresse).add(compl);
                }
                rs2.close();
                pstmt2.close();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adresses;
    }

}
