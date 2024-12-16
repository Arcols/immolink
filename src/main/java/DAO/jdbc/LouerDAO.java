package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Locataire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LouerDAO implements DAO.LouerDAO{
        @Override
        public void create(Locataire locataire, Bail bail, int quotite) throws DAOException {
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "INSERT INTO louer (id_bail,id_locataire,quotite) VALUES (?,?,?)";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,new BailDAO().getId(bail));
                pstmt.setInt(2, new LocataireDAO().getId(locataire));
                pstmt.setInt(3,quotite);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<Integer> getIdLoc(int idBail){
            List<Integer> idLocataires = new ArrayList<>() ;
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "SELECT id_locataire FROM louer WHERE id_bail = ?";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,idBail);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    int id = rs.getInt("id_locataire");
                    idLocataires.add(id);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  idLocataires;
        }

    @Override
    public Integer getQutotié(int idBail, int idLocataire) {
        Integer quotite = -1;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT quotite FROM louer WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBail);
            pstmt.setInt(2,idLocataire);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                quotite = rs.getInt("quotite");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quotite;
    }

    @Override
    public void updateQuotite(int idBail, int idLocataire, int quotite) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE louer SET quotite = ? WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,quotite);
            pstmt.setInt(2,idBail);
            pstmt.setInt(3,idLocataire);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}