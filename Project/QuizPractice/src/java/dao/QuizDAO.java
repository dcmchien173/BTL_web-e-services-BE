/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Dell
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Quiz;

public class QuizDAO extends DBContext {

    public List<Quiz> getQuizByPage(List<Quiz> list, int start, int end) {
        List<Quiz> t = new ArrayList<>();
        for (int i = start; i < end; i++) {
            t.add(list.get(i));
        }
        return t;
    }

    public int countUserEnrollAQuiz(String quizID) {
//        List<Quiz> list = new ArrayList<>();
        try {
            String query = "select count(Student_id) from Enrollment\n"
                    + "  where quiz_id = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, quizID);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public Quiz getQuizByID(String id) {
        String query = " select * from Quiz where quiz_id = ?";
        Quiz q = new Quiz();
        QuizDAO dao = new QuizDAO();
        PreparedStatement pd;
        try {
            pd = connection.prepareStatement(query);
            pd.setString(1, id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
                String description = rs.getNString("description");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
//                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
                q.setDescription(description);
//                q.setPrice(price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    public List<Quiz> getRecentQuiz(int user_id) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select * from ((Quiz q  join Enrollment e on q.quiz_id = e.quiz_id) \n"
                    + "join [User] u on e.user_id = u.user_id) where e.user_id =? order by day_enroll desc";// lay quiz_id, creator_id,..
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, user_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> getRecentQuizByName(int user_id, String search) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select * from ((Quiz q  join Enrollment e on q.quiz_id = e.quiz_id) \n"
                    + "join [User] u on e.user_id = u.user_id) where e.user_id =? and [name] like ? order by day_enroll desc";// lay quiz_id, creator_id,..
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, user_id);
            pd.setString(2, "%" + search + "%");
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
//                double price = rs.getDouble("price");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
//                q.setPrice(price);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> getQuizByCreatorID(String creatorId) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select * from Quiz where creator_id =?\n"
                    + "order by last_update desc";// lay quiz_id, 
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, creatorId);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
//                double price = rs.getDouble("price");
                Date date = rs.getDate("last_update");
                Date date2 = rs.getDate("create_date");
                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
//                q.setPrice(price);
                q.setLast_Update(date);
                q.setCreate_date(date2);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> searchQuizByCreatorIDandQuizName(String creatorId, String search) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select * from Quiz where creator_id =? and [name] like ?\n"
                    + "  order by last_update desc";// lay quiz_id, 
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, creatorId);
            pd.setString(2, "%" + search + "%");
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
//                double price = rs.getDouble("price");
                Date date = rs.getDate("last_update");
                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
//                q.setPrice(price);
                q.setLast_Update(date);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> getAllQuiz() {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select * from Quiz ";// lay quiz_id, 
            PreparedStatement pd = connection.prepareStatement(query);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
//                double price = rs.getDouble("price");
                Date date = rs.getDate("last_update");
                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
//                q.setPrice(price);
                q.setLast_Update(date);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> searchByName(String search) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = " select * from quiz where [Quiz_name] like ? ";// lay quiz_id, 
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, "%" + search + "%");
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("quiz_name");
//                double price = rs.getDouble("price");
                Date date = rs.getDate("last_update");
                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
//                q.setPrice(price);
                q.setLast_Update(date);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int countQuestion(int quizID) {
//        List<Quiz> list = new ArrayList<>();
        try {
            String query = "select count(quiz_id) from Question where quiz_id = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, quizID);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public String getTeacher_name(String creator_id) {
        List<Quiz> list = new ArrayList<>();
        try {
            String query = "select top 1 t.Teacher_name from [Teacher] t join Quiz q on t.Teacher_id =q.Teacher_id\n"
                    + "where q.Teacher_id = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, creator_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                return rs.getString("Teacher_name");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Quiz> getTopQuizByUserID(int user_id) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "SELECT Top (100) e.quiz_id, q.name, q.creator_id,Count(*) as countUserEnroll\n"
                    + "FROM Enrollment e inner join Quiz q on q.quiz_id=e.quiz_id\n"
                    + "WHERE Not EXISTS (SELECT 1 FROM Enrollment \n"
                    + "e where e.quiz_id = q.quiz_id and e.user_id = ? ) AND q.creator_id <> ?\n"
                    + "GROUP BY e.quiz_id, q.name,q.creator_id\n"
                    + "order by countUserEnroll desc, NEWID() ";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, user_id);
            pd.setInt(2, user_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("quiz_name");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Quiz> getTopQuiz() {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select top 100 e.quiz_id, q.name,q.creator_id ,Count(*) as countUserEnroll\n"
                    + "from Enrollment e inner join quiz q on e.quiz_id=q.quiz_id\n"
                    + "GROUP BY e.quiz_id, q.name,q.creator_id  order by countUserEnroll desc, NEWID()";
            PreparedStatement pd = connection.prepareStatement(query);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("quiz_name");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuestionNum(dao.countQuestion(quiz_id));
                q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                q.setQuizName(quiz_name);
                list.add(q);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int getLastQuizId(int user_id) {
        QuizDAO dao = new QuizDAO();
        try {
            String query = "select top 1 quiz_id from Quiz where creator_id =?\n"
                    + "order by quiz_id desc";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, user_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                return rs.getInt("quiz_id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void addQuiz(String Teacher_id, String quizName, String description, String create_date) {
        Quiz quiz = new Quiz();
        String query = "insert into Quiz (Teacher_id, quiz_name, description, create_date) values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Teacher_id);
            ps.setString(2, quizName);
            ps.setString(3, description);
            ps.setString(4, create_date);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkEnrollmentExist(String quiz_id, int user_id) {
        String query = "select *from Enrollment\n"
                + "where quiz_id = ? and user_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, quiz_id);
            pd.setInt(2, user_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void addEnrollment(String quiz_id, int user_id, String day_enroll) {
        String query = "insert into enrollment (quiz_id,user_id,day_enroll) values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, quiz_id);
            ps.setInt(2, user_id);
            ps.setString(3, day_enroll);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public double updateQuiz(String quiz_name, String description, String last_update, int quiz_id) {
        String query = "Update Quiz set name=?, description=?,last_update = ?\n"
                + "where quiz_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, quiz_name);
            ps.setString(2, description);
            ps.setString(3, last_update);
            ps.setInt(4, quiz_id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public Quiz getQuizByCreatorIDAndQuizID(String Teacher_id, int quiz_id) {
        String query = "select * from quiz where Teacher_id = ? and quiz_id=?";
        Quiz q = new Quiz();
        QuizDAO dao = new QuizDAO();
        PreparedStatement pd;
        try {
            pd = connection.prepareStatement(query);
            pd.setString(1, Teacher_id);
            pd.setInt(2, quiz_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                String quiz_name = rs.getString("name");
                String description = rs.getNString("description");

                q.setTeacher_id(Teacher_id);
                q.setQuiz_id(quiz_id);
                q.setQuizName(quiz_name);
                q.setDescription(description);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return q;
    }

    public List<Quiz> getQuizByCategories(String[] search) {
        List<Quiz> list = new ArrayList<>();
        QuizDAO dao = new QuizDAO();
        try {
            String query1 = "select COUNT(cq.quiz_id) as COUNT_QUIZ,cq.quiz_id, q.name,q.creator_id from Category_quiz cq \n"
                    + "join Quiz q on (cq.quiz_id=q.quiz_id)\n"
                    + "where cq.category_id in (";
            String ss = "";
            for (int i = 0; i < search.length - 1; i++) {
                ss += "?,";
            }
            ss += "?";
            String query2 = query1 + ss + ") GROUP BY cq.quiz_id,q.name,q.creator_id";
            PreparedStatement pd = connection.prepareStatement(query2);
            for (int i = 0; i < search.length; i++) {
                pd.setString(i + 1, search[i]);
            }
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                Quiz q = new Quiz();
                int quiz_id = rs.getInt("quiz_id");
                int count = rs.getInt("COUNT_QUIZ");
                String Teacher_id = rs.getString("Teacher_id");
                String quiz_name = rs.getString("name");
                if (count == search.length) {
                    q.setQuiz_id(quiz_id);
                    q.setCount(count);
                    q.setTeacher_id(Teacher_id);
                    q.setQuestionNum(dao.countQuestion(quiz_id));
                    q.setTeacher_name(dao.getTeacher_name(Teacher_id));
                    q.setQuizName(quiz_name);
                    list.add(q);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public String getQuizNameByID(String id) {
        String query = " select q.name from Quiz q where q.quiz_id = ?";
        String name = "";
        PreparedStatement pd;
        try {
            pd = connection.prepareStatement(query);
            pd.setString(1, id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    public static void main(String[] args) {
        QuizDAO dao = new QuizDAO();
        System.out.println(dao.getTopQuiz().size());

    }
}
