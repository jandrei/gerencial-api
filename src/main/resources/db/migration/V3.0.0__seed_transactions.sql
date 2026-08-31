-- 1. Inserir associados adicionais para a organização 'Roncadores do Asfalto'
INSERT INTO associados (nome, email, documento)
VALUES 
('Robert Plant', 'robert.plant@email.com', '11122233344'),
('Ozzy Osbourne', 'ozzy@email.com', '55566677788'),
('Bruce Dickinson', 'bruce@email.com', '99900011122');

INSERT INTO organizacao_associados (organizacao_id, associado_id, perfil, cargo_customizado, status)
VALUES 
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'robert.plant@email.com' LIMIT 1), 'MEMBRO', 'Diretor de Viagens', 'ATIVO'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'ozzy@email.com' LIMIT 1), 'MEMBRO', 'Sargento de Armas', 'ATIVO'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'bruce@email.com' LIMIT 1), 'MEMBRO', 'Road Captain', 'ATIVO');

-- 2. Inserir tags iniciais
INSERT INTO tag (descricao) VALUES 
('Mensalidade'),
('Combustível'),
('Churrasco'),
('Fardamento'),
('Manutenção'),
('Evento'),
('Patrocínio');

-- 3. Inserir transações com diferentes datas (para testar o agrupamento por dia)
-- Dia 2026-07-27
INSERT INTO transacoes (organizacao_id, associado_id, tipo, valor, descricao, data_vencimento, data_pagamento, status)
VALUES 
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'tesoureiro.jandrei@email.com' LIMIT 1), 'CREDITO', 150.00, 'Mensalidade Jandrei Julho', '2026-07-27', '2026-07-27 10:00:00', 'PAGO'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'robert.plant@email.com' LIMIT 1), 'DEBITO', 80.00, 'Reembolso Gasolina Viagem', '2026-07-27', '2026-07-27 14:30:00', 'PAGO'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), NULL, 'CREDITO', 500.00, 'Patrocínio Loja de Peças', '2026-07-27', '2026-07-27 16:00:00', 'PAGO');

-- Dia 2026-07-26
INSERT INTO transacoes (organizacao_id, associado_id, tipo, valor, descricao, data_vencimento, data_pagamento, status)
VALUES 
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'ozzy@email.com' LIMIT 1), 'DEBITO', 350.00, 'Compra de Bebidas/Carne Churrasco', '2026-07-26', NULL, 'PENDENTE'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'bruce@email.com' LIMIT 1), 'CREDITO', 150.00, 'Mensalidade Bruce Julho', '2026-07-26', '2026-07-26 11:00:00', 'PAGO');

-- Dia 2026-07-25
INSERT INTO transacoes (organizacao_id, associado_id, tipo, valor, descricao, data_vencimento, data_pagamento, status)
VALUES 
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), NULL, 'DEBITO', 1200.00, 'Manutenção da Sede', '2026-07-25', '2026-07-25 09:00:00', 'PAGO'),
((SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1), (SELECT id FROM associados WHERE email = 'robert.plant@email.com' LIMIT 1), 'CREDITO', 150.00, 'Mensalidade Robert Julho', '2026-07-25', '2026-07-25 10:15:00', 'PAGO');

-- 4. Vincular tags às transações
INSERT INTO transacao_tag (transacao_id, tag_id) VALUES 
((SELECT id FROM transacoes WHERE descricao = 'Mensalidade Jandrei Julho' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Mensalidade' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Reembolso Gasolina Viagem' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Combustível' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Reembolso Gasolina Viagem' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Evento' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Patrocínio Loja de Peças' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Patrocínio' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Compra de Bebidas/Carne Churrasco' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Churrasco' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Compra de Bebidas/Carne Churrasco' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Evento' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Mensalidade Bruce Julho' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Mensalidade' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Manutenção da Sede' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Manutenção' LIMIT 1)),
((SELECT id FROM transacoes WHERE descricao = 'Mensalidade Robert Julho' LIMIT 1), (SELECT id FROM tag WHERE descricao = 'Mensalidade' LIMIT 1));
