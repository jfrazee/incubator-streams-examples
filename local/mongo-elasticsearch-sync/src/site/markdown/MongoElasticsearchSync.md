### MongoElasticsearchSync

#### Description:

Copies documents from mongodb to elasticsearch

#### Configuration:

[MongoElasticsearchSyncIT.conf](MongoElasticsearchSyncIT.conf "MongoElasticsearchSyncIT.conf" )

#### Run (SBT):

    sbtx -210 -sbt-create
    set resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
    set libraryDependencies += "org.apache.streams" % "mongo-elasticsearch-sync" % "0.4-incubating-SNAPSHOT"
    set fork := true
    set javaOptions +="-Dconfig.file=application.conf"
    run mongo-elasticsearch-sync org.apache.streams.example.MongoElasticsearchSync

#### Run (Docker):

    docker run apachestreams/mongo-elasticsearch-sync java -cp mongo-elasticsearch-sync-jar-with-dependencies.jar org.apache.streams.example.MongoElasticsearchSync

#### Specification:

[MongoElasticsearchSync.dot](MongoElasticsearchSync.dot "MongoElasticsearchSync.dot" )

#### Diagram:

![MongoElasticsearchSync.dot.svg](./MongoElasticsearchSync.dot.svg)

###### Licensed under Apache License 2.0 - http://www.apache.org/licenses/LICENSE-2.0