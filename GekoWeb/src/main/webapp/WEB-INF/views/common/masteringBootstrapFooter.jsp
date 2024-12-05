<!-- Footer -------------------------->
 
<div class="row">
	    <div class="col-12 col">
		    <security:authorize access="hasRole('MANAGER')">
		      <a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />" class="btn btn-primary">MANAGER</a>
		    </security:authorize> 
		    <security:authorize access="hasRole('CONTROLLER')">
		  
		      <a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />" class="btn btn-success" >CONTROLLER</a>
		   
		    </security:authorize>  
		    <security:authorize access="hasRole('SUPERCONTROLLER')">
		      <a href="<spring:url value="/ROLE_SUPERCONTROLLER" htmlEscape="true" />" class="btn btn-info">SUPERCONTROLLER</a>
		    </security:authorize>
		    
		   <security:authorize access="hasRole('GABINETTO')">
		      <a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />"class="btn btn-warning">GABINETTO</a>
		    </security:authorize>  
		    
		    <security:authorize access="hasRole('OIV')">
		  	      <a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />"class="btn btn-danger">OIV</a>
		    </security:authorize>  
		    <security:authorize access="hasRole('SUPERGABINETTO')">
		   
		      <a href="<spring:url value="/ROLE_SUPERGABINETTO" htmlEscape="true" />"class="btn btn-primary">SUPERGABINETTO</a>
		    
		    </security:authorize>
	    </div>
  </div>
  
 <div class="row">
  <div class="col-3 col">
    Ge.Ko. by ing. R. Cirrito </td>
  </div>
  <div class="col-1 col">
   	13/04/2018 </td>
  </div>
   <div class="col-8 col">
    [${pageName}] 
  </div>
  

</div>