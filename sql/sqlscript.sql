-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema up2date
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `up2date` ;

-- -----------------------------------------------------
-- Schema up2date
-- -----------------------------------------------------
CREATE SCHEMA `up2date` DEFAULT CHARACTER SET utf8 ;
USE `up2date` ;

-- -----------------------------------------------------
-- Table `up2date`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `up2date`.`employees` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(125) NOT NULL,
  `given_name` VARCHAR(125) NOT NULL,
  `family_name` VARCHAR(125) NOT NULL,
  PRIMARY KEY (`employee_id`)
);

-- -----------------------------------------------------
-- Table `up2date`.`trainings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `up2date`.`trainings` (
  `training_id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL,
  `category` VARCHAR(125) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `total_hours` int NOT NULL,
  PRIMARY KEY (`training_id`),
  FOREIGN KEY (`training_id`) REFERENCES employees(`employee_id`)
);

