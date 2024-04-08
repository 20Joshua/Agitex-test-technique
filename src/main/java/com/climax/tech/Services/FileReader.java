package com.climax.tech.Services;

import com.climax.tech.Entities.Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<Client> readFile(MultipartFile file) throws IOException;
}
