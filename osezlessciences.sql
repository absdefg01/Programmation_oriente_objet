--PARTIE DATABASE

-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema osezlessciences
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `Mention`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mention` (
  `idMention` INT NOT NULL AUTO_INCREMENT,
  `nomMention` VARCHAR(45) NULL,
  PRIMARY KEY (`idMention`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Matiere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Matiere` (
  `idMatiere` INT NOT NULL AUTO_INCREMENT,
  `nomMatiere` VARCHAR(45) NULL,
  `idMention` INT NOT NULL,
  PRIMARY KEY (`idMatiere`),
  INDEX `fk_Matiere_Mention1_idx` (`idMention` ASC),
  CONSTRAINT `fk_Matiere_Mention1`
    FOREIGN KEY (`idMention`)
    REFERENCES `Mention` (`idMention`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Enseignant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Enseignant` (
  `idEnseignant` INT NOT NULL AUTO_INCREMENT,
  `nomEnseignant` VARCHAR(45) NULL,
  `prenomEnseignant` VARCHAR(45) NULL,
  PRIMARY KEY (`idEnseignant`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Creneau`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Creneau` (
  `idCreneau` INT NOT NULL AUTO_INCREMENT,
  `dateCreneau` DATE NULL,
  `heureDebut` TIME(1) NULL,
  `heureFin` TIME(1) NULL,
  `nbEleveMax` INT NULL,
  `idMatiere` INT NOT NULL,
  `idEnseignant` INT NOT NULL,
  PRIMARY KEY (`idCreneau`),
  INDEX `fk_Creneau_Matiere_idx` (`idMatiere` ASC),
  INDEX `fk_Creneau_Enseignant1_idx` (`idEnseignant` ASC),
  CONSTRAINT `fk_Creneau_Matiere`
    FOREIGN KEY (`idMatiere`)
    REFERENCES `Matiere` (`idMatiere`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Creneau_Enseignant1`
    FOREIGN KEY (`idEnseignant`)
    REFERENCES `Enseignant` (`idEnseignant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eleve`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eleve` (
  `idEleve` INT NOT NULL AUTO_INCREMENT,
  `nomEleve` VARCHAR(45) NULL,
  `prenomEleve` VARCHAR(45) NULL,
  `mailEleve` VARCHAR(45) NULL,
  `passwordEleve` VARCHAR(45) NULL,
  PRIMARY KEY (`idEleve`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Inscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Inscription` (
  `idCreneau` INT NOT NULL,
  `idEleve` INT NOT NULL,
  `validite` INT NULL,
  PRIMARY KEY (`idCreneau`, `idEleve`),
  INDEX `fk_Creneau_has_Eleve_Eleve1_idx` (`idEleve` ASC),
  INDEX `fk_Creneau_has_Eleve_Creneau1_idx` (`idCreneau` ASC),
  CONSTRAINT `fk_Creneau_has_Eleve_Creneau1`
    FOREIGN KEY (`idCreneau`)
    REFERENCES `Creneau` (`idCreneau`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Creneau_has_Eleve_Eleve1`
    FOREIGN KEY (`idEleve`)
    REFERENCES `Eleve` (`idEleve`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Gestionnaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Gestionnaire` (
  `idGestionnaire` INT NOT NULL AUTO_INCREMENT,
  `nomCompte` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`idGestionnaire`))
ENGINE = InnoDB;




--PARTIE DES DONNÉE PRÉ-INSERER DANS BDD
--les donnée pré-entrées dans BDD
--gestionnaire
insert into gestionnaire values(1,'aa','aa');
--mention
insert into mention values(5,'Science de la vie');
insert into mention values(4,'Physique - Chimie');
insert into mention values(3,'ElectroniqueEnergieélectriqueetAutomatique');
insert into mention values(2,'Mathématique');
insert into mention values(1,'Informatique');

