package DAO.jdbc;

import DAO.db.ConnectionDB;
import classes.Locataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocataireDAO implements DAO.LocataireDAO {
    @Override
    public void addLocataire(Locataire locataire) {
        try {
            Connection cn = ConnectionDB.getInstance();
            Statement st = cn.createStatement();
            String query = "INSERT INTO locataire (nom, prenom, lieu_naissance, date_naissance, téléphone, date_arrive,mail,loc_actuel,genre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getNom());
            pstmt.setString(2, locataire.getPrénom());
            pstmt.setString(3,locataire.getLieuNaissance());
            pstmt.setString(4,locataire.getDateNaissance());
            pstmt.setString(5, locataire.getTéléphone());
            pstmt.setDate(6, locataire.getDateArrive());
            pstmt.setString(7, locataire.getMail());
            pstmt.setInt(8, 1); // requette pour la loc actuelle ?
            pstmt.setString(9, locataire.getGenre());
            pstmt.executeUpdate();
            pstmt.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLocataireTel(Locataire locataire, String tel) {
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
    public void updateLocataireMail(Locataire locataire, String mail) {
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
    public void updateLocataireGenre(Locataire locataire, String genre) {
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
            String query = "SELECT * FROM locataire WHERE nom = ? AND prenom = ? AND téléphone = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, nom);
            pstmt.setString(2, prénom);
            pstmt.setString(3, téléphone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String lieuNaissance = rs.getString("lieu_naissance");
                String dateNaissance = rs.getString("date_naissance");
                String genre = rs.getString("genre");
                String mail = rs.getString("mail");
                Date date_arrive = rs.getDate("date_arrive");
                locataire = new Locataire(nom, prénom, lieuNaissance, dateNaissance, téléphone, mail, date_arrive, genre);
            }
            rs.close();
            pstmt.close();

            return locataire;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Locataire> getAllLocataire() {
        try {
            Connection cn = ConnectionDB.getInstance();
            List<Locataire> locataires = new ArrayList<>();
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locataire");
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prénom = rs.getString("prenom");
                String lieu_naissance = rs.getString("lieu_naissance");
                String date_naissance = rs.getString("date_naissance");
                String téléphone = rs.getString("téléphone");
                String genre = rs.getString("genre");
                String mail = rs.getString("mail");
                Date date_arrive = rs.getDate("date_arrive");

                Locataire locataire = new Locataire(nom, prénom,lieu_naissance,date_naissance, téléphone, mail, date_arrive, genre);
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
    public int getId(Locataire locataire) {
        Integer idloc = (Integer) null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id_loc FROM locataire WHERE prenom = ? AND nom = ? AND téléphone = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, locataire.getPrénom());
            pstmt.setString(2, locataire.getNom());
            pstmt.setString(3, locataire.getTéléphone());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idloc = rs.getInt("id_loc");
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idloc;
    }

    @Override
    public Locataire getLocFromId(int id) {
        Locataire locataire = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            ResultSet rs;
            String query = "SELECT * FROM locataire WHERE id_loc= ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prénom = rs.getString("prenom");
                String lieu_naissance = rs.getString("lieu_naissance");
                String date_naissance = rs.getString("date_naissance");
                String téléphone = rs.getString("téléphone");
                String genre = rs.getString("genre");
                String mail = rs.getString("mail");
                Date date_arrive = rs.getDate("date_arrive");
                locataire = new Locataire(nom, prénom, lieu_naissance, date_naissance, téléphone, mail, date_arrive, genre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locataire;
    }

    public List<Integer> getBauxLocataire(Integer idLocataire) {
        List<Integer> idBaux = new LinkedList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id_bail FROM louer WHERE louer.id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idLocataire);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer idBail = rs.getInt("id_bail");
                idBaux.add(idBail);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idBaux;
    }


}