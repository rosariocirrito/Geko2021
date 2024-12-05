<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Storia</title>
	<link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:16px;
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

<div class="row">
<div class="span8"> 
    
<article>
<section  >   
<h2 >Attività storica per ultimi ${giorni} giorni per ${chi}</h2>
 

    
<c:if test="${empty journals}">
    <p>Nessun record storico trovato.</p>
</c:if>
            
    
<table class="table table-bordered table-striped">
<colgroup>
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup>     
<thead>
	<tr>
         <th scope= "col" >cosa</th>
         <th scope= "col" >perche</th>
         <th scope= "col" >quando</th>
     </tr>
</thead>     
 <tbody>    
	<c:forEach items="${journals}" var="item" varStatus ="status">
	    <tr>    
	        <td>${item.cosa}</td>   
	        <td>${item.perche}</td>    
	        <td>${item.quando}</td>
	    </tr>
	    
	</c:forEach>
</tbody>
</table>   
    

</section>
</article>



<!-- Footer -------------------------->

 <p><span class="label label-warning"><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a></span>
 Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [view:listJournalUserController.jsp]<p>

</div> 

</div>

  
</body>
</html>
