package ec.edu.espam.api.caja.service.impl;

import ec.edu.espam.api.caja.domain.Client;
import ec.edu.espam.api.caja.domain.dto.ClientDto;
import ec.edu.espam.api.caja.repository.ClientRepository;
import ec.edu.espam.api.caja.service.ClientService;
import ec.edu.espam.api.caja.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public ClientDto create(ClientDto dto) {
        Client client = convertDtoToEntity(dto);
        return convertEntityToDto(this.repository.save(client));
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Client Not found"));
    }

    private ClientDto convertEntityToDto(Client client) {
        return Mapper.modelMapper().map(client, ClientDto.class);
    }

    private Client convertDtoToEntity(ClientDto dto) {
        return Mapper.modelMapper().map(dto, Client.class);
    }
}
