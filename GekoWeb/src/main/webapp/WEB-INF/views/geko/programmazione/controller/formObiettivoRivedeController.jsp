<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Modifica Obiettivo</title>
    <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<h2 >Programmazione anno ${obiettivo.anno} da modificare per struttura ${obiettivo.incarico.opPersonaGiuridica.denominazione}</h2>

<form:form  modelAttribute="obiettivo" method="put">
        
<fieldset>
    <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
    <tr>
        <td width="20%"><form:label for="codice" path="codice">Codice (es.OPR_1)</form:label></td>
        <td><form:textarea path="codice" size="16" maxlength="16"/></td>
        <td><form:errors path="codice" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label for="descrizione" path="descrizione">Descrizione</form:label></td>
        <td><form:textarea path="descrizione" size="300" maxlength="1000"/></td>
        <td><form:errors path="descrizione" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label for="note" path="note">Note</form:label></td>
        <td><form:textarea path="note" size="1000" maxlength="1000"/></td>
        <td><form:errors path="note" cssClass="error"/></td>
    </tr>
     <tr>
	         <td width="20%"><form:label for="peso" path="peso">Peso</form:label></td>
	         <td><form:input path="peso"/></td>
	         <td><form:errors path="peso" cssClass="error"/></td> 
	     </tr>
    </table>  
</fieldset>
        

        
<fieldset>
    <h2 class="stato">Stato della Programmazione</h2>
    <table  width="80%">
    	<tr>
            <td width="20%"><form:label for="statoApprov" path="statoApprov">Approvazione</form:label></td>
            <td>${obiettivo.statoApprov}</td>
       </tr>  
</table>  
</fieldset>
        
<fieldset>
<h2 class="comandi">Comandi</h2>
<table  width="80%">
	<tr>
		<td >
			<p class="button"><input type="submit" name="update" value="Aggiorna Obiettivo"/></p>
		</td>    
	    <c:if test="${obiettivo.statoApprov == 'INTERLOCUTORIO' || obiettivo.statoApprov == 'RICHIESTO'}">
	        <td><p class="button"><input type="submit" name="delete" value="Cancella Obiettivo"/></p></td>
	        <td><p class="button"><input type="submit" name="addAction" value="Aggiungi Nuova Azione"/></p></td>
	    </c:if>
	    <td>
	    	<p class="button"><input type="submit" name="cancel" value="Esci"/></p> 
	    </td>
	</tr>
</table>  
</fieldset>
      
</form:form>         
        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
        Ge.Ko by - Segreteria Generale - ing. R. Cirrito 2019/04/16
      </td>
      
    </tr>
    <tr>
        <td><p>[view: formObiettivoRivedeController.jsp]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>
