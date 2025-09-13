DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'pagamentos') THEN
      CREATE USER pagamentos WITH PASSWORD 'pagamentos';
   END IF;
END
$$;

DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagamentos') THEN
      CREATE DATABASE pagamentos;
   END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE pagamentos TO pagamentos;

-- Criar schema pagamentos se não existir
CREATE SCHEMA IF NOT EXISTS pagamentos AUTHORIZATION pagamentos;

-- Permissões no schema pagamentos
GRANT ALL ON SCHEMA pagamentos TO pagamentos;
GRANT CREATE ON SCHEMA pagamentos TO pagamentos;
ALTER DEFAULT PRIVILEGES IN SCHEMA pagamentos GRANT ALL ON TABLES TO pagamentos;
ALTER DEFAULT PRIVILEGES IN SCHEMA pagamentos GRANT ALL ON SEQUENCES TO pagamentos;

-- Manter permissões no schema public como fallback
GRANT ALL ON SCHEMA public TO pagamentos;
GRANT CREATE ON SCHEMA public TO pagamentos;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO pagamentos;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO pagamentos;