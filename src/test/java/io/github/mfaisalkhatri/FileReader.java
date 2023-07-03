package io.github.mfaisalkhatri;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileReader {
    public File fileToUpload(String fileName) {
        URL resource = getClass().getClassLoader()
                .getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new Error("File not found!!");
            }
        }

    }

}
