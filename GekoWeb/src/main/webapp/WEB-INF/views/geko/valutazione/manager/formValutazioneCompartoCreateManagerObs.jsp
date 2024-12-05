<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
	<title>Maschera Creazione Valutazione Personale del Comparto</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<security:authorize access="hasRole('MANAGER')">
<div id="wrapper">

<h2 > Nuova valutazione nell' anno ${valutazioneComparto.anno}</h2>

<form:form  modelAttribute="valutazioneComparto" method="post">

<script type="text/javascript">
	$(function(){
		
		
	});
</script>
        
<fieldset>
    <h2 class="info"> Capacità Gestione Complessità e Difficoltà </h2>
    <ul>
	    <li>
	        <form:label for="complessDifficoltaAss" path="complessDifficoltaAss">Peso attribuito</form:label>
	        <form:input path="complessDifficoltaAss" size="3" maxlength="3" class="small"/>
	        <form:errors path="complessDifficoltaAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="complessDifficoltaVal" path="complessDifficoltaVal">Valutazione</form:label>
	        <form:select path="complessDifficoltaVal" class="large">
	        	<form:option value="OTTIMO"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="SOLA_PARTECIPAZIONE"/>
           	</form:select>	
	        <form:errors path="complessDifficoltaVal" cssClass="error"/>
	    </li>
	    <li>
	        <form:label for="annot1" path="annot1">Annotazioni</form:label>
	        <form:textarea path="annot1" size="255" maxlength="255"/>
	        <form:errors path="annot1" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info"> Competenze Tecnico-Professionali </h2>
    <ul>
	    <li>
	        <form:label for="competProfAss" path="competProfAss">Peso attribuito</form:label>
	        <form:input path="competProfAss" size="3" maxlength="3" class="small"/>
	        <form:errors path="competProfAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="competProfVal" path="competProfVal">Valutazione</form:label>
	        <form:select path="competProfVal" class="large">
	        	<form:option value="OTTIMO"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="SOLA_PARTECIPAZIONE"/>
            </form:select>	
	        <form:errors path="competProfVal" cssClass="error"/>
	    </li>
	    <li>
	        <form:label for="annot2" path="annot2">Annotazioni</form:label>
	        <form:textarea path="annot2" size="255" maxlength="255"/>
	        <form:errors path="annot2" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
    <h2 class="info"> Capacità di promuovere e gestire l'innovazione </h2>
    <ul>
	    <li>
	        <form:label for="innovazAss" path="innovazAss">Peso attribuito</form:label>
	        <form:input path="innovazAss" size="3" maxlength="3" class="small"/>
	        <form:errors path="innovazAss" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="innovazVal" path="innovazVal">Valutazione</form:label>
	        <form:select path="innovazVal" class="large">
	        	<form:option value="OTTIMO"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="SOLA_PARTECIPAZIONE"/>
           	</form:select>	
	        <form:errors path="innovazVal" cssClass="error"/>
	    </li>
	    <li>
	        <form:label for="annot3" path="annot3">Annotazioni</form:label>
	        <form:textarea path="annot3" size="255" maxlength="255"/>
	        <form:errors path="annot3" cssClass="error"/>
	    </li>
	 </ul>
</fieldset>

<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="add" value="Aggiungi Valutazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali </p>      
<p>[view: formValutazioneCompartoCreateManager.jsp]</p>

</div>
</body>

</html>