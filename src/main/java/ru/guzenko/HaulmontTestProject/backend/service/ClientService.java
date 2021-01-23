package ru.guzenko.HaulmontTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll(Long bankId) {
        return clientRepository.findAllByBank_Id(bankId);
    }

    public List<Client> findAll(String filterText, Long bankId) {
        if (filterText == null || filterText.isEmpty()) {
            return clientRepository.findAll();
        } else {
            return clientRepository.search(filterText/*, bankId*/);
        }
    }


    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }
}
