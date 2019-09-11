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
        

