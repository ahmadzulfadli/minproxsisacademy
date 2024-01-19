var currentPage = 0;
var size = 3;
var totalPage = 0;
var order = "ASC";

var isSearch = false

$(function () {
    f_data_pageable()
})

function action(){
    if(search){
        f_data_search()
    }else{
        f_data_pageable()
    }
}

function f_all_data() {
    $.ajax({
        url: 'http://localhost:8888/api/paymentmethod/showall',
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            for (var i = 0; i < data.length; i++) {
                str += '<tr>'
                str += '<td>'+ data[i].name +'</td>'
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

function f_data_search(){
    var formdata = ""
    formdata = $('#search').val()

    size = $('#rowPage').val()
    order = $('#order').val()

    if (formdata == ""){
        isSearch = false
    }else{
        isSearch = true
    }

    $.ajax({
        url: 'http://localhost:8888/api/courier/show/search?name='+formdata+'&pageNumber='+currentPage+'&pageSize='+size+'&order='+order,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            totalPage = data.totalPages - 1

            // cek data
            if (data.data.length == 0) {
                str += '<td colspan=3>Data tidak ditemukan</td>'
            }

            for (var i = 0; i < data.data.length; i++) {
                var item = data.data[i]
                str += '<tr>'
                str += '<td>'+ item.name +'</td>'
                if (item.mUserModify == null) {
                    str += '<td> - </td>'
                }else{
                    str += '<td>'+ item.mUserModify.mBiodata.fullname +'</td>'
                }
                str += '<td style="text-align: center;">'
                str += '<button class="btn btn-warning mr-2" value='+ item.id +' onclick="f_edit(this.value)">Ubah</button>'
                str += '<button class="btn btn-danger" value='+ item.id +' onclick="f_delete(this.value)">Hapus</button>'
                str += '</td>'
                str += '</tr>'
            }

            if(data.data.length > 0){
                $('.fitur').show()
            }else{
                $('.fitur').hide()
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
                if (i === currentPage) {
                    strPage += '<li class="page-item">';
                    strPage += '<button class="btn btn-primary" id="btnPage" onclick="page_number(' + i + ')">' + (i + 1) + ' <span class="sr-only">(current)</span></button>';
                    strPage += '</li>';
                } else{
                    strPage += '<li class="page-item">';
                    strPage += '<button class="page-link" id="btnPage" onclick="page_number(' + i + ')">' + (i + 1) + '</button>';
                    strPage += '</li>';
                }
            }
            strPage += '<li class="page-item">'
            strPage += '<button class="page-link" id="btnNext" onclick="page_next()" aria-label="Next">'
            strPage += '<span aria-hidden="true">&raquo;</span>'
            strPage += '</button>'
            strPage += '</li>'
            strPage += '</ul>'
            strPage += '</nav>'
            
            $('#pageable').html(strPage)
            $('#isiData').html(str)
            
            if (currentPage >= totalPage && totalPage != 0) {
                currentPage = totalPage - 1
            }
        }
    })

    if (formdata === "") {
        search = false
        $('.fitur').show()
        f_data_pageable()
    } else {
        search = true
    }
}

function f_data_pageable(){
    $('#search').val("")

    size = $('#rowPage').val()
    order = $('#order').val()
    
    $.ajax({
        url: 'http://localhost:8888/api/courier/show/pageable?pageNumber='+currentPage+'&pageSize='+size+'&order='+order,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ""
            totalPage = data.totalPages - 1
            
            for (var i = 0; i < data.pageSize; i++) {   
                if (data.data[i] == null) {
                    $.ajax({
                        url: 'http://localhost:8888/api/courier/showall',
                        type: 'get',
                        contentType: 'application/json',
                        success: function (data) {
                            for (var i = 0; i < data.length; i++) {
                                var item = data[i]
        
                                str += '<tr>'
                                str += '<td>'+ item.name +'</td>'
                                if (item.mUserModify == null) {
                                    str += '<td> - </td>'
                                }else{
                                    str += '<td>'+ item.mUserModify.mBiodata.fullname +'</td>'
                                }
                                str += '<td style="text-align: center;">'
                                str += '<button class="btn btn-warning mr-2" value='+ item.id +' onclick="f_edit(this.value)">Ubah</button>'
                                str += '<button class="btn btn-danger" value='+ item.id +' onclick="f_delete(this.value)">Hapus</button>'
                                str += '</td>'
                                str += '</tr>'
                            }
                        }
                    })
                }else{
                    var item = data.data[i]
                    str += '<tr>'
                    str += '<td>'+ item.name +'</td>'
                    if (item.mUserModify == null) {
                        str += '<td> - </td>'
                    }else{
                        str += '<td>'+ item.mUserModify.mBiodata.fullname +'</td>'
                    }
                    str += '<td style="text-align: center;">'
                    str += '<button class="btn btn-warning mr-2" value='+ item.id +' onclick="f_edit(this.value)">Ubah</button>'
                    str += '<button class="btn btn-danger" value='+ item.id +' onclick="f_delete(this.value)">Hapus</button>'
                    str += '</td>'
                    str += '</tr>'
                }
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
                if (i === currentPage) {
                    strPage += '<li class="page-item">';
                    strPage += '<button class="btn btn-primary" id="btnPage" onclick="page_number(' + i + ')">' + (i + 1) + ' <span class="sr-only">(current)</span></button>';
                    strPage += '</li>';
                } else{
                    strPage += '<li class="page-item">';
                    strPage += '<button class="page-link" id="btnPage" onclick="page_number(' + i + ')">' + (i + 1) + '</button>';
                    strPage += '</li>';
                }
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

            if (currentPage >= totalPage && totalPage != 0) {
                currentPage = totalPage

                console.log("masuk " + currentPage)
                f_data_pageable
            }

        }
    })
}

function page_previous() {
    currentPage -= 1;
    console.log(currentPage)
    if(currentPage < 0){
        currentPage = 0;
    }
    if (isSearch) {
        f_data_search()
    }else{
        f_data_pageable()
    }
}

function page_next() {
    currentPage += 1;
    console.log(currentPage)
    if (currentPage > totalPage) {
        currentPage = totalPage; 
    }
    if (isSearch) {
        f_data_search()
    }else{
        f_data_pageable()
    }
}

function page_number(page) {
    currentPage = page;
    if (isSearch) {
        f_data_search()
    }else{
        f_data_pageable()
    }
}

function f_create(){
    $('#btnSubmit').removeClass('btn-danger')
    $('#alert').empty()
    var str = '<div class="mb-3 row">'
    str += '<label for="name" class="col-sm-2 col-form-label">Name</label>'
    str += '<div class="col-sm-10">'
    str += '<input type="text" class="form-control" id="name">'
    str += '</div>'
    str += '</div>'

    $('.modal-title').html('Tambah Courier')
    $('.modal-body').html(str)
    $('#btnSubmit').html('Save').off('click').on('click', saveCreate)
    $('#modal').modal('show')
}

function saveCreate(){
    
    var formdata = '{'
    formdata += '"name":"'+ $('#name').val() +'"'
    formdata += '}'

    $.ajax({
        url: 'http://localhost:8888/api/courier/create',
        type:'post',
        contentType:'application/json',
        data: formdata,
        success:function(data){
            if(data.status === "failed"){
                $('#alert').empty()
                $('#alert').empty()
                var str = '<div class="alert alert-danger" role="alert">'
                str += data.message
                str += '</div>'
                $('#alert').append(str)
            }else{
                $('#modal').modal('toggle')
                f_data_pageable()
            }
        }
    })
}

function f_edit(id){
    $('#btnSubmit').removeClass('btn-danger')
    $('#alert').empty()
    $.ajax({
        url: 'http://localhost:8888/api/courier/show/'+id,
        type: 'get',
        contentType:'application/json',
        success:function(data){
            var item = data.data;
            var str = '<div class="mb-3 row">'
            str += '<label for="name" class="col-sm-2 col-form-label">Name</label>'
            str += '<div class="col-sm-10">'
            str += '<input type="text" class="form-control" id="name" value="'+ item.name +'">'
            str += '</div>'
            str += '</div>'

            $('.modal-title').html('Ubah Metode Pembayaran')
            $('.modal-body').html(str)
            $('#btnSubmit').html('Save Change').off('click').on('click', function(){saveEdit(item.id)})
            $('#modal').modal('show')
        }
    })
}

function saveEdit(id){
    var formdata = '{'
    formdata += '"id":'+ id +','
    formdata += '"name":"'+ $('#name').val() +'"'
    formdata += '}'

    $.ajax({
        url: 'http://localhost:8888/api/courier/update',
        type:'put',
        contentType:'application/json',
        data: formdata,
        success:function(data){
            if(data.status === "failed"){
                $('#alert').empty()
                var str = '<div class="alert alert-danger" role="alert">'
                str += data.message
                str += '</div>'
                $('#alert').append(str)
            }else{
                $('#modal').modal('toggle')
                f_data_pageable()
            }
        }
    })
}

function f_delete(id){
    $('#alert').empty()
    $.ajax({
        url: 'http://localhost:8888/api/courier/show/'+id,
        type: 'get',
        contentType:'application/json',
        success:function(data){
            $('#alert').empty()
            var item = data.data;
            var str = '<div>'
            str += '<h5>Anda akan menghapus kurir '+ item.name  +'?</h5>'
            str += '</div>'

            $('.modal-title').html('Hapus Kurir')
            $('.modal-body').html(str)
            $('#btnSubmit').html('Delete').addClass('btn btn-danger').off('click').on('click', function(){saveDelete(item.id)})
            $('#modal').modal('show')
        }
    })
}

function saveDelete(id){
    $.ajax({
        url:'http://localhost:8888/api/courier/delete/'+id,
        type:'put',
        contentType:'application/json',
        success:function(){
            $('#modal').modal('toggle')
            f_data_pageable()
        }
    })
}