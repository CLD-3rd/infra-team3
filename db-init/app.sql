-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `globetrek` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `globetrek` ;

-- -----------------------------------------------------
-- Table `project`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`countries` (
  `country_id` INT NOT NULL AUTO_INCREMENT,
  `country_name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `lat` DECIMAL(9,6) NULL DEFAULT NULL,
  `lon` DECIMAL(9,6) NULL DEFAULT NULL,
  -- `flag_url` TEXT NULL DEFAULT NULL,  -- 🔹 추가된 부분
  PRIMARY KEY (`country_id`),
  UNIQUE INDEX `country_name` (`country_name` ASC)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `project`.`travel_logs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`travel_logs` (
  `log_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `pic_url` TEXT NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `like_count` INT NULL DEFAULT '0',
  `comment_count` INT NULL DEFAULT '0',
  `country_id` INT NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`),
  INDEX `country_id` (`country_id` ASC),
  CONSTRAINT `travel_logs_ibfk_1`
    FOREIGN KEY (`country_id`)
    REFERENCES `globetrek`.`countries` (`country_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `project`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `user_nickname` VARCHAR(50) NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name` (`user_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `project`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `log_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  INDEX `log_id` (`log_id` ASC),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `comments_ibfk_1`
    FOREIGN KEY (`log_id`)
    REFERENCES `globetrek`.`travel_logs` (`log_id`)
    ON DELETE CASCADE,
  CONSTRAINT `comments_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `globetrek`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `project`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`likes` (
  `user_id` INT NOT NULL,
  `log_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `log_id`),
  INDEX `log_id` (`log_id` ASC),
  CONSTRAINT `likes_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `globetrek`.`users` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `likes_ibfk_2`
    FOREIGN KEY (`log_id`)
    REFERENCES `globetrek`.`travel_logs` (`log_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `project`.`wishlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `globetrek`.`wishlists` (
  `wishlist_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `country_id` INT NOT NULL,
  `travel_date` DATE NULL DEFAULT NULL,
  `end_date` DATE NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wishlist_id`),
  INDEX `user_id` (`user_id` ASC),
  INDEX `country_id` (`country_id` ASC),
  CONSTRAINT `wishlists_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `globetrek`.`users` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `wishlists_ibfk_2`
    FOREIGN KEY (`country_id`)
    REFERENCES `globetrek`.`countries` (`country_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('1', 'Afghanistan', 'Known for its rugged mountains and ancient culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('2', 'Albania', 'A hidden gem with beaches and Ottoman history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('3', 'Algeria', 'The largest African country, rich in Sahara landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('4', 'American Samoa', 'A tropical U.S. territory with Polynesian culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('5', 'Angola', 'Known for oil, music, and post-colonial heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('6', 'Anguilla', 'A British Caribbean island with stunning beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('7', 'Antigua and Barbuda', 'Twin islands famous for 365 beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('8', 'Argentina', 'Home of tango, steak, and Patagonia.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('9', 'Armenia', 'The first Christian nation, full of monasteries.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('10', 'Aruba', 'Dutch Caribbean island with clear waters.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('11', 'Australia', 'Famous for kangaroos, beaches, and the Outback.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('13', 'Austria', 'Mozart's homeland, known for classical music and Alps.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('14', 'Azerbaijan', 'The land of fire and Caspian energy.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('15', 'Bahamas', 'Island paradise with turquoise waters.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('16', 'Bahrain', 'A small Gulf kingdom with ancient trade routes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('17', 'Bangladesh', 'Densely populated, rich in rivers and textiles.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('18', 'Barbados', 'Caribbean island with British heritage and rum.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('19', 'Belarus', 'Known for Soviet-era architecture and dense forests.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('20', 'Belgium', 'Famous for chocolate, beer, and waffles.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('21', 'Belize', 'English-speaking Central American country with reefs.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('22', 'Benin', 'The cradle of Vodun and ancient Dahomey kingdom.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('23', 'Bermuda', 'British island known for pink sand beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('24', 'Bhutan', 'Himalayan kingdom known for Gross National Happiness and monasteries.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('25', 'Bolivia', 'Landlocked country with highlands and salt flats.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('26', 'Bosnia and Herzegovina', 'Known for its Ottoman bridges and post-war history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('27', 'Botswana', 'Rich in wildlife and famous for the Okavango Delta.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('28', 'Brazil', 'Home to the Amazon rainforest and vibrant Carnival.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('29', 'British Virgin Islands', 'Caribbean territory known for sailing and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('30', 'Brunei Darussalam', 'Oil-rich sultanate with Islamic traditions.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('31', 'Bulgaria', 'Balkan country with mountains and ancient ruins.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('32', 'Burkina Faso', 'West African nation known for music and art.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('33', 'Burundi', 'Small landlocked country with rolling hills.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('34', 'Cambodia', 'Famous for Angkor Wat and Khmer heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('35', 'Cameroon', 'Often called \"Africa in miniature\" for its diversity.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('36', 'Canada', 'A vast country known for nature, hockey, and maple syrup.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('37', 'Canary Islands (Spain)', 'Volcanic islands with year-round sunshine.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('38', 'Cape Verde', 'Island nation with Creole culture and morna music.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('39', 'Cayman Islands', 'Island nation with Creole culture and morna music.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('40', 'Central African Republic', 'Rich in minerals but affected by political unrest.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('41', 'Chad', 'Desert country with Lake Chad and Sahel culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('42', 'Chile', 'Long coastal nation with deserts and glaciers.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('43', 'China', 'World's most populous nation with ancient civilization.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('44', 'Colombia', 'Known for coffee, music, and vibrant culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('45', 'Comoros', 'Island group in the Indian Ocean with Arabic influence.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('46', 'Costa Rica', 'Eco-tourism haven with rainforests and beaches.');

INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('47', 'Côte d''Ivoire', 'West African country known for cocoa and cultural diversity.');

INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('48', 'Croatia', 'Adriatic gem with medieval towns and clear seas.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('49', 'Cuba', 'Island nation famous for cigars, music, and vintage cars.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('50', 'Curaçao', 'Dutch Caribbean island with colorful architecture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('51', 'Cyprus', 'Island at a crossroads of Europe and the Middle East.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('52', 'Czech Republic', 'Known for Prague, castles, and beer.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('53', 'Dem. Rep. Korea', 'Isolated nation known for strict regime and militarization.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('54', 'Democratic Republic of the Congo', 'Vast central African country rich in minerals and rainforest.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('55', 'Denmark', 'Scandinavian country known for design and high quality of life.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('56', 'Dijbouti', 'Strategic port nation in the Horn of Africa.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('57', 'Dominica', 'Lush Caribbean island with volcanic peaks and hot springs.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('58', 'Dominican Republic', 'Shares Hispaniola, famous for beaches and bachata music.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('59', 'Ecuador', 'Home to the Galápagos Islands and the equator.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('60', 'Egypt', 'Land of ancient pyramids and the Nile River.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('61', 'El Salvador', 'Smallest Central American country with volcanic landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('62', 'Equatorial Guinea', 'Oil-rich country with Spanish as official language.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('63', 'Eritrea', 'Horn of Africa nation with Red Sea coastline and history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('64', 'Estonia', 'Baltic country known for digital innovation and medieval towns.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('65', 'Ethiopia', 'Ancient land with unique calendar, script, and coffee culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('66', 'Faeroe Islands', 'Remote Danish islands with dramatic cliffs and puffins.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('67', 'Falkland Islands', 'British territory in South Atlantic, known for wildlife.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('68', 'Federated States of Micronesia', 'Pacific island nation spread across many small islands.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('69', 'Fiji', 'South Pacific paradise with coral reefs and friendly locals.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('70', 'Finland', 'Nordic country famous for saunas and education.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('71', 'France', 'Known for its rich history, cuisine, and the Eiffel Tower.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('72', 'French Guiana', 'South American territory with rainforest and space center.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('73', 'French Polynesia', 'Island group in the Pacific with turquoise lagoons.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('74', 'Gabon', 'Oil-rich country with dense equatorial rainforest.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('75', 'Georgia', 'Caucasus nation with wine tradition and mountain vistas.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('76', 'Germany', 'Industrial powerhouse known for cars, beer, and castles.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('77', 'Ghana', 'West African country known for gold and vibrant culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('78', 'Greece', 'Birthplace of democracy and Western philosophy.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('79', 'Greenland', 'World's largest island, mostly covered in ice.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('80', 'Grenada', '"Spice Island" of the Caribbean with nutmeg and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('81', 'Guadeloupe', 'French Caribbean island with volcanoes and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('82', 'Guam', 'U.S. territory in the Pacific with military bases.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('83', 'Guatemala', 'Home of Mayan ruins and volcanic highlands.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('84', 'Guinea', 'West African nation rich in minerals and music.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('85', 'Guinea-Bissau', 'Small coastal country with diverse ethnic groups.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('86', 'Guyana', 'South American nation with Caribbean culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('87', 'Haiti', 'Shares island with Dominican Republic; rich in resilience and art.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('88', 'Honduars', 'Central American country with Mayan ruins and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('89', 'Hungary', 'Landlocked country known for Budapest and thermal baths.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('90', 'Iceland', 'Land of volcanoes, glaciers, and northern lights.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('91', 'India', 'Vast and diverse nation known for spices, Bollywood, and the Taj Mahal.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('92', 'Indonesia', 'Island nation with volcanoes, temples, and rich biodiversity.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('93', 'Iran', 'Ancient Persia with rich culture, poetry, and architecture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('94', 'Iraq', 'Home to Mesopotamia, one of the world's oldest civilizations.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('95', 'Ireland', 'Emerald Isle with folklore, pubs, and green landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('96', 'Israel', 'Holy land with sacred sites and modern innovation.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('97', 'Italy', 'Land of pasta, Roman ruins, and Renaissance art.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('98', 'Jamaica', 'Birthplace of reggae and island with lush mountains and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('99', 'Japan', 'Blend of tradition and technology, known for sushi and samurai.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('100', 'Jordan', 'Home to Petra and desert landscapes like Wadi Rum.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('101', 'Kazakhstan', 'Vast Central Asian country with steppes and space history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('102', 'Kenya', 'Known for safaris, wildlife, and the Great Rift Valley.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('103', 'Kosovo', 'Young Balkan republic with a complex modern history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('104', 'Kuwait', 'Gulf country rich in oil and modern skyscrapers.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('105', 'Kyrgyzstan', 'Mountainous land with nomadic culture and alpine lakes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('106', 'Lao PDR', 'Laid-back Southeast Asian country with the Mekong and temples.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('107', 'Latvia', 'Baltic nation with forests, medieval towns, and song festivals.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('108', 'Lebanon', 'Historic country with cedar trees, cuisine, and Roman ruins.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('109', 'Lesotho', 'Mountain kingdom completely surrounded by South Africa.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('110', 'Liberia', 'Founded by freed American slaves; rich in African culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('111', 'Libya', 'North African country with desert and ancient Roman cities.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('112', 'Lithuania', 'Baltic state known for baroque architecture and medieval heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('113', 'Luxembourg', 'Small but wealthy country at the heart of Europe.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('114', 'Macedonia', 'Balkan nation with ancient ruins and mountain landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('115', 'Madagascar', 'Island nation known for unique wildlife and baobab trees.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('116', 'Malawi', 'The "Warm Heart of Africa," with lakes and friendly people.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('117', 'Malaysia', 'Multicultural country with rainforests and modern cities.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('118', 'Maldives', 'Tropical paradise of atolls and luxury resorts.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('119', 'Mali', 'West African country rich in music and desert history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('120', 'Malta', 'Island nation with historic fortresses and Mediterranean charm.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('121', 'Marshall Islands', 'Pacific nation with coral atolls and WWII history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('122', 'Martinique', 'French Caribbean island with volcanoes and Creole culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('123', 'Mauritania', 'Desert country where nomadic life still thrives.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('124', 'Mauritius', 'Indian Ocean island known for beaches and multiculturalism.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('125', 'Mayotte', 'French overseas territory in the Indian Ocean.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('126', 'Mexico', 'Land of tacos, Aztecs, and vibrant traditions.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('127', 'Moldova', 'Eastern European country known for wine and monasteries.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('128', 'Mongolia', 'Land of vast steppes, yurts, and Genghis Khan.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('129', 'Montenegro', 'Adriatic country with mountains and coastal towns.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('130', 'Montserrat', 'Volcanic Caribbean island and British territory.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('131', 'Morocco', 'North African country with souks, deserts, and mountains.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('132', 'Mozambique', 'Southeastern African nation with long coastline and culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('133', 'Myanmar', 'Southeast Asian country with golden pagodas and complex history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('134', 'Namibia', 'Desert landscapes and safari adventures in southern Africa.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('135', 'Nauru', 'World's third smallest country, once rich in phosphate.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('136', 'Nepal', 'Himalayan country home to Mount Everest and spiritual heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('137', 'Netherlands', 'Low-lying country known for tulips, canals, and windmills.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('138', 'New Caledonia', 'French Pacific territory with reefs and Kanak culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('139', 'New Zealand', 'Island nation with stunning nature and Māori culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('140', 'Nicaragua', 'Central American country with lakes and volcanoes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('141', 'Nigar', 'Saharan country with ancient trade history and deserts.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('142', 'Nigeria', 'Africa's most populous nation with Nollywood and diverse cultures.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('143', 'Nother Mariana Islands', 'U.S. territory with Pacific beaches and WWII history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('144', 'Norway', 'Scandinavian country with fjords and high living standards.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('145', 'Oman', 'Arabian country with deserts, forts, and coastal beauty.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('146', 'Pakistan', 'South Asian nation with mountain peaks and Mughal heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('147', 'Palu', 'Micronesian island nation known for diving and marine life.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('148', 'Palestine', 'Territory with deep historical and religious significance.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('149', 'Panama', 'Bridge between Americas, known for the Panama Canal.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('150', 'Papua New Guinea', 'Culturally diverse nation with hundreds of languages.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('151', 'Paraguay', 'Landlocked South American country with rivers and tradition.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('152', 'Peru', 'Home of Machu Picchu and ancient Incan civilization.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('153', 'Philippines', 'Island nation with vibrant culture and over 7,000 islands.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('154', 'Poland', 'Central European nation with a resilient history and rich heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('155', 'Portugal', 'Seafaring nation famous for Lisbon, port wine, and fado.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('156', 'Puerto Rico', 'U.S. territory with Caribbean flair and Spanish influence.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('157', 'Qatar', 'Rich Gulf state known for gas reserves and modern skylines.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('158', 'Repulic of Congo', 'Central African country with rainforest and river culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('159', 'Republic of Korea', 'A high-tech nation famous for K-pop, kimchi, and culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('160', 'Reunion', 'French island in the Indian Ocean with volcanic landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('161', 'Romania', 'Eastern European country with castles and Carpathian mountains.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('162', 'Russian Federation', 'World's largest country spanning Europe and Asia.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('163', 'Rwanda', 'Known as the "Land of a Thousand Hills" and post-conflict recovery.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('164', 'Saba (Netherlands)', 'Small Caribbean island with pristine diving spots.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('165', 'Saint-Barthélemy', 'French Caribbean island known for luxury and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('166', 'Saint Kitts and Nevis', 'Twin-island nation with colonial history and lush mountains.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('167', 'Saint Lucia', 'Caribbean island with volcanic peaks and vibrant culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('168', 'Saint-Martin', 'Caribbean island split between French and Dutch rule.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('169', 'Saint Vincent and the Grenadines', 'Island chain in the Caribbean with sailing and natural beauty.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('170', 'Samoa', 'Polynesian country with strong traditions and scenic islands.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('171', 'São Tomé and Principe', 'Small island nation off West Africa, rich in biodiversity.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('172', 'Saudi Arabia', 'Oil-rich kingdom and birthplace of Islam.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('173', 'Senegal', 'West African country known for music, culture, and democracy.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('174', 'Serbia', 'Balkan country with medieval heritage and modern revival.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('175', 'Seychelles', 'Indian Ocean paradise with white beaches and coral reefs.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('176', 'Sierra Leone', 'West African country rebuilding after civil war, rich in diamonds.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('177', 'Sint Maarten', 'Dutch Caribbean territory sharing island with Saint-Martin.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('178', 'Slovakia', 'Central European country with castles, mountains, and folklore.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('179', 'Slovenia', 'Alpine country with lakes, caves, and medieval towns.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('180', 'Solomon Islands', 'Pacific nation with WWII history and coral reefs.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('181', 'Somalia', 'Horn of Africa country with a long coastline and complex history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('182', 'South Africa', 'Diverse nation with wildlife, Cape Town, and apartheid legacy.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('183', 'South Sudan', 'World's newest country, gaining independence in 2011.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('184', 'Spain', 'Known for flamenco, tapas, and historic cities.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('185', 'Sri Lanka', 'Island nation with ancient temples, tea, and beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('186', 'St. Eustatis (Netherlands)', 'Tiny Dutch Caribbean island with volcanic landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('187', 'Sudan', 'Northeast African nation with pyramids and the Nile.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('188', 'Suriname', 'South American country with Dutch influence and rainforests.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('189', 'Swaziland', 'Small kingdom in southern Africa with rich traditions.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('190', 'Sweden', 'Scandinavian country known for innovation, forests, and design.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('191', 'Switzerland', 'Famous for neutrality, Alps, chocolate, and banking.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('192', 'Syria', 'Ancient land deeply affected by modern conflict.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('193', 'Taiwan', 'Island known for tech industry and night markets.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('194', 'Tajikistan', 'Mountainous Central Asian country with Silk Road heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('195', 'Tanzania', 'Home to Serengeti, Kilimanjaro, and Zanzibar.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('196', 'Thailand', 'Southeast Asian country known for temples and street food.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('197', 'The Gambia', 'Small West African nation along the Gambia River.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('198', 'Timor-Leste', 'Young Southeast Asian country with Portuguese heritage.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('199', 'Togo', 'Narrow West African country with varied landscapes.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('200', 'Tonga', 'Polynesian kingdom with strong cultural identity.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('201', 'Trinidad and Tobago', 'Caribbean nation known for Carnival and calypso music.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('202', 'Tunisia', 'North African country with ancient ruins and Mediterranean coast.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('203', 'Turkey', 'Crossroads of Europe and Asia, rich in history and cuisine.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('204', 'Turkmenistan', 'Central Asian country with deserts and marble cities.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('205', 'Turks and Caicos Islands', 'British territory with luxury resorts and coral reefs.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('206', 'Tuvalu', 'Small Pacific island nation threatened by rising seas.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('207', 'Uganda', 'East African country known for gorillas and Lake Victoria.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('209', 'United Arab Emirates', 'Oil-rich federation with futuristic cities like Dubai and Abu Dhabi.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('210', 'United Kingdom', 'Island nation known for monarchy, tea, and global history.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('211', 'United States', 'A diverse country with 50 states and global influence.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('212', 'United States Virgin Island', 'U.S. territory in the Caribbean with scenic beaches.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('213', 'Uruguay', 'Progressive South American country with beaches and mate culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('214', 'Uzbekistan', 'Silk Road nation with Islamic architecture and desert cities.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('215', 'Vanuatu', 'Pacific island nation with active volcanoes and traditions.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('216', 'Venezuela', 'Oil-rich country with political turmoil and natural beauty.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('217', 'Vietnam', 'Southeast Asian nation with rich history and street food culture.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('218', 'Western Sahara', 'Disputed territory with desert landscape in North Africa.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('219', 'Yemen', 'Arabian country with ancient cities and ongoing conflict.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('220', 'Zambia', 'Home to Victoria Falls and vast national parks.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('221', 'Zimbabwe', 'Southern African country known for ruins and wildlife.');
INSERT INTO `globetrek`.`countries` (`country_id`, `country_name`, `description`) VALUES ('208', 'Ukraine', 'Eastern European nation with fertile land and strong identity.');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('159', '1', 'bukchon');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('159', '2', 'Gwangandaegyo');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('159', '3', 'HanRiver');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('159', '4', 'palace');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('210', '5', 'londontower');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('210', '6', 'towerbridge');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('210', '7', 'chester');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('210', '8', 'blackpooltower');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('71', '9', 'eiffeltower');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('71', '10', 'louvremuseum');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('71', '11', 'OrsayMuseum');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('71', '12', 'ArcdeTriomphe');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('211', '13', '24hrStreet');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('211', '14', 'goldgatebridge');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('211', '15', 'grandcanyon');
INSERT INTO `globetrek`.`travel_logs` (`country_id`, `log_id`, `title`) VALUES ('211', '16', 'newyork');

UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '1', `like_count` = '4', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '1');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '2', `like_count` = '3', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '2');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '3', `like_count` = '2', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '3');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '4', `like_count` = '1', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '4');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '1', `like_count` = '4', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '5');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '2', `like_count` = '3', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '6');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '3', `like_count` = '2', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '7');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '4', `like_count` = '1', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '8');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '1', `like_count` = '4', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '9');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '2', `like_count` = '3', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '10');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '3', `like_count` = '2', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '11');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '4', `like_count` = '1', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '12');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '1', `like_count` = '4', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '13');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '2', `like_count` = '3', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '14');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '3', `like_count` = '2', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '15');
UPDATE `globetrek`.`travel_logs` SET `comment_count` = '0', `hit` = '4', `like_count` = '1', `created_at` = '2025-05-20 18:00', `updated_at` = '2025-05-20 18:00' WHERE (`log_id` = '16');

COMMIT;