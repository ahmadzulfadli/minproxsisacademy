$(function() {
	var lokasi = localStorage.getItem("nameLokasi")
	var dokter = localStorage.getItem("nameDokter")
	var tindakan = localStorage.getItem("nameTindakan")
	var spesialis = localStorage.getItem("nameSpesialis")
	console.log(lokasi, dokter, tindakan, spesialis)
	fetchDokter(lokasi, dokter, tindakan, spesialis)
})
const now = new Date()
const day = now.getDay()
var hari = ""
if (day == 1) {
	hari = "Monday"
}
else if (day == 2) {
	hari = "Tuesday"
}
else if (day == 3) {
	hari = "Wednesday"
}
else if (day == 4) {
	hari = "Thursday"
}
else if (day == 5) {
	hari = "Friday"
}
else if (day == 6) {
	hari = "Saturday"
}
else if (day == 0) {
	hari = "Sunday"
}



function fetchDokter(lokasi, fullname, tindakan, spesialis) {
	$.ajax({
		url: `http://localhost:8888/doctor/pagingsearch?namaDokter=${fullname}&namaSpesialis=${spesialis}&namaTindakan=${tindakan}&namaLokasi=${lokasi}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataDoctor) {
			var str = ""
			for (var i = 0; i < dataDoctor.data.length; i++) {
				if (i == 0) {
					str += '<div class="col mb-3 pb-5" style="inline-size: 500px;">'
					str += '<div class="card border-primary shadow">'
					str += '<div class="card-body">'
					str += '<div class="row">'
					str += '<div class="col-sm">'
					str += '<div class="text-xs font-weight-bold text-primary text-uppercase mb-1" style="font-size: medium;">' + dataDoctor.data[i].fullName + '</div>'
					str += '<p class="ml-3" style="font-size: small; margin-bottom:0">' + dataDoctor.data[i].namaSpesialis + '</p>'
					str += '<p class="ml-3" style="font-size: medium;"> ' + dataDoctor.data[i].pengalaman + ' Tahun Pengalaman</p>'
					var count = 0
					for (var j = 0; j < dataDoctor.data.length; j++) {
						count += 1
						if (count > 2) {
							continue
						}
						if (dataDoctor.data[i].dokterId == dataDoctor.data[j].dokterId) {
							if (dataDoctor.data[j].jenisRumahSakit == "Online") {
								continue
							}
							str += '<p class="ml-2" style="font-size: medium;"><img src="/svg/hospital.svg" alt="hospital SVG" width="30px" height="30px" class="text-warning mr-2" />' + dataDoctor.data[j].namaMedicalFacility + '(' + dataDoctor.data[j].namaKecamatan + ', ' + dataDoctor.data[j].namaKota + ')</p>'
						}
					}
					str += '<button class="btn btn-outline-primary form-control" style="height:35px; text-align:center; inline-size: 300px;" onclick="change(' + dataDoctor.data[i].dokterId + ')">Lihat info lebih banyak</button>'
					str += '</div>'
					str += '<div class="col-sm">'
					str += '<div class="d-flex justify-content-end pt-3 pr-2 mb-4"><img src="' + dataDoctor.data[i].fotoUrl + '" alt="person SVG" width="95px" height="95px" class="text-warning" /></div>'
					var flag1 = 0
					var flag2 = 0
					for (var j = 0; j < dataDoctor.data.length; j++) {
						if (dataDoctor.data[i].dokterId == dataDoctor.data[j].dokterId) {
							if (dataDoctor.data[j].jenisRumahSakit != "Online") {
								flag1 = 1
							} else {
								flag1 = 0
								break
							}
						}
					}
					for (var j = 0; j < dataDoctor.data.length; j++) {
						if (dataDoctor.data[i].dokterId == dataDoctor.data[j].dokterId) {
							if (hari != dataDoctor.data[j].hariBuka) {
								flag2 = 1
							} else {
								flag2 = 0
								break
							}
						}
					}

					console.log(hari, dataDoctor.data[i].hariBuka)
					if (flag1 == 1) {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-dark " style="height:35px; width:100px;" disabled>Invite</button></div>'

					} else if (flag2 == 1) {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-dark " style="height:35px; width:100px;" disabled>Offline</button></div>'

					} else {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-primary " style="height:35px; width:100px;" onclick="">Chat</button></div>'

					}
					str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-primary text-white" style="height:40px; width:100px;" onclick="">Buat Janji</button></div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
				}
				else if (dataDoctor.data[i].dokterId == dataDoctor.data[i - 1].dokterId) {
					continue
				} else {
					str += '<div class="col mb-3 pb-5" style="inline-size: 500px;">'
					str += '<div class="card border-primary shadow">'
					str += '<div class="card-body">'
					str += '<div class="row">'
					str += '<div class="col-sm">'
					str += '<div class="text-xs font-weight-bold text-primary text-uppercase mb-1" style="font-size: medium;">' + dataDoctor.data[i].fullName + '</div>'
					str += '<p class="ml-3" style="font-size: small; margin-bottom:0">' + dataDoctor.data[i].namaSpesialis + '</p>'
					str += '<p class="ml-3" style="font-size: medium;"> ' + dataDoctor.data[i].pengalaman + ' Tahun Pengalaman</p>'
					var count = 0
					for (var j = 0; j < dataDoctor.data.length; j++) {
						count += 1
						if (count > 2) {
							continue
						}
						if (dataDoctor.data[i].dokterId == dataDoctor.data[j].dokterId) {
							if (dataDoctor.data[j].jenisRumahSakit == "Online") {
								continue
							}
							str += '<p class="ml-2" style="font-size: medium;"><img src="/svg/hospital.svg" alt="hospital SVG" width="30px" height="30px" class="text-warning mr-2" />' + dataDoctor.data[j].namaMedicalFacility + '(' + dataDoctor.data[j].namaKecamatan + ', ' + dataDoctor.data[j].namaKota + ')</p>'
						}
					}
					str += '<button class="btn btn-outline-primary form-control" style="height:35px; text-align:center; inline-size: 300px;" onclick="change(' + dataDoctor.data[i].dokterId + ')">Lihat info lebih banyak</button>'
					str += '</div>'
					str += '<div class="col-sm">'
					str += '<div class="d-flex justify-content-end pt-3 pr-2 mb-4"><img src="' + dataDoctor.data[i].fotoUrl + '" alt="person SVG" width="95px" height="95px" class="text-warning" /></div>'
					var flag = 0
					for (var j = 0; j < dataDoctor.data.length; j++) {
						if (dataDoctor.data[i].dokterId == dataDoctor.data[j].dokterId) {
							if (dataDoctor.data[j].jenisRumahSakit != "Online") {
								flag = 1
								continue
							}
							if (day != dataDoctor.data[j].hariBuka) {
								flag = 2
								continue
							}
						}
					}
					if (flag == 1) {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-dark " style="height:35px; width:100px;" disabled>Cannot Chat</button></div>'

					} else if (flag == 2) {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-dark " style="height:35px; width:100px;" disabled>Offline</button></div>'

					} else {
						str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-outline-primary " style="height:35px; width:100px;" onclick="">Chat</button></div>'

					}
					str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-primary text-white" style="height:40px; width:100px;" onclick="">Buat Janji</button></div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
				}

				/*$.ajax({
					url: `http://localhost:8888/biodata/${dataDoctor[i].biodataId}`,
					type: 'get',
					contentType: 'application/json',
					success: function(dataBio) {
						
					}
				})*/
			}
			$('#isidata').html(str)
		}

	})
}

function change(idDokter) {
	localStorage.setItem("dokterId", idDokter)
	window.location.href = "/app/doctor/detail"
}