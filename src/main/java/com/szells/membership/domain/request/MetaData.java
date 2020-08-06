package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MetaData {
    @JsonProperty(value = "migrated_flag")
    private final boolean migratedFlag;
    @JsonProperty(value = "user_case")
    private final String userCase;
    @JsonProperty(value = "source_system")
    private final String sourceSystem;
    @JsonProperty(value = "source_subsystem")
    private final String sourceSubSystem;
    @JsonProperty(value = "version_number")
    private final String versionNumber;
    @JsonProperty(value = "limited_disclosure")
    private final boolean limitedDisclosure;
    @JsonProperty(value = "tier_change_to_solicitation")
    private final String tierChangeToSolicitation;
    @JsonProperty(value = "tier_change_from_solicitation")
    private final String tierChangeFromSolicitation;
    @JsonProperty(value = "tier_change_to_package")
    private final String tierChangeToPackage;
    @JsonProperty(value = "tier_change_from_package")
    private final String tierChangeFromPackage;
    private final AuditBean audit;

    public static MetaData prepareMetaData(AuditBean audit, String userCase, String sourceSystem) {
        return MetaData.builder()
                .migratedFlag(true)
                .userCase(userCase)
                .sourceSystem(sourceSystem)
                .sourceSubSystem("benefit_history")
                .limitedDisclosure(true)
                .audit(audit)
                .build();
    }
}