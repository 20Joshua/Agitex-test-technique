package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;

import lombok.extern.slf4j.Slf4j;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocxFileReaderServiceImpl implements FileReader {

    @Override
    public List<Client> readFile(MultipartFile file) throws IOException {
        List<Client> clients = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(inputStream, handler, metadata);

            String[] lines = handler.toString().split("\\r?\\n");

            List<String[]> records = new ArrayList<>();

            for (String line : lines) {
                records.add(line.split(","));
            }

            for (String[] record : records) {
                if (record.length >= 5) {
                    String nom = record[0].trim();
                    String prenom = record[1].trim();
                    int age = Integer.parseInt(record[2].trim().replaceAll("[^\\d]", ""));
                    String profession = record[3].trim();
                    int salaire = Integer.parseInt(record[4].trim().replaceAll("[^\\d]", ""));
                    clients.add(new Client(nom, prenom, age, profession, salaire));
                } else {
                    log.info("Format de ligne invalide : " + String.join(",", record));
                }
            }
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
