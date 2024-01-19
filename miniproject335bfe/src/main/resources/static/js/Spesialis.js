$(function() {
	fetchData(0, 5)
	console.log(search)
})
var halaman = 0
var halamanPerLembar = 0
var isSearch = false
var search = ""
var cek = 0



function fetchData(page, perPages) {
	$.ajax({
		url: `http://localhost:8888/dokter/spesialis/paging?page=${page}&perPages=${perPages}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataResponse) {
			var str = ''
			for (var i = 0; i < dataResponse.data.length; i++) {
				str += '<tr>'
				str += '<td class="pl-5">' + dataResponse.data[i].name + '</td>'
				str += '<td><button class="btn btn-primary text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_Edit(this.value)" > Ubah </td>'
				str += '<td><button class="btn btn-danger text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_delete(this.value)"> Hapus </td>'
				str += '</tr>'
			}
			halaman = dataResponse.page
			halamanPerLembar = dataResponse.perPages
			pagingList(dataResponse)
			$('#isidata').html(str)
		}
	})
}

function pagingList(data) {
	var nextMove = halaman + 1 > data.total_pages - 1 ? data.total_pages - 1 : halaman + 1
	var prevMove = halaman - 1 < 0 ? 0 : halaman - 1
	var str = '<nav aria-label="Page navigation example">'
	str += '<ul class="pagination">'
	str += '<li class="page-item"><a class="page-link" onclick="fetchData(' + prevMove + ',' + halamanPerLembar + ')">Prev</a></li>'

	for (var i = 0; i < data.total_pages; i++) {
		var isActive = 'active'

		if (i == halaman) {
			str += '<li class="page-item ' + isActive + '"><a class="page-link" role ="button" onclick="fetchData(' + i + ',' + halamanPerLembar + ')">' + (i + 1) + '</a></li>'
		} else {
			str += '<li class="page-item"><a class="page-link" role ="button" onclick="fetchData(' + i + ',' + halamanPerLembar + ')">' + (i + 1) + '</a></li>'
		}
	}

	str += '<li class="page-item"><a class="page-link" onclick="fetchData(' + nextMove + ',' + halamanPerLembar + ')">Next</a></li>'
	str += '</ul>'
	str += '</nav>'

	$('#pagination').html(str)
}

function createNew() {
	var str = "<form>"
	str += "<div class='form-group'>"
	str += "<label>Nama Spesialis</label>"
	str += "<input type='text' class='form-control' id='spesialisName'>"
	str += "</div>"
	str += "</form>"
	$('.modal-title').html('Tambah Spesialis')
	$('.modal-body').html(str)
	$('#modal').modal('show')
	$('#btnSilang').on('click', hide)
	$('#btnCancel').html('Batal').on('click', hide)
	$('#btnSubmit').html('Tambah').off('click').on('click', saveSpesialis)
}

function hide() {
	$('#modal').modal('hide')
}

function saveSpesialis() {
	var spesialisName = $('#spesialisName').val()
	var formData = '{'
	formData += '"name" : "' + spesialisName + '"'
	formData += '}'

	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis/add',
		type: 'post',
		contentType: 'application/json',
		data: formData,
		success: function(response) {
			if(spesialisName == ""){
				alert("Tolong Isi Nama Spesialis")
			}
			else if (response == "Data Sudah Ada") {
				alert("Data Sudah Ada")
			} else {
				$('#modal').modal('toggle')
				if (isSearch == true) {
					searchSpesialis(search, halaman, halamanPerLembar)
				} else {
					fetchData(halaman, halamanPerLembar)
				}

			}
		}
	})
}

function f_delete(id) {
	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = '<center><h4>Apa anda yakin ingin menghapus</h4><h5>"' + data.name + '"<h5></center>'
			$('.modal-body').html(str)
			$('#btnSubmit').html('Delete').off('click').on('click', function() { deleteSpesialis(data.id) })
			$('#btnCancel').addClass('btn btn-danger')
			$('.modal-title').html('Delete')
			$('#modal').modal('show')
		}
	})

}

function deleteSpesialis(id) {
	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis/delete/' + id,
		type: 'put',
		contentType: 'application/json',
		success: function() {
			$('#modal').modal('toggle')
			fetchData(halaman, halamanPerLembar)
		}
	})
}

function f_Edit(id) {
	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis/' + id,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var str = '<form>'
			str += '<div class="form-group">'
			str += '<label>Nama Spesialis</label>'
			str += '<input type="text" class="form-control" value="' + data.name + '" id="spesialisName">'
			str += '</div>'
			str += '</form>'
			$('.modal-body').html(str)
			$('#btnSubmit').html('Ubah Data').off('click').on('click', function() { editSpesialis(data.id) })
			$('#btnCancel').addClass('btn btn-danger')
			$('.modal-title').html('Ubah Data Spesialis Dokter')
			$('#modal').modal('show')
		}
	})
}

function editSpesialis(id) {
	var formdata = '{'
	formdata += '"id" : ' + id + ','
	formdata += '"name" : "' + $('#spesialisName').val() + '"'
	formdata += '}'
	$.ajax({
		url: 'http://localhost:8888/dokter/spesialis/edit',
		type: 'put',
		contentType: 'application/json',
		data: formdata,
		success: function() {
			$('#modal').modal('toggle')
			fetchData(halaman, halamanPerLembar)
		}
	})
}


function searchSpesialis(value, page, perPages) {
	search = value
	halaman = page
	halamanPerLembar = perPages
	isSearch = true
	if (value == "") {
		isSearch = false
	}
	//search = encodeURIComponent(value)
	console.log({value})
	console.log(search)
	$.ajax({
		url: `http://localhost:8888/dokter/spesialis/pagingSearch?keyword=${search}&page=${page}&perPages=${perPages}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataResponse) {
			var str = ''
			for (var i = 0; i < dataResponse.data.length; i++) {
				str += '<tr>'
				str += '<td class="pl-5">' + dataResponse.data[i].name + '</td>'
				str += '<td><button class="btn btn-primary text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_Edit(this.value)" > Ubah </td>'
				str += '<td><button class="btn btn-danger text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_delete(this.value)"> Hapus </td>'
				str += '</tr>'
			}

			pagingListSearch(dataResponse)
			$('#isidata').html(str)
		}
	})
}

function searchSpesialisnext(page, perPages) {
	//search = encodeURIComponent(value)
	$.ajax({
		url: `http://localhost:8888/dokter/spesialis/pagingSearch?keyword=${search}&page=${page}&perPages=${perPages}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataResponse) {
			var str = ''
			for (var i = 0; i < dataResponse.data.length; i++) {
				str += '<tr>'
				str += '<td class="pl-5">' + dataResponse.data[i].name + '</td>'
				str += '<td><button class="btn btn-primary text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_Edit(this.value)" > Ubah </td>'
				str += '<td><button class="btn btn-danger text-white form-control" value="' + dataResponse.data[i].id + '" onclick="f_delete(this.value)"> Hapus </td>'
				str += '</tr>'
			}

			pagingListSearch(dataResponse)
			$('#isidata').html(str)
		}
	})
}

function pagingListSearch(data) {
	var nextMove = data.page + 1 > data.total_pages - 1 ? data.total_pages - 1 : halaman + 1
	var prevMove = data.page - 1 < 0 ? 0 : data.page - 1
	//val = encodeURI(val)
	var str = '<nav aria-label="Page navigation example">'
	str += '<ul class="pagination">'
	str += '<li class="page-item"><a class="page-link" onclick=searchSpesialisnext('+ prevMove +','+halamanPerLembar+')>Prev</a></li>'

	for (var i = 0; i < data.total_pages; i++) {
		var isActive = 'active'
		if (i == data.page) {
			str += '<li class="page-item ' + isActive + '"><a class="page-link" onclick=searchSpesialisnext('+ i +','+halamanPerLembar +')>' + (i + 1) + '</a></li>'
		} else {
			str += '<li class="page-item"><a class="page-link" role onclick=searchSpesialisnext('+ i +','+halamanPerLembar+')>' + (i + 1) + '</a></li>'
		}
	}

	str += '<li class="page-item"><a class="page-link" onclick=searchSpesialisnext('+ nextMove +','+halamanPerLembar +')>Next</a></li>'
	str += '</ul>'
	str += '</nav>'

	$('#pagination').html(str)
}


