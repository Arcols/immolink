package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocataireDAO implements DAO.LocataireDAO {
    private Connection cn;
    @Override
    public void addLocataire(Locataire locataire) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireTel(Locataire locataire,String tel) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireMail(Locataire locataire,String mail) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireGenre(Locataire locataire,String genre) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Locataire getLocataireByNomPrénomTel(String nom, String prénom, String téléphone) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
            return locataire;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  List<Locataire> getAllLocataire() {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
            cn.close();
            return locataires;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteLocataire(Locataire locataire) {
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            Statement st = cn.createStatement();
            String query = "DELETE FROM locataire WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getNom());
            pstmt.setString(2, locataire.getPrénom());
            pstmt.setString(3, locataire.getTéléphone());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            cn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
