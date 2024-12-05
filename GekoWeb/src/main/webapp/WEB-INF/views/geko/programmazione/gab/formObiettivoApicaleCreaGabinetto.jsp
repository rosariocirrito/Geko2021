<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Creazione Obiettivo Apicale</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
	<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>
</head>

<body>
<security:authorize access="hasRole('GABINETTO')">
<div id="wrapper">
            
<h2 > Nuova programmazione apicale per anno ${obiettivo.anno}</h2>
<form:form  modelAttribute="obiettivo" method="post">
        
<fieldset>
<h2 class="info"> Dati descrittivi  </h2>
<table width="80%">
   <tr>
       <td width="20%"><form:label for="codice" path="codice">Codice (es. APIC_1)</form:label></td>
       <td><form:textarea path="codice" size="16" maxlength="16"/></td>
       <td><form:errors path="codice" cssClass="error"/></td>
   </tr>
   <tr>
       <td><form:label for="descrizione" path="descrizione">Descrizione estesa</form:label></td>
       <td><form:textarea path="descrizione" size="300" maxlength="1000"/></td>
       <td><form:errors path="descrizione" cssClass="error"/></td>
   </tr>
   <tr>
       <td><form:label for="note" path="note">Note eventuali</form:label></td>
       <td><form:textarea path="note" size="1000" maxlength="1000"/></td>
       <td><form:errors path="note" cssClass="error"/></td>
   </tr>
   
   <tr>
   	   <td><form:label for="pesoApicale" path="pesoApicale">Peso apicale</form:label></td>
       <td>	<form:input class="form-control" path="pesoApicale" maxlength="3"/></td>
       <td> <form:errors path="pesoApicale" cssClass="error"/> </td>
	</tr>
	
</table>  
</fieldset>
        

        
<fieldset>
<h2 class="stato">Stato della Programmazione</h2>
<table  width="80%">
	<tr>
        <td width="20%"><form:label for="statoApprov" path="statoApprov">Stato</form:label></td>
        <td><form:select path="statoApprov">
        	<form:option value="INTERLOCUTORIO"/>
        	<form:option value="RICHIESTOGAB"/>
            </form:select>
        </td>
        <td><form:errors path="statoApprov" cssClass="error"/></td>
	</tr>   
</table>  
</fieldset>
        
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>
	    <input type="submit" name="add" value="Crea l'obiettivo apicale"/>  
       	<input type="submit" name="cancel" value="Rinuncia ed esci"/>
    </li>
</ul>  
</fieldset>
        

</form:form>         
        
<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formObiettivoApicaleCreaGabinetto.jsp 2019/07/22]</p>


</div>
  
</security:authorize>
</body>

</html>
