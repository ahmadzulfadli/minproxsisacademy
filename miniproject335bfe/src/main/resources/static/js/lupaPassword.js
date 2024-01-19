function lupaPassword() {
	//alert("lupa Pasword")

	var str = '<form>'
	str += '<div class="form-group">'
	str += '<div class="alert alert-danger form-group" role="alert">'
	str += '</div>'
	str += '<label class="form-text">Masukkan email anda. Kami akan melakukan pengecekan.</label>'
	str += '<label>Email*</label>'
	str += '<input type="email" class="form-control" id="inputEmail">'
	str += '<br>'
	str += '<div class="text-center">'
	str += '<button type="button" class="btn btn-primary" onclick="checkEmailLupa()">Kirim OTP</button>'
	str += '</div>'
	str += '</div>'
	str += '</form>'

	$('.modal-title').html('Lupa Password')
	$('.modal-body').html(str)
	$('.modal-footer').hide()
	$('.alert').hide()
	$('#btnSubmit').html('Kirim OTP')
	$('#modal').modal('toggle')
}


var Timing = false

function checkEmailLupa() {
	var cm = '{'
	cm += '"email" : "' + $('#inputEmail').val() + '"'
	cm += '}'

	$.ajax({
		url: 'http://localhost:8888/user/edit',
		type: 'put',
		contentType: 'application/json',
		data: cm,
		success: function(data) {
			if (data.status == "Edit User Success") {
				$('#modal').modal('hide')
				sendOTPLupa(data.id, data.email)
			} else if (data.status == "Invalid Email") {
				$('.alert').html(' * Email yang dimasukkan tidak valid!')
				$('.alert').show()
			} else if (data.status == "Email Not Found") {
				$('.alert').html(' * Email yang dimasukkan tidak memiliki akun!')
				$('.alert').show()
			}
		}
	})
}


function otpLupa(id, email) {
	var str = '<form>'
	str += '<div class="form-group">'
	str += '<div id="alertOTP"></div>'
	str += '<label class="form-text">Masukkan kode OTP yang telah dikirimkan ke email anda.</label>'
	str += '<label>Kode OTP*</label>'
	str += '<input type="text" class="form-control" id="inputOtp">'
	str += '<br>'
	str += '<div class="text-center" id="timedButton">'
	str += '</div>'
	str += '<br>'
	str += '<div class="text-center">'
	str += '<button type="button" class="btn btn-primary" onclick="confirmOTPLupa(' + id + ',\'' + email + '\')">Konfirmasi OTP</button>'
	str += '</div>'
	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Konfirmasi OTP')
	$('.modal-body').html(str)
	$('#modal').modal('show')
}


function sendOTPLupa(id, email) {
	Timing = true
	str = '<button type="button" class="btn btn-primary" disabled>Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
	var tForm = '{'
	tForm += '"id":' + id + ','
	tForm += '"email":"' + email + '"'
	tForm += '}'
	$.ajax({
		url: "http://localhost:8888/token/create/2",
		type: "post",
		contentType: "application/json",
		data: tForm,
		success: function() {
		}
	}).then(function() {
		otpLupa(id, email)
		timerLupa(180, id, email)
	})
}


function timerLupa(time, id, email) {
	var m = Math.floor(time / 60)
	var s = time % 60

	if (m < 10) {
		m = '0' + m
	}
	if (s < 10) {
		s = '0' + s
	}
	
	
	str = "<p>Kirim ulang kode OTP dalam " + m + ":" + s + "</p>"
	$("#timedButton").html(str);
	nt = time - 1
	if (nt >= 0 && Timing) {
		setTimeout(function() { timerLupa(nt, id, email) }, 1000)
		return;
	}
	if (nt < 0) {
		putButtonLupa(id, email);
	}
}


function putButtonLupa(id, email) {
	str = '<button type="button" class="btn btn-primary" onclick="sendOTPLupa(' + id + ',\'' + email + '\')">Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
}


function confirmOTPLupa(id, email) {
	var cToken = $('#inputOtp').val()
	if (cToken == "") {
		cToken = 0
	}
	
	$.ajax({
		url: "http://localhost:8888/token/check/" + id + "/" + cToken,
		type: "get",
		contentType: "application/json",
		success: function(data) {
			if (data == "Token Ditemukan!") {
				$('#modal').modal('hide')
				Timing = false
				setTimeout(function() { passwordLupa(id, email) }, 500)
			} else if (data == "Tidak ada token!") {

				var alert = '<div class="alert alert-danger form-group" role="alert">'
				alert += ' * Token tidak ditemukan!'
				alert += '</div>'
				$('#alertOTP').html(alert)
			} else if (data == "Token telah Kadaluarsa!") {

				var alert = '<div class="alert alert-danger form-group" role="alert">'
				alert += ' * Kode OTP kadaluarsa, silahkan kirim ulang OTP.'
				alert += '</div>'
				$('#alertOTP').html(alert)
			}
		}
	})
}


function passwordLupa(id, email) {
	var str = '<form>'
	str += '<div class="form-group">'
	str += '<div id="alertPassword"></div>'
	str += '<label class="form-text">Masukkan password baru untuk akun anda.</label>'
	str += '<label>Password</label>'
	str += '<div class="input-group" id="show_hide_password">'
	str += '<input class="form-control border-right-0" type="password" id="inputPassword">'
	str += '<div class="input-group-text bg-white border-left-0">'
	str += '<i class="bi bi-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(1,this)"></i>'
	str += '</div>'
	str += '</div>'
	str += '<br>'
	str += '<label>Masukkan Ulang Password</label>'
	str += '<div class="input-group" id="show_hide_password2">'
	str += '<input class="form-control border-right-0" type="password" id="inputPassword2">'
	str += '<div class="input-group-text bg-white border-left-0">'
	str += '<i class="bi bi-eye" style="cursor: pointer; margin-left: 0px; z-index: 100;" onclick="togglePassword(2,this)"></i>'
	str += '</div>'
	str += '</div>'
	str += '<br>'
	str += '<div class="text-center">'
	str += '<button type="button" class="btn btn-primary" onclick="confirmPasswordLupa(' + id + ',\'' + email + '\')">Set Password</button>'
	str += '</div>'
	str += '</div>'
	str += '</form>'
	$('.alert').hide()
	$('.modal-title').html('Lupa Password')
	$('.modal-body').html(str)
	$('#modal').modal('show')
}


function confirmPasswordLupa(id, email) {
	var p1 = $('#inputPassword').val()
	var p2 = $('#inputPassword2').val()
	var matching = true
	var valid = true
	if (p1 != p2) {
		matching = false
	}
	if (p1.length < 8) {
		valid = false
	}
	if (!p1.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
		valid = false
	}
	if (!p1.match(/([0-9])/)) {
		valid = false
	}
	if (!p1.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) {
		valid = false
	}
	if (!matching) {
		
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += ' * Password tidak sama'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	if (!valid) {
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += ' * Password tidak sesuai standar. Password harus terdiri dari minimal 8 karakter dengan minimal 1 huruf kapital, 1 huruf kecil, 1 angka, dan satu karakter spesial.'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	sendPasswordLupa(id, p1, email)
}


function togglePassword(no, t) {
	t.classList.toggle('bi-eye')
	t.classList.toggle('bi-eye-slash')
	var change = "inputPassword"
	if (no > 1) {
		change += no
	}
	if ($('#' + change).attr("type") == "password") {
		$('#' + change).attr("type", "text")
	} else {
		$('#' + change).attr("type", "password")
	}
}


function sendPasswordLupa(id, pass,email){
	console.log(id)
	
	var pw = '{'
	pw += '"id":'+id+','
	pw += '"password":"'+pass+'"'
	pw += '}'
	$.ajax({
		url:"http://localhost:8888/user/password",
		type: "put",
		contentType:"application/json",
		data: pw,
		success: function(){
			$('#modal').modal('hide')
		}
	})
}

