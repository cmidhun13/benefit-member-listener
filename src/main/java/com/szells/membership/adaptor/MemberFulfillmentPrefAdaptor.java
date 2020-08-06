package com.szells.membership.adaptor;

import com.szells.membership.domain.MemberFulfillmentPref;
import com.szells.membership.domain.MemberFulfillmentPrefPayload;
import com.szells.membership.mapper.IFulfillmentPreferenceMapper;
import com.szells.membership.repository.IMemberFulfillmentPreferenceRepository;
import com.szells.util.domain.ServiceParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MemberFulfillmentPrefAdaptor implements IMemberFulfillmentPrefAdaptor {

    private final IMemberFulfillmentPreferenceRepository repository;
    private final IFulfillmentPreferenceMapper mapper;

    @Autowired
    public MemberFulfillmentPrefAdaptor(IMemberFulfillmentPreferenceRepository repository,
                                        IFulfillmentPreferenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<MemberFulfillmentPref> findFulfillmentPref(ServiceParameters<MemberFulfillmentPrefPayload> params) {
        var memberFulfillmentPrefPayload = params.getPayload().get();

        return repository.findByMemberIdAndCommCategoryAndFulfillmentType(memberFulfillmentPrefPayload.getMemberId(),
                memberFulfillmentPrefPayload.getCommCategory(),
                memberFulfillmentPrefPayload.getFulfillmentType())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<MemberFulfillmentPref> saveFulfillmentPref(ServiceParameters<MemberFulfillmentPref> params) {
        var memberFulfillmentPrefPayload = mapper.toEntity(params.getPayload().get());

        return repository.save(memberFulfillmentPrefPayload)
                .map(mapper::toDomain);
    }
}
