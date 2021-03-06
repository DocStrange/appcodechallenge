CREATE SCHEMA account
  AUTHORIZATION appdirect;

CREATE TABLE account.status
(
  status_id           serial NOT NULL,
  name                character varying(255),
  created             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  CONSTRAINT status_id_pk PRIMARY KEY (status_id)
)
WITH (
OIDS = FALSE
);


INSERT INTO account.status (name, created)VALUES ('INITIALIZED' , now());
INSERT INTO account.status (name, created)VALUES ('FAILED' , now());
INSERT INTO account.status (name, created)VALUES ('FREE_TRIAL' , now());
INSERT INTO account.status (name, created)VALUES ('FREE_TRIAL_EXPIRED' , now());
INSERT INTO account.status (name, created)VALUES ('ACTIVE' , now());
INSERT INTO account.status (name, created)VALUES ('SUSPENDED' , now());
INSERT INTO account.status (name, created)VALUES ('CANCELLED' , now());


CREATE TABLE account.account
(
  account_id               SERIAL NOT NULL,
  number_users             INTEGER,
  uuid                     TEXT NOT NULL,
  status_id                INTEGER,
  edition_code             character varying(255),
  pricing_duration         character varying(255),
  modified                 TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  created                  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  CONSTRAINT account_id_pk PRIMARY KEY (account_id),
  CONSTRAINT subscription_status_fkey FOREIGN KEY (status_id)
  REFERENCES account.status (status_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);

CREATE UNIQUE INDEX account_uuid_idx ON account.account USING btree (uuid);


CREATE TABLE account.user
(
  user_id             SERIAL NOT NULL,
  user_uuid           TEXT NOT NULL,
  email               character varying(255),
  first_name          character varying(255),
  last_name           character varying(255),
  language            character varying(2),
  open_id             TEXT,
  attributes          TEXT,
  city                character varying(255),
  country             character varying(2),
  state               character varying(2),
  street1             TEXT,
  street2             TEXT,
  zip                 character varying(10),
  account_id          INTEGER,
  owner               boolean,
  modified            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  created             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  CONSTRAINT user_id_pk PRIMARY KEY (user_id),
  CONSTRAINT account_id_fkey FOREIGN KEY (account_id)
  REFERENCES account.account (account_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);


CREATE UNIQUE INDEX user_user_uuid_idx ON account."user" USING btree (user_uuid);


