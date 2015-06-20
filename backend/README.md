# Pine
## Pine Processor
Lee un archivo con tweets e inserta los datos en C*


## Pre Requisitos
* sbt
* Cassandra
* Apache Spark

```bash
$ brew install sbt cassandra apache-spark
```

##Creando el modelo de datos
```bash
cassandra -f

# Abrir otra ventana
cqlsh -f extras/nardoz_pine.cql


cqlsh -k nardoz_pine
Connected to Test Cluster at 127.0.0.1:9042.
[cqlsh 5.0.1 | Cassandra 2.1.6 | CQL spec 3.2.0 | Native protocol v3]
Use HELP for help.

cqlsh:nardoz_pine> DESCRIBE TABLES;

rts_tweet_stats  replies_tweet_stats  rts_flock  replies_flock
```


## Usando con Spark-Shell
```bash
sbt assembly && \
  spark-shell -i extras/shell-init.scala --jars target/scala-2.10/pine-backend-assembly-0.1.0.jar
```

## Running the App

    Driver Parameters: TweetsDriver <filename> [cassandra node]

    Example:
    spark-submit --master 'local[*]' \
    --class com.nardoz.pine.TweetsDriver \
    target/scala-2.10/pine-backend-assembly-0.1.0.jar \
    ../data/tweets.json.gz Arjones.local

## CQL
Sample CQL query to obtain data
```sql
-- RTS
SELECT * FROM rts_tweet_stats WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;
SELECT * FROM rts_flock  WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;

-- REPLIES
SELECT * FROM replies_tweet_stats WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;
SELECT * FROM replies_flock  WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;
```

## TODO
* Consumir los tweets de un stream
* Juntar ambos datos en una misma tabla, necesita [FULL OUTER JOIN](https://github.com/apache/spark/pull/1395)
