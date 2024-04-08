package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonFileReaderServiceImpl implements FileReader {

    @Override
    public List<Client> readFile(MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Client> clients = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Client>>(){});
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
