package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;
import org.example.bo.enums.UserRole;
import org.example.ui.ItemDTO;
import org.example.ui.UserDTO;

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
            return;
        } else if (path.equals("/products")) {
            request.setAttribute("contentPage", "adminProductView.jsp");
            request.setAttribute("productList", Facade.getAllItems());

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
            return;
        } else if (path.startsWith("/editUser")) {
            String id = request.getParameter("id");

            request.setAttribute("selectedUser", Facade.getUserById(id));
            request.setAttribute("contentPage", "editUserView.jsp");

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
            return;
        } else if (path.startsWith("/editProduct")) {
            String itemId = request.getParameter("id");

            request.setAttribute("selectedItem", Facade.getItemById(itemId));
            request.setAttribute("contentPage", "editProductView.jsp");

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
                    Integer.parseInt(request.getParameter("id")));
            if (Facade.updateUser(updatedUser)) {
                response.sendRedirect(request.getContextPath() + "/controller/admin/users");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        } else if (path.equals("/updateProduct")) {
            ItemDTO updatedItem = new ItemDTO(
                    request.getParameter("name"),
                    request.getParameter("description"),
                    request.getParameter("category"),
                    Integer.parseInt(request.getParameter("price")),
                    Integer.parseInt(request.getParameter("stock")),
                    Integer.parseInt(request.getParameter("id")));

            if (Facade.updateItem(updatedItem)) {
                response.sendRedirect(request.getContextPath() + "/controller/admin/products");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private boolean checkIfadmin(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        return user != null && user.role() == UserRole.ADMIN;
    }
}
