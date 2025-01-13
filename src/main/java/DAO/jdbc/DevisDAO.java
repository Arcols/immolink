package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Devis;
import enumeration.TypeLogement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DevisDAO implements DAO.DevisDAO {

    @Override
    public void create(Devis devis, String num_fiscal, TypeLogement typeLogement) throws DAOException {
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO Devis (num_devis,num_facture, date_debut,date_facture,montant_devis,montant_travaux,nature,type,adresse_entreprise,nom_entreprise) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setString(1,devis.getNumDevis());
            pstmt.setString(2,devis.getNumFacture());
            pstmt.setDate(3, devis.getDateDebut());
            pstmt.setDate(4, devis.getDateFacture());
            pstmt.setFloat(5, devis.getMontantDevis());
            pstmt.setFloat(6, devis.getMontantTravaux());
            pstmt.setString(7, devis.getNature());
            pstmt.setString(8, devis.getType());
            pstmt.setString(9, devis.getAdresseEntreprise());
            pstmt.setString(10, devis.getNomEntreprise());
            pstmt.executeUpdate();
            pstmt.close();
            TravauxAssocieDAO travauxAssocieDAO;
            travauxAssocieDAO = new TravauxAssocieDAO();
            travauxAssocieDAO.create(num_fiscal,devis,typeLogement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Devis read(String num_devis) throws DAOException {
        Devis devis = null;
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT * FROM Devis WHERE num_devis = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setString(1,num_devis);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                devis = new Devis(rs.getString("num_devis"),rs.getString("num_facture"),rs.getFloat("montant_devis"),rs.getString("nature"),rs.getFloat("montant_travaux"),rs.getDate("date_debut"),rs.getDate("date_facture"),rs.getString("type"),rs.getString("adresse_entreprise"),rs.getString("nom_entreprise"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return devis;
    }

    @Override
    public Devis readId(Integer id){
        Devis devis = null;
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT * FROM Devis WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                devis = new Devis(rs.getString("num_devis"),rs.getString("num_facture"),rs.getFloat("montant_devis"),rs.getString("nature"),rs.getFloat("montant_travaux"),rs.getDate("date_debut"),rs.getDate("date_facture"),rs.getString("type"),rs.getString("adresse_entreprise"),rs.getString("nom_entreprise"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return devis;
    }

    @Override
    public void delete(Integer id) {
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "DELETE FROM Devis WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getId(Devis devis){
        Integer id = 0;
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id FROM Devis WHERE num_devis = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setString(1,devis.getNumDevis());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Devis> getAllDevisFromABien(String num_fiscal, TypeLogement typeLogement){
        TravauxAssocieDAO travauxAssocieDAO = new TravauxAssocieDAO();
        List<Devis> liste_devis = new ArrayList<>();
        List<Integer> liste_id_devis;
        try {
            liste_id_devis = travauxAssocieDAO.findAll(num_fiscal,typeLogement);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        for (Integer id : liste_id_devis){
            Devis devis = readId(id);
            liste_devis.add(devis);
        }
        return liste_devis;
    }

    @Override
    public List<Devis> getAllDevisFromABienAndDate(String num_fiscal, TypeLogement typeLogement, Date annee) {
        TravauxAssocieDAO travauxAssocieDAO = new TravauxAssocieDAO();
        List<Devis> liste_devis = new ArrayList<>();
        List<Integer> liste_id_devis;
        try {
            liste_id_devis = travauxAssocieDAO.findAllWithDate(num_fiscal,typeLogement,annee);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        for (Integer id : liste_id_devis){
            Devis devis = readId(id);
            liste_devis.add(devis);
        }
        return liste_devis;
    }

    @Override
    public double getMontantTotalDevis(String num_fiscal, TypeLogement typeLogement) {
        double resultat=0.0;
        List<Devis> liste_devis = getAllDevisFromABien(num_fiscal,typeLogement);
        for (Devis devis : liste_devis){
            resultat += devis.getMontantDevis();
        }
        return resultat;
    }

    @Override
    public double getMontantTotalTravaux(String num_fiscal, TypeLogement typeLogement) {
        double resultat=0.0;
        List<Devis> liste_devis = getAllDevisFromABien(num_fiscal,typeLogement);
        for (Devis devis : liste_devis){
            resultat += devis.getMontantTravaux();
        }
        return resultat;
    }

}
