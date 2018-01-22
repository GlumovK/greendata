DELETE FROM deposits;
DELETE FROM banks;
DELETE FROM clients;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO clients (name, shortName, address, form) VALUES
  ('Putin V.V.', 'Putin', 'Russia, Moscow', 'FORM_PERSON'),
  ('Trump D.', 'Trump', 'USA, Washington', 'FORM_PERSON'),
  ('Roga & Co', 'Roga', 'Russia, Perm', 'FORM_COMPANY');

INSERT INTO banks (name, bic) VALUES
  ('Sberbank', '045773603'),
  ('DeutscheBank', '044525101');

INSERT INTO deposits (client_id, bank_id, date, interest, period) VALUES
  (100000, 100003, '2000-03-30', 35, 240),
  (100001, 100004, '2016-11-08', 4, 48),
  (100002, 100003, '2018-01-01', 7, 12),
  (100002, 100004, '2017-03-03', 8, 24);



