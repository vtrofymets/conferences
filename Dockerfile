FROM openjdk:17.0.2-slim as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} conferences.jar
RUN java -Djarmode=layertools -jar conferences.jar extract


FROM openjdk:17.0.2-slim
VOLUME /tmp
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]