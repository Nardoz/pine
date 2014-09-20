# Pine

## Pine Processor
Lee un archivo con tweets e inserta los datos en C*


## Pre Requisitos
* Cassandra 2.0.9
* Spark 1.0.2

sbt eclipse
sbt assembly


## Usando con Spark-Shell
sbt assembly && spark-shell -i extras/shell-init.scala --jars target/scala-2.10/pine-backend-assembly-0.1.0.jar

## Running the App

    spark-submit --master 'local[*]' \
    --class com.nardoz.pine.TweetsDriver \
    target/scala-2.10/pine-backend-assembly-0.1.0.jar \
    ../data/tweets.json.gz


## CQL
Sample CQL query to obtain data
```sql
SELECT * FROM tweet_stats WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;

SELECT * FROM flock  WHERE screen_name = 'connieansaldi' AND tweet_id = 512646924915470336;
```

## TODO
* Consumir los tweets de un stream
