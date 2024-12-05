<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Errore Mancanza Incarico</title>
    <link href="<spring:url value="/resources/css/lista.css" htmlEscape="true" />" rel="stylesheet"/>
	
</head>

<body>
<security:authorize access="hasRole('MANAGER')">
<div id="wrapper">
            
<h2 > Manca l'incarico di dirigente responsabile della struttura!</h2>
<h3 > Contatta il tuo responsabile del Controllo di Gestione!</h3>

<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale -  ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[errNoResponsabileStruttura.jsp] </td>
    </tr>
</table>
</div>   

</div>
  
  
</security:authorize>
</body>

</html>
