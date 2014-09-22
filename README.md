# Pine
Visualización de Usuarios y de Tweets

## Pre Requisitos (MacOSX install)

###Instalar sbt (Simple Build Tool)
```
brew update
brew install sbt
```

###Instalar Spark
```
cd $( brew --prefix )
git checkout 7397998 /usr/local/Library/Formula/apache-spark.rb
brew install apache-spark
git checkout -- Library/Formula/apache-spark.rb
```

###Instalar Cassandra
```
brew install cassandra

# Crear el Keyspace en Cassandra
cassandra -f
```
####(en otra ventana)
```
cqlsh -f backend/extras/nardoz_pine.cql
cqlsh -k nardoz_pine
```

###Instrucciones para procesar los tweets con Spark
[Seguir acá](https://github.com/Nardoz/pine/tree/master/backend)



#TODO
* Documentar instalación de WebAPI + UI
