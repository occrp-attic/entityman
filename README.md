# entityman

Project modules :
- entitycore : core classes for entityman project
- glutton : main entity extraction flow, here is the main configuration for entity extraction logic 
- gluttonwar : REST api call to glutton functionality
- nerwrapper : REST wrapper for Stanford NER (it is possible to have multiple instances configured in the glutton config, with dictionaries for multiple languages)
- schema : occrp object schema
- authserver : simple oauth2 server (to be used in multiuser environment)
- resource : the resource REST API for oauth2 to provide model to the UI
- ui : user interface for resource model 


Build requirements:
- Oracle JDK 8
- Maven 3

Building:
mvn clean install -Dmaven.test.skip=true

Configuration files (/etc/entityman):
sudo mkdir /etc/entityman
sudo cp gluttonwar/src/main/webapp/WEB-INF/gluttonwar.properties.sample /etc/entityman/gluttonwar.properties
sudo cp glutton/src/main/resources/dic_firstnames_ro.txt /etc/entityman/
sudo cp glutton/src/main/resources/blacklist_person_name.txt /etc/entityman/

edit /etc/entityman/gluttonwar.properties 

Run:
docker-compose up

