package ec.edu.espam.api.caja.feign;

import lombok.Data;

import java.util.List;

@Data
public class ResponseEntryDto {
    private Integer count;
    private List<EntryDto> entries;
}
