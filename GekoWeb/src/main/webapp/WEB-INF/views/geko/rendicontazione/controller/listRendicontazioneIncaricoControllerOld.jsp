<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Rendicontazione Obiettivi della Struttura del Dirigente</title>
	<link href="<spring:url value="/resources/bootstrap/css/bootstrap.css" htmlEscape="true" />" rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:16px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:8px;
			border:2px solid darkgray;
		}
	</style>

</head>

<body>
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 

   
<h2>Rendicontazione Obiettivi per <em>${incarico.stringa} </em>- anno ${anno}</h2>
<hr />  
<h4>Responsabile: ${responsabile}</h4>   
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
           
<%@ include file="/WEB-INF/views/geko/rendicontazione/controller/tableObjsRend.jsp" %>


    
<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a></span>
    Ge.Ko. by ing. R. Cirrito [listRendicontazioneIncaricoController.jsp 2015/07/08]
  
</div>
</div>    
</div>    
</body>
</html>
