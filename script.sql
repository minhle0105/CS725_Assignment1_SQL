
-- create database

CREATE DATABASE CS725_Assignment1_Fake;


-- create tables

CREATE TABLE `CS725_Assignment1_Fake`.`Users` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`));


CREATE TABLE `CS725_Assignment1_Fake`.`Products` (
  `idProducts` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `price` DECIMAL NOT NULL,
  `number_of_units` INT NOT NULL,
  PRIMARY KEY (`idProducts`));

CREATE TABLE `CS725_Assignment1_Fake`.`Reviews` (
  `idReview` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `product_id` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `rating` INT NOT NULL,
  PRIMARY KEY (`idReview`));

CREATE TABLE `CS725_Assignment1_Fake`.`Orders` (
  `idOrders` VARCHAR(225) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`idOrders`));

CREATE TABLE `CS725_Assignment1_Fake`.`Order_Details` (
  `idOrder_Details` INT NOT NULL AUTO_INCREMENT,
  `order_id` VARCHAR(225) NOT NULL,
  `product_id` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`idOrder_Details`));


-- create foreign keys

ALTER TABLE `CS725_Assignment1_Fake`.`Reviews` 
ADD INDEX `product_id_idx` (`product_id` ASC) VISIBLE,
ADD INDEX `username_idx` (`username` ASC) VISIBLE;
;
ALTER TABLE `CS725_Assignment1_Fake`.`Reviews` 
ADD CONSTRAINT `product_id`
  FOREIGN KEY (`product_id`)
  REFERENCES `CS725_Assignment1_Fake`.`Products` (`idProducts`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `username`
  FOREIGN KEY (`username`)
  REFERENCES `CS725_Assignment1_Fake`.`Users` (`username`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `CS725_Assignment1_Fake`.`Order_Details` 
ADD CONSTRAINT `order_id`
  FOREIGN KEY (`order_id`)
  REFERENCES `CS725_Assignment1_Fake`.`Orders` (`idOrders`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `CS725_Assignment1_Fake`.`Order_Details` 
ADD INDEX `productId_idx` (`product_id` ASC) VISIBLE;
;
ALTER TABLE `CS725_Assignment1_Fake`.`Order_Details` 
ADD CONSTRAINT `product_id`
  FOREIGN KEY (`product_id`)
  REFERENCES `CS725_Assignment1_Fake`.`Products` (`idProducts`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;






