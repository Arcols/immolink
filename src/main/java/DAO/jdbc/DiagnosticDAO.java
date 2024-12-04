package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticDAO implements DAO.DiagnosticDAO{

    private Connection cn;

    @Override
    public void create(Diagnostic diagnostic,String numero_fiscal) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String requete = "INSERT INTO diagnostiques (id,pdf_diag, type, date_expiration) VALUES (?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(requete);
            pstmt.setInt(1, id);
            pstmt.setString(2, diagnostic.getPdfPath());
            pstmt.setString(3, diagnostic.getReference());
            pstmt.setDate(4, diagnostic.getDateInvalidite());
            pstmt.executeUpdate();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Diagnostic read(String numero_fiscal,String reference) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        List<Diagnostic> lDiags = null;
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updatePath(Diagnostic diagnostic, String numero_fiscal, String path) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "UPDATE diagnostiques SET pdf_diag = ? WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, path);
            pstmt.setInt(2, id);
            pstmt.setString(3, diagnostic.getReference());
            pstmt.executeUpdate();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDate(Diagnostic diagnostic, String numero_fiscal, Date date) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "UPDATE diagnostiques SET date_expiration = ? WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDate(1, date);
            pstmt.setInt(2, id);
            pstmt.setString(3, diagnostic.getReference());
            pstmt.executeUpdate();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String numero_fiscal, String reference) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "DELETE FROM diagnostiques WHERE id = ? AND type = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, reference);
            pstmt.executeUpdate();
            pstmt.close();
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Diagnostic> readAllDiag(int id) throws DAOException {

        List<Diagnostic> lDiags = new ArrayList<>();
        ConnectionDB db;
        Connection cn = null;
        try {
            db = ConnectionDB.getInstance();
            cn = db.getConnection();
            String query = "SELECT pdf_diag, type, date_expiration FROM diagnostiques WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String pdf = rs.getString("pdf_diag");
                String type = rs.getString("type");
                Date expi = rs.getDate("date_expiration");
                Diagnostic diag = new Diagnostic(type,pdf,expi);
                lDiags.add(new Diagnostic(type,pdf,expi));
            }
            rs.close();
            pstmt.close();
            cn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lDiags;
    }
}
