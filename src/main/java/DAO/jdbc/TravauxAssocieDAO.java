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
    public void create(String num_fiscal, Devis devis,TypeLogement type_logement) throws DAOException {
        Integer id = 0;
        switch (type_logement) {
            case APPARTEMENT:
            case MAISON:
                BienLouableDAO bien_DAO = new BienLouableDAO();
                id = bien_DAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batiment_DAO = new BatimentDAO();
                Batiment batiment = batiment_DAO.readFisc(num_fiscal);
                id = batiment_DAO.getIdBat(batiment.getVille(), batiment.getAdresse());
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garage_DAO = new GarageDAO();
                id = garage_DAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
        }
        DevisDAO devis_DAO = new DevisDAO();
        Integer id_devis = devis_DAO.getId(devis);
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO TravauxAssocie (id_devis,id_bien,type_bien) VALUES (?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id_devis);
            pstmt.setInt(2,id);
            pstmt.setInt(3,type_logement.getValue());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> findAll(String num_fiscal, TypeLogement type_logement) throws DAOException {
        List<Integer> id_devis = new ArrayList<>();
        Integer id = null;
        switch (type_logement){
            case APPARTEMENT:
            case MAISON:
                BienLouableDAO bien_DAO = new BienLouableDAO();
                id = bien_DAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batiment_DAO = new BatimentDAO();
                Batiment batiment = batiment_DAO.readFisc(num_fiscal);
                id = batiment_DAO.getIdBat(batiment.getVille(),batiment.getAdresse());
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garage_DAO = new GarageDAO();
                id = garage_DAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
            case GARAGE_ASSOCIE:
                GarageDAO garageDAO2 = new GarageDAO();
                id = garageDAO2.getIdGarage(num_fiscal,TypeLogement.GARAGE_ASSOCIE);
                break;
        }
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id_devis FROM TravauxAssocie WHERE id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            pstmt.setInt(2,type_logement.getValue());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                id_devis.add(rs.getInt("id_devis"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_devis;
    }

    @Override
    public List<Integer> findAllWithDate(String num_fiscal, TypeLogement type_logement, Date annee) throws DAOException {
        List<Integer> id_devis = new ArrayList<>();
        Integer id = null;
        switch (type_logement){
            case APPARTEMENT:
            case MAISON:
                BienLouableDAO bien_DAO = new BienLouableDAO();
                id = bien_DAO.getId(num_fiscal);
                break;
            case BATIMENT:
                BatimentDAO batiment_DAO = new BatimentDAO();
                Batiment batiment = batiment_DAO.readFisc(num_fiscal);
                id = batiment_DAO.getIdBat(batiment.getVille(),batiment.getAdresse());
                break;
            case GARAGE_PAS_ASSOCIE:
                GarageDAO garage_DAO = new GarageDAO();
                id = garage_DAO.getIdGarage(num_fiscal,TypeLogement.GARAGE_PAS_ASSOCIE);
                break;
        }
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id_devis FROM TravauxAssocie WHERE id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            pstmt.setInt(2,type_logement.getValue());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id_devis_sans_date = rs.getInt("id_devis");
                String requete2 = "SELECT id FROM devis WHERE id = ? AND YEAR(date_facture) = YEAR(?)";
                PreparedStatement pstmt2 = cn.prepareStatement(requete2);
                pstmt2.setInt(1,id_devis_sans_date);
                pstmt2.setDate(2, annee);
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()){
                    id_devis.add(rs2.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_devis;
    }

    @Override
    public void delete(Integer id_devis, Integer id_bien, TypeLogement type_logement) throws DAOException {
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "DELETE FROM TravauxAssocie WHERE id_devis = ? AND id_bien = ? AND type_bien = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id_devis);
            pstmt.setInt(2,id_bien);
            pstmt.setInt(3,type_logement.getValue());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}