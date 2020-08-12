package com.szells.membership.service;

import com.szells.membership.constants.Constants;
import com.szells.membership.model.MemberDomain;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.model.request.MemberSubscriptionHistory;
import com.szells.membership.model.request.MetaData;
import com.szells.membership.mapper.IAuditBeanMapper;
import com.szells.membership.mapper.IMembershipMapper;
import com.szells.membership.producer.IEventPublisher;
import com.szells.membership.util.MembershipUtil;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HistoryService implements IHistoryService {

    private final IAuditBeanMapper mapper;
    private final IEventPublisher producer;
    private final IMembershipMapper membershipMapper;

    @Autowired
    public HistoryService(IAuditBeanMapper mapper, IEventPublisher producer, IMembershipMapper membershipMapper) {
        this.mapper = mapper;
        this.producer = producer;
        this.membershipMapper = membershipMapper;
    }

    @Override
    @Async
    public void publishForCreateMembership(MembershipPayload membershipPayload) {
        MetaData metaData = MetaData.prepareMetaData(mapper.toRequest(membershipPayload.getMembers().get(0).getAudit()),
                Constants.MEMBER_SUBSCRIPTION_HISTORY, Constants.BATCH);

        Map<String, Object> data = new HashMap<>();
        data.put("membership",
                MembershipUtil.convertObjectToMap(membershipMapper.toEntity(membershipPayload), membershipPayload.getId()));

        MemberSubscriptionHistory history = MemberSubscriptionHistory.builder()
                .data(data)
                .metaData(metaData)
                .correlationId(membershipPayload.getCorrelationId())
                .build();

        ServiceParameters<MemberSubscriptionHistory> parameters = ServiceParameters.<MemberSubscriptionHistory>builder()
                .payload(RequestPayload.<MemberSubscriptionHistory>builder()
                        .payload(history)
                        .build())
                .build();

        producer.sendMessage(parameters);

    }

    @Override
    public void publishForMember(MemberDomain memberDomain) {

    }

    public void publishForCreateMembership(MemberProfilePayload memberPayload) {
        MetaData metaData = MetaData.prepareMetaData(mapper.toRequest(memberPayload.getAudit()),
                Constants.MEMBER_SUBSCRIPTION_HISTORY, Constants.BATCH);

        Map<String, Object> data = new HashMap<>();
        data.put("update_member",
                MembershipUtil.convertObjectToMap(memberPayload, Long.parseLong(memberPayload.getId())));

        MemberSubscriptionHistory history = MemberSubscriptionHistory.builder()
                .data(data)
                .metaData(metaData)
                .correlationId(memberPayload.getCorrelationId())
                .build();

        ServiceParameters<MemberSubscriptionHistory> parameters = ServiceParameters.<MemberSubscriptionHistory>builder()
                .payload(RequestPayload.<MemberSubscriptionHistory>builder()
                        .payload(history)
                        .build())
                .build();

        producer.sendMessage(parameters);

    }
}
