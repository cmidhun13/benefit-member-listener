package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author Riya Patel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnBoardRequest {
    String customerId;
    String solicitationId;
    List<String> emailIds;
}
