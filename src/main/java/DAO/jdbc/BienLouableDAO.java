package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import enumeration.TypeLogement;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BienLouableDAO implements DAO.BienLouableDAO {
    @Override
    public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface,Integer id_garage_assoc){

        ConnectionDB cn;
        try {
            cn = ConnectionDB.getInstance();
            String query = "INSERT INTO bienlouable VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setString(1, bien.getNumero_fiscal());
            pstmt.setString(2, bien.getComplement_adresse());
            pstmt.setInt(3, type.getValue());
            pstmt.setInt(4, nb_piece);
            pstmt.setDouble(5,surface);
            if(id_garage_assoc != null){
                pstmt.setInt(6,id_garage_assoc);
                pstmt.setInt(7,1);
            } else {
                pstmt.setInt(6,(Integer) null);
                pstmt.setInt(7,0);
            }

            pstmt.executeUpdate();
            pstmt.close();
            cn.closeConnection();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public BienLouable readFisc(String num_fiscal) throws DAOException {
        ConnectionDB cn;
        BienLouable bien = null;
        try {
            cn = ConnectionDB.getInstance();
            String query = "SELECT numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, garage_assoc, idBat FROM Bienloable WHERE numero_fiscal = ? ";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                String complement = rs.getString("complement_adresse");
                String code_postal = rs.getString("code_postal");
                Integer type_logement = rs.getInt("type_logement");
                Integer nb_piece = rs.getInt("Nombre_pieces");
                Double surface = rs.getDouble("surface");
                Integer idBat = rs.getInt("idBat");
                Batiment bat = new BatimentDAO().readId(idBat);
                List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(num_fiscal);
                bien = new BienLouable(num_fiscal, bat.getVille(), bat.getAdresse(), complement,diags);
            }
            pstmt.close();
            cn.closeConnection();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bien;
    }
    @Override
    public Integer getId(String num_fiscal) throws DAOException {
        ConnectionDB cn;
        Integer id = null;
        try {
            cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM Bienloable WHERE numero_fiscal = ? ";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                 id = rs.getInt("id");
            }
            pstmt.close();
            cn.closeConnection();

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
        // TODO Auto-generated method stub

    }

    @Override
    public List<BienLouable> findAll() throws DAOException {
        ConnectionDB cn;
        List<BienLouable> Allbien = null;
        try {
            cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String num_fisc = rs.getString("numero_fiscal");
                String compl = rs.getString("complement_adresse");
                String ville = new BatimentDAO().readFisc(num_fisc).getVille();
                String adresse = new BatimentDAO().readFisc(num_fisc).getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(num_fisc);
                Allbien.add(new BienLouable(num_fisc,ville,adresse,compl,lDiags));
            }
            pstmt.close();
            cn.closeConnection();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Allbien;
    }
}





