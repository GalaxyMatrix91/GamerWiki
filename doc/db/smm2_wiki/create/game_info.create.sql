create table "smm2_wiki"."game_info" ("id" BIGSERIAL NOT NULL PRIMARY KEY,
  "game_poster_url" VARCHAR NOT NULL,
  "game_name_zh" VARCHAR NOT NULL,
  "game_name_en" VARCHAR NOT NULL,
  "game_screenshots_url" text [] NOT NULL,
  "game_metainfo" jsonb NOT NULL,
  "game_description" VARCHAR NOT NULL,
  "state_id" INTEGER DEFAULT 1 NOT NULL,
  "remark" VARCHAR,
  "created_at" TIMESTAMPTZ DEFAULT NOW() NOT NULL,
  "updated_at" TIMESTAMPTZ DEFAULT NOW() NOT NULL);
ALTER TABLE smm2_wiki.game_info ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.game_info ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

