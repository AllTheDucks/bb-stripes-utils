package com.alltheducks.bb.stripes.exceptions;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.exception.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wiley on 1/10/15.
 */
public class BbExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("checking exceptions");
        if (throwable instanceof EntitlementException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("<html><body><h1>Not Authorised!!</h1></body></html>");
        } else {
            throw new ServletException(throwable);
        }
    }

    @Override
    public void init(Configuration configuration) throws Exception {

    }
}
