package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Locataire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                e.printStackTrace();
            }
        }
    }