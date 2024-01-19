$(function(){
	if(localStorage.getItem("id")==null){
		alert("null id")
	}else{
		getData(localStorage.getItem("id"))
	}
})
var bioId = null
var uId = null

function getData(id){
	//alert(id)
	uId = id
	$.ajax({
		url:"http://localhost:8888/user/"+id,
		type:"get",
		contentType:"application/json",
		success:function(dt){
			if(dt.biodataId!=null){
				bioId = dt.biodataId
				$.ajax({
					url:"http://localhost:8888/biodata/"+dt.biodataId,
					type:"get",
					contentType:"application/json",
					success:function(dta){
						$('#nama').val(dta.fullname)
						//$('#tgl').val()
						$('#hp').val(dta.mobilePhone)
						//console.log(dta)
						$.ajax({
							url:"http://localhost:8888/api/customer/bio/"+dt.biodataId,
							type:"get",
							contentType:"application/json",
							success:function(dtt){
								
								$('#tgl').val(dtt.dob.substring(0,10))
							}
						})
					}
				})
			}
			$('#email').val(dt.email)
			$('#pass').val(dt.password)
		}
	})
}

function logout(){
	localStorage.setItem("id", null)
	localStorage.setItem("role", null)
	localStorage.setItem("name", null)
	window.location.href = "/index";
}
