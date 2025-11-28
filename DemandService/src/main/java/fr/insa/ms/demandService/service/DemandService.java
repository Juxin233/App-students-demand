package fr.insa.ms.demandService.service;

import fr.insa.ms.demandService.model.Demand;
import fr.insa.ms.demandService.model.Demand.StatutDemande;
import fr.insa.ms.demandService.repository.DemandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {

    private final DemandRepository repo;

    public DemandService(DemandRepository repo){
        this.repo = repo;
    }

    public Demand post(Demand d){
        return repo.postDemand(d);
    }

    public boolean assign(int id, int repondeurId){
        return repo.assignDemand(id, repondeurId);
    }

    public boolean updateStatut(int id, int userId,StatutDemande statut){
        return repo.updateStatut(id, userId,statut);
    }

    public boolean comment(int id, int userId,String avis){
        return repo.commentDemand(id, userId,avis);
    }

    public List<Demand> all(){
        return repo.getAllDemands();
    }

    public Demand get(int id){
        return repo.getById(id);
    }
    
    public List<Demand> getByDemandeur(int id){
    	return repo.getByDemandeurId(id);
    }
    
    public List<Demand> getByRepondeur(int id){
    	return repo.getByRepondeurId(id);
    }
    
    public boolean delete(int id){
        return repo.delete(id);
    }
    
}
