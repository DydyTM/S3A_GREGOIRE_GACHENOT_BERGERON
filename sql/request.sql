SELECT numplat, libelle from plat natural join contient natural join commande where datcom between to_date('10/09/2016') and to_date('12/10/2016') group by numplat, libelle order by numplat;

SELECT numplat, libelle from plat group by numplat, libelle having numplat not in (SELECT numplat from plat natural join contient natural join commande where datcom between to_date('10/09/2016') and to_date('12/09/2016'));

SELECT nomserv, dataff from serveur natural join affecter where numtab = 15 and dataff between to_date('10/09/2016') and to_date('12/10/2016');

SELECT nomserv, sum(montcom), count(numcom) from commande natural join affecter natural join serveur where dataff between to_date('10/09/2016') and to_date('12/10/2016') group by nomserv;

SELECT nomserv from serveur group by nomserv having nomserv not in (SELECT nomserv from commande natural join affecter natural join serveur where dataff between to_date('10/09/2016') and to_date('12/10/2016'));

update COMMANDE set montcom = (select sum(prixunit * quantite) from CONTIENT natural join PLAT where NUMCOM = 100) where numcom = 100;