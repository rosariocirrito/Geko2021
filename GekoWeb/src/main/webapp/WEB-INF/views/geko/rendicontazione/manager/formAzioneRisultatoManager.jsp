<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Modifica Risultato Azione</title>
    <link href="<spring:url value="/resources/css/mask.css" htmlEscape="true" />" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">
        
<c:choose>
	<c:when test="${azione.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<h3>Modifica Risultato Azione
</h3>



<form:form modelAttribute="azione" method="${method}">
    
<fieldset>
<h2 class="info"> Dati descrittivi </h2>
<ul>
	<li>
		Denominazione: ${azione.denominazione}
	</li>
	<li>
	    Descrizione: ${azione.descrizione}
	</li>
	<li>
	    Note: ${azione.note}
	</li>
</ul>
</fieldset> 
    
<fieldset>
<h2 class="tipologia">Tipologia Azione</h2>
<ul>
<li>
    Indicatore: ${azione.indicatore}
</li>
<li>
    Valore Obiettivo: ${azione.prodotti}
</li>

<li>
    Scadenza: <fmt:formatDate value="${azione.scadenza}" pattern="dd/MM/yyyy"/>
</li>

<li>
    Peso: ${azione.peso}
</li>

</ul>
</fieldset>
    
<fieldset>
<h2 class="stato">Risultato Azione</h2>
<ul>
	<li>
    <security:authorize access="hasRole('MANAGER')">
        <form:label for="risultato" path="risultato">Risultato</form:label>
        <form:input path="risultato" size="100" maxlength="150"/>
    </security:authorize>
	</li>
</ul>
</fieldset>


<fieldset>
<h2 class="stato">Comandi</h2>
<ul>
  <li>    
    <p class="button"><input type="submit" name="update" value="Aggiorna Risultato Azione"/></p>
    <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
  </li>
</ul>
</fieldset>
        
    
        
</form:form>
</div>

<p><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </p>
<p>[view: formAzioneRisultatoManager.jsp]</p>
    
</body>

</html>