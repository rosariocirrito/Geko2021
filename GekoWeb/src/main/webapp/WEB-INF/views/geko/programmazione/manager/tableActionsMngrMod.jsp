<table class="table table-bordered table-striped">
		<colgroup>
			<col class="name">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="desrc">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	        <th scope= "col" >scadenza</th>
	        <th scope= "col" >peso</th>
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
			<!-- Comandi -->
			<td>
			<c:forEach items="${act.guiCommands}" var="actCmd" varStatus ="status">
				<span class="label label-warning">
			    <a href="<spring:url value="  ${actCmd.uri}  " htmlEscape="true" />">${actCmd.label}</a>
			    </span>
			    <br />
		    </c:forEach>
			</td>
			
			<!-- Descrizione -->
	        <td>
	        	<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
				        ${act.descrizioneProp}
			    </c:if>
	        	${act.denominazione} - ${act.descrizione} <c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if>
	        </td>
	        
	        <!-- Note -->
	        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
	        
	         <!-- Indicatore -->    
	        <td>${act.indicatore}</td>
	        
	         <!-- Valore obiettivo -->
	        <td>${act.prodotti}</td>
	        
	         <!-- Scadenza -->
	        <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
	        	<c:if test="${act.tassativa}"> TASSATIVA</c:if>
	        </td>
	         <!-- peso -->
	        <td><c:if test="${act.peso >0 }" >${act.peso}</td></c:if>
	        </tr>
	        
	         <!-- Dipendenti -->
	        <tr>
	        <td> 
	          	<a href="<spring:url value="/dirigenteAssegn/new/${act.idAzione}" htmlEscape="true" />">AGGIUNGI DIP.</a></br>
	        </td>
	        <td colspan="6">        
	            <c:forEach items="${act.assegnazioni}" var="assegn">
	            	<a href="<spring:url value="/dirigenteAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA</a>
	                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}% -
	                da: <fmt:formatDate value="${assegn.dataInizio}" pattern="dd/MM/yyyy"/> 
	                a: <fmt:formatDate value="${assegn.dataFine}" pattern="dd/MM/yyyy"/> 
	                </br>
	             </c:forEach>
	         </td>
        	</tr>
    </c:forEach>
    </tbody>
</table>   
    