# build frontend separately
FROM node:22-alpine AS frontend

ENV BUILD_DIR=/build/pm-web-panel

RUN mkdir -p $BUILD_DIR
WORKDIR $BUILD_DIR/pwp-frontend

COPY pwp-frontend/package.json $BUILD_DIR/package.json
COPY pwp-frontend/yarn.lock $BUILD_DIR/yarn.lock

RUN yarn install --frozen-lockfile

COPY pwp-frontend $BUILD_DIR

RUN yarn build

FROM eclipse-temurin:17-jdk-alpine AS backend

SHELL ["/bin/sh", "-c"]

# install missing xargs library
RUN apk update
RUN apk add --no-cache findutils

ENV BUILD_DIR=/build/pm-web-panel

RUN mkdir -p $BUILD_DIR
WORKDIR $BUILD_DIR

# copy only maven-based resources for optimized caching
COPY gradle $BUILD_DIR/gradle
COPY gradlew $BUILD_DIR/gradlew
COPY build.gradle.kts $BUILD_DIR/build.gradle.kts
COPY settings.gradle.kts $BUILD_DIR/settings.gradle.kts

COPY pwp-frontend/build.gradle.kts $BUILD_DIR/pwp-frontend/build.gradle.kts
COPY pwp-backend/build.gradle.kts $BUILD_DIR/pwp-backend/build.gradle.kts

RUN chmod +x $BUILD_DIR/gradlew
RUN ./gradlew dependencies --no-daemon

# copy rest of resources
COPY pwp-backend $BUILD_DIR/pwp-backend
COPY --from=frontend $BUILD_DIR/target/dist $BUILD_DIR/pwp-backend/src/main/resources/static
COPY docker $BUILD_DIR/docker

RUN ./gradlew clean --no-daemon
RUN ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:17-jre-alpine

ENV BUILD_DIR=/build/pm-web-panel
ENV ENTRY_DIR=/app/pm-web-panel
ENV JAR_NAME=pm-web-panel.jar

WORKDIR $ENTRY_DIR

RUN addgroup -S pmwpgroup && \
    adduser -S pmwpuser -G pmwpgroup

COPY --chown=pmwpuser:pmwpgroup --from=backend $BUILD_DIR/.bin/$JAR_NAME $ENTRY_DIR/$JAR_NAME
COPY --from=backend $BUILD_DIR/docker/entrypoint $ENTRY_DIR/entrypoint

RUN sed -i \
  -e "s/\$JAR_NAME/$JAR_NAME/g" \
  entrypoint

RUN chmod +x entrypoint && \
    chown pmwpuser:pmwpgroup entrypoint

LABEL maintainer="Miłosz Gilga <miloszgilga@gmail.com>"

EXPOSE 8080

USER pmwpuser

ENTRYPOINT [ "./entrypoint" ]
