FROM bellsoft/liberica-openjdk-alpine:17 as final
RUN mkdir /app
COPY application/build/libs/*.jar /app/app.jar
ENTRYPOINT [ "java","-jar","/app/app.jar" ]