package fr.insa.ms.studentService.repository;

import fr.insa.ms.studentService.model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class StudentRepository {

    private final String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_063";
    private final String user = "projet_gei_063";
    private final String pass = "eeph3Jae";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // ------------------------- Sign in --------------------------------

    public int register(Student s){
        String sql = "INSERT INTO student(prenom, nom, email, etablissement, filiere, annee, passwd) VALUES (?,?,?,?,?,?,?)";
        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, s.getPrenom());
            ps.setString(2, s.getNom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getEtablissement());
            ps.setString(5, s.getFiliere());
            ps.setInt(6, s.getAnnee());
            ps.setString(7, s.getPasswd());
            ps.executeUpdate();

            // get ID
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                s.setId(id);
            }

            insertCapacites(s);
            return s.getId();
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // ------------------------- Login in --------------------------------

    public boolean login(int id, String passwd){
        String sql = "SELECT passwd FROM student WHERE id=?";
        try(Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("passwd").equals(passwd);
            }
            return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------- update --------------------------------

    public boolean update(Student s){
        String sql = "UPDATE student SET prenom=?, nom=?, email=?, etablissement=?, filiere=?, annee=?, passwd=? WHERE id=?";
        try(Connection c = connect(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, s.getPrenom());
            ps.setString(2, s.getNom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getEtablissement());
            ps.setString(5, s.getFiliere());
            ps.setInt(6, s.getAnnee());
            ps.setString(7, s.getPasswd());
            ps.setInt(8, s.getId());

            ps.executeUpdate();

            deleteCapacites(s.getId());
            insertCapacites(s);

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // ------------------------- capacities ----------------------------

    private void deleteCapacites(int id) throws SQLException {
        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement("DELETE FROM student_capacite WHERE student_id=?")){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void insertCapacites(Student s) throws SQLException {
        if(s.getCapacites() == null) return;

        for(String cap : s.getCapacites()){
            int capId = getOrCreateCapacite(cap);

            try(Connection c = connect();
                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO student_capacite(student_id, capacite_id) VALUES (?,?)"))
            {
                ps.setInt(1, s.getId());
                ps.setInt(2, capId);
                ps.executeUpdate();
            }
        }
    }

    private int getOrCreateCapacite(String cap) throws SQLException {
        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement("SELECT id FROM capacite WHERE capacite=?"))
        {
            ps.setString(1, cap);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt("id");
        }

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO capacite(capacite) VALUES (?)", Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, cap);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Incapable de créer capacité");
    }

    // ------------------------- all students ----------------------------

    public List<Student> all(){
        String sql = "SELECT * FROM student";
        List<Student> list = new ArrayList<>();

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                list.add(toStudent(rs));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    // for orchestrater 
    public List<Student> batch(List<Integer> ids){
        List<Student> list = new ArrayList<>();

        if(ids == null || ids.isEmpty()) return list;

        String inSql = String.join(",", ids.stream().map(x -> "?").toList());

        String sql = "SELECT * FROM student WHERE id IN (" + inSql + ")";

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql))
        {
            int i=1;
            for(int id : ids) ps.setInt(i++, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(toStudent(rs));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    // ------------------------- tool ----------------------------

    private Student toStudent(ResultSet rs) throws Exception {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setPrenom(rs.getString("prenom"));
        s.setNom(rs.getString("nom"));
        s.setEmail(rs.getString("email"));
        s.setEtablissement(rs.getString("etablissement"));
        s.setFiliere(rs.getString("filiere"));
        s.setAnnee(rs.getInt("annee"));
        s.setCapacites(getCaps(rs.getInt("id")));
        return s;
    }

    private List<String> getCaps(int studentId) throws Exception {
        List<String> list = new ArrayList<>();

        String sql = """
            SELECT capacite.capacite 
            FROM student_capacite 
            JOIN capacite ON student_capacite.capacite_id = capacite.id
            WHERE student_capacite.student_id = ?
        """;

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql))
        {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("capacite"));
            }
        }
        return list;
    }
}
