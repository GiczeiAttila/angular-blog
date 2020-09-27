package com.progmasters.reactblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AngularBlogApplication {

    //TODO Review - Mivel kezd eléggé dagadni az alkalmazás, el lehetne gondolkozni a
    // package-by-feature elrendezésben ( google: package-by-feature Vs. package-by-layer)

    public static void main(String[] args) {
        SpringApplication.run(AngularBlogApplication.class, args);
    }

}
