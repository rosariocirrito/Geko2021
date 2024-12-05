<script type="text/javascript">
	$(function(){
		$('#dataInizio').datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear: true
		});
		
		$('#dataFine').datepicker({
			dateFormat: 'dd/mm/yy',
			changeYear: true
		});		
		
		
	});
</script>
    
<fieldset>
<h2 class="info"> Dati modificabili </h2>
<table width="80%">
    <tr>
        <td><form:label for="dataInizio" path="dataInizio">dataInizio</form:label></td>
        <td><form:input path="dataInizio" size="10" maxlength="20"/> (gg/mm/anno)</td>
        <td><form:errors path="dataInizio" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="dataFine" path="dataFine">dataFine</form:label></td>
        <td><form:input path="dataFine" size="10" maxlength="20"/> (gg/mm/anno)</td>
        <td><form:errors path="dataFine" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="peso" path="peso">Peso</form:label></td>
        <td><form:input path="peso" size="3" maxlength="3"/></td>
        <td><form:errors path="peso" cssClass="errors"/></td>
    </tr>
</table>
</fieldset> 
    

