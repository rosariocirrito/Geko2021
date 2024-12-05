<article>
<section id="obj-sezione">  
<div id="wrapper">
<h1 id="titolo-lista">Modifica Strutture/dipendenti per: <em>${dipartimento}</em></h1>

<a href="<spring:url value="/administratorStruttura/new/${dipartimento.idPersona}" htmlEscape="true" />">CREA NUOVA STRUTTURA</a>
<hr />      

<c:if test="${empty strutture}"><p>Nessuna struttura trovata !</p></c:if>
    
<ul>
	<c:forEach items="${dipartimento.dipendenti}" var="dip">
    	<li class="dipendente"> 
    	<a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /edit" htmlEscape="true" />">MODIFICA</a>
	    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /sposta" htmlEscape="true" />">SPOSTA</a>
	    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /trasferisci" htmlEscape="true" />">TRASFERISCI</a>     	
    	
    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} -${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	
    	</li>
    </c:forEach>
</ul>

<ul>   
 
<c:forEach items="${dipartimento.struttureAttive}" var="servizio">   

    <li class="servizio">
		 <a href="<spring:url value="/administratorStruttura/${servizio.idPersona} /edit" htmlEscape="true" />">MODIFICA </a>
	    [id: ${servizio.idPersona}] - ${servizio.codice} / ${servizio.denominazione} &nbsp (creata il <fmt:formatDate value="${servizio.dataInserimento}" pattern="dd/MM/yyyy"/> ) &nbsp
	    <c:if test="${servizio.opPersonaGiuridicaTipo.idopPersonaGiuridicaTipo == 3}">
	    	<a href="<spring:url value="/administratorStruttura/new/${servizio.idPersona}" htmlEscape="true" />">AGGIUNGI UOB</a> &nbsp
	    </c:if>
	        <a href="<spring:url value="/administratorDipendenti/aggiungiDipendente/${servizio.idPersona}" htmlEscape="true" />">AGGIUNGI DIPENDENTE</a>
	</li>   
    <ul>
    <c:forEach items="${servizio.dipendenti}" var="dip">
    	<li class="dipendente">
    	<c:if test="${dip.opPersonaGiuridica == servizio }">
    	<a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /edit" htmlEscape="true" />">MODIFICA</a>
	    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /sposta" htmlEscape="true" />">SPOSTA</a>
	    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /trasferisci" htmlEscape="true" />">TRASFERISCI</a>     	
    	</c:if>	
    	<c:if test="${dip.opPersonaGiuridica != servizio }">
    	${dip.opPersonaGiuridica.codice}
    	</c:if> 
    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} - ${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	</li>
    </c:forEach>
    </ul>
    <ul>     
    <c:forEach items="${servizio.struttureAttive}" var="uob">
    	<li class="uob">
    	<a href="<spring:url value="/administratorStruttura/${uob.idPersona} /edit" htmlEscape="true" />">MODIFICA </a>
    	[id: ${uob.idPersona}] - ${uob.codice} / ${uob.denominazione} &nbsp (creata il <fmt:formatDate value="${uob.dataInserimento}" pattern="dd/MM/yyyy"/> ) &nbsp
    	<a href="<spring:url value="/administratorDipendenti/aggiungiDipendente/${uob.idPersona}" htmlEscape="true" />">AGGIUNGI DIPENDENTE</a>
    	</li> 
    	<ul>
    	<c:forEach items="${uob.dipendenti}" var="dip">
    	<li class="dipendente">  
	    	<c:if test="${dip.opPersonaGiuridica == uob }">
	    	<a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /edit" htmlEscape="true" />">MODIFICA</a>
		    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /sposta" htmlEscape="true" />">SPOSTA</a>
		    <a href="<spring:url value="/administratorDipendenti/${dip.idPersona} /trasferisci" htmlEscape="true" />">TRASFERISCI</a>     	
	    	</c:if>	
	    	<c:if test="${dip.opPersonaGiuridica != uob }"> 	${dip.opPersonaGiuridica.codice}   	</c:if>
	    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} -${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	</li>
    	</c:forEach>
    	</ul> 
	</c:forEach>
	</ul>
</c:forEach>
</ul> 
</section>

</div>
</article>  