INSERT INTO tb_client (name, cpf, register_Date, img_Url) VALUES ('Alex', '123.456.789-10', TIMESTAMP WITH TIME ZONE '2023-07-24T11:15:00', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg');
INSERT INTO tb_client (name, cpf, register_Date, img_Url) VALUES ('Maria', '000.000.000-00', TIMESTAMP WITH TIME ZONE '2023-08-10T15:45:14', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg');
INSERT INTO tb_client (name, cpf, register_Date, img_Url) VALUES ('Bob', '999.999.999-99', TIMESTAMP WITH TIME ZONE '2023-09-10T12:30:00', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg');

INSERT INTO tb_address (client_id, street, city, neighborhood, complement, tag) VALUES (1, 'Rua Assis Brasil, 465', 'Bento Gonçalves', 'Centro', null, 'Tag1');
INSERT INTO tb_address (client_id, street, city, neighborhood, complement, tag) VALUES (2, 'Rua Ramiro Barcelos, 123', 'Bento Gonçalves', 'Centro', 2, 'Tag2');
INSERT INTO tb_address (client_id, street, city, neighborhood, complement, tag) VALUES (3, 'Rua Saldanha Marinho, 777', 'Bento Gonçalves', 'Centro', null, 'Tag3');

INSERT INTO tb_contact (client_id, tag, type, contact_Value) VALUES (1, 'Tag1', true, 'alex@gmail.com');
INSERT INTO tb_contact (client_id, tag, type, contact_Value) VALUES (2, 'Tag1', false, '(54)99999-9999');
INSERT INTO tb_contact (client_id, tag, type, contact_Value) VALUES (3, 'Tag1', true, 'bob@gmail.com');

INSERT INTO tb_breed (description) VALUES ('Pitbull');
INSERT INTO tb_breed (description) VALUES ('Golden Retriever');

INSERT INTO tb_pet (client_id, breed_id, name, birth_Date, img_Url) VALUES (1, 1, 'Myke', TIMESTAMP WITH TIME ZONE '2016-01-11', 'https://www.petlove.com.br/images/breeds/193221/profile/original/pitbull-p.jpg?1532539293');
INSERT INTO tb_pet (client_id, breed_id, name, birth_Date, img_Url) VALUES (2, 1, 'Sia', TIMESTAMP WITH TIME ZONE '2022-10-20', 'https://www.petlove.com.br/images/breeds/193223/profile/original/golden_retriever-p.jpg?1532539102');
INSERT INTO tb_pet (client_id, breed_id, name, birth_Date, img_Url) VALUES (3, 2, 'Gary', TIMESTAMP WITH TIME ZONE '2022-10-20', 'https://www.petlove.com.br/images/breeds/193223/profile/original/golden_retriever-p.jpg?1532539102');

INSERT INTO tb_assistance (pet_id, description, assistance_Value, date) VALUES (1, 'Regular exams', 50.0, TIMESTAMP WITH TIME ZONE '2023-09-20T13:45:10');
INSERT INTO tb_assistance (pet_id, description, assistance_Value, date) VALUES (2, 'Monthly exams', 120.0, TIMESTAMP WITH TIME ZONE '2023-11-02T10:20:51');
INSERT INTO tb_assistance (pet_id, description, assistance_Value, date) VALUES (3, 'Exams', 185.0, TIMESTAMP WITH TIME ZONE '2023-11-02T10:20:51');