package DAO;

import classes.Diagnostic;

import java.util.List;

public interface DiagnosticDAO {

    public List<Diagnostic> readAllDiag(String numero_fiscal) throws DAOException;
}
