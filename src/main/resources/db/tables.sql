CREATE TABLE IF NOT EXISTS users (login varchar PRIMARY KEY, firstname varchar, lastname varchar, phonenumber varchar, birthdate varchar, avatar varchar);
MERGE INTO users VALUES('lmorda', 'Lou', 'Morda', '858-323-4431', '1981-01-11', 'https://randomuser.me/api/portraits/lego/0.jpg'),
('kmorda', 'Kate', 'Morda','858-323-4432', '1982-02-22', 'https://randomuser.me/api/portraits/lego/1.jpg'),
('lillym', 'Lilly', 'Morda','858-323-4433', '2014-03-03','https://randomuser.me/api/portraits/lego/2.jpg'),
('mmorda', 'Mary', 'Morda','858-323-4434', '1945-04-04', 'https://randomuser.me/api/portraits/lego/3.jpg');
CREATE TABLE IF NOT EXISTS messages (created_timestamp varchar PRIMARY KEY, recipient_login varchar, sender_login varchar, message varchar);
MERGE INTO messages VALUES
('2019-01-11T11:22:33Z', 'lmorda', 'kmorda', 'Hi Lou'),
('2019-02-12T12:33:44Z', 'kmorda', 'lmorda', 'Hi Kate'),
('2019-03-13T13:44:55Z', 'lillym', 'lmorda', 'Hi Lilly');

