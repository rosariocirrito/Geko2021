<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Lista Dipendenti del Dipartimento</title>
    <link href="../../resources/css/lista.css" rel="stylesheet"/>
</head>

<body>
        <security:authentication property="name" var="nomeUtente"/>
        <security:authentication property="authorities" var="ruoli" />
        
<div id="wrapper">     
<article>
<section id="obj-sezione">   
<h2 id="titolo-lista">Lista Missioni</h2>   
<hr />      

<c:if test="${empty listMissioni}">
    <p>Missioni non trovate !</p>
</c:if>

<c:forEach items="${listMissioni}" var="missione">
               
	<table>
	<tbody>   
	    <tr>
	        <th colspan="3"><h1>${missione.codice} - ${missione.descrizione}</h1> </th>
	    </tr>  
	     
	    
        <c:forEach items="${missione.programmi}" var="prog" varStatus ="status">
	        <tr>
	            <th> 
	                <h4>${prog.descrizione}</h4>
	            </th>
	        </tr>
	        
	    </c:forEach>
        
        
        </tbody>
       </table>       
                       
       <br/>                
</c:forEach>

</section>
</article>   
</div>    
           
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

 
</body>
</html>
