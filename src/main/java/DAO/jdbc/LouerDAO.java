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
    public void create(Locataire locataire, Bail bail) throws DAOException {
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "INSERT INTO louer (id_bail,id_locataire) VALUES (?,?)";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,new BailDAO().getId(bail));
                pstmt.setInt(2, new LocataireDAO().getId(locataire));
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
    }