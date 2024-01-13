package ec.edu.espam.api.caja.service.impl;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.Client;
import ec.edu.espam.api.caja.domain.dto.AccountDto;
import ec.edu.espam.api.caja.repository.AccountRepository;
import ec.edu.espam.api.caja.service.AccountService;
import ec.edu.espam.api.caja.service.ClientService;
import ec.edu.espam.api.caja.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ClientService clientService;

    @Override
    public List<AccountDto> getAll() {
        return repository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public AccountDto create(AccountDto dto) {
        Client client = clientService.getById(dto.getClientId());
        Account account = convertDtoToEntity(dto);
        //account.setClient(client);
        return convertEntityToDto(repository.save(account));
    }

    private AccountDto convertEntityToDto(Account account) {
        return Mapper.modelMapper().map(account, AccountDto.class);
    }

    private Account convertDtoToEntity(AccountDto dto) {
        return Mapper.modelMapper().map(dto, Account.class);
    }

}
