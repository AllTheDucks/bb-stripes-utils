package com.alltheducks.bb.stripes;

import com.alltheducks.bb.stripes.interceptors.BlackboardSecurityInterceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * This is a Class and Method level annotation for specifying which Blackboard Entitlements
 * users must have in order to access this class. <br>
 * Users must have <b>ALL</b> the specified entitlements to be allowed access to the resource.
 * These entitlements are enforced by the {@link BlackboardSecurityInterceptor}
 * Stripes interceptor.<br>
 * e.g. @EntitlementRestrictions(entitlements = {"course.content.CREATE", "course.content.MODIFY"}, errorPage = "/error/noaccess.jsp")<br>
 *
 * @see BlackboardSecurityInterceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EntitlementRestrictions {
    String[] entitlements();
    String errorPage() default "";
}
