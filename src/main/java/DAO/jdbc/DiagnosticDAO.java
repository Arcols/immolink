package DAO.jdbc;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Batiment;
import classes.BienLouable;
import classes.Diagnostic;
import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DiagnosticDAO implements DAO.DiagnosticDAO{
    @Override
    public List<Diagnostic> readAllDiag(String numero_fiscal) throws DAOException {
        BienLouable bien = new BienLouableDAO().readFisc(numero_fiscal);
        Integer id = new BienLouableDAO().getId(numero_fiscal);
        ConnectionDB cn;
       List<Diagnostic> lDiags = null;
        try {
            cn = new ConnectionDB();
            String query = "SELECT pdf_diag, type, date_expiration FROM diagnostiques WHERE id = ?";
            PreparedStatement pstmt = cn.getConnection().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String pdf = rs.getString("pdf_diag");
                String type = rs.getString("type");
                Date expi = rs.getDate("date_expiration");
                lDiags.add(new Diagnostic(type,pdf,expi));
            }
            pstmt.close();
            cn.closeConnection();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lDiags;
    }
}
