UPDATE tb_vehicles SET contract_id = NULL WHERE contract_id IS NOT NULL;

DELETE FROM tb_contracts;
DELETE FROM tb_vehicles;
DELETE FROM tb_clients;
DELETE FROM tb_contract_templates;
DELETE FROM tb_payments;

ALTER SEQUENCE tb_clients_client_id_seq RESTART WITH 1;
ALTER SEQUENCE tb_contract_templates_contract_template_id_seq RESTART WITH 1;
ALTER SEQUENCE tb_vehicles_vehicle_id_seq RESTART WITH 1;
ALTER SEQUENCE tb_contracts_contract_id_seq RESTART WITH 1;
ALTER SEQUENCE tb_payments_payment_id_seq RESTART WITH 1;


INSERT INTO tb_clients (name, phone_number, email, cpf)
VALUES
    ('Ana Silva', '11987654321', 'ana.silva@email.com', '12345678901'),
    ('Bruno Costa', '11998765432', 'bruno.costa@email.com', '10987654321'),
    ('Carlos Souza', '11912345678', 'carlos.souza@email.com', '11223344556'),
    ('Daniela Lima', '11923456789', 'daniela.lima@email.com', '12312312301'),
    ('Eduardo Santos', '11934567890', 'eduardo.santos@email.com', '23423423402'),
    ('Fernanda Alves', '11945678901', 'fernanda.alves@email.com', '34534534503'),
    ('Gabriel Pereira', '11956789012', 'gabriel.pereira@email.com', '45645645604'),
    ('Helena Rocha', '11967890123', 'helena.rocha@email.com', '56756756705'),
    ('Igor Martins', '11978901234', 'igor.martins@email.com', '67867867806'),
    ('Juliana Ribeiro', '11989012345', 'juliana.ribeiro@email.com', '78978978907');

INSERT INTO tb_contract_templates (name, value, due_date)
VALUES
    ('Plano Mensal - Carro', 150.00, 10),
    ('Plano Anual - Moto', 1200.00, 20),
    ('Plano Semestral - Carro', 750.00, 5);

INSERT INTO tb_vehicles (license_plate, vehicle_type, client_id, contract_id)
VALUES
    ('ABC1234', 'CAR', 1, NULL),
    ('XYZ5678', 'MOTORCYCLE', 2, NULL),
    ('DEF9012', 'CAR', 3, NULL),
    ('GHI3456', 'CAR', 4, NULL),
    ('JKL7890', 'MOTORCYCLE', 5, NULL),
    ('MNO1234', 'CAR', 6, NULL),
    ('PQR5678', 'CAR', 7, NULL),
    ('STU9012', 'MOTORCYCLE', 8, NULL),
    ('VWX3456', 'CAR', 9, NULL),
    ('YZA7890', 'MOTORCYCLE', 10, NULL);

INSERT INTO tb_contracts (start_date, renewal_date, contract_status, contract_template_id, vehicle_vehicle_id, last_payment_id)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_DATE + INTERVAL '15' DAY, 'ACTIVE', 1, 1, NULL),
    (CURRENT_TIMESTAMP, CURRENT_DATE + INTERVAL '10' DAY, 'ACTIVE', 2, 2, NULL),
    (CURRENT_TIMESTAMP, CURRENT_DATE + INTERVAL '5' DAY, 'ACTIVE', 3, 3, NULL),
    ('2025-09-01T10:00:00', '2026-09-01T10:00:00', 'ACTIVE', 1, 4, NULL),
    ('2025-09-02T10:00:00', '2026-09-02T10:00:00', 'ACTIVE', 2, 5, NULL),
    ('2025-09-03T10:00:00', '2026-09-03T10:00:00', 'ACTIVE', 3, 6, NULL),
    ('2025-09-04T10:00:00', '2026-09-04T10:00:00', 'ACTIVE', 1, 7, NULL);

UPDATE tb_vehicles SET contract_id = 1 WHERE vehicle_id = 1;
UPDATE tb_vehicles SET contract_id = 2 WHERE vehicle_id = 2;
UPDATE tb_vehicles SET contract_id = 3 WHERE vehicle_id = 3;
UPDATE tb_vehicles SET contract_id = 4 WHERE vehicle_id = 4;
UPDATE tb_vehicles SET contract_id = 5 WHERE vehicle_id = 5;
UPDATE tb_vehicles SET contract_id = 6 WHERE vehicle_id = 6;
UPDATE tb_vehicles SET contract_id = 7 WHERE vehicle_id = 7;