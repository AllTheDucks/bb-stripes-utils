package com.alltheducks.bb.stripes.lti;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WrappingResolution implements Resolution {

    private final String classicPath;
    private final String ltiPath;

    public WrappingResolution(final String path) {
        this.classicPath = path;
        this.ltiPath = path;
    }

    public WrappingResolution(final String classicPath, final String ltiPath) {
        this.classicPath = classicPath;
        this.ltiPath = ltiPath;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String wrapperPath;
        final String innerJsp;
        if(this.isLti(request)) {
            wrapperPath = request.getServletContext().getInitParameter("atd.wrapper.lti.servletpath");
            innerJsp = this.ltiPath;
        } else {
            wrapperPath = request.getServletContext().getInitParameter("atd.wrapper.classic.servletpath");
            innerJsp = this.classicPath;
        }

        final ForwardResolution forwardResolution;
        if(wrapperPath == null) {
            forwardResolution = new ForwardResolution(innerJsp);
        } else {
            request.setAttribute("innerJsp", innerJsp);
            forwardResolution = new ForwardResolution(wrapperPath);
        }

        forwardResolution.execute(request, response);
    }

    public abstract boolean isLti(final HttpServletRequest request);
}
