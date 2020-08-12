package com.szells.membership.adaptor;

import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.entity.Membership;
import com.szells.membership.exception.InvalidMembershipIdException;
import com.szells.membership.mapper.IMembershipMapper;
import com.szells.membership.repository.IMembershipRepository;
import com.szells.util.domain.ServiceParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.szells.membership.constants.Constants.*;

@Service
public class MembershipAdaptor implements IMembershipAdaptor {

    private final IMembershipRepository repository;
    private final IMembershipMapper mapper;

    @Autowired
    public MembershipAdaptor(IMembershipRepository repository, IMembershipMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Long> createMembership(MembershipPayload membership) {
        return repository.save(mapper.toEntity(membership))
                .map(Membership::getMembershipId);
    }

    @Override
    public Mono<Integer> deactivateMembership(ServiceParameters<String> parameters) {
        return repository.updateMembershipStatus(Long.parseLong(parameters.getParameters().getParam(MEMBERSHIP_ID)),
                false, SUSPEND);
    }

    @Override
    public Mono<Integer> activateMemberShip(ServiceParameters<String> parameters) {
        return repository.updateMembershipStatus(Long.parseLong(parameters.getParameters().getParam(MEMBERSHIP_ID)),
                true, ENROLLED);
    }

    @Override
    public Mono<Integer> suspendMemberShip(String status, Boolean isActive, String membershipNumber,Integer customerId ) {
        return repository.updateMembershipStatus(status,isActive,membershipNumber, customerId);
    }

    @Override
    public Mono<Boolean> checkIfMembershipExists(ServiceParameters<String> parameters) {
        return repository.findById(Long.parseLong(parameters.getParameters().getParam(MEMBERSHIP_ID)))
                .switchIfEmpty(Mono.error(new InvalidMembershipIdException(parameters.getHeaders().getCorrelationId())))
                .map(membership -> true);
    }

    @Override
    public Mono<Long> getMemberShipIdByMemberIdBySolicitationId(Long memberId, Integer solicitationId) {
        return repository.queryMemberShipIdByMemberIdBySolicitationId(memberId, solicitationId);
    }
}
