<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Modifica Risultato Azione</title>
    <link href="../../resources/css/mask.css" rel="stylesheet"/>
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
  <table width="80%">
    <tr>
        <td width="20%"><form:label for="denominazione" path="denominazione">Denominazione</form:label></td>
        <td>${azione.denominazione}</td>
    </tr>
    <tr>
        <td><form:label for="descrizione" path="descrizione">Descrizione</form:label></td>
        <td>${azione.descrizione}</td>
    </tr>
    <tr>
        <td><form:label for="note" path="note">Note</form:label></td>
        <td>${azione.note}</td>
    </tr>
    </table>
    </fieldset> 
    
    <fieldset>
        <h2 class="tipologia">Tipologia Azione</h2>
        <table width="80%">
	        <tr>
	        <td><form:label for="indicatore" path="indicatore">Indicatore</form:label></td>
	        <td>${azione.indicatore}</td>
	    </tr>
	    <tr>
	        <td width="20%"><form:label for="prodotti" path="prodotti">Prodotti</form:label></td>
	        <td>${azione.prodotti}</td>
	    </tr>
	    
	    <tr>
	        <td><form:label for="scadenza" path="scadenza">Scadenza</form:label></td>
	        <td><fmt:formatDate value="${azione.scadenza}" pattern="dd/MM/yyyy"/></td>
	    </tr>
	    
	    <tr>
	        <td ><form:label for="peso" path="peso">Peso</form:label></td>
	        <td>${azione.peso}</td>
	    </tr>

    </table>
    </fieldset>
    
    <fieldset>
        <table width="80%">
        <h2 class="stato">Risultato Azione</h2>
    <tr>
        <security:authorize access="hasRole('CONTROLLER')">
            <td><form:label for="risultato" path="risultato">Risultato</form:label></td>
            <td><form:input path="risultato" size="100" maxlength="150"/></td>
        </security:authorize>
    </tr>
    </table>
    </fieldset>
    

    <fieldset>
        <table  width="80%">
          <tr>    
            <td><p class="button"><input type="submit" name="update" value="Aggiorna Risultato Azione"/></p></td>
            <td><p class="button"><input type="submit" name="cancel" value="Esci"/></p></td>
          </tr>
        </table>
    </fieldset>
        
    
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formAzioneRisultatoApicaleController.jsp 02/07/2014]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>