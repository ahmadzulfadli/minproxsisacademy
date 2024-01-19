var currentPage = 0;
var size = 3;
var totalPage = 0;
var order = "ASC";
var isSearch = false;

var currentPageS = 0;
var totalPageS = 0;
var uId = 0;

$(function () {
    // f_all_data()
    if(localStorage.getItem("id")==null){
		alert("null id")
	}else{
		uId = localStorage.getItem("id")
	}
    f_data_pageable()
})
/*
function f_all_data() {
    $.ajax({
        url: 'http://localhost:8888/api/role/show',
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            for (var i = 0; i < data.length; i++) {
                str += '<tr>'
                str += '<td>'+ data[i].name +'</td>'
                str += '<td>'+ data[i].code +'</td>'
                str += '<td style="text-align: center;">'
                str += '<button class="btn btn-warning mr-2" value='+ data[i].id +' onclick="f_edit(this.value)">Ubah</button>'
                str += '<button class="btn btn-danger" value='+ data[i].id +' onclick="f_delete(this.value)">Hapus</button>'
                str += '</td>'
                str += '</tr>'
            }

            $('#isiData').html(str)
        }
    })
}
*/

function f_data_toggle(){
	if(isSearch){
		f_data_search()
	}else{
		f_data_pageable()
	}
}

function f_data_search(){
	isSearch = true
    var formdata = ""
    formdata = $('#search').val()
	size = $('#rowPage').val()
    order = $('#order').val()
    $.ajax({
        url: 'http://localhost:8888/role/showpage/search?pageNumber='+currentPageS+'&pageSize='+size+'&order='+order+'&name='+formdata,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            totalPage = data.totalPages - 1
            for (var i = 0; i < data.data.length; i++) {
                    var item = data.data[i]
                    str += '<tr>'
                    str += '<td>'+ item.name +'</td>'
                    str += '<td>'+ item.code +'</td>'
                    str += '<td style="text-align: center;">'
                    str += '<button class="btn btn-warning mr-2" value='+ item.id +' onclick="f_edit(this.value)">Ubah</button>'
                    str += '<button class="btn btn-danger" value='+ item.id +' onclick="f_delete(this.value)">Hapus</button>'
                    str += '</td>'
                    str += '</tr>'
            }
            
            var strPage = ""
            strPage += '<nav aria-label="Page navigation">'
            strPage += '<ul class="pagination" id="pagination">'
            strPage += '<li class="page-item">'
            strPage += '<button class="page-link" id="btnPrevious" onclick="page_previouss()" aria-label="Previous">'
            strPage += '<span aria-hidden="true">&laquo;</span>'
            strPage += '</button>'
            strPage += '</li>'
            for (var i = 0; i <= totalPage; i++) {
                strPage += '<li class="page-item">'
                strPage += '<button class="page-link" id="btnPage" onclick="page_numbers('+i+')">'+(i+1)+'</button>'
                strPage += '</li>'
            }
            strPage += '<li class="page-item">'
            strPage += '<button class="page-link" id="btnNext" onclick="page_nexts()" aria-label="Next">'
            strPage += '<span aria-hidden="true">&raquo;</span>'
            strPage += '</button>'
            strPage += '</li>'
            strPage += '</ul>'
            strPage += '</nav>'

            $('#isiData').html(str)
            $('#pageable').html(strPage)
        }
    })

    if(formdata === ""){
        $('.fitur').show()
        f_data_pageable()
        isSearch = false
    }
}

function f_data_pageable(){
    size = $('#rowPage').val()
    order = $('#order').val()
    console.log(order)
    $.ajax({
        url: 'http://localhost:8888/role/showpage?pageNumber='+currentPage+'&pageSize='+size+'&order='+order,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            totalPage = data.totalPages - 1
            for (var i = 0; i < data.data.length; i++) {
                    var item = data.data[i]
                    str += '<tr>'
                    str += '<td>'+ item.name +'</td>'
                    str += '<td>'+ item.code +'</td>'
                    str += '<td style="text-align: center;">'
                    str += '<button class="btn btn-warning mr-2" value='+ item.id +' onclick="f_edit(this.value)">Ubah</button>'
                    str += '<button class="btn btn-danger" value='+ item.id +' onclick="f_delete(this.value)">Hapus</button>'
                    str += '</td>'
                    str += '</tr>'
            }
            
            var strPage = ""
            strPage += '<nav aria-label="Page navigation">'
            strPage += '<ul class="pagination" id="pagination">'
            strPage += '<li class="page-item">'
            strPage += '<button class="page-link" id="btnPrevious" onclick="page_previous()" aria-label="Previous">'
            strPage += '<span aria-hidden="true">&laquo;</span>'
            strPage += '</button>'
            strPage += '</li>'
            for (var i = 0; i <= totalPage; i++) {
                strPage += '<li class="page-item">'
                strPage += '<button class="page-link" id="btnPage" onclick="page_number('+i+')">'+(i+1)+'</button>'
                strPage += '</li>'
            }
            strPage += '<li class="page-item">'
            strPage += '<button class="page-link" id="btnNext" onclick="page_next()" aria-label="Next">'
            strPage += '<span aria-hidden="true">&raquo;</span>'
            strPage += '</button>'
            strPage += '</li>'
            strPage += '</ul>'
            strPage += '</nav>'

            $('#isiData').html(str)
            $('#pageable').html(strPage)
        }
    })
}

function page_previous() {
    currentPage -= 1;
    if(currentPage < 0){
        currentPage = 0;
    }
    console.log(currentPage)
    f_data_pageable()
}

function page_next() {
    currentPage += 1;
    if (currentPage > totalPage) {
        currentPage = totalPage; 
    }
    console.log(currentPage)
    f_data_pageable()
}

function page_number(page) {
    currentPage = page;
    console.log(currentPage)
    f_data_pageable()

}

function page_previouss() {
    currentPageS -= 1;
    if(currentPageS < 0){
        currentPageS = 0;
    }
    console.log(currentPageS)
    f_data_search()
}

function page_nexts() {
    currentPageS += 1;
    if (currentPageS > totalPage) {
        currentPageS = totalPage; 
    }
    console.log(currentPageS)
    f_data_search()
}

function page_numbers(page) {
    currentPageS = page;
    console.log(currentPageS)
    f_data_search()

}

function f_create(){
    $('#btnSubmit').removeClass('btn-danger')
    var str = '<div class="alert alert-danger form-group" role="alert">'
	str += '</div>'
    str += '<div class="mb-3 row">'
	str += '</div>'
    str += '<label for="name" class="col-sm-2 col-form-label">Name</label>'
    str += '<div class="col-sm-10">'
    str += '<input type="text" class="form-control" id="name">'
    str += '</div>'
    str += '<label for="name" class="col-sm-2 col-form-label">Code</label>'
    str += '<div class="col-sm-10">'
    str += '<input type="text" class="form-control" id="code">'
    str += '</div>'
    str += '</div>'

    $('.modal-title').html('Tambah Role')
    $('.modal-body').html(str)
    $('#btnSubmit').html('Save').off('click').on('click', saveCreate)
    $('#modal').modal('show')
    $('.alert').hide()
}

function saveCreate(){
    var formdata = '{'
    formdata += '"name":"'+ $('#name').val() +'",'
    formdata += '"code":"'+ $('#code').val() +'"'
    formdata += '}'

    $.ajax({
        url: 'http://localhost:8888/role/add/'+uId,
        type:'post',
        contentType:'application/json',
        data: formdata,
        success:function(data){
            if(data.status === "failed"){
				$('.alert').html(data.message)
				$('.alert').show()
            }else{
                $('#modal').modal('toggle')
                f_data_pageable()
            }
        }
    })
}

function f_edit(id){
    $('#btnSubmit').removeClass('btn-danger')
    $.ajax({
        url: 'http://localhost:8888/role/show/'+id,
        type: 'get',
        contentType:'application/json',
        success:function(data){
            var item = data.data;
            var str = '<div class="alert alert-danger form-group" role="alert">'
			str += '</div>'
            str += '<div class="mb-3 row">'
            str += '<label for="name" class="col-sm-2 col-form-label">Name</label>'
            str += '<div class="col-sm-10">'
            str += '<input type="text" class="form-control" id="name" value="'+ item.name +'">'
            str += '</div>'
            str += '<label for="name" class="col-sm-2 col-form-label">Code</label>'
            str += '<div class="col-sm-10">'
            str += '<input type="text" class="form-control" id="code" value="'+ item.code +'">'
            str += '</div>'
            str += '</div>'

            $('.modal-title').html('Ubah Role')
            $('.modal-body').html(str)
            $('#btnSubmit').html('Save Change').off('click').on('click', function(){saveEdit(item.id)})
            $('#modal').modal('show')
            $('.alert').hide()
        }
    })
}

function saveEdit(id){
    var formdata = '{'
    formdata += '"name":"'+ $('#name').val() +'",'
    formdata += '"code":"'+ $('#code').val() +'"'
    formdata += '}'

    $.ajax({
        url: 'http://localhost:8888/role/edit/'+id+'/'+uId,
        type:'put',
        contentType:'application/json',
        data: formdata,
        success:function(data){
            if(data.status === "failed"){
                $('.alert').html(data.message)
				$('.alert').show()
            }else{
                $('#modal').modal('toggle')
                f_data_pageable()
            }
        }
    })
}

function f_delete(id){
    $.ajax({
        url: 'http://localhost:8888/role/show/'+id,
        type: 'get',
        contentType:'application/json',
        success:function(data){
            var item = data.data;
            var str = '<div>'
            str += '<h5>Anda akan menghapus role '+ item.name  +'?</h5>'
            str += '</div>'


            $('.modal-title').html('Hapus Role')
            $('.modal-body').html(str)
            $('#btnSubmit').html('Delete').addClass('btn btn-danger').off('click').on('click', function(){saveDelete(item.id)})
            $('#modal').modal('show')
        }
    })
}

function saveDelete(id){
    $.ajax({
        url:'http://localhost:8888/role/delete/'+id+'/'+uId,
        type:'put',
        contentType:'application/json',
        success:function(){
            $('#modal').modal('toggle')
            f_data_pageable()
        }
    })
}