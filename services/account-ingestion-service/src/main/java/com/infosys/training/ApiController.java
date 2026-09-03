package com.infosys.training;
import java.util.*; import org.springframework.beans.factory.annotation.Value; import org.springframework.http.MediaType; import org.springframework.web.bind.annotation.*; import org.springframework.web.client.RestClient;
@RestController @RequestMapping("/api/v1") public class ApiController {
 private final RestClient client=RestClient.create();
 @Value("${workflow.services.validation-url}") String validation; @Value("${workflow.services.eligibility-url}") String eligibility; @Value("${workflow.services.segmentation-url}") String segmentation; @Value("${workflow.services.enrollment-url}") String enrollment; @Value("${workflow.services.transformation-url}") String transformation; @Value("${workflow.services.notification-url}") String notification;
 @GetMapping("/info") public Map<String,Object> info(){return Map.of("service","account-ingestion-service","status","UP");}
 @PostMapping(value="/accounts",consumes=MediaType.APPLICATION_JSON_VALUE) public Map<String,Object> ingest(@RequestBody Map<String,Object> request){
  String cid=UUID.randomUUID().toString(); Map<String,Object> body=new LinkedHashMap<>(request); body.put("correlationId",cid);
  body=call(validation,body); body=call(eligibility,body); if(Boolean.FALSE.equals(body.get("eligible"))) return result(cid,"REJECTED","Customer/account is not eligible.",body);
  body=call(segmentation,body); body=call(enrollment,body); body=call(transformation,body); body=call(notification,body); return result(cid,"COMPLETED","Financial enrollment workflow completed successfully.",body);
 }
 private Map<String,Object> call(String base,Map<String,Object> body){Map<String,Object> r=client.post().uri(base+"/api/v1/process").contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Map.class); return r==null?body:r;}
 private Map<String,Object> result(String cid,String status,String msg,Map<String,Object> d){Map<String,Object> r=new LinkedHashMap<>();r.put("service","account-ingestion-service");r.put("correlationId",cid);r.put("status",status);r.put("message",msg);r.put("workflowResult",d);return r;}
}
