var userId = 1;

$(function() {
	fetchData()
})


function fetchData() {
	$.ajax({
		url: 'http://localhost:8888/api/bloodgroup',
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = ""
			for (var i = 0; i < data.length; i++) {
				str += "<tr>"
				str += "<td style='text-align:center;'>" + data[i].code + "</td>"
				if (data[i].description == null) {
					str += "<td>-</td>"
				} else {
					str += "<td>" + data[i].description + "</td>"
				}
				str += "<td style='text-align:center;'><button class='btn btn-warning' value='" + data[i].id + "' onclick='modalEdit(this.value)'><img src='/svg/pencil-fill.svg'></button></td>"
				str += "<td style='text-align:center;'><button class='btn btn-danger' value='" + data[i].id + "' onclick='modalDelete(this.value)'><img src='/svg/trash-fill.svg'> </button></td>"
				str += "</tr>"
			}

			$('#isidata').html(str)
		}
	})
}

function modalNew() {
	//console.log("test")

	var str = "<form>"
	str += '<div class="alert alert-danger form-group" role="alert">'
	str += 'Masukkan Kode Terlebih Dahulu!!'
	str += '</div>'
	str += "<div class='form-group'>"
	str += "<label>Kode*</label>"
	str += "<input type='text' class='form-control' id='code'>"
	str += "</div>"
	str += "<div class='form-group'>"
	str += "<label>Deskripsi</label>"
	str += "<textarea class='form-control' id='description'></textarea>"
	str += "</div>"
	str += "</form>"

	$('.modal-title').html("Tambah Golongan Darah")
	$('.modal-body').html(str)
	$('#btnSubmit').html("Simpan")
	$('#btnSubmit').off('click').on('click', addBlood)
	$('.alert').hide()
	$('#modal').modal('toggle')
}


function addBlood() {
	//console.log("test")

	if ($('#code').val() == "") {
		$('.alert').show()
	} else {
		var formdata = '{'
		formdata += '"code":"' + $('#code').val() + '",'
		if ($('#description').val() == "") {
			formdata += '"description": null'
		} else {
			formdata += '"description": "' + $('#description').val() + '"'
		}
		formdata += '}'

		console.log(formdata)

		$.ajax({
			url: 'http://localhost:8888/api/blood/add',
			type: 'post',
			contentType: 'application/json',
			data: formdata,
			success: function(resAdd) {
				if (resAdd == "Data Sudah Ada") {
					$('.alert').html("Data Sudah Ada")
					$('.alert').show()
				} else {
					fetchData()
					$('#modal').modal('toggle')
				}

			}
		})
	}
}


function modalEdit(id) {
	//console.log(id)

	$.ajax({
		url: 'http://localhost:8888/api/get/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = "<form>"
			str += '<div class="alert alert-danger form-group" role="alert">'
			str += 'Masukkan Kode Terlebih Dahulu!!'
			str += '</div>'
			str += "<div class='form-group'>"
			str += "<label>Kode*</label>"
			str += "<input type='text' class='form-control' id='code' value='" + data.code + "'>"
			str += "</div>"
			str += "<div class='form-group'>"
			str += "<label>Deskripsi</label>"
			str += "<textarea class='form-control' id='description'></textarea>"
			str += "</div>"
			str += "</form>"

			//console.log(data.description)

			$('.modal-title').html("Ubah Golongan Darah")
			$('.modal-body').html(str)
			$('#btnSubmit').html("Simpan")
			$('#btnSubmit').off('click').on('click', function() { editBlood(id) })
			$('.alert').hide()
			$('#modal').modal('toggle')
			if (data.description == "") {
				$('#description').val("-")
			} else {
				$('#description').val(data.description)
			}

		}
	})

}


function editBlood(id) {
	//console.log(id)

	if ($('#code').val() == "") {
		$('.alert').show()
	} else {
		var formdata = '{'
		formdata += '"id": ' + id + ','
		formdata += '"code":"' + $('#code').val() + '",'
		if ($('#description').val() == "") {
			formdata += '"description": null'
		} else {
			formdata += '"description": "' + $('#description').val() + '"'
		}
		formdata += '}'

		console.log(formdata)

		$.ajax({
			url: 'http://localhost:8888/api/blood/edit/' + userId,
			type: 'put',
			contentType: 'application/json',
			data: formdata,
			success: function(resEdit) {
				if (resEdit == "Data Sudah Ada") {
					$('.alert').html("Data Sudah Ada")
					$('.alert').show()
				} else {
					fetchData()
					$('#modal').modal('toggle')
				}

			}
		})
	}
}


function modalDelete(id) {

	//console.log(id)
	$.ajax({
		url: 'http://localhost:8888/api/get/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = '<center><h5>Apakah anda yakin ingin mengahapus golongan darah ' + data.code + '</h5></center>'

			$('.modal-title').html("Hapus Golongan Darah")
			$('.modal-body').html(str)
			$('#btnCancel').html("Tidak")
			$('#btnSubmit').html("Ya")
			$('#btnSubmit').off('click').on('click', function() { deleteBlood(id) })
			$('.alert').hide()
			$('#modal').modal('toggle')
		}
	})
}

function deleteBlood(id) {
	$.ajax({
		url: 'http://localhost:8888/api/blood/delete/' + userId + '/' + id,
		type: 'put',
		contentType: 'application/json',
		success: function() {
			fetchData()
			$('#modal').modal('toggle')
		}
	})
}


function searchBlood(value) {
	$.ajax({
		url: 'http://localhost:8888/api/blood/search?keyword=' + value,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = ""
			for (var i = 0; i < data.length; i++) {
				str += "<tr>"
				str += "<td style='text-align:center;'>" + data[i].code + "</td>"
				if (data[i].description == null) {
					str += "<td>-</td>"
				} else {
					str += "<td>" + data[i].description + "</td>"
				}
				str += "<td style='text-align:center;'><button class='btn btn-warning' value='" + data[i].id + "' onclick='modalEdit(this.value)'><img src='/svg/pencil-fill.svg'></button></td>"
				str += "<td style='text-align:center;'><button class='btn btn-danger' value='" + data[i].id + "' onclick='modalDelete(this.value)'><img src='/svg/trash-fill.svg'> </button></td>"
				str += "</tr>"
			}

			$('#isidata').html(str)
		}
	})
}