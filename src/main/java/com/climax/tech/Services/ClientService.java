package com.climax.tech.Services;

import com.climax.tech.Entities.Client;
import com.climax.tech.Repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
     return clientRepository.findAll();
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public void updateClient(Long id, Client newClientData) {
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (existingClientOptional.isPresent()) {
            Client existingClient = existingClientOptional.get();
            existingClient.setPrenom(newClientData.getPrenom());
            existingClient.setNom(newClientData.getNom());
            existingClient.setAge(newClientData.getAge());
            existingClient.setProfession(newClientData.getProfession());
            existingClient.setSalaire(newClientData.getSalaire());
            clientRepository.save(existingClient);
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }


    public void save(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

        public double calculateAverageSalaryByProfession(String profession) {
            List<Client> clients = clientRepository.findByProfession(profession);
            if (clients.isEmpty()) {
                throw new RuntimeException("Aucun client trouvé avec la profession spécifiée");
            }
            double totalSalaire = 0;
            for (Client client : clients) {
                totalSalaire += client.getSalaire();
            }
            return totalSalaire / clients.size();
        }

}
