<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Obiettivi della Struttura del Dirigente</title>
	<link href="<spring:url value="/resources/bootstrap336/css/bootstrap.css" htmlEscape="true" />" rel="stylesheet"/>
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

<section>   
<h1 id="titolo-lista">Visualizza Programmazione Obiettivi per <em>${struttura} </em>- anno ${anno}</h1>
<hr />   
<h4>Responsabile: ${responsabile}</h4> 
<hr />   

<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>

<!-- table obiettivi -->
<table class="table table-bordered table-striped">
	<colgroup>
		<col class="desrc">
		<col class="desrc">
		<col class="name">
		<col class="name">
	</colgroup>   
    <tbody>
    <thead> 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	<!-- riga obiettivo -->  
	<tr class="success">
		<!-- descrizione -->
	    <td>     
		    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
		    </c:if>
		    ${obj.codice} -  ${obj.descrizione} 
	    </td>
	    
	    <!-- note -->
	    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
	    
	    <!-- stato -->
	    <td>${obj.statoApprov} </td>
	    
	    <!-- peso -->
	    <td>${obj.peso}
	    <c:if test="${obj.pesoAzioni > 100}"><span class="warning"> attnz. peso Azioni= ${obj.pesoAzioni} maggiore di 100!</span></c:if>
	    <c:if test="${obj.pesoAzioni < 100}"><span class="warning"> attnz. peso Azioni= ${obj.pesoAzioni} minore di 100!</span></c:if>
	   	</td> 
   	</tr>
	
	<!-- riga tabella dipendenti -->
	<tr>
	<td colspan="4"> 
		<table class="table table-striped">
		<colgroup>
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup>   
	    <tbody>
	    <thead> 
			<tr>
				<th scope= "col" >dipendente</th>
		    	<th scope= "col" >note</th>
		        <th scope= "col" >peso</th>
		    </tr>  
		</thead>
		
		<c:forEach items="${obj.assegnazioni}" var="objassegn">
            <tr>
            	<td>${objassegn.opPersonaFisica.stringa}</td>
	            <td>${objassegn.note}</td>
	            <td>${objassegn.peso}</td>
	     	</tr>       	
         </c:forEach>
         </tbody>
	   </table> <!-- FINE TABELLA DIPENDENTI -->     
	  </td>
	</tr>
	
	<!-- riga tabella AZIONI -->
	<tr>
	    <td colspan="4">    
	    <table class="table table-bordered table-striped">
			<colgroup>
				<col class="desrc">
				<col class="desrc">
				<col class="name">
				<col class="name">
				<col class="name">
				<col class="name">
				<col class="name">
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
	            </tr>  
			</thead> 
		    <c:forEach items="${obj.azioni}" var="act">
		        <tr>
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
			        <td><c:if test="${act.peso >0 }" >${act.peso}</c:if></td>
			        </tr>
	        </c:forEach>
		    </tbody>
			</table> <!-- fine tabella azioni -->   
	
		    </td>
	    </tr>
	    
	    <!-- riga vuota di separazione -->
    	<tr>
    	<td colspan="4"></td>
    	</tr>
</c:forEach> 

</table><!-- fine tabella OBIETTIVI --> 
                

</section>
</article>  
    
<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
    Ge.Ko. by ing. R. Cirrito [listPianificazioneIncaricoManager.jsp 2019/05/23] 
</div>
</div>
</div>
  
    
</body>
</html>
