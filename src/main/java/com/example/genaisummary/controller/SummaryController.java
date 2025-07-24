package com.example.genaisummary.controller;

import com.example.genaisummary.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/summarize")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    /*
    * Post method
    * Accepts input file
    * Returns JSON
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> summarize(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response=null;
        try {
            String summary = summaryService.processFileAndSummarize(file);
             response = Collections.singletonMap("summary", summary);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
