
var value = ""
var idLokasi =0
var nameDoktor =""
var spesialisId = 0
var tindakanId = 0

function oper(val) {
	value = val
	console.log(value)
}

function openModal() {
	$.ajax({
		url: 'http://localhost:8888/dokter/lokasi',
		type: 'get',
		contentType: 'application/json',
		success: function(dataLokasi) {

			var str = '<form>'
			str += '<h5>Masukkan Minimal 1 Kata Kunci Untuk Pencarian Dokter Anda</h5>'
			str += '<p></p>'
			str += '<div class="form-group">'
			str += '<label>Lokasi</label>'
			str += '<select class = "custom-select form-control" id="lokasiId">'
			str += '<option value=>---Pilih---</option>'
			for (var i = 0; i < dataLokasi.length; i++) {
				if (dataLokasi[i].parentId == null) {
					str += '<option value="' + dataLokasi[i].name + '">' + dataLokasi[i].name + '</option>'
				}
			}
			str += '</select>'
			str += '</div>'
			str += '<br>'
			str += '<div class="form-group">'
			str += '<label>Nama Dokter</label>'
			str += '<input type="text" class="form-control" value="' + value + '" id="namadokter" oninput="oper(this.value)">'
			str += '</div>'
			str += '<br>'

			$.ajax({
				url: 'http://localhost:8888/dokter/spesialis',
				type: 'get',
				contentType: 'application/json',
				success: function(dataSpesialis) {

					str += '<div class="form-group">'
					str += '<label>Spesialisasi / Sub-Spesialisasi</label>'
					str += '<select class = "custom-select form-control" id="spesialis">'
					str += '<option value="">---Pilih---</option>'
					for (var i = 0; i < dataSpesialis.length; i++) {
						if (dataSpesialis[i].parentId == null) {
							str += '<option value="' + dataSpesialis[i].name + '">' + dataSpesialis[i].name + '</option>'
						}
					}
					str += '</select>'
					str += '</div>'
					str += '<br>'
					str += '<div class="form-group">'
					$.ajax({
						url: 'http://localhost:8888/treatment/tindakan',
						type: 'get',
						contentType: 'application/json',
						success: function(dataTindakan) {
							str += '<label>Tindakan Medis</label>'
							str += '<select class = "custom-select form-control" id="tindakan">'
							str += '<option value="">---Pilih---</option>'
							for (var i = 0; i < dataTindakan.length; i++) {
								if (dataTindakan[i].parentId == null) {
									str += '<option value="' + dataTindakan[i].name + '">' + dataTindakan[i].name + '</option>'
								}
							}
							str += '</select>'
							str += '</div>'
							str += '<br>'
							str += '</form>'

							$('.modal-title').html("Cari Dokter")
							$('.modal-body').html(str)
							$('#btnSubmit').html("Cari").off('click').on('click', change)
							$('#btnCancel').html("Atur Ulang").addClass("btn-light btn-outline-primary")
							$('#modal').modal('show')
						}
					})
				}
			})

		}
	})

}

function change() {
	idLokasi = $('#lokasiId').val()
	spesialisId = $('#spesialis').val()
	tindakanId = $('#tindakan').val()
	
	localStorage.setItem("nameLokasi", idLokasi)
	localStorage.setItem("nameDokter",value)
	localStorage.setItem("nameTindakan",tindakanId)
	localStorage.setItem("nameSpesialis", spesialisId)
	console.log(idLokasi,value,spesialisId,tindakanId)
	window.location.href="/dokter/caridokter"
}



