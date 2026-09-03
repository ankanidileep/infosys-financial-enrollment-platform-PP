package com.infosys.training;
import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1") public class ApiController {
 @GetMapping("/info") public Map<String,Object> info(){return Map.of("service","enrollment-service","status","UP");}
 @PostMapping("/process") public ResponseEntity<Map<String,Object>> process(@RequestBody Map<String,Object> body){
  String cid=String.valueOf(body.getOrDefault("correlationId",UUID.randomUUID().toString())); String cur=String.valueOf(body.getOrDefault("currentTier","")); String next=String.valueOf(body.getOrDefault("segment","")); String action;
  if(cur.isBlank()) action="NEW_ENROLLMENT"; else if(cur.equalsIgnoreCase(next)) action="NO_CHANGE"; else if(rank(next)>rank(cur)) action="UPTIER"; else action="DOWNGRADE";
  Map<String,Object> r=new LinkedHashMap<>(); r.put("service","enrollment-service"); r.put("correlationId",cid); r.put("status","ENROLLMENT_DECIDED"); r.put("action",action); r.put("payload",body); return ResponseEntity.ok(r);
 }
 private int rank(String t){return switch(t.toUpperCase()){case "PREF"->1;case "GOLD"->2;case "PLAT"->3;case "SPCS_PINN"->4;case "SPWS"->5;default->0;};}
}
