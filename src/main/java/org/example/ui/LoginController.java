package org.example.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;

import java.io.IOException;

@WebServlet("/login/*")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user !=  null) {
            response.sendRedirect(request.getContextPath() + "/controller/home");

            return;
        }

        request.setAttribute("contentPage", "login.jsp");

        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO user = Facade.loginAsUser(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);

            response.sendRedirect(request.getContextPath() + "/controller/home");
            return;
        }

        // --- Login failed ---
        request.setAttribute("loginError","Invalid username or password");

        request.setAttribute("contentPage", "login.jsp");

        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }
}
