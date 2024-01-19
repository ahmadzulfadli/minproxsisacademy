var currentPage = 0;
var size = 3;
var totalPage = 0;
var sort = "label";
var search = false;

$(function () {
    // f_all_data()
    f_data_pageable()
})

function f_data_pageable(){
    size = $('#rowPage').val()
    sort = $('#sort').val()
    $.ajax({
        url: 'http://localhost:8888/api/biodataaddress/pageable?pageNumber='+currentPage+'&pageSize='+ size +'&sort='+sort,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            console.log(data)
            var str =''
            for (var i = 0; i < data.data.length; i++) {
				console.log(data.data[i].id)
				console.log(data.data[i].label)
				console.log(data.data[i].recipient)
				console.log(data.data[i].recipientPhoneNumber)
				console.log(data.data[i].address)
				console.log(data.data[i].mLocation.name)
				console.log(data.data[i].mLocation.mLocation.mLocationLevel.abbreviation)
				console.log(data.data[i].mLocation.mLocation.name)
				str += '<tr>'
				str += '<td>'
				str += '<div class="form-check d-flex justify-content-center" style="margin-top: 15%;">'
				str += '<input class="form-check-input" type="checkbox" name="listCheck" id="check' + data.data[i].id + '" value=' + data.data[i].id + ' onclick="check(this.value)">'
				str += '</div>'
				str += '</td>'
				str += '<td id="datapasien">'
				str += '<h6 class="my-0" style="font-size: 15px; color: #4E73DF;">' + data.data[i].label + '</h6>'
				str += '<h6 class="my-0" style="font-size: 12px;">' + data.data[i].recipient + ', ' + data.data[i].recipientPhoneNumber + '</h6>'
				// str += '<h6 class="my-0" style="font-size: 10px;">' + data.data[i].address + ', ' + data.data[i].mLocation.name + ', ' + data.data[i].mLocation.mLocation.mLocationLevel.abbreviation + ','+data.data[i].mLocation.mLocation.name+'</h6>'
				str += '</td>'
				str += '<td id="aksi">'
				str += '<div style="padding-left: 50%;">'
				str += '<button class="badge btn-warning text-white mr-2" value="' + data.data[i].id + '" onclick="f_edit(this.value)">Edit</button>'
				str += '<button class="badge btn-danger" value="' + data.data[i].id + '" onclick="f_delete(this.value)">Delete</button>'
				str += '</div>'
				str += '</td>'
				str += '</tr>'

			}
			var sortData = '<label for="rowPage" class="ml-4 m-2">Urutkan</label>'
			sortData += '<select class="form-control" id="order" onchange="sortBy(' + page + ',' + per_page + ', this.value)">'
			sortData += '<option disabled>Berdasarkan</option>'
			sortData += '<option value="fullname" selected>Nama</option>'
			sortData += '<option value="dob">Umur</option>'
			sortData += '<option value="name">Relasi</option>'
			sortData += '</select>'


			//datapasien.data.filter((element) => { console.log(element) })
			var btnMultiDeletes = '<button class="btn btn-danger" onclick="f_multidelete(' + (datapasien.page - 1) + ', ' + datapasien.per_pages + ')" class="btn btn-primary mb-2" data-toggle="modal" data-target="#exampleModal">Hapus</button>'
			$('#btnMultiDelete').html(btnMultiDeletes)

			$('#sorting').html(sortData)
			//pagingList(datapasien)
			$('#isidata').html(str)
			//datapasien.data.filter((element) => { if (yourArray.includes(element.id.toString())) { $('#check' + element.id.toString())[0].checked = true } })

        }
    })
}