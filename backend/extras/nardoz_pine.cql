CREATE KEYSPACE nardoz_pine WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE nardoz_pine;

-- Contadores de rt y replies que participaron de la conversacin por minuto
DROP TABLE IF EXISTS tweet_stats;
CREATE TABLE tweet_stats (
  screen_name varchar,
  tweet_id bigint,
  ts bigint,
  rts int,
  replies int,
  PRIMARY KEY((screen_name), tweet_id, ts)
);

-- Mantiene el dato de quienes participaron de la conversacin
DROP TABLE IF EXISTS flock;
CREATE TABLE flock (
  screen_name varchar,
  tweet_id bigint,
  ts bigint,
  fan_screen_name varchar,
  fan_pic varchar,
  fan_followers varchar,
  PRIMARY KEY((screen_name), tweet_id, ts)
);
