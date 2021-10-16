FROM openjdk:8-jre
MAINTAINER dalexander2israel
COPY maven /usr/src/${appname}
VOLUME ["/usr/src/${appname}/data"]
WORKDIR /usr/src/${appname}
CMD ["java","-cp","${project.build.finalName}.${project.packaging}:lib/*","com.dalexander2israel.Queen8.Queen8"]
