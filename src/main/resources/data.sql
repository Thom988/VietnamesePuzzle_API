DROP TABLE IF EXISTS COMBINAISON;

CREATE TABLE COMBINAISON (
    combinaison_id INT AUTO_INCREMENT PRIMARY KEY,
    valeur VARCHAR(20) NOT NULL,
    valide BOOLEAN NOT NULL
);

INSERT INTO COMBINAISON (valeur, valide)
VALUES ('3,2,8,6,5,1,9,7,4', TRUE);