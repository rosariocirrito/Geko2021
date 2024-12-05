<article>
<div id="wrapper">
<h1 id="titolo-lista">Lista Strutture di: ${dipartimento} - ${dipartimento.responsabile}</h1>
<ul>   
<section id="obj-sezione">   
<c:forEach items="${dipartimento.struttureAttive}" var="servizio">   
    <li class="servizio">${servizio.denominazione} - ${servizio.responsabile}</li>   
    <ul>     
    <c:forEach items="${servizio.struttureAttive}" var="uob">
    	<li class="uob">${uob.denominazione}- ${uob.responsabile}</li>  
	</c:forEach>
	</ul>
</c:forEach>
</section>
</ul> 
</div>
</article>     