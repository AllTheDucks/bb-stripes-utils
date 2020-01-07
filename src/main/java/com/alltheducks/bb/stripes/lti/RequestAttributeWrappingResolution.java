package com.alltheducks.bb.stripes.lti;

import javax.servlet.http.HttpServletRequest;

public class RequestAttributeWrappingResolution extends WrappingResolution {

    public RequestAttributeWrappingResolution(final String path) {
        super(path);
    }

    public RequestAttributeWrappingResolution(final String classicPath, final String ltiPath) {
        super(classicPath, ltiPath);
    }

    @Override
    public boolean isLti(final HttpServletRequest request) {
        return request.getAttribute(LtiConstants.LTI_REQUEST_ATTRIBUTE) == Boolean.TRUE;
    }
}

