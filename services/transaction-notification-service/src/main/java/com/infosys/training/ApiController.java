package com.infosys.training;
import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1") public class ApiController {
 @GetMapping("/info") public Map<String,Object> info(){return Map.of("service","transaction-notification-service","status","UP");}
 @PostMapping("/process") public ResponseEntity<Map<String,Object>> process(@RequestBody Map<String,Object> body){String cid=String.valueOf(body.getOrDefault("correlationId",UUID.randomUUID().toString())); Map<String,Object> r=new LinkedHashMap<>(); r.put("service","transaction-notification-service"); r.put("correlationId",cid); r.put("status","DELIVERED"); r.put("targetSystem","transaction-processing-system"); r.put("payload",body); return ResponseEntity.ok(r);}
}
