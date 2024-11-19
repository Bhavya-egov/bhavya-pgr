package digit.service;

import digit.config.PGRConfiguration;
import digit.repository.IdGenRepository;
import digit.util.PGRUtils;
import digit.web.models.RequestSearchCriteria;
import digit.web.models.Service;
import digit.web.models.ServiceRequest;
import digit.web.models.Workflow;
import io.micrometer.common.util.StringUtils;
import org.egov.common.contract.idgen.IdResponse;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static digit.config.ServiceConstants.USERTYPE_CITIZEN;

@org.springframework.stereotype.Service
public class EnrichmentService {
    @Autowired
    private PGRUtils utils;
    @Autowired
    private PGRConfiguration config;

    @Autowired
    private IdGenRepository idGenRepository;

    public void enrichCreateRequest(ServiceRequest serviceRequest) {

        RequestInfo requestInfo = serviceRequest.getRequestInfo();
        Service service = serviceRequest.getPgrEntity().getService();
        Workflow workflow = serviceRequest.getPgrEntity().getWorkflow();
        String tenantId = service.getTenantId();

        // Enrich accountId of the logged in citizen
        if (requestInfo.getUserInfo().getType().equalsIgnoreCase(USERTYPE_CITIZEN))
            serviceRequest.getPgrEntity().getService().setAccountId(requestInfo.getUserInfo().getUuid());

//        userService.callUserService(serviceRequest);


        AuditDetails auditDetails = utils.getAuditDetails(requestInfo.getUserInfo().getUuid(), service, true);

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

        if (StringUtils.isEmpty(service.getAccountId()))
            service.setAccountId(service.getCitizen().getUuid());

        List<String> customIds = getIdList(requestInfo,tenantId,config.getServiceRequestIdGenName(),config.getServiceRequestIdGenFormat(),1);
//        List<String> customIds = Collections.singletonList("pgr-" + UUID.randomUUID());


        service.setServiceRequestId(customIds.get(0));


    }
    // to enrich search request
    public void enrichSearchRequest(RequestInfo requestInfo, RequestSearchCriteria criteria){

        if(criteria.isEmpty() && requestInfo.getUserInfo().getType().equalsIgnoreCase(USERTYPE_CITIZEN)){
            String citizenMobileNumber = requestInfo.getUserInfo().getUserName();
            criteria.setMobileNumber(citizenMobileNumber);
        }

        criteria.setAccountId(requestInfo.getUserInfo().getUuid());

        String tenantId = (criteria.getTenantId()!=null) ? criteria.getTenantId() : requestInfo.getUserInfo().getTenantId();

//        if(criteria.getMobileNumber()!=null){
//            userService.enrichUserIds(tenantId, criteria);
//        }

        if(criteria.getLimit()==null)
            criteria.setLimit(config.getDefaultLimit());

        if(criteria.getOffset()==null)
            criteria.setOffset(config.getDefaultOffset());

        if(criteria.getLimit()!=null && criteria.getLimit() > config.getMaxLimit())
            criteria.setLimit(config.getMaxLimit());

    }

    public void enrichUpdateRequest(ServiceRequest serviceRequest){

        RequestInfo requestInfo = serviceRequest.getRequestInfo();
        Service service = serviceRequest.getPgrEntity().getService();
        AuditDetails auditDetails = utils.getAuditDetails(requestInfo.getUserInfo().getUuid(), service,false);

        service.setAuditDetails(auditDetails);

//        userService.callUserService(serviceRequest);
    }

    /**
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }
}
