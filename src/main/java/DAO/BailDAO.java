package DAO;

import classes.Bail;

import java.sql.SQLException;
import java.util.List;

public interface BailDAO {
        /**
         * Crée un nouveau bail dans la base de données.
         *
         * @param bail L'objet bail à insérer
         * @throws DAOException             en cas d'erreur lors de la création du bien
         *                                  bail
         * @throws SQLException
         * @throws IllegalArgumentException
         */
        void create(Bail bail) throws DAOException;

        int getId(Bail bail);

        List<Bail> getAllBaux();
}
