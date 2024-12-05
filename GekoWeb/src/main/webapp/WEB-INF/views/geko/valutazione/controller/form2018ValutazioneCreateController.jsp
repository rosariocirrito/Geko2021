<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Valutazione</title>
        
        <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<c:choose>
	<c:when test="${valutazione.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="post"/></c:otherwise>
</c:choose>

<h3>
	Nuova Valutazione per l'incarico di </br> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura}/
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="POST">
    
    <fieldset>
        <h2 class="info"> Pesi Attribuiti </h2>
  <table width="50%">
  	
    <tr>
        <td><form:label for="pdlAss" path="pdlAss">Piano di Lavoro peso attibuito</form:label></td>
       <td><form:input path="pdlAss" size="4" maxlength="4"/> </td>
       <td><form:errors path="pdlAss" cssClass="errors"/></td>
        
        
        
    </tr>
    <tr>
        <td><form:label for="analProgrAss" path="analProgrAss">Capacit� di Analisi e Programmazione Peso Attribuito</form:label></td>
        <td><form:input path="analProgrAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="analProgrAss" cssClass="errors"/></td>
    
        
    </tr>
    
    <tr>
        <td><form:label for="relazCoordAss" path="relazCoordAss">Capacit� di Relazione e Coordinamento Peso Attribuito</form:label></td>
        <td><form:input path="relazCoordAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="relazCoordAss" cssClass="errors"/></td>
    
        
    </tr>
    
    <tr>
        <td><form:label for="gestRealAss" path="gestRealAss">Capacit� di Gestione e Realizzazione Peso Attribuito</form:label></td>
        <td><form:input path="gestRealAss" size="4" maxlength="4"/> </td>
        <td><form:errors path="gestRealAss" cssClass="errors"/></td>
    
        
    </tr>
    
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  width="80%">
    <tr>  
        
            <td>             
            <p class="button"><input type="submit" name="add" value="Aggiungi Valutazione"/></p>
            </td>
        
           
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
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formValutazioneCreateController.jsp 2017/02/23]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>