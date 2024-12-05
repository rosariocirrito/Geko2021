<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Maschera Modifica Valutazione Personale del Comparto</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<security:authorize access="hasRole('MANAGER')">
<div id="wrapper">

<h2 > Modifica valutazione nell'anno ${valutazioneComparto.anno}</h2>

<form:form  modelAttribute="valutazioneComparto" method="put">


        
<fieldset>
    <h2 class="info"> Competenza nello svolgimento delle attività</h2>
    <ul>
	    <li>
	        <form:label for="competSvolgAttivAss" path="competSvolgAttivAss">Peso attribuito</form:label>
	        <form:input path="competSvolgAttivAss" size="2" maxlength="2" class="small"/>
	        <form:errors path="competSvolgAttivAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="competSvolgAttivVal" path="competSvolgAttivVal">Valutazione</form:label>
	        <form:select path="competSvolgAttivVal" class="large">
	        	<form:option value="DA_VALUTARE"/>
	        	<form:option value="ECCELLENTE"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="INSUFFICIENTE"/>
           	</form:select>	
	        <form:errors path="competSvolgAttivVal" cssClass="error"/>
	    </li>
	    
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info"> Capacità di adattamento al contesto lavorativo </h2>
    <ul>
	    <li>
	        <form:label for="adattContextLavAss" path="adattContextLavAss">Peso attribuito</form:label>
	        <form:input path="adattContextLavAss" size="3" maxlength="3" class="small"/>
	        <form:errors path="adattContextLavAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="adattContextLavVal" path="adattContextLavVal">Valutazione</form:label>
	        <form:select path="adattContextLavVal" class="large">
	        	<form:option value="DA_VALUTARE"/>
	        	<form:option value="ECCELLENTE"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="INSUFFICIENTE"/>
            </form:select>	
	        <form:errors path="adattContextLavVal" cssClass="error"/>
	    </li>
	    
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info">Capacità di assolvere ai compiti assegnati</h2>
    <ul>
	    <li>
	        <form:label for="capacAssolvCompitiAss" path="capacAssolvCompitiAss">Peso attribuito</form:label>
	        <form:input path="capacAssolvCompitiAss" size="3" maxlength="3" class="small"/>
	        <form:errors path="capacAssolvCompitiAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="capacAssolvCompitiVal" path="capacAssolvCompitiVal">Valutazione</form:label>
	        <form:select path="capacAssolvCompitiVal" class="large">
	        	<form:option value="DA_VALUTARE"/>
	        	<form:option value="ECCELLENTE"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="INSUFFICIENTE"/>
            </form:select>	
	        <form:errors path="capacAssolvCompitiVal" cssClass="error"/>
	    </li>
	    
	 </ul>
</fieldset>


<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Valutazione"/>
	</li>
	<li>    
	   <input type="submit" name="delete" value="Cancella Valutazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
	
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formValutazioneCompartoABEditManager.jsp 2019/10/17]</p>

</div>
</body>

</html>