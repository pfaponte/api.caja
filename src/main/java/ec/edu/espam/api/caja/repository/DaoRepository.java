package ec.edu.espam.api.caja.repository;

import java.util.Date;
import java.util.List;

public interface DaoRepository {
    List<Object[]> getReport(Date startDate, Date endDate, Long clientId);
}
