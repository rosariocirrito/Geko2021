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
	        Descrizione proposta: ${obiettivo.descrizioneProp}
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
    </ul>  
</fieldset>
        
<fieldset>
    <h2 class="tipologia">Tipologia Programmazione</h2>
    <ul>
    	<li>
            <form:label for="tipo" path="tipo">Tipo</form:label>
            	<form:select path="tipo">
	                <form:option value="${obiettivo.tipo}"/>
	                <form:option value="OBIETTIVO"/>
	                <form:option value="ISTITUZ_MAGG_RILEV"/>
	                <form:option value="AMMIN_MANUALE_SUPP"/>
            	</form:select>
            <form:errors path="tipo" cssClass="error"/>
        </li>
        
        <li>
			<form:label for="priorita" path="priorita">Priorita</form:label>
        		<form:select path="priorita">
            		<form:option value="ALTA"/>
            		<form:option value="BASSA"/>
            		<form:option value="NESSUNA"/>
        		</form:select>
        	<form:errors path="tipo" cssClass="error"/>
    	</li>
        
        <c:if test="${obiettivo.peso > 0}">
        <li>
            <form:label for="peso" path="peso">Peso</form:label>
            <form:input path="peso"/>
            <form:errors path="peso" cssClass="error"/>
        </li>
        </c:if>
    </ul>  
</fieldset>
        
<fieldset>
    <h2 class="stato">Stato della Programmazione</h2>
    <ul>
       <li>
            <form:label for="statoApprov" path="statoApprov">Approvazione</form:label>           
            ${obiettivo.statoApprov}
            
       </li>        
	</ul>  
</fieldset>
        

      
