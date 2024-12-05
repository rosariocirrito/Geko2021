<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Modifica Obiettivo Apicale</title>
    <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
	<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>
</head>

<body>
<security:authorize access="hasRole('GABINETTO')">
<div id="wrapper">

<h2 >Programmazione apicale anno ${obiettivo.anno} da modificare per incarico ${obiettivo.incarico.stringa}</h2>

<form:form  modelAttribute="obiettivo" method="put">
        
<fieldset>
    <h2 class="info"> Dati descrittivi  </h2>
    <ul>
	    <li>
	        <form:label for="codice" path="codice">Codice (es. APIC_1)</form:label>
	        <form:textarea path="codice" size="16" maxlength="16"/>
	        <form:errors path="codice" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="descrizione" path="descrizione">Descrizione</form:label>
	        <form:textarea path="descrizione" size="1000" maxlength="1000"/>
	        <form:errors path="descrizione" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="note" path="note">Note</form:label>
	        <form:textarea path="note" size="1000" maxlength="1000"/>
	        <form:errors path="note" cssClass="error"/>
	    </li>
	    
	    <li>
			<form:label for="pesoApicale" path="pesoApicale">Peso apicale</form:label>
	       	<form:input class="form-control" path="pesoApicale" maxlength="3"/>
	        <form:errors path="pesoApicale" cssClass="error"/> 
	
		</li>
    </ul>  
</fieldset>
        

        

        

<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li><input type="submit" name="update" value="Aggiorna Obiettivo"/></li>
    <li><input type="submit" name="delete" value="Cancella Obiettivo"/></li>
    <li><input type="submit" name="cancel" value="Rinuncia ed esci"/></li>
</ul>  
</fieldset>
       
      
</form:form>         

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formObiettivoApicaleEditGabinetto.jsp 2019/07/22]</p>
        
</div>
</security:authorize>
</body>

</html>
