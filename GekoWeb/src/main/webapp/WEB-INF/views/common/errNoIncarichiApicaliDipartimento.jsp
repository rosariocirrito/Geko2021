<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Errore Mancanza Incarico dirigenziale</title>
    <link href="<spring:url value="/resources/css/lista.css" htmlEscape="true" />" rel="stylesheet"/>
	
</head>

<body>
<div id="wrapper">
            
<h2 > Non riesco a trovare nessun incarico dirigenziale apicale per il tuo dipartimento per l'anno in questione</h2>
<h3 > Contatta il tuo responsabile del Controllo di Gestione !</h3>

<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale -  ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[errNoIncarichiApicaliDipartimento.jsp] 2020/01/27</td>
    </tr>
</table>
</div>   

</div>
  
  
</body>

</html>
