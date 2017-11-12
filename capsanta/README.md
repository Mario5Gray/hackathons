# Capital One Santas Demo Services

##Build
must haves:
Java-8 (JDK), maven2, some disk space

Use homebrew to install these packages if you lack them.

####Maven2 in-place execution procedure:

```
mvn clean install
mvn spring-boot:run
```

####Maven2 executable jar procedure

... TBA

##Server Details
service runs on `localhost:8080`

###service endpoints:

GET /capsanta/byDocId/$DOCID -> {json}

GET /capsanta/connect -> redirect to capOne

GET /capsanta/oauth/token -> login

GET /capsanta/... TBA
