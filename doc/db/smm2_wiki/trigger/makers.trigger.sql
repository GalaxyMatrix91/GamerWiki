CREATE TRIGGER makers_updated_at BEFORE UPDATE ON smm2_wiki.makers FOR EACH ROW EXECUTE PROCEDURE updated_at();
