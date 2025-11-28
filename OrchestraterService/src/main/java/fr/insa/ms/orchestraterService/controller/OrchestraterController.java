package fr.insa.ms.orchestraterService.controller;

import fr.insa.ms.orchestraterService.model.*;
import fr.insa.ms.orchestraterService.security.AuthClient;
import fr.insa.ms.orchestraterService.service.OrchestraterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help-requests")
public class OrchestraterController {

    private final OrchestraterService service;
    private final AuthClient authClient;
    
    public OrchestraterController(OrchestraterService service,AuthClient authClient){
        this.service = service;
        this.authClient= authClient;
    }
    
    private Integer extractAndVerify(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        return authClient.verify(token);
    }

    
    // create demand + return recommand
    @PostMapping
    public HelperRequestResponse create(@RequestBody Demand demand, 
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null){
            return new HelperRequestResponse("UNAUTHORIZED", java.util.List.of());
        }
        demand.setDemandeurId(userId);
        return service.createDemandAndRecommend(demand);
    }

}


