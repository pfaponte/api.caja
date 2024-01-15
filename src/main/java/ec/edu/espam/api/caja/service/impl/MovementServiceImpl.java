package ec.edu.espam.api.caja.service.impl;

import ec.edu.espam.api.caja.domain.Account;
import ec.edu.espam.api.caja.domain.Movement;
import ec.edu.espam.api.caja.domain.dto.MovementDto;
import ec.edu.espam.api.caja.exceptions.EntityNotFoundException;
import ec.edu.espam.api.caja.exceptions.PreconditionFailedException;
import ec.edu.espam.api.caja.repository.MovementRepository;
import ec.edu.espam.api.caja.service.AccountService;
import ec.edu.espam.api.caja.service.MovementService;
import ec.edu.espam.api.caja.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository repository;

    @Override
    public MovementDto create(MovementDto dto) {
        validateDebit(dto);

        if (Movement.Type.DEBIT.compareTo(dto.getType()) == 0) {
            dto.setAmount(dto.getAmount().multiply(new BigDecimal(-1)));
        }

        Movement movement = convertDtoToEntity(dto);
        return convertEntityToDto(repository.save(movement));
    }

    private void validateDebit(MovementDto dto) {
        BigDecimal balance = BigDecimal.ZERO;
        Optional<Movement> optional = repository.findByAccountOrderByDateDesc(dto.getAccountId());
        if (optional.isPresent()) {
            balance = optional.get().getBalance();
        }

        if (BigDecimal.ZERO.compareTo(balance) <= 0 || balance.compareTo(dto.getAmount()) < 0) {
            throw new PreconditionFailedException("Balance not available");
        }




    }

    @Override
    public MovementDto update(Long id, MovementDto dto) {
        Movement movement = getEntityById(id);
        movement.setDate(dto.getDate());
        return convertEntityToDto(repository.save(movement));
    }

    @Override
    public void delete(Long id) {
        Movement movement = getEntityById(id);
        repository.delete(movement);
    }

    @Override
    public MovementDto getById(Long id) {
        return convertEntityToDto(getEntityById(id));
    }

    @Override
    public List<MovementDto> getAll(Long accountId) {
        if (accountId == null) {
            return repository.findAll().stream().map(this::convertEntityToDto).toList();
        } else {
            //Account account = Mapper.modelMapper().map(accountService.getById(accountId), Account.class);
            return repository.findByAccountId(accountId).stream().map(this::convertEntityToDto).toList();
        }
    }

    private Movement getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movement not found"));
    }

    private MovementDto convertEntityToDto(Movement entity) {
        return Mapper.modelMapper().map(entity, MovementDto.class);
    }

    private Movement convertDtoToEntity(MovementDto dto) {
        return Mapper.modelMapper().map(dto, Movement.class);
    }

}
