package com.infosys.training;
import java.util.*; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1") public class ApiController {
 @GetMapping("/info") public Map<String,Object> info(){return Map.of("service","account-segmentation-service","status","UP");}
 @PostMapping("/process") public ResponseEntity<Map<String,Object>> process(@RequestBody Map<String,Object> body){
  String cid=String.valueOf(body.getOrDefault("correlationId",UUID.randomUUID().toString())); double v=0; Object av=body.get("assetValue");
  try{v=av instanceof Number n?n.doubleValue():Double.parseDouble(String.valueOf(av));}catch(Exception ignored){}
  String tier=v>=10000000?"SPWS":v>=1000000?"SPCS_PINN":v>=250000?"PLAT":v>=100000?"GOLD":"PREF";
  Map<String,Object> r=new LinkedHashMap<>(); r.put("service","account-segmentation-service"); r.put("correlationId",cid); r.put("status","SEGMENTED"); r.put("segment",tier); r.put("payload",body); return ResponseEntity.ok(r);
 }
}
