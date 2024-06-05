package com.spoonacular.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The ApplicationStarter class is the entry point of the Spring Boot application.
 * The class is annotated with @SpringBootApplication, indicating that it's a Spring Boot application.
 * The @SpringBootApplication annotation is a convenience annotation that adds all of the following:
 * - @Configuration: Tags the class as a source of bean definitions for the application context.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'com.spoonacular.api' package, allowing it to find and register the controllers.
 * The class includes a main method which is the entry point of the Java application.
 * The main method delegates to Spring Boot’s SpringApplication class by calling run.
 * SpringApplication bootstraps the application, starting Spring which will then start the auto-configured Tomcat web server.
 * The SpringApplication.run method returns an ApplicationContext where all the beans that were created can be looked up.
 */
@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
