-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 23 jan. 2025 à 11:55
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `immolink`
--

DELIMITER $$
--
-- Fonctions
--
DROP FUNCTION IF EXISTS `check_statut`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_statut` (`id_locataire_input` INT) RETURNS TINYINT  BEGIN
    DECLARE toutes_apres INT;

        SELECT COUNT(*) INTO toutes_apres
    FROM louer
    WHERE id_locataire = id_locataire_input
      AND dernier_paiement <= DATE_FORMAT(CURDATE(), '%Y-%m-01');

        RETURN (toutes_apres = 0);
END$$

DROP FUNCTION IF EXISTS `check_statut_bail`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_statut_bail` (`id_bail_input` INT) RETURNS TINYINT  BEGIN
    DECLARE toutes_apres INT;

        SELECT COUNT(*) INTO toutes_apres
    FROM louer
    WHERE id_bail = id_bail_input
      AND dernier_paiement <= DATE_FORMAT(CURDATE(), '%Y-%m-01');

        RETURN (toutes_apres = 0);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `bail`
--

DROP TABLE IF EXISTS `bail`;
CREATE TABLE IF NOT EXISTS `bail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `solde_de_compte` tinyint(1) NOT NULL DEFAULT '0',
  `id_bien_louable` int NOT NULL,
  `loyer` double DEFAULT NULL,
  `charges` double DEFAULT NULL,
  `depot_garantie` double DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `ICC` decimal(8,2) DEFAULT NULL,
  `index_eau` int UNSIGNED DEFAULT NULL,
  `date_dernier_anniversaire` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bail_bien_louable` (`id_bien_louable`)
) ENGINE=InnoDB AUTO_INCREMENT=2014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Déclencheurs `bail`
--
DROP TRIGGER IF EXISTS `before_insert_bail`;
DELIMITER $$
CREATE TRIGGER `before_insert_bail` BEFORE INSERT ON `bail` FOR EACH ROW BEGIN
    DECLARE overlap_count INT;

        SELECT COUNT(*) INTO overlap_count
    FROM bail
    WHERE id_bien_louable = NEW.id_bien_louable
      AND (
           (NEW.date_debut BETWEEN date_debut AND date_fin)
           OR (NEW.date_fin BETWEEN date_debut AND date_fin)
           OR (NEW.date_debut <= date_debut AND NEW.date_fin >= date_fin)
      );

        IF overlap_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Erreur : chevauchement de dates pour ce bien louable';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `batiment`
--

DROP TABLE IF EXISTS `batiment`;
CREATE TABLE IF NOT EXISTS `batiment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_fiscal` char(12) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `code_postal` varchar(5) NOT NULL,
  `ville` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `bienlouable`
--

DROP TABLE IF EXISTS `bienlouable`;
CREATE TABLE IF NOT EXISTS `bienlouable` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_fiscal` char(12) NOT NULL,
  `complement_adresse` varchar(100) NOT NULL,
  `type_logement` tinyint(1) NOT NULL,
  `Nombre_pieces` tinyint DEFAULT NULL,
  `Surface` double DEFAULT NULL,
  `garage_assoc` int DEFAULT NULL,
  `idBat` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `garage_assoc` (`garage_assoc`),
  KEY `FK_bienlouable` (`idBat`)
) ENGINE=InnoDB AUTO_INCREMENT=9612 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déclencheurs `bienlouable`
--
DROP TRIGGER IF EXISTS `before_insert_bien_louable`;
DELIMITER $$
CREATE TRIGGER `before_insert_bien_louable` BEFORE INSERT ON `bienlouable` FOR EACH ROW BEGIN
    DECLARE msg VARCHAR(255);
    IF EXISTS (SELECT 1 FROM bienlouable WHERE numero_fiscal = NEW.numero_fiscal) THEN
        SET msg = 'Le numero_fiscal existe déjà dans la table bienlouable.';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_insert_check_garage_deja_existant`;
DELIMITER $$
CREATE TRIGGER `before_insert_check_garage_deja_existant` BEFORE INSERT ON `bienlouable` FOR EACH ROW BEGIN
    IF NEW.type_logement = 2 THEN
                IF EXISTS (
            SELECT 1 
            FROM bienlouable 
            WHERE numero_fiscal = NEW.numero_fiscal AND type_logement = 2
        ) THEN
                        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Erreur : Un garage associé à un logement avec ce numero_fiscal existe déjà.';
        END IF;
    END IF;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_insert_check_logement_deja_existant`;
DELIMITER $$
CREATE TRIGGER `before_insert_check_logement_deja_existant` BEFORE INSERT ON `bienlouable` FOR EACH ROW BEGIN
    IF NEW.type_logement = 0 THEN
                IF EXISTS (
            SELECT 1 
            FROM bienlouable 
            WHERE numero_fiscal = NEW.numero_fiscal AND type_logement = 0
        ) THEN
                        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Erreur : Un logement avec ce numero_fiscal existe déjà.';
        END IF;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `charges`
--

DROP TABLE IF EXISTS `charges`;
CREATE TABLE IF NOT EXISTS `charges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `id_bail` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_bail` (`id_bail`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `devis`
--

DROP TABLE IF EXISTS `devis`;
CREATE TABLE IF NOT EXISTS `devis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num_devis` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `num_facture` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `montant_devis` double DEFAULT NULL,
  `montant_travaux` double DEFAULT NULL,
  `nature` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  `adresse_entreprise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `nom_entreprise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_facture` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=721 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `diagnostiques`
--

DROP TABLE IF EXISTS `diagnostiques`;
CREATE TABLE IF NOT EXISTS `diagnostiques` (
  `id` int NOT NULL,
  `pdf_diag` longblob,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `date_expiration` date DEFAULT NULL,
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `date` date DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `id_charge` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_charge` (`id_charge`)
) ENGINE=InnoDB AUTO_INCREMENT=430 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `locataire`
--

DROP TABLE IF EXISTS `locataire`;
CREATE TABLE IF NOT EXISTS `locataire` (
  `nom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `prenom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `téléphone` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `mail` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `date_arrive` date NOT NULL,
  `id_loc` int NOT NULL AUTO_INCREMENT,
  `loc_actuel` tinyint(1) NOT NULL,
  `genre` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `lieu_naissance` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `date_naissance` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id_loc`)
) ENGINE=InnoDB AUTO_INCREMENT=816 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `louer`
--

DROP TABLE IF EXISTS `louer`;
CREATE TABLE IF NOT EXISTS `louer` (
  `id_bail` int NOT NULL,
  `id_locataire` int NOT NULL,
  `quotite` int NOT NULL,
  `dernier_paiement` date DEFAULT NULL,
  PRIMARY KEY (`id_bail`,`id_locataire`),
  KEY `FK_louer_idbail` (`id_bail`),
  KEY `FK_id_loc` (`id_locataire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Structure de la table `regimemicrofoncier`
--

DROP TABLE IF EXISTS `regimemicrofoncier`;
CREATE TABLE IF NOT EXISTS `regimemicrofoncier` (
  `id` int NOT NULL,
  `valeur` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Déchargement des données de la table `regimemicrofoncier`
--

INSERT INTO `regimemicrofoncier` (`id`, `valeur`) VALUES
(1, 15.5);

-- --------------------------------------------------------

--
-- Structure de la table `travauxassocie`
--

DROP TABLE IF EXISTS `travauxassocie`;
CREATE TABLE IF NOT EXISTS `travauxassocie` (
  `id_devis` int NOT NULL,
  `id_bien` int NOT NULL,
  `type_bien` int NOT NULL,
  PRIMARY KEY (`id_devis`,`id_bien`,`type_bien`),
  KEY `FK_travauxassocie_id_bien` (`id_bien`),
  KEY `fk_id_devis` (`id_devis`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bail`
--
ALTER TABLE `bail`
  ADD CONSTRAINT `FK_bail_bien_louable` FOREIGN KEY (`id_bien_louable`) REFERENCES `bienlouable` (`id`);

--
-- Contraintes pour la table `bienlouable`
--
ALTER TABLE `bienlouable`
  ADD CONSTRAINT `bienlouable_ibfk_1` FOREIGN KEY (`garage_assoc`) REFERENCES `bienlouable` (`id`),
  ADD CONSTRAINT `FK_bienlouable` FOREIGN KEY (`idBat`) REFERENCES `batiment` (`id`);

--
-- Contraintes pour la table `charges`
--
ALTER TABLE `charges`
  ADD CONSTRAINT `fk_id_bail` FOREIGN KEY (`id_bail`) REFERENCES `bail` (`id`);

--
-- Contraintes pour la table `diagnostiques`
--
ALTER TABLE `diagnostiques`
  ADD CONSTRAINT `fk_diagnostiques` FOREIGN KEY (`id`) REFERENCES `bienlouable` (`id`);

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `fk_id_charge` FOREIGN KEY (`id_charge`) REFERENCES `charges` (`id`);

--
-- Contraintes pour la table `louer`
--
ALTER TABLE `louer`
  ADD CONSTRAINT `FK_id_loc` FOREIGN KEY (`id_locataire`) REFERENCES `locataire` (`id_loc`),
  ADD CONSTRAINT `FK_louer_idbail` FOREIGN KEY (`id_bail`) REFERENCES `bail` (`id`);

--
-- Contraintes pour la table `travauxassocie`
--
ALTER TABLE `travauxassocie`
  ADD CONSTRAINT `fk_id_devis` FOREIGN KEY (`id_devis`) REFERENCES `devis` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
