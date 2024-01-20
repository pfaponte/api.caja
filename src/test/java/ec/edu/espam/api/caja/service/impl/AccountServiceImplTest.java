package ec.edu.espam.api.caja.service.impl;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.dto.AccountDto;
import ec.edu.espam.api.caja.repository.AccountRepository;
import ec.edu.espam.api.caja.repository.MovementRepository;
import ec.edu.espam.api.caja.service.AccountService;
import ec.edu.espam.api.caja.service.ClientService;
import ec.edu.espam.api.caja.service.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    private AccountService accountService;
    private AccountRepository accountRepositoryMock;

    @BeforeEach
    void setup() {
        this.accountRepositoryMock = Mockito.mock(AccountRepository.class);
        MovementService movementServiceMock = Mockito.mock(MovementService.class);
        ClientService clientServiceMock = Mockito.mock(ClientService.class);
        this.accountService = new AccountServiceImpl(this.accountRepositoryMock, clientServiceMock, movementServiceMock);
    }

    @Test
    void getById() {
        Account account = new Account();
        account.setId(1l);
        account.setNumber("12313243");

        Mockito.when(this.accountRepositoryMock.findById(1l)).thenReturn(Optional.of(account));
        AccountDto dto = this.accountService.getById(1l);

        assertNotNull(dto);
        assertEquals(1l, dto.getId());
    }
}
