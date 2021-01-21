<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<%
		String itemNo = request.getParameter("itemNo");
%>
<script>
	$(function () {
		$.ajax({
			url: '../getProduct',
			data:{item_no:	<%=itemNo%>},
			dataType: 'json',
			success: function(result){
				console.log(result);
				$('#item_no').val(result.itemNo);
				$('#item').val(result.item);
			},
			error: function(reject){
				console.log(reject);
				
			}
		});
		$('#btnChange').on('click' , function() {
			
		})
	});
</script>
</head>
<body>
				<input type='text' id='item_no' name='item_no'>
				<input type='text' id='item' name='item'>
				<input type='submit' value='수정'>
</body>
</html>