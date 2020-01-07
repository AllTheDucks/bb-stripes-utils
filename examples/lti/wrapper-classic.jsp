<!DOCTYPE html>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:useBean id="innerJsp" type="java.lang.String" scope="request"/>

<bbNG:learningSystemPage ctxId="ctx" navItem="atd-outline-nav-outline-course">
    <jsp:include page="${innerJsp}" />
</bbNG:learningSystemPage>