function login(){
	var str = '<form>'
 	str += '<div class="form-group">'
 	//str += '<label class="form-text">Masuk</label>'
    str += '<div class="alert alert-danger form-group" role="alert">'
	str += '</div>'
	str += '<label>Email*</label>'
    str += '<input type="email" class="form-control" id="loginEmail">'
    str += '<br>'
    str += '<label>Password*</label>'
    str += '<div class="input-group" id="show_hide_password">'
    str += '<input class="form-control border-right-0" type="password" id="loginPassword">'
    str += '<div class="input-group-text bg-white border-left-0">'
    str += '<i class="bi bi-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="toggleLoginPass(this)"></i>'
    str += '</div>'
    str += '</div>'
    //str += '<small id="loginHelp" class="form-text text-muted"></small>'
    str += '<br>'
    str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="checkLogin()">Masuk</button>'
	str += '</div>'
	str += '<br>'
	str += '<div class="text-center">'
	str += '<h5><b><a class="link" onclick="forgotPassword()">Lupa Password?</a></b></h5>'
	str += '<h6><b>atau</b></h6>'
	str += '<h5><b>Belum Memiliki Akun? <a class="link" onclick="doRegister()">Daftar</a></b></h5>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	
	$('.modal-title').html('Masuk')
	$('.modal-body').html(str)
	$('.modal-footer').hide()
	$('.alert').hide()
	$('#btnSubmit').html('Kirim OTP')
	$('#modal').modal('toggle')
}

function tutup(){
	$('#modal').modal('toggle')
}

function toggleLoginPass(t){
	t.classList.toggle('bi-eye')
	t.classList.toggle('bi-eye-slash')

	if($('#loginPassword').attr("type")=="password"){
		$('#loginPassword').attr("type","text")
	}else{
		$('#loginPassword').attr("type","password")
	}
}

function checkLogin(){
	var email = $('#loginEmail').val()
	var password = $('#loginPassword').val()
	var checks = '{'
	checks += '"email":"'+email+'",'
	checks += '"password":"'+password+'"'
	checks += '}'
	//alert("Login "+email+" "+password)
	$.ajax({
		url:"http://localhost:8888/user/login",
		type:"post",
		contentType:"application/json",
		data: checks,
		success:function(res){
			if(res.status=="Account Locked"){
				//$('#loginHelp').html(' * Akun anda terkunci!')
				$('.alert').html(' * Akun Anda Terkunci!')
				$('.alert').show()
			}else if(res.status=="Wrong Password"){
				//$('#loginHelp').html(' * password salah')
				$('.alert').html(' * Password Salah')
				$('.alert').show()
			}else if(res.status=="Wrong Password Locked"){
				//$('#loginHelp').html(' * password anda salah sebanyak 3 kali. Akun anda dikunci')
				$('.alert').html(' * PAssword Anda Salah Sebanyak 3 Kali. Akun Anda Dikunci')
				$('.alert').show()
			}else if(res.status=="Email not found"){
				//$('#loginHelp').html(' * akun tidak ditemukan!')
				$('.alert').html(' * Akun Tidak Ditemukan')
				$('.alert').show()
			}else{
				localStorage.setItem("id", res.id)
				localStorage.setItem("role", res.role)
				localStorage.setItem("name", res.name)
				//alert(res)
				console.log(res.id+" "+res.role+" "+res.name)
				window.location.href = "/login";
			}
		}
	})
}

function forgotPassword(){
	//alert("Forgot Password")
	$('#modal').modal('hide')
	setTimeout(function(){lupaPassword()}, 500)
	//lupaPassword()
}

function doRegister(){
	$('#modal').modal('hide')
	setTimeout(function(){register()}, 500)
}