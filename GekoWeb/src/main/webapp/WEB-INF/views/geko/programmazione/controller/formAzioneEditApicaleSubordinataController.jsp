<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
<title>Maschera Azione</title>

<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>

</head>

<body>
<div id="wrapper">
        
<h2>Azione apicale da modificare per ${azione.obiettivo.codice}</h2>

<security:authorize access="hasRole('CONTROLLER')">
<form:form modelAttribute="azione" method="put">
    
<script type="text/javascript">
	$(function(){
		$('#scadenza').datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear: true
		});
		$('#scadenzaApicale').datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear: true
		});
		
	});
</script>
    
<fieldset>
<h2 class="info"> Dati descrittivi </h2>
<ul>
    <li>
        <form:label for="denominazione" path="denominazione">Codice </form:label>
        ${azione.denominazione}
    </li>
    
    <li>
        <form:label for="descrizione" path="descrizione">Descrizione</form:label>
        ${azione.descrizione}      
    </li>
    <li>
        <form:label for="note" path="note">Note</form:label>
        ${azione.note}
        
    </li>
</ul>
</fieldset> 

<fieldset>
<h2 class="tipologia">Tipologia Azione</h2>
<ul>
	<li>
    	<form:label for="indicatore" path="indicatore">Indicatore</form:label>
    	${azione.indicatore}
	</li>
	<li>
	    <form:label for="prodotti" path="prodotti">Valore Obiettivo</form:label>
	    ${azione.prodotti}
	</li>
	<li>
    	<form:label for="scadenza" path="scadenza">Scadenza</form:label>
    	<form:input path="scadenza" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenza" cssClass="errors"/>
    	
	</li>
	<li>
    	<form:label for="scadenzaApicale" path="scadenza">ScadenzaApicale</form:label>
    	<form:input path="scadenzaApicale" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenzaApicale" cssClass="errors"/>
    	
	</li>
	
	<li>
    	<form:label for="pesoApicale" path="pesoApicale">Peso Apicale</form:label>
    	<form:input path="pesoApicale"/>
    	<form:errors path="pesoApicale" cssClass="error"/>
	</li>
		
	
</ul>
</fieldset>
    

    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Azione Apicale"/>
	</li>
	<li>   
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
        
        
</form:form>
</security:authorize>


<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formAzioneEditApicaleSubordinataController.jsp 2016/04/27]</p>

</div>
</body>

</html>