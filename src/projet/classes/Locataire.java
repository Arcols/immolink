package projet.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import projet.DAO.db.ConnectionDB;

public class Locataire {

    private String nom;

    private String prénom;

    private String téléphone;

    private String genre;

    private String mail;

    private Date date_arrive;

    public List<Bail> bails = new ArrayList<Bail> ();

    public List<Charge> charges;

    public Locataire(String nom, String prénom, String téléphone, String mail,Date date_arrivee,String genre) throws SQLException {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.genre = genre;
        //this.bails.add(bail);
        this.charges = new ArrayList<Charge>();
        this.mail=mail;
        this.date_arrive=date_arrivee;
        insertIntoTable(nom, prénom, téléphone, date_arrivee,mail,genre);
    }
    public Locataire(String nom, String prénom, String téléphone,Date date_arrive,String genre) throws SQLException {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.genre = genre;
        this.date_arrive=date_arrive;
        //this.bails.add(bail);
        this.mail=null;
        insertIntoTable(nom, prénom, téléphone, date_arrive,mail,genre);
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrénom() {
        return this.prénom;
    }

    public String getTéléphone() {
        return this.téléphone;
    }
    
    public String getMail() {
    	return this.mail;
    }
    
    public Date getDateArrive() {
    	return this.date_arrive;
    }

    public String getGenre() {
        return this.genre;
    }

    public List<Bail> getBails() {
        return this.bails;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    public void addCharge(Charge charge){
        this.charges.add(charge);
    }
    
    public void addBail(Bail bail){
        this.bails.add(bail);
    }

    private void insertIntoTable(String nom, String prénom, String téléphone, Date date_arrivee,String mail,String genre) throws SQLException{
        ConnectionDB db = new ConnectionDB();
        Statement st = db.getConnection().createStatement();
		String query = "INSERT INTO locataire (nom, prenom, téléphone, date_arrive,mail,loc_actuel,genre) VALUES (?, ?, ?, ?, ?, ?,?)";
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setString(1, nom); 
		pstmt.setString(2, prénom); 
        pstmt.setString(3, téléphone); 
        pstmt.setDate(4, date_arrivee);        
        pstmt.setString(5, mail); 
        pstmt.setInt(6,1);
        pstmt.setString(7, genre); 
        pstmt.executeUpdate();
        pstmt.close();
        st.close();
        db.closeConnection(); 
    }
    public static List<Locataire> getAllLocataires() throws SQLException {
        List<Locataire> locataires = new ArrayList<>();
        ConnectionDB db = new ConnectionDB();

        try (Connection conn = db.getConnection();
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
    }

}
