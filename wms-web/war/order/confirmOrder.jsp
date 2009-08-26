<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>guestbook Index</title>
<link rel="stylesheet" type="text/css" href="/css/global.css" />
</head>
<body>
<form method="post" action="/order/commitOrder">
<div id="container">
<div id="commands">
<div><c:out value="${eat}" /><input type="hidden" name="eat" value="<c:out value="${eat}" />" /></div>
<div><c:out value="${ammount}" /><input type="hidden" name="ammount" value="<c:out value="${ammount}" />" /></div>
<div><c:out value="${type}" /><input type="hidden" name="type" value="<c:out value="${type}" />" /></div>
</div>
<div id="commands">
<input type="submit" value="確認" />
</div>
</div>
</form>
</body>
</html>