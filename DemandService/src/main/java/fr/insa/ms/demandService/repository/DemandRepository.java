package fr.insa.ms.demandService.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.insa.ms.demandService.model.Demand;
import fr.insa.ms.demandService.model.Demand.StatutDemande;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class DemandRepository {

    private final String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_063";
    private final String user = "projet_gei_063";
    private final String pass = "eeph3Jae";
    
    private final ObjectMapper om = new ObjectMapper();

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // ------------------ Create Demand ------------------
    public Demand postDemand(Demand d) {
        String sql = """
            INSERT INTO demande (titre, description, motsCles, demandeurId, datesouhaitee, statut)
            VALUES (?,?,?,?,?,?)
        """;

        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getTitre());
            ps.setString(2, d.getDescription());

            String motsJson = om.writeValueAsString(
                    d.getMotsCles() == null ? List.of() : d.getMotsCles()
            );
            ps.setString(3, motsJson);

            ps.setInt(4, d.getDemandeurId());
            ps.setTimestamp(5, Timestamp.valueOf(d.getDatesouhaitee()));
            ps.setString(6, StatutDemande.En_attente.name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                d.setId(rs.getInt(1));
            }
            d.setStatut(StatutDemande.En_attente);
            return d;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------ assign repondeur ------------------
    public boolean assignDemand(int demandId, int repondeurId) {
        String sql = "UPDATE demande SET repondeurId=?, statut=? WHERE id=?";
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, repondeurId);
            ps.setString(2, StatutDemande.En_cours.name());
            ps.setInt(3, demandId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ update statut ------------------
    public boolean updateStatut(int id, int userId,StatutDemande statut) {
        String sql = "UPDATE demande SET statut=? WHERE id=? AND (demandeurId = ? OR repondeurId = ?)";
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, statut.name());
            ps.setInt(2, id);
            ps.setInt(3, userId);
            ps.setInt(4, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ commentaire ------------------
    public boolean commentDemand(int id, int userId,String avis) {
        String sql = "UPDATE demande SET avis=? WHERE (id=? AND demandeurId=?)";
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, avis);
            ps.setInt(2, id);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ view all ------------------
    public List<Demand> getAllDemands() {
        List<Demand> list = new ArrayList<>();
        String sql = "SELECT * FROM demande";

        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(toDemand(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ------------------ find by id（for Orchestrater ） ------------------
    public Demand getById(int id){
        String sql = "SELECT * FROM demande WHERE id=?";
        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return toDemand(rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
 
    // ------------------ find by demandeur ------------------
    public List<Demand> getByDemandeurId(int studentId){
        List<Demand> list = new ArrayList<>();
        String sql = "SELECT * FROM demande WHERE demandeurId=? ORDER BY datesouhaitee DESC";

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql)){

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(toDemand(rs));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
 // ------------------ find by repondeur ------------------
    public List<Demand> getByRepondeurId(int studentId){
        List<Demand> list = new ArrayList<>();
        String sql = "SELECT * FROM demande WHERE repondeurId=? ORDER BY datesouhaitee DESC";

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql)){

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(toDemand(rs));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }


    // ------------------ delete ------------------
    public boolean delete(int id){
        String sql = "DELETE FROM demande WHERE id=?";
        try(Connection c=connect(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // ------------------ tool：ResultSet -> Demand ------------------
    private Demand toDemand(ResultSet rs) throws Exception {
        Demand d = new Demand();
        d.setId(rs.getInt("id"));
        d.setTitre(rs.getString("titre"));
        d.setDescription(rs.getString("description"));
        d.setDemandeurId(rs.getInt("demandeurId"));

        int rep = rs.getInt("repondeurId");
        d.setRepondeurId(rs.wasNull() ? null : rep);

        String motsJson = rs.getString("motsCles");
        List<String> mots = motsJson == null
                ? List.of()
                : om.readValue(motsJson, new TypeReference<List<String>>(){});
        d.setMotsCles(mots);

        Timestamp ts = rs.getTimestamp("datesouhaitee");
        d.setDatesouhaitee(ts == null ? null : ts.toLocalDateTime());

        d.setStatut(StatutDemande.valueOf(rs.getString("statut")));

        d.setAvis(rs.getString("avis"));
        return d;
    }
}

