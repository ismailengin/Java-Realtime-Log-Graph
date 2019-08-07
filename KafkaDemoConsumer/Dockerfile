FROM openjdk

COPY . /usr/src/app/KafkaDemoConsumer

WORKDIR /usr/src/app/KafkaDemoConsumer

#COPY ./out/artifacts/KafkaDemoConsumer_jar/KafkaDemoConsumer.ja

CMD ["ls", "-l", "/usr/src/app/out/artifacts/KafkaDemoConsumer_jar"]

ENTRYPOINT [ "java", "-jar", "/usr/src/app/out/artifacts/KafkaDemoConsumer_jar/KafkaDemoConsumer.jar"]