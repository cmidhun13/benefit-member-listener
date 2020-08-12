package com.szells.membership.service;

import com.szells.membership.client.CustomerClient;
import com.szells.membership.model.GenericResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Riya Patel
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerClient customerClient;
    public GenericResponse getSolictiation(String authorization,String query){
        try{
            return customerClient.getSolicitation(authorization,query);
        }catch(FeignException ex){
            throw ex;

        }

        }
    public ResponseEntity<Object> getCustomer(String authorization,String query) {
        try {
            return customerClient.getCustomer(authorization, query);
        } catch (FeignException ex) {
            throw ex;

        }
    }

}
