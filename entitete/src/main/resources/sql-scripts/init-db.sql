-- Insert Uporabnik
ALTER SEQUENCE uporabnik_id_seq RESTART WITH 1;

INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Davorin', 'Alfa', 'davorin.alfa@hotmail.com', 'alfaD');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Friderik', 'Beta', 'friderik.beta@gmail.com', 'fridB');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Bonifacij', 'Gama', 'bonifacij.gama@yahoo.com', 'BonG');

-- Insert Lokacija
ALTER SEQUENCE lokacija_id_seq RESTART WITH 1;

INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (1000, 'Slovenija', 'Ljubljana', 'Bolonjska ulica 69');
INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (1000, 'Slovenija', 'Ljubljana', 'Zeusova cesta 420');
INSERT INTO lokacija (postna_st, drzava, mesto, naslov) VALUES (2000, 'Slovenija', 'Maribor', 'Odinov trg 1337');

-- Insert Polnilnica
ALTER SEQUENCE polnilnica_id_seq RESTART WITH 1;

INSERT INTO polnilnica (ime, cas_odprtja, cas_zaprtja, cena_polnjenja, "moc_v_kw", vrsta_toka, lastnik_id, lokacija_id) VALUES ('Petrol elektro', '8:00:00', '18:00:00', 10.00, 50, 'AC', 1, 1);
INSERT INTO lokacija_polnilnica (lokacija_id, polnilnice_id) VALUES (1, 1);

INSERT INTO polnilnica (ime, cas_odprtja, cas_zaprtja, cena_polnjenja, "moc_v_kw", vrsta_toka, lastnik_id, lokacija_id) VALUES ('Hofer elektro', '7:00:00', '19:00:00', 9.00, 50, 'DC', 2, 3);
INSERT INTO lokacija_polnilnica (lokacija_id, polnilnice_id) VALUES (3, 2);

-- Insert Termin
ALTER SEQUENCE termin_id_seq RESTART WITH 1;

INSERT INTO termin (uporabnik_id, polnilnica_id, zacetek_termina, konec_termina) VALUES (3, 1, '2021-11-05T10:15:30', '2021-11-05T10:45:30');
INSERT INTO polnilnica_termin(polnilnica_id, termini_id) VALUES (1, 1);
UPDATE uporabnik SET rezervacija_id = 1 WHERE id = 3;


