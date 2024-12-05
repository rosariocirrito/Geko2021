<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Programmazione Obiettivi Apicali per dipartimento: <em>${dipartimento} </em>- anno ${anno}</title>
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
<h1 id="titolo-lista">Programmazione Obiettivi Apicali per dipartimento: <em>${dipartimento} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            
<%@ include file="/WEB-INF/views/common/tableObjsApicali.jsp" %>


</section>
</article>
    
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
