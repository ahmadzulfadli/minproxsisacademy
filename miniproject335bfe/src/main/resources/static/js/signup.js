function register(){
	//alert("register")

	var str = '<form>'
 	str += '<div class="form-group">'
 	str += '<div class="alert alert-danger form-group" role="alert">'
	str += '</div>'
 	str += '<label class="form-text">Masukkan email anda. Kami akan melakukan pengecekan.</label>'
    str += '<label>Email*</label>'
    str += '<input type="email" class="form-control" id="inputEmail">'
    str += '<br>'
    str += '<div class="text-center">'
  	str += '<button type="button" class="btn btn-primary" onclick="checkEmail()">Kirim OTP</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	
	$('.modal-title').html('Daftar')
	$('.modal-body').html(str)
	$('.modal-footer').hide()
	$('#btnSubmit').html('Kirim OTP')
	$('.alert').hide()
	//$("#btnSubmit").off("click").on("click",checkEmail);
	//$('#btnCancel').hide()
	$('#modal').modal('toggle')

	//biodata(1)
}

var Timing = false

function checkEmail(){
	var em = '{'
	em += '"email":"'+$('#inputEmail').val()+'"'
	em +=  '}'
	$.ajax({
		url:"http://localhost:8888/user/create",
		type:"post",
		contentType:"application/json",
		data:em,
		success:function(data){
			//alert(data)
			if(data.status=="Add User Success"){
				$('#modal').modal('hide')
				sendOTP(data.id,data.email)
			}else if(data.status=="Invalid Email"){
				$('.alert').html(' * Email yang dimasukkan tidak valid!')
				$('.alert').show()
			}else if(data.status=="Email Exist"){
				$('.alert').html(' * Email yang dimasukkan telah memiliki akun!')
				$('.alert').show()
			}
			
		}
	})
}
function otp(id,email){
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
  	str += '<button type="button" class="btn btn-primary" onclick="confirmOTP('+id+',\''+email+'\')">Konfirmasi OTP</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Konfirmasi OTP')
	$('.modal-body').html(str)
	$('#modal').modal('show')
	//sendOTP(id)
	//$("#btnSubmit").off("click").on("click",checkEmail);
}

function sendOTP(id,email){
	Timing = true
	str = '<button type="button" class="btn btn-primary" disabled>Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
	var tForm = '{'
	tForm += '"id":'+id+','
	tForm += '"email":"'+email+'"'
	tForm += '}'
	$.ajax({
		url:"http://localhost:8888/token/create/1",
		type:"post",
		contentType:"application/json",
		data:tForm,
		success:function(){
			//console.log(id)
		}
	}).then(function(){
		//console.log(id)
		otp(id,email)
		timer(180,id,email)
	})
}

function timer(time,id,email){
	//alert("Test")
	var m = Math.floor(time/60)
	var s = time%60
	
	if(m<10){
		m = '0' + m
	}
	if(s<10){
		s = '0' + s
	}
	//alert("Test")
	//console.log($("#timedButton").html)
	str = "<p>Kirim ulang kode OTP dalam "+m+":"+s+"</p>"
	$("#timedButton").html(str);
	nt = time-1
	if(nt>=0 && Timing){
		setTimeout(function(){timer(nt,id,email)}, 1000)
		return;
	}
	if(nt<0){
		putButton(id,email);
	}
}

function putButton(id,email){
	str = '<button type="button" class="btn btn-primary" onclick="sendOTP('+id+',\''+email+'\')">Kirim Ulang OTP</button>'
	$("#timedButton").html(str);
}

function confirmOTP(id,email){
	var cToken = $('#inputOtp').val()
	if(cToken==""){
		cToken=0
	}
	//alert("confirmOTP")
	$.ajax({
		url:"http://localhost:8888/token/check/"+id+"/"+cToken,
		type: "get",
		contentType:"application/json",
		success: function(data){
			if(data=="Token Ditemukan!"){
				$('#modal').modal('hide')
				//console.log(id)
				Timing = false
				setTimeout(function(){password(id,email)}, 500)
			}else if(data=="Tidak ada token!"){
				var alert = '<div class="alert alert-danger form-group" role="alert">'
				alert += ' * Token tidak ditemukan!'
				alert += '</div>'
				$('#alertOTP').html(alert)
			}else if(data=="Token telah Kadaluarsa!"){
				var alert = '<div class="alert alert-danger form-group" role="alert">'
				alert += ' * Kode OTP kadaluarsa, silahkan kirim ulang OTP.'
				alert += '</div>'
				$('#alertOTP').html(alert)
			}
		}
	})
}

function password(id,email){
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
  	str += '<button type="button" class="btn btn-primary" onclick="confirmPassword('+id+',\''+email+'\')">Set Password</button>'
	str += '</div>'
  	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Set Password')
	$('.modal-body').html(str)
	$('#modal').modal('show')
}

function confirmPassword(id,email){
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
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += ' * Password tidak sama'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	if(!valid){
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += ' * Password tidak sesuai standar. Password harus terdiri dari minimal 8 karakter dengan minimal 1 huruf kapital, 1 huruf kecil, 1 angka, dan satu karakter spesial.'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	sendPassword(id, p1, email)
}

function togglePassword(no,t){
	t.classList.toggle('bi-eye')
	t.classList.toggle('bi-eye-slash')
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

function sendPassword(id, pass,email){
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
			setTimeout(function(){biodata(id,email)}, 500)
		}
	})
}

function biodata(id,email){
    $.ajax({
		url:"http://localhost:8888/role/showall",
		type:"get",
		contentType:"application/json",
		success:function(d){
			var str = '<form>'
		 	str += '<div class="form-group">'
		 	str += '<div id="alertPassword"></div>'
		    str += '<label>Nama Lengkap*</label>'
		    str += '<input type="text" class="form-control" id="inputNama">'
		    str += '<br>'
		    str += '<label>Nomor Handphone*</label>'
		    str += '<div class="input-group">'
		  	str += '<div class="input-group-prepend">'
		    str += '<span class="input-group-text" id="basic-addon1">+62</span>'
		  	str += '</div>'
		  	str += '<input type="number" class="form-control" id="inputPhone">'
			str += '</div>'
		    str += '<br>'
		    str += '<label>Daftar Sebagai*</label>'
		    str += '<select class="form-control" id="inputRole">'
		    for(var i=0;i<d.data.length;i++){
				str += '<option value="'+d.data[i].id+'">'+d.data[i].name+'</option>'
			}
		    str += '</select>'
		    str += '<br>'
			str += '<div class="text-center">'
		  	str += '<button type="button" class="btn btn-primary" onclick="sendBio('+id+',\''+email+'\')">Daftar</button>'
			str += '</div>'
		  	str += '</div>'
			str += '</form>'
			$('.modal-title').html('Daftar')
			$('.modal-body').html(str)
			$('#modal').modal('show')
		}
	})
  	//str += '<option>Small select</option>'
	
	
}

function sendBio(id,email){
	var checkNama = $('#inputNama').val()
	var checkForm = $('#inputPhone').val()
	var checkRole = $('#inputRole').val()
	if(checkNama==""){
		//$('#supHelp').html('Nama tidak ada')
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += 'Nama Tidak Ada'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	if(checkForm==""){
		//$('#supHelp').html('Phone tidak ada')
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += 'Nomor Handphone Tidak Ada'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}else{
		checkForm = "+62"+checkForm
	}
	if(checkRole==0 || checkRole=="0" || checkRole==null){
		//$('#supHelp').html('Role tidak ada')
		var alert = '<div class="alert alert-danger form-group" role="alert">'
		alert += 'Role Tidak Ada'
		alert += '</div>'
		$('#alertPassword').html(alert)
		return
	}
	
	upForm = '{'
	upForm += '"user":{'
	upForm += '"id":'+id+','
	upForm += '"email":"'+email+'",'
	upForm += '"roleId":'+checkRole
	upForm += '},'
	upForm += '"biodata":{'
	upForm += '"fullname":"'+checkNama+'",'
	upForm += '"mobilePhone":"'+checkForm+'"'
	upForm += '}'
	upForm += '}'
	$.ajax({
		url:"http://localhost:8888/user/signup",
		type: "put",
		contentType:"application/json",
		data: upForm,
		success: function(){
			$('#modal').modal('hide')
		}
	})
}