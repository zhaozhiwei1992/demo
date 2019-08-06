package com.example.config;

public interface CustomConstants {
    // Spring profiles for development, test and production, see https://www.jhipster.tech/profiles/
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    String SPRING_PROFILE_TEST = "test";
    String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used when deploying to Amazon ECS
    String SPRING_PROFILE_AWS_ECS = "aws-ecs";
    // Spring profile used to enable swagger
    String SPRING_PROFILE_SWAGGER = "swagger";
    // Spring profile used to disable running liquibase
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
    // Spring profile used when deploying to Kubernetes and OpenShift
    String SPRING_PROFILE_K8S = "k8s";
}
