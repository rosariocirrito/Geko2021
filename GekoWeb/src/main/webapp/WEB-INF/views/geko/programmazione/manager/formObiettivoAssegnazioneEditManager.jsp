<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Assegnazione</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<div id="wrapper">

<h3> Assegnazione da Modificare per Dipendente ${obiettivoAssegnazione.opPersonaFisica.stringa}
   
    </h3>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="obiettivoAssegnazione" method="put">

<fieldset>
<h2 class="info"> Dati modificabili </h2>
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
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Assegnazione"/>
	   <input type="submit" name="delete" value="Cancella Assegnazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formObiettivoAssegnazioneEditManager.jsp 2019/05/23]</p>

</div>
</body>

</html>