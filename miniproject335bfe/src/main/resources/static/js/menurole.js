$(function() {
	fetchData()
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

function fetchData() {
	$.ajax({
		url: 'http://localhost:8888/role/showall',
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = ''
			for (var i = 0; i < data.data.length; i++) {
				str += '<tr>'
				str += '<td>' + data.data[i].name + '</td>'
				str += '<td>' + data.data[i].code + '</td>'
				str += '<td>'
				str += '<button class="badge btn btn-primary text-white mr-2" value="' + data.data[i].id + '" onclick="f_atur(this.value)">Atur</button>'
				str += '</td>'
				str += '</tr>'
			}
			$('#isidata').html(str)
		}
	})
}

function f_atur(role_id) {
	$.ajax({
		url: 'http://localhost:8888/landingpage/menu/topmenu',
		type: 'get',
		contentType: 'application/json',
		success: function(datatopmenu) {
			console.log(datatopmenu)
			console.log(datatopmenu[0].name)
			var str = ''
			str += '<form>'
			str += '<div class="form-group">'
			str += '<div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="selectall" value="" id="selectAll">'
			str += '<label class="form-check-label" for="defaultCheck1">Select All</label>'
			str += '</div>'
			for (var i = 0; i < datatopmenu.length; i++) {
				var id = datatopmenu[i].id
				console.log(id)
				str += '<div class="form-check">'
				str += '<input class="form-check-input" type="checkbox" name="topmenu" value="'+id+'" id="menu' + id + '" onchange="selectByTopMenu(this.value)">'
				str += '<label class="form-check-label" for="defaultCheck1">' + datatopmenu[i].name + '</label>'
				str += '<div id="subMenu' + id + '">'

				$.ajax({
					url: 'http://localhost:8888/landingpage/menu/submenu?id=' + id,
					type: 'get',
					contentType: 'application/json',
					success: function(datasubmenu) {
						console.log(id)
						console.log(datasubmenu)
						//console.log(datasubmenu[0].parentId.id)
						var strsub = ''
						for (var j = 0; j < datasubmenu.length; j++) {
							console.log("masuk")
							console.log(datasubmenu[j].name)
							console.log(datasubmenu[j].parentId.id)
							var parentId = datasubmenu[j].parentId
							strsub += '<div class="form-check" >'
							strsub += '<input class="form-check-input subMenu-'+ parentId +'" type="checkbox" name="submenu" value="' + datasubmenu[j].id + '" onchange="selectBymenu(this.value)">'
							strsub += '<label class="form-check-label" for="defaultCheck1">' + datasubmenu[j].name + '</label>'
							strsub += '</div>'
							console.log(datasubmenu[j].parentId.id)
							$('#subMenu' + datasubmenu[j].parentId.id).html(strsub)

						}


					}
				})
				str += '</div>'
				str += '</div>'

			}
			str += '</div>'
			str += '</div>'
			str += '</form>'
			
			findByRoleId(role_id)

			$('.modal-title').html('ATUR AKSES')
			$('.modal-body').html(str)
			$('#btnCancel').html('Batal')
			$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-danger').off('click').on('click', s_atur)
			$('#modal').modal('show')
			
			$('#selectAll').click(function() {
				if (this.checked) {
					$(':checkbox').each(function() {
						this.checked = true
					})
				} else {
					$(':checkbox').each(function() {
						this.checked = false
					})
				}
			})



		}

	})
	

}



function findByRoleId(role_id) {

	$.ajax({
		url: 'http://localhost:8888/api/menurole/all?id='+role_id,
		type: 'get',
		contentType: 'application/json',
		success: function(dataRole) {
			console.log("==============================")
			console.log(dataRole)
			for (var i = 0; i < dataRole.length; i++) {
				console.log(dataRole[i].menu.id)
				/*console.log($('input:checkbox[value="' + dataRole[i].menu.id + '"]')[0].value)*/
				if ($('input:checkbox[value="' + dataRole[i].menu.id + '"]').val() == dataRole[i].menu.id) {
					$('input:checkbox[value="' + dataRole[i].menu.id + '"]')[0].checked = true
				} else {
					$('input:checkbox[value="' + dataRole[i].menu.id + '"]')[0].checked = false
				}
			}
			console.log("==============================")
		}
	})
}

function selectByTopMenu(id) {
	console.log(id)
	if ($('#menu' + id)[0].checked == true) {
		$('.subMenu-' + id).each(function() {
			this.checked = true
		})
	} else {
		$('.subMenu-' + id).each(function() {
			this.checked = false
		})
	}
}

/*
function f_atur(id) {
	$.ajax({
		url: 'http://localhost:8888/api/menurole/all/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(datarole) {
			console.log(datarole)
			//console.log(datarole[0].role.id)
			//console.log(datarole[0].menu.name)
			//TopMenu
			var master = ''
			var transaksi = ''
			var aturtindakandokter = ''
			var aturhargatindakandokter = ''
			//SubMenu
			var kurir = ''
			var spesialisasi = ''
			var buatjanji = ''
			for (var i = 0; i < datarole.length; i++) {
				if (datarole[i].menu.id == 1) {
					master = 'checked'
				}
				if (datarole[i].menu.id == 2) {
					transaksi = 'checked'
				}
				if (datarole[i].menu.id == 3) {
					aturtindakandokter = 'checked'
				}
				if (datarole[i].menu.id == 4) {
					aturhargatindakandokter = 'checked'
				}
				if (datarole[i].menu.id == 5) {
					kurir = 'checked'
				}
				if (datarole[i].menu.id == 6) {
					spesialisasi = 'checked'
				}
				if (datarole[i].menu.id == 7) {
					buatjanji = 'checked'
				}

			}

			//	for (var i=0;i<datarole.length;i++){

			//	}

			var str = '<form>'
			str += '<div class="form-group">'
			str += '<div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="selectall" value="Select All" id="selectallrole">'
			str += '<label class="form-check-label" for="defaultCheck1">Select All</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Master"' + master + ' id="master">'
			str += '<label class="form-check-label" for="defaultCheck1">Master</label>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Kurir"' + kurir + ' id="kurir">'
			str += '<label class="form-check-label" for="defaultCheck1">Kurir</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Spesialisasi"' + spesialisasi + ' id="spesialisasi">'
			str += '<label class="form-check-label" for="defaultCheck1">Spesialisasi</label>'
			str += '</div>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Transaksi"' + transaksi + ' id="transaksi">'
			str += '<label class="form-check-label" for="defaultCheck1">Transaksi</label>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Buat Janji"' + buatjanji + ' id="buatjanji">'
			str += '<label class="form-check-label" for="defaultCheck1">Buat Janji</label>'
			str += '</div>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Atur Tindakan Dokter"' + aturtindakandokter + ' id="aturtindakandokter">'
			str += '<label class="form-check-label" for="defaultCheck1">Atur Tindakan Dokter</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Atur Harga Tindakan Dokter"' + aturhargatindakandokter + ' id="aturhargatindakandokter">'
			str += '<label class="form-check-label" for="defaultCheck1">Atur Harga Tindakan Dokter</label>'
			str += '</div>'
			str += '</div>'
			str += '</div>'
			str += '</form>'

			$('.modal-title').html('ATUR AKSES')
			$('.modal-body').html(str)
			$('#btnCancel').html('Batal')
			$('#btnSubmit').html('Simpan').removeAttr('class').addClass('btn btn-danger').off('click').on('click', s_atur)
			$('#modal').modal('show')

		}
	})

}
*/
function s_atur() {

	//TopMenu
	var master = $('#master').val()
	var transaksi = $('#transaksi').val()
	var aturtindakandokter = $('#aturtindakandokter').val()
	var aturhargatindakandokter = $('#aturhargatindakandokter').val()
	//SubMenu
	var kurir = $('#kurir').val()
	var spesialisasi = $('#spesialisasi').val()
	var buatjanji = $('#buatjanji').val()

	console.log(master)
	console.log(transaksi)
	console.log(aturtindakandokter)
	console.log(aturhargatindakandokter)
	console.log(kurir)
	console.log(spesialisasi)
	console.log(buatjanji)

	$.ajax({
		url: 'http://localhost:8888/api/menurole/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(dataallmenurole) {
			console.log(dataallmenurole)
			var master = dataallmenurole[0].menu.name == 'Master' ? 'checked' : ''
			console.log(dataallmenurole[0].menu.name)
			for (var i = 0; i < dataallmenurole.length; i++) {

			}
			var str = '<form>'
			str += '<div class="form-group">'
			str += '<div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="selectall" value="Select All" id="selectallrole">'
			str += '<label class="form-check-label" for="defaultCheck1">Select All</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Master" id="master">'
			str += '<label class="form-check-label" for="defaultCheck1">Master</label>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Kurir" id="kurir">'
			str += '<label class="form-check-label" for="defaultCheck1">Kurir</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Spesialisasi" id="spesialisasi">'
			str += '<label class="form-check-label" for="defaultCheck1">Spesialisasi</label>'
			str += '</div>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Transaksi" id="transaksi">'
			str += '<label class="form-check-label" for="defaultCheck1">Transaksi</label>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="submenu" value="Buat Janji" id="buatjanji">'
			str += '<label class="form-check-label" for="defaultCheck1">Buat Janji</label>'
			str += '</div>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Atur Tindakan Dokter" id="aturtindakandokter">'
			str += '<label class="form-check-label" for="defaultCheck1">Atur Tindakan Dokter</label>'
			str += '</div>'
			str += '<div class="form-check">'
			str += '<input class="form-check-input" type="checkbox" name="topmenu" value="Atur Harga Tindakan Dokter" id="aturhargatindakandokter">'
			str += '<label class="form-check-label" for="defaultCheck1">Atur Harga Tindakan Dokter</label>'
			str += '</div>'
			str += '</div>'
			str += '</div>'
			str += '</form>'
		}
	})
}