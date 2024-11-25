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

    }

    @Override
    public Locataire getLocataireById(int id) {
        return null;
    }

    @Override
    public List<Locataire> getAllLocataire() {
        ConnectionDB cn;
        try {
            cn = new ConnectionDB();
            List<Locataire> locataires = new ArrayList<>();
            try (Connection conn = cn.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM locataire")) {
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

    }
}
