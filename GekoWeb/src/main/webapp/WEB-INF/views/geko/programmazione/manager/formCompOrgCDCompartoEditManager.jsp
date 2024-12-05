<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Maschera Modifica Comportamenti Organizzativi del Comparto</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<security:authorize access="hasRole('MANAGER')">
<div id="wrapper">

<h2 > Modifica comportamenti organizzativi nell'anno ${valutazioneComparto.anno}</h2>

<form:form  modelAttribute="valutazioneComparto" method="put">


        
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
    <h2 class="info">Capacità propositiva e propensione all'aggiornamento professionale e all'innovazione</h2>
    <ul>
	    <li>
	        <form:label for="innovazAss" path="innovazAss">Peso attribuito</form:label>
	        <form:input path="innovazAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="innovazAss" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info">Capacità di organizzazione del lavoro</h2>
    <ul>
	    <li>
	        <form:label for="orgLavAss" path="orgLavAss">Peso attribuito</form:label>
	        <form:input path="orgLavAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="orgLavAss" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>


<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Comportamento Organizzativo"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>


<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formCompOrgCDCompartoCreateManager.jsp 2019/08/23]</p>

</div>
</security:authorize>
</body>

</html>