package com.szells.membership.controller;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.GenericResponse;
import com.szells.membership.domain.payload.DeactivateMembershipPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.domain.payload.MembershipPayload;
import com.szells.membership.service.IMemberService;
import com.szells.membership.service.IMembershipService;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ReturnDetail;
import com.szells.util.domain.ServiceParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.szells.membership.constants.Constants.MEMBERSHIP_ID;


@Tag(description = "Publishes event for Membership related create/update/delete actions and create's a record in INBOX table! ", name = "Membership Controller")
@RestController
@RequestMapping("/core/memberships/v2")
@RequiredArgsConstructor
public class MembershipController {

    private final IMembershipService service;
    private final IMemberService memberService;

    @Operation(description = "Creates a new Membership and member", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Input Request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "500", description = "Error occurred while submitting the request!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "202", description = "the create membership request is Accepted. However success/failure of the operation" +
                    " is later determined during Asynchronous event processing and can be found out in inbox record for this request.!")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity> createMembership(@RequestHeader(Constants.X_VISIBILITY_SCOPE_KEY) String scopeKey,
                                                 @RequestHeader(value = "correlationId", required = false, defaultValue = "") String correlationId,
                                                 @RequestHeader(value = "Authorization", required = false, defaultValue = "") String authorization,
                                                 @RequestBody MembershipPayload membershipPayload) {


        return service.processCreateMembership(ServiceParameters.<MembershipPayload>builder()
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .correlationId(correlationId)
                        .visibilityScopeKey(scopeKey)
                        .authorization(authorization)
                        .build())
                .payload(RequestPayload.<MembershipPayload>builder()
                        .payload(membershipPayload)
                        .build())
                .build())
                .map(this::getResponse);

    }

    @Operation(description = "Creates a new member under an existing membership!", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Input Request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "500", description = "Error occurred while submitting the request!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "202", description = "the create member request under an existing membership is Accepted. However success/failure of the operation" +
                    " is later determined during Asynchronous event processing and can be found out in inbox record for this request.!")})
    @PostMapping(value = "/{membershipId}/members", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> createMember(@RequestHeader(Constants.X_VISIBILITY_SCOPE_KEY) String scopeKey,
                                             @RequestHeader(value = "correlationId", required = false) String correlationId,
                                             @RequestHeader(value = "Authorization", required = false) String authorization,
                                             @PathVariable(value = "membershipId", required = true) String membershipId,
                                             @RequestBody MemberProfilePayload payload) {

        return memberService.processCreateMemberEvent(ServiceParameters.<MemberProfilePayload>builder()
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .visibilityScopeKey(scopeKey)
                        .correlationId(correlationId)
                        .authorization(authorization)
                        .build())
                .parameters(RequestParameters.builder()
                        .param(MEMBERSHIP_ID, membershipId)
                        .build())
                .payload(RequestPayload.<MemberProfilePayload>builder()
                        .payload(payload)
                        .build())
                .build()
        ).map(this::getResponse);
    }

    @PostMapping(value = "/deactivate")
    public Mono<GenericResponse<Boolean>> deactivateMember(@RequestHeader(Constants.X_VISIBILITY_SCOPE_KEY) String scopeKey,
                                                           @RequestHeader(value = "correlationId", required = false) String correlationId,
                                                           @RequestHeader(value = "Authorization", required = false) String authorization,
                                                           @RequestBody DeactivateMembershipPayload payload) {
        ServiceParameters<DeactivateMembershipPayload> build = ServiceParameters.<DeactivateMembershipPayload>builder()
                .headers(com.szells.util.domain.RequestHeader.builder()
                        .visibilityScopeKey(scopeKey)
                        .correlationId(correlationId)
                        .authorization(authorization)
                        .build())
                .payload(RequestPayload.<DeactivateMembershipPayload>builder().payload(payload).build())
                .build();
        return service.deactivateMembershipEvent(build).map(aBoolean ->
                new GenericResponse<>(aBoolean, HttpStatus.OK.value(), "Your Enrollment has been cancelled.", null, aBoolean))
                .switchIfEmpty(Mono.just(new GenericResponse<>(false, HttpStatus.OK.value(), "Sorry we cannot complete this action", null, false)));
    }


    private ResponseEntity getResponse(Boolean isProcessed) {
        return isProcessed ? ResponseEntity.accepted().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
