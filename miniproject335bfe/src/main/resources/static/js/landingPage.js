$(function() {
	fetchdata(localStorage.getItem("id"))
	fetchProfil(localStorage.getItem("id"))
})
var userName = localStorage.getItem("name")
var userRole = localStorage.getItem("role")

function fetchdata(userId) {
	$.ajax({
		url: `http://localhost:8888/landingpage/menu/${userId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(data) {
			var biodataId = data.biodataId
			console.log(biodataId)
			var str = ""
			for (var i = 0; i < data.length; i++) {
				str += '<li class="nav-item">'
				if (data[i].parentId == null && data[i].url == "#") {
					str += '<a class="nav-link collapsed" href="' + data[i].url + '" data-toggle="collapse" data-target="#collapseDokter" aria-expanded="true" aria-controls="collapseUtilities">'
					str += data[i].smallIcon
					str += '<span>' + data[i].name + '</span>'
					str += '</a>'
					str += '<div id="collapseDokter" class="collapse" aria-labelledby="headingUtilities"data-parent="#accordionSidebar">'
					str += '<div class="bg-white py-2 collapse-inner rounded">'
					for (var j = 0; j < data.length; j++) {
						if (data[j].parentId == null) {
							continue
						}
						if (data[j].parentId.id == data[i].id) {
							str += '<a class="collapse-item" href="' + data[j].url + '">' + data[j].name + '</a>'
						}
					}
					str += '</div>'
					str += '</div>'
				} else if (data[i].url != "#" && data[i].parentId == null) {
					str += '<a class="nav-link" href="' + data[i].url + '">'
					str += data[i].smallIcon
					str += '<span class="mx-auto pl-2">' + data[i].name + '</span>'
					str += '</a>'
				}
				str += '</li>'
			}
			console.log(userName, userRole)
			$('#navBar').html(str)
		}
	})
}

function fetchProfil(userId) {

	$.ajax({
		url: `http://localhost:8888/user/${userId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataUser) {
			var bioId = dataUser.biodataId
			console.log(dataUser.biodataId)
			$.ajax({
				url: `http://localhost:8888/biodata/${bioId}`,
				type: 'get',
				contentType: 'application/json',
				success: function(dataBio) {
					var bio = ""
					bio += '<a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
					bio += '<span class="mr-2 d-none d-lg-inline text-gray-600 small">' + userName + '</span>'
					bio += '<img class="img-profile rounded-circle" src="' + dataBio.imagePath + '">'
					bio += '</a>'
					//console.log(dataBio.imagePath)
					console.log(dataBio)
					$.ajax({
						url: `http://localhost:8888/role/show/${userRole}`,
						type: 'get',
						contentType: 'application/json',
						success: function(dataRole) {
							//console.log(userId)
							//console.log(dataRole.data.name)
							if (dataRole.data.name == "Dokter") {
								bio += '<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">'
								bio += '<a class="dropdown-item" href="/profile/index">'
								bio += '<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>'
								bio += 'Profile'
								bio += '</a>'
								bio += '<div class="dropdown-item"></div>'
								bio += '<a class="dropdown-item" href="/index">'
								bio += '<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>'
								bio += 'Logout'
								bio += '</a>'
								bio += '</div>'
							} else {
								bio += '<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown" id"loginRole">'
								bio += '<a class="dropdown-item" href="/pasien/profile">'
								bio += '<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>'
								bio += 'Profile'
								bio += '</a>'
								bio += '<div class="dropdown-item"></div>'
								bio += '<a class="dropdown-item" href="/index">'
								bio += '<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>'
								bio += 'Logout'
								bio += '</a>'
								bio += '</div>'
							}
							console.log(bio)
							$('#biodata').html(bio)
						}
					})


				}
			})
		}
	})

}



