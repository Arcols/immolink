package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
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
            case MAISON:
                BienLouableDAO maisonDAO = new BienLouableDAO();
                id = maisonDAO.getId(num_fiscal);
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garageDAO = new GarageDAO();
                id = garageDAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
        }
        DevisDAO devisDAO = new DevisDAO();
        Integer idDevis = devisDAO.getId(devis);
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO TravauxAssocie (id_devis,id_bien,type_bien) VALUES (?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,idDevis);
            pstmt.setInt(2,id);
            pstmt.setInt(3,typeLogement.getValue());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> findAll(String num_fiscal, TypeLogement typeLogement) throws DAOException {
        List<Integer> idDevis = new ArrayList<Integer>();
        Integer id = null;
        switch (typeLogement){
            case APPARTEMENT:
                BienLouableDAO bienDAO = new BienLouableDAO();
                id = bienDAO.getId(num_fiscal);
                break;
            case MAISON:
                BienLouableDAO maisonDAO = new BienLouableDAO();
                id = maisonDAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batimentDAO = new BatimentDAO();
                Batiment batiment = batimentDAO.readFisc(num_fiscal);
                id = batimentDAO.getIdBat(batiment.getVille(),batiment.getAdresse());
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garageDAO = new GarageDAO();
                id = garageDAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
        }
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id_devis FROM TravauxAssocie WHERE id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            pstmt.setInt(2,typeLogement.getValue());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                idDevis.add(rs.getInt("id_devis"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idDevis;
    }

    @Override
    public List<Integer> findAllWithDate(String num_fiscal, TypeLogement typeLogement, Date annee) throws DAOException {
        List<Integer> idDevis = new ArrayList<Integer>();
        Integer id = null;
        switch (typeLogement){
            case APPARTEMENT:
                BienLouableDAO bienDAO = new BienLouableDAO();
                id = bienDAO.getId(num_fiscal);
                break;
            case MAISON:
                BienLouableDAO maisonDAO = new BienLouableDAO();
                id = maisonDAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batimentDAO = new BatimentDAO();
                Batiment batiment = batimentDAO.readFisc(num_fiscal);
                id = batimentDAO.getIdBat(batiment.getVille(),batiment.getAdresse());
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garageDAO = new GarageDAO();
                id = garageDAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
        }
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id_devis FROM TravauxAssocie WHERE id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            pstmt.setInt(2,typeLogement.getValue());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id_devis_sans_date = rs.getInt("id_devis");
                String requete2 = "SELECT id FROM devis WHERE id = ? AND YEAR(date_facture) = YEAR(?)";
                PreparedStatement pstmt2 = cn.prepareStatement(requete2);
                pstmt2.setInt(1,id_devis_sans_date);
                pstmt2.setDate(2, annee);
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()){
                    idDevis.add(rs2.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idDevis;
    }

    @Override
    public void delete(Integer id_devis, Integer id_bien, TypeLogement typeLogement) throws DAOException {
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "DELETE FROM TravauxAssocie WHERE id_devis = ? AND id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id_devis);
            pstmt.setInt(2,id_bien);
            pstmt.setInt(3,typeLogement.getValue());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}