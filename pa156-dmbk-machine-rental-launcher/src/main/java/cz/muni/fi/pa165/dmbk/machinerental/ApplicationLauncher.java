package cz.muni.fi.pa165.dmbk.machinerental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This class is entry point of Spring Boot application.
 * Its place where configuration starts after application
 * is deployed on server. It scans for package prefix
 * <b>cz.muni.fi.pa165.dmbk.machinerental</b> which is used by
 * all <i>pa156-dmbk-machine-rental</i> modules, to register all
 * modules functionality.
 */
@SpringBootApplication
public class ApplicationLauncher extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationLauncher.class);
    }

} // ApplicationLauncher
