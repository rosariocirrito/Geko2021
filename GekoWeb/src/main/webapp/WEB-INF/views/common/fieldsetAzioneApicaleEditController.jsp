<script type="text/javascript">
	$(function(){
		$('#inizio').datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear: true
		});
		$('#scadenza').datepicker({
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
        Descrizione: ${azione.descrizione}
    </li>
    <li>
        <form:label for="descrizioneProp" path="descrizioneProp">Descrizione Proposta</form:label>
        <form:textarea path="descrizioneProp" size="300" maxlength="1000"/>
        <form:errors path="descrizioneProp" cssClass="error"/>       
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
    	<form:label for="inizio" path="inizio">Inizio</form:label>
    	<form:input path="inizio" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="inizio" cssClass="errors"/>
    	
	</li>
	<li>
    	<form:label for="scadenza" path="scadenza">Scadenza</form:label>
    	<form:input path="scadenza" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenza" cssClass="errors"/>
    	
	</li>
	
	
		
		<li>
	    	<form:label for="pesoApicale" path="pesoApicale">Peso Apicale</form:label>
	    	<form:input path="pesoApicale"/>
	    	<form:errors path="pesoApicale" cssClass="error"/>
		</li>
		
	
</ul>
</fieldset>
    
<fieldset>
<h2 class="stato">Assegnazioni</h2>
<ul>
	<c:forEach items="${azione.assegnazioni}" var="assegn" >
		<li>   
			 <a href="<spring:url value="/controllerAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA</a>
		     ${assegn.opPersonaFisica.cognome}  
		     ${assegn.opPersonaFisica.nome}
		     ${assegn.opPersonaFisica.opPersonaFisicaTipo.descrizione}
		     &nbsp; (da 
		    <fmt:formatDate value="${assegn.dataInizio}" pattern="dd/MM/yyyy" />
		     &nbsp; a 
		    <fmt:formatDate value="${assegn.dataFine}" pattern="dd/MM/yyyy" />
		    con peso
		    ${assegn.peso} &nbsp;%
		    )
	    </li> 
    </c:forEach>
</ul>
</fieldset>
    
