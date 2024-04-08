package com.climax.tech.Services;

import com.climax.tech.Services.Impl.*;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FileReaderFactory {
    private final Map<String, FileReader> strategies;

    public  FileReaderFactory (

            CsvFileReaderServiceImpl csvReader,
            JsonFileReaderServiceImpl jsonReader,
            TextFileReaderServiceImpl txtReader,
            XmlFileReaderServiceImpl xmlReader,
            PdfFileReaderServiceImpl pdfReader,
            XlsxFileReaderServiceImpl xlsxReader,
            DocxFileReaderServiceImpl docxReader )
    {
        strategies = new HashMap<>();
        strategies.put("csv", csvReader);
        strategies.put("json", jsonReader);
        strategies.put("txt", txtReader);
        strategies.put("xml", xmlReader);
        strategies.put("pdf", pdfReader);
        strategies.put("xlsx", xlsxReader);
        strategies.put("docx", docxReader);
    }

    public FileReader getStrategy(String fileType) {
        return strategies.get(fileType.toLowerCase());
    }
}
