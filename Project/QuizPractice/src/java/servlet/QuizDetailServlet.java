/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.QuizPractice.src.java.servlet;

import dao.QuestionDAO;
import dao.QuizDAO;
import dao.UsersDAO;
import model.Option;
import model.Question;
import model.Quiz;
import model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Dell
 */
@WebServlet(name = "QuizDetailServlet", urlPatterns = {"/QuizDetailServlet"})
public class QuizDetailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuizDetailsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizDetailsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("userSeisson");
        if(user != null){
        String quiz_id = request.getParameter("quizid");
        QuizDAO dao = new QuizDAO();
        UsersDAO udao = new UsersDAO();
        Quiz quiz = dao.getQuizByID(quiz_id);
        Users creator = udao.getUserByID(String.valueOf(quiz.getCreator_id()));
        QuestionDAO quesdao = new QuestionDAO();
        List<Question> listQuestion = quesdao.getAllQuestion(Integer.parseInt(quiz_id));
        List<Option> listOption = quesdao.getAllOption(Integer.parseInt(quiz_id));
//        PrintWriter out = response.getWriter();
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (!dao.checkEnrollmentExist(quiz_id, user.getUser_id()) && user.getRole_id()!=2) {
           dao.addEnrollment(quiz_id, user.getUser_id(), dateNow);
        }
        float avgRate = dao.avgRateOfQuiz(quiz_id);
        request.setAttribute("quiz", quiz);
        request.setAttribute("creator", creator);
        request.setAttribute("listQuestion", listQuestion);
        request.setAttribute("listOption", listOption);
         request.setAttribute("avgRate",  avgRate);
        request.getRequestDispatcher("QuizDetail.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("Home");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("userSeisson");
        if(user != null){
        String quiz_id = request.getParameter("quizid");
        QuizDAO dao = new QuizDAO();
        UsersDAO udao = new UsersDAO();
        Quiz quiz = dao.getQuizByID(quiz_id);
        Users creator = udao.getUserByID(String.valueOf(quiz.getCreator_id()));
        QuestionDAO quesdao = new QuestionDAO();
        List<Question> listQuestion = quesdao.getAllQuestion(Integer.parseInt(quiz_id));
        List<Option> listOption = quesdao.getAllOption(Integer.parseInt(quiz_id));
//        PrintWriter out = response.getWriter();
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (!dao.checkEnrollmentExist(quiz_id, user.getUser_id())) {
           dao.addEnrollment(quiz_id, user.getUser_id(), dateNow);
        }
        float avgRate = dao.avgRateOfQuiz(quiz_id);
        request.setAttribute("quiz", quiz);
        request.setAttribute("creator", creator);
        request.setAttribute("listQuestion", listQuestion);
        request.setAttribute("listOption", listOption);
         request.setAttribute("avgRate",  avgRate);
        request.getRequestDispatcher("QuizDetail.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("Home");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
