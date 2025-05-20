package DAO.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import classes.*;
import enumeration.*;

public class BienLouableDAO implements DAO.BienLouableDAO {

    @Override
    public void create(BienLouable bien, TypeLogement type, int nb_piece, double surface) {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "INSERT INTO bienlouable (numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, idBat, garage_assoc) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, bien.getNumeroFiscal());
            pstmt.setString(2, bien.getComplementAdresse());
            pstmt.setInt(3, type.getValue());
            pstmt.setInt(4, nb_piece);
            pstmt.setDouble(5, surface);
            pstmt.setInt(6, new BatimentDAO().getIdBat(bien.getVille(), bien.getAdresse()));
            pstmt.setNull(7, java.sql.Types.INTEGER); // si on veut ajouter un garagon on utilisera
                                                      // ajouterUnGarageAuBienLouable par la suite
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException | DAOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void lierUnGarageAuBienLouable(BienLouable bien, Garage garage) throws DAOException {
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bienlouable SET garage_assoc = ? WHERE numero_fiscal = ? AND type_logement = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            GarageDAO garage_DAO = new GarageDAO();
            Integer id_garage = garage_DAO.getIdGarage(garage.getNumeroFiscal(), TypeLogement.GARAGE_PAS_ASSOCIE);
            pstmt.setInt(1, id_garage);
            pstmt.setString(2, bien.getNumeroFiscal());
            pstmt.setInt(3, getTypeFromId(getId(bien.getNumeroFiscal())).getValue());
            pstmt.executeUpdate();
            pstmt.close();
            garage_DAO.updateTypeGarage(id_garage, TypeLogement.GARAGE_PAS_ASSOCIE, TypeLogement.GARAGE_ASSOCIE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BienLouable readFisc(String num_fiscal) throws DAOException {
        BienLouable bien = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id,numero_fiscal, complement_adresse, type_logement, Nombre_pieces, surface, garage_assoc, idBat FROM bienlouable WHERE numero_fiscal = ? ";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String complement = rs.getString("complement_adresse");
                int type_logement = rs.getInt("type_logement");
                Integer id_garage = rs.getInt("garage_assoc");
                int id_batiment = rs.getInt("idBat");
                Batiment bat = new BatimentDAO().readId(id_batiment);
                List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(id);
                bien = new BienLouable(num_fiscal, bat.getVille(), bat.getAdresse(), complement, diags, id_garage,
                        TypeLogement.fromInt(type_logement));
            }
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated c
            // patch block
            throw new RuntimeException(e);
        }
        return bien;
    }

    @Override
    public BienLouable readId(int id) throws DAOException {
        BienLouable bien_louable = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable WHERE id = ? AND type_logement = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setInt(2, getTypeFromId(id).getValue());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String numero_fiscal = rs.getString("numero_fiscal");
                String complement_d_adresse = rs.getString("complement_adresse");
                BatimentDAO bat_DAO = new BatimentDAO();
                Batiment bat = bat_DAO.readId(rs.getInt("idBat"));
                String ville = bat.getVille();
                String adresse = bat.getAdresse();
                List<Diagnostic> liste_diagnostics = new DiagnosticDAO().readAllDiag(id);
                GarageDAO garage_DAO = new GarageDAO();
                bien_louable = new BienLouable(numero_fiscal, ville, adresse, complement_d_adresse, liste_diagnostics,
                        garage_DAO.getIdGarage(numero_fiscal, TypeLogement.GARAGE_ASSOCIE), getTypeFromId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bien_louable;
    }

    @Override
    public Integer getId(String num_fiscal) throws DAOException {
        int id = 0;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM bienlouable WHERE numero_fiscal = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setString(1, num_fiscal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void delete(int id) throws DAOException {
        List<Devis> liste_devis = new DevisDAO().getAllDevisFromABien(readId(id).getNumeroFiscal(), getTypeFromId(id));
        List<Integer> liste_id_baux = getListeBeauxFromBien(readId(id));
        List<Diagnostic> liste_diagnostics = new DiagnosticDAO().readAllDiag(id);

        try {
            Connection cn = ConnectionDB.getInstance();
            Runnable supprimer_bien_louable = () -> {
                try {
                    String query = "DELETE FROM bienlouable WHERE id = ?";
                    PreparedStatement pstmt = cn.prepareStatement(query);
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            for (Diagnostic diag : liste_diagnostics) {
                new DiagnosticDAO().delete(readId(id).getNumeroFiscal(), diag.getReference());
            }
            for (Devis d : liste_devis) {
                Integer id_devis = new DevisDAO().getId(d);
                new TravauxAssocieDAO().delete(id_devis, id, getTypeFromId(id));
                new DevisDAO().delete(id_devis);
            }
            for (Integer id_beau : liste_id_baux) {
                new BailDAO().delete(id_beau);
            }
            if (haveGarage(id)) {
                GarageDAO garage_DAO = new GarageDAO();
                Integer id_garage = garage_DAO.readIdGarageFromBien(id);
                garage_DAO.updateTypeGarage(id_garage, TypeLogement.GARAGE_ASSOCIE, TypeLogement.GARAGE_PAS_ASSOCIE);
                supprimer_bien_louable.run();
            } else {
                if(getTypeFromId(id).equals(TypeLogement.GARAGE_ASSOCIE)){
                    new GarageDAO().delete(id, TypeLogement.GARAGE_ASSOCIE);
                }
                supprimer_bien_louable.run();
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BienLouable> findAll() throws DAOException {
        List<BienLouable> Allbien = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String num_fisc = rs.getString("numero_fiscal");
                String compl = rs.getString("complement_adresse");
                int id_bat = rs.getInt("IdBat");
                String ville = new BatimentDAO().readId(id_bat).getVille();
                String adresse = new BatimentDAO().readId(id_bat).getAdresse();
                List<Diagnostic> liste_diagnostics = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                GarageDAO garage_DAO = new GarageDAO();
                int type_logement = rs.getInt("type_logement");
                TypeLogement type = TypeLogement.fromInt(type_logement);
                if (type.estBienLouable()) {
                    Allbien.add(new BienLouable(num_fisc, ville, adresse, compl, liste_diagnostics,
                            garage_DAO.getIdGarage(num_fisc, TypeLogement.GARAGE_ASSOCIE), type));
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return Allbien;
    }



    @Override
    public Integer getNbPieceFromCompl(String ville, String adresse, String complement) {
        Integer nb_pieces = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            int id_batiment = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_batiment);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nb_pieces = rs.getInt("Nombre_pieces");
            }
        } catch (DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return nb_pieces;
    }

    @Override
    public Double getSurfaceFromCompl(String ville, String adresse, String complement) {
        Double surface = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            int id_batiment = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_batiment);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                surface = rs.getDouble("surface");
            }
        } catch (DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return surface;
    }

    @Override
    public String getFiscFromCompl(String ville, String adresse, String complement) {
        String fisc = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            int id_batiment = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id_batiment);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fisc = rs.getString("numero_fiscal");
            }
        } catch (DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return fisc;
    }



    @Override
    public Bail getBailFromBienAndDate(BienLouable bien, Date annne) {
        Bail bail = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bail WHERE id_bien_louable = ? AND YEAR(date_fin) >= YEAR(?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, new BienLouableDAO().getId(bien.getNumeroFiscal()));
            pstmt.setDate(2, annne);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int solde_de_compte = rs.getInt("solde_de_compte");
                double loyer = rs.getDouble("loyer");
                double charges = rs.getDouble("charges");
                double depot_garantie = rs.getDouble("depot_garantie");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                bail = new Bail((solde_de_compte == 1), bien.getNumeroFiscal(), loyer, charges, depot_garantie,
                        date_debut, date_fin, icc, index_eau, dernier_anniversaire);
            }
        } catch (SQLException | DAOException e) {
            throw new RuntimeException(e);
        }
        return bail;
    }

    @Override
    public Map<String, List<String>> getAllcomplements() {
        Map<String, List<String>> adresses = new HashMap<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT adresse, id FROM batiment";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String adresse = rs.getString("adresse");
                String id_batiment = rs.getString("id");
                adresses.putIfAbsent(adresse, new ArrayList<>());
                String query2 = "SELECT complement_adresse FROM bienlouable WHERE idBat = ?";
                PreparedStatement pstmt2 = cn.prepareStatement(query2);
                pstmt2.setString(1, id_batiment);
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()) {
                    String compl = rs2.getString("complement_adresse");
                    adresses.get(adresse).add(compl);
                }
                rs2.close();
                pstmt2.close();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return adresses;
    }

    @Override
    public Map<String, List<String>> getAllComplBail() {
        Map<String, List<String>> complements = new HashMap<>();
        String query = "SELECT b.adresse, bl.complement_adresse FROM batiment b, bienlouable bl WHERE b.id IN (SELECT idBat FROM bienlouable) AND b.id = bl.idBat;";
        try {
            Connection cn = ConnectionDB.getInstance();
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String adresse = rs.getString("adresse");
                String complement = rs.getString("complement_adresse");
                // Ajoute le complement à la liste associee à l'adresse
                complements.computeIfAbsent(adresse, k -> new ArrayList<>()).add(complement);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complements;
    }

    @Override
    public boolean haveGarage(Integer id) {
        boolean garage = false;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                garage = rs.getInt("garage_assoc") != 0 || rs.getObject("garage_assoc") != null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return garage;
    }

    @Override
    public List<Integer> getListeBeauxFromBien(BienLouable bien) {
        List<Integer> id_beaux = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT id FROM bail WHERE id_bien_louable = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, new BienLouableDAO().getId(bien.getNumeroFiscal()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id_beaux.add(rs.getInt("id"));
            }
        } catch (SQLException | DAOException e) {
            throw new RuntimeException(e);
        }
        return id_beaux;
    }

    @Override
    public TypeLogement getTypeFromId(int id) {
        TypeLogement type = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT type_logement FROM bienlouable WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                type = TypeLogement.fromInt(rs.getInt("type_logement"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    @Override
    public void delierGarage(Integer id_bien) throws DAOException {
        Integer id_garage = new GarageDAO().readIdGarageFromBien(id_bien);
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bienlouable SET garage_assoc = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setNull(1, java.sql.Types.INTEGER);
            pstmt.setInt(2, id_bien);
            pstmt.executeUpdate();
            pstmt.close();
            new GarageDAO().updateTypeGarage(id_garage, TypeLogement.GARAGE_ASSOCIE, TypeLogement.GARAGE_PAS_ASSOCIE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BienLouable> getAllBienLouableNoGarageLink() throws DAOException {
        List<BienLouable> liste_bien_louable = new ArrayList<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bienlouable WHERE (garage_assoc = -1 OR garage_assoc = 0 OR garage_assoc IS NULL) AND (type_logement = ? OR type_logement = ?)";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, TypeLogement.APPARTEMENT.getValue());
            pstmt.setInt(2, TypeLogement.MAISON.getValue());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String num_fisc = rs.getString("numero_fiscal");
                String compl = rs.getString("complement_adresse");
                int id_bat = rs.getInt("IdBat");
                String ville = new BatimentDAO().readId(id_bat).getVille();
                String adresse = new BatimentDAO().readId(id_bat).getAdresse();
                List<Diagnostic> liste_diagnostics = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                GarageDAO garage_DAO = new GarageDAO();
                int type_logement = rs.getInt("type_logement");
                TypeLogement type = TypeLogement.fromInt(type_logement);
                if (type.equals(TypeLogement.APPARTEMENT)
                        || type.equals(TypeLogement.MAISON)) {
                    liste_bien_louable.add(new BienLouable(num_fisc, ville, adresse, compl, liste_diagnostics,
                            garage_DAO.getIdGarage(num_fisc, TypeLogement.GARAGE_ASSOCIE), type));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_bien_louable;
    }

}
