package projet.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Locataire {

    private String nom;

    private String prénom;

    private String téléphone;

    private String genre;

    private String mail;

    private Date date_arrive;

    public List<Bail> bails = new ArrayList<Bail> ();

    public List<Charge> charges = new ArrayList<Charge> ();

    public Locataire(String nom, String prénom, String téléphone, String mail,Date dateArrivee,String genre) throws SQLException {
        this.nom = nom;
        this.prénom = prénom;
        this.téléphone = téléphone;
        this.genre = genre;
        //this.bails.add(bail);
        this.mail=mail;
        this.date_arrive=dateArrivee;
        insertIntoTable(nom, prénom, téléphone, dateArrivee,mail,genre);
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
}
