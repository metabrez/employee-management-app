CREATE TABLE Employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) NOT NULL,
                          firstName VARCHAR(50) default ,
                          lastName VARCHAR(50) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          age INT NOT NULL,
                          address VARCHAR(100) NOT NULL
);
