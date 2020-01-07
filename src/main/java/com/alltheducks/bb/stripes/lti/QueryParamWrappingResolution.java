package com.alltheducks.bb.stripes.lti;

import javax.servlet.http.HttpServletRequest;

public class QueryParamWrappingResolution extends WrappingResolution {

    public QueryParamWrappingResolution(final String path) {
        super(path);
    }

    public QueryParamWrappingResolution(final String classicPath, final String ltiPath) {
        super(classicPath, ltiPath);
    }

    @Override
    public boolean isLti(final HttpServletRequest request) {
        return request.getParameter(LtiConstants.LTI_PARAM_NAME) != null;
    }
}
