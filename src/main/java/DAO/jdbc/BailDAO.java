package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.BienLouable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

