<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Errore Mancanza Incarico Pop</title>
    <link href="<spring:url value="/resources/css/lista.css" htmlEscape="true" />" rel="stylesheet"/>
	
</head>

<body>
<div id="wrapper">
            
<h2 > Non riesco a trovare nessun incarico POP per l'anno in questione</h2>
<h3 > Contatta il tuo responsabile del Controllo di Gestione e fagli verificare l'inizio e la scadenza del tuo incarico POP!</h3>

<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_RESP_POP" htmlEscape="true" />">Menu Responsabile Pop</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale -  ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[errNoIncarichiPop.jsp] 2021/01/12</td>
    </tr>
</table>
</div>   

</div>
  
  
</body>

</html>
