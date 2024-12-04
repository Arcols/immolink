package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import enumeration.TypeLogement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BienLouableDAO implements DAO.BienLouableDAO {
    @Override
    public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface,Integer id_garage_assoc){
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, idBat, garage_assoc) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, bien.getNumero_fiscal());
            pstmt.setString(2, bien.getComplement_adresse());
            pstmt.setInt(3, type.getValue());
            pstmt.setInt(4, nb_piece);
            pstmt.setDouble(5,surface);
            pstmt.setInt(6,new BatimentDAO().getIdBat(bien.getVille(), bien.getAdresse()));
            if(id_garage_assoc != null){
                pstmt.setInt(7,id_garage_assoc);
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();
            pstmt.close();
            cn.close();

        } catch (SQLException | DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public BienLouable readFisc(String num_fiscal) throws DAOException {
        ConnectionDB db;
        Connection cn = null;
        BienLouable bien = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
                Integer idBat = rs.getInt("idBat");
                Batiment bat = new BatimentDAO().readId(idBat);
                List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(id);
                bien = new BienLouable(num_fiscal, bat.getVille(), bat.getAdresse(), complement,diags);
            }
            pstmt.close();
            cn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bien;
    }

    @Override
    public Integer getId(String num_fiscal) throws DAOException {
        ConnectionDB db;
        Connection cn = null;
        Integer id = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                 id = rs.getInt("id");
            }
            pstmt.close();
            cn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(BienLouable bien) throws DAOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(int id) throws DAOException {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "DELETE FROM bienlouable WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BienLouable> findAll() throws DAOException {
        ConnectionDB db;
        Connection cn =null;
        List<BienLouable> Allbien = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "SELECT * FROM bienlouable";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String num_fisc = rs.getString("numero_fiscal");
                String compl = rs.getString("complement_adresse");
                String ville = new BatimentDAO().readFisc(num_fisc).getVille();
                String adresse = new BatimentDAO().readFisc(num_fisc).getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                Allbien.add(new BienLouable(num_fisc,ville,adresse,compl,lDiags));
            }
            pstmt.close();
            cn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Allbien;
    }

    @Override
    public Map<String, List<String>> getAllcomplements() throws SQLException {
        Map<String, List<String>> adresses = new HashMap<>();
        ConnectionDB db;
        Connection cn = null;
        try {
            String query = "SELECT adresse, id FROM batiment";
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String adresse = rs.getString("adresse");
                String idBat = rs.getString("id");
                adresses.putIfAbsent(adresse, new ArrayList<>());
                String query2 = "SELECT compelement_adresse FROM bienlouable WHERE idBat = ?";
                PreparedStatement pstmt2 = cn.prepareStatement(query);
                pstmt2.setString(1,idBat);
                ResultSet rs2 = pstmt.executeQuery();
                while (rs2.next()){
                    String compl = rs2.getString("complement_adresse");
                    adresses.get(adresse).add(compl);
                }
                rs2.close();
                pstmt2.close();
            }
            rs.close();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adresses;
    }
}





