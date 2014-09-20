# Pine

## Pine Processor
Lee un archivo con tweets e inserta los datos en C*


## Pre Requisitos
* Cassandra 2.0.9
* Spark 1.0.2



sbt eclipse
sbt assembly


## Usando Spark-Shell

sbt assembly && spark-shell -i extras/shell-init.scala --jars target/scala-2.10/pine-backend-assembly-0.1.0.jar





## TODO
* Consumir los tweets de un stream




