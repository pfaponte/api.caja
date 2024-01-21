package ec.edu.espam.api.caja.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class EntryDto {
    @JsonProperty("API")
    private String api;
    private String description;
    private String auth;
    @JsonProperty("HTTPS")
    private Boolean https;
    private String cors;
    private String link;
    private String category;
}
