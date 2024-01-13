package ec.edu.espam.api.caja.service;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.dto.AccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAll();

    AccountDto create(AccountDto cuenta);
}
