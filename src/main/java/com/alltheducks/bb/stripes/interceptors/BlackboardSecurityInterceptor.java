package com.alltheducks.bb.stripes.interceptors;

import com.alltheducks.bb.stripes.exceptions.EntitlementException;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import com.alltheducks.bb.stripes.LoginRequired;
import blackboard.data.user.User;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.security.Entitlement;
import blackboard.platform.security.SecurityUtil;
import blackboard.platform.session.BbSession;
import blackboard.platform.session.BbSessionManagerServiceFactory;
import java.lang.reflect.Method;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * A stripes interceptor for enforcing Blackboard security restrictions.<br>
 * Restrictions are based on blackboard Entitlements.
 *
 * @see EntitlementRestrictions
 * @see LoginRequired
 */
@Intercepts({LifecycleStage.ActionBeanResolution, LifecycleStage.HandlerResolution})
public class BlackboardSecurityInterceptor implements Interceptor {

    Logger logger = LoggerFactory.getLogger(BlackboardSecurityInterceptor.class);

    @Override
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        logger.debug("Running the security interceptor for URL: {} in lifecycle stage: {}  And ActionBean: {}",
                ctx.getActionBeanContext().getRequest().getRequestURI(),
                ctx.getLifecycleStage().name(),
                ctx.getActionBean() != null ? ctx.getActionBean().getClass().getName() : "");

        final Resolution resolution = ctx.proceed();
        final ActionBean action = ctx.getActionBean();

        LoginRequired loginRequired = action.getClass().getAnnotation(LoginRequired.class);
        if (loginRequired != null) {
            BbSession bbSession = BbSessionManagerServiceFactory.getInstance().getSession(ctx.getActionBeanContext().getRequest());
            if (!bbSession.isAuthenticated()) {
                return new RedirectResolution("/", false);
            }
        }

        //Get entitlement restrictions from the Class
        EntitlementRestrictions actionBeanRestrictions = action.getClass().getAnnotation(EntitlementRestrictions.class);
        String errorPageUrl = null;
        if (actionBeanRestrictions != null) {
            errorPageUrl = actionBeanRestrictions.errorPage();
        }

        //Get entitlement restrictions from the handler Method
        Method handler = null;
        if (ctx.getLifecycleStage().equals(LifecycleStage.HandlerResolution)) {
            handler = ctx.getHandler();
        }
        EntitlementRestrictions handlerRestrictions = null;
        if (handler != null) {
            handlerRestrictions = handler.getAnnotation(EntitlementRestrictions.class);
        }
        if (handlerRestrictions != null) {
            errorPageUrl = handlerRestrictions.errorPage();
        }

        //Put them all in one array.
        int beanEntitlementsLength = (actionBeanRestrictions == null ? 0 : actionBeanRestrictions.entitlements().length);
        int handlerEntitlementsLength = (handlerRestrictions == null ? 0 : handlerRestrictions.entitlements().length);
        String[] allEntitlements = new String[beanEntitlementsLength + handlerEntitlementsLength];
        int eCounter = 0;
        for (int i = 0; eCounter < beanEntitlementsLength; eCounter++) {
            allEntitlements[eCounter] = actionBeanRestrictions.entitlements()[i++];
        }
        for (int i = 0; eCounter < allEntitlements.length; eCounter++) {
            allEntitlements[eCounter] = handlerRestrictions.entitlements()[i++];
        }

        //Check that the user has ALL the listed entitlements
        Context bbContext = ContextManagerFactory.getInstance().getContext();
        User user = bbContext.getUser();
        for (String entitlment : allEntitlements) {
            if (!SecurityUtil.userHasEntitlement(new Entitlement(entitlment))) {
                logger.warn("Current User: {} Doesn't have entitlement: {}", user.getUserName(), entitlment);
                if (errorPageUrl != null && !errorPageUrl.trim().isEmpty()) {
                    return new RedirectResolution(errorPageUrl);
                } else {
                    throw new EntitlementException(
                            String.format("Current User: %s Doesn't have entitlement: %s",
                            (user == null ? "null" : user.getUserName()), entitlment));
                }
            }
        }
        return resolution;
    }
}
