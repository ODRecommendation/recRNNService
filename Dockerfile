FROM openjdk:8

# Define env variables
ENV SBT_VERSION  1.1.2
ENV SBT_HOME /usr/local/sbt
ENV PATH ${PATH}:${SBT_HOME}/bin

EXPOSE 9000

USER sbt

# Copy to directory
COPY . .

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  apt-get -y install libiomp5 && \
  sbt -J-Xmx8G clean assembly && \
  chmod -R 777 /modelFiles && \
  chmod -R 777 /target/universal/stage/modelFiles

ENTRYPOINT ["/entrypoint.sh"]