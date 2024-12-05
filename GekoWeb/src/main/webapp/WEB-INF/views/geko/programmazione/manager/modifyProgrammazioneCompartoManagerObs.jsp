<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Programmazione Dipendenti della Struttura/SubStrutture</title>
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
   
    
    
<article>
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 

    <div class="breakafter"> 
        <h1 id="titolo-lista">Programmazione Dipendenti per <em>${struttura} anno: ${anno}</em></h1>   

        <hr />      
        <c:if test="${empty mapDipendentiAssegnazioni}">
            <p>Nessun dipendente trovato !</p>
        </c:if>
    </div>
    <!-- itero sulle assegnazioni -->
    <c:forEach items="${mapDipendentiAssegnazioni}" var="mapItem">
    <div class="breakafter">
    	<!-- nome dipendente -->
   		<h3>${mapItem.key.stringa}</h3> 
   		
   		<!-- valutazione dei risultati -->
	    <table class="table table-bordered table-striped">
	    <colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup> 
		<thead>  
	     
	    <tr>
	    	<th scope= "col">Comando</th>
	        <th scope= "col">Obiettivo</th>
	        <th scope= "col">Note</th>
	        <th scope= "col">Peso</th>
	    </tr>
	
	    <tbody>
	    <c:forEach items="${mapItem.value}" var="assegnazione" varStatus ="status">
	         <tr>
		         <td>
		         	<span class="label label-warning">
			            <a href="<spring:url value="/dirigenteObjAssegn/${assegnazione.id}/edit/${idIncarico}" htmlEscape="true" />">MODIFICA</a>
			        </span>
		         </td>
		         <td>${assegnazione.obiettivo.descrizione}</td>
		         <td>${assegnazione.note}</td>
		         <td>${assegnazione.peso}</td>
	         </tr>
	    </c:forEach>
	    
	    <!-- totale dei risultati -->
	 		<tr>
	 			<td colspan="3">
	         		totale pesi
	         	</td>
	         	<td>
	         		${mapItem.key.pesoObiettivoAssegnazioni}
	         		 <c:if test="${mapItem.key.pesoObiettivoAssegnazioni != 60}">
	         		 <br /> <p style="color:red;">attenzione il totale pesi è diverso da 60 !</p>
	         		 </c:if>
	         	</td>
	         	                
	           
	            
	         </tr>
	    </tbody>
	    </table>           
	    
	
	<br />
	<hr />    
	</div>
</c:forEach>

<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
    Ge.Ko. by ing. R. Cirrito [view:modifyProgrammazioneCompartoManager.jsp 2019/05/27] 

</div>
</div>    
</div>


</article>       
          
    
</body>
</html>
