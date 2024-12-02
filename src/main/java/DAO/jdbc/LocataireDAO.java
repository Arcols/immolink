package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocataireDAO implements DAO.LocataireDAO {

    @Override
    public void addLocataire(Locataire locataire) {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            Statement st = cn.getConnection().createStatement();
            String query = "INSERT INTO locataire (nom, prenom, téléphone, date_arrive,mail,loc_actuel,genre) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
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
            cn.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateLocataire(Locataire locataire) {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            Statement st = cn.getConnection().createStatement();
            String query = "UPDATE locataire SET nom = ?, prenom = ?, téléphone = ?, date_arrive = ?, mail = ?, genre = ? WHERE id_loc = ?";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setString(1, locataire.getNom());
            pstmt.setString(2, locataire.getPrénom());
            pstmt.setString(3, locataire.getTéléphone());
            pstmt.setDate(4, locataire.getDateArrive());
            pstmt.setString(5, locataire.getMail());
            pstmt.setString(6, locataire.getGenre());
            pstmt.setInt(7, locataire.getId());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            cn.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Locataire getLocataireById(int id) {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            Locataire locataire = null;
            try (Connection conn = cn.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM locataire WHERE id_loc = " + id)) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String prénom = rs.getString("prenom");
                    String téléphone = rs.getString("téléphone");
                    String genre = rs.getString("genre");
                    String mail = rs.getString("mail");
                    Date date_arrive = rs.getDate("date_arrive");

                    locataire = new Locataire(nom, prénom, téléphone, mail, date_arrive, genre,id);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return locataire;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  List<Locataire> getAllLocataire() {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            List<Locataire> locataires = new ArrayList<>();
            try (Connection conn = cn.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM locataire")) {
                while (rs.next()) {
                    int id = rs.getInt("id_loc");
                    String nom = rs.getString("nom");
                    String prénom = rs.getString("prenom");
                    String téléphone = rs.getString("téléphone");
                    String genre = rs.getString("genre");
                    String mail = rs.getString("mail");
                    Date date_arrive = rs.getDate("date_arrive");

                    Locataire locataire = new Locataire(nom, prénom, téléphone, mail, date_arrive, genre,id);
                    locataires.add(locataire);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return locataires;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteLocataire(int id) {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            Statement st = cn.getConnection().createStatement();
            String query = "DELETE FROM locataire WHERE id_loc = ?";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
            cn.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLastIdLocataire() {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            int id = 0;
            try (Connection conn = cn.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT MAX(id_loc) FROM locataire")) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
