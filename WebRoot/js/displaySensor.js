

function getSensorListByPage(type,flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	var bridgeId = "";
	var sensorTypeId = "";
	var workSectionId = "";
	if(type == 1){
		gentPage({cur:curPage,p:$(".page-sensor")});
		$('#show-Sensor').html("");
		pageSize = pageSizeTen;
		bridgeId = ($("#show-bridgeList-sensor").val() != null  &&  $("#show-bridgeList-sensor").val() != -1) ? parseInt($("#show-bridgeList-sensor").val()) : null;
		sensorTypeId =  null;
		workSectionId = ($("#show-workSectionList-sensor").val() != null && $("#show-workSectionList-sensor").val() != -1) ? parseInt($("#show-workSectionList-sensor").val()) : null;
	}else if(type == 2){
		pageSize = pageSizeTen;
		bridgeId = ($("#show-add-bridge-sensor").val() != null  &&  $("#show-add-bridge-sensor").val() != -1) ? parseInt($("#show-add-bridge-sensor").val()) : null;
		sensorTypeId =  null;
		workSectionId = ($("#show-add-work-sensor").val() != null && $("#show-add-work-sensor").val() != -1) ? parseInt($("#show-add-work-sensor").val()) : null;
	}
	
	var params ={
			bridgeId:bridgeId,
			sensorTypeId:sensorTypeId,
			workSectionId:workSectionId
	};
	commonDataLoader(JSON.stringify(params),resInterface.getSensorList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		if(data.rsData.length > 0){
			var valArr = data.rsData;
			for(var i =0;i < valArr.length; i++){
				if(type == 1){
					str = '<tr cid='+valArr[i].id+'>'+
								"<td><input type='checkbox' onclick='judgeCheckTotal(\"#show-Sensor\",\"#sensor-update-btn\")'></td>"+
								'<td><a href="javascript:void(0)" data-toggle="modal" onclick="showSensorInfo(this)" data-target="#show-sensorInfo"  onclick="showWorkSensor(this)">'+valArr[i].name+'</a></td>' +
								'<td class="text-left width-4">'+valArr[i].description+'</td>'+
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
						tr = $(str).data("data",valArr[i]);
						$('#show-Sensor').append(tr);
				}
			}
		}else{
			if(type == 1){
				$("#show-Sensor").html("没有数据");
			}
		}
		 
		if(flag && type == 1){
			if(data.rsCount > 10){
				$(".page-sensor").removeClass('hide');
			}else{
				$(".page-sensor").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:$(".page-sensor"),event:"getSensorListByPage("+type+",false,"});
		}else if(type == 2){
			initImage(data.rsData,'#picture-area-sensor','point-sensor','con-sensor');
			var cid = parseInt($("#show-add-work-sensor").val());
		}
	})
}

function deleteSensorListByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-Sensor");
	 }
	 
	params = {
		"ids":JSON.stringify(ids)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteSensorByIds ,function(data){
		 if(data){
			 getSensorListByPage(1,true,1);
		 }
	 })
}

function deleteSensorTypeByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-add-sensorTypeList");
	 }
	 
	params = {
		"ids":JSON.stringify(ids)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteSensorTypeByIds ,function(data){
		 if(data){
			 getSensorTypeByPage(1,true,1);
			 getSensorTypeByPage(2);
		 }
	 })
}

function insertOrUpdateSensor(){
	var tag = parseInt($("#show-add-addSensor").attr('tag'));
	var name = $("#add-sensor-name").val();
//	var image = $("#uploader-sensor-file").attr('url');
	var positionX = $("#cur-pos-x-sensor").val();
	var positionY = $("#cur-pos-y-sensor").val();
	var description = $("#add-sensor-description").val();
	var bridgeId = parseInt($("#show-add-bridge-sensor").val());
	var workSectionId = parseInt($("#show-add-work-sensor").val());
	var sensorTypeId = parseInt($("#show-sensorTypeList").val());
	var params = {
			name:name,
			image:"",
			positionX:positionX,
			positionY:positionY,	
			description:description,
			bridgeId:bridgeId,
			workSectionId:workSectionId,
			sensorTypeId:sensorTypeId
	}
	if(tag == 2){
		params.id = parseInt($("#show-add-addSensor").attr('cid'));
		commonDataLoader(JSON.stringify(params),resInterface.updateSensorById ,function(data){
			 if(data){
				 getSensorListByPage(1,true,1);
				 $('#addOrUpdat-sensor').modal('hide');
				 insertOrUpdateChannelData();
			 }
		 })
	}else{
		commonDataLoader(JSON.stringify(params),resInterface.insertSensor ,function(data){
			 if(data.rsData.length > 0){
				 getSensorListByPage(1,true,1);
				 insertOrUpdateChannelData(data.rsData[0].id);
				 $('#addOrUpdat-sensor').modal('hide');
			 }
		 })
	}
}

function showSensorInfo(that){
	$("#sensor-update-btn").attr('cur-item-id',$(that).closest('tr').attr('cid'));
	$("#addOrUpdat-sensor").modal('show');
	$("#addOrUpdat-sensor").find('button,input,select,textarea').prop('disabled',true);
	$("#addOrUpdat-sensor").find('.modal-footer').addClass('hide');
	createOrUpdateSensor("#sensor-update-btn",true);
}

function insertOrUpdateSensorType(){
	var name = $("#add-sensorType-name").val();
	var description = $("#add-sensorType-description").val();
	var id = $("#show-add-sensorTypeList").attr('cid');
    var image = $("#uploader-sensorType-file").attr('url');
	var params = {
			name:name,
			description:description,
			image:image
	}
	
	if(id !=""){
		params.id = parseInt($("#show-add-sensorTypeList").attr('cid'));
		commonDataLoader(JSON.stringify(params),resInterface.updateSensorTypeById ,function(data){
			 if(data){
				 insertOrUpdateAattribute(3);
				 getSensorTypeByPage(1,true,1);
				 getSensorTypeByPage(2);
				 insertOrUpdateSensorTypeAttribute(3);
//				 $('#addOrUpdat-sensorType').modal('hide');
			 }
		 })
	}else{
		commonDataLoader(JSON.stringify(params),resInterface.insertSensorType ,function(data){
			 if(data.rsData.length > 0){
				 $("#show-addSensorType").attr('cid',data.rsData[0].id)
				 getSensorTypeByPage(1,true,1);
				 getSensorTypeByPage(2);
				 insertOrUpdateSensorTypeAttribute(3);
//				 $('#addOrUpdat-sensorType').modal('hide');
			 }
		 })
	}
}

$(function(){
	uploadFile("#uploader-sensorType-file",function(data){
		$("#show-sensorTypeImg").attr('src',data);
	});
	
	$("#show-bridgeList-sensor").on('change',function(){
		getWorkSectionListByPage(3);
	})
	
	$("#show-add-bridge-sensor").on('change',function(){
		$("#cur-point-sensor").remove();
		 if(parseInt($(this).val()) == -1){
				$("#show-add-bridge-sensor").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',true);
				$("#addOrUpdat-sensor").find('button').prop('disabled',true);
				$("#show-add-work-sensor").html('<option value="-1">请选择观测点</option>');
			}else{
				$("#show-add-work-sensor").prop('disabled',false);
				if($("#show-add-work-sensor").val() != "-1"){
					$("#show-add-bridge-sensor").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',false);
					$("#addOrUpdat-sensor").find('button').prop('disabled',false);
				}
				getWorkSectionListByPage(4);
		} 
	})
	
	$("#show-add-work-sensor").on('change',function(){
		if(parseInt($(this).val()) == -1){
			$("#show-add-work-sensor").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',true);
			$("#addOrUpdat-sensor").find('button').prop('disabled',true);
			$("#picture-area-sensor").attr('src','');
		}else{
			var cid = parseInt($("#show-add-work-sensor").val());
			$("#show-add-work-sensor").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',false);
			$("#addOrUpdat-sensor").find('button').prop('disabled',false);
			var url = $("#show-add-work-sensor").find('option[value='+cid+']').attr("url");
			$("#picture-area-sensor").attr('src',CUR_PROJECT_IP + url);
			getSensorListByPage(2);
		}
	})
	
	$("#show-sensorTypeList").on("change",function(){
		if(parseInt($(this).val()) == -2){
			$("#add-sensorType").modal('show');
			getSensorTypeByPage(1,true,1);
			$(this).val($(this).children('option:not(.hide):eq(0)').val());
		}
	})
	
	var params ={
			curDom:'#cur-point-sensor', //当前选中的节点
			curDom_:'cur-point-sensor',
			curPosX:'#cur-pos-x-sensor',//当前显示的坐标X
			curPosY:'#cur-pos-y-sensor',//挡墙显示的坐标Y
			imgArea:'#picture-area-sensor',//画布的Id
			movePx:'#pos-x-sensor', //移动时显示的坐标X
			movePy:'#pos-y-sensor'//移动时显示的坐标y
	}
  keyDownOnImg(params);
	
	$("#show-sensorTypeAttributeList").on('change',function(){
		
		if(parseInt($(this).val()) == -2){
			$("#add-sensorTypeAttribute").modal('show');
			getSensorTypeAttributeListByPage(true,1);
			$(this).val($(this).children('option:not(.hide):eq(0)').val());
		}
		
		if($("#show-sensorTypeAttribute").children('option[value="'+$(this).val()+'"]').attr('tp') == "Date"){
			$("#add-bridge-sensorTypeAttribute").children('td:eq(1)').children('input[date]').removeClass('hide');
			$("#add-bridge-sensorTypeAttribute").children('td:eq(1)').children('input[str]').addClass('hide');
		}else{
			$("#add-bridge-sensorTypeAttribute").children('td:eq(1)').children('input[date]').addClass('hide');
			$("#add-bridge-sensorTypeAttribute").children('td:eq(1)').children('input[str]').removeClass('hide');
		}
	});
})


function insertOrUpdateChannelData(sensorId){
	var arr = [];
	var obj = {};
	var delIdsArr = [];
	var updateArr = [];
	var insertArr = [];
	var totalParam = {};
 
 	if($("#show-add-sensorChannel").find('tr[ad]:not(".hide")').length > 0){
		 $.each($("#show-add-sensorChannel").find('tr[ad]:not(".hide")'),function(i,v){
			 obj = {
				name : 	$(v).children('td:eq(0)').find('input').val(),
				unit : $(v).children('td:eq(2)').find('input').val(),
				description : $(v).children('td:eq(1)').find('input').val(),
				sensorId:sensorId != null ? sensorId : parseInt($("#show-add-addSensor").attr('cid')),
				sensorTypeId:parseInt($("#show-sensorTypeList").val()),
				workSectionId:parseInt($("#show-add-work-sensor").val()),
				bridgeId:parseInt($("#show-add-bridge-sensor").val())
			 }
			 insertArr.push(obj);
		 })
	 }
 	
	 if($("#show-add-sensorChannel").find('tr[tp].hide').length > 0){
		 $.each($("#show-add-sensorChannel").find('tr[tp].hide'),function(i,v){
			 delIdsArr.push(parseInt($(v).attr('cid')))
		 })
		 
	 }
	 
	 if($("#show-add-sensorChannel").find('tr[tp]:not(".hide")').length > 0){
		$.each($("#show-add-sensorChannel").find('tr[tp]:not(".hide")'),function(i,v){
			obj = {
				id:parseInt($(v).attr('cid')),
				name : 	$(v).children('td:eq(0)').find('input').val(),
				unit : $(v).children('td:eq(2)').find('input').val(),
				description : $(v).children('td:eq(1)').find('input').val(),
				sensorId:parseInt($("#show-add-addSensor").attr('cid')),
				workSectionId:parseInt($("#show-add-work-sensor").val()),
				bridgeId:parseInt($("#show-add-bridge-sensor").val())
			}
			updateArr.push(obj);
		}) 
	 }
	 
	totalParam = {
		delIdsArr:JSON.stringify(delIdsArr),
		updateArr:JSON.stringify(updateArr),
		insertArr:JSON.stringify(insertArr)
	}
		
	commonDataLoader(JSON.stringify(totalParam),resInterface.changeSensorChannel,function(data){
		 if(data){
			 $('#addOrUpdat-sensor').modal('hide');
		 }
	 })
	
}

function createOrUpdateSensor(that,flag){
	$("#picture-area-sensor").attr('src',"");
	cleanValue("#show-add-addSensor","#display-sensor");
	$("#picture-area-sensor").nextAll('div').remove();
	$("#show-add-sensorChannel tr:gt(1)").remove();
	
	if(!flag){
		$("#addOrUpdat-sensor").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdat-sensor").find('.modal-footer').removeClass('hide');
//		$("#uploader-sensor-file").removeClass('hide');
		$("#sensor-update-btn").attr('Flag',1);
	}else{
//		$("#uploader-sensor-file").addClass('hide');
		$("#sensor-update-btn").attr('Flag',0);
	}
	
//	$("#show-sensorImg").attr('src',"");
	
	if(that  || flag ){
		setTimeout(function(){
			 $("#sensor-update-btn").prop('disabled',true);
		 },500);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-Sensor").find('tr[cid='+itemId+']').get(0)).data('data');
		var pointX = data.positionX;
		var pointY = data.positionY;
		var description = data.description;
		var name = data.name;
		var bridgeId = data.bridgeId;
		var sensorTypeId = data.sensorTypeId;
		var workSectionId = data.workSectionId;
		var image = data.image;
		var pointStr = "<div id='cur-point-sensor'  px="+pointX+" py="+pointY+"  title='x:"+pointX+" y:"+pointY+"' class='img-point' ></div>";
		$("#show-point-area-Sensor").append(pointStr);
		$('#cur-point-sensor').css({'position':'absolute','left':''+(pointX) +'px','top':''+(pointY + 24)+'px'});
//		$("#uploader-sensor-file").attr('url',image);
//		$("#show-sensorImg").attr('src',CUR_PROJECT_IP + image);
		$("#add-sensor-name").val(name);
		$("#add-sensor-description").val(description);
		$("#cur-pos-x-sensor").val(pointX);
		$("#cur-pos-y-sensor").val(pointY);
		
		$("#show-add-addSensor").attr({"tag":2,"cid":data.id});
		$("#picture-area-sensor").attr({'cid':data.id});
		$("#show-add-bridge-sensor").attr('cid',bridgeId);
		$("#show-add-work-sensor").attr('cid',workSectionId);
		$("#show-sensorTypeList").attr('cid',sensorTypeId);
		$("#show-add-bridge-sensor,#show-add-work-sensor").prop('disabled',true)
		var obj = {
				sensorId:data.id,
				sensorTypeId:sensorTypeId,
				bridgeId:bridgeId,
				workSectionId:workSectionId
		}
		getSensorChannelList(1,obj);
		getWorkSectionListByPage(4);
	}else{
		$("#show-add-bridge-sensor,#show-add-work-sensor").prop('disabled',false)
		$("#show-add-bridge-sensor").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',true);
		$("#show-add-addSensor").attr({"tag":1,"cid":""});
		$("#picture-area-sensor").attr('cid','');
		$("#show-add-bridge-sensor").attr('cid','');
		$("#show-add-work-sensor").attr('cid','');
		$("#show-sensorTypeList").attr('cid','');
		$("#show-add-sensorChannel").find('tr:eq(1)').removeClass('hide');
	}
	
	getByBridgeListByPage(5);
 	getSensorTypeByPage(2);
}

function getSensorChannelList(type,obj){
	var index = 0;
	var pageSize = pageSizeLlong;
	var params  = {
			sensorId:obj.sensorId,
			sensorTypeId:obj.sensorTypeId,
			bridgeId:obj.bridgeId,
			workSectionId:obj.workSectionId,
			logicGroupId:null
	}
	commonDataLoader(JSON.stringify(params),resInterface.getSensorChannelList + "?index="+index+"&pageSize="+pageSize+"",function(data){
		var str = "";
		if(data.rsData.length > 0){
			 data = data.rsData;
			 var selStr = "";
			 if($("#sensor-update-btn").attr('Flag') == 1){
				 selStr = '<td><button onclick="hideLine(this)" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button></td>';
			 }
			 $.each(data,function(i,v){
				 if(type == 1){
					 str +='<tr tp cid='+v.id+'>'+
					 			'<td><input type="text" class="form-control" value='+v.name+'></td>'+
					 			'<td><input type="text" class="form-control" value='+v.description+'></td>'+
					 			'<td><input type="text" class="form-control" value='+v.unit+'></td>'+
					 			selStr +
					 		'</tr>';
				 }else if(type == 2){
					 str +='<li cid='+v.id+' style="width:150px;" class="checkbox pull-left text-left"><label><input type="checkbox" onclick="">'+v.name+'</label></li>';
				 }
				 
			 })
			 if(type == 1){
				 $("#show-add-sensorChannel").append(str);
			 }
			 if(type == 2){
				 $("#show-add-sensorChannel-logicGroup").html(str);
			 }
		 }else {
			 if(type == 2){
				 $("#show-add-sensorChannel-logicGroup").html("没有数据");
			 }
		 }
		
		 if(type == 1){
			 if($("#show-add-sensorChannel").children('tr').length > 2){
				 $("#show-add-sensorChannel").find('tr:eq(1)').addClass('hide');
			 }else{
				 $("#show-add-sensorChannel").find('tr:eq(1)').removeClass('hide');
			 }
		 }
	 })
	
}

function addOrUpdateSensorType(that){
	cleanValue("#addOrUpdat-sensorType","#add-sensorType");
	$("#show-sensorTypeImg").attr('src',"");
	$("#show-addSensorType").find('tr:gt(3):not("#add-sensorType-attribute")').remove();
	if(that){
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-add-sensorTypeList").find('tr[cid='+itemId+']').get(0)).data('data');
		$("#show-add-sensorTypeList").attr('cid',data.id);
		$("#add-sensorType-name").val(data.name);
		$("#add-sensorType-description").val(data.description);
		if(data.image){
			$("#uploader-sensorType-file").attr('url',data.image);
			$("#show-sensorTypeImg").attr('src',CUR_PROJECT_IP + data.image);
		}
		initSensorTypeAttributeList(2,true);
	}else{
		initSensorTypeAttributeList(1);
		$("#show-add-sensorTypeList").attr('cid','');
	}
}

function getSensorTypeByPage(type,flag,page){
	 if(!page)page = 1;
	 var index = (page - 1) * 10;
	 page = page || 1;
	 curPage = page;
	 if(type == 1){
		 $("#show-add-sensorTypeList").html('');
		 gentPage({cur:curPage,p:$(".page-sensorType")});
		 pageSize = pageSizeTen;
	 }else if(type == 2){
		 pageSize = pageSizeLlong;
	 }
	
	 commonDataLoader(null,resInterface.getSensorTypeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		 var valArr = data.rsData;
		 var sel = "";
		 var strCheck = "";
		 var tr = "";
		 var str = "";
		 if(data.rsData.length > 0){
			  for(var i =0; i<valArr.length;i++){
				  if(type == 1){
					  str ="<tr cid="+valArr[i].id+">" +
				  		"<td><input type='checkbox' onclick='judgeCheckTotal(\"#show-add-sensorTypeList\",\"#sensorType-update-btn\")'></td>" +
				  		"<td>"+valArr[i].name+"</td>"+
				  		"<td>"+dateFormat(valArr[i].lastUpdateTime)+"</td>"+
				  		"</tr>";
					  tr = $(str).data("data",valArr[i]);
					  $("#show-add-sensorTypeList").append(tr);
				  }else if(type == 2){
					  sel +="<option value="+valArr[i].id+">"+valArr[i].name+"</option>";
				  }
			  }
			  if(type == 1){
				  
			  }else if(type == 2){
				  sel += '<option value="-2">==传感器类型管理==</option>';
				  $("#show-sensorTypeList").html(sel);
				  if($("#show-sensorTypeList").attr('cid') != ""){
					  $("#show-sensorTypeList").val(parseInt($("#show-sensorTypeList").attr('cid')));
				  }
			  }
		 }else{
			 if(type == 1){
				  $("#show-add-sensorTypeList").html("没有数据");
			  }else if(type == 2){
				  $("#show-sensorTypeList").html("<option value='-1'>请选择传感器类型</option><option value='-2'>==传感器类型管理==</option>");
			  }
		 }
		 
		 if(flag && type == 1){
				if(data.rsCount > 10){
					$(".page-sensorType").removeClass('hide');
				}else{
					$(".page-sensorType").addClass('hide');
				}
				gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:$(".page-sensorType"),event:"getSensorTypeByPage("+type+",false,"});
			}
	 })
}


function initSensorTypeAttributeList(type,flag){
	var index = 0;
	var pageSize = pageSizeLlong;
	var params = {}
	var sensorTypeId =  "-1";
	if(type == 1){
		//
	}else if(type == 2){
		sensorTypeId =  $("#show-add-sensorTypeList").attr('cid');
	}
	params = {sensorTypeId:sensorTypeId};
	commonDataLoader(JSON.stringify(params),resInterface.getSensorTypeAttributeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "";
		var str = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				if(type == 1){
					sel +="<option nm="+v.name+" value="+v.id+" tp="+v.type+">"+v.name+"</option>";
				}else if(type == 2){
//					if($("#sensorType-update-btn").attr('Flag') == "1"){
						str += "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td>"+setInputByType(valArr[i].type,valArr[i].value )+"</td><td><button onclick='removeLine(this,\"#show-sensorTypeAttributeList\")' class='btn btn-default'><span class='glyphicon glyphicon-minus'></span></button></td></tr>";
//					}else{
//						str += "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td>"+setInputByType(valArr[i].type,valArr[i].value )+"</td><td></td></tr>";
//					}
				}
			});
			if(type == 1){
				sel += '<option value="-2">==传感器属性管理==</option>';
				$("#show-sensorTypeAttributeList").html(sel);
				if(flag){
					$.each($("#show-addSensorType").find('tr[attr-init]'),function(i,v){
						$("#show-sensorTypeAttributeList").find('option[nm='+$(v).find('td:first').text()+']').addClass('hide');
					})
				}
			}else if(type == 2){
				$("#add-sensorType-attribute").before(str);
				initSensorTypeAttributeList(1,flag);
			}
//			$("#add-sensorType-attribute").find("#show-attributeListUpdate").html(str);
		}else{
			if(type == 1){
				sel += '<option value="-1" tp="-1">请选择自定义属性</option><option value="-2">==传感器属性管理==</option>';
				$("#show-sensorTypeAttributeList").html(sel);
			}else if(type == 2){
				initSensorTypeAttributeList(1);
			} 
		}
	})
}

function getSensorTypeAttributeListByPage(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-sensorTypeAttribute")});
	$("#show-add-sensorTypeAttributeList").html("");
	var sensorTypeId = "-1";
	params = {sensorTypeId:sensorTypeId}
	commonDataLoader(JSON.stringify(params),resInterface.getSensorTypeAttributeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		var tr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				str ="<tr cid="+v.id+" name="+v.name+"><td><input  type='checkbox' onclick='judgeCheckTotal(\"#show-add-sensorTypeAttributeList\",\"#sensorTypeAttribute-update-btn\")'></td><td>"+v.name+"</td><td>"+v.type+"</td></tr>";
				tr = $(str).data('data',v);
				$("#show-add-sensorTypeAttributeList").append(tr);
			});
		}else{
			$("#show-add-sensorTypeAttributeList").html("没有数据");
		}
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-sensorTypeAttribute").removeClass('hide');
			}else{
				$(".page-sensorTypeAttribute").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-sensorTypeAttribute"),event:"getSensorTypeAttributeListByPage(false,"});
		} 
	})
}

function deleteSensorTypeAttributeByName(Name){
	 var nameList =[];
	 var params = {};
	 if(Name){
		 nameList.push(id);
	 }else{
		 nameList = getDeleteNames("#show-add-sensorTypeAttributeList");
	 }
	params = {
		"nameList":JSON.stringify(nameList)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteSensorTypeAttributeByName ,function(data){
		 if(data){
			 initSensorTypeAttributeList(1);
			 getSensorTypeAttributeListByPage(true,1);
			 for(var i =0;i < nameList.length; i ++){
				 $("#show-addSensorType").find('tr[nm="'+nameList[i]+'"]').remove();
			 }
		 }
	 })
}



function addOrUpdateSensorTypeAttribute(that){
	cleanValue("#show-sensorTypeAttribute","#add-sensorTypeAttribute");
	if(that){//修改
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-add-sensorTypeAttributeList").find('tr[cid='+itemId+']').get(0)).data('data');
		$("#add-sensorTypeAttribute-name").val(data.name);
		$("#show-sensorTypeAttribute").attr("originName",data.name);
		$("#show-sensorTypeAttributeTypeList").val(data.type);
	}else{
		$("#show-sensorTypeAttributeTypeList").val("-1");
		$("#show-add-sensorTypeAttributeList").attr('cid',"");
		$("#show-sensorTypeAttribute").attr("originName","");
	}
}

function insertOrUpdateSensorTypeAttribute(type){
	var delIdsArr = [];
	var updateArr = [];
	var insertArr = [];
	var singleParam = {};
	var totalParam = {};
	if(type == 1){
		var originName = $("#show-sensorTypeAttribute").attr("originName");
		var wordType = $("#show-sensorTypeAttributeTypeList").val();
		var name = $("#add-sensorTypeAttribute-name").val();
		singleParam = {
			name:name,
			type:wordType,
			value:null,
			sensorTypeId:"-1"
		};
		
		if(originName != ""){
			singleParam.originName = $.trim(originName);
			type = 2;
		} 
	}else if(type == 3){
		if($("#show-addSensorType").find('tr.hide').length > 0){
			$.each($("#show-addSensorType").find('tr.hide'),function(i,v){
				delIdsArr.push(parseInt($(v).attr("cid")));
			})
		}
		
		if($("#show-addSensorType").find('tr[attr-init]:not(".hide")').length > 0){
			var obj = {};
			$.each($("#show-addSensorType").find('tr[attr-init]:not(".hide")'),function(i,v){
				obj = {
					id:parseInt($(v).attr("cid")),
					value:$(v).children('td:eq(1)').find('input').val() 		
				}
				updateArr.push(obj);
			})
		}
		
		if($("#show-addSensorType").find('tr[ad]').length > 0){
			 var obj = {};
			 var sensorTypeId = parseInt($("#show-add-sensorTypeList").attr("cid"));
			 $.each($("#show-addSensorType").find('tr[ad]'),function(i,v){
				 obj ={
					name:$(v).children('td:eq(0)').text(),
					type:$(v).attr('tp'),
					value:$(v).children('td:eq(1)').find('input').val(),
					sensorTypeId:sensorTypeId
				 }
				 insertArr.push(obj);
			 })
		}
	}
	totalParam = {
			delIdsArr:JSON.stringify(delIdsArr),
			updateArr:JSON.stringify(updateArr),
			insertArr:JSON.stringify(insertArr),
			singleParam:JSON.stringify(singleParam)
	}
	commonDataLoader(JSON.stringify(totalParam),resInterface.changeSensorTypeAttribute + "?type="+type+"",function(data){
		if((type == 1 || type == 2 )&& data){
			$('#addOrUpdat-sensorTypeAttribute').modal('hide');
			initSensorTypeAttributeList(1);
			getSensorTypeAttributeListByPage(true,1);
			if(type == 2){
				$("#show-addSensorType").find('tr[nm="'+originName+'"]').attr('nm',name).children('td:first').text(name);
			}
		}else if (type == 3){
			$("#addOrUpdat-sensorType").modal("hide");
		}
	});
}