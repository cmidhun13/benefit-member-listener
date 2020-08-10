package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.membership.exception.MemberSaveException;
import com.szells.membership.repository.ISvcChannelAccntRepository;
import com.szells.util.domain.ServiceParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SvcChannelAccntAdaptor implements ISvcChannelAccntAdaptor {

    private final ISvcChannelAccntRepository repository;

    @Override
    public Mono<Integer> deactivateForMemberId(ServiceParameters<String> parameters) {
        return repository.updateStatusForMemberId(Long.parseLong(parameters.getParameters().getParam(Constants.MEMBER_ID)),
                Long.parseLong(parameters.getHeaders().getVisibilityScopeId()), false, parameters.getHeaders().getCorrelationId())
                .switchIfEmpty(Mono.error(new MemberSaveException(parameters.getHeaders().getCorrelationId())));
    }
}
