create table "smm2_wiki"."makers" ("maker_id" VARCHAR NOT NULL PRIMARY KEY,
  "maker_name" VARCHAR,
  "maker_point" BIGINT,
  "maker_avatar" VARCHAR,
  "maker_nationality" VARCHAR,
  "maker_profile_image" VARCHAR,
  "ec_easy_high_score" BIGINT,
  "ec_normal_high_score" BIGINT,
  "ec_expert_high_score" BIGINT,
  "ec_super_expert_high_score" BIGINT,
  "mv_wins" BIGINT,
  "mv_plays" BIGINT,
  "mco_clears" BIGINT,
  "mco_plays" BIGINT,
  "ph_courses_played" BIGINT,
  "ph_courses_cleared" BIGINT,
  "ph_attempts" BIGINT,
  "ph_lives_lost" BIGINT,
  "versus_rating_score" BIGINT,
  "oss_maker_image" VARCHAR,
  "oss_maker_detail_image" VARCHAR);
ALTER TABLE smm2_wiki.makers ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.makers ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

