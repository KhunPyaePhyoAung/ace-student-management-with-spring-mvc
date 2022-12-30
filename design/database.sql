DROP DATABASE IF EXISTS `ace_student_management`;
CREATE DATABASE `ace_student_management` CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci';

USE `ace_student_management`;

CREATE TABLE `user` (
    `id_code` INT UNIQUE NOT NULL AUTO_INCREMENT,
    `id_prefix` VARCHAR(10) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('ADMIN', 'USER') NOT NULL,
    `approved` BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY(`id_code`, `id_prefix`)
);

CREATE TABLE `course` (
    `id_prefix` VARCHAR(10) NOT NULL,
    `id_code` INT,
    `name` VARCHAR(255) UNIQUE NOT NULL,
    `short_name` VARCHAR(10) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE `student` (
    `id_code` INT UNIQUE NOT NULL AUTO_INCREMENT,
    `id_prefix` VARCHAR(10) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `date_of_birth` DATE NOT NULL,
    `gender` ENUM('MALE', 'FEMALE') NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    `education` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id_code`, `id_prefix`)
);

CREATE TABLE `student_course` (
    `student_id` INT NOT NULL,
    `course_id` INT NOT NULL,
    UNIQUE (`student_id`, `course_id`),
    FOREIGN KEY(`student_id`) REFERENCES `student`(`id_code`) ON DELETE CASCADE,
    FOREIGN KEY(`course_id`) REFERENCES `course`(`id_code`),
    PRIMARY KEY(`student_id`, `course_id`)
);

CREATE VIEW `student_with_course_view` AS
SELECT `s`.`id_prefix` AS `student_id_prefix`, `s`.`id_code` AS `student_id_code`, CONCAT(`s`.`id_prefix`, `s`.`id_code`) AS `student_id`,`s`.`name` AS `student_name`, `s`.`date_of_birth` AS `student_date_of_birth`, `s`.`gender` AS `student_gender`,`s`.`phone` AS `student_phone`, `s`.`education` AS `student_education`, `c`.`id_prefix` AS `course_id_prefix`, `c`.`id_code` AS `course_id_code`, CONCAT(`c`.`id_prefix`, `c`.`id_code`) AS `course_id`, `c`.`name` AS `course_name`, `c`.`short_name` AS `course_short_name` FROM `student` `s` INNER JOIN `student_course` `sc` ON `s`.`id_code` = `sc`.`student_id` INNER JOIN `course` `c` ON `sc`.`course_id` = `c`.`id_code`;

INSERT INTO `user` (`id_prefix`, `name`, `email`, `password`, `approved`) VALUES ('USR', 'Admin', 'admin@gmail.com', 'admin', true);

