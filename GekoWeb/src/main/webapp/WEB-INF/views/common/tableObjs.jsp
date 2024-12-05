<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead > 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	
	<tr class="success">	
	

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
   
    <td>${obj.statoApprov} </td>
	<td> ${obj.peso} </td> 
	</tr>
	<tr>
    <td colspan="4">
    
    <%@ include file="/WEB-INF/views/common/tableActions.jsp" %>
    </td>
    </tr>
</c:forEach>
</table>