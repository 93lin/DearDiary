<%--
    Document   : index
    Created on : 2011-12-28, 20:31:47
    Author     : jhsr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<body style="padding:100px;">
		<div style="width:900px;margin: 0px auto;">
			<div style="text-align: left;padding-top:50px;">
				<div>                     
					<br />
					<h1>${empty errorMsg ? i18n.error404_error_no_em_suffix_page : errorMsg}</h1>
                    <br/>
				</div>
			</div>
		</div>
	</body>
</html>
