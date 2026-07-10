-- 1. A entidade principal do SaaS (Continua igual)
CREATE TABLE organizacoes (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(10) UNIQUE,
    nome_fantasia VARCHAR(150) NOT NULL,
    razao_social VARCHAR(150),
    documento VARCHAR(18),
    tipo_negocio VARCHAR(50), 
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_organizacoes_codigo_id ON organizacoes(codigo);

-- 2. O cadastro GLOBAL do usuário (A pessoa física/jurídica única no sistema)
CREATE TABLE associados (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL, -- O e-mail passa a ser o identificador único global (ex: login do Google)
    documento VARCHAR(14),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABELA DE VÍNCULO (O coração da sua alteração)
-- Vincula o associado à organização e define o perfil/cargo dele especificamente ali
CREATE TABLE organizacao_associados (
    id SERIAL PRIMARY KEY,
    organizacao_id INT REFERENCES organizacoes(id) ON DELETE CASCADE,
    associado_id INT REFERENCES associados(id) ON DELETE CASCADE,
    perfil VARCHAR(50) NOT NULL, -- Ex: 'ADMINISTRADOR', 'TESOUREIRO', 'MEMBRO', 'VISITANTE'
    cargo_customizado VARCHAR(100), -- Ex: 'Presidente', 'Vice-Presidente', 'Diretor de Eventos'
    status VARCHAR(20) DEFAULT 'ATIVO', -- ATIVO, INATIVO, SUSPENSO (nesta organização específica)
    data_vinculo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_associado_por_organizacao UNIQUE (organizacao_id, associado_id)
);

-- 4. Planos de Cobrança (Continua igual)
CREATE TABLE planos_cobranca (
    id SERIAL PRIMARY KEY,
    organizacao_id INT REFERENCES organizacoes(id) ON DELETE CASCADE,
    nome VARCHAR(100) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    frequencia VARCHAR(20) NOT NULL, 
    data_vencimento_padrao DATE
);

-- 5. Transações Financeiras (Ajustada para refletir o vínculo)
CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    organizacao_id INT REFERENCES organizacoes(id) ON DELETE CASCADE,
    associado_id INT REFERENCES associados(id) ON DELETE SET NULL, -- Histórico financeiro atrelado ao indivíduo global
    plano_cobranca_id INT REFERENCES planos_cobranca(id),
    tipo VARCHAR(10) NOT NULL, 
    valor NUMERIC(10, 2) NOT NULL,
    descricao TEXT,
    data_vencimento DATE NOT NULL,
    data_pagamento TIMESTAMP, 
    status VARCHAR(20) DEFAULT 'PENDENTE'
);