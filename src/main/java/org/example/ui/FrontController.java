package org.example.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bo.Facade;

import java.io.IOException;

@WebServlet(urlPatterns = "/controller/*")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        System.out.println("PATH:" + path);
        if (path.equals("/home")) {
            request.setAttribute("contentPage", "home.jsp");

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);

            return;
        } else if (path.equals("/products")) {
            request.setAttribute("contentPage", "products.jsp");

            request.setAttribute("productList", Facade.getAllItems());

            request.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(request, response);

            return;
        }

        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        request.getRequestDispatcher(path).forward(request, response);
    }
}
