package com.szells.membership.controller;

import com.szells.membership.config.StorageProperties;
import com.szells.membership.constants.Constants;
import com.szells.membership.model.GenericResponse;
import com.szells.membership.model.payload.*;
import com.szells.membership.model.request.CreateUpdateMember;
import com.szells.membership.model.request.EnrollRequest;
import com.szells.membership.model.request.MemberMembershipOnBoardRequest;
import com.szells.membership.model.request.UpdateHashRequest;
import com.szells.membership.processor.MembershipPreProcessor;
import com.szells.membership.service.IFileStorageService;
import com.szells.membership.service.IMemberService;
import com.szells.util.StringOperation;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@Tag(description = "Publishes event for Member related create/update/delete actions and create's a record in INBOX table! ", name = "Member Controller")
@RestController
@RequestMapping("/core/members/v2")
@RequiredArgsConstructor
@EnableConfigurationProperties(StorageProperties.class)
public class MemberController {

    private final IMemberService memberService;
    private final IFileStorageService fileStorageService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Input Request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "500", description = "Error occurred while submitting the request!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "202", description = "the delete request is Accepted. However success/failure of the delete" +
                    " is later determined during Asynchronous event processing and can be found out in inbox record for this request.!")})
    @Operation(description = "Delete an existing member by member ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping(path = "/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> deleteByMemberId(@RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                 @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                 @PathVariable(Constants.MEMBER_ID) String memberId,
                                                 @RequestParam(name = Constants.ID_TYPE, required = false) String idType,
                                                 @RequestBody @Valid CancelRequestPayload cancelRequest) {
        ServiceParameters<CancelRequestPayload> parameters = MembershipPreProcessor
                .getBuild(correlationId, visibilityScopeKey, memberId, idType, cancelRequest);

        return memberService.processDeleteMemberById(parameters)
                .map(success -> success ? ResponseEntity.accepted() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR))
                .map(ResponseEntity.BodyBuilder::build);

    }

    @Operation(description = "Update an existing member by member id!", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Input Request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "500", description = "Error occurred while submitting the request!",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReturnDetail.class)))),
            @ApiResponse(responseCode = "202", description = "the update member request is Accepted. However success/failure of the delete" +
                    " is later determined during Asynchronous event processing and can be found out in inbox record for this request.!")})
    @PostMapping(value = "/{memberId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> updateMemberProfile(
            @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
            @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
            @PathVariable(Constants.MEMBER_ID) String memberId,
            @RequestParam(name = Constants.ID_TYPE, required = false) String idType,
            @RequestBody @Valid MemberProfilePayload payload) {
        ServiceParameters<MemberProfilePayload> parameters = MembershipPreProcessor
                .getBuild(correlationId, visibilityScopeKey, null, memberId, idType, payload);
        return memberService.processUpdateMembersProfile(parameters)
                .map(this::getResponse);
    }

    @PostMapping(value = "/activate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenericResponse<Long>> activateMemberProfile(
            @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
            @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
            @RequestBody @Valid ActivateMemberPayload payload) {
        ServiceParameters<ActivateMemberPayload> parameters = MembershipPreProcessor
                .getBuild(correlationId, visibilityScopeKey, null, null, null, payload);
        return memberService.processActivateMembersProfile(parameters)
                .switchIfEmpty(Mono.just(new GenericResponse<>(false, HttpStatus.OK.value(), "Can't be activated.", null, null)));
    }
    @PostMapping(value = "/createOrUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GenericResponse<Long>> createOrUpdateProfile(@RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                             @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                             @RequestBody @Valid CreateUpdateMember payload) {
        return memberService.createOrUpdateMemberProfile(MembershipPreProcessor.getBuild(correlationId, visibilityScopeKey, null, null, null, payload));
    }
    @PostMapping(value = "/onboard-email")
    public Mono<GenericResponse<Integer>> onboardEmail(@RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                       @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                       @RequestBody MemberMembershipOnBoardRequest payload) {
        payload.setAuthorization(authorization);
        return memberService.onBoardMemberMembershipEmail(MembershipPreProcessor.getBuild(correlationId, visibilityScopeKey, authorization, null, null, payload));

    }
    @PostMapping(value = "/onboarding")
    public Mono<GenericResponse<Boolean>> onBoarding(@RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                     @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                     @RequestBody @Valid MemberMembershipOnBoardRequest payload) {
        return memberService.onBoardMemberMership(MembershipPreProcessor.getBuild(correlationId, visibilityScopeKey, null, null, null, payload))
                .map(aBoolean ->
                        new GenericResponse<>(aBoolean, HttpStatus.OK.value(), "OnBoarded", null, aBoolean))/**/
                .switchIfEmpty(Mono.just(new GenericResponse<>(false, HttpStatus.OK.value(), "Not OnBoarded", null, false)));
    }

    @PostMapping(value = "/updateHash")
    public Mono<GenericResponse<Boolean>> updateHash(@RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                     @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                     @RequestBody @Valid UpdateHashRequest payload) {
        return memberService.updateMemberHashCode(MembershipPreProcessor.getBuild(correlationId, visibilityScopeKey, null, null, null, payload));

    }
    @PostMapping(value = "/enroll")
    public Mono<GenericResponse<Boolean>> enroll(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                 @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                 @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                 @RequestBody @Valid EnrollRequest payload) {
        return memberService.enrollMember(MembershipPreProcessor.getBuild(correlationId, visibilityScopeKey, null, null, null, payload))
                .map(aBoolean ->
                        new GenericResponse<>(aBoolean, HttpStatus.OK.value(), "You are successfully enrolled", null, aBoolean))
                .switchIfEmpty(Mono.just(new GenericResponse<>(false, HttpStatus.OK.value(), "Sorry, you are not enrolled", null, false)));
    }

    private ResponseEntity getResponse(Boolean isProcessed) {
        return isProcessed ? ResponseEntity.accepted().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


        @PostMapping(value = "/member/file-upload")
    public Mono<GenericResponse<Object>> fileUpload(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                    @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                       @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                    @RequestParam("file") MultipartFile file
                                                     ) {
            Object data = fileStorageService.store(file);
        return Mono.just(new GenericResponse<>(true, HttpStatus.OK.value(), "File uploaded successfully.", null, data));
    }
    @PostMapping(value = "/member/onboard-xls")
    public Mono<GenericResponse<String>> onboardXls(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                    @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                     @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                     @RequestBody @Valid BulkUploadMember bulkUploadMember) {
        bulkUploadMember.setAuthorization(authorization);
        ServiceParameters<BulkUploadMember> parameters = MembershipPreProcessor
                .getBuild(correlationId, visibilityScopeKey, authorization, null, null, bulkUploadMember);
        return memberService.processBulkUploadMembersProfile(parameters);
    }
    @PostMapping(value = "/member/status")
    public Mono<GenericResponse<Boolean>> membersuspend(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
                                                    @RequestHeader(name = Constants.CORRELATION_ID) String correlationId,
                                                     @RequestHeader(name = Constants.X_VISIBILITY_SCOPE_KEY) String visibilityScopeKey,
                                                    @RequestBody @Valid SuspendMemberPayload payload) {
        ServiceParameters<SuspendMemberPayload> parameters = MembershipPreProcessor
                .getBuild(correlationId, visibilityScopeKey, authorization, null, null, payload);
        return memberService.processSuspendMembersProfile(parameters);
    }
    @GetMapping(value = "/encryption")
    public GenericResponse<String> encryption(@RequestParam("memberid") String msg) throws Exception {
        String encrypt = StringOperation.encrypt(msg);
        return new GenericResponse<>(true, HttpStatus.OK.value(), "Encrypted successfully.", null, encrypt);
    }
    @GetMapping(value = "/decryption")
    public GenericResponse<String> decryption(@RequestParam("memberid") String msg) throws Exception {
        String decrypt = StringOperation.decrypt(msg);
        return new GenericResponse<>(true, HttpStatus.OK.value(), "Decrypted successfully.", null, decrypt);
    }
}
