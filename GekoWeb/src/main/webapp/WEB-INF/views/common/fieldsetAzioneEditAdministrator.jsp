<script type="text/javascript">
	$(function(){
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
	        Descrizione proposta: ${azione.descrizioneProp}
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
    	<form:input path="indicatore" size="16" maxlength="16"/>
    	<form:errors path="indicatore" cssClass="errors"/>
	</li>
	<li>
	    <form:label for="prodotti" path="prodotti">Valore Obiettivo</form:label>
	    <form:textarea path="prodotti" size="150" maxlength="150"/>
	    <form:errors path="prodotti" cssClass="errors"/>
	</li>
	
	<li>
    	<form:label for="scadenza" path="scadenza">Scadenza</form:label>
    	<form:input path="scadenza" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenza" cssClass="errors"/>
    	
	</li>
	
	
		<li>
	    	<form:label for="peso" path="peso">Peso</form:label>
	    	<form:input path="peso"/>
	    	<form:errors path="peso" cssClass="error"/>
		</li>
	
</ul>
</fieldset>
    
<fieldset>
<h2 class="stato">Assegnazioni</h2>
<ul>
	<c:forEach items="${azione.assegnazioni}" var="assegn" >
		<li>   
			 <a href="<spring:url value="/administratorAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA</a>
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
    
