package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;
import org.example.bo.enums.OrderStatus;
import org.example.bo.enums.UserRole;
import org.example.ui.UserDTO;

import java.io.IOException;

@WebServlet("/warehouse/*")
public class WarehouseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkIfAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("contentPage", "orderManagementView.jsp");
        request.setAttribute("orderList", Facade.getOrdersByStatus(OrderStatus.PLACED));

        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkIfAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();


        if (path.equals("/assign")) {
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            if (Facade.assignOrderToStaff(orderId, user.id())) {
                response.sendRedirect(request.getContextPath() + "/controller/warehouse");
                return;
            }
            // Failed
            return;
        } else if (path.equals("/package")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            if (Facade.packageOrder(orderId)) {
                response.sendRedirect(request.getContextPath() + "/controller/profile");
                return;
            }
            // Fail
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }


    private boolean checkIfAuthorized(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        return user != null && (user.role() == UserRole.ADMIN || user.role() == UserRole.STAFF);
    }
}
