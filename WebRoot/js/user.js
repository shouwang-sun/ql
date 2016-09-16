$(function(){
	getUserListByPage(1,true);
})

 function getUserListByPage(flag,page){
	 	page = page || 1;
		curPage = page;
		gentPage({cur:curPage,p:$(".page-user")});
		var index = (page - 1) * 10;
		$('#show-user').html("");
	    commonDataLoader(null,resInterface.getUserList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
			var valArr = data.rsData;
			var str= "";
			if(valArr.length > 0){
				for(var i=0;i<valArr.length;i++){
					str = '<tr cid='+valArr[i].id+'>'+
								'<td><input type="checkbox" onclick="judgeCheckTotal(\'#show-user\',\'#user-update-btn\')"></td>'+
								'<td>'+valArr[i].username+'</td>' +
								'<td>'+valArr[i].sex+'</td>' +
								'<td>'+valArr[i].email+'</td>' +
								'<td>'+valArr[i].phone+'</td>' +
								/*'<td>'+valArr[i].department_id+'</td>' +*/
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
					tr = $(str).data("data",valArr[i]);
					$('#show-user').append(tr);
				}
			}else{
				$('#show-user').html("没有数据");
			} 
			
			if(flag){
				if(data.rsCount > 10){
					$(".page-user").removeClass('hide');
				}else{
					$(".page-user").addClass('hide');
				}
				gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-user"),event:"getByUserByPage(false,"});
			} 
		});
 }

function createOrUpdateUser(that){
	 cleanValue("#addOrUpdat-user","#show-user");
	 if(that){
		 setTimeout(function(){
			 $("#user-update-btn").prop('disabled',true);
		 },500);
		 var itemId = $(that).attr('cur-item-id');
		 var data = $($("#show-user").find('tr[cid='+itemId+']').get(0)).data('data');
		 var id = data.id;
		 var username = data.username;
		 var password = data.password;
		 var sex = data.sex;
		 var email = data.email;
		 var phone = data.phone;
		 $("#show-user").attr('cid',id);
		 $("#add-user-username").val(username);
		 $("#add-user-password").val(password);
		 $("#add-user-sex").val(sex);
		 $("#add-user-email").val(email);
		 $("#add-user-phone").val(phone);
	 }else{
		 $("#add-user-sex").val("男");
		 $("#show-user").attr('cid','');
	 }
}

function insertOrUpdateUser(){
	var id = $("#show-user").attr('cid');
	var username = $("#add-user-username").val();
	var password = $("#add-user-password").val();
	var sex = $("#add-user-sex").val();
	var email = $("#add-user-email").val();
	var phone = $("#add-user-phone").val();
	var params = {
		username:username,
		password:password,
		sex:sex,
		email:email,
		phone:phone
	};
	if(id != ""){//修改
		params.id = parseInt(id);
		commonDataLoader(JSON.stringify(params),resInterface.updateUserById ,function(data){
			 if(data){
				 getUserListByPage(1,true);
				 $("#addOrUpdat-user").modal('hide');
			 }
		 })
	}else{
		commonDataLoader(JSON.stringify(params),resInterface.insertUser ,function(data){
			 if(data.rsData.length > 0){
				 getUserListByPage(1,true);
				 $("#addOrUpdat-user").modal('hide');
			 }
		 })
	}
}

function deleteByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-user");
	 }
	 commonDataLoader(JSON.stringify(ids),resInterface.deleteUserByIds ,function(data){
		 if(data){
			 getUserListByPage(1,true);
		 }
	 })
}