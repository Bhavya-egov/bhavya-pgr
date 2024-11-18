package digit.web.controllers;


import digit.service.PGRService;
import digit.util.ResponseInfoFactory;
import digit.web.models.ErrorRes;
import digit.web.models.ServiceRequest;
import digit.web.models.ServiceResponse;
    import com.fasterxml.jackson.databind.ObjectMapper;
import digit.web.models.ServiceWrapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.common.contract.response.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public class RequestApiController{

        private final ObjectMapper objectMapper;

    private ResponseInfoFactory responseInfoFactory;


    private PGRService pgrService;

        private final HttpServletRequest request;

        @Autowired
        public RequestApiController(ObjectMapper objectMapper,PGRService pgrService, HttpServletRequest request) {
        this.objectMapper = objectMapper;
            this.pgrService = pgrService;
        this.request = request;
        }

                @RequestMapping(value="/request/_count", method = RequestMethod.POST)
                public ResponseEntity<ServiceResponse> requestCountPost(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Unique id for a tenant." ,required=true,schema=@Schema()) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@Parameter(in = ParameterIn.QUERY, description = "Allows search for service type - comma separated list" ,schema=@Schema()) @Valid @RequestParam(value = "serviceCode", required = false) List<String> serviceCode,@Parameter(in = ParameterIn.QUERY, description = "Search by list of UUID" ,schema=@Schema()) @Valid @RequestParam(value = "ids", required = false) List<String> ids,@Parameter(in = ParameterIn.QUERY, description = "Search by mobile number of service requester" ,schema=@Schema()) @Valid @RequestParam(value = "mobileNo", required = false) String mobileNo,@Parameter(in = ParameterIn.QUERY, description = "Search by serviceRequestId of the complaint" ,schema=@Schema()) @Valid @RequestParam(value = "serviceRequestId", required = false) String serviceRequestId,@Parameter(in = ParameterIn.QUERY, description = "Search by list of Application Status" ,schema=@Schema()) @Valid @RequestParam(value = "applicationStatus", required = false) List<String> applicationStatus) {
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

    @RequestMapping(value="/request/_create", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse> requestsCreatePost(@Valid @RequestBody ServiceRequest request) throws IOException {
        ServiceRequest enrichedReq = pgrService.create(request);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
//        ServiceWrapper serviceWrapper = ServiceWrapper.builder().service(enrichedReq.getService()).workflow(enrichedReq.getWorkflow()).build();
        ServiceResponse response = ServiceResponse.builder().responseInfo(responseInfo).pgREntities(Collections.singletonList(request.getPgrEntity())).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

                @RequestMapping(value="/request/_search", method = RequestMethod.POST)
                public ResponseEntity<ServiceResponse> requestSearchPost(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Unique id for a tenant." ,required=true,schema=@Schema()) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@Parameter(in = ParameterIn.QUERY, description = "Allows search for service type - comma separated list" ,schema=@Schema()) @Valid @RequestParam(value = "serviceCode", required = false) List<String> serviceCode,@Parameter(in = ParameterIn.QUERY, description = "Search by list of UUID" ,schema=@Schema()) @Valid @RequestParam(value = "ids", required = false) List<String> ids,@Parameter(in = ParameterIn.QUERY, description = "Search by mobile number of service requester" ,schema=@Schema()) @Valid @RequestParam(value = "mobileNo", required = false) String mobileNo,@Parameter(in = ParameterIn.QUERY, description = "Search by serviceRequestId of the complaint" ,schema=@Schema()) @Valid @RequestParam(value = "serviceRequestId", required = false) String serviceRequestId,@Parameter(in = ParameterIn.QUERY, description = "Search by list of Application Status" ,schema=@Schema()) @Valid @RequestParam(value = "applicationStatus", required = false) List<String> applicationStatus) {
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

                @RequestMapping(value="/request/_update", method = RequestMethod.POST)
                public ResponseEntity<ServiceResponse> requestUpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "Request schema.", required=true, schema=@Schema()) @Valid @RequestBody ServiceRequest body) {
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

        }
