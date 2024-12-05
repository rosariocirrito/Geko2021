<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Associazione Obiettivi del Dirigente Generale</title>
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
    
<div class="container" >
<div class="row">
<div class="col-lg-12 col">
    
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Associazione Obiettivi Apicali per dipartimento: <em>${struttura} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            
<table class="table table-bordered table-striped">
		<colgroup>
			
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			
			<th scope= "col" >strategico</th>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	<tr class="success">
	
	<td>
	   	<c:if test="${empty obj.associazObiettivi}">
		<a href="<spring:url value="/gabinettoAssocia/new/${obj.idObiettivo}/${idIncaricoApicale}" htmlEscape="true" />">ASSOCIA STRATEGICO </a>
		
		</c:if>
	
    
		<c:forEach items="${obj.associazObiettivi}" var = "associaz">
		<a href="<spring:url value="/gabinettoAssocia/${associaz.id}/edit/${idIncaricoApicale}" htmlEscape="true" />">MODIFICA </a>
		
		${associaz.strategico.codice} -	${associaz.strategico.descrizione}
		</c:forEach>
	</td>
    <td>  
    
    ${obj.codice} -  ${obj.descrizione} 
    
    
	</td>	 
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
	<td>${obj.pesoApicale} </td> 
	</tr>
	
</c:forEach>
</table>


</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu Gabinetto</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: associaPianificazioneStrategicaIncaricoApicaleGabinetto.jsp 2019/07/23] </td>
    </tr>
</table>

</div>
  
</div>
</div>
    
    
</div>     
</body>
</html>
