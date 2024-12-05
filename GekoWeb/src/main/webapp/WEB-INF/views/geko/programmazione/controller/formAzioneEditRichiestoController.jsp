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
        
<h2>Azione da modificare per ${azione.obiettivo.codice}</h2>

<form:form modelAttribute="azione" method="put">
    
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
	    <form:label for="prodotti" path="prodotti">Prodotti</form:label>
	    <form:textarea path="prodotti" size="150" maxlength="150"/>
	    <form:errors path="prodotti" cssClass="errors"/>
	</li>
	
	<li>
    	<form:label for="scadenza" path="scadenza">Scadenza</form:label>
    	<form:input path="scadenza" size="10" maxlength="20"/> (gg/mm/anno)
    	<form:errors path="scadenza" cssClass="errors"/>
    	
	</li>
	
	<c:if test="${azione.obiettivo.tipo == 'OBIETTIVO'}">
		<li>
	    	<form:label for="peso" path="peso">Peso</form:label>
	    	<form:input path="peso"/>
	    	<form:errors path="peso" cssClass="error"/>
		</li>
	</c:if>
</ul>
</fieldset>
    
<fieldset>
<h2 class="stato">Assegnazioni</h2>
<ul>
	<c:forEach items="${azione.assegnazioni}" var="assegn" >
		<li>   
			 <a href="<spring:url value="/dirigenteAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA</a>
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










    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Azione"/>
	   <input type="submit" name="delete" value="Cancella Azione"/>
	</li>
	<li>   
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
        
        
</form:form>
<p>Ge.Ko by - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali </p>      
<p>[view: formAzioneEditRichiestoController.jsp]</p>

</div>
</body>

</html>