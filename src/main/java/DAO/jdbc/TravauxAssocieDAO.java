package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;

import java.util.ArrayList;

import java.sql.*;
import java.util.List;

public class TravauxAssocieDAO implements DAO.TravauxAssocieDAO {

    @Override
    public void create(String num_fiscal, Devis devis,TypeLogement typeLogement) throws DAOException {
        Integer id = 0;
        switch (typeLogement) {
            case APPARTEMENT:
                BienLouableDAO bienDAO = new BienLouableDAO();
                id = bienDAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batimentDAO = new BatimentDAO();
                Batiment batiment = batimentDAO.readFisc(num_fiscal);
                id = batimentDAO.getIdBat(batiment.getVille(), batiment.getAdresse());
                break;
            case GARAGE:
                GarageDAO garageDAO = new GarageDAO();
                id = garageDAO.getIdGarage(num_fiscal);
                break;
        }
        DevisDAO devisDAO = new DevisDAO();
        Integer idDevis = devisDAO.getId(devis);
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO TravauxAssocie (id_devis,id_bien) VALUES (?,?)";
            System.out.println("test");
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,idDevis);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> findAll(String num_fiscal, TypeLogement typeLogement) throws DAOException {
        List<Integer> idDevis = new ArrayList<Integer>();
        Integer id = 0;
        switch (typeLogement){
            case APPARTEMENT:
                BienLouableDAO bienDAO = new BienLouableDAO();
                id = bienDAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batimentDAO = new BatimentDAO();
                Batiment batiment = batimentDAO.readFisc(num_fiscal);
                id = batimentDAO.getIdBat(batiment.getVille(),batiment.getAdresse());
                break;
            case GARAGE:
                GarageDAO garageDAO = new GarageDAO();
                id = garageDAO.getIdGarage(num_fiscal);
                break;
        }
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id_devis FROM TravauxAssocie WHERE id_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                idDevis.add(rs.getInt("id_devis"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idDevis;
    }

}