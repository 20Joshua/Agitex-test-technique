package com.climax.tech.Controllers;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.ClientService;
import com.climax.tech.Services.FileReader;
import com.climax.tech.Services.FileReaderFactory;
import com.climax.tech.Services.RapportService;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final FileReaderFactory fileReaderFactory;
    private final List<FileReader> strategy;
    private final RapportService rapportService;

    public ClientController(
            ClientService clientService,
            FileReaderFactory fileReaderFactory,
            List<FileReader> strategy,
            RapportService rapportService) {
        this.clientService = clientService;
        this.fileReaderFactory = fileReaderFactory;
        this.strategy = strategy;
        this.rapportService = rapportService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam MultipartFile file, @RequestParam String fileType) throws IOException {

            FileReader strategy = fileReaderFactory.getStrategy(fileType);
            List<Client> clients = strategy.readFile(file);
            clientService.save(clients);

        return ResponseEntity.ok("Fichier traité avec succès et données clients enregistrées dans la base de données.");
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<Resource> generateClientListStatetoPdf() throws BadRequestException {
        String path = "/reportDataJrxml/ClientsList.jrxml";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        org.springframework.core.io.Resource report = rapportService.generateClientsListtoPDF(path);
        return ResponseEntity.ok().headers(headers).body(report);
    }

    @GetMapping("/export-excel")
    public ResponseEntity<Resource> generateClientListStatetoExcel() throws BadRequestException {
        String path = "/reportDataJrxml/ClientsList.jrxml";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] report = rapportService.generateClientsListToExcel(path);
        headers.setContentDispositionFormData("attachment", "ClientsList.xlsx");
        Resource resource = new ByteArrayResource(report);
        return ResponseEntity.ok().headers(headers).body(resource);
    }


    @GetMapping("/getAll")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody Client client) {
        clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/moyenne-salaires")
    public ResponseEntity<String> getAverageSalaryByProfession(@RequestParam String profession) {
        double moyenneSalaire = clientService.calculateAverageSalaryByProfession(profession);
        return ResponseEntity.ok().body("la moyenne des salaires en K€ pour un " +profession+ " est de " +moyenneSalaire);
    }

}
