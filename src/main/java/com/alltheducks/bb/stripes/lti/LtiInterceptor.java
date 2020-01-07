package com.alltheducks.bb.stripes.lti;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import javax.servlet.http.HttpServletRequest;

@Intercepts(LifecycleStage.ActionBeanResolution)
public class LtiInterceptor implements Interceptor {

    @Override
    @Before
    public Resolution intercept(final ExecutionContext context) throws Exception {
        final HttpServletRequest request = context.getActionBeanContext().getRequest();

        request.setAttribute(LtiConstants.LTI_REQUEST_ATTRIBUTE, request.getParameter(LtiConstants.LTI_PARAM_NAME) != null);

        final String method = request.getMethod();

        if(!method.equals("POST")) {
            return context.proceed();
        }

        final String courseId = request.getParameter("course_id");

        if(courseId != null && !courseId.trim().isEmpty()) {
            return context.proceed();
        }

        final String ltiCourseId = request.getParameter("custom_course_id");
        if(ltiCourseId == null) {
            return context.proceed();
        }

        request.setAttribute(LtiConstants.LTI_REQUEST_ATTRIBUTE, true);
        return new RedirectResolution(this.buildUri(request, ltiCourseId), false);
    }

    private String buildUri(final HttpServletRequest request, final String courseId) {
        final String queryString = request.getQueryString();
        final String requestUri = request.getRequestURI();

        final StringBuilder sb = new StringBuilder();

        if(!requestUri.startsWith("/")) {
            sb.append("/");
        }
        sb.append(requestUri);
        sb.append("?");
        if(queryString != null){
            sb.append(queryString);
            sb.append("&");
        }
        sb.append("course_id=");
        sb.append(courseId);
        sb.append("&");
        sb.append(LtiConstants.LTI_PARAM_NAME);

        return sb.toString();
    }

}
