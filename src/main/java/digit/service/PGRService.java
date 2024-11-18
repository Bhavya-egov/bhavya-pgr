package digit.service;


import digit.config.PGRConfiguration;
import digit.producer.Producer;
import digit.util.MdmsUtil;
import digit.validator.ServiceRequestValidator;
import digit.web.models.ServiceRequest;
//import org.egov.pgr.producer.Producer;
//import org.egov.pgr.repository.PGRRepository;
//import org.egov.pgr.util.MDMSUtils;
//import org.egov.pgr.ServiceRequestValidator.ServiceRequestValidator;
//import org.egov.pgr.web.models.ServiceWrapper;
//import org.egov.pgr.web.models.RequestSearchCriteria;
//import org.egov.pgr.web.models.ServiceRequest;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class PGRService {



    private EnrichmentService enrichmentService;

//    private UserService userService;

//    private WorkflowService workflowService;

    private ServiceRequestValidator serviceRequestValidator;

    private ServiceRequestValidator validator;

    private Producer producer;

    private PGRConfiguration config;

//    private PGRRepository repository;

    private MdmsUtil mdmsUtil;


    @Autowired
    public PGRService(EnrichmentService enrichmentService,
                      ServiceRequestValidator serviceRequestValidator, ServiceRequestValidator validator, Producer producer,
                      PGRConfiguration config,  MdmsUtil mdmsUtil) {
        this.enrichmentService = enrichmentService;
//        this.userService = userService;
//        this.workflowService = workflowService;
        this.serviceRequestValidator = serviceRequestValidator;
        this.validator = validator;
        this.producer = producer;
        this.config = config;
//        this.repository = repository;
        this.mdmsUtil = mdmsUtil;
    }


    /**
     * Creates a complaint in the system
     * @param request The service request containg the complaint information
     * @return
     */
    public ServiceRequest create(ServiceRequest request){
        String tenantId = request.getService().getTenantId();
//        Object mdmsData = mdmsUtil.mDMSCall(request);
        validator.validateCreate(request);
        enrichmentService.enrichCreateRequest(request);
//        workflowService.updateWorkflowStatus(request);
        producer.push(tenantId,config.getCreateTopic(),request);
        return request;
    }


    /**
     * Searches the complaints in the system based on the given criteria
     * @param requestInfo The requestInfo of the search call
     * @param criteria The search criteria containg the params on which to search
     * @return
     */
//    public List<ServiceWrapper> search(RequestInfo requestInfo, RequestSearchCriteria criteria){
//        ServiceRequestValidator.validateSearch(requestInfo, criteria);
//
//        enrichmentService.enrichSearchRequest(requestInfo, criteria);
//
//        if(criteria.isEmpty())
//            return new ArrayList<>();
//
//        if(criteria.getMobileNumber()!=null && CollectionUtils.isEmpty(criteria.getUserIds()))
//            return new ArrayList<>();
//
//        criteria.setIsPlainSearch(false);
//
//        List<ServiceWrapper> serviceWrappers = repository.getServiceWrappers(criteria);
//
//        if(CollectionUtils.isEmpty(serviceWrappers))
//            return new ArrayList<>();;
//
//        userService.enrichUsers(serviceWrappers);
//        List<ServiceWrapper> enrichedServiceWrappers = workflowService.enrichWorkflow(requestInfo,serviceWrappers);
//        Map<Long, List<ServiceWrapper>> sortedWrappers = new TreeMap<>(Collections.reverseOrder());
//        for(ServiceWrapper svc : enrichedServiceWrappers){
//            if(sortedWrappers.containsKey(svc.getService().getAuditDetails().getCreatedTime())){
//                sortedWrappers.get(svc.getService().getAuditDetails().getCreatedTime()).add(svc);
//            }else{
//                List<ServiceWrapper> serviceWrapperList = new ArrayList<>();
//                serviceWrapperList.add(svc);
//                sortedWrappers.put(svc.getService().getAuditDetails().getCreatedTime(), serviceWrapperList);
//            }
//        }
//        List<ServiceWrapper> sortedServiceWrappers = new ArrayList<>();
//        for(Long createdTimeDesc : sortedWrappers.keySet()){
//            sortedServiceWrappers.addAll(sortedWrappers.get(createdTimeDesc));
//        }
//        return sortedServiceWrappers;
//    }
//

    /**
     * Updates the complaint (used to forward the complaint from one application status to another)
     * @param request The request containing the complaint to be updated
     * @return
     */
//    public ServiceRequest update(ServiceRequest request){
//        String tenantId = request.getService().getTenantId();
//        Object mdmsData = mdmsUtils.mDMSCall(request);
//        ServiceRequestValidator.validateUpdate(request, mdmsData);
//        enrichmentService.enrichUpdateRequest(request);
//        workflowService.updateWorkflowStatus(request);
//        producer.push(tenantId,config.getUpdateTopic(),request);
//        return request;
//    }

    /**
     * Returns the total number of comaplaints matching the given criteria
     * @param requestInfo The requestInfo of the search call
     * @param criteria The search criteria containg the params for which count is required
     * @return
     */
//    public Integer count(RequestInfo requestInfo, RequestSearchCriteria criteria){
//        criteria.setIsPlainSearch(false);
//        Integer count = repository.getCount(criteria);
//        return count;
//    }


//    public List<ServiceWrapper> plainSearch(RequestInfo requestInfo, RequestSearchCriteria criteria) {
//        ServiceRequestValidator.validatePlainSearch(criteria);
//
//        criteria.setIsPlainSearch(true);
//
//        if(criteria.getLimit()==null)
//            criteria.setLimit(config.getDefaultLimit());
//
//        if(criteria.getOffset()==null)
//            criteria.setOffset(config.getDefaultOffset());
//
//        if(criteria.getLimit()!=null && criteria.getLimit() > config.getMaxLimit())
//            criteria.setLimit(config.getMaxLimit());
//
//        List<ServiceWrapper> serviceWrappers = repository.getServiceWrappers(criteria);
//
//        if(CollectionUtils.isEmpty(serviceWrappers)){
//            return new ArrayList<>();
//        }
//
//        userService.enrichUsers(serviceWrappers);
//        List<ServiceWrapper> enrichedServiceWrappers = workflowService.enrichWorkflow(requestInfo, serviceWrappers);
//
//        Map<Long, List<ServiceWrapper>> sortedWrappers = new TreeMap<>(Collections.reverseOrder());
//        for(ServiceWrapper svc : enrichedServiceWrappers){
//            if(sortedWrappers.containsKey(svc.getService().getAuditDetails().getCreatedTime())){
//                sortedWrappers.get(svc.getService().getAuditDetails().getCreatedTime()).add(svc);
//            }else{
//                List<ServiceWrapper> serviceWrapperList = new ArrayList<>();
//                serviceWrapperList.add(svc);
//                sortedWrappers.put(svc.getService().getAuditDetails().getCreatedTime(), serviceWrapperList);
//            }
//        }
//        List<ServiceWrapper> sortedServiceWrappers = new ArrayList<>();
//        for(Long createdTimeDesc : sortedWrappers.keySet()){
//            sortedServiceWrappers.addAll(sortedWrappers.get(createdTimeDesc));
//        }
//        return sortedServiceWrappers;
//    }


//    public Map<String, Integer> getDynamicData(String tenantId) {
//
//        Map<String,Integer> dynamicData = repository.fetchDynamicData(tenantId);
//
//        return dynamicData;
//    }


//    public int getComplaintTypes() {
//
//        return Integer.valueOf(config.getComplaintTypes());
//    }
}
