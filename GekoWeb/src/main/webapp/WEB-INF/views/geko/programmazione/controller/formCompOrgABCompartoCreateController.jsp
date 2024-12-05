<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Maschera Creazione Comportamenti Organizzativi del Comparto</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<security:authorize access="hasRole('CONTROLLER')">
<div id="wrapper">

<h2 > Nuova valutazione nell' anno ${valutazioneComparto.anno}</h2>

<form:form  modelAttribute="valutazioneComparto" method="POST">


        
<fieldset>
    <h2 class="info">Competenza nello svolgimento delle attività</h2>
    <ul>
	    <li>
	        <form:label for="competSvolgAttivAss" path="competSvolgAttivAss">Peso attribuito</form:label>
	        <form:input path="competSvolgAttivAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="competSvolgAttivAss" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info">Capacità di adattamento al contesto lavorativo</h2>
    <ul>
	    <li>
	        <form:label for="adattContextLavAss" path="adattContextLavAss">Peso attribuito</form:label>
	        <form:input path="adattContextLavAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="adattContextLavAss" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info">Capacità di assolvere ai compiti assegnati</h2>
    <ul>
	    <li>
	        <form:label for="capacAssolvCompitiAss" path="capacAssolvCompitiAss">Peso attribuito</form:label>
	        <form:input path="capacAssolvCompitiAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="capacAssolvCompitiAss" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>


<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="add" value="Aggiungi Comportamento Organizzativo"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>


<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formCompOrgABCompartoCreateController.jsp 2020/04/10]</p>

</div>
</security:authorize>
</body>

</html>