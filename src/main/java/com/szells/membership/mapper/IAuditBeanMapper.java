package com.szells.membership.mapper;


import com.szells.membership.model.payload.MemberAttributeBean;
import com.szells.membership.model.request.AuditBean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuditBeanMapper {
    AuditBean toRequest(MemberAttributeBean.AuditBean auditBean);
}
