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
        

