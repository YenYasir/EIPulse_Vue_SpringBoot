CREATE TABLE `news`  (
  `news_id` int PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255)  NOT NULL,
  `content` text NOT NULL,
  `file` longblob ,
  `post_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `publisher` int NOT NULL,
	`is_visible` boolean DEFAULT true NOT NULL,
 FOREIGN KEY (`publisher`) REFERENCES `employee` (`emp_id`)
) AUTO_INCREMENT = 1 ;