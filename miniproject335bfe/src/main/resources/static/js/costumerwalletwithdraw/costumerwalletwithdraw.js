var idCustomerWallet = 1
var customerId;
var nominal = 0
var walletdefaultnominalId
var saveNominalHistoryTransaction = false
var countWrongPin = 0
var disabledTimeOut;
var balance = 0;


$(function () {
    profile(idCustomerWallet);
    f_all_data(idCustomerWallet);
});

function profile(idCustomerWallet) {
    $.ajax({
        url: 'http://localhost:8888/api/customer/' + idCustomerWallet,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            console.log(data.mbiodata.imagePath)
            var str = '<h4 class="card-title">' + data.mbiodata.fullname + '</h4>'

            var strImg = '<img src="'+ data.mbiodata.imagePath +'" class="card-img-top rounded-circle mx-auto my-3" style="width: 200px; height: 200px; object-fit: cover;">'

            $('#imgCustomer').html(strImg)
            $('#name').html(str)
        }
    })
}

function f_all_data(idCustomerWallet) {
    $('#pilihan').html('')
    $('#pilihanCancel').prop("disabled", true)
    $('#pilihanSubmit').prop("disabled", true)

    var nominalArray = [];

    $.ajax({
        url: 'http://localhost:8888/api/costumerwallet/show/' + idCustomerWallet,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            balance = data.data.balance;
            customerId = data.data.costumerId;

            var str = '<p>Saldo Anda Saat Ini Rp.' + balance + '</p>';
            $('#saldo').html(str);

            $.ajax({
                url: 'http://localhost:8888/api/walletdefaultnominal/show?balance=' + balance,
                type: 'get',
                contentType: 'application/json',
                success: function (data) {
                    for (var i = 0; i < data.data.length; i++) {
                        var item = {
                            id: data.data[i].id,
                            nominal: data.data[i].nominal,
                            keterangan: 0
                        };
                        nominalArray.push(item);
                    }

                    $.ajax({
                        url: 'http://localhost:8888/api/customernominal/show/' + customerId + '?balance=' + balance,
                        type: 'get',
                        contentType: 'application/json',
                        success: function (data) {
                            for (var i = 0; i < data.data.length; i++) {
                                var item = {
                                    id: data.data[i].id,
                                    nominal: data.data[i].nominal,
                                    keterangan: 1
                                };
                                nominalArray.push(item);
                            }

                            nominalArray.sort(function (a, b) {
                                return a.nominal - b.nominal;
                            });

                            var str = ''
                            for (var i = 0; i < nominalArray.length; i++) {
                                str += '<button class="btn btn-primary mt-2 mr-2" onclick="nominalSelected(' + nominalArray[i].id + ',' + nominalArray[i].nominal + ',' + nominalArray[i].keterangan + ')">' + nominalArray[i].nominal + '</button>';
                            }
                            str+='<button class="btn btn-primary mr-2 mt-2" onclick="f_other_nominal()">Nominal Lainnya</button>';

                            $('#defaultNominal').html(str)

                        }
                    });
                }
            });
        }
    });
}

function nominalSelected(idNominal, nominalSelected, keterangan){
    walletdefaultnominalId = null
    if(keterangan == 0){
        walletdefaultnominalId = idNominal
    }
    console.log(walletdefaultnominalId)
    nominal = nominalSelected
    var str = '<p>Anda akan melakukan penarikan saldo sebesar Rp.'+ nominal +'</p>'
    str+='<button class="btn btn-primary btn-lg btn-block mr-2 mt-2" id="btnNext" onclick="f_password()">Lanjut</button>';
    
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(idCustomerWallet)})
    $('#pilihan').html(str)
}

function f_other_nominal(){
    $('#alert').empty()
    var str = '<div class="form-group">'
    str+='<label for="nominal">Isi Nominal Lain</label>'
    str+='<input type="number" class="form-control" id="nominalLain" placeholder="Nominal">'
    str+='</div>'

    $('.modal-title').html('Isi Nominal Lain')
    $('.modal-body').html(str).css('color', 'gray')
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
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(idCustomerWallet)})
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
    $('.modal-body').html(str).css('color', 'gray')
    $('#btnCancel').hide()
    $('#btnSubmit').show().html('OK').off('click').on('click', function(){passwordChek(idCustomerWallet)})
    $('#modal').modal('show')
    
    if (countWrongPin >= 3) {
        $('#btnSubmit').hide()
        $('#pin').prop("disabled", true);
        disabledTimeOut = setTimeout(enableInputs, 3600000)
    }
}

function passwordChek(idCustomerWallet){
    var pin = $('#pin').val()
    var formData = '{'
    formData += '"id":'+idCustomerWallet+','
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
    $('#pilihanCancel').removeAttr("disabled").off('click').on('click', function(){f_all_data(idCustomerWallet)})
    $('#pilihanSubmit').removeAttr("disabled").off('click').on('click', withdrawTransaction)
    $('#modal').modal('toggle')
}

function withdrawTransaction(){
    var formData = '{'
    formData += '"costumerId":'+customerId+','
    formData += '"walletDefaultNominalId":'+walletdefaultnominalId+','
    formData += '"amount":'+nominal
    formData += '}'

    var formDataCustomNominal = '{'
    formDataCustomNominal += '"customerId":'+customerId+','
    formDataCustomNominal += '"nominal":'+nominal
    formDataCustomNominal += '}'

    $.ajax({
        url: 'http://localhost:8888/api/costumerwalletwithdraw/create?idCostumerWallet='+idCustomerWallet,
        type: 'post',
        contentType: 'application/json',
        data: formData,
        success: function (data) {
            if (data.status == 'failed') {
                $('#alert').empty()
                var str = '<div class="alert alert-danger" role="alert">'
                str += 'The nominal cannot be negative or empty or zero'
                str += '</div>'
                $('#alert').append(str)
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

            f_all_data(idCustomerWallet)

            $('#alert').empty()

            var str = '<div class="form-group">'
            str+='<label>Proses Penarikan Saldo Berhasil</label>'
            str+='<h5>Kode OTP Anda : '+data.data.otp+'</h5>'
            str+='<p>*OTP akan kadaluarsa setelah 1 jam</p>'
            str+='</div>'

            $('.modal-title').html('Tarik Saldo')
            $('.modal-body').html(str).css('color', 'green')

            $('#btnNext').hide()
            $('#btnSubmit').hide()
            $('#modal').modal('show')
        }
    })
}