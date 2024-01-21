package ec.edu.espam.api.caja.service.impl;

import ec.edu.espam.api.caja.domain.dto.ReportDto;
import ec.edu.espam.api.caja.repository.DaoRepository;
import ec.edu.espam.api.caja.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final DaoRepository daoRepository;


    @Override
    public List<ReportDto> getByDateRange(Date startDate, Date endDate, Long clientId) {
        List<Object[]> objects = daoRepository.getReport(startDate, endDate, clientId);
        List<ReportDto> reports = new ArrayList<>();
        for (Object[] object : objects) {
            reports.add(new ReportDto(object));
        }

        return reports;
    }
}
