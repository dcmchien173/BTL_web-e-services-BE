package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import model.ExamReview;
import model.Option;
import model.Question;

public class ExamDAO extends DBContext {

    public List<Question> getNQuestion(String quizId, int numOfQ) {
        List<Question> listQuesInExam = new ArrayList<>();
        String query = "select top (?) *from Question \n"
                + "		where quiz_id = ?\n"
                + "		order by newid()";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setInt(1, numOfQ);
            pd.setString(2, quizId);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                Question examQues = new Question();
                String questionId = rs.getString("question_id");
                String question = rs.getString("question");
                String instruction = rs.getString("intruction");
                List<Option> listOp = new ExamDAO().GetOptionByQuestion(quizId, questionId);

                examQues.setQuiz_id(quizId);
                examQues.setInstruction(instruction);
                examQues.setQuestion_id(questionId);
                examQues.setQuestion(question);
                examQues.setListOption(listOp);
                listQuesInExam.add(examQues);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listQuesInExam;
    }

    public List<Option> GetOptionByQuestion(String quiz_id, String question_id) {
        List<Option> list = new ArrayList<>();
        String query = " select * from [Option]\n"
                + "  where quiz_id = ? and question_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, quiz_id);
            pd.setString(2, question_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                Option op = new Option();
                int op_id = rs.getInt("option_id");
                String op_content = rs.getString("option_content");
                int op_right = rs.getInt("right_option");
                op.setRight_option(op_right);
                op.setOption_id(op_id);
                op.setOption_content(op_content);
                list.add(op);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public void addExam(String Student_id, String quiz_id, float mark, int correct_anwers, int num_of_question, String date, String time) {
        String query = "insert into exam (Student_id, quiz_id, mark, correct_answers, num_of_question, exam_date,time)\n"
                + "values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Student_id);
            ps.setString(2, quiz_id);
            ps.setFloat(3, mark);
            ps.setInt(4, correct_anwers);
            ps.setInt(5, num_of_question);
            ps.setString(6, date);
            ps.setString(7, time);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public int getCurentExamID(String Student_id, String quiz_id) {
        try {
            String query = "select top(1) exam_id from exam \n"
                    + "where Student_id = ? and quiz_id = ? \n"
                    + "order by exam_id desc";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, Student_id);
            pd.setString(2, quiz_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                return rs.getInt("exam_id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void addQuesExam(String exam_id, List<Question> questionReview) {
        String query = "insert into Question_exam(exam_id, question_id, question, question_status)\n"
                + "values (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            for (Question question : questionReview) {
                ps.setString(1, exam_id);
                ps.setString(2, question.getQuestion_id());
                ps.setString(3, question.getQuestion());
                ps.setInt(4, question.getCheckQuestion());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

    public void addOptionExam(String exam_id, List<Option> optionReview) {
        String query = "insert into Option_question_exam (exam_id,question_id,option_id,[option],right_option,option_status)\n"
                + "values (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            for (Option option : optionReview) {
                ps.setString(1, exam_id);
                ps.setString(2, option.getQuestion_id());
                ps.setInt(3, option.getOption_id());
                ps.setString(4, option.getOption_content());
                ps.setInt(5, option.getRight_option());
                ps.setInt(6, option.getOption_status());
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }

    }

    public String getQuizNamebyID(String quiz_id) {
        try {
            String query = "select q.[quiz_name] from Quiz q\n"
                    + "where q.quiz_id = ?";
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, quiz_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                return rs.getString("quiz_name");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<ExamReview> getExamReviewByTeacherId(String Teacher_id) {
        List<ExamReview> list = new ArrayList<>();
        String query = "select * from exam e \n"
                + "where e.Student_id =?\n"
                + "order by exam_id desc";
        try {

            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, Teacher_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                ExamReview examReview = new ExamReview();
                ExamDAO dao = new ExamDAO();
                String exam_id = rs.getString("exam_id");

                String quiz_id = rs.getString("quiz_id");
                String quiz_name = dao.getQuizNamebyID(quiz_id);
                float mark = rs.getFloat("mark");
                int correct_answers = rs.getInt("correct_answers");
                int num_of_question = rs.getInt("num_of_question");
                String date = rs.getString("exam_date");
                String time = rs.getString("time");

                examReview.setExam_id(exam_id);
                examReview.setTeacher_id(Teacher_id);
                examReview.setQuiz_id(quiz_id);
                examReview.setQuiz_name(quiz_name);
                examReview.setDate(date);
                examReview.setCorrect_answers(correct_answers);
                examReview.setNum_of_question(num_of_question);
                examReview.setTime(time);
                examReview.setMark(mark);
                list.add(examReview);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ExamReview> getExamReviewByQuizID(String Teacher_id, String quiz_id) {
        List<ExamReview> list = new ArrayList<>();
        String query = "select * from exam \n"
                + "where Teacher_id =? and quiz_id=?\n"
                + "order by exam_id desc";
        try {

            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, Teacher_id);
            pd.setString(2, quiz_id);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                ExamReview examReview = new ExamReview();
                ExamDAO dao = new ExamDAO();
                String exam_id = rs.getString("exam_id");
                String quiz_name = dao.getQuizNamebyID(quiz_id);
                float mark = rs.getFloat("mark");
                int correct_answers = rs.getInt("correct_answers");
                int num_of_question = rs.getInt("num_of_question");
                String date = rs.getString("exam_date");
                String time = rs.getString("time");

                examReview.setExam_id(exam_id);
                examReview.setTeacher_id(Teacher_id);
                examReview.setQuiz_id(quiz_id);
                examReview.setQuiz_name(quiz_name);
                examReview.setDate(date);
                examReview.setCorrect_answers(correct_answers);
                examReview.setNum_of_question(num_of_question);
                examReview.setTime(time);
                examReview.setMark(mark);
                list.add(examReview);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Option> optionReview(String[] listExam) {
        List<Option> examReview = new ArrayList<>();
        for (int i = 0; i < listExam.length; i++) {
            Option ex = new Option();
            String[] words = listExam[i].split("\\-");
            ex.setQuestion_id(words[0]);
            ex.setOption_id(Integer.parseInt(words[1]));
            ex.setRight_option(Integer.parseInt(words[2]));
            int start = words[0].length() + words[1].length() + words[2].length() + 3;
            int end = listExam[i].length() - words[words.length - 1].length() - 1;
            ex.setOption_content(listExam[i].substring(start, end));
            ex.setOption_status(Integer.parseInt(words[words.length - 1]));
            examReview.add(ex);
        }
        return examReview;
    }

    public List<Question> questionReview(String[] listQues, List<Option> optionReview) {
        List<Question> quesReview = new ArrayList<>();
        for (int i = 0; i < listQues.length; i++) {
            boolean chekQues = true;
            String[] words = listQues[i].split("\\-");   // word 0 là question id
            for (Option opReview : optionReview) {
                if (opReview.getQuestion_id() == words[0] && opReview.getOption_status() != opReview.getRight_option()) {
                    chekQues = false;
                }
            }
            Question ques = new Question();
            ques.setQuestion_id(words[0]);
            ques.setQuestion(listQues[i].substring(words[0].length() + 1));
            if (chekQues) {
                ques.setCheckQuestion(1);
            } else {
                ques.setCheckQuestion(0);
            }
            quesReview.add(ques);
        }
        return quesReview;
    }

    public int numOfCorrect(List<Question> questionReview) {
        int count = 0;
        for (Question question : questionReview) {
            if (question.getCheckQuestion() == 1) {
                count++;
            }
        }
        return count;
    }

    public ExamReview getExamById(String examId) {
        ExamReview examReview = new ExamReview();
        String query = "select * from exam \n"
                + "where exam_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, examId);
            ResultSet rs = pd.executeQuery();

            while (rs.next()) {
                ExamDAO dao = new ExamDAO();
                String quiz_id = rs.getString("quiz_id");
                String quiz_name = dao.getQuizNamebyID(quiz_id);
                float mark = rs.getFloat("mark");
                String Teacher_id = rs.getString("Teacher_id");
                int correct_answers = rs.getInt("correct_answers");
                int num_of_question = rs.getInt("num_of_question");
                String date = rs.getString("exam_date");
                String time = rs.getString("time");

                examReview.setExam_id(examId);
                examReview.setTeacher_id(Teacher_id);
                examReview.setQuiz_id(quiz_id);
                examReview.setQuiz_name(quiz_name);
                examReview.setDate(date);
                examReview.setCorrect_answers(correct_answers);
                examReview.setNum_of_question(num_of_question);
                examReview.setTime(time);
                examReview.setMark(mark);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return examReview;
    }

    public List<Question> getQuesInExam(String exam_id) {
        List<Question> listQues = new ArrayList<>();
        String query = " select question_id, question,question_status from Question_exam  where exam_id = ? ";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, exam_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                Question ques = new Question();
                ques.setQuestion_id(rs.getString("question_id"));
                ques.setQuestion(rs.getString("question"));
                ques.setCheckQuestion(rs.getInt("question_status"));
                ExamDAO examDAO = new ExamDAO();
                List<Option> listOption = examDAO.getOptionInExam(exam_id, rs.getString("question_id"));
                ques.setListOption(listOption);
                listQues.add(ques);
            }
        } catch (Exception e) {
        }
        return listQues;
    }

    public List<Option> getOptionInExam(String exam_id, String question_id) {
        List<Option> listOp = new ArrayList<>();
        String query = "select question_id, option_id,[option], right_option, option_status \n"
                + "from Option_question_exam  \n"
                + "where exam_id = ? and question_id = ?";
        try {
            PreparedStatement pd = connection.prepareStatement(query);
            pd.setString(1, exam_id);
            pd.setString(2, question_id);
            ResultSet rs = pd.executeQuery();
            while (rs.next()) {
                Option option = new Option();
                option.setOption_id(rs.getInt("option_id"));
                option.setOption_content(rs.getString("option"));
                option.setRight_option(rs.getInt("right_option"));
                option.setOption_status(rs.getInt("option_status"));
                listOp.add(option);
            }
        } catch (Exception e) {
        }
        return listOp;
    }

    public static void main(String[] args) {
        ExamDAO dao = new ExamDAO();
        List<Option> listop = new ArrayList<>();
        listop = dao.GetOptionByQuestion("37", "9");
        for (Option option : listop) {
            System.out.println(option);
        }
    }

}
