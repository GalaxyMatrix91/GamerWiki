CREATE TRIGGER game_info_updated_at BEFORE UPDATE ON smm2_wiki.game_info FOR EACH ROW EXECUTE PROCEDURE updated_at();
