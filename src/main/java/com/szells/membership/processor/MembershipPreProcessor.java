package com.szells.membership.processor;

import com.szells.membership.constants.Constants;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MembershipPreProcessor implements IMembershipPreProcessor {
    public static ServiceParameters<CancelRequestPayload> getBuild(String correlationId,
                                                                   String visibilityScopeKey,
                                                                   String memberId,
                                                                   String idType,
                                                                   CancelRequestPayload cancelRequest) {

        AtomicLong visibilityScopeId = new AtomicLong(-1L);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(visibilityScopeKey)
                .map(VisibilityScopeData::getVisbilityScopeId)
                .ifPresent(visibilityScopeId::set);
        return ServiceParameters.<CancelRequestPayload>builder()
                .payload(RequestPayload.<CancelRequestPayload>builder()
                        .payload(cancelRequest)
                        .build())
                .parameters(RequestParameters.builder()
                        .param(Constants.MEMBER_ID, memberId)
                        .param(Constants.ID_TYPE, idType)
                        .build())
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .visibilityScopeKey(visibilityScopeKey)
                        .visibilityScopeId(String.valueOf(visibilityScopeId.get()))
                        .correlationId(correlationId)
                        .build())
                .build();
    }

    public static ServiceParameters<MemberProfilePayload> getBuild(String correlationId,
                                                                   String visibilityScopeKey,
                                                                   String authorization,
                                                                   String memberId,
                                                                   String idType,
                                                                   MemberProfilePayload payload) {
        AtomicLong visibilityScopeId = new AtomicLong(-1);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(visibilityScopeKey)
                .map(VisibilityScopeData::getVisbilityScopeId)
                .ifPresent(visibilityScopeId::set);
        payload.setVisibilityScope(visibilityScopeId.get());
        payload.setVisibilityScopeKey(visibilityScopeKey);

        return ServiceParameters.<MemberProfilePayload>builder()
                .payload(RequestPayload.<MemberProfilePayload>builder()
                        .payload(payload)
                        .build())
                .parameters(RequestParameters.builder()
                        .param(Constants.MEMBER_ID, memberId)
                        .param(Constants.ID_TYPE, idType)
                        .build())
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .visibilityScopeKey(visibilityScopeKey)
                        .authorization(authorization)
                        .visibilityScopeId(String.valueOf(visibilityScopeId.get()))
                        .correlationId(correlationId)
                        .build())
                .build();
    }

    public static <T> ServiceParameters<T> getBuild(String correlationId,
                                                    String visibilityScopeKey,
                                                    String authorization,
                                                    String memberId,
                                                    String idType,
                                                    T payload) {
        AtomicLong visibilityScopeId = new AtomicLong(-1);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(visibilityScopeKey)
                .map(VisibilityScopeData::getVisbilityScopeId)
                .ifPresent(visibilityScopeId::set);

        return ServiceParameters.<T>builder()
                .payload(RequestPayload.<T>builder()
                        .payload(payload)
                        .build())
                .parameters(RequestParameters.builder()
                        .param(Constants.MEMBER_ID, memberId)
                        .param(Constants.ID_TYPE, idType)
                        .build())
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .visibilityScopeKey(visibilityScopeKey)
                        .authorization(authorization)
                        .visibilityScopeId(String.valueOf(visibilityScopeId.get()))
                        .correlationId(correlationId)
                        .build())
                .build();
    }

    @Override
    public ServiceParameters<CancelRequestPayload> buildCancelMemberParams(CancelRequestPayload cancelRequestPayload) {
        AtomicReference<String> visibilityScopeId = new AtomicReference<>("-1");
        VisibilityScopeCache.getInstance().getCacheEntry(cancelRequestPayload.getVisibilityScopeKey())
                .map(VisibilityScopeData::getVisbilityScopeId)
                .ifPresent(id -> visibilityScopeId.set(id.toString()));
        return ServiceParameters.<CancelRequestPayload>builder()
                .headers(RequestHeader.builder()
                        .correlationId(cancelRequestPayload.getCorrelationId())
                        .visibilityScopeKey(cancelRequestPayload.getVisibilityScopeKey())
                        .visibilityScopeId(visibilityScopeId.get())
                        .build())
                .payload(RequestPayload.<CancelRequestPayload>builder()
                        .payload(cancelRequestPayload)
                        .build())
                .build();
    }

    @Override
    public ServiceParameters<CancelRequestPayload> addMemberId(ServiceParameters<CancelRequestPayload> parameters, Long memberId) {
        parameters.getPayload().get().setCorrelationId(parameters.getHeaders().getCorrelationId());
        parameters.getPayload().get().setVisibilityScopeKey(parameters.getHeaders().getVisibilityScopeKey());
        parameters.getPayload().get().setMemberId(memberId);
        return ServiceParameters.<CancelRequestPayload>builder()
                .headers(parameters.getHeaders())
                .payload(RequestPayload.<CancelRequestPayload>builder()
                        .payload(parameters.getPayload().get())
                        .build())
                .build();
    }
}
