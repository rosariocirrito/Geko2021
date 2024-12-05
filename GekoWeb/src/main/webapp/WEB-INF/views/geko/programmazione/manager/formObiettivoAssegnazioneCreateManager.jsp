<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Assegnazione</title>
    <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<h3>Nuova Assegnazione </h3>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="obiettivoAssegnazione" method="post">
    
<fieldset>
<h2 class="info"> Dati descrittivi </h2>
<table width="80%">
    <tr>
        <td><form:label for="peso" path="peso">Peso</form:label></td>
        <td><form:input path="peso" size="3" maxlength="3"/></td>
        <td><form:errors path="peso" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="note" path="peso">Note</form:label></td>
        <td><form:textarea path="note" size="200" maxlength="200"/></td>
        <td><form:errors path="note" cssClass="errors"/></td>
    </tr>
</table>
</fieldset> 
    
<fieldset>
 
<h2 class="stato">Scelta Dipendente</h2>
<table width="80%">
	<c:forEach items="${listaEsistenti}" var="dipEsist" varStatus ="status">
	    <tr>
	        <td>${dipEsist.cognome}</td>
	    </tr>
	</c:forEach>
	
    <tr>
        <td>Nuove Assegnazioni</td>
        <td>
    <form:select path="idPersona" >
    <form:options items="${listaDisponibili}" itemValue="idPersona" itemLabel="stringa"/>    
    </form:select> 
        </td>
    <td><form:errors path="idPersona" cssClass="error"/></td>
    </tr>
   
</table>
</fieldset>
       
<fieldset>
<table  width="80%">
	<tr>
	    <td>             
	    	<p class="button"><input type="submit" name="add" value="Aggiungi Assegnazione"/></p>
	    </td>
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</security:authorize>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoAssegnazioneCreateManager.jsp 2019/05/24]</p></td>
    </tr>
</table>

</div>
</body>

</html>