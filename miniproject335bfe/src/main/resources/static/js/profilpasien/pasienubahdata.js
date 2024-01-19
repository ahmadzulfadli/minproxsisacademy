function f_ubahData() {
	//console.log(bioId)
	var str = '<form>'
	str += '<div class="form-group">'
	str += '<div class="alert alert-danger form-group" role="alert"></div>'
	str += '<label>Nama Lengkap*</label>'
	str += '<input type="text" class="form-control" id="inputName" value="'+$('#nama').val()+'">'
	str += '<br>'
	str += '<label>Tanggal Lahir*</label>'
	str += '<input type="date" class="form-control" id="inputDate" value="'+$('#tgl').val()+'">'
	str += '<br>'
	str += '<label>Nomor Handphone*</label>'
	str += '<div class="input-group">'
	str += '<div class="input-group-prepend">'
	str += '<span class="input-group-text" id="basic-addon1">+62</span>'
	str += '</div>'
	var hp = parseInt($('#hp').val().substring(3))
	str += '<input type="number" class="form-control" id="inputPhone" value="'+hp+'">'
	str += '</div>'
	str += '<br>'
	str += '<div class="text-center">'
	str += '<button type="button" class="btn btn-primary" onclick="ubahData('+uId+','+bioId+')">Simpan</button>'
	str += '</div>'
	str += '</div>'
	str += '</form>'
	$('.modal-title').html('Data Pribadi')
	$('.modal-footer').hide()
	$('.modal-body').html(str)
	$('#modal').modal('show')
	$('.alert').hide()
	var today = new Date().toISOString().split('T')[0];
	$('#inputDate').attr('max',today)
}

function ubahData(id,bid){
	var n = $('#inputName').val()
	if(n==null || n==""){
		$('.alert').html(' * Nama kosong!')
		$('.alert').show()
		return
	}
	var p = $('#inputPhone').val()
	if(p==null || p==""){
		$('.alert').html(' * Nomor Telepon kosong!')
		$('.alert').show()
		return
	}
	var s = '{'
	s += '"createdOn":"'+$('#inputDate').val()+'",'
	s += '"fullname":"'+$('#inputName').val()+'",'
	s += '"mobilePhone":"'+'+62'+$('#inputPhone').val()+'",'
	s += '"id":'+bid
	s += '}'
	$.ajax({
		url:"http://localhost:8888/biodata/edit/"+id,
		type:"put",
		contentType:"application/json",
		data:s,
		success:function(){
			getData(id)
			$('#modal').modal('hide')
		}
	})
	
	/*
	console.log($('#inputDate').val())
	console.log($('#inputName').val())
	console.log($('#inputPhone').val())
	*/
}