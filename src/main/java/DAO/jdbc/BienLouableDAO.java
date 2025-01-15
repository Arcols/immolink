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
            GarageDAO garageDAO = new GarageDAO();
            Integer idGarage = garageDAO.getIdGarage(garage.getNumeroFiscal(), TypeLogement.GARAGE_PAS_ASSOCIE);
            pstmt.setInt(1, idGarage);
            pstmt.setString(2, bien.getNumeroFiscal());
            pstmt.setInt(3, getTypeFromId(getId(bien.getNumeroFiscal())).getValue());
            pstmt.executeUpdate();
            pstmt.close();
            garageDAO.updateTypeGarage(idGarage, TypeLogement.GARAGE_PAS_ASSOCIE, TypeLogement.GARAGE_ASSOCIE);
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
                Integer id = rs.getInt("id");
                String complement = rs.getString("complement_adresse");
                Integer type_logement = rs.getInt("type_logement");
                Integer nb_piece = rs.getInt("Nombre_pieces");
                Double surface = rs.getDouble("surface");
                Integer idGarage = rs.getInt("garage_assoc");
                Integer idBat = rs.getInt("idBat");
                Batiment bat = new BatimentDAO().readId(idBat);
                List<Diagnostic> diags = new DiagnosticDAO().readAllDiag(id);
                GarageDAO garageDAO = new GarageDAO();
                bien = new BienLouable(num_fiscal, bat.getVille(), bat.getAdresse(), complement, diags, idGarage,
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
        BienLouable bienLouable = null;
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
                BatimentDAO batDAO = new BatimentDAO();
                Batiment bat = batDAO.readId(rs.getInt("idBat"));
                String ville = bat.getVille();
                String adresse = bat.getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(id);
                GarageDAO garageDAO = new GarageDAO();
                bienLouable = new BienLouable(numero_fiscal, ville, adresse, complement_d_adresse, lDiags,
                        garageDAO.getIdGarage(numero_fiscal, TypeLogement.GARAGE_ASSOCIE), getTypeFromId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bienLouable;
    }

    @Override
    public Integer getId(String num_fiscal) throws DAOException {
        Integer id = 0;
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
        List<Devis> lDevis = new DevisDAO().getAllDevisFromABien(readId(id).getNumeroFiscal(), getTypeFromId(id));
        List<Integer> idBeaux = getListeBeauxFromBien(readId(id));
        List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(id);

        try {
            Connection cn = ConnectionDB.getInstance();
            Runnable supprimerBL = new Runnable() {
                @Override
                public void run() {
                    try {
                        String query = "DELETE FROM bienlouable WHERE id = ?";
                        PreparedStatement pstmt = cn.prepareStatement(query);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            for (Diagnostic diag : lDiags) {
                new DiagnosticDAO().delete(readId(id).getNumeroFiscal(), diag.getReference());
            }
            for (Devis d : lDevis) {
                Integer id_devis = new DevisDAO().getId(d);
                new TravauxAssocieDAO().delete(id_devis, id, getTypeFromId(id));
                new DevisDAO().delete(id_devis);
            }
            for (Integer id_beau : idBeaux) {
                new BailDAO().delete(id_beau);
            }
            if (haveGarage(id)) {
                GarageDAO garageDAO = new GarageDAO();
                Integer idGarage = garageDAO.readIdGarageFromBien(id);
                garageDAO.updateTypeGarage(idGarage, TypeLogement.GARAGE_ASSOCIE, TypeLogement.GARAGE_PAS_ASSOCIE);
                supprimerBL.run();
            } else {
                supprimerBL.run();
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
                Integer id_bat = rs.getInt("IdBat");
                String ville = new BatimentDAO().readId(id_bat).getVille();
                String adresse = new BatimentDAO().readId(id_bat).getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                GarageDAO garageDAO = new GarageDAO();
                Integer type_logement = rs.getInt("type_logement");
                TypeLogement type = TypeLogement.fromInt(type_logement);
                if (type.estBienLouable()) {
                    Allbien.add(new BienLouable(num_fisc, ville, adresse, compl, lDiags,
                            garageDAO.getIdGarage(num_fisc, TypeLogement.GARAGE_ASSOCIE), type));
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
    public Integer getTypeFromCompl(String ville, String adresse, String complement) {
        Integer type = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBat);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                type = rs.getInt("type_logement");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    @Override
    public Integer getNbPieceFromCompl(String ville, String adresse, String complement) {
        Integer nb_pieces = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBat);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nb_pieces = rs.getInt("Nombre_pieces");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nb_pieces;
    }

    @Override
    public Double getSurfaceFromCompl(String ville, String adresse, String complement) {
        Double surface = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBat);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                surface = rs.getDouble("surface");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return surface;
    }

    @Override
    public String getFiscFromCompl(String ville, String adresse, String complement) {
        String fisc = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            Integer idBat = new BatimentDAO().getIdBat(ville, adresse);
            String query = "SELECT * FROM bienlouable WHERE idBat = ? AND complement_adresse = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, idBat);
            pstmt.setString(2, complement);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fisc = rs.getString("numero_fiscal");
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fisc;
    }

    @Override
    public Bail getBailFromBien(BienLouable bien) {
        Bail bail = null;
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT * FROM bail WHERE id_bien_louable = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setInt(1, new BienLouableDAO().getId(bien.getNumeroFiscal()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Integer solde_de_compte = rs.getInt("solde_de_compte");
                Double loyer = rs.getDouble("loyer");
                Double charges = rs.getDouble("charges");
                Double depot_garantie = rs.getDouble("depot_garantie");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                Double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                bail = new Bail((solde_de_compte == 1), bien.getNumeroFiscal(), loyer, charges, depot_garantie,
                        date_debut, date_fin, icc, index_eau, dernier_anniversaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return bail;
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
                Integer solde_de_compte = rs.getInt("solde_de_compte");
                Double loyer = rs.getDouble("loyer");
                Double charges = rs.getDouble("charges");
                Double depot_garantie = rs.getDouble("depot_garantie");
                Date date_debut = rs.getDate("date_debut");
                Date date_fin = rs.getDate("date_fin");
                Double icc = rs.getDouble("icc");
                Integer index_eau = rs.getInt("index_eau");
                Date dernier_anniversaire = rs.getDate("date_dernier_anniversaire");
                bail = new Bail((solde_de_compte == 1), bien.getNumeroFiscal(), loyer, charges, depot_garantie,
                        date_debut, date_fin, icc, index_eau, dernier_anniversaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return bail;
    }

    @Override
    public Map<String, List<String>> getAllcomplements() throws SQLException {
        Map<String, List<String>> adresses = new HashMap<>();
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "SELECT adresse, id FROM batiment";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String adresse = rs.getString("adresse");
                String idBat = rs.getString("id");
                adresses.putIfAbsent(adresse, new ArrayList<>());
                String query2 = "SELECT complement_adresse FROM bienlouable WHERE idBat = ?";
                PreparedStatement pstmt2 = cn.prepareStatement(query2);
                pstmt2.setString(1, idBat);
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
                // Ajoute le complément à la liste associée à l'adresse
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
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
    public void délierGarage(Integer idBien) throws DAOException {
        Integer idGarage = new GarageDAO().readIdGarageFromBien(idBien);
        try {
            Connection cn = ConnectionDB.getInstance();
            String query = "UPDATE bienlouable SET garage_assoc = ? WHERE id = ?";
            PreparedStatement pstmt = cn.prepareStatement(query);
            pstmt.setNull(1, java.sql.Types.INTEGER);
            pstmt.setInt(2, idBien);
            pstmt.executeUpdate();
            pstmt.close();
            new GarageDAO().updateTypeGarage(idGarage, TypeLogement.GARAGE_ASSOCIE, TypeLogement.GARAGE_PAS_ASSOCIE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BienLouable> getAllBienLouableNoGarageLink() throws DAOException {
        List<BienLouable> listBienlouable = new ArrayList<>();
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
                Integer id_bat = rs.getInt("IdBat");
                String ville = new BatimentDAO().readId(id_bat).getVille();
                String adresse = new BatimentDAO().readId(id_bat).getAdresse();
                List<Diagnostic> lDiags = new DiagnosticDAO().readAllDiag(rs.getInt("id"));
                GarageDAO garageDAO = new GarageDAO();
                Integer type_logement = rs.getInt("type_logement");
                TypeLogement type = TypeLogement.fromInt(type_logement);
                if (type.equals(TypeLogement.APPARTEMENT)
                        || type.equals(TypeLogement.MAISON)) {
                    listBienlouable.add(new BienLouable(num_fisc, ville, adresse, compl, lDiags,
                            garageDAO.getIdGarage(num_fisc, TypeLogement.GARAGE_ASSOCIE), type));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBienlouable;
    }
}
