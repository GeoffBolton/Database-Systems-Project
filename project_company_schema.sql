-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `simple_company` ;

-- -----------------------------------------------------
-- Table `simple_company`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Customer` (
  `id` BIGINT(8) NOT NULL AUTO_INCREMENT COMMENT '',
  `firstName` VARCHAR(45) NOT NULL COMMENT '',
  `lastName` VARCHAR(45) NOT NULL COMMENT '',
  `gender` CHAR(1) NULL DEFAULT NULL COMMENT '',
  `dob` DATE NOT NULL COMMENT '',
  `email` VARCHAR(100) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`CreditCard` (
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `ccNumber` VARCHAR(45) NOT NULL COMMENT '',
  `expDate` VARCHAR(45) NOT NULL COMMENT '',
  `securityCode` VARCHAR(45) NOT NULL COMMENT '',
  `customerID` BIGINT(8) NOT NULL COMMENT '',
  INDEX `fk_CreditCard_Customer_idx` (`customerID` ASC)  COMMENT '',
  PRIMARY KEY (`customerID`)  COMMENT '',
  CONSTRAINT `fk_CreditCard_Customer`
    FOREIGN KEY (`customerID`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Address` (
  `address1` VARCHAR(100) NOT NULL COMMENT '',
  `address2` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `city` VARCHAR(45) NOT NULL COMMENT '',
  `state` VARCHAR(20) NOT NULL COMMENT '',
  `zipcode` VARCHAR(20) NOT NULL COMMENT '',
  `customerID` BIGINT(8) NOT NULL COMMENT '',
  PRIMARY KEY (`customerID`)  COMMENT '',
  CONSTRAINT `fk_Address_Customer1`
    FOREIGN KEY (`customerID`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Product` (
  `id` BIGINT(8) NOT NULL COMMENT '',
  `prodName` VARCHAR(45) NOT NULL COMMENT '',
  `prodDescription` VARCHAR(100) NOT NULL COMMENT '',
  `prodCategory` INT NOT NULL COMMENT '',
  `prodUPC` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Purchase` (
  `id` BIGINT(8) NOT NULL COMMENT '',
  `productID` BIGINT(8) NOT NULL COMMENT '',
  `customerID` BIGINT(8) NOT NULL COMMENT '',
  `purchaseDate` DATE NOT NULL COMMENT '',
  `purchaseAmount` DECIMAL(10,2) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_Purchase_Customer1_idx` (`customerID` ASC)  COMMENT '',
  INDEX `fk_Purchase_Product1_idx` (`productID` ASC)  COMMENT '',
  CONSTRAINT `fk_Purchase_Customer1`
    FOREIGN KEY (`customerID`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Product1`
    FOREIGN KEY (`productID`)
    REFERENCES `simple_company`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

