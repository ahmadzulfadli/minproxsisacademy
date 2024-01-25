$(function () {
    profileDoctor(3);
})
function profileDoctor(id) {
    // window.location.href = "/doctordetails/" + id;
    $.ajax({
        url: 'http://localhost:8888/doctor/' + id,
        type: 'get',
        contenttype: 'application/json',
        success: function (data) {
            var pageName = data.mBiodata.fullname
            var fullname = '<h3>' + data.mBiodata.fullname + '</h3>'
            var img = '<img src="' + data.mBiodata.imagePath + '" alt="avatar" class="rounded-circle img-fluid" style="width: 150px; height:150px;">'
            $('#pageName').html(pageName)
            $('#imgBiodata').html(img)
            $('#nama').html(fullname)

            // navContent
            getRiwayatPraktekAndSpesialization(id)
            getPriceTreatment(id)
            getActiveChat(id)

            // sideContent
            getMedicalTreatment(id)
            getEducation(id)

            // mainContent
            getPraktekLocation(id)

        }
    })
}

function calculateExperience(startDates, endDates) {
    let totalExperience = 0;
    for(let i = startDates.length -1; i >= 0; i--) {
        var start = startDates[i];
        var end =  endDates[i];
        
        
        if (i < startDates.length - 1) {
            var isBeforeDate = (dateA, dateB) => dateA < dateB;
            // console.log(isBeforeDate(new Date(start), new Date(endDates[i+1])))
            if (isBeforeDate(new Date(start), new Date(endDates[i+1]))) {
                start = endDates[i+1]
            }
        }
        
        start = new Date(start)
        end = new Date(end)
        console.log("start "+ i +": " + start)
        console.log("end   "+ i +": " + end)

        let experience = end.getTime() - start.getTime();

        if (experience != NaN) {
            totalExperience += experience;
        }

        console.log("experience "+ i +": " + experience)
        console.log("totalExperience "+ i +": " + totalExperience)
    }

    let totalYears = Math.floor(totalExperience / (1000 * 60 * 60 * 24 * 365));
    let totalMonths = Math.floor((totalExperience % (1000 * 60 * 60 * 24 * 365)) / (1000 * 60 * 60 * 24 * 30));
    let totalDays = Math.floor((totalExperience % (1000 * 60 * 60 * 24 * 30)) / (1000 * 60 * 60 * 24));

    // return {
    //     years: totalYears,
    //     months: totalMonths,
    //     days: totalDays
    // };

    return totalYears
}

function getRiwayatPraktekAndSpesialization(id) {
    $.ajax({
        url: 'http://localhost:8888/doctor/detail/location/' + id,
        type: 'get',
        contenttype: 'application/json',
        success: function (items) {
            data = items.data
            var str = ''

            let arrayStart = []
            let arrayEnd = []
            for (var i = 0; i < data.length; i++) {

                var start = data[i].startDate + ""
                var end = data[i].endDate + ""

                
                var endYear = end
                if (end == "null") {
                    end = new Date().toISOString()
                    endYear = 'sekarang'
                }

                start = start.substring(0, 10)
                end = end.substring(0, 10)

                // perhitungan pengalaman
                arrayStart.push(start)
                arrayEnd.push(end)
                
                start = start.substring(0, 4)
                end = end.substring(0, 4)

                str += '<p class="ml-4" style="font-size: medium;" id="riwayat">' + data[i].mMedicalFacility.name + ', ' + data[i].mMedicalFacility.mLocation.name + '</p>'
                str += '<div class="row ml-1">'
                str += '<div class="col-sm-6 d-flex justify-content-left">'
                str += '<p class="ml-4" style="font-size: small;">' + data[i].specialization + '</p>'
                str += '</div>'
                str += '<div class="col-sm-6 d-flex justify-content-end pr-5" >'
                if (endYear == "sekarang") {
                    str += '<p style="font-size: small;">' + start + ' - Sekarang</p>'
                }else{
                    str += '<p style="font-size: small;">' + start + '-' + end + '</p>'
                }
                str += '</div>'
                str += '</div>'
            }
            
            // Menghitung Pengalaman
            // console.log("career : " + dayCareer)
            var pengalaman = calculateExperience(arrayStart, arrayEnd)

            if (pengalaman == 0) {
                pengalaman = 1
            }

            var strSpesialization = '<h6>' + data[0].specialization + '<br>' + pengalaman + ' Tahun Pengalaman</h6>'

            $('#spesialisasi').html(strSpesialization)
            $('#riwayatPraktek').html(str)
        }
    })
}

function getActiveChat(id) {
    $.ajax({
        url: 'http://localhost:8888/doctor/detail/online/' + id,
        type: 'get',
        contenttype: 'application/json',
        success: function (data) {
            var active = false
            var str = ''

            var day;
            for (var i = 0; i < data.data.length; i++) {

                if (data.data[i].day == "Minggu") {
                    day = 0
                } else if (data.data[i].day == "Senin") {
                    day = 1
                } else if (data.data[i].day == "Selasa") {
                    day = 2
                } else if (data.data[i].day == "Rabu") {
                    day = 3
                } else if (data.data[i].day == "Kamis") {
                    day = 4
                } else if (data.data[i].day == "Jumat") {
                    day = 5
                } else if (data.data[i].day == "Sabtu") {
                    day = 6
                }
                

                
                if (day == new Date().getDay()) {
                    var timeStart = data.data[i].timeScheduleStart
                    var timeEnd = data.data[i].timeScheduleEnd
    
                    var timeStartHour = parseInt(timeStart.substring(0, 2))
                    var timeEndHour = parseInt(timeEnd.substring(0, 2))
                    var timeStartMinute = parseInt(timeStart.substring(3, 5))
                    var timeEndMinute = parseInt(timeEnd.substring(3, 5))

                    var getHours = parseInt(new Date().getHours())
                    var getMinutes = parseInt(new Date().getMinutes())

    
                    if (timeStartHour < getHours && timeEndHour > getHours) {
                        console.log("Online")
                        active = true
                        break
                    }else if(timeStartHour == getHours){
                        if(timeStartMinute <= getMinutes){
                            console.log("Online")
                            active = true
                            break
                        }
                    }else if(timeEndHour == getHours){
                        if(timeEndMinute >= getMinutes){
                            console.log("Online")
                            active = true
                            break
                        }
                    }
                }
            }
            if (active) {
                str += '<button class="btn btn-primary btn-block" id="btnChat">Chat Dokter</button>'
            }else{
                str += '<button class="btn btn-primary btn-block" id="btnChat" disabled>Chat Dokter</button>'
            }
            $('#onlineService').html(str)
        }
    })
}

function getPraktekLocation(id) {
    $.ajax({
        url: 'http://localhost:8888/doctor/detail/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ''
            for (var i = 0; i < data.detail.length; i++) {
                str += '<div class="card mb-4 p-4">'
                str += '<div class="row" id="lokasiPraktek">'
                str += '<div class="col-8">'
                str += '<p class="ml-2" style="font-size: 20px;" id="riwayat">' + data.detail[i].name + '</p>'
                str += '<div class="row ml-1">'
                str += '<div class="col-12">'
                str += '<p class="ml-2" style="font-size: 15px;" id="riwayat">Poliklinik Anak</p>'
                str += '<div class="row">'
                str += '<div class="col-12 m-0 pb-0 d-flex justify-content-left">'
                str += '<p class="ml-2" style="font-size: 15px;">'
                str += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt-fill" viewBox="0 0 16 16">'
                str += '<path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10m0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6" />'
                str += '</svg>'
                str += data.detail[i].fullAddress
                str += '</p>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '<div class="col-4">'
                str += '<p class="text-primary">Konsultasi mulai dari</p>'
                str += '<p class="text-primary">Rp. ' + data.detail[i].priceStartFrom + '</p>'
                str += '</div>'
                str += '</div>'
                str += '<hr>'
                str += '<div class="row">'
                str += '<div class="col-12">'
                str += '<div id="accordion">'
                str += '<div id="heading">'
                str += '<h5 class="mb-0">'
                str += '<a class="btn btn-link" data-toggle="collapse" data-target="#collapseOne' + i + '" aria-expanded="true" aria-controls="collapseOne">Lihat Jadwal Praktek <i class="fas fa-caret-down"></i>'
                str += '</a>'
                str += '</h5>'
                str += '</div>'
                str += '<div id="collapseOne' + i + '" class="collapse" aria-labelledby="heading" data-parent="#accordion">'
                str += '<div class="card-body">'
                str += '<div class="row align-items-center">'
                str += '<div class="col-9" id="jadwal">'
                for (var j = 0; j < data.schedule[i].length; j++) {
                    str += '<div class="row">'
                    str += '<div class="col-3">'
                    str += '<p>' + data.schedule[i][j].day + '</p>'
                    str += '</div>'
                    str += '<div class="col-9">'
                    str += '<p>' + data.schedule[i][j].timeScheduleStart + ' - ' + data.schedule[i][j].timeScheduleEnd + '</p>'
                    str += '</div>'
                    str += '</div>'
                }
                str += '</div>'
                str += '<div class="col-3">'
                str += '<button class="btn btn-primary">Buat Janji</button>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
                str += '</div>'
            }
            $('#lokasiPraktek').html(str)

            $('.collapse').on('show.bs.collapse', function () {
                $('.collapse').not($(this)).collapse('hide');
            });
        }
    })
}

function getEducation(id) {
    $.ajax({
        url: 'http://localhost:8888/education/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (data) {
            var str = ''
            for (var i = 0; i < data.length; i++) {

                str += '<p class="ml-4" style="font-size: medium;" id="riwayat">' + data[i].institutionName + '</p>'
                str += '<div class="row ml-1">'
                str += '<div class="col-sm-8 d-flex justify-content-left">'
                str += '<p class="ml-4" style="font-size: small;">' + data[i].major + '</p>'
                str += '</div>'
                str += '<div class="col-sm-4 d-flex justify-content-end pr-5" >'
                if (data[i].endYear == "") {
                    str += '<p style="font-size: small;">Sekarang</p>'
                } else {
                    str += '<p style="font-size: small;">' + data[i].endYear + '</p>'
                }

                str += '</div>'
                str += '</div>'
            }
            $('#education').html(str)
        }
    })
}

function getPriceTreatment(id) {
    $.ajax({
        url: 'http://localhost:8888/api/office/treatment/price/doctor/' + id,
        type: 'get',
        contenttype: 'application/json',
        success: function (data) {
            var str = '<h6>Rp. ' + data.data.price + '</h6>'
            $('#priceConsultasi').html(str)
        }
    })
}

function getMedicalTreatment(id) {
    $.ajax({
        url: 'http://localhost:8888/treatment/' + id,
        type: 'get',
        contentType: 'application/json',
        success: function (treatment) {
            var str = ""
            for (var i = 0; i < treatment.length; i++) {
                str += '<li>' + treatment[i].name + '</li>'
            }
            $('#tindakan').html(str)
        }
    })
}