FROM 729964090428.dkr.ecr.us-east-1.amazonaws.com/ecom-arch/alpine-scala:2.11.12

COPY . .


RUN /usr/local/sbt/bin/sbt -J-Xmx4G assembly

#RUN unzip ./target/universal/subscriptionservice-1.0-SNAPSHOT.zip

EXPOSE 9000

RUN chmod -R 777 /modelfiles

USER sbt

#ENTRYPOINT ["subscriptionservice-1.0-SNAPSHOT/bin/subscriptionservice", "-J-Xms2g", "-J-Xmx4g", "-J-server"]
ENTRYPOINT ["/entrypoint.sh"]