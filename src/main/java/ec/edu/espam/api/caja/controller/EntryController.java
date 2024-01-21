package ec.edu.espam.api.caja.controller;

import ec.edu.espam.api.caja.feign.EntryDto;
import ec.edu.espam.api.caja.feign.EntryProxy;
import ec.edu.espam.api.caja.feign.ResponseEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entries")
public class EntryController {

    private final EntryProxy entryProxy;

    @GetMapping
    public ResponseEntity<List<EntryDto>> getEntries(
            @RequestParam(name = "category") String category) {
        List<EntryDto> entries = entryProxy.getResponse()
                .getEntries()
                .stream()
                .filter(entryDto -> entryDto.getCategory().equals(category))
                .toList();

        return ResponseEntity.ok(entries);
    }

    @GetMapping("/rest-template")
    public ResponseEntity<List<EntryDto>> getEntriesByRestTemplate(
            @RequestParam(name = "category") String category) throws URISyntaxException {

        var uri = new URI("https://api.publicapis.org/entries");
        var restTemplate = new RestTemplate();
        var responseType = new ParameterizedTypeReference<ResponseEntryDto>() {};
        var response = restTemplate.exchange(uri, HttpMethod.GET, null, responseType);

        List<EntryDto> entries = Objects.requireNonNull(response.getBody())
                .getEntries()
                .stream()
                .filter(entryDto -> entryDto.getCategory().equals(category))
                .toList();

        return ResponseEntity.ok(entries);

    }
}
