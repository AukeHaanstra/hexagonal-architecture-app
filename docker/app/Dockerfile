FROM eclipse-temurin:23-jre

USER root

WORKDIR /app
COPY main/target/*-exec.jar run.jar
USER 1001

CMD ["bash", "-c", "java -jar /app/run.jar -DPOSTGRES_URL=$POSTGRES_URL -DPOSTGRES_USERNAME=$POSTGRES_USERNAME -DDEFAULT_LOGGING_LEVEL=$DEFAULT_LOGGING_LEVEL"]