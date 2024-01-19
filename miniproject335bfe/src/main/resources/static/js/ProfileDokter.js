var userId = localStorage.getItem("id")
var biodataId
var doctorId
//var tes = null

$(function() {
	$.ajax({
		url: 'http://localhost:8888/user/' + userId,
		typr: 'get',
		contenttype: 'application/json',
		success: function(user) {
			biodataId = user.biodataId
			profileLayout()
		}
	})
	
	//console.log(biodataId)
})


function profileLayout() {
	$.ajax({
		url: 'http://localhost:8888/doctor/' + biodataId,
		type: 'get',
		contenttype: 'application/json',
		success: function(data) {
			//console.log(data)
			$('#nama').html(data.mBiodata.fullname)
			doctorId = data.id

			$.ajax({
				url: 'http://localhost:8888/chat/' + doctorId,
				type: 'get',
				contenttype: 'application/json',
				success: function(chat) {
					if (chat != 0) {
						var chat = '<p style="font-size: medium;" class="mr-3 text-primary" id="chat"><b>' + chat + '</b></p>'
						$('#jumlahChat').html(chat)
					}

				}
			})

			$.ajax({
				url: 'http://localhost:8888/appointment/' + doctorId,
				type: 'get',
				contenttype: 'application/json',
				success: function(janji) {
					if (janji != 0) {
						var janji = '<p style="font-size: medium;" class="mr-3 text-primary" id="janji"><b>' + janji + '</b></p>'
						$('#jumlahJanji').html(janji)
					}


				}
			})

			$.ajax({
				url: 'http://localhost:8888/biodata/' + biodataId,
				type: 'get',
				contentType: 'application/json',
				success: function(biodata) {
					if (biodata.imagePath == null) {
						$('#imgBiodata').html('<img src="/svg/person-circle.svg" alt="avatar" class="rounded-circle img-fluid" style="width: 150px;">')
					} else {
						$('#imgBiodata').html('<img src="' + biodata.imagePath + '" alt="avatar" class="rounded-circle img-fluid" style="width: 150px;">')
					}
				}
			})

			$.ajax({
				url: 'http://localhost:8888/current/' + doctorId,
				type: 'get',
				contentType: 'application/json',
				success: function(special) {
					if (special != "") {
						$('#judulSpecial').html(special.mspecialization.name)
					}

				}
			})

			$.ajax({
				url: 'http://localhost:8888/treatment/' + doctorId,
				type: 'get',
				contentType: 'application/json',
				success: function(treatment) {
					var str = ""
					for (var i = 0; i < treatment.length; i++) {
						str += '<li>' + treatment[i].name + '</li>'
					}
					$('#tindakan').html(str)
				}
			})

			$.ajax({
				url: 'http://localhost:8888/office/' + doctorId,
				type: 'get',
				contentType: 'application/json',
				success: function(office) {

					//console.log(office)
					var str = ''
					for (var i = 0; i < office.length; i++) {

						var start = office[i].startDate + ""
						var end = office[i].endDate + ""

						//console.log(start)
						//console.log(end)

						start = start.substring(0, 4)
						end = end.substring(0, 4)



						str += '<p class="ml-4" style="font-size: medium;" id="riwayat">' + office[i].mMedicalFacility.name + ', ' + office[i].mMedicalFacility.mLocation.name + '</p>'
						str += '<div class="row ml-1">'
						str += '<div class="col-sm-6 d-flex justify-content-left">'
						str += '<p class="ml-4" style="font-size: small;">' + office[i].specialization + '</p>'
						str += '</div>'
						str += '<div class="col-sm-6 d-flex justify-content-end pr-5" >'
						if (office[i].endDate == null || office[i].endDate == "") {
							str += '<p style="font-size: small;">' + start + '-sekarang</p>'
						} else {
							str += '<p style="font-size: small;">' + start + '-' + end + '</p>'

						}

						//console.log(end)
						//console.log(office[i].endData)

						str += '</div>'
						str += '</div>'
					}
					$('#office').html(str)


				}
			})

			$.ajax({
				url: 'http://localhost:8888/education/' + doctorId,
				type: 'get',
				contentType: 'application/json',
				success: function(education) {
					var str = ''
					for (var i = 0; i < education.length; i++) {

						str += '<p class="ml-4" style="font-size: medium;" id="riwayat">' + education[i].institutionName + '</p>'
						str += '<div class="row ml-1">'
						str += '<div class="col-sm-8 d-flex justify-content-left">'
						str += '<p class="ml-4" style="font-size: small;">' + education[i].major + '</p>'
						str += '</div>'
						str += '<div class="col-sm-4 d-flex justify-content-end pr-5" >'
						if (education[i].endYear == "" || education[i].endYear == null) {
							str += '<p style="font-size: small;">Sekarang</p>'
						} else {
							str += '<p style="font-size: small;">' + education[i].endYear + '</p>'
						}

						//console.log(education[i].endYear)

						str += '</div>'
						str += '</div>'
					}
					$('#education').html(str)
				}
			})
		}
	})
}


function special() {
	//console.log("halo")

	$.ajax({
		url: 'http://localhost:8888/current/' + doctorId,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {

			//console.log(data)

			if (data == "") {
				var str = '<h2>Anda Belum Menambahkan Spesialisasi</h2>'
				str += '<div class="d-flex justify-content-end container">'
				str += '<button class="btn btn-link" onclick="modalAdd()"><img src="/svg/plus-square-fill.svg" width="50px" class="mt-4"></button>'
				str += '</div>	'
			} else {
				var str = '<h2>' + data.mspecialization.name + '</h2>'
				str += '<div class="d-flex justify-content-end container">'
				str += '<button class="btn btn-link" onclick="modalEdit()"><img src="/svg/pencil-square.svg" width="50px" class="mt-4"></button>'
				str += '</div>	'
			}

			$.ajax({
				url: 'http://localhost:8888/current/' + doctorId,
				type: 'get',
				contentType: 'application/json',
				success: function(special) {
					if (special != "") {
						$('#judulSpecial').html(special.mspecialization.name)
					}

				}
			})

			$('#special').html(str)
			//console.log(str)
		}
	})
}


function modalAdd() {
	//console.log("add")	
	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis',
		type: 'get',
		contentType: 'applicationa/json',
		success: function(data) {
			var str = '<form>'
			str += '<div class="form-group">'
			str += '<div class="alert alert-danger form-group" role="alert">'
			str += ' Pilih Option Terlebih Dahulu!!'
			str += '</div>'
			str += '<label>Spesialisasi</label>'
			str += '<select id="optionSpecial" class="form-control">'
			str += '<option>--Pilih--</option>'
			for (var i = 0; i < data.length; i++) {
				str += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
			}
			str += '</select>'
			str += '</div>'
			str += '</form>'

			$('.modal-title').html("Pilih Spesialisasi Anda")
			$('.modal-body').html(str)
			$('#btnSubmit').html("Simpan")
			$('#btnSubmit').off('click').on('click', addSpecial)
			$('.alert').hide()
			$('.modal-footer').show()
			$('#modal').modal('toggle')
		}
	})


}

function addSpecial() {
	//console.log($('#optionSpecial').val())

	if ($('#optionSpecial').val() == "--Pilih--") {
		$('.alert').show()
	} else {
		var formdata = '{'
		formdata += '"doctorId": ' + doctorId + ','
		formdata += '"specializationId": ' + $('#optionSpecial').val()
		formdata += '}'

		$.ajax({
			url: 'http://localhost:8888/current/add',
			type: 'post',
			contentType: 'application/json',
			data: formdata,
			success: function() {
				$('.modal-title').html("Sukses")
				$('.modal-body').html("<h3><center>Spesialisasi Berhasil Ditambahkan</center></h3>")
				$('.modal-footer').hide()
				special()
			}
		})
	}
}

function modalEdit() {
	//console.log("edit")

	$.ajax({
		url: 'http://localhost:8888/current/' + doctorId,
		type: 'get',
		contentType: 'application/json',
		success: function(dataDoc) {
			$.ajax({
				url: 'http://localhost:8888/dokter/spesialis',
				type: 'get',
				contentType: 'applicationa/json',
				success: function(data) {
					var str = '<form>'
					str += '<div class="form-group">'
					str += '<div class="alert alert-danger form-group" role="alert">'
					str += ' Pilih Option Terlebih Dahulu!!'
					str += '</div>'
					str += '<label>Spesialisasi</label>'
					str += '<select id="optionSpecial" class="form-control">'
					str += '<option>--Pilih--</option>'
					for (var i = 0; i < data.length; i++) {
						str += '<option value="' + data[i].id + '">' + data[i].name + '</option>'
					}
					str += '</select>'
					str += '</div>'
					str += '</form>'

					$('.modal-title').html("Pilih Spesialisasi Anda")
					$('.modal-body').html(str)
					$('#btnSubmit').html("Simpan")
					$('#btnSubmit').off('click').on('click', editSpecial)
					$('.alert').hide()
					$('.modal-footer').show()
					$('#modal').modal('toggle')
					$('#optionSpecial').val(dataDoc.specializationId)
				}
			})
		}
	})
}


function editSpecial() {
	//console.log("halo")

	if ($('#optionSpecial').val() == "--Pilih--") {
		$('.alert').show()
	} else {
		var formdata = '{'
		formdata += '"doctorId": ' + doctorId + ','
		formdata += '"specializationId": ' + $('#optionSpecial').val()
		formdata += '}'

		$.ajax({
			url: 'http://localhost:8888/current/edit',
			type: 'put',
			contentType: 'application/json',
			data: formdata,
			success: function() {
				//$('#modal').modal('toggle')

				$('.modal-title').html("Sukses")
				$('.modal-body').html("<h3><center>Spesialisasi Berhasil diubah</center></h3>")
				$('.modal-footer').hide()
				//$('#modal').modal('toggle')
				special()
			}
		})
	}
}


function modalImg() {
	var str = '<form enctype="multipart/form-data">'
	str += '<div class="form-group">'
	str += '<div class="alert alert-danger form-group" role="alert">'
	str += 'Masukkan Image Terlebih Dahulu!!'
	str += '</div>'
	str += '<label for="exampleFormControlFile1">File type (JPG, PNG, SVG) max 1 mb</label>'
	str += '<input type="file" class="form-control-file" id="inputImg">'
	str += '</div>'
	str += '</form>'

	$('.modal-title').html("Change Profile Picture")
	$('.modal-body').html(str)
	$('#btnSubmit').html("Simpan")
	$('#btnSubmit').off('click').on('click', editImg)
	$('.alert').hide()
	$('#modal').modal('toggle')
	$('.modal-footer').show()
}


function editImg() {
	//console.log("tes")


	// var fileInput = document.getElementById('inputImg').files[0]

	//console.log(fileInput)

	// form = new FormData(document.getElementById("inputImg"));
	/*$.ajax({
		type: "post",
		enctype: "multipart/form-data",
		url: "http://localhost:8888/biodata/img/1",
		data: fileInput.files,
		processData: false, 
		contentType: false,
		cache: false,
		success: function (data) {
			alert("hi stuff worked");
		},
		error: function (e) {
			alert(e)
			console.log(e)
		}
	});*/

	/*$.ajax({
		url: 'http://localhost:8888/biodata/img/1'+biodataId,
		type: 'file',
		data: formData,
		success: function() {
			profileLayout()
		}
	})*/


	//formData.append("img", fileInput.name)



	/*
	for (var key of formData.entries()) {
		console.log(key[0] + ', ' + key[1]);
	}*/

	//formData = {
	//'img' : fileInput
	//}

	//console.log(formData)

	//console.log($('#inputImg').val())
	var size = document.getElementById('inputImg').files.item(0).size;

	if ($('#inputImg').val() == "") {
		$('.alert').show()
	} else if (size > 1048576) {
		$('.alert').html("Ukuran File Terlalu Besar!")
		$('.alert').show()
	} else {

		var formData = new FormData()

		var fileInput = $('#inputImg')[0].files[0]

		console.log(size)

		formData.append('img', fileInput)

		$.ajax({
			type: 'put',
			processData: false,
			contentType: false,
			cache: false,
			data: formData,
			enctype: 'multipart/form-data',
			url: 'http://localhost:8888/biodata/img/' + biodataId,
			success: function(response) {
				console.log(response)
				if (response == "Error") {
					$('.alert').html("Tipe File Tidak Sesuai")
					$('.alert').show()
				} else {
					profileLayout()
					$('#modal').modal('toggle')
				}

			}
		})
	}
}

function getTreatment() {
	//console.log("halo")

	$.ajax({
		url: 'http://localhost:8888/treatment/' + doctorId,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {

			//console.log(data)
			var str = ""
			if (data == "") {
				str += '<h2>Anda Belum Menambahkan Tindakan</h2>'
				str += '<div class="d-flex justify-content-end container">'
				str += '<button class="btn btn-link" onclick="modalAddTreatment()"><img src="/svg/plus-square-fill.svg" width="50px" class="mt-4"></button>'
				str += '</div>'
			} else {
				for (var i = 0; i < data.length; i++) {
					if (data[i] != "") {
						str += '<label class="mr-1 ml-1" style=" text-align:center; background: #4e73df; color:white; width: 140px;  height: 25px; border-radius:10%/50% ">' + data[i].name + '<button class="close mr-2" type="button" value="'+data[i].id+'" onclick="modalDeleteTreatment(this.value)"><p aria-hidden="true" style="color:white;">×</p></button> </label>'
					}
				}

				str += '<div class="d-flex justify-content-end container">'
				str += '<button class="btn btn-link" onclick="modalAddTreatment()"><img src="/svg/plus-square-fill.svg" width="50px" class="mt-4"></button>'
				str += '</div>	'
			}

			var treatment = ""
			for (var i = 0; i < data.length; i++) {
				if (data[i] != "") {
					treatment += '<li>' + data[i].name + '</li>'
				}
			}
			$('#tindakan').html(treatment)
			$('#special').html(str)
			//console.log(str)
		}
	})
}

function modalAddTreatment() {
	//console.log("add")	

	var str = '<form class="">'
	str += '<center><div class="form">'
	str += '<div class="alert alert-danger form-group" role="alert"></div>'
	str += '<label>Tindakan *</label>'
	str += '</div></center>'
	str += '<center><input class="border-primary" placeholder="Masukkan Tindakan..." type="text" id="namaTindakan"><center>'
	str += '</form>'

	$('.modal-title').html("Tambahkan Tindakan")
	$('.modal-body').html(str)
	$('#btnSubmit').html("Simpan")
	$('.alert').hide()
	$('#btnSubmit').off('click').on('click', addTindakan)
	$('.modal-footer').show()
	$('#modal').modal('toggle')



}

function addTindakan() {
	//console.log($('#optionSpecial').val())
	var namaTindakan = $('#namaTindakan').val()
	var formdata = '{'
	formdata += '"doctorId": ' + doctorId + ','
	formdata += '"name": "' + namaTindakan + '"'
	formdata += '}'

	$.ajax({
		url: 'http://localhost:8888/treatment/addtreatment',
		type: 'post',
		contentType: 'application/json',
		data: formdata,
		success: function(response) {
			if (namaTindakan == "") {
				//alert("Tolong Isi Tindakan")
				$('.alert').html('Tolong Isi Tindakan')
				$('.alert').show()
			} else if (response == "Data Sudah Ada") {
				//alert("Tindakan Sudah Ada")
				$('.alert').html('Data Sudah Ada')
				$('.alert').show()
			} else {
				$('.modal-title').html("Sukses")
				$('.modal-body').html("<h3><center>Tindakan Berhasil Ditambahkan</center></h3>")
				$('.modal-footer').hide()
				getTreatment()
			}
		}
	})

}

function modalDeleteTreatment(Id) {
	//console.log("add")	
	$.ajax({
		url: 'http://localhost:8888/treatment/get/' + Id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = '<form class="">'
			str += '<center><div class="form">'
			str += '<h3>Anda Setuju Untuk Menghapus Tindakan '+data.name+'</h3>'
			str += '</div></center>'
			str += '</form>'

			$('.modal-title').html("Hapus Tindakan")
			$('.modal-body').html(str)
			$('#btnSubmit').html("Hapus")
			$('#btnSubmit').off('click').on('click', function() { deleteTindakan(data.id) })
			$('.modal-footer').show()
			$('#modal').modal('toggle')
			}
		})
}

function deleteTindakan(id) {
	//console.log($('#optionSpecial').val())
	$.ajax({
		url: 'http://localhost:8888/treatment/deletetreatment/' + id,
		type: 'put',
		contentType: 'application/json',
		success: function() {
			$('.modal-title').html("Sukses")
			$('.modal-body').html("<h3><center>Tindakan Berhasil Dihapus</center></h3>")
			$('.modal-footer').hide()
			getTreatment()
		}
	})

}

