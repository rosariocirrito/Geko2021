<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Modifica Obiettivo</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
	<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>
</head>

<body>
<div id="wrapper">

<h2 >Programmazione da modificare </h2>

<security:authorize access="hasRole('CONTROLLER')">
<form:form  modelAttribute="obiettivo" method="put">
        
<script type="text/javascript">
	$(function(){
		
	});
</script>
        
<fieldset>
    <h2 class="info"> Dati descrittivi  </h2>
    <ul>
	    <li>
	        <form:label for="codice" path="codice">Codice (es. OPR_1)</form:label>
	        <form:textarea path="codice" size="16" maxlength="16"/>
	        <form:errors path="codice" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="descrizione" path="descrizione">Descrizione</form:label>
	        <form:textarea path="descrizione" size="1000" maxlength="1000"/>
	        <form:errors path="descrizione" cssClass="error"/>
	    </li>
	    
	    
	    <c:if test="${obiettivo.statoApprov != 'RICHIESTO'}">
	    <li>
	        <form:label for="descrizioneProp" path="descrizioneProp">Descrizione Proposta</form:label>
	        <form:textarea path="descrizioneProp" size="1000" maxlength="1000"/>
	        <form:errors path="descrizioneProp" cssClass="error"/>
	    </li>
	    </c:if>
	    <li>
	        <form:label for="note" path="note">Note</form:label>
	        <form:textarea path="note" size="1000" maxlength="1000"/>
	        <form:errors path="note" cssClass="error"/>
	    </li>
	    
	    <li>
			<form:label for="peso" path="peso">Peso</form:label>
	       	<form:input class="form-control" path="peso" maxlength="3"/>
	        <form:errors path="peso" cssClass="error"/> 
	
		</li>
    </ul>  
</fieldset>
        

        


<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>
	    <input type="submit" name="update" value="Aggiorna Obiettivo"/> 
		<input type="submit" name="delete" value="Cancella Obiettivo"/>
    </li>
    <li>    
	    <input type="submit" name="addAction" value="Aggiungi Nuova Azione"/>
    </li>
    <li>    
       	<input type="submit" name="cancel" value="Rinuncia ed esci"/>
    </li>
</ul>  
</fieldset>
       
      
</form:form>        
</security:authorize> 

<p>Ge.Ko by - Segreteria Generale - ing R. Cirrito </p>      
<p>[view: formObiettivoEditController.jsp 201/04/24]</p>
        
</div>
</body>

</html>
