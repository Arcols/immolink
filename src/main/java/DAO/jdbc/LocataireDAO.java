package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocataireDAO implements DAO.LocataireDAO {
    @Override
    public void addLocataire(Locataire locataire) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "INSERT INTO locataire (nom, prenom, téléphone, date_arrive,mail,loc_actuel,genre) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getNom());
            pstmt.setString(2, locataire.getPrénom());
            pstmt.setString(3, locataire.getTéléphone());
            pstmt.setDate(4, locataire.getDateArrive());
            pstmt.setString(5, locataire.getMail());
            pstmt.setInt(6, 1); // requette pour la loc actuelle ?
            pstmt.setString(7, locataire.getGenre());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireTel(Locataire locataire,String tel) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "UPDATE locataire SET téléphone = ? WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, tel);
            pstmt.setString(2, locataire.getNom());
            pstmt.setString(3, locataire.getPrénom());
            pstmt.setString(4, locataire.getTéléphone());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireMail(Locataire locataire,String mail) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "UPDATE locataire SET mail = ? WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, mail);
            pstmt.setString(2, locataire.getNom());
            pstmt.setString(3, locataire.getPrénom());
            pstmt.setString(4, locataire.getTéléphone());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireGenre(Locataire locataire,String genre) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "UPDATE locataire SET genre = ? WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, genre);
            pstmt.setString(2, locataire.getNom());
            pstmt.setString(3, locataire.getPrénom());
            pstmt.setString(4, locataire.getTéléphone());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Locataire getLocataireByNomPrénomTel(String nom, String prénom, String téléphone) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Locataire locataire = null;
            ResultSet rs;
            String query="SELECT * FROM locataire WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, nom);
            pstmt.setString(2, prénom);
            pstmt.setString(3, téléphone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String genre = rs.getString("genre");
                String mail = rs.getString("mail");
                Date date_arrive = rs.getDate("date_arrive");
                locataire = new Locataire(nom, prénom, téléphone, mail, date_arrive, genre);
            }
            rs.close();
            pstmt.close();
            
            return locataire;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  List<Locataire> getAllLocataire() {
        try {
            Connection cn = ConnectionDB.getInstance();
            List<Locataire> locataires = new ArrayList<>();
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locataire");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prénom = rs.getString("prenom");
                String téléphone = rs.getString("téléphone");
                String genre = rs.getString("genre");
                String mail = rs.getString("mail");
                Date date_arrive = rs.getDate("date_arrive");

                Locataire locataire = new Locataire(nom, prénom, téléphone, mail, date_arrive, genre);
                locataires.add(locataire);
            }
            rs.close();
            stmt.close();
            
            return locataires;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteLocataire(Locataire locataire) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "DELETE FROM locataire WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getNom());
            pstmt.setString(2, locataire.getPrénom());
            pstmt.setString(3, locataire.getTéléphone());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getId(Locataire locataire){
        Integer idloc=(Integer)  null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id_loc FROM locataire WHERE prenom = ? AND nom = ? AND téléphone = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getPrénom());
            pstmt.setString(2, locataire.getNom());
            pstmt.setString(3, locataire.getTéléphone());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                idloc = rs.getInt("id_loc");
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idloc;
    }
}
