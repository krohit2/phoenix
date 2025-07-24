package com.example.genaisummary.service;

import com.example.genaisummary.util.FileContentExtractor;
import com.example.genaisummary.config.GeminiConfig;
import com.example.genaisummary.entity.InputFile;
import com.example.genaisummary.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SummaryService {

    @Autowired
    private FileContentExtractor fileContentExtractor;

    @Autowired
    private GeminiConfig geminiConfig;

    @Autowired
    private FileRepository fileRepository;

    /*
     * Service to wrap extracted text and provide summary
     * Saves data to database
     */
    public String processFileAndSummarize(MultipartFile file) throws Exception {
        String extractedText = fileContentExtractor.extractText(file);
        String summary = geminiConfig.callGeminiForSummary(extractedText);

        InputFile inputFile = new InputFile();
        inputFile.setOriginalFilename(file.getOriginalFilename());
        inputFile.setContentType(file.getContentType());
        inputFile.setExtractedText(extractedText);
        inputFile.setSummary(summary);

        fileRepository.save(inputFile);

        return summary;
    }

}
