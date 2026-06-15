-- ==========================================
-- BANCO DE DADOS CLINICA MEDICA
-- Autor: Kauhã Costa
-- ==========================================

CREATE DATABASE dbclinica;


-- ==========================================
-- TABELA ESPECIALIDADE
-- ==========================================

CREATE TABLE especialidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(500) NOT NULL,
    descricao TEXT
);


-- ==========================================
-- TABELA MEDICO
-- ==========================================

CREATE TABLE medico (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(500) NOT NULL,
    crm VARCHAR(50) NOT NULL,
    email VARCHAR(250),
    telefone VARCHAR(20),
    datahorareg TIMESTAMP NOT NULL,

    id_especialidade INTEGER NOT NULL,

    CONSTRAINT fk_medico_especialidade
        FOREIGN KEY (id_especialidade)
        REFERENCES especialidade(id)
);


-- ==========================================
-- TABELA PACIENTE
-- ==========================================

CREATE TABLE paciente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(500) NOT NULL,
    cpf VARCHAR(14),
    datanascimento DATE,
    email VARCHAR(250),
    telefone VARCHAR(20),
    convenio VARCHAR(200),
    datahorareg TIMESTAMP NOT NULL
);


-- ==========================================
-- TABELA CONSULTA
-- ==========================================

CREATE TABLE consulta (
    id SERIAL PRIMARY KEY,

    datahoraconsulta TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    observacao TEXT,
    datahorareg TIMESTAMP NOT NULL,

    id_medico INTEGER NOT NULL,
    id_paciente INTEGER NOT NULL,

    CONSTRAINT fk_consulta_medico
        FOREIGN KEY (id_medico)
        REFERENCES medico(id),

    CONSTRAINT fk_consulta_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES paciente(id)
);


-- ==========================================
-- DADOS INICIAIS PARA TESTE
-- ==========================================

INSERT INTO especialidade (nome, descricao)
VALUES
('Cardiologia', 'Especialidade do coração'),
('Pediatria', 'Especialidade infantil'),
('Ortopedia', 'Especialidade óssea');

INSERT INTO medico
(nome, crm, email, telefone, datahorareg, id_especialidade)
VALUES
('João Silva', 'CRM12345', 'joao@clinica.com',
 '(54)99999-1111', CURRENT_TIMESTAMP, 1);

INSERT INTO paciente
(nome, cpf, datanascimento, email, telefone, convenio, datahorareg)
VALUES
('Maria Souza',
 '123.456.789-00',
 '1990-05-20',
 'maria@email.com',
 '(54)99999-2222',
 'Unimed',
 CURRENT_TIMESTAMP);

INSERT INTO consulta
(datahoraconsulta, status, observacao,
 datahorareg, id_medico, id_paciente)
VALUES
(
 CURRENT_TIMESTAMP + INTERVAL '1 day',
 'AGENDADA',
 'Primeira consulta',
 CURRENT_TIMESTAMP,
 1,
 1
);

-- ==========================================
-- USER PARA LOGIN
-- ==========================================
INSERT INTO usuario (usuario, senha) VALUES ('admin', '123');