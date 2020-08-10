package com.szells.membership.mapper;


import com.szells.membership.domain.payload.MemberAttributeBean;
import com.szells.membership.domain.request.AuditBean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuditBeanMapper {
    AuditBean toRequest(MemberAttributeBean.AuditBean auditBean);
}
