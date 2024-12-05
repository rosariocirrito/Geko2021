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
	Nuova Valutazione per l'incarico di <br /> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura}/
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="POST">
    
    <fieldset>
        <h2 class="info"> Pesi Attribuiti </h2>
  <table>
  	    
    <tr>
        <td><form:label for="analProgrAss" path="analProgrAss">Promozione strumenti analisi...</form:label></td>
        <td><form:input path="analProgrAss" size="2" maxlength="2"/> </td>
        <td><form:errors path="analProgrAss" cssClass="errors"/></td>
        <td>range (>1)</td>
    </tr>
    
    <tr>
        <td><form:label for="relazCoordAss" path="relazCoordAss">Capacita' di organizzazione</form:label></td>
        <td><form:input path="relazCoordAss" size="2" maxlength="2"/> </td>
        <td><form:errors path="relazCoordAss" cssClass="errors"/></td>
        <td>range (>1)</td>
    </tr>    
    
    <tr>
        <td><form:label for="pdlAss" path="pdlAss">Capacita' individuazione livello priorità</form:label></td>
       	<td><form:input path="pdlAss" size="2" maxlength="2"/> </td>
       	<td><form:errors path="pdlAss" cssClass="errors"/></td>
        <td>range (>1)</td>
    </tr>
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  >
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
        <td><p>[view: formValutazionePopCreateController.jsp 2020/11/23]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>