-- 1. Inserir a Organização (Moto Clube)
-- Gerando o ID público 'MOTO1' com 5 dígitos alfanuméricos
INSERT INTO organizacoes (nome_fantasia, razao_social, documento, tipo_negocio, ativo)
VALUES ('Roncadores do Asfalto', 'Associação Moto Clube Roncadores', '12345678000199', 'MOTO_CLUBE', true);

-- 2. Inserir o Associado (Cadastro Global do Usuário)
-- O e-mail deve ser o mesmo que virá no Token do Google durante o login
INSERT INTO associados (nome, email, documento)
VALUES ('Jandrei Silva', 'tesoureiro.jandrei@email.com', '01234567890');

-- 3. Inserir o Vínculo (Unindo o Associado à Organização com um Perfil)
-- Usamos subqueries (SELECT) para buscar os IDs gerados automaticamente pelo SERIAL
INSERT INTO organizacao_associados (organizacao_id, associado_id, perfil, cargo_customizado, status)
VALUES (
    (SELECT id FROM organizacoes WHERE nome_fantasia = 'Roncadores do Asfalto' LIMIT 1),
    (SELECT id FROM associados WHERE email = 'tesoureiro.jandrei@email.com' LIMIT 1),
    'TESOUREIRO', 
    'Primeiro Tesoureiro', 
    'ATIVO'
);