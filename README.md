# entityman


Running :
mvn clean install jetty:run

Posting files:
curl -q  -F name=test -F filedata=@pathto/input1.pdf http://127.0.0.1:8080/entities/ingestSync/default | python -m json.tool
