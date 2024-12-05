<!-- Footer -------------------------->


<div class="row">
<div class="col-12 col ">
	    
	    <security:authorize access="hasRole('MANAGER')">	    
	    	<a class="btn btn-primary" href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />" role="button">MANAGER</a>    
	    </security:authorize> 
	    
	    <security:authorize access="hasRole('CONTROLLER')">	    
	      	<a class="btn btn-secondary" href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />" role="button">CONTROLLER</a>
	    </security:authorize>  
	    
	    <security:authorize access="hasRole('SUPERCONTROLLER')">
	      	<a class="btn btn-success" href="<spring:url value="/ROLE_SUPERCONTROLLER" htmlEscape="true" />" role="button">SUPERCONTROLLER</a>
	    </security:authorize>
	    
	    <security:authorize access="hasRole('GABINETTO')">	    
	      	<a class="btn btn-danger" href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />" role="button">GABINETTO</a>
	    </security:authorize>
	      
	    <security:authorize access="hasRole('OIV')">
	    	<a class="btn btn-warning" href="<spring:url value="/ROLE_OIV" htmlEscape="true" />" role="button">OIV</a>	    
	    </security:authorize>  
	    
	    <security:authorize access="hasRole('SUPERGABINETTO')">
	    	<a class="btn btn-info"href="<spring:url value="/ROLE_SUPERGABINETTO" htmlEscape="true" />" role="button">SUPERGABINETTO</a>
	    </security:authorize>
</div>
</div>
<div class="row">
<div class="col-12 col ">
<p>pageName: ${pageName}</p>
</div>
</div>
