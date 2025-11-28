package fr.insa.ms.orchestraterService.service;

import fr.insa.ms.orchestraterService.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrchestraterService {

    private final RestTemplate rest;

    public OrchestraterService(RestTemplate rest){
        this.rest = rest;
    }

    public HelperRequestResponse createDemandAndRecommend(Demand demand){

        // 1) create demand
        Demand created = rest.postForObject(
                "http://DemandService/demands",
                demand,
                Demand.class
        );

        if(created == null){
            // create demand failed
            return new HelperRequestResponse("ERROR_CREATE_DEMAND", List.of());
        }

        // 2) Recommand helperIds
        RecommendationRequest req = new RecommendationRequest(
                created.getMotsCles()
        );

        Integer[] helperIdsArr = rest.postForObject(
                "http://RecommendationService/recommendations",
                req,
                Integer[].class
        );

        List<Integer> helperIds = helperIdsArr == null
                ? List.of()
                : Arrays.asList(helperIdsArr);

        // 3) get batch helper profiles
        Student[] helpersArr = helperIds.isEmpty()
                ? new Student[0]
                : rest.postForObject(
                    "http://StudentService/students/batch",
                    helperIds,
                    Student[].class
                );

        List<Student> helpers = helpersArr == null
                ? List.of()
                : Arrays.asList(helpersArr);
        List<StudentView> helpers_view = new ArrayList<StudentView>();
        for (Student s : helpers){
        	StudentView helper = new StudentView();
        	helper.setAnnee(s.getAnnee());
        	helper.setCapacites(s.getCapacites());
        	helper.setEmail(s.getEmail());
        	helper.setEtablissement(s.getEtablissement());
        	helper.setFiliere(s.getFiliere());
        	helper.setNom(s.getNom());
        	helper.setPrenom(s.getPrenom());
        	helper.setAvisUrl("http://localhost:8110/demands/helper/"+s.getId());
        	helpers_view.add(helper);
        }
        	
        // 4) combine and return
        return new HelperRequestResponse(
//                created.getId(),
                created.getStatut().name(),
                helpers_view
        );
    }

    public boolean assignHelper(int demandId, int helperId){
        try{
            rest.postForLocation(
                    "http://DemandService/demands/{id}/assign/{helperId}",
                    null,
                    demandId,
                    helperId
            );
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

