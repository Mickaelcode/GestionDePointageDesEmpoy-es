create table EMPLOYE(
    numEmp varchar(5) not null,
    Nom varchar(25),
    Prenom varchar(100),
    poste varchar(50),
    salaire int,
    primary key(numEmp)
);

create table POINTAGE(
    datePointage TIMESTAMP not null,
    numEmp varchar(5) not null,
    pointage varchar(20),
    primary key(datePointage),
    foreign key (numEmp) references EMPLOYE(numEmp)

);

create table CONGE(
    numConge varchar(5) not null,
    numEmp varchar(5) not null,
    motif varchar(255),
    nbrjr int,
    dateDemande date,
    dateRetour date,
    primary key(numConge),
    foreign key (numEmp) references EMPLOYE(numEmp)
);



SELECT
    p.datePointage AS Date,
    e.Nom AS Nom,
    e.Prenom AS Prénoms,
    e.poste AS Poste,
    COUNT(CASE WHEN p.pointage = 'absence' THEN 1 END) AS "Nombre d'absence",
    CONCAT(TO_CHAR(e.salaire, 'L99G999D99'), ' Ariary') AS Montant
FROM
    EMPLOYE e
JOIN
    POINTAGE p ON e.numEmp = p.numEmp
WHERE
    p.numEmp ='E003'
GROUP BY
    p.datePointage,
    e.Nom,
    e.Prenom,
    e.poste,
    e.salaire;

