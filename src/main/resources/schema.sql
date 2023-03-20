USE akkadian_lexicon;
DROP TABLE IF EXISTS `word_source`;
DROP TABLE IF EXISTS `adjective`;
DROP TABLE IF EXISTS `pronoun`;
DROP TABLE IF EXISTS `verb`;
DROP TABLE IF EXISTS `noun`;
DROP TABLE IF EXISTS `source`;
DROP TABLE IF EXISTS `king`;
DROP TABLE IF EXISTS `word`;

-- akkadian_lexicon.king definition

CREATE TABLE `king` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                        `regnal_year_from` date DEFAULT NULL,
                        `regnal_year_to` date DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.word definition

CREATE TABLE `word` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `meaning` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                        `nominative` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                        `sumerian_form` varchar(255) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.pronoun definition

CREATE TABLE `pronoun` (
                           `id` bigint NOT NULL,
                           PRIMARY KEY (`id`),
                           CONSTRAINT `FKah63mav2v9c1qjo9j14xx39wd` FOREIGN KEY (`id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.adjective definition

CREATE TABLE `adjective` (
                             `id` bigint NOT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT `FK99j7eg11fxd0uoxf194usp7x0` FOREIGN KEY (`id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.noun definition

CREATE TABLE `noun` (
                        `id` bigint NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `FKs8fhdv6gg9tn552wmbq8mrgos` FOREIGN KEY (`id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.source definition

CREATE TABLE `source` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `bibliography` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                          `catalogue_ref` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                          `text` mediumtext COLLATE utf8mb4_0900_as_cs NOT NULL,
                          `title` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                          `king_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKfgi7jq0tm9s4uummju1hwais4` (`king_id`),
                          CONSTRAINT `FKfgi7jq0tm9s4uummju1hwais4` FOREIGN KEY (`king_id`) REFERENCES `king` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.verb definition

CREATE TABLE `verb` (
                        `verbal_stem` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                        `vowel_class` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
                        `id` bigint NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `FK2phw9aowq1sxv4l1abd9ampy8` FOREIGN KEY (`id`) REFERENCES `word` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;

-- akkadian_lexicon.word_source definition

CREATE TABLE `word_source` (
                               `source_id` bigint NOT NULL,
                               `word_id` bigint NOT NULL,
                               KEY `FK52lgck6s6ci2s7recvrgbsl8w` (`word_id`),
                               KEY `FKqdbgd2h6xe2v48qt5yh2pdyro` (`source_id`),
                               CONSTRAINT `FK52lgck6s6ci2s7recvrgbsl8w` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`),
                               CONSTRAINT `FKqdbgd2h6xe2v48qt5yh2pdyro` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
