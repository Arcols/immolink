package DAO.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.Bail;
import classes.Locataire;

public class LouerDAO implements DAO.LouerDAO{
        @Override
        public void create(Locataire locataire, Bail bail, int quotite) throws DAOException {
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "INSERT INTO louer (id_bail,id_locataire,quotite,dernier_paiement) VALUES (?,?,?,?)";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,new BailDAO().getId(bail));
                pstmt.setInt(2, new LocataireDAO().getId(locataire));
                pstmt.setInt(3,quotite);
                pstmt.setDate(4,bail.getDateDebut());
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean locInBail(int idloc, int idBail){
                int nb = 0;
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "SELECT count(*) FROM louer WHERE id_bail = ? and id_locataire = ?";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,idBail);
                pstmt.setInt(2,idloc);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    nb = rs.getInt("count(*)");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  nb > 0;
        }
        @Override
        public List<Integer> getIdLoc(int idBail){
            List<Integer> idLocataires = new ArrayList<>() ;
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "SELECT id_locataire FROM louer WHERE id_bail = ?";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setInt(1,idBail);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    int id = rs.getInt("id_locataire");
                    idLocataires.add(id);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  idLocataires;
        }

    @Override
    public Integer getQuotité(int idBail, int idLocataire) {
        Integer quotite = -1;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT quotite FROM louer WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBail);
            pstmt.setInt(2,idLocataire);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                quotite = rs.getInt("quotite");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quotite;
    }

    @Override
    public void delete(int idBail, int idLocataire) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "DELETE FROM louer WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,idBail);
            pstmt.setInt(2,idLocataire);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateQuotite(int idBail, int idLocataire, int quotite) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE louer SET quotite = ? WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1,quotite);
            pstmt.setInt(2,idBail);
            pstmt.setInt(3,idLocataire);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, List<Integer>> getAllLocatairesDesBeaux() {
            Map<Integer, List<Integer>> locataires = new HashMap<>();
            try {
                Connection cn = ConnectionDB.getInstance();
                String query = "SELECT id_bail,id_locataire FROM louer";
                PreparedStatement pstmt = cn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    int idBail = rs.getInt("id_bail");
                    int idLocataire = rs.getInt("id_locataire");
                    if (locataires.containsKey(idBail)){
                        locataires.get(idBail).add(idLocataire);
                    } else {
                        List<Integer> locataire = new ArrayList<>();
                        locataire.add(idLocataire);
                        locataires.put(idBail,locataire);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return locataires;
    }

    @Override
    public Boolean getStatut(int idLocataire) {
        Boolean statut = null;

        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "{ ? = call check_statut(?) }";
            CallableStatement cstmt = cn.prepareCall(query);
            cstmt.registerOutParameter(1, Types.BOOLEAN);
            cstmt.setInt(2, idLocataire);
            cstmt.execute();
            statut = cstmt.getBoolean(1);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
        }
        return statut;
    }

    @Override
    public Boolean getStatutBail(int idBail) {
        Boolean statut = null;

        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "{ ? = call check_statut_bail(?) }";
            CallableStatement cstmt = cn.prepareCall(query);
            cstmt.registerOutParameter(1, Types.BOOLEAN);
            cstmt.setInt(2, idBail);
            cstmt.execute();
            statut = cstmt.getBoolean(1);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
        }
        return statut;
    }

    @Override
    public Boolean getLoyerPaye(int idLocataire, int idBail) {
        Date date = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT dernier_paiement FROM louer WHERE id_bail = ? and id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBail);
            pstmt.setInt(2, idLocataire);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                date = rs.getDate("dernier_paiement");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Vérification si la date est null
        if (date == null) {
            return false; // Si pas de date de paiement, on retourne false (aucun paiement effectué)
        }

        // Convertir la java.sql.Date en java.time.LocalDate pour éviter UnsupportedOperationException
        LocalDate paymentDate = date.toLocalDate();

        // Comparer avec le premier jour du mois courant
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

        return paymentDate.isAfter(firstDayOfCurrentMonth.minusDays(1));  // Si paiement est après ou le premier jour du mois
    }

    @Override
    public void updatePaiement(int idBail, int idLocataire, Date date) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE louer SET dernier_paiement = ? WHERE id_bail = ? AND id_locataire = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setDate(1,date);
            pstmt.setInt(2,idBail);
            pstmt.setInt(3,idLocataire);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}