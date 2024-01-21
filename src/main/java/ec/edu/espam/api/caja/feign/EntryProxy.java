package ec.edu.espam.api.caja.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "entryProxy", url = "https://api.publicapis.org")
public interface EntryProxy {

    @GetMapping("/entries")
    public ResponseEntryDto getResponse();
}
