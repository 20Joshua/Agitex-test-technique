package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfFileReaderServiceImpl implements FileReader {
    @Override
    public List<Client> readFile(MultipartFile file) {
        List<Client> clients = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            String regex = "Nom:\\s*(\\w+)\\s*Prénom:\\s*(\\w+)\\s*Age:\\s*(\\d+)\\s*Profession:\\s*(\\w+)\\s*Salaire:\\s*(\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {

                String nom = matcher.group(1);
                String prenom = matcher.group(2);
                int age = Integer.parseInt(matcher.group(3));
                String profession = matcher.group(4);
                int salaire = Integer.parseInt(matcher.group(5));

                clients.add(new Client(nom, prenom, age, profession, salaire));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
