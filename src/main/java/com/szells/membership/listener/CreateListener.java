package com.szells.membership.listener;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.payload.ServiceResponse;
import com.szells.membership.service.IMemberService;
import com.szells.membership.service.IMembershipService;
import com.szells.membership.util.MembershipUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class CreateListener {

    private final IMembershipService membershipService;
    private final IMemberService memberService;

    @KafkaListener(topics = "membership.create", containerFactory = "memberProfileCreateListenerFactory")
    public void createMembershipListener(@Payload String payload, @Headers MessageHeaders headers) {
        if (headers.containsKey(Constants.CREATE_TYPE)) {
            String createType = new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE)));

            if (Constants.CREATE_MEMBER_PROFILE.equalsIgnoreCase(createType)) {
                MembershipUtil.getMemberProfilePayload(payload)
                        .map(MembershipUtil::buildMemberProfileParams)
                        .ifPresent(member -> memberService.createMemberProfile(member)
                                .subscribe(this::publish));
            } else if (Constants.CREATE_MEMBERSHIP.equalsIgnoreCase(createType)) {
                MembershipUtil.getMembershipPayload(payload)
                        .map(MembershipUtil::buildMembershipParams)
                        .ifPresent(membership -> membershipService.createMembership(membership)
                                .subscribe(this::publish));
            }
        }
    }

    private void publish(ServiceResponse memberProfileResponse) {
        // handle inbox
        log.info("Message to post to inbox :" + memberProfileResponse.getNoOfRows());
    }
}
