<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Lista Rendicontazione Obiettivi del Dipartimento</title>
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
<div class="col-sm-12 col"> 

<article>
<section > 
    
<h1 id="titolo-lista">Rendicontazione Obiettivi per <em>${dipartimento.denominazione} </em>- anno ${anno}</h1> 
<hr />  

<c:if test="${empty mapObiettiviDept}">
    <p>Nessun obiettivo trovato per il dipartimento.</p>
</c:if>

<!--  per ogni struttura -->
<c:forEach items="${mapObiettiviDept}" var="mapItem">
	<h3>${mapItem.key.opPersonaGiuridica.denominazione}</h3>
	<h4>Responsabile: ${mapItem.key.opPersonaFisica}</h4>
	<h4>Competenze</h4>  
	<h5>${mapItem.key.opPersonaGiuridica.competenze}</h5>  
	
	<%@ include file="/WEB-INF/views/common/mapTableObjsRend.jsp" %>   
    
    <hr />                       
</c:forEach>
</section>
</article>      
                
<!-- Footer -------------------------->
<div id="footer">      
<table class="footer" >
    <tr>
	    <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
	    Ge.Ko. by Regione Siciliana - Segreteria Generale - 
	    ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listRendicontazioneDipartimentoManager.jsp 2015/07/02] </td>
	</tr>
</table>
</div>
    
</div>
</body>
  
</html>
