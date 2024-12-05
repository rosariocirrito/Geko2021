<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security"  uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Modifica Valutazione Dirigenziale Apicale</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<style>
		.row{
			margin-top:1px;
			margin-left:16px;
			background-color:lightgray;
		}
		.rowobj{
			margin-top:1px;
			margin-left:16px;
			background-color:lightgreen;
		}
		
		.col{
			background-color:lightblue;
			padding:10px;
			border:1px solid darkgray;
		}
		.colobj{
			background-color:lightgreen;
			padding:10px;
			border:1px solid darkgray;
		}
		
		
	</style>
	
</head>
    
<body>
<div class="container-fluid" >
<div class="jumbotron" > 
<div class="row">
	<div class="col-12 col">    
	<h3>Modifica Valutazione Dirigenziale Apicale:  ${incarico.denominazioneStruttura}</h3>     	
	</div>
</div>	
<hr />      
        


<div class="row">
	<div class="col-12 col">           
		
		 <h4 > Responsabile  ${incarico.responsabile} con incarico dal <fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/>
		 al <fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/>
		 <c:if test="${incarico.interim}">
		     AD INTERIM
		</c:if>
		 </h4>
</div>
</div>
	
 	
	
	
	
<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
<div class="row">
	<div class="col-6 col">Comportamenti Organizzativi</div>
	<div class="col-2 col">Peso Assegnato</div>
	<div class="col-2 col"><a href="../../../gabinettoValutazione/${valutazione.id}/editValutComportOrgan">Edit</a>Valutazione</div>
	<div class="col-2 col">Punteggio</div>
</div>

<div class="row">
   	<div class="col-6 col">Capacità di intercettare, gestire risorse e programmare</div>
	<div class="col-2 col">${valutazione.gestRealAss}</div>
	<div class="col-2 col">${valutazione.gestRealVal}</div>
	<div class="col-2 col">${valutazione.gestPunteggio}</div>
</div>

<div class="row">
   	<div class="col-6 col">Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</div>
	<div class="col-2 col">${valutazione.analProgrAss}</div>
	<div class="col-2 col">${valutazione.analProgrVal}</div>
	<div class="col-2 col">${valutazione.analPunteggio}</div>
</div>

<div class="row">
   	<div class="col-6 col">Capacita' di valorizzare competenze ed attitudini dei propri collaboratori</div>
	<div class="col-2 col">${valutazione.relazCoordAss}</div>
	<div class="col-2 col">${valutazione.relazCoordVal}</div>
	<div class="col-2 col">${valutazione.relazPunteggio}</div>	
</div>
	
<div class="row">
   	<div class="col-6 col">Capacità di individuazione del livello di priorità degli interventi da realizzare</div>
	<div class="col-2 col">${valutazione.pdlAss}</div>
	<div class="col-2 col">${valutazione.pdlVal}</div>
	<div class="col-2 col">${valutazione.pdlPunteggio}</div>	
</div>
	
	
	<div class="row">
			<div class="col-6 col ">
				Comportamento Organizzativo ( peso = 30)
			</div>
			<div class="col-2 col ">
				${valutazione.gestRealAss+valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}
			</div>
			<div class="col-2 col "></div>
			<div class="col-2 col ">
				<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
				${valutazione.gestPunteggio+valutazione.analPunteggio+valutazione.relazPunteggio+valutazione.pdlPunteggio} " pattern="###.#"/>
			</div>
		</div>
	
	
	</c:forEach>
	

	
<c:if test="${empty incarico.valutazioni}">
<div class="row">
 <div class="col-12 "> 
    <p><a href=\"../../../gabinettoValutazione/new/${incarico.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${incarico.responsabile} </a></p></p>
</div>
</div>
</c:if>

<div class="row">
<div class="col-12 col ">
<hr/>
Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [modifyValutazioneApicaleGabinetto] [ultima modifica: 2019/11/28]
<hr/>
</div>
</div>

 <%@ include file="/WEB-INF/views/common/footer.jsp" %>  
 
</div>
</div> <!-- container -->   

<script src="<spring:url value="/resources/bootstrap4.0.0/js/bootstrap.js" htmlEscape="true" />"></script>

</body>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</html>




