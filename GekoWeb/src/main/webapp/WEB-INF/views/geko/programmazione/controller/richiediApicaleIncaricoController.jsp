<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Richiedi Obiettivi Apicali al Dirigente</title>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
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
<h1 id="titolo-lista">Richiedi Obiettivi Apicali ai dirigenti per dipartimento: <em>${dipartimento} </em>- anno ${anno}</h1>
<hr />  
<h4>Obiettivi da richiedere a: ${responsabile}</h4>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            
<table class="table table-bordered table-striped">
	<colgroup>
		<col class="desrc">
		<col class="desrc">
		<col class="desrc">
		<col class="desrc">
		
		<col class="name">
		<col class="name">
	</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">	
	<tr class="success">
		<td>	
		<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
			<span class="label label-success">
				<a href="<spring:url value="  ${cmd.uri}/${obj.idObiettivo}  " htmlEscape="true" />">${cmd.label}</a>
		    </span>
	    	  <br />
	    </c:forEach>
	
	</td>
	<td>${obj.codice} -  ${obj.descrizione}</td>	 
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
	<td>${obj.pesoApicale} </td> 
	</tr>
	<tr>
    <td colspan="5">
	    <table class="table table-bordered table-striped">
		<tbody>    
	    <c:forEach items="${obj.incarichiDirig}" var="inc">
	        <tr>
		        <td>
		        <span class="label label-info">
					<a href="<spring:url value="/controller/modifyPianificazioneIncaricoController/${anno}/${inc.idIncarico}" htmlEscape="true" />">Modifica richiesta</a>
			    </span>
			    </td>
		        <td>
		        	già richiesto a:
		        </td>
		        <td>
		        	${inc.responsabile}
		        </td>
		     </tr>        
	    </c:forEach>
	    </tbody>
		</table>    
	</td>
 </tr>
</c:forEach>
</table>

<hr /> 
	
</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: ${pageName} 2019/05/27] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
