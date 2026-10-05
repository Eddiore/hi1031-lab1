package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.bo.Facade;
import org.example.ui.ShoppingCart;
import org.example.ui.UserDTO;

import java.io.IOException;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCartFromSession(request);
        if (cart == null || cart.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
//        else if (cart.isEmpty())

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user == null) {
            request.setAttribute("noLoginOrder", "Please login to order items");
            response.sendRedirect(request.getContextPath() + "/controller/profile");
            return;
        }

        request.setAttribute("orderMap", cart.getItems());
        request.setAttribute("priceTotal", cart.getTotalCost());

        request.setAttribute("contentPage", "orderView.jsp");
        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCartFromSession(request);
        if (cart == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user == null) {
            request.setAttribute("noLoginOrder", "Please login to order items");
            response.sendRedirect(request.getContextPath() + "/controller/profile");
            return;
        }

        if (!Facade.placeOrder(user, cart.getItems())) {
            request.setAttribute("failedOrderMsg", "Order failed, please try again!");
            response.sendRedirect(request.getContextPath() + "/controller/order");
        } else {
            request.setAttribute("contentPage", "orderSuccessView.jsp");
            request.getSession().removeAttribute("cart");
            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
        }
    }


    private ShoppingCart getCartFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return (ShoppingCart) session.getAttribute("cart");
    }

}