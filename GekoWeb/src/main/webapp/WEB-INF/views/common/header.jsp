<!--
	GE.KO. :: controllo di gestione
-->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
  <meta charset="ISO-8859-1" />
  <link rel="stylesheet" href="<spring:url value="/styles/irc.css" htmlEscape="true" />" type="text/css"/>
  <title>GE.KO :: controllo di gestione della Segreteria Generale - Regione Siciliana</title>	
</head>
        
<body>
    <security:authentication property="name" var="nomeUtente"/>
    <security:authentication property="authorities" var="ruoli" />

  <div id="main">