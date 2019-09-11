CREATE TRIGGER admin_updated_at BEFORE UPDATE ON smm2_wiki.admin FOR EACH ROW EXECUTE PROCEDURE updated_at();
