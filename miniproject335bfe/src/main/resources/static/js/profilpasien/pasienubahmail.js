var Timing = false

function f_ubahMail(){
	var str = '<form>'
 	str += '<div class="form-group">'
 	str += '<div class="alert alert-danger form-group" role="alert">'
	str += '</div>'
 	str += '<label class="form-text">Masukkan email anda yang baru.</label>'
    str += '<label>Email*</label>'
    str += '<input type="email" class="form-control" id="inputEmail">'
    str += '<small id="emailHelp" class="form-text text-danger"></small>'
    str += '<br>'
    str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="checkMail()">Ubah Email</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	
	$('.modal-title').html('Ubah E-mail')
	$('.modal-body').html(str)
	$('.modal-footer').hide()
	$('#btnSubmit').html('Ubah Email')
	$('#modal').modal('toggle')
	$('.alert').hide()

	//biodata(1)
}

function checkMail(){
	var email = $('#inputEmail').val()
	var fm = '{"email":"'+email+'"}'
	//console.log(email+" "+fm)
	$.ajax({
		url:"http://localhost:8888/user/check/email",
		type:"put",
		contentType:"application/json",
		data:fm,
		success:function(data){
			//alert(data)
			if(data=="not exist"){
				$('#modal').modal('hide')
				sendOTPM(email)
			}else if(data=="invalid"){
				$('.alert').html(' * Email yang dimasukkan tidak valid!')
				$('.alert').show()
			}else if(data=="exist"){
				$('.alert').html(' * Email yang dimasukkan telah memiliki akun!')
				$('.alert').show()
			}
			
		}
	})
}

function sendOTPM(email){
	Timing = true
	str = '<button type="button" class="btn btn-primary" disabled>Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
	var tForm = '{'
	tForm += '"id":'+uId+','
	tForm += '"email":"'+email+'"'
	tForm += '}'
	$.ajax({
		url:"http://localhost:8888/token/create/3",
		type:"post",
		contentType:"application/json",
		data:tForm,
		success:function(){
		}
	}).then(function(){
		otpm(email)
		timerm(180, email)
	})
}

function otpm(email){
	var str = '<form>'
 	str += '<div class="form-group">'
 	str += '<div class="alert alert-danger form-group" role="alert"></div>'
 	str += '<label class="form-text">Masukkan kode OTP yang telah dikirimkan ke email anda.</label>'
    str += '<label>Kode OTP*</label>'
    str += '<input type="text" class="form-control" id="inputOtp">'
    str += '<br>'
    str += '<div class="text-center" id="timedButton">'
	str += '</div>'
	str += '<br>'
	str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="confirmOTPM(\''+email+'\')">Konfirmasi OTP</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Ubah E-mail')
	$('.modal-body').html(str)
	$('#modal').modal('show')
	$('.alert').hide()
	//sendOTP(id)
	//$("#btnSubmit").off("click").on("click",checkEmail);
}

function timerm(time,email){
	//alert("Test")
	var m = Math.floor(time/60)
	var s = time%60
	
	if(m<10){
		m = '0' + m
	}
	if(s<10){
		s = '0' + s
	}
	str = "<p>Kirim ulang kode OTP dalam "+m+":"+s+"</p>"
	$("#timedButton").html(str);
	nt = time-1
	if(nt>=0 && Timing){
		setTimeout(function(){timerm(nt,email)}, 1000)
		return;
	}
	if(nt<0){
		putButton(email);
	}
}

function putButton(email){
	str = '<button type="button" class="btn btn-primary" onclick="sendOTPM(\''+email+'\')">Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
}

function confirmOTPM(email){
	var cToken = $('#inputOtp').val()
	if(cToken==""){
		cToken=0
	}
	$.ajax({
		url:"http://localhost:8888/token/check/"+uId+"/"+cToken,
		type: "get",
		contentType:"application/json",
		success: function(data){
			if(data=="Token Ditemukan!"){
				//console.log(id)
				Timing = false
				setTimeout(function(){changeMail(email)}, 500)
			}else if(data=="Tidak ada token!"){
				$('.alert').html(' * Token tidak ditemukan!')
				$('.alert').show()
			}else if(data=="Token telah Kadaluarsa!"){
				$('.alert').html(' * Kode OTP kadaluarsa, silahkan kirim ulang OTP.')
				$('.alert').show()
			}
		}
	})
}

function changeMail(email){
	var fm = '{"id":'+uId+',"email":"'+email+'"}'
	$.ajax({
		url:"http://localhost:8888/user/email",
		type:"put",
		contentType:"application/json",
		data:fm,
		success:function(){
			getData(uId)
			$('#modal').modal('hide')
			setTimeout(function(){logoutModalE()}, 500)
		}
	})
}

function logoutModalE(){
	var str = '<p>Ubah E-mail berhasil. Anda akan logout dalam 3 detik. Silakan masuk kembali menggunakan e-mail Anda yang baru.</p>'
	$('.modal-title').html('Ubah E-mail')
	$('.modal-body').html(str)
	$('#modal').modal('toggle')
	setTimeout(function(){logout()}, 3000)
}