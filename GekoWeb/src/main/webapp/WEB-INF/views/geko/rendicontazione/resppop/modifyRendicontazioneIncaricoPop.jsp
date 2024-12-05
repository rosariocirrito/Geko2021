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
		}
		.col{
			padding:8px;
			
		}
	</style>
</head>

<body>
<div class="container" >

<div class="jumbotron">
<div class="row">
	<div class="col-lg-12 col"> 
	<h2 >Rendicontazione Obiettivi per <em>${struttura} </em>- anno ${anno}</h2>
	<h4>Responsabile: ${responsabile}</h4>   
	        
	<c:if test="${empty listObiettivi}">
	    <p>Nessun obiettivo trovato.</p>
	</c:if>
	</div>           
</div>  
</div>
        
<!-- per ogni obiettivo --> 
<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
<div class="jumbotron">

<!-- riga obiettivo -->
<div class="row" style="background: lightgreen;">
	<div class="col-lg-2 col" >
		<h3>ID</h3>
		<h4>${obj.codice}</h4> [${obj.idObiettivo}]
	</div>
	<div class="col-lg-7 col">
		<h3>Obiettivo</h3>
		<h4>
		<c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
		    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
	    </c:if>
	    <c:if test="${obj.apicale}">APICALE</c:if>  
	    	${obj.descrizione} 
	    </h4>	
	    <c:if test="${obj.note> ''}"> 
	    	<br />
	    	<em>nota:${obj.note}</em>
	    </c:if>
	</div>
	<div class="col-lg-2 col">
		<h3>stato</h3>${obj.statoApprov}
	</div>
	<div class="col-lg-1 col">
		<h3>peso</h3>
		<c:if test= "${obj.peso >0}"> ${obj.peso} </c:if>
	</div>
</div>	<!-- chiudi riga obiettivo -->

<!-- per ogni azione -->
	<c:forEach items="${obj.azioni}" var="act">   
	   
		<!-- prima riga azione -->
		<div class="row" style="background: lightblue;">
			<div class="col-lg-2 col" >
			<h4>ID</h4>
			<h4>${act.denominazione}</h4> [${act.idAzione}]
		</div>
		<div class="col-lg-4 col"><h4>Azione</h4>
			<h4>
			<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			        ${act.descrizioneProp}
		    </c:if>
	       	${act.descrizione}
	       	</h4>
		<c:if test="${act.note>''}">
			<em>nota: ${act.note} </em>
		</c:if>
		</div>
		<div class="col-lg-1 col"><h4>indicatore previsto</h4>
			${act.indicatore}
		</div>
		<div class="col-lg-1 col"><h4>valore obiettivo</h4>
			${act.prodotti}
		</div>
		<div class="col-lg-1 col"><h4>scadenza</h4>
			<fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
		</div>
		<div class="col-lg-1 col"><h4>peso</h4>
			<c:if test="${act.peso >0 }" >${act.peso}</c:if>
		</div>
		<div class="col-lg-2 col"><h4>risultato</h4>
		<c:if test="${obj.rendicontabile }" > 
		<span class="label label-info">
				<a href="<spring:url value="/resppopAct/${act.idAzione}/editRisultato/${idIncarico}" htmlEscape="true" />">Aggiungi / Modifica</a>
        </span>
        <br />
        </c:if>
			${act.risultato}
		</div>
	</div> <!-- fine riga azione -->

	<!-- seconda riga documenti -->
	<div class="row" style="background: lightyellow;">
		<div class="col-lg-12 col">
		<span class="label label-success">
	        <a href="<spring:url value="/respPopDocumenti/new/${act.idAzione}" htmlEscape="true" />">Aggiungi nuovo documento</a>
	        </span>
		</div>
    </div>
	<div class="row" style="background: lightyellow;">
		<div class="col-lg-1 col"><h4>ID</h4></div>
		<div class="col-lg-1 col"><h4>Data Creazione</h4></div>
		<div class="col-lg-3 col"><h4>Nome Documento</h4></div>
		<div class="col-lg-3 col"><h4>Descrizione estesa</h4></div>
		<div class="col-lg-4 col"><h4>Nome File</h4></div>
    </div>	
	<c:forEach items="${act.documenti}" var="docu">
		<div class="row" style="background: lightyellow;">
			<div class="col-lg-1 col">[${docu.id}]</div>
			<div class="col-lg-1 col"><fmt:formatDate value="${docu.creato}" pattern="dd/MM/yyyy"/></div>
			<div class="col-lg-3 col">
			<c:if test="${docu.editable}" > 
				<span class="label label-success">
              	<a href="<spring:url value="/respPopDocumenti/${docu.id}/editDescrizione" htmlEscape="true" />">Edit nome/descrizione --></a>
              	</span>
            </c:if>	
           	<a href="<spring:url value="/respPopDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nome} </a>
         	</div>
         	<div class="col-lg-3 col">
         		${docu.descrizione}
         	</div>
         	<div class="col-lg-4 col">
         		<span class="label label-success">
		         	<a href="<spring:url value="/respPopDocumenti/${docu.id}/editFile" htmlEscape="true" />">Edit File --></a>
	         	</span>
	         	${docu.nomefile}
		   	</div>
        </div>
	
	    </c:forEach>       
       <!-- fine righe documenti -->
        
        
			        
		   
       
    

	</c:forEach> 
	</div>
</c:forEach> <!-- fine loop obiettivo -->



    
<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_RESP_POP" htmlEscape="true" />">Menu Resp_POP</a></span>
    Ge.Ko. by ing. R. Cirrito [modifyRendicontazioneIncaricoPop.jsp 2020/12/07] 
</p>
</div>   
</body>
</html>
