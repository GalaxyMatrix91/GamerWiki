CREATE TRIGGER admin_updated_at BEFORE UPDATE ON smm2_wiki.admin FOR EACH ROW EXECUTE PROCEDURE updated_at();
CREATE TRIGGER role_updated_at BEFORE UPDATE ON smm2_wiki.role FOR EACH ROW EXECUTE PROCEDURE updated_at();
CREATE TRIGGER makers_updated_at BEFORE UPDATE ON smm2_wiki.makers FOR EACH ROW EXECUTE PROCEDURE updated_at();
CREATE TRIGGER courses_updated_at BEFORE UPDATE ON smm2_wiki.courses FOR EACH ROW EXECUTE PROCEDURE updated_at();
