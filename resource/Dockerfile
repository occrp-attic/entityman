FROM jetty:latest
MAINTAINER Smári McCarthy <smari@occrp.org>

RUN apt-get update
RUN apt-get install -y maven mongodb

RUN mkdir -p /srv/projects/entityman
COPY . /srv/projects/entityman

WORKDIR /srv/projects/entityman
RUN mvn clean install

CMD ["mvn", "jetty:run"]
