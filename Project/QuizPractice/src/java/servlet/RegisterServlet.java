
package Project.QuizPractice.src.java.servlet;

import dao.UsersDAO;
import model.Register;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "VerifyUser", urlPatterns = {"/VerifyUser"})
public class RegisterServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
        String username = request.getParameter("user_name");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String re_pass = request.getParameter("re_password");
        
        if (!pass.equals(re_pass)) {
            request.setAttribute("message1", "The re-entered password does not match the entered password!");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else{
            UsersDAO dao = new UsersDAO();
            Register emailExist = dao.checkAccountExist(email);
            
           if(emailExist==null){

            dao.register(username, pass, email);
            request.getRequestDispatcher("Login.jsp").forward(request, response);

        }
            else{
                request.setAttribute("message2", "Email already exists, please use another email!");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
        }  
    }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
