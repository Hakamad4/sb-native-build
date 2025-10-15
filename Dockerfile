FROM ghcr.io/graalvm/graalvm-community:21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw -Pnative -DskipTests package -T4

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/ /app/app
RUN chmod +x /app/app

ENTRYPOINT ["./app/sb-native-build"]