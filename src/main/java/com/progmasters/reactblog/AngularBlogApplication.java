package com.progmasters.reactblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AngularBlogApplication {

    //TODO Review - Mivel kezd eléggé dagadni az alkalmazás, el lehetne gondolkozni a
    // package-by-feature elrendezésben ( google: package-by-feature Vs. package-by-layer)

    //TODO Review - Érdemes mindenkinek átfutni a code style beállításokat,
    // és ott tweakelni kicsit a beállítást, esetleg megegyezni valami közös configban,
    // Majd ezt commit előtt mindig futtatgatni
    // ( CTRL + ALT + L -t ki lehet adni akár egész packagekre is)
    // Brutál sok hasznos opció van, csoportosítja a getter/settereket, methodokat, fieldeket,
    // javítja az indentálást, kitörli ha 1nél több üres sor van egymás után stb. stb.
    // Ezekkel sokat tudnak segíteni, hogy a kód formailag egységes legyen projekt szinten
    // Ha egy valaki megcsinálja a konfigot, az utánna ezt tudja exportálni/megosztani a többiekkel!
    public static void main(String[] args) {
        SpringApplication.run(AngularBlogApplication.class, args);
    }

}
