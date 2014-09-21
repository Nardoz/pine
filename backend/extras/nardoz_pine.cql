CREATE KEYSPACE IF NOT EXISTS nardoz_pine WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE nardoz_pine;

-- Contadores de rt y replies que participaron de la conversacin por minuto
DROP TABLE IF EXISTS rts_tweet_stats;
CREATE TABLE rts_tweet_stats (
  screen_name varchar,
  tweet_id bigint,
  created_at bigint,
  count int,
  PRIMARY KEY((screen_name), tweet_id, created_at)
);

-- Mantiene el dato de quienes participaron de la conversacin
DROP TABLE IF EXISTS rts_flock;
CREATE TABLE rts_flock (
  screen_name varchar,
  tweet_id bigint,
  created_at bigint,
  fan_screen_name varchar,
  fan_pic varchar,
  fan_followers varchar,
  PRIMARY KEY((screen_name), tweet_id, created_at)
);

DROP TABLE IF EXISTS replies_tweet_stats;
CREATE TABLE replies_tweet_stats (
  screen_name varchar,
  tweet_id bigint,
  created_at bigint,
  count int,
  PRIMARY KEY((screen_name), tweet_id, created_at)
);

-- Mantiene el dato de quienes participaron de la conversacin
DROP TABLE IF EXISTS replies_flock;
CREATE TABLE replies_flock (
  screen_name varchar,
  tweet_id bigint,
  created_at bigint,
  fan_screen_name varchar,
  fan_pic varchar,
  fan_followers varchar,
  PRIMARY KEY((screen_name), tweet_id, created_at)
);
