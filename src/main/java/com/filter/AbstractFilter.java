package com.filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AbstractFilter {

    public AbstractFilter() {
        super();
    }

    protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/loginOrRegister.xhtml");
        rd.forward(request, response);
    }

    protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/loginOrRegister.xhtml");
        rd.forward(request, response);
    }
}