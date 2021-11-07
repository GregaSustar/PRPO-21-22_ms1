ALTER SEQUENCE uporabnik_id_seq RESTART WITH 1;
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Davorin', 'Alfa', 'alfaD', 'davorin.alfa@hotmail.com');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Friderik', 'Beta', 'fridB', 'friderik.beta@gmail.com');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Bonifacij', 'Gama', 'BonG', 'bonifacij.gama@yahoo.com');
ALTER SEQUENCE lokacija_id_seq RESTART WITH 1;
INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (1000, 'Slovenija', 'Ljubljana', 'Bolonjska ulica 69');
INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (1000, 'Slovenija', 'Ljubljana', 'Zeusova cesta 420');
INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (2000, 'Slovenija', 'Maribor', 'Odinov trg 1337');
ALTER SEQUENCE polnilnica_id_seq RESTART WITH 1;
INSERT INTO polnilnica (ime, lokacija_id, cas_odprtja, cas_zaprtja, lastnik_id, cena_polnjenja, "moc_kW", vrsta_toka)VALUES ('Petrol elektro', 1, '8:00:00', '18:00:00', 1, 10.00, 50, 'AC');
INSERT INTO polnilnica (ime, lokacija_id, cas_odprtja, cas_zaprtja, lastnik_id, cena_polnjenja, "moc_kW", vrsta_toka)VALUES ('Hofer elektro', 2, '7:00:00', '19:00:00', 1, 9.00, 50, 'DC');
ALTER SEQUENCE termin_id_seq RESTART WITH 1;
INSERT INTO termin (uporabnik_id, polnilnica_id, zacetek_termina, konec_termina) VALUES (3, 1, '2021-11-05T10:15:30', '2021-11-05T10:45:30');
UPDATE uporabnik SET termin_id = 1 WHERE id = 3