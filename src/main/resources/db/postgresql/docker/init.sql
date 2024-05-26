CREATE USER pagamentos;
ALTER ROLE pagamentos WITH PASSWORD 'pagamentos';
GRANT ALL PRIVILEGES ON DATABASE pagamentos TO pagamentos;
GRANT ALL ON SCHEMA public TO pagamentos;