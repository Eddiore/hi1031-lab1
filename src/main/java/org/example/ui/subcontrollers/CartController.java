package org.example.ui.subcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.bo.Facade;
import org.example.ui.ItemDTO;
import org.example.ui.ShoppingCart;

import java.io.IOException;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCartFromSession(request);

        request.setAttribute("cart", cart);
        request.setAttribute("totalPrice", cart.getTotalCost());
        request.setAttribute("contentPage", "cart.jsp");

        request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShoppingCart cart = getCartFromSession(request);

        String action = request.getPathInfo();

        if (action.equals("/add")) {
            String itemId = request.getParameter("itemId");
            ItemDTO item = Facade.getItemById(itemId);

            cart.addItem(item);
            response.sendRedirect(request.getContextPath() + "/controller/products");
        } else if (action.equals("/remove")) {
            String itemId = request.getParameter("itemId");
            ItemDTO item = Facade.getItemById(itemId);

            cart.removeItem(item);
            response.sendRedirect(request.getContextPath() + "/controller/cart");
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
