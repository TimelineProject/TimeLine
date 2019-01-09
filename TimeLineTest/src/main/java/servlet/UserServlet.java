package servlet;

import bean.User;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet{

    private UserDao userDao = new UserDao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("method").equals("login")) {
                this.login(request, response);
            } else if (request.getParameter("method").equals("register")) {
                this.register(request, response);
            }
        } catch (Throwable var4) {
            var4.printStackTrace();
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Throwable {
        request.setCharacterEncoding("UTF-8");
        String userName = new String(request.getParameter("lgn_username").getBytes("ISO-8859-1"), "utf-8");
        String password = request.getParameter("lgn_password");
        //System.out.println(userName);
        //System.out.println(password);
        if(userName.equals("")|| password.equals("")){
            request.setAttribute("flag", "1");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }
        User user = this.userDao.findUser(userName,password);
        if (user == null) {
            request.setAttribute("flag", "0");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getUserName());
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            return;
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        String userName = new String(request.getParameter("reg_username").getBytes("ISO-8859-1"), "utf-8");
        //System.out.println(userName);
        String password1 = request.getParameter("reg_password1");
        //System.out.println(password1);
        String password2 = request.getParameter("reg_password2");
        //System.out.println(password2);
        if(userName.equals("") || password1.equals("") || password2.equals("")){
            request.setAttribute("flag", "3");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }
        if(!password1.equals(password2)){
            request.setAttribute("flag", "2");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }
        boolean existUserName = this.userDao.existUserName(userName);
        if (existUserName == true) {
            request.setAttribute("flag", "0");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }else {
           this.userDao.addNewUser(userName,password1);
           request.setAttribute("flag", "1");
           request.getRequestDispatcher("Register.jsp").forward(request, response);
           return;
        }
    }

}
