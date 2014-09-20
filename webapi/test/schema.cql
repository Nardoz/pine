CREATE TABLE tweet_stats (
  screen_name varchar,
  tweet_id varchar,
  create_at int,
  rts int,
  replies int,
  PRIMARY KEY((screen_name), tweet_id, ts)
);


CREATE TABLE tweet_stats (
  screen_name varchar,
  PRIMARY KEY(screen_name)
);

CREATE TABLE tweet_stats (
  key uuid PRIMARY KEY, 
  screen_name TEXT
);