/**
* cafeList.js
*/

$(function () {
	$.ajax({
	url: '../getProdList',
	dataType:'json',
	success: showContents,
	error: showErrors
	});
	
	})


function showErrors(result) {
	console.log(result);

}

function showContents(result) {						
	console.log(result);   
	let data=result;
	for(val of data) {
	//let db_value='data_value';
	let elem_1, elem_2, elem_3;	

	//첫번째 자식 요소. <a>태그
	elem_1 = $('<a />').attr('href', 'db_value');
	let e1_img = $('<img />').attr('src', '../images/' + val.image)
							 .attr('alt', 'db_value');
	e1_img.addClass("card-img-top");
	elem_1.append(e1_img);

	//두번째 자식 요소.
	elem_2 = $('<div />').addClass("card-body");
	let e2_h4 = $('<h4 />').addClass("card-title");
	e2_h4.append($('<a />').html(val.item).attr('herf', 'db_value'));
	let e2_h5 = $('<h5 />').html(val.price);
	let e2_p = $('<p />').html(val.content);
	elem_2.append(e2_h4, e2_h5, e2_p);


	//세번째 자식 요소.
	elem_3 = $('<div />').addClass("card-footer");
	let star='&#9733;';							//별모양
	for(let i=0; i<val.like_it; i++) {			//like_it 갯수만큼 별모양 반복
		star += '&#9733;';
	}
	let em3_small = $('<small />').addClass("text-muted")
		.html(star);
	elem_3.append(em3_small);

	//제일 밖에 있는 div 2가지 
	let div_1, div_2
	div_1 = $('<div />').addClass("col-lg-4 col-md-6 mb-4");
	div_2 = $('<div />').addClass("card h-100");

	div_1.append(div_2);
	div_2.append(elem_1, elem_2, elem_3);

	$('.col-lg-9 .row').append(div_1);			//상단의 class="col-lg-9"
		
	}
			

}