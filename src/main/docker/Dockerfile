FROM eclipse-temurin:11.0.13_8-jre-alpine@sha256:6bb47aba5cebd4cb5ae09b64a54bcaf4fa5903dde0526a664e0b20528336075b

ARG JAR_FILE

RUN addgroup -S app-group && adduser -D -H -S app-user -G app-group -u 1000
USER app-user
EXPOSE 8080
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
