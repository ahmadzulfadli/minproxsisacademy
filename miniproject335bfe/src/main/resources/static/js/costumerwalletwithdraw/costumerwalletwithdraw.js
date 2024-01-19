var id = 2
var nominal = 0
var walletdefaultnominalId
var saveNominalHistoryTransaction = false
var countWrongPin = 0
var disabledTimeOut;
var balance = 0;


$(function () {
    f_all_data(id);
});

function f_all_data(id){
    $('#pilihan').html('')
    $('#pilihanCancel').prop("disabled", true)
    $('#pilihanSubmit').prop("disabled", true)

    $.ajax({
        url: 'http://localhost:8888/api/costumerwallet/show/'+id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            balance = data.data.balance
            var str = '<p>Saldo Anda Saat Ini Rp.'+ balance +'</p>'
            $('#saldo').html(str)
        }
    })

    $.ajax({
        url: 'http://localhost:8888/api/walletdefaultnominal/show',
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var nominalArray = [];
            for (var i = 0; i < data.data.length; i++) {
                var item = data.data[i].nominal;
                nominalArray.push(item)
            }

            $.ajax({
                url: 'http://localhost:8888/api/customernominal/show/'+id,
                type: 'get',
                contentType: 'application/json',
                success: function (data) {
                    for (var i = 0; i < data.data.length; i++) {
                        var item = data.data[i].nominal;
                        nominalArray.push(item)
                    }

                    nominalArray.sort(function(a, b){return a - b});

                    var str = ''
                    for (var i = 0; i < nominalArray.length; i++) {
                        if (nominalArray[i] <= balance) {
                            str += '<button class="btn btn-primary mt-2 mr-2" onclick="nominalSelected(' + id + ',' + nominalArray[i] + ')">' + nominalArray[i] + '</button>';
                        }
                    }
                    str+='<button class="btn btn-primary mr-2 mt-2" onclick="f_other_nominal()">Nominal Lainnya</button>';

                    $('#defaultNominal').html(str)
                }
            })
        }
    }) 
}

function nominalSelected(id, nominalSelected){
    walletdefaultnominalId = id
    nominal = nominalSelected
    var str = '<p>Anda akan melakukan penarikan saldo sebesar Rp.'+ nominal +'</p>'
    str+='<button class="btn btn-primary btn-lg btn-block mr-2 mt-2" id="btnNext" onclick="f_password()">Lanjut</button>';
    
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(id)})
    $('#pilihan').html(str)
}

function f_other_nominal(){
    $('#alert').empty()
    var str = '<div class="form-group">'
    str+='<label for="nominal">Isi Nominal Lain</label>'
    str+='<input type="number" class="form-control" id="nominalLain" placeholder="Nominal">'
    str+='</div>'

    $('.modal-title').html('Isi Nominal Lain')
    $('.modal-body').html(str)
    $('#btnCancel').html('Batal')
    $('#btnSubmit').show().html('OK').off('click').on('click', selectedOtherNominal)
    $('#modal').modal('show')
}

function selectedOtherNominal(){
    var otherNominal = $('#nominalLain').val()
    if(otherNominal > balance){
        $('#alert').empty()
        var str = '<div class="alert alert-danger" role="alert">'
        str += 'Balance Not Enough'
        str += '</div>'
        $('#alert').append(str)
        return
    }
    if (otherNominal <= 0 || otherNominal == null || otherNominal == '') {
        $('#alert').empty()
        var str = '<div class="alert alert-danger" role="alert">'
        str += 'The nominal cannot be negative or empty or zero'
        str += '</div>'
        $('#alert').append(str)
        return
    }
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(id)})
    $('#modal').modal('hide')
    nominal = otherNominal
    saveNominalHistoryTransaction = true
    walletdefaultnominalId = null
    var str = '<p>Anda akan melakukan penarikan saldo sebesar Rp.'+ nominal+'</p>'
    str+='<button class="btn btn-primary btn-lg btn-block mr-2 mt-2" id="btnNext" onclick="f_password()">Lanjut</button>';
    $('#pilihan').html(str)

}

function f_password(){
    $('#alert').empty()
    
    var str = '<div class="form-group">'
    str+='<label for="pin">Masukkan Password</label>'
    str+='<input type="password" class="form-control" id="pin">'
    str+='</div>'

    $('.modal-title').html('Masukkan PIN')
    $('.modal-body').html(str)
    $('#btnCancel').hide()
    $('#btnSubmit').show().html('OK').off('click').on('click', function(){passwordChek(id)})
    $('#modal').modal('show')
    
    if (countWrongPin >= 3) {
        $('#btnSubmit').hide()
        $('#pin').prop("disabled", true);
        disabledTimeOut = setTimeout(enableInputs, 3600000)
    }
}

function passwordChek(id){
    var pin = $('#pin').val()
    var formData = '{'
    formData += '"id":'+id+','
    formData += '"pin":"'+pin+'"'
    formData += '}'

    $.ajax({
        url: 'http://localhost:8888/api/costumerwallet/verify?nominal='+nominal,
        type: 'post',
        contentType: 'application/json',
        data: formData,
        success: function (data) {
            if(data.status === "failed"){
                $('#alert').empty()
                var str = '<div class="alert alert-danger" role="alert">'
                str += data.message
                str += '</div>'
                $('#alert').append(str)
                countWrongPin++
                if (countWrongPin >= 3) {
                    $('#btnSubmit').hide()
                    $('#pin').prop("disabled", true);
                    disabledTimeOut = setTimeout(enableInputs, 3600000)
                }
            }else{
                passwordSuccess()
            }
        }
    })
}

function enableInputs() {
    countWrongPin = 0
    $('#btnSubmit').removeAttr("disabled")
    $('#pin').prop('disabled', false);
    clearTimeout(disableTimeout);
}

function passwordSuccess(){

    $('#btnNext').prop("disabled", true)
    $('#btnSubmit').hide()
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(id)})
    $('#pilihanSubmit').removeAttr("disabled").off('click').on('click', withdrawTransaction)
    $('#modal').modal('toggle')
}

function withdrawTransaction(){
    var formData = '{'
    formData += '"costumerId":'+id+','
    formData += '"walletDefaultNominalId":'+walletdefaultnominalId+','
    formData += '"amount":'+nominal
    formData += '}'

    var formDataCustomNominal = '{'
    formDataCustomNominal += '"customerId":'+id+','
    formDataCustomNominal += '"nominal":'+nominal
    formDataCustomNominal += '}'

    $.ajax({
        url: 'http://localhost:8888/api/costumerwalletwithdraw/create?idCostumerWallet='+id,
        type: 'post',
        contentType: 'application/json',
        data: formData,
        success: function (data) {
            if (data.status == 'failed') {
                alert(data.message)
                return
            }

            if (saveNominalHistoryTransaction) {
                console.log('save')
                $.ajax({
                    url: 'http://localhost:8888/api/customernominal/create',
                    type: 'post',
                    contentType: 'application/json',
                    data: formDataCustomNominal,
                    success: function (data) {
                        console.log(data)
                    }
                })
                saveNominalHistoryTransaction = false
            }

            f_all_data(id)

            var str = '<div class="form-group">'
            str+='<label>Proses Penarikan Saldo Berhasil</label>'
            str+='<h5>Kode OTP Anda : '+data.data.otp+'</h5>'
            str+='<p>*OTP akan kadaluarsa setelah 1 jam</p>'
            str+='</div>'

            $('.modal-title').html('Tarik Saldo').css('color', 'green')
            $('.modal-body').html(str)

            $('#btnNext').hide()
            $('#btnSubmit').hide()
            $('#modal').modal('show')
        }
    })
}