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

<h2>Nuova Azione Apicale Diretta per ${azione.obiettivo.codice} / ${azione.obiettivo.descrizione} </h2>

<form:form modelAttribute="azione" method="post">
    
<script type="text/javascript">
	$(function(){
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
        <form:label for="denominazione" path="denominazione">Codice</form:label>
        <form:textarea path="denominazione" size="300" maxlength="300"/>
        <form:errors path="denominazione" cssClass="error"/>
    </li>
    <li>
        <form:label for="descrizione" path="descrizione">Descrizione</form:label>
        <form:textarea path="descrizione" size="300" maxlength="1000"/>
        <form:errors path="descrizione" cssClass="error"/>
        
    </li>
    <li>
        <form:label for="note" path="note">Note</form:label>
        <form:textarea path="note" size="200" maxlength="1000"/>
        <form:errors path="note" cssClass="errors"/>
        
    </li>
</ul>
</fieldset> 

<fieldset>
<h2 class="tipologia">Tipologia Azione</h2>
<ul>
	<li>
    	<form:label for="indicatore" path="indicatore">Indicatore</form:label>
    	<form:select class="form-control" path="indicatore">
       		<form:option value="Numero"/>
            <form:option value="Euro"/>
            <form:option value="%"/>
            <form:option value="SI/NO"/>
        </form:select> 
    	<form:errors path="indicatore" cssClass="errors"/>
	</li>
	<li>
	    <form:label for="prodotti" path="prodotti">Valore Obiettivo</form:label>
	    <form:textarea path="prodotti" size="150" maxlength="150"/>
	    <form:errors path="prodotti" cssClass="errors"/>
	</li>
	
	<li>
    	<form:label for="scadenzaApicale" path="scadenzaApicale">Scadenza Apicale</form:label>
    	<form:input path="scadenzaApicale" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenzaApicale" cssClass="errors"/>
    	
	</li>
	
	
		<li>
	    	<form:label for="pesoApicale" path="peso">pesoApicale</form:label>
	    	<form:input path="pesoApicale"/>
	    	<form:errors path="pesoApicale" cssClass="error"/>
		</li>
	
</ul>
</fieldset>
    

    

    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>
		<input type="submit" name="add" value="Aggiungi Azione"/>
	</li>
	<li>
		<input type="submit" name="cancel" value="Rinuncia"/>
	</li>
</ul>
</fieldset>    
    

</form:form>

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formAzioneApicaleDirettaCreateGabinetto.jsp 2019/07/23] </p>  

</div>

</body>

</html>