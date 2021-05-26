FROM node:12 as frontend

RUN mkdir /app
WORKDIR /app
COPY . /app
RUN cd frontend && npm install && npm run build
RUN rm -rf /app/frontend  # remove node modules to save some space

FROM gradle:jdk16 as builder

COPY --from=frontend --chown=gradle:gradle /app /home/gradle/src
COPY app/src/main/resources/app.prod.conf /home/gradle/src/app/src/main/resources/app.conf
WORKDIR /home/gradle/src
RUN gradle build && gradle fatJar

FROM openjdk:16-alpine

RUN mkdir -p /app/
COPY --from=builder /home/gradle/src/app/build/libs/app.jar /app/app.jar

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]