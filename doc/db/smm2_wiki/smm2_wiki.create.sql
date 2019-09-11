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
        

create table "smm2_wiki"."courses" ("course_id" VARCHAR NOT NULL PRIMARY KEY,
  "course_name" VARCHAR NOT NULL,
  "course_type" VARCHAR NOT NULL,
  "course_image_url" VARCHAR NOT NULL,
  "course_detail_url" VARCHAR NOT NULL,
  "num_likes" BIGINT NOT NULL,
  "times_played" BIGINT NOT NULL,
  "clear_check_time" VARCHAR NOT NULL,
  "creator_name" VARCHAR NOT NULL,
  "creator_avatar" VARCHAR NOT NULL,
  "creator_nationality" VARCHAR NOT NULL,
  "tag1" VARCHAR NOT NULL,
  "tag2" VARCHAR NOT NULL,
  "wr_holder_name" VARCHAR,
  "wr_holder_avatar" VARCHAR,
  "wr_holder_nationality" VARCHAR,
  "wr_time" VARCHAR,
  "first_name" VARCHAR,
  "first_avatar" VARCHAR,
  "first_nationality" VARCHAR,
  "clear_rate" VARCHAR NOT NULL,
  "completed" BIGINT NOT NULL,
  "total_times" BIGINT NOT NULL,
  "oss_image_url" VARCHAR,
  "oss_image_detail_url" VARCHAR);
ALTER TABLE smm2_wiki.courses ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
ALTER TABLE smm2_wiki.courses ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL;
        

