<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Valutazione Comparto</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<div id="wrapper">

<h3> Valutazione comparto da Modificare per <br/> ${azioneAssegnazione.azione.descrizione}</h3>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="azioneAssegnazione" method="put">


    
<fieldset>
<h2 class="info"> Dati descrittivi </h2>
<table width="80%">
    
    <tr>
        <td><form:label for="peso" path="peso">Peso</form:label></td>
        <td><form:input path="peso" size="3" maxlength="3"/></td>
        <td><form:errors path="peso" cssClass="errors"/></td>
    </tr>
    <tr>
        <td width="20%"><form:label for="valutazione" path="valutazione">Tipo</form:label></td>
        <td><form:select path="valutazione">
            <form:option value="ALTO"/>
            <form:option value="MEDIO"/>
            <form:option value="BASSO"/>
            <form:option value="NULLO"/>
            <form:option value="DA_VALUTARE"/>
        </form:select> </td>
        <td><form:errors path="valutazione" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label for="annotazioni" path="annotazioni">annotazioni</form:label></td>
        <td><form:textarea path="annotazioni" size="100" maxlength="100"/></td>
        <td><form:errors path="annotazioni" cssClass="errors"/></td>
    </tr>
</table>
</fieldset> 
    

<fieldset>
<h2 class="stato">Dipendente 
    ${azioneAssegnazione.opPersonaFisica.cognome}
    ${azioneAssegnazione.opPersonaFisica.nome}
</h2>
</fieldset>
   
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Valutazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - ing. Rosario Cirrito </p>      
<p>[view: formAzioneAssegnazioneEditValutazioneManager.jsp 2019/12/09]</p>

</div>
</body>

</html>