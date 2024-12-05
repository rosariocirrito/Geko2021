<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Criticità Valutazione</title>
        
        <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
        
            
            
            
<c:choose>
	<c:when test="${criticita.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<h3>
    
    <c:choose>
	<c:when test="${criticita.nuovo}">Nuova Criticità</c:when>
	<c:otherwise>Criticità da Modificare</c:otherwise>
    </c:choose>
    
    per  ${criticita.valutazione.incarico.opPersonaFisica.order} / anno: ${criticita.valutazione.anno}
    
</h3>



<form:form modelAttribute="criticita" method="${method}">
    
    <fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
    <tr>
        <td><form:label for="descrizione" path="descrizione">descrizione</form:label></td>
        <td><form:textarea path="descrizione" size="100" maxlength="200"/> </td>
        <td><form:errors path="descrizione" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="proposte" path="proposte">proposte</form:label></td>
        <td><form:textarea path="proposte" size="100" maxlength="200"/> </td>
        <td><form:errors path="proposte" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="indicazioni" path="indicazioni">indicazioni</form:label></td>
        <td><form:textarea path="indicazioni" size="100" maxlength="200"/> </td>
        <td><form:errors path="indicazioni" cssClass="errors"/></td>
    </tr>
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  width="80%">
    <tr>  
        
        <c:choose>
	<c:when test="${criticita.nuovo}">
            <td>             
            <p class="button"><input type="submit" name="add" value="Aggiungi Criticità"/></p>
            </td>
        </c:when>
	<c:otherwise>
            <td>
           <p class="button"><input type="submit" name="update" value="Modifica Criticità"/></p>
           </td>
           <td>
           <p class="button"><input type="submit" name="delete" value="Cancella Criticità"/></p>
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
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 24/09/2013 </td>
    </tr>
  </table>

  </div>
</body>

</html>