package dao;

import model.Register;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Teacher;
import model.Quiz;

public class TeacherDAO extends DBContext {
    public void register(String Teacher_name,
            String Teacher_password, String Teacher_email) {
        String query = "insert into [Teacher](Teacher_name,Teacher_password,Teacher_email,role_id) "
                + "Values(?,?,?,1)"; // role_id cua teacher la 1
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Teacher_name);
            ps.setString(2, Teacher_password);
            ps.setString(3, Teacher_email);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Register checkAccountExist(String email) {
        Register t = new Register();
        try {
            String query = "select * from [Teacher] where Teacher_email =?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, email);

            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                t.setTeacherEmail(email);
                return t;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Teacher getTeacher(String Teacher_email, String Teacher_password) {
    Teacher t = new Teacher();
        try {
            String query = "select * from [Teacher] t where \n"
                    + "t.Teacher_email = ? and Teacher_password = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, Teacher_email);
            pd.setString(2, Teacher_password);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Teacher_id");
                String user_name = rs.getString("Teacher_name");
                String avatar = rs.getString("Teacher_avatar");
                int role_id = rs.getInt("role_id");
                String connect = rs.getString("Teaher_connect");
                String aboutMe = rs.getString("Teacher_about_me");
                
                t.setTeacherEmail(Teacher_email);
                t.setTeacher_Password(Teacher_password);
                t.setTeacher_id(Teacher_id);
                t.setTeacher_name(Teacher_name);
                t.setAvatar(Teacher_avatar);
                t.setRole_id(role_id);
                t.setTeacher_Connect(Teacher_connect);
                t.setTeacher_About_me(Teacher_about_me);
               return t;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Teacher getTeacherByID(String id) {
        Teacher t = new Teacher();
        String query = "select * from [Teacher] where Teacher_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                 t = new Teacher(rs.getInt("Teacher_id"),
                        rs.getString("Teacher_name"),
                        rs.getString("Teacher_password"),
                        rs.getString("Teacher_email"),
                        rs.getString("Teacher_avatar"),
                        rs.getInt("role_id"),
                        rs.getString("Teacher_about_me"),
                        rs.getString("Teacher_connect")
                                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return t;
    }

    public Teacher getTeacherByEmail(String email) {
        Teacher t = new Teacher();
        String query = "select * from [Teacher] where Teacher_email = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, email);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                 t = new Teacher(rs.getInt("Teacher_id"),
                        rs.getString("Teacher_name"),
                        rs.getString("Teacher_password"),
                        rs.getString("Teacher_email"),
                        rs.getString("aTeacher_vatar"),
                        rs.getInt("role_id"),
                        rs.getString("Teacher_about_me"),
                        rs.getString("Teacher_connect")
                                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return t;
    }

    public void createClass(String Class_id, String Class_name, int num_of_students){
        String query = "insert into [Class](Class_id,Class_name,num_of_students,Teacher_id) "
        + "Values(?,?,?,Teacher_id)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Class_id);
            ps.setString(2, Class_name);
            ps.setInt(3, num_of_students);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStudentToClass(String Class_id, String Student_id){
        String query = "insert into [Class_Student](Class_id,Student_id)"
        + "Values(?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Class_id);
            ps.setString(2, Student_id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addQuizToclass(String Class_id, String Quiz_id){
        String query = "insert into [Class](Quiz_id) where Class_id = ?"
        + "Values(?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Class_id);
            ps.setString(2, Quiz_id);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
