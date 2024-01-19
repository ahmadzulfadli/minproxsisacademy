$(function() {
	fetchmenu(localStorage.getItem("id"))
})

function fetchmenu(userId) {
	$.ajax({
		url: `http://localhost:8888/landingpage/menu/${userId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = ""
			str +='<div class="d-sm-flex align-items-center justify-content-between mb-4">'
			str +='<h1 class="h3 mb-0 text-gray-800">Dashboard</h1>'		
			str +='</div>'
			str+='<div class="row">'		
			for (var i = 0; i < data.length; i++) {
				if (data[i].url != "#") {
					var link=data[i].url
					str += '<div class="col-xl-5 mb-5 pb-5  pt-5 pl-5 ml-5">'
					str += '<div class="card border-left-primary shadow">'
					str += `<button class="btn btn-outline-light" onclick="window.location.href='${link}'">`
					str += '<div class="card-body">'
					str += '<div class="row no-gutters align-items-center">'
					str += '<div class="col mr-2">'
					str += '<div class="text-xs font-weight-bold text-primary text-uppercase mb-1" style="font-size: medium;">'+data[i].name+'</div>'
					str += '</div>'
					str += '<div class="col-auto">'
                    str += data[i].bigIcon
                    str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</button>'
					str += '</div>'
					str += '</div>'
				}
			}
			str += '</div>'
			console.log(str)
			$('#dashboard').html(str)
		}
	})
}