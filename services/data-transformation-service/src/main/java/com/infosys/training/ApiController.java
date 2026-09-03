package com.infosys.training;
import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1") public class ApiController {
 @GetMapping("/info") public Map<String,Object> info(){return Map.of("service","data-transformation-service","status","UP");}
 @PostMapping("/process") public ResponseEntity<Map<String,Object>> process(@RequestBody Map<String,Object> body){
  String cid=String.valueOf(body.getOrDefault("correlationId",UUID.randomUUID().toString())); Map<String,Object> target=new LinkedHashMap<>();
  target.put("customer",body.getOrDefault("customerId","")); target.put("account",body.getOrDefault("accountId","")); target.put("tierCode",code(String.valueOf(body.getOrDefault("segment","")))); target.put("operation",op(String.valueOf(body.getOrDefault("action","UPDATE"))));
  Map<String,Object> r=new LinkedHashMap<>(); r.put("service","data-transformation-service"); r.put("correlationId",cid); r.put("status","TRANSFORMED"); r.put("targetPayload",target); return ResponseEntity.ok(r);
 }
 private String code(String t){return switch(t){case "PREF"->"01";case "GOLD"->"02";case "PLAT"->"03";case "SPCS_PINN"->"04";case "SPWS"->"05";default->"00";};}
 private String op(String a){return switch(a){case "NEW_ENROLLMENT"->"CREATE";case "UPTIER","DOWNGRADE","UPDATE"->"UPDATE";default->"NO_CHANGE";};}
}
