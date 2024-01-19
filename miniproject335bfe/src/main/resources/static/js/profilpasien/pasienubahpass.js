function f_ubahPass(){
	var str = '<form>'
 	str += '<div class="form-group">'
 	str += '<div class="alert alert-danger form-group" role="alert"></div>'
 	str += '<label class="form-text">Masukkan password anda saat ini.</label>'
    str += '<label>Password</label>'
    str += '<div class="input-group" id="show_hide_password">'
    str += '<input class="form-control border-right-0" type="password" id="inputPassword">'
    str += '<div class="input-group-text bg-white border-left-0">'
    str += '<i class="fa fa-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(1,this)"></i>'
    str += '</div>'
    str += '</div>'
    str += '<br>'
    str += '<label>Masukkan Ulang Password</label>'
    str += '<div class="input-group" id="show_hide_password2">'
    str += '<input class="form-control border-right-0" type="password" id="inputPassword2">'
    str += '<div class="input-group-text bg-white border-left-0">'
    str += '<i class="fa fa-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(2,this)"></i>'
    str += '</div>'
    str += '</div>'
    str += '<br>'
	str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="confirmOldPass()">Ubah Password</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Ubah Password')
	$('.modal-footer').hide()
	$('.modal-body').html(str)
	$('#modal').modal('show')
	$('.alert').hide()
}

function confirmOldPass(){
	var p1 = $('#inputPassword').val()
	var p2 = $('#inputPassword2').val()
	var matching = true
	var valid = true
	if(p1!=p2){
		matching = false
	}
	if(!matching){
		$('.alert').html(' * Password tidak sama!')
		$('.alert').show()
		return
	}
	//alert(p1+" "+$('#pass').val())
	//sendPassword()
	var pp = $('#pass').val()
	if(p1!=pp){
		$('.alert').html(' * Password bukan password anda saat ini!')
		$('.alert').show()
	}else{
		$('#modal').modal('hide')
		setTimeout(function(){f_baruPass()}, 500)
	}
}

function togglePassword(no,t){
	t.classList.toggle('fa-eye')
	t.classList.toggle('fa-eye-slash')
	var change = "inputPassword"
	if(no>1){
		change += no
	}
	if($('#'+change).attr("type")=="password"){
		$('#'+change).attr("type","text")
	}else{
		$('#'+change).attr("type","password")
	}
}

function f_baruPass(){
	var str = '<form>'
 	str += '<div class="form-group">'
 	str += '<div class="alert alert-danger form-group" role="alert"></div>'
 	str += '<label class="form-text">Masukkan password baru.</label>'
    str += '<label>Password</label>'
    str += '<div class="input-group" id="show_hide_password">'
    str += '<input class="form-control border-right-0" type="password" id="inputPassword">'
    str += '<div class="input-group-text bg-white border-left-0">'
    str += '<i class="fa fa-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(1,this)"></i>'
    str += '</div>'
    str += '</div>'
    str += '<br>'
    str += '<label>Masukkan Ulang Password</label>'
    str += '<div class="input-group" id="show_hide_password2">'
    str += '<input class="form-control border-right-0" type="password" id="inputPassword2">'
    str += '<div class="input-group-text bg-white border-left-0">'
    str += '<i class="fa fa-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(2,this)"></i>'
    str += '</div>'
    str += '</div>'
    str += '<br>'
	str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="confirmNewPass()">Ubah Password</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Ubah Password')
	$('.modal-footer').hide()
	$('.modal-body').html(str)
	$('#modal').modal('show')
	$('.alert').hide()
}

function confirmNewPass(){
	var p1 = $('#inputPassword').val()
	var p2 = $('#inputPassword2').val()
	var matching = true
	var valid = true
	if(p1!=p2){
		matching = false
	}
	if(p1.length<8){
		valid = false
	}
	if(!p1.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)){
		valid = false
	}
	if(!p1.match(/([0-9])/)){
		valid = false
	}
	if(!p1.match(/([!,%,&,@,#,$,^,*,?,_,~])/)){
		valid = false
	}
	if(!matching){
		$('.alert').html(' * Password tidak sama!')
		$('.alert').show()
		return
	}
	if(!valid){
		$('.alert').html('* Password tidak sesuai standar. Password harus terdiri dari 8 karakter dengan minimal 1 huruf kapital, 1 huruf kecil, 1 angka, dan satu karakter spesial.')
		$('.alert').show()
		return
	}
	sendNewPass(p1)
}

function sendNewPass(pass){
	var pw = '{'
	pw += '"id":'+uId+','
	pw += '"password":"'+pass+'"'
	pw += '}'
	$.ajax({
		url:"http://localhost:8888/user/password",
		type: "put",
		contentType:"application/json",
		data: pw,
		success: function(){
			$('#modal').modal('hide')
			getData(uId)
			setTimeout(function(){logoutModalP()}, 500)
		}
	})
}

function logoutModalP(){
	var str = '<p>Password berhasil diubah. Anda akan logout dalam 3 detik. Silakan masuk kembali menggunakan Password yang baru.</p>'
	$('.modal-title').html('Ubah Password')
	$('.modal-body').html(str)
	$('#modal').modal('toggle')
	setTimeout(function(){logout()}, 3000)
}