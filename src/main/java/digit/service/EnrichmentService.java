package digit.service;

import digit.util.PGRUtils;
import digit.web.models.Service;
import digit.web.models.ServiceRequest;
import io.micrometer.common.util.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static digit.config.ServiceConstants.USERTYPE_CITIZEN;

public class EnrichmentService {

    private PGRUtils utils;

    public void enrichCreateRequest(ServiceRequest serviceRequest){

        RequestInfo requestInfo = serviceRequest.getRequestInfo();
        Service service = serviceRequest.getService();
//        Workflow workflow = serviceRequest.getWorkflow();
        String tenantId = service.getTenantId();

        // Enrich accountId of the logged in citizen
        if(requestInfo.getUserInfo().getType().equalsIgnoreCase(USERTYPE_CITIZEN))
            serviceRequest.getService().setAccountId(requestInfo.getUserInfo().getUuid());

//        userService.callUserService(serviceRequest);


        AuditDetails auditDetails = utils.getAuditDetails(requestInfo.getUserInfo().getUuid(), service,true);

        service.setAuditDetails(auditDetails);
        service.setId(UUID.randomUUID().toString());
        service.getAddress().setId(UUID.randomUUID().toString());
        service.getAddress().setTenantId(tenantId);
        service.setActive(true);

//        if(workflow.getVerificationDocuments()!=null){
//            workflow.getVerificationDocuments().forEach(document -> {
//                document.setId(UUID.randomUUID().toString());
//            });
//        }

        if(StringUtils.isEmpty(service.getAccountId()))
            service.setAccountId(service.getCitizen().getUuid());

//        List<String> customIds = getIdList(requestInfo,tenantId,config.getServiceRequestIdGenName(),config.getServiceRequestIdGenFormat(),1);
        List<String> customIds = Collections.singletonList("pgr-" + UUID.randomUUID());


        service.setServiceRequestId(customIds.get(0));


    }

}
