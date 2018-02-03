create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256), --password,implicit,code/authorization_code,client
  web_server_redirect_uri VARCHAR(256),
  -- authorities VARCHAR(256),
  --autoapprove VARCHAR(256),
  --additional_information VARCHAR(4096),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token text,
  authentication_id VARCHAR(256),
  user_id integer not null,
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token text,
  authentication_id VARCHAR(256),
  user_id integer not null,
  client_id VARCHAR(256),
  authentication text,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token text,
  authentication text
);

create table oauth_code (
  code VARCHAR(256),
  authentication text
);

create table oauth_approvals (
  userId VARCHAR(256),
  client_id VARCHAR(256),
  scope VARCHAR(256),
  status VARCHAR(10),
  expires_at TIMESTAMP,
  updated_at TIMESTAMP
);


create table users (
    id serial primary key,
    password text not null,
    mobile text,
    email text
    -- additional user info
);