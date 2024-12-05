<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
<title>Maschera Azione</title>

<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>

</head>

<body>
<div id="wrapper">
        
<h2>Azione da rendere tassativa</h2>

<form:form modelAttribute="azione" method="put">
    

    
<fieldset>
<h2 class="info"> Dati descrittivi </h2>
<ul>
    
    <li>
        Descrizione: ${azione.descrizione}
    </li>
    
</ul>
</fieldset> 


    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Togli Tassativa ad Azione"/>
	   
	   
	   <input type="submit" name="delete" value="Cancella Azione"/>
	   
	</li>
	<li>   
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
        
        
</form:form>
<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formAzioneRendiTassativaGabinetto.jsp 2019/07/23]</p>

</div>
</body>

</html>