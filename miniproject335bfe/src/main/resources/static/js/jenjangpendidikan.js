$(function() {
	fetchData()
})

function fetchData() {
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan',
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			console.log(data)
			
			var str = ''
			for (var i = 0; i < data.length; i++) {
				str += '<tr>'
				str += '<td>' + data[i].name + '</td>'
				str += '<td>'
				str += '<button class="badge btn-warning text-white mr-2" value="' + data[i].id + '" onclick="f_edit(this.value)">Ubah</button>'
				str += '<button class="badge btn-danger" value="' + data[i].id + '" onclick="f_delete(this.value)">Hapus</button>'
				str += '</td>'
				str += '</tr>'
			}
			$('#isidata').html(str)
		}
	})
}
function openModal() {
	var str = '<form>'
	str += '<div class="form-group">'
	str += '<label for="name">Nama*</label>'
	str += '<input type="text" class="form-control" id="tambahname">'
	str += '</div>'
	str += '</form>'

	$('.modal-title').html('TAMBAH')
	$('.modal-body').html(str)
	$('#btnCancel').html('Batal')
	$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-primary').off('click').on('click',s_add)
	$('#modal').modal('show')
}

function s_add() {
	var name = $('#tambahname').val()
	if(name == "" ){
		alert("Masukkan nama terlebih dahulu")
	}else{
	var formdata = '{'
	formdata += '"name": "' + name + '"'
	formdata += '}'	
	}

	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/add',
		type: 'post',
		contentType: 'application/json',
		data: formdata,
		success: function(data) {
			console.log(data)
			if(data.status == "failed"){
				alert("Nama sudah terpakai")
			}else{
				$('#modal').modal('toggle')
				fetchData()				
			}

		}
	})
}

function f_edit(id) {
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/'+id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			console.log(data)
			var str = '<form>'
			str += '<div class="form-group">'
			str += '<label for="name">Nama*</label>'
			str += '<input type="text" class="form-control" id="editname" value="' + data.name + '">'
			str += '</div>'
			str += '</form>'

			$('.modal-title').html('UBAH')
			$('.modal-body').html(str)
			$('#btnCancel').html('Batal')
			$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-danger').off('click').on('click', function() { s_edit(data.id) })
			$('#modal').modal('show')
		}
	})
}

function s_edit(id) {
	var name = $('#editname').val()
	if(name == ""){
		alert("Masukkan nama terlebih dahulu")
	}else{
		var formdata = '{'
		formdata += '"id": ' + id + ','
		formdata += '"name": "' + name + '"'
		formdata += '}'
	}
	
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/edit',
		type: 'put',
		contentType: 'application/json',
		data: formdata,
		success: function(data) {
			if(data.status == "failed"){
				alert("Nama sudah terpakai")
			}else{
				$('#modal').modal('toggle')
				fetchData()				
			}
		}
	})
}

function f_delete(id) {
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = 'Anda akan menghapus jenjang pendidikan ' + data.name + '?'

			$('.modal-title').html('HAPUS !')
			$('.modal-body').html(str)
			$('#btnCancel').html('Tidak')
			$('#btnSubmit').html('Hapus').removeAttr('class').addClass('btn btn-danger').off('click').on('click', function() { s_delete(data.id) })
			$('#modal').modal('show')
		}
	})
}

function s_delete(id) {
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/delete/' + id,
		type: 'put',
		contentType: 'application/json',
		success: function() {
			$('#modal').modal('toggle')
			fetchData()
		}
	})
}

function searchJenjangPendidikan(value) {
	$.ajax({
		url: 'http://localhost:8888/api/jenjangpendidikan/search?keyword=' + value,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = ''
			for (var i = 0; i < data.length; i++) {
				str += '<tr>'
				str += '<td>' + data[i].name + '</td>'
				str += '<td>'
				str += '<button class="badge btn-warning text-white mr-2" value="' + data[i].id + '" onclick="f_edit(this.value)">Edit</button>'
				str += '<button class="badge btn-danger" value="' + data[i].id + '" onclick="f_delete(this.value)">Delete</button>'
				str += '</td>'
				str += '</tr>'
			}
			$('#isidata').html(str)
		}
	})
}
