package fr.insa.ms.studentService.repository;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class AuthRepository {

    private final String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_063";
    private final String user = "projet_gei_063";
    private final String pass = "eeph3Jae";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // generate and store token
    public String createToken(int studentId){
        String token = UUID.randomUUID().toString().replace("-", "");
        String sql = "INSERT INTO auth_token(token, student_id, created_at) VALUES (?,?,?)";

        try(Connection c = connect();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, token);
            ps.setInt(2, studentId);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            return token;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // token -> studentId 
    public Integer verifyToken(String token){
        String sql = "SELECT student_id FROM auth_token WHERE token=?";
        try(Connection c=connect();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("student_id");
            }
            return null;
        }catch(Exception e){
        	return null;
        }
        
    }

    public boolean deleteToken(String token){
        String sql = "DELETE FROM auth_token WHERE token=?";
        try(Connection c=connect(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1, token);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

