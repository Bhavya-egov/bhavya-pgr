package digit.web.controllers;


import digit.service.PGRService;
import digit.util.ResponseInfoFactory;
import digit.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.response.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.egov.common.contract.response.ResponseInfo;

import java.io.IOException;
import java.util.*;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-11-18T11:06:14.160295565+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("pgr")
public class RequestApiController {

    private final ObjectMapper objectMapper;

    private final ResponseInfoFactory responseInfoFactory;


    private PGRService pgrService;

    private final HttpServletRequest request;

    @Autowired
    public RequestApiController(ObjectMapper objectMapper, ResponseInfoFactory responseInfoFactory, PGRService pgrService, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.responseInfoFactory = responseInfoFactory;
        this.pgrService = pgrService;
        this.request = request;
    }

    @RequestMapping(value = "/request/_count", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> requestCountPost(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Unique id for a tenant.", required = true, schema = @Schema()) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @Parameter(in = ParameterIn.QUERY, description = "Allows search for service type - comma separated list", schema = @Schema()) @Valid @RequestParam(value = "serviceCode", required = false) List<String> serviceCode, @Parameter(in = ParameterIn.QUERY, description = "Search by list of UUID", schema = @Schema()) @Valid @RequestParam(value = "ids", required = false) List<String> ids, @Parameter(in = ParameterIn.QUERY, description = "Search by mobile number of service requester", schema = @Schema()) @Valid @RequestParam(value = "mobileNo", required = false) String mobileNo, @Parameter(in = ParameterIn.QUERY, description = "Search by serviceRequestId of the complaint", schema = @Schema()) @Valid @RequestParam(value = "serviceRequestId", required = false) String serviceRequestId, @Parameter(in = ParameterIn.QUERY, description = "Search by list of Application Status", schema = @Schema()) @Valid @RequestParam(value = "applicationStatus", required = false) List<String> applicationStatus) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ServiceResponse>(objectMapper.readValue("{  \"responseInfo\" : \"{}\",  \"PGREntities\" : [ \"{}\", \"{}\" ]}", ServiceResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<ServiceResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
    //done
    @RequestMapping(value = "/request/_create", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> requestsCreatePost(@Valid @RequestBody ServiceRequest request) throws IOException {
        ServiceRequest enrichedReq = pgrService.create(request);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        ServiceWrapper serviceWrapper = ServiceWrapper.builder().service(enrichedReq.getPgrEntity().getService()).workflow(enrichedReq.getPgrEntity().getWorkflow()).build();
        ServiceResponse response = ServiceResponse.builder().
                responseInfo(responseInfo)
                .pgREntities(Collections.singletonList(serviceWrapper)).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value="/request/_search", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> requestsSearchPost(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper,
                                                              @Valid @ModelAttribute RequestSearchCriteria criteria) {

        String tenantId = criteria.getTenantId();
        List<ServiceWrapper> serviceWrappers = pgrService.search(requestInfoWrapper.getRequestInfo(), criteria);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        ServiceResponse response = ServiceResponse.builder().responseInfo(responseInfo).pgREntities(serviceWrappers).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @RequestMapping(value="/request/_update", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> requestsUpdatePost(@Valid @RequestBody ServiceRequest request) throws IOException {
        ServiceRequest enrichedReq = pgrService.update(request);
        ServiceWrapper serviceWrapper = ServiceWrapper.builder().service(enrichedReq.getPgrEntity().getService()).workflow(enrichedReq.getPgrEntity().getWorkflow()).build();
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        ServiceResponse response = ServiceResponse.builder().responseInfo(responseInfo).pgREntities(Collections.singletonList(serviceWrapper)).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
