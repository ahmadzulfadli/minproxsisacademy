var bioId = 1
var yourArray = []
var boolean = false

$(function() {
	fetchData(0, 5)

	localStorage.setItem("role", "admin")
	if (localStorage.getItem("role") != "admin") {
		$('#editfaskes').hide()
		$('#editTransaksi').hide()
		$('#editdokter').hide()
		$('#editpasien').hide()
	} else if (localStorage.getItem("role") != "pasien") {
		$('#riwayat').hide()
		$('#rencanadatang').hide()
		$('#obat').hide()
	}
})

function fetchData(page, per_page) {
	console.log(page, per_page)
	$.ajax({
		url: `http://localhost:8888/api/customermember/paging?page=${page}&per_page=${per_page}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datapasien) {
			var str = ''
			for (var i = 0; i < datapasien.data.length; i++) {
				var datedob = hitungUmur(datapasien.data[i].mcustomer.dob) + " tahun"
				//datedob = datedob.substring(0,10)			
				//console.log(datapasien)
				str += '<tr>'
				str += '<td>'
				str += '<div class="form-check d-flex justify-content-center" style="margin-top: 15%;">'
				str += '<input class="form-check-input" type="checkbox" name="listCheck" id="check' + datapasien.data[i].id + '" value=' + datapasien.data[i].id + ' onclick="check(this.value)">'
				str += '</div>'
				str += '</td>'
				str += '<td id="datapasien">'
				str += '<h6 class="my-0" style="font-size: 15px; color: #4E73DF;">' + datapasien.data[i].mcustomer.mbiodata.fullname + '</h6>'
				str += '<h6 class="my-0" style="font-size: 12px;">' + datapasien.data[i].mcustomerrelation.name + ', ' + datedob + '</h6>'
				str += '<h6 class="my-0" style="font-size: 10px;">9 Chat Dokter, 3 Janji Dokter</h6>'
				str += '</td>'
				str += '<td id="aksi">'
				str += '<div style="padding-left: 50%;">'
				str += '<button class="badge btn-warning text-white mr-2" value="' + datapasien.data[i].id + '" onclick="f_edit(this.value)">Edit</button>'
				str += '<button class="badge btn-danger" value="' + datapasien.data[i].id + '" onclick="f_delete(this.value)">Delete</button>'
				str += '</div>'
				str += '</td>'
				str += '</tr>'
				//console.log("kiw")
				//console.log(datapasien)
				//console.log(page, per_page)

			}
			var sortData = '<label for="rowPage" class="ml-4 m-2">Urutkan</label>'
			sortData += '<select class="form-control" id="order" onchange="sortBy(' + page + ',' + per_page + ', this.value)">'
			sortData += '<option disabled>~Berdasarkan~</option>'
			sortData += '<option value="fullname" selected>Nama</option>'
			sortData += '<option value="dob">Umur</option>'
			sortData += '<option value="name">Relasi</option>'
			sortData += '</select>'

			
			datapasien.data.filter((element) => { console.log(element) })
			var btnMultiDeletes = '<button class="btn btn-danger" onclick="f_multidelete('+(datapasien.page - 1 )+', '+datapasien.per_pages+')" class="btn btn-primary mb-2" data-toggle="modal" data-target="#exampleModal">Hapus</button>'
			$('#btnMultiDelete').html(btnMultiDeletes)
			
			$('#sorting').html(sortData)
			pagingList(datapasien)
			$('#isidata').html(str)
			datapasien.data.filter((element) => { if (yourArray.includes(element.id.toString())) { $('#check' + element.id.toString())[0].checked = true } })
		}
	})
}

function pagingList(data) {
	var nextMove = data.page + 1 > data.total_pages - 1 ? data.total_pages - 1 : data.page
	var prevMove = data.page - 1 < 0 ? data.page - 1 : 0
	var str = '<nav aria-label="Page navigation example">'
	str += '<ul class="pagination">'
	str += '<li class="page-item" > <a class="page-link" onclick="fetchData(' + prevMove + ',' + data.per_pages + ')">Previous</a></li >'
	for (var i = 0; i < data.total_pages; i++) {
		str += '<li class="page-item"><a class="page-link" onclick="fetchData(' + i + ',' + data.per_pages + ')">' + (i + 1) + '</a></li >'
	}
	str += '<li class="page-item"><a class="page-link" onclick="fetchData(' + nextMove + ',' + data.per_pages + ')">Next</a></li >'
	str += '</ul>'
	str += '</nav>'
	$('#paging').html(str)
}

function pagingListSort(data, value) {
	var tes = value + ""
	console.log(tes)

	var nextMove = data.page + 1 > data.total_pages - 1 ? data.total_pages - 1 : data.page
	var prevMove = data.page - 1 < 0 ? data.page - 1 : 0
	var str = '<nav aria-label="Page navigation example">'
	str += '<ul class="pagination">'
	str += '<li class="page-item" > <a class="page-link" onclick=sortBy(' + prevMove + ',' + data.per_pages + ',"' + tes + '")>Previous</a></li >'
	for (var i = 0; i < data.total_pages; i++) {
		str += '<li class="page-item"><a class="page-link" onclick=sortBy(' + i + ',' + data.per_pages + ',"' + tes + '")>' + (i + 1) + '</a></li >'
	}
	str += '<li class="page-item"><a class="page-link" onclick=sortBy(' + nextMove + ',' + data.per_pages + ',"' + tes + '")>Next</a></li >'
	str += '</ul>'
	str += '</nav>'
	$('#paging').html(str)
}
function openModal() {

	$.ajax({
		url: 'http://localhost:8888/api/bloodgroup',
		type: 'get',
		contentType: 'application/json',
		success: function(datablood) {
			$.ajax({
				url: 'http://localhost:8888/api/customerrelation',
				type: 'get',
				contentType: 'application/json',
				success: function(datacusrelation) {
					var str = '<form>'
					str += '<div class="form-group">'
					str += '<label for="fullName">Nama Lengkap*</label>'
					str += '<input type="text" class="form-control" id="fullname" placeholder="Nama Lengkap">'
					str += '</div>'
					str += '<div class="form-group">'
					str += '<label for="dob">Tanggal Lahir*</label>'
					str += '<input type="date" class="form-control" id="tgllahir">'
					str += '</div>'
					str += '<div class="form-row">'
					str += '<div class="col-md-4 pb-0">'
					str += '<div class="form-group">'
					str += '<label>Jenis Kelamin*</label>'
					str += '</div>'
					str += '</div>'
					str += '<div class="col-md-8 pb-0 text-center">'
					str += '<div class="form-check form-check-inline ml-3">'
					str += '<input class="form-check-input" type="radio" name="gender" id="pria" value="L">'
					str += '<label class="form-check-label" for="pria">Pria</label>'
					str += '</div>'
					str += '<div class="form-check form-check-inline ml-2">'
					str += '<input class="form-check-input" type="radio" name="gender" id="wanita" value="P">'
					str += '<label class="form-check-label" for="wanita">Wanita</label>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '<div class="row">'
					str += '<div class="col">'
					str += '<label for="bloodGroupId">Golongan Darah / Rhesus</label>'
					str += '</div>'
					str += '</div>'
					str += '<div class="form-row">'
					str += '<div class="form-group col-md-4">'
					str += '<select id="bloodGroupId" class="form-control">'
					str += '<option>--Gol Dar--</option>'
					for (var i = 0; i < datablood.length; i++) {
						str += '<option value=' + datablood[i].id + '>' + datablood[i].code + '</option>'
					}
					str += '</select>'
					str += '</div>'
					str += '<div class="form-group col-md-8 text-center">'
					str += '<div class="form-check form-check-inline mr-3">'
					str += '<input class="form-check-input" type="radio" name="rhesusType" id="rh+" value="Rh+">'
					str += '<label class="form-check-label" for="rh+">Rh+</label>'
					str += '</div>'
					str += '<div class="form-check form-check-inline">'
					str += '<input class="form-check-input" type="radio" name="rhesusType" id="rh-" value="Rh-">'
					str += '<label class="form-check-label" for="rh-">Rh-</label>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '<div class="form-row">'
					str += '<div class="form-group col-md-4">'
					str += '<label for="height">Tinggi Badan</label>'
					str += '<input type="text" class="form-control" id="height" placeholder="Tinggi Badan">'
					str += '</div>'
					str += '<div class="col-md-2"></div>'
					str += '<div class="form-group col-md-4">'
					str += '<label for="weight">Berat Badan</label>'
					str += '<input type="text" class="form-control" id="weight" placeholder="Berat Badan">'
					str += '</div>'
					str += '</div>'
					str += '<div class="form-row">'
					str += '<div class="form-group col">'
					str += '<label for="customerRelasiId">Relasi*</label>'
					str += '<select id="customerRelasiId" class="form-control">'
					str += '<option>--Relasi--</option>'
					for (var i = 0; i < datacusrelation.length; i++) {
						str += '<option value=' + datacusrelation[i].id + '>' + datacusrelation[i].name + '</option>'
					}
					str += '</select>'
					str += '</div>'
					str += '</div>'
					str += '</form>'

					$('.modal-title').html('Tambah Pasien')
					$('.modal-body').html(str)
					$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-primary').off('click').on('click', s_add)
					$('#modal').modal('show')
				}
			})


		}
	})

}

function s_add() {
	var fullName = $('#fullname').val()
	var tglLahir = $('#tgllahir').val()
	var gender = $("input[name='gender']:checked").val()
	var golDar = $('#bloodGroupId').val()
	var rhesusType = $("input[name='rhesusType']:checked").val()
	var tinggiBadan = $('#height').val()
	var beratBadan = $('#weight').val()
	var relasiId = $('#customerRelasiId').val()
	//var customerId = $().val()
	console.log(relasiId)

	var formdatabiodata = '{'
	formdatabiodata += '"fullname": "' + fullName + '"'
	formdatabiodata += '}'


	$.ajax({
		url: 'http://localhost:8888/biodata/add/fullname',
		type: 'post',
		contentType: 'application/json',
		data: formdatabiodata,
		success: function() {

			$.ajax({
				url: 'http://localhost:8888/biodata/findbiodata/maxid',
				type: 'get',
				contentType: 'application/json',
				success: function(datamaxbioid) {
					var formdatacustomer = '{'
					formdatacustomer += '"biodataId": "' + datamaxbioid + '",'
					formdatacustomer += '"dob": "' + tglLahir + '",'
					formdatacustomer += '"gender": "' + gender + '",'
					formdatacustomer += '"bloodGroupId": "' + golDar + '",'
					formdatacustomer += '"rhesusType": "' + rhesusType + '",'
					formdatacustomer += '"height": ' + tinggiBadan + ','
					formdatacustomer += '"weight": ' + beratBadan
					formdatacustomer += '}'
					$.ajax({
						url: 'http://localhost:8888/api/customer/add',
						type: 'post',
						contentType: 'application/json',
						data: formdatacustomer,
						success: function() {
							$.ajax({
								url: 'http://localhost:8888/api/customer/maxid',
								type: 'get',
								contentType: 'application/json',
								success: function(dataformid) {
									console.log(dataformid)
									var formdatacusmember = '{'
									formdatacusmember += '"parentBiodataId": ' + bioId + ','
									formdatacusmember += '"customerId": ' + dataformid + ','
									formdatacusmember += '"customerRelationId": ' + relasiId
									formdatacusmember += '}'
									$.ajax({
										url: 'http://localhost:8888/api/customermember/add',
										type: 'post',
										contentType: 'application/json',
										data: formdatacusmember,
										success: function() {
											$('#modal').modal('toggle')
											fetchData()

										}
									})
								}
							})


						}
					})
				}
			})

		}
	})
}

function searchPasienName() {
	var values = $('#test').val()
	$.ajax({
		url: 'http://localhost:8888/api/customermember/search?keyword=' + values,
		type: 'get',
		contentType: 'application/json',
		success: function(datapasien) {
			console.log(datapasien.length)
			console.log(datapasien[0][2])

			var str = ''
			for (var i = 0; i < datapasien.length; i++) {
				var datedob = hitungUmur(datapasien[i][2]) + " tahun"
				//datedob = datedob.substring(0,10)			
				console.log(datapasien)
				str += '<tr>'
				str += '<td>'
				str += '<div class="form-check d-flex justify-content-center" style="margin-top: 15%;">'
				str += '<input class="form-check-input" type="checkbox" name="listCheck" id="check' + datapasien[i][0] + '" value=' + datapasien[i][0] + ' onclick="check(this.value)">'
				str += '</div>'
				str += '</td>'
				str += '<td id="datapasien">'
				str += '<h6 class="my-0" style="font-size: 15px; color: #4E73DF;">' + datapasien[i][1] + '</h6>'
				str += '<h6 class="my-0" style="font-size: 12px;">' + datapasien[i][3] + ', ' + datedob + '</h6>'
				str += '<h6 class="my-0" style="font-size: 10px;">9 Chat Dokter, 3 Janji Dokter</h6>'
				str += '</td>'
				str += '<td id="aksi">'
				str += '<div style="padding-left: 50%;">'
				str += '<button class="badge btn-warning text-white mr-2" value="' + datapasien[i][0] + '" onclick="f_edit(this.value)">Edit</button>'
				str += '<button class="badge btn-danger" value="' + datapasien[i][0] + '" onclick="f_delete(this.value)">Delete</button>'
				str += '</div>'
				str += '</td>'
				str += '</tr>'
				console.log("kiw")
				console.log(datapasien)
				//console.log(page, per_page)

			}
			$('#isidata').html(str)
		}
	})
}

function f_edit(id) {
	$.ajax({
		url: `http://localhost:8888/api/customermember/${id}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datacusmember) {
			$.ajax({
				url: `http://localhost:8888/api/bloodgroup`,
				type: 'get',
				contentType: 'application/json',
				success: function(datablood) {
					$.ajax({
						url: `http://localhost:8888/api/customerrelation`,
						type: 'get',
						contentType: 'application/json',
						success: function(datacusrelation) {
							console.log(datacusmember)
							//console.log(mcustomer.id)

							var fullnames = datacusmember.mcustomer.mbiodata.fullname
							var tanggallahir = datacusmember.mcustomer.dob
							console.log(tanggallahir)
							var formatdate = new Date(tanggallahir)
							var tanggal = formatdate.getDate().toString().padStart(2, '0')
							var bulan = (formatdate.getMonth() + 1).toString().padStart(2, '0')
							var tahun = formatdate.getFullYear()
							console.log(`${tahun}-${bulan}-${tanggal}`)
							var hasilformat = `${tahun}-${bulan}-${tanggal}`
							var genderL = datacusmember.mcustomer.gender == 'L' ? 'checked' : ''
							var genderP = datacusmember.mcustomer.gender == 'P' ? 'checked' : ''
							var goldar = datacusmember.mcustomer.bloodGroupId
							var rhesusTypePositif = datacusmember.mcustomer.rhesusType == 'Rh+' ? 'checked' : ''
							var rhesusTypeNegatif = datacusmember.mcustomer.rhesusType == 'Rh-' ? 'checked' : ''
							var tinggiBadan = datacusmember.mcustomer.height
							var beratBadan = datacusmember.mcustomer.weight
							var customerRelasi = datacusmember.mcustomerrelation.id

							console.log(fullnames, hasilformat, genderL, genderP, goldar, rhesusTypePositif, rhesusTypeNegatif, tinggiBadan, beratBadan, customerRelasi)

							var str = '<form>'
							str += '<div class="form-group">'
							str += '<label for="fullName">Nama Lengkap*</label>'
							str += '<input type="text" class="form-control" id="fullname" placeholder="Nama Lengkap" value="' + fullnames + '">'
							str += '</div>'
							str += '<div class="form-group">'
							str += '<label for="dob">Tanggal Lahir*</label>'
							str += '<input type="date" class="form-control" id="tgllahir" value="' + hasilformat + '">'
							str += '</div>'
							str += '<div class="form-row">'
							str += '<div class="col-md-4 pb-0">'
							str += '<div class="form-group">'
							str += '<label>Jenis Kelamin*</label>'
							str += '</div>'
							str += '</div>'
							str += '<div class="col-md-8 pb-0 text-center">'
							str += '<div class="form-check form-check-inline ml-3">'
							str += '<input class="form-check-input" type="radio" name="gender" id="pria" value="L" ' + genderL + '>'
							str += '<label class="form-check-label" for="pria">Pria</label>'
							str += '</div>'
							str += '<div class="form-check form-check-inline ml-2">'
							str += '<input class="form-check-input" type="radio" name="gender" id="wanita" value="P" ' + genderP + '>'
							str += '<label class="form-check-label" for="wanita">Wanita</label>'
							str += '</div>'
							str += '</div>'
							str += '</div>'
							str += '<div class="row">'
							str += '<div class="col">'
							str += '<label for="bloodGroupId">Golongan Darah / Rhesus</label>'
							str += '</div>'
							str += '</div>'
							str += '<div class="form-row">'
							str += '<div class="form-group col-md-4">'
							str += '<select id="bloodGroupId" class="form-control">'
							str += '<option>--Gol Dar--</option>'
							for (var i = 0; i < datablood.length; i++) {
								str += '<option value=' + datablood[i].id + '>' + datablood[i].code + '</option>'
							}
							str += '</select>'
							str += '</div>'
							str += '<div class="form-group col-md-8 text-center">'
							str += '<div class="form-check form-check-inline mr-3">'
							str += '<input class="form-check-input" type="radio" name="rhesusType" id="rh+" value="Rh+" ' + rhesusTypePositif + '>'
							str += '<label class="form-check-label" for="rh+">Rh+</label>'
							str += '</div>'
							str += '<div class="form-check form-check-inline">'
							str += '<input class="form-check-input" type="radio" name="rhesusType" id="rh-" value="Rh-" ' + rhesusTypeNegatif + '>'
							str += '<label class="form-check-label" for="rh-">Rh-</label>'
							str += '</div>'
							str += '</div>'
							str += '</div>'
							str += '<div class="form-row">'
							str += '<div class="form-group col-md-4">'
							str += '<label for="height">Tinggi Badan</label>'
							str += '<input type="text" class="form-control" id="height" placeholder="Tinggi Badan" value="' + tinggiBadan + '">'
							str += '</div>'
							str += '<div class="col-md-2"></div>'
							str += '<div class="form-group col-md-4">'
							str += '<label for="weight">Berat Badan</label>'
							str += '<input type="text" class="form-control" id="weight" placeholder="Berat Badan" value="' + beratBadan + '">'
							str += '</div>'
							str += '</div>'
							str += '<div class="form-row">'
							str += '<div class="form-group col">'
							str += '<label for="customerRelasiId">Relasi*</label>'
							str += '<select id="customerRelasiId" class="form-control">'
							str += '<option>--Relasi--</option>'
							for (var i = 0; i < datacusrelation.length; i++) {
								str += '<option value=' + datacusrelation[i].id + '>' + datacusrelation[i].name + '</option>'
							}
							str += '</select>'
							str += '</div>'
							str += '</div>'
							str += '</form>'
							console.log(datacusmember.mcustomer.biodataId)
							console.log(datacusmember.id)
							$('.modal-title').html('Tambah Pasien')
							$('.modal-body').html(str)
							$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-primary').off('click').on('click', function() { s_edit(datacusmember.id) })
							$('#modal').modal('show')

							$('#bloodGroupId').val(goldar)
							$('#customerRelasiId').val(customerRelasi)
						}
					})
				}
			})
		}
	})
}

function s_edit(id) {

	var fullName = $('#fullname').val()
	var tglLahir = $('#tgllahir').val()
	var gender = $('input[name="gender"]:checked').val()
	var golDar = $('#bloodGroupId').val()
	var rhesusType = $('input[name="rhesusType"]:checked').val()
	var tinggiBadan = $('#height').val()
	var beratBadan = $('#weight').val()
	var relasiId = $('#customerRelasiId').val()

	$.ajax({
		url: `http://localhost:8888/api/customermember/${id}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datacusmember) {
			console.log(datacusmember)
			$.ajax({
				url: `http://localhost:8888/api/customer/${datacusmember.customerId}`,
				type: 'get',
				contentType: 'application/json',
				success: function(datacustomer) {
					console.log(datacustomer)
					$.ajax({
						url: `http://localhost:8888/biodata/edit/${datacustomer.biodataId}`,
						type: 'get',
						contentType: 'application/json',
						success: function(databiodata) {
							console.log(databiodata)
							var formBiodata = '{'
							formBiodata += '"id": ' + databiodata.id + ','
							formBiodata += '"fullname": "' + fullName + '"'
							formBiodata += '}'

							$.ajax({
								url: 'http://localhost:8888/biodata/edit/data',
								type: 'put',
								contentType: 'application/json',
								data: formBiodata,
								success: function() {
									var formCustomer = '{'
									formCustomer += '"id": ' + datacustomer.id + ','
									formCustomer += '"dob": "' + tglLahir + '",'
									formCustomer += '"gender": "' + gender + '",'
									formCustomer += '"bloodGroupId": ' + golDar + ','
									formCustomer += ' "rhesusType": "' + rhesusType + '",'
									formCustomer += '"height": ' + tinggiBadan + ','
									formCustomer += '"weight": ' + beratBadan
									formCustomer += '}'

									$.ajax({
										url: 'http://localhost:8888/api/customer/edit',
										type: 'put',
										contentType: 'application/json',
										data: formCustomer,
										success: function() {
											var formCustomerMember = '{'
											formCustomerMember += '"id": ' + datacusmember.id + ','
											formCustomerMember += '"parentBiodataId": ' + bioId + ','
											formCustomerMember += '"customerRelationId": ' + relasiId
											formCustomerMember += '}'

											$.ajax({
												url: 'http://localhost:8888/api/customermember/edit',
												type: 'put',
												contentType: 'application/json',
												data: formCustomerMember,
												success: function() {
													$('#modal').modal('toggle')
													fetchData()
												}
											})
										}
									})
								}
							})


						}
					})
				}
			})
		}
	})
}

function f_delete(id) {
	$.ajax({
		url: `http://localhost:8888/api/customermember/${id}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datacusmember) {
			console.log(datacusmember)
			$.ajax({
				url: `http://localhost:8888/api/customer/${datacusmember.customerId}`,
				type: 'get',
				contentType: 'application/json',
				success: function(datacustomer) {
					console.log(datacustomer)
					$.ajax({
						url: `http://localhost:8888/biodata/delete/${datacustomer.biodataId}`,
						type: 'get',
						contentType: 'application/json',
						success: function(databiodata) {
							console.log(databiodata)
							var str = '<h5>Anda yakin akan menghapus pasien ' + databiodata.fullname + ' ?</h5>'

							$('.modal-title').html('Hapus Data')
							$('#btnSubmit').html('Hapus').removeAttr('class').addClass('btn btn-danger')
							$('#btnSubmit').off('click').on('click', function() { s_delete(datacusmember.id) })
							$('.modal-body').html(str)
							$('#modal').modal('show')
						}
					})
				}
			})
		}
	})
}

function s_delete(id) {
	$.ajax({
		url: `http://localhost:8888/api/customermember/${id}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datacusmember) {
			$.ajax({
				url: `http://localhost:8888/api/customer/${datacusmember.customerId}`,
				type: 'get',
				contentType: 'application/json',
				success: function(datacustomer) {
					$.ajax({
						url: `http://localhost:8888/biodata/delete/${datacustomer.biodataId}`,
						type: 'get',
						contentType: 'application/json',
						success: function(databiodata) {
							$.ajax({
								url: `http://localhost:8888/biodata/delete/data/${databiodata.id}`,
								type: 'put',
								contentType: 'application/json',
								success: function() {
									$.ajax({
										url: `http://localhost:8888/api/customer/delete/${datacustomer.id}`,
										type: 'put',
										contentType: 'application/json',
										success: function() {
											$.ajax({
												url: `http://localhost:8888/api/customermember/delete/${datamember.id}`,
												type: 'put',
												contentType: 'application/json',
												success: function() {
													$('#modal').modal('toggle')
													fetchData()
												}
											})
										}
									})
								}
							})
						}
					})
				}
			})
		}
	})
}
function sortBy(page, per_page, value) {
	console.log(page, per_page, value)
	$.ajax({
		url: `http://localhost:8888/api/customermember/sort/test?page=${page}&per_page=${per_page}&sort=${value}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datapasien) {
			console.log(datapasien.data[0].id)
			var str = ''
			for (var i = 0; i < datapasien.data.length; i++) {
				var datedob = hitungUmur(datapasien.data[i].mcustomer.dob) + " tahun"
				//datedob = datedob.substring(0,10)	

				str += '<tr>'
				str += '<td>'
				str += '<div class="form-check d-flex justify-content-center" style="margin-top: 15%;">'
				str += '<input class="form-check-input" type="checkbox" id="gridCheck">'
				str += '</div>'
				str += '</td>'
				str += '<td id="datapasien">'
				str += '<h6 class="my-0" style="font-size: 15px; color: #4E73DF;">' + datapasien.data[i].mcustomer.mbiodata.fullname + '</h6>'
				str += '<h6 class="my-0" style="font-size: 12px;">' + datapasien.data[i].mcustomerrelation.name + ', ' + datedob + '</h6>'
				str += '<h6 class="my-0" style="font-size: 10px;">9 Chat Dokter, 3 Janji Dokter</h6>'
				str += '</td>'
				str += '<td id="aksi">'
				str += '<div style="padding-left: 50%;">'
				str += '<button class="badge btn-warning text-white mr-2" value="' + datapasien.data[i].id + '" onclick="f_edit(this.value)">Edit</button>'
				str += '<button class="badge btn-danger" value="' + datapasien.data[i].id + '" onclick="f_delete(this.value)">Delete</button>'
				str += '</div>'
				str += '</td>'
				str += '</tr>'
			}
			$('#isidata').html(str)
			//console.log(str)
			pagingListSort(datapasien, value)
		}
	})
}


function hitungUmur(tanggallahir) {
	var tanggallahir = new Date(tanggallahir);
	var sekarang = new Date();

	var tahun = sekarang.getFullYear() - tanggallahir.getFullYear();
	var bulan = sekarang.getMonth() - tanggallahir.getMonth();

	if (bulan < 0 || (bulan === 0 && sekarang.getDate() < tanggallahir.getDate())) {
		tahun--;
	}

	return tahun;
}


function check() {
	var check = $("input:checkbox[name=listCheck]")
	for (var i = 0; i < check.length; i++) {
		if (check[i].checked) {
			if (!yourArray.includes(check[i].value)) {
				yourArray.push(check[i].value)
				for (var i = 0; i < yourArray.length; i++) {
				}
			}
		} else {
			var index = yourArray.indexOf(check[i].value)
			if (index !== -1) {
				yourArray.splice(index, 1)
			}
		}
	}
	console.log(yourArray)
}
function f_multidelete(page, per_page) {
	//page = page -1
	console.log(page, per_page)

	$.ajax({
		url: `http://localhost:8888/api/customermember/multiselect?ids=${yourArray}&page=${page}&per_page=${per_page}`,
		type: 'get',
		contentType: 'application/json',
		success: function(datamember) {
			console.log(datamember)
			var str = '<h5>Anda yakin ingin menghapus pasien</h5>'
			str += '<ul>'
			for (var i = 0; i < datamember.length; i++) {
				str += '<li>' + datamember[i].mcustomer.mbiodata.fullname + '</li>'
			}
			str += '</ul>'

			$('.modal-title').html('Hapus Data')
			$('#btnSubmit').html('Hapus').removeAttr('class').addClass('btn btn-danger')
			$('#btnSubmit').off('click').on('click', function() { s_multidelete(page, per_page) })
			$('.modal-body').html(str)
			$('#modal').modal('show')

		}
	})
}

function s_multidelete(page, per_page) {
	console.log(page, per_page)

	$.ajax({
		url: `http://localhost:8888/api/delete?listId=${yourArray}&page=${page}&per_page=${per_page}`,
		type: 'put',
		contentType: 'application/json',
		success: function() {
			$('#modal').modal('toggle')
			console.log(page, per_page)
			fetchData(page, per_page)
			console.log("sukses")
		}
	})
}