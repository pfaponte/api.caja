package ec.edu.espam.api.caja.service;

import ec.edu.espam.api.caja.domain.dto.ReportDto;

import java.util.Date;
import java.util.List;

public interface ReportService {
    List<ReportDto> getByDateRange(Date startDate, Date endDate, Long clientId);
}
