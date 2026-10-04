package org.example.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;
import org.example.bo.enums.UserRole;

import java.io.IOException;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkIfadmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();

        if (path.equals("/users")) {
            request.setAttribute("contentPage", "adminUserView.jsp");
            request.setAttribute("userList", Facade.getAllUsers());

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);

        } else if (path.equals("/products")) {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

        } else if (path.startsWith("/editUser")) {
            String id = request.getParameter("id");

            request.setAttribute("selectedUser", Facade.getUserById(id));
            request.setAttribute("contentPage", "editUserView.jsp");

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkIfadmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();

        if (path.equals("/updateUser")) {
            UserDTO updatedUser = new UserDTO(
                    request.getParameter("username"),
                    request.getParameter("passwordHash"),
                    UserRole.valueOf(request.getParameter("role")),
                    Integer.parseInt(request.getParameter("id"))
                    );
            if (Facade.updateUser(updatedUser)) {
                response.sendRedirect(request.getContextPath() + "/controller/admin/users");
            } else {
                //Add error message to ui
                response.sendRedirect(request.getContextPath() + "/controller/admin/users");
            }
        }
    }

    private boolean checkIfadmin(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        return user != null && user.role() == UserRole.ADMIN;
    }
}