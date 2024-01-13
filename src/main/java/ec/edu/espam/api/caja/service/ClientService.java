package ec.edu.espam.api.caja.service;

import ec.edu.espam.api.caja.domain.Client;
import ec.edu.espam.api.caja.domain.dto.ClientDto;

public interface ClientService {
    ClientDto create(ClientDto client);

    Client getById(Long id);
}
