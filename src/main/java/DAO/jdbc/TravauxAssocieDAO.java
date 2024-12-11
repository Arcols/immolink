package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.BienLouable;
import classes.Devis;

import java.sql.*;

public class TravauxAssocieDAO implements DAO.TrauxAssocieDAO {

    @Override
    public void create(String num_fiscal, Devis devis) throws DAOException {
        BienLouableDAO bienDAO = new BienLouableDAO();
        Integer idBien = bienDAO.getId(num_fiscal);
        DevisDAO devisDAO = new DevisDAO();
        Integer idDevis = devisDAO.getId(devis);
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO TravauxAssocie (id_devis,id_bien) VALUES (?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,idDevis);
            pstmt.setInt(2,idBien);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}