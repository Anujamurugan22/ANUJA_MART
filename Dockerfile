# Stage 1: Build the Maven WAR using Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependency downloads
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy application source code and package WAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Serve WAR on Apache Tomcat 9 with Java 17
FROM tomcat:9.0-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy packaged WAR to ROOT.war so ANUJA MART is served at the domain root
COPY --from=build /app/target/ANUJA_MART.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

# Configure concrete port dynamically at container startup with 8080 fallback
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/g\" /usr/local/tomcat/conf/server.xml && catalina.sh run"]
