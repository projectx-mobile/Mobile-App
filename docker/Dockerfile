FROM gradle:jdk17-alpine as build
RUN mkdir /home/app
ADD ./ /home/app
WORKDIR /home/app
RUN gradle build
FROM bellsoft/liberica-openjdk-alpine:17 as final
RUN mkdir /app
COPY --from=build /home/app/application/build/libs/*.jar /app/app.jar
ENTRYPOINT [ "java","-jar","/app/app.jar" ]