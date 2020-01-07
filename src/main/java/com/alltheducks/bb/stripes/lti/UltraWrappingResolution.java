package com.alltheducks.bb.stripes.lti;

import blackboard.data.course.Course;
import blackboard.platform.context.ContextManagerFactory;

import javax.servlet.http.HttpServletRequest;

public class UltraWrappingResolution extends WrappingResolution {

    public UltraWrappingResolution(final String path) {
        super(path);
    }

    public UltraWrappingResolution(final String classicPath, final String ltiPath) {
        super(classicPath, ltiPath);
    }

    @Override
    public boolean isLti(final HttpServletRequest request) {
        final Course course = ContextManagerFactory.getInstance().getContext().getCourse();
        return course.getUltraStatus().isUltra();
    }
}

