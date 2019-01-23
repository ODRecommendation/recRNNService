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
  apt-get install sbt && \
  apt-get -y install libiomp5

# Copy to directory
COPY . .

# Build fat jar
RUN sbt -J-Xmx8G clean assembly

EXPOSE 9000

RUN chmod -R 777 /modelFiles && \
    chmod -R 777 /target/universal/stage/modelFiles

USER sbt

ENTRYPOINT ["/entrypoint.sh"]