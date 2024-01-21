package ec.edu.espam.api.caja.controller;

import ec.edu.espam.api.caja.domain.dto.ReportDto;
import ec.edu.espam.api.caja.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportDto>> getByDateRange(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
            @RequestParam(name = "clientId") Long clientId) {
        return ResponseEntity.ok(reportService.getByDateRange(startDate, endDate, clientId));
    }
}
