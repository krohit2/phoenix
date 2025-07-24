package com.example.genaisummary.util;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class FileContentExtractor {

    private final Tika tika = new Tika();

    /*
    * Accept input file
    * Returns extracted texts
     */
    public String extractText(MultipartFile file) throws IOException, TikaException {
        return tika.parseToString(file.getInputStream());
    }
}
