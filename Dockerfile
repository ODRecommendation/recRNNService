FROM openjdk:8

# Define env variables
ENV SBT_VERSION  1.1.2
ENV SBT_HOME /usr/local/sbt
ENV PATH ${PATH}:${SBT_HOME}/bin

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt

# Define working directory
WORKDIR /

RUN mkdir modelFiles

# Copy to directory
COPY /target/scala-2.11/recrnnservice-assembly-1.0-SNAPSHOT.jar ./
COPY /modelFiles ./modelFiles

# Build to fat jar
#RUN sbt -J-Xmx4G assembly


# Define entrypoint
#ENTRYPOINT ["/entrypoint.sh"]

CMD ["java", "-Xmx2g", "-jar", "recrnnservice-assembly-1.0-SNAPSHOT.jar"]