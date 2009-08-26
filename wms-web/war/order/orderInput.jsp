<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/global.css" />
<title>guestbook Index</title>
<link rel="stylesheet" type="text/css" href="/css/global.css" />
</head>
<body>
<form method="post" action="/order/stageOrder">
<div id="container">
<div id="labels">
<div>入荷予定日：</div>
<div>物品ID：</div>
<div>数量：</div>
</div>
<div id="inputs">
<div><input type="text" name="eat" /></div>
<div><input type="text" name="type"></div>
<div><input type="text" name="ammount" /></div>
</div>
<div id="commands">
<input type="submit" value="確認" />
</div>
</div>
</form>
</body>
</html>