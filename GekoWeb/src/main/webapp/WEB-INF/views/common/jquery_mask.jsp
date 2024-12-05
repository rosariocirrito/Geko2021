<spring:url value="/resources/js/jquery-1.8.2.js" var="jquery_url" />
<spring:url value="/resources/js/jquery-ui-1.9.1.custom.min.js" var="jquery_ui_url" />
<spring:url value="/resources/css/smoothness/jquery-ui-1.9.1.custom.css" var="jquery_ui_theme_css" />
<script src="${jquery_url}" type="text/javascript"><jsp:text/></script>
<script src="${jquery_ui_url}" type="text/javascript"><jsp:text/></script>     
<link rel="stylesheet" type="text/css" media="screen" href="${jquery_ui_theme_css}" />
    
<spring:url value="/resources/css/mask.css" var="mask_css" />   
<link rel="stylesheet" type="text/css" media="screen" href="${mask_css}" /> 