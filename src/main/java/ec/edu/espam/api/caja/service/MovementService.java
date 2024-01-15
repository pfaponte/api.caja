package ec.edu.espam.api.caja.service;

import ec.edu.espam.api.caja.domain.dto.MovementDto;

import java.util.List;

public interface MovementService {
    MovementDto create(MovementDto dto);

    MovementDto update(Long id, MovementDto dto);

    void delete(Long id);

    MovementDto getById(Long id);

    List<MovementDto> getAll(Long accountId);

}
