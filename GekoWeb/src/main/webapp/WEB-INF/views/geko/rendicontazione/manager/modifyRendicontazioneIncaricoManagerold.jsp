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
			margin-left:2px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:4px;
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
<h1 id="titolo-lista">Rendicontazione Obiettivi per <em>${struttura} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>   
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
           
<%@ include file="/WEB-INF/views/geko/rendicontazione/manager/tableObjsRendMod.jsp" %>

</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[modifyRendicontazioneIncaricoManager.jsp 31/05/2017] </td>
    </tr>
  </table>
</div>
  
</div>
</div>    
</div>    
</body>
</html>
