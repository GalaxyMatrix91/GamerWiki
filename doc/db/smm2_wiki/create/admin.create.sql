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
        

