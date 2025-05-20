package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Diagnostic;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticDAO implements DAO.DiagnosticDAO{


    @Override
    public void create(Diagnostic diagnostic,String numero_fiscal) throws DAOException {
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        try {
            Connection cn = ConnectionDB.getInstance();
            String requete = "INSERT INTO diagnostiques (id,pdf_diag, type, date_expiration) VALUES (?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1, id);
            pstmt.setString(2, diagnostic.getPdfPath());
            pstmt.setString(3, diagnostic.getReference());
            pstmt.setDate(4, diagnostic.getDateInvalidite());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Diagnostic read(String numero_fiscal,String reference) throws DAOException {
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT pdf_diag, type, date_expiration FROM diagnostiques WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                String pdf = rs.getString("pdf_diag");
                String type = rs.getString("type");
                Date expi = rs.getDate("date_expiration");
                return new Diagnostic(type,pdf,expi);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    @Override
    public void updateDate(Diagnostic diagnostic, String numero_fiscal, Date date) throws DAOException {
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE diagnostiques SET date_expiration = ? WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDate(1, date);
            pstmt.setInt(2, id);
            pstmt.setString(3, diagnostic.getReference());
            pstmt.executeUpdate();
            pstmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String numero_fiscal, String reference) throws DAOException {
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "DELETE FROM diagnostiques WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, reference);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Diagnostic> readAllDiag(int id) {
        List<Diagnostic> liste_diagnostic = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT pdf_diag, type, date_expiration FROM diagnostiques WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String pdf = rs.getString("pdf_diag");
                String type = rs.getString("type");
                Date expi = rs.getDate("date_expiration");
                Diagnostic diag = new Diagnostic(type,pdf,expi);
                liste_diagnostic.add(diag);
            }
            rs.close();
            pstmt.close();
            

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return liste_diagnostic;
    }

    @Override
    public List<String> readDiagPerimes() {
        List<String> lNotifs = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT di.type, di.date_expiration, ba.adresse ,bl.complement_adresse, ba.ville  " +
                    "FROM diagnostiques di, bienlouable bl, batiment ba  " +
                    "WHERE di.id = bl.id AND bl.idBat = ba.id " +
                    "AND di.date_expiration < CURRENT_DATE";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String type = rs.getString("type");
                Date expi = rs.getDate("date_expiration");
                String adresse = rs.getString("adresse");
                String complement = rs.getString("complement_adresse");
                String ville = rs.getString("ville");
                lNotifs.add("<html>Le <b>"+type+"</b> du logement situe au <b>"+adresse+" "+complement+" "+ville+"</b> a expire depuis le <b>"+ expi.toString()+"</b></html>");
            }
            rs.close();
            pstmt.close();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return lNotifs;
    }

}
