package org.example.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCartFromSession(request);

        request.setAttribute("cart", cart);

        request.getRequestDispatcher("/cart.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShoppingCart cart = getCartFromSession(request);

        String action = request.getPathInfo();

        if (action.equals("/add")) {
            String itemId = request.getParameter("itemId");
            cart.addItem(itemId);
            response.sendRedirect("/lab1/index.jsp");
        }
    }

    private ShoppingCart getCartFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }
}
