<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Rendicontazione Obiettivi della Struttura del Dirigente</title>
	<link href="<spring:url value="/resources/bootstrap336/css/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:8px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:8px;
			border:2px solid darkgray;
		}
	</style>

</head>

<body>
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 

<article>  
<h2 >Rendicontazione Obiettivi per <em>${struttura} </em>- anno ${anno}</h2>
<hr />  
<h4>Responsabile: ${responsabile}</h4>   
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
           
        

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
<table class="table table-bordered table-striped">
	<colgroup>
		<col class="desrc">
		<col class="desrc">
		<col class="name">
		<col class="name">
		<col class="name">
	</colgroup> 
	<tbody>
    <thead> 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>     
	<c:choose>
	<c:when test="${obj.tipo == 'OBIETTIVO'}">
		<tr class="success">	
	</c:when>
	
	<c:when test="${obj.tipo == 'ISTITUZ_MAGG_RILEV'}">
		<tr class="info">
	</c:when>
	
	<c:when test="${obj.tipo == 'AMMIN_MANUALE_SUPP' }">
		<tr class="warning">	
	</c:when>
	</c:choose>

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    <td>   
    <c:if test="${obj.tipo == 'OBIETTIVO'}">  
	    <c:if test="${obj.priorita == 'ALTA'}"> ALTA</c:if>
	    <c:if test="${obj.priorita == 'BASSA'}"> BASSA</c:if>
	</c:if>    
    </td>
    <td>${obj.statoApprov} </td>
	<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if></td> 
	</tr>
	<tr>
    <td colspan="5">
    
   	<!-- azioni -->
	<table class="table table-bordered table-striped">
			<colgroup>
				<col class="desrc">
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
				
		    	<th scope= "col" >azioni</th>
		    	<th scope= "col" >note</th>
		    	<th scope= "col" >indicatore previsto</th>
		    	<th scope= "col" >valore obiettivo</th>
		        <th scope= "col" >scadenza</th>
		        <th scope= "col" >peso</th>
		        <th scope= "col" >risultato</th>
		        <th scope= "col" >documenti</th>
		        <th scope= "col" >indicazioni</th>
		    </tr>  
		</thead> 
	    <c:forEach items="${obj.azioni}" var="act">
	        <tr>
	        
	        <td>
	        	<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
				        ${act.descrizioneProp}
			    </c:if>
	        	${act.denominazione} - ${act.descrizione}
	        </td>
	        
	        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
	            
	        <td>${act.indicatore}</td>
	        <td>${act.prodotti}</td>
	        <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
	        <td><c:if test="${act.peso >0 }" >${act.peso}</td></c:if> 
	        <td>${act.risultato}</td>
	        <td> 
	            <c:forEach items="${act.documenti}" var="docu">
	                <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>
	             </c:forEach>
	         </td>
	        <td> 
	            <c:forEach items="${act.criticita}" var="critic3">
					${critic3.indicazioni} </br>
	            </c:forEach>
	        </td>
	        </tr>
	    </c:forEach>
	    </tbody>
	</table>   



    </td>
    </tr>
    </table> <!-- fine tabella obiettivi -->
</c:forEach>


</article>
    
<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
    Ge.Ko. by ing. R. Cirrito [listRendicontazioneIncaricoManager.jsp 2019/06/14] 
  
</div>
</div>    
</div>    
</body>
</html>
