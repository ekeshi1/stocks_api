FROM openjdk:15-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/opt/stocksapi/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

FROM adoptopenjdk/openjdk15:alpine-jre
ENV ARTIFACT_NAME=stocksapi-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/opt/stocksapi/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8081
ENTRYPOINT exec java -jar -Dspring.profiles.active=combo ${ARTIFACT_NAME}
