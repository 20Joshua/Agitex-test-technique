package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextFileReaderServiceImpl implements FileReader {

    @Override
    public List<Client> readFile(MultipartFile file) {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 5) {
                    String nom = values[0].trim();
                    String prenom = values[1].trim();
                    int age = Integer.parseInt(values[2].trim().replaceAll("[^\\d]", ""));
                    String profession = values[3].trim();
                    int salaire = Integer.parseInt(values[4].trim().replaceAll("[^\\d.]", ""));
                    clients.add(new Client(nom, prenom, age, profession, salaire));
                } else {
                    throw new RuntimeException("Format de ligne invalide : " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier", e);
        }

        return clients;
    }
}
