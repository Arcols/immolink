package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Devis;

import java.sql.*;

public class DevisDAO implements DAO.DevisDAO {

    @Override
    public void create(Devis devis,String num_fiscal) throws DAOException {
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO Devis (num_devis,date_debut,date_fin,montant_devis,montant_travaux,nature,type,adresse_entreprise,nom_entreprise) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setString(1,devis.getNumDevis());
            pstmt.setDate(2, devis.getDateDebut());
            pstmt.setDate(3, devis.getDateFin());
            pstmt.setFloat(4, devis.getMontantDevis());
            pstmt.setFloat(5, devis.getMontantTravaux());
            pstmt.setString(6, devis.getNature());
            pstmt.setString(7, devis.getType());
            pstmt.setString(8, devis.getAdresseEntreprise());
            pstmt.setString(9, devis.getNomEntreprise());
            pstmt.executeUpdate();
            pstmt.close();
            TravauxAssocieDAO travauxAssocieDAO = new TravauxAssocieDAO();
            travauxAssocieDAO.create(num_fiscal,devis);
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
                devis = new Devis(rs.getString("num_devis"),rs.getFloat("montant_devis"),rs.getString("nature"),rs.getFloat("montant_travaux"),rs.getDate("date_debut"),rs.getDate("date_fin"),rs.getString("type"),rs.getString("adresse_entreprise"),rs.getString("nom_entreprise"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return devis;
    }

    @Override
    public Integer getId(Devis devis){
        try{
            Connection cn = ConnectionDB.getInstance();
            String requete = "SELECT id FROM Devis WHERE num_devis = ?";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setString(1,devis.getNumDevis());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
