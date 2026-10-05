package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;
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

//        request.setAttribute("contentPage", "orderView.jsp");
//        request.setAttribute("orderList", Facade.getOrdersByStatus());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkIfAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

    }


    private boolean checkIfAuthorized(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        return user != null && (user.role() == UserRole.ADMIN || user.role() == UserRole.STAFF);
    }
}
