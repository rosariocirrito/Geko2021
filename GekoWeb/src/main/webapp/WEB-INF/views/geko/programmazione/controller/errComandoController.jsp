<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Errore Comando Manager</title>
    <link href="<spring:url value="/resources/css/lista.css" htmlEscape="true" />" rel="stylesheet"/>
	
</head>

<body>
<security:authorize access="hasRole('CONTROLLER')">
<div id="wrapper">
            
<h2 > Errore nel comando con ruolo controller</h2>
<h4 > Evento: ${evento}</h4>

<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale -  ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>view: ${viewName} </td>
    </tr>
</table>
</div>   

</div>
  
  
</security:authorize>
</body>

</html>
