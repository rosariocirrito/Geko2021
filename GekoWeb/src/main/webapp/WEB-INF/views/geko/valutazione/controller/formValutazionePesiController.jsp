<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Valutazione Pesi </title>
        
        
    <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<c:choose>
	<c:when test="${valutazione.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="post"/></c:otherwise>
</c:choose>

<h3>
	<c:if test="${valutazione.nuovo}">Nuovo Comportamento Organizzativo</c:if>
	<c:if test="${!valutazione.nuovo}">Comportamento Organizzativo</c:if>
	per incarico di </br> 
	${valutazione.incarico.opPersonaFisica.order} /
	${valutazione.incarico.opPersonaGiuridica.denominazione}
</h3>
            

<form:form modelAttribute="valutazione" method="${method}">
    
    <fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
  	<tr>
        <td><form:label for="anno" path="anno">Anno</form:label></td>
        <td><form:input path="anno" size="4" maxlength="4"/> </td>
        <td><form:errors path="anno" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="pdlAss" path="pdlAss">Attuazione Piano di lavoro Peso Attribuito</form:label></td>
        <td><form:input path="pdlAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="pdlAss" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="analProgrAss" path="analProgrAss">Capacità di Analisi e Programmazione Peso Attribuito</form:label></td>
        <td><form:input path="analProgrAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="analProgrAss" cssClass="errors"/></td>
    </tr>
    
    <tr>
        <td><form:label for="relazCoordAss" path="relazCoordAss">Capacità di Relazione e Coordinamento Peso Attribuito</form:label></td>
        <td><form:input path="relazCoordAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="relazCoordAss" cssClass="errors"/></td>
    </tr>
    
    <tr>
        <td><form:label for="gestRealAss" path="gestRealAss">Capacità di Gestione e Realizzazione Peso Attribuito</form:label></td>
        <td><form:input path="gestRealAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="gestRealAss" cssClass="errors"/></td>
    </tr>
    
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  width="80%">
    <tr>  
        
        <c:choose>
	<c:when test="${valutazione.nuovo}">
            <td>             
            <p class="button"><input type="submit" name="add" value="Aggiungi Valutazione"/></p>
            </td>
        </c:when>
	<c:otherwise>
            <td>
           <p class="button"><input type="submit" name="update" value="Modifica Valutazione"/></p>
           </td>
           <td>
           <p class="button"><input type="submit" name="delete" value="Cancella Valutazione"/></p>
           </td>
        </c:otherwise>
        </c:choose>
           
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali </td>
    </tr>
  </table>

  </div>
</body>

</html>