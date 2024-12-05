<article>
    <div id="wrapper">
    <h1 id="titolo-lista">Lista Strutture di: <em>${dipartimento}</em> - ${dipartimento.responsabile}</h1>
    <ul>
    <c:forEach items="${dipartimento.dipendenti}" var="dip">
    	<li class="dipendente"> 
    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} -${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	</li>
    </c:forEach>
    </ul>
<ul>   
<section id="obj-sezione">   
<c:forEach items="${dipartimento.struttureAttive}" var="servizio">   
    <li class="servizio">${servizio.denominazione} - ${servizio.responsabile}</li>   
    <ul>
    <c:forEach items="${servizio.dipendenti}" var="dip">
    	<li class="dipendente"> 
    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} - ${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	</li>
    </c:forEach>
    </ul>
    <ul>     
    <c:forEach items="${servizio.struttureAttive}" var="uob">
    	<li class="uob">${uob.denominazione}- ${uob.responsabile}</li> 
    	<ul>
    <c:forEach items="${uob.dipendenti}" var="dip">
    	<li class="dipendente">  
    	[id: ${dip.idPersona}] - [matr: ${dip.matricola}] - ${dip.cognome} -${dip.nome} - ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
    	</li>
    </c:forEach>
    </ul> 
	</c:forEach>
	</ul>
</c:forEach>
</section>
</ul> 
</div>
</article>  