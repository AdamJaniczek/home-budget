CREATE SCHEMA `home_budget` ;

CREATE TABLE `home_budget`.`transaction` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `type` ENUM('INCOME', 'EXPENSE') NOT NULL,
    `description` VARCHAR(100) NULL,
    `amount` DOUBLE NULL,
    `date` DATE NULL DEFAULT (CURDATE()),
    PRIMARY KEY (`id`));

INSERT INTO `home_budget`.`transaction` (`type`, `description`, `amount`, `date`)
    VALUES ('INCOME', 'Wypłata', '6000', '2025-05-01'),
           ('EXPENSE', 'MIeszkanie', '1500', '2024-05-10'),
           ('EXPENSE', 'Zakupy', '150.50', '2024-05-11');