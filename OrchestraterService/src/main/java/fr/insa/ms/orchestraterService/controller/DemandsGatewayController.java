package fr.insa.ms.orchestraterService.controller;

import fr.insa.ms.orchestraterService.model.Demand;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/demands")
public class DemandsGatewayController {

    private final RestTemplate rest;

    public DemandsGatewayController(RestTemplate rest){
        this.rest = rest;
    }

    // ---------- modification after authentification：valid token then transmit ----------
    
    @GetMapping
    public Demand[] all(@RequestHeader(value="Authorization", required=false) String authHeader){
    	
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers); // transmit token too
        return rest.exchange(
                "http://DemandService/demands",
                org.springframework.http.HttpMethod.GET,
                entity,
                Demand[].class
        ).getBody();
    }

    @GetMapping("/{id}")
    public Demand get(@PathVariable int id,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

    	 HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", authHeader);
         HttpEntity<Void> entity = new HttpEntity<>(headers); // transmit token too
        return rest.exchange(
                "http://DemandService/demands/{id}",
                org.springframework.http.HttpMethod.GET,
                entity,
                Demand.class,
                id
        ).getBody();
    }

    @GetMapping("/student/{studentId}")
    public Demand[] byDemandeur(@PathVariable int studentId,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers); // transmit token too
        return rest.exchange(
                "http://DemandService/demands/student/{studentId}",
                org.springframework.http.HttpMethod.GET,
                entity,
                Demand[].class,
                studentId
        ).getBody();
    }
    
    @GetMapping("/helper/{studentId}")
    public Demand[] byRepondeur(@PathVariable int studentId,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers); // transmit token too
        return rest.exchange(
                "http://DemandService/demands/helper/{studentId}",
                org.springframework.http.HttpMethod.GET,
                entity,
                Demand[].class,
                studentId
        ).getBody();
    }
    
    @PostMapping
    public Demand post(@RequestBody Demand d,
                       @RequestHeader(value="Authorization", required=false) String authHeader){

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Demand> entity = new HttpEntity<>(d, headers); // transmit token too
        ResponseEntity<Demand> resp = rest.exchange(
                "http://DemandService/demands",
                HttpMethod.POST,
                entity,
                Demand.class
        );
        return resp.getBody();
    }

    @PostMapping("/{id}/assign")
    public boolean assign(@PathVariable int id,
                          @RequestHeader(value="Authorization", required=false) String authHeader){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> resp = rest.exchange(
                "http://DemandService/demands/{id}/assign",
                HttpMethod.PUT,
                entity,
                Boolean.class,
                id
        );
        return Boolean.TRUE.equals(resp.getBody());
    }

    @PutMapping("/{id}/statut/{statut}")
    public boolean updateStatut(@PathVariable int id,
                                @PathVariable Demand.StatutDemande statut,
                                @RequestHeader(value="Authorization", required=false) String authHeader){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> resp = rest.exchange(
                "http://DemandService/demands/{id}/statut/{statut}",
                HttpMethod.PUT,
                entity,
                Boolean.class,
                id, statut.name()
        );
        return Boolean.TRUE.equals(resp.getBody());
    }

    @PutMapping("/{id}/avis")
    public boolean avis(@PathVariable int id,
                        @RequestBody String avis,
                        @RequestHeader(value="Authorization", required=false) String authHeader){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(avis, headers);

        ResponseEntity<String> resp = rest.exchange(
                "http://DemandService/demands/{id}/avis",
                HttpMethod.PUT,
                entity,
                String.class,
                id
        );
        String res = resp.getBody();
        if(res.equals("Not a valid token.")) {
        	return false;
        }else if(res.equals("Commentaire failed.")){
        	return false;
        }else {
        	return true;
        }
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id,
                          @RequestHeader(value="Authorization", required=false) String authHeader){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> resp = rest.exchange(
                "http://DemandService/demands/{id}",
                HttpMethod.DELETE,
                entity,
                Boolean.class,
                id
        );
        return Boolean.TRUE.equals(resp.getBody());
    }

}

