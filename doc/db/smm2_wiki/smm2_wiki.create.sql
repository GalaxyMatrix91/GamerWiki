create table "smm2_wiki"."admin" ("id" SERIAL NOT NULL PRIMARY KEY,
  "token" VARCHAR NOT NULL UNIQUE,
  "role_id" INTEGER NOT NULL,
  "account" VARCHAR NOT NULL UNIQUE,
  "nonce" VARCHAR NOT NULL,
  "salt" VARCHAR NOT NULL,
  "password" VARCHAR NOT NULL,
  "remark" VARCHAR,
  "state_id" INTEGER DEFAULT 1 NOT NULL);
create index "idx_admin_account" on "smm2_wiki"."admin" ("account");
ALTER TABLE smm2_wiki.admin ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.admin ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

create table "smm2_wiki"."role" ("id" SERIAL NOT NULL PRIMARY KEY,
  "token" VARCHAR NOT NULL UNIQUE,
  "admin_id" INTEGER NOT NULL,
  "name" VARCHAR NOT NULL,
  "is_admin" BOOLEAN NOT NULL,
  "remark" VARCHAR,
  "state_id" INTEGER DEFAULT 1 NOT NULL);
create index "idx_role_admin_id" on "smm2_wiki"."role" ("admin_id");
ALTER TABLE smm2_wiki.role ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.role ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

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
        

create table "smm2_wiki"."courses" ("course_id" VARCHAR NOT NULL PRIMARY KEY,
  "course_name" VARCHAR,
  "course_type" VARCHAR,
  "course_image_url" VARCHAR,
  "course_detail_url" VARCHAR,
  "num_likes" BIGINT,
  "times_played" BIGINT,
  "clear_check_time" VARCHAR,
  "creator_name" VARCHAR,
  "creator_avatar" VARCHAR,
  "creator_nationality" VARCHAR,
  "tag1" VARCHAR,
  "tag2" VARCHAR,
  "wr_holder_name" VARCHAR,
  "wr_holder_avatar" VARCHAR,
  "wr_holder_nationality" VARCHAR,
  "wr_time" VARCHAR,
  "first_name" VARCHAR,
  "first_avatar" VARCHAR,
  "first_nationality" VARCHAR,
  "clear_rate" VARCHAR,
  "completed" BIGINT,
  "total_times" BIGINT,
  "is_poison" INTEGER,
  "oss_image_url" VARCHAR,
  "oss_image_detail_url" VARCHAR);
ALTER TABLE smm2_wiki.courses ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.courses ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

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
        

