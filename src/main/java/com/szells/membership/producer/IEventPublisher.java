package com.szells.membership.producer;

import com.szells.membership.domain.payload.BasePayload;
import com.szells.membership.domain.request.MemberMembershipOnBoardRequest;
import com.szells.membership.domain.request.MemberSubscriptionHistory;
import com.szells.membership.domain.request.ProofOfEnrollment;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IEventPublisher {
    void sendMessage(ServiceParameters<MemberSubscriptionHistory> params);

    //void sendMessage1(ServiceParameters<MemberMembershipOnBoardRequest> params);
    void sendMessage(List<ProofOfEnrollment> poe);

    Mono<Boolean> sendEvent(ServiceParameters<? extends BasePayload> payload, String topicName, String eventType, String event);
}
