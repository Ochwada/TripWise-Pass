# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# (Optional) If you have a Maven wrapper/.mvn directory, copy those too for reproducibility
# COPY .mvn/ .mvn/
# COPY mvnw mvnw.cmd ./

# 1) Copy only the pom first to leverage Docker layer caching of dependencies
COPY pom.xml .

# If you have a custom settings.xml, uncomment and copy it:
# COPY .docker/maven-settings.xml /root/.m2/settings.xml

# 2) Prime the local repo so later builds are faster (no tests)
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q -DskipTests dependency:go-offline

# 3) Copy sources and build the fat jar (Spring Boot repackage)
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Security: run as non-root
RUN useradd --no-create-home --uid 10001 appuser
USER appuser

# Choose a deterministic jar name from target (set in pom.xml via <finalName>)
# If you haven't set <finalName>, copy the only bootable jar found:
# (Safer: explicitly name it to avoid wildcard surprises.)
ARG JAR_NAME=app.jar
COPY --from=builder /app/target/*-SNAPSHOT.jar /app/${JAR_NAME}

# Spring Boot will read this as server.port
ENV SERVER_PORT=9091
EXPOSE 9091

# Healthcheck (adjust path to a known endpoint, e.g., /actuator/health if enabled)
# HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
#   CMD wget -qO- http://127.0.0.1:9080/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]
