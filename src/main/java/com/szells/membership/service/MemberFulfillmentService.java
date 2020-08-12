package com.szells.membership.service;

import com.szells.membership.adaptor.IMemberFulfillmentPrefAdaptor;
import com.szells.membership.model.MemberFulfillmentPref;
import com.szells.membership.model.MemberFulfillmentPrefPayload;
import com.szells.membership.mapper.IFulfillmentPreferenceMapper;
import com.szells.membership.util.MembershipUtil;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Service
public class MemberFulfillmentService implements IMemberFulfillmentService {

    boolean isH2NGFlow = true;
    private final IMemberFulfillmentPrefAdaptor adaptor;
    private final IFulfillmentPreferenceMapper mapper;

    @Autowired
    public MemberFulfillmentService(IMemberFulfillmentPrefAdaptor adaptor,
                                    IFulfillmentPreferenceMapper mapper) {
        this.adaptor = adaptor;
        this.mapper = mapper;
    }

    @Override
    public Disposable updateMemberFulfillmentPreference(ServiceParameters<MemberFulfillmentPrefPayload> params) {
        if (isH2NGFlow) {
            return adaptor.findFulfillmentPref(params)
                    .switchIfEmpty(Mono.just(MembershipUtil.buildDefaultPref(params.getPayload().get())))
                    .map(pref -> setPrefUpdate(pref, params.getPayload().get()))
                    .flatMap(adaptor::saveFulfillmentPref)
                    .subscribe();
        }
        return adaptor.findFulfillmentPref(params)
                .switchIfEmpty(Mono.just(MembershipUtil.buildDefaultPref(params.getPayload().get())))
                .map(pref -> setPrefUpdate(pref, params.getPayload().get()))
                .flatMap(adaptor::saveFulfillmentPref)
                .subscribe();
    }

    private ServiceParameters<MemberFulfillmentPref> setPrefUpdate(MemberFulfillmentPref pref,
                                                                   MemberFulfillmentPrefPayload payload) {
        mapper.toDomain(pref, payload);
        return ServiceParameters.<MemberFulfillmentPref>builder()
                .payload(RequestPayload.<MemberFulfillmentPref>builder()
                        .payload(pref)
                        .build())
                .build();
    }
}
