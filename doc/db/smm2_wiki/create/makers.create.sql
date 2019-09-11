create table "smm2_wiki"."makers" ("maker_id" VARCHAR NOT NULL PRIMARY KEY,
  "maker_name" VARCHAR NOT NULL,
  "maker_point" BIGINT NOT NULL,
  "maker_avatar" VARCHAR NOT NULL,
  "maker_nationality" VARCHAR NOT NULL,
  "maker_profile_image" VARCHAR NOT NULL,
  "ec_easy_high_score" BIGINT NOT NULL,
  "ec_normal_high_score" BIGINT NOT NULL,
  "ec_expert_high_score" BIGINT NOT NULL,
  "ec_super_expert_high_score" BIGINT NOT NULL,
  "mv_wins" BIGINT NOT NULL,
  "mv_plays" BIGINT NOT NULL,
  "mco_clears" BIGINT NOT NULL,
  "mco_plays" BIGINT NOT NULL,
  "ph_courses_played" BIGINT NOT NULL,
  "ph_courses_cleared" BIGINT NOT NULL,
  "ph_attempts" BIGINT NOT NULL,
  "ph_lives_lost" BIGINT NOT NULL,
  "oss_maker_image" VARCHAR,
  "oss_maker_detail_image" VARCHAR);
ALTER TABLE smm2_wiki.makers ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.makers ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

