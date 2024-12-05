<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Modifica Completamento Azione</title>
        <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
		<link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<h2>Modifica Completamento Azione</h2>

<form:form modelAttribute="azione" method="put">
    
<fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
    <tr>
        <td width="20%"><form:label for="denominazione" path="denominazione">Denominazione</form:label></td>
        <td>${azione.denominazione}</td>
    </tr>
    <tr>
        <td><form:label for="descrizione" path="descrizione">Descrizione</form:label></td>
        <td>${azione.descrizione}</td>
    </tr>
    <tr>
        <td><form:label for="note" path="note">Note</form:label></td>
        <td>${azione.note}</td>
    </tr>
    </table>
</fieldset> 
    
<fieldset>
        <h2 class="tipologia">Tipologia</h2>
        <table width="80%">
    <tr>
        <td width="20%"><form:label for="prodotti" path="prodotti">Prodotti</form:label></td>
        <td>${azione.prodotti}</td>
    </tr>
    <tr>
        <td><form:label for="indicatore" path="indicatore">Indicatore</form:label></td>
        <td>${azione.indicatore}</td>
    </tr>
    <tr>
        <td><form:label for="scadenza" path="scadenza">Scadenza</form:label></td>
        <td><fmt:formatDate value="${azione.scadenza}" pattern="dd/MM/yyyy"/></td>
    </tr>
    
    <tr>
        <td ><form:label for="peso" path="peso">Peso</form:label></td>
        <td>${azione.peso}</td>
    </tr>

    <tr>
        <td ><form:label for="risultato" path="risultato">Risultato</form:label></td>
        <td>${azione.risultato}</td>
    </tr>
    
    </table>
</fieldset>
    
<fieldset>
<h2 class="stato">Livello di Completamento</h2>
<table width="80%">
        
    <tr>
        <security:authorize access="hasRole('CONTROLLER')">
            <td><form:label for="completamento" path="completamento">Completamento</form:label></td>
            <td>

	        <form:select path="completamento" class="large" >
	        	<form:option value="ALTO"/>
            	<form:option value="MEDIO"/>
            	<form:option value="BASSO"/>
            	<form:option value="NULLO"/>
            	<form:option value="DA_VALUTARE"/>
            	<form:option value="INCAR_VARIATO"/>
           	</form:select>	
	        <form:errors path="completamento" cssClass="error"/>
            
            </td>
            
        </security:authorize>
    </tr>
    </table>
</fieldset>
    

    <fieldset>
        <table  width="80%">
          <tr>    
            <td><p class="button"><input type="submit" name="update" value="Aggiorna Completamento Azione"/></p></td>
            <td><p class="button"><input type="submit" name="cancel" value="Esci"/></p></td>
          </tr>
        </table>
    </fieldset>
        
    
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formAzioneCompletamentoController.jsp 2019/11/29]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>