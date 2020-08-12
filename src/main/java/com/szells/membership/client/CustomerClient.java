package com.szells.membership.client;

import com.szells.membership.model.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Riya Patel
 */
@FeignClient(name = "CustomerFeignClient", url = "${member.customerService.url}")
public interface CustomerClient {

    @PostMapping(path="${member.customerService.solicitationUrl}")
    GenericResponse getSolicitation(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody String query
    );
    @PostMapping(path="${member.customerService.customerUrl}")
    ResponseEntity<Object> getCustomer(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                       @RequestBody String query);
}