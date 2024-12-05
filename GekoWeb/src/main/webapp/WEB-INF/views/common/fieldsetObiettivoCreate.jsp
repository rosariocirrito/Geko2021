<script type="text/javascript">
	$(function(){
		
		
	});
</script>

<fieldset>
<h2 class="info"> Dati descrittivi  </h2>
<table width="80%">
   <tr>
       <td width="20%"><form:label for="codice" path="codice">Codice (es. OPR_1)</form:label></td>
       <td><form:textarea path="codice" size="16" maxlength="16"/></td>
       <td><form:errors path="codice" cssClass="error"/></td>
   </tr>
   <tr>
       <td><form:label for="descrizione" path="descrizione">Descrizione estesa</form:label></td>
       <td><form:textarea path="descrizione" size="300" maxlength="1000"/></td>
       <td><form:errors path="descrizione" cssClass="error"/></td>
   </tr>
   <tr>
       <td><form:label for="note" path="note">Note eventuali</form:label></td>
       <td><form:textarea path="note" size="1000" maxlength="1000"/></td>
       <td><form:errors path="note" cssClass="error"/></td>
   </tr>
</table>  
</fieldset>
        
<fieldset>
<h2 class="tipologia">Tipologia Programmazione</h2>
<table width="80%">
	<tr>
		<td width="20%"><form:label for="tipo" path="tipo">Tipo</form:label></td>
        <td><form:select path="tipo">
            <form:option value="DIRIGENZIALE"/>
            <form:option value="POS_ORGAN"/>
            <form:option value="STRUTTURA"/>
        </form:select> </td>
        <td><form:errors path="tipo" cssClass="error"/></td>
    </tr>
    
    <tr>
        <td width="20%"><form:label for="peso" path="peso">Peso (eventuale)</form:label></td>
        <td><form:input path="peso"/></td>
        <td><form:errors path="peso" cssClass="error"/></td> 
    </tr>
</table>  
</fieldset>
        
<fieldset>
<h2 class="stato">Stato della Programmazione</h2>
<table  width="80%">
	<tr>
        <td width="20%"><form:label for="statoApprov" path="statoApprov">Stato</form:label></td>
        <td><form:select path="statoApprov">
        	<form:option value="INTERLOCUTORIO"/>
            <form:option value="PROPOSTO"/>
            </form:select>
        </td>
        <td><form:errors path="statoApprov" cssClass="error"/></td>
	</tr>   
</table>  
</fieldset>
        
