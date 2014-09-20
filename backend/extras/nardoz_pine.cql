CREATE KEYSPACE nardoz_pine WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE nardoz_pine;

-- Contadores de rt y replies que participaron de la conversacin por minuto
DROP TABLE IF EXISTS tweet_stats;
CREATE TABLE tweet_stats (
  screen_name TEXT,
  tweet_id BIGINT,
  ts BIGINT,
  rts INT,
  replies INT,
  PRIMARY KEY((screen_name), tweet_id, ts)
);

-- Mantiene el dato de quienes participaron de la conversacin
DROP TABLE IF EXISTS flock;
CREATE TABLE flock (
  screen_name TEXT,
  tweet_id BIGINT,
  ts BIGINT,
  fan_screen_name TEXT,
  fan_pic TEXT,
  fan_followers INT,
  PRIMARY KEY((screen_name), tweet_id, ts)
);