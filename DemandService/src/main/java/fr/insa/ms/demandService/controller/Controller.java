package fr.insa.ms.demandService.controller;

import fr.insa.ms.demandService.model.Demand;
import fr.insa.ms.demandService.model.Demand.StatutDemande;
import fr.insa.ms.demandService.security.AuthClient;
import fr.insa.ms.demandService.service.DemandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demands")
public class Controller {

    private final DemandService service;
    @Autowired AuthClient authClient;
    
    public Controller(DemandService service){
        this.service = service;
    }

    private Integer extractAndVerify(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        System.out.println(token);
        return authClient.verify(token); // send it to student-service to verify 
    }

    @PostMapping
    public Demand post(@RequestBody Demand d,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return null;
        System.out.println(userId);
        d.setDemandeurId(userId);
        return service.post(d);
    }

    @PutMapping("/{id}/assign")
    public boolean assign(@PathVariable int id,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return false;

        return service.assign(id, userId);
    }

    @PutMapping("/{id}/statut/{statut}")
    public boolean updateStatut(@PathVariable int id, @PathVariable StatutDemande statut,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return false;
        return service.updateStatut(id, userId.intValue(),statut);
    }

    @PutMapping("/{id}/avis")
    public String comment(@PathVariable int id, @RequestBody String avis,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return "Not a valid token.";
        
        boolean res = service.comment(id, userId.intValue(), avis);
        if (res) {
        	return "Commentaire mis à jour.";
        }else {
        	return "Commentaire failed.";
        }
    }

    @GetMapping
    public List<Demand> all(@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return null;

        return service.all();
    }

    @GetMapping("/{id}")
    public Demand get(@PathVariable int id,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return null;

        return service.get(id);
    }
    
    // find  demandeur demands
    @GetMapping("/student/{studentId}")
    public List<Demand> byDemandeur(@PathVariable int studentId,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return null;

        return service.getByDemandeur(studentId);
    }

    // find  repondeur demands
    @GetMapping("/helper/{helperId}")
    public List<Demand> byRepondeur(@PathVariable int helperId,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return null;

        return service.getByRepondeur(helperId);
    }
    
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id,
    		@RequestHeader(value="Authorization", required=false) String authHeader){

        Integer userId = extractAndVerify(authHeader);
        if(userId == null) return false;

        return service.delete(id);
    }
    

}

