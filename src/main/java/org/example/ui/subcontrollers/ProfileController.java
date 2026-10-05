package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;
import org.example.ui.UserDTO;

import java.io.IOException;

@WebServlet("/profile/*")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String path = request.getPathInfo();

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user != null) {
            request.setAttribute("contentPage", "profile.jsp");
            request.setAttribute("assignedOrders", Facade.getAssignedOrders(user.id()));

        } else {
            request.setAttribute("contentPage", "login.jsp");
        }

        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path.equals("/login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UserDTO user = Facade.loginAsUser(username, password);

            if (user != null) {
                request.getSession().setAttribute("user", user);

                response.sendRedirect(request.getContextPath() + "/controller/profile");
                return;
            }

            // --- Login failed ---
            request.setAttribute("loginError","Invalid username or password");

            request.setAttribute("contentPage", "login.jsp");

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
        } else if (path.equals("/logout")) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/controller/home");
        }
    }
}
