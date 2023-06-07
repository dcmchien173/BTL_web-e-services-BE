package dao;

import model.Register;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Teacher;

public class TeacherDAO extends DBContext {
    public void register(String user_name,
            String password, String email) {
        String query = "insert into [User](username,password,email,role_id) "
                + "Values(?,?,?,1)"; // role_id cua teacher la 1
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user_name);
            ps.setString(2, password);
            ps.setString(3, email);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Register checkAccountExist(String email) {
        Register u = new Register();
        try {
            String query = "select * from [User] where email =?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, email);

            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                u.setEmail(email);
                return u;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Teacher getTeacher(String email, String password) {
        Users u = new Users();
        try {
            String query = "select * from [User] u where \n"
                    + "u.email = ? and password = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, email);
            pd.setString(2, password);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String user_name = rs.getString("username");
                String avatar = rs.getString("avatar");
                int role_id = rs.getInt("role_id");
                String connect = rs.getString("connect");
                String aboutMe = rs.getString("about_me");
                
                u.setEmail(email);
                u.setPassword(password);
                u.setUser_id(id);
                u.setUsername(user_name);
                u.setAvatar(avatar);
                u.setRole_id(role_id);
                u.setConnect(connect);
                u.setAbout_me(aboutMe);
               return u;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Teacher getTeacherByID(String id) {
        Users u = new Users();
        String query = "select * from [User] where user_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                 u = new Users(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("avatar"),
                        rs.getInt("role_id"),
                        rs.getString("about_me"),
                        rs.getString("connect")
                                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return u;
    }

    public Users getTeacherByEmail(String email) {
        Users u = new Users();
        String query = "select * from [User] where email = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, email);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                 u = new Users(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("avatar"),
                        rs.getInt("role_id"),
                        rs.getString("about_me"),
                        rs.getString("connect")
                                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return u;
    }
    
}
