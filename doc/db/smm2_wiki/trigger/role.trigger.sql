CREATE TRIGGER role_updated_at BEFORE UPDATE ON smm2_wiki.role FOR EACH ROW EXECUTE PROCEDURE updated_at();
