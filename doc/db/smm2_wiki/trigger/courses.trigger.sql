CREATE TRIGGER courses_updated_at BEFORE UPDATE ON smm2_wiki.courses FOR EACH ROW EXECUTE PROCEDURE updated_at();
