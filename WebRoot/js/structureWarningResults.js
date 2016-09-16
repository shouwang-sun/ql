$(function(){
	$("#show-addbridgeList-structure").on('change',function(){
		$("#structure-logicGroup").attr('cid',"");
		$("#structure-logicGroupOut").attr('cid',"").val("-1");
		if(parseInt($(this).val()) == -1){
	    	$("#show-addbridgeList-structure").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',true);
	    	$("#structure-description").val("-1");
	    	$("#structure-sensor").val("-1");
		}else{
			$("#show-addbridgeList-structure").closest('tr').nextAll('tr').find('input,textArea,select').prop('disabled',false);
			getLogicGroupListByPage(2,true,1);
			getSensorListByPage(1);
		} 
	})
	
	$("#structure-logicGroup").on('change',function(){
		$("#structure-logicGroupOut").attr('cid',"");
		if(parseInt($(this).val()) == -1){
			$("#structure-logicGroupOut").val("-1");
		}else{
			getLogicOutputList(1);
		}
	})
	
	$("#structure-sensor").on('change',function(){
		if(parseInt($(this).val()) == -1){
			$("#structure-sensorChannel").val("-1");
		}else{
			var params  = {
					sensorId:parseInt($(this).val()),
					sensorTypeId:null,
					bridgeId:parseInt($('#show-addbridgeList-structure').val()) != -1 ? parseInt($('#show-addbridgeList-structure').val()):null,
					workSectionId:null,
					logicGroupId:null
			}
			getSensorChannelList(3,params);
		}
	})
})

function getSensorListByPage(type){
	var bridgeId = "";
	var sensorTypeId = "";
	var workSectionId = "";
	var index = 0;
	var pageSize = pageSizeLlong;
	if(type == 1){
		bridgeId = $("#show-addbridgeList-structure").val();
		sensorTypeId = null;
		workSectionId = null;
	} 
	var params ={
			bridgeId:bridgeId,
			sensorTypeId:sensorTypeId,
			workSectionId:workSectionId
	};
	
	commonDataLoader(JSON.stringify(params),resInterface.getSensorList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var sel = "<option value='-1'>请选择观测点</option>";
		if(data.rsData.length > 0){
			var valArr = data.rsData;
			for(var i =0;i < valArr.length; i++){
				if(type == 1){
					sel +="<option value="+valArr[i].id+">"+valArr[i].name+"</option>";
				}
			}
			if(type == 1){
				$("#structure-sensor").html(sel);
				if($("#structure-sensor").attr('cid') != ""){
					$("#structure-sensor").val(parseInt($("#structure-sensor").attr('cid')));
				}
			}
		}else{
			if(type == 1){
				$("#structure-sensor").html(sel);
			}
		}
	})
}

function changeStructureAddContent(that,flag){
	$(that).addClass('btn-primary').siblings().removeClass('btn-primary');
	$("#show-structureList").find('tr[tk]').addClass('hide');
	$("#show-structureList").find('tr['+flag+']').removeClass('hide');
}

 function getStructureListByPage(type,flag,page){
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-structure")});
	var index = (page - 1) * 10;
	var pageSize = pageSizeTen;
	$('#show-structure').html("");
	var bridgeId = $("#show-bridgeList-structure").val() != "-1" ? $("#show-bridgeList-structure").val() : "-1" ;
	var startTime = $.trim($("#start-date-structure").val()) != "" ? $.trim($("#start-date-structure").val()): "-1";
	var endTime = $.trim($('#end-date-structure').val()) != "" ? $.trim($('#end-date-structure').val()): "-1";
	
	var params  = {
			bridgeId:bridgeId,
			startTime:startTime,
			endTime:endTime
	}
	
	commonDataLoader(JSON.stringify(params),resInterface.getStructureWarningList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str= "";
		var url = "";
		var td = "";
		console.log(valArr);
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				str = '<tr cid='+valArr[i].id+' name='+valArr[i].name+'>'+
							'<td><input type="checkbox"  onclick="judgeCheckTotal(\'#show-structure\',\'#structure-update-btn\')"></td>'+
							'<td><a href="javascript:void(0)" onclick="showStructureInfo(this)">'+valArr[i].name+'<a></td>' +
							'<td>'+valArr[i].threshold+'</td>' +
							'<td class="text-left width-4">'+valArr[i].description+'</td>' +
							'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
						'</tr>';
				td = $(str).data("data",valArr[i]);
				$('#show-structure').append(td);
			}
		}else{
			$('#show-structure').html("没有数据");
		} 
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-structure").removeClass('hide');
			}else{
				$(".page-structure").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-structure"),event:"getStructureListByPage("+type+",false,"});
		} 
	});
}

function deleteStructureListByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-structure");
	 }
	 
	 commonDataLoader(JSON.stringify(ids),resInterface.deleteStructureWarningByIds ,function(data){
		 if(data){
			 getStructureListByPage(1,true,1);
		 }
	 })
}

function showStructureInfo(that){
	$("#structure-update-btn").attr('cur-item-id',$(that).closest('tr').attr('cid'));
	$("#addOrUpdat-structure").modal('show');
	$("#addOrUpdat-structure").find('button,input,select,textarea').prop('disabled',true);
	$("#addOrUpdat-structure").find('.modal-footer').addClass('hide');
	createOrUpdateStructure("#structure-update-btn",true);
}

function createOrUpdateStructure(that,flag){
	cleanValue("#addOrUpdat-structure","#structure-setting");
	$("#show-addbridgeList-structure").empty().append($("#show-bridgeList-structure").children().clone());
	
	$("#addOrUpdat-structure").find('.modal-header').children('button').prop('disabled',false);
	if(!flag){
		$("#addOrUpdat-structure").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdat-structure").find('.modal-footer').removeClass('hide');
	}
	
	if(that){//修改
		setTimeout(function(){
			 $("#structure-update-btn").prop('disabled',true);
		 },500);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-structure").find('tr[cid='+itemId+']').get(0)).data('data');
		var bridgeId = data.bridgeId;
		var logicGroupId = data.logicGroupId;
		var id = data.id;
		var outputId = data.outputId;
		var name = data.name;
		var threshold = data.threshold;
		threshold = threshold.split(",");
		var description = data.description;
		var sensorId = data.sensorId;
		var sensorChannelId = data.sensorChannelId;
		$("#structure-sensorChannel").attr('cid',sensorChannelId);
		$("#structure-name").val(name);
		$("#structure-threshold-1").val(threshold[0]);
		$("#structure-threshold-2").val(threshold[1]);
		$("#structure-threshold-3").val(threshold[2]);
		$("#structure-threshold-4").val(threshold[3]);
		$("#structure-description").val(description);
		$("#show-structure").attr("cid",id);
		$("#show-addbridgeList-structure").attr('cid',bridgeId);
		$("#show-addbridgeList-structure").val(bridgeId);
		$("#show-addbridgeList-structure").prop('disabled',true);
		if(logicGroupId != null){//逻辑组
			$("#choose-structure-type").children('button:eq(0)').addClass('btn-primary').prop('disabled',false).siblings('').removeClass('btn-primary').prop('disabled',true);
			$("#show-structureList").find('tr[logicGroupOut]').removeClass('hide');
			$("#show-structureList").find('tr[sensorChannel]').addClass('hide');
			getLogicGroupListByPage(2,true,1);
			getLogicOutputList(1);
			$("#structure-logicGroup").attr('cid',logicGroupId);
			$("#structure-logicGroupOut").attr('cid',outputId);
		}else{//观测点通道
			$("#choose-structure-type").children('button:eq(1)').addClass('btn-primary').prop('disabled',false).siblings('').removeClass('btn-primary').prop('disabled',true);;
			$("#show-structureList").find('tr[logicGroupOut]').addClass('hide');
			$("#show-structureList").find('tr[sensorChannel]').removeClass('hide');
			$("#structure-sensor").attr('cid',sensorId);
			var params  = {
					sensorId:sensorId != null ? parseInt(sensorId):null,
					sensorTypeId:null,
					bridgeId:bridgeId != null ? parseInt(bridgeId):null,
					workSectionId:null,
					logicGroupId:null
			}
			getSensorListByPage(1);
			getSensorChannelList(3,params);
		}
	}else{
		$("#show-addbridgeList-structure").prop('disabled',false);
		$("#structure-sensorChannel").attr('cid','');
		$("#choose-structure-type").children('button').prop('disabled',false);
		$("#choose-structure-type").children('button:eq(0)').removeClass('hide').addClass('btn-primary');
		$("#show-structureList").find('tr[logicGroupOut]').removeClass('hide');
		$("#show-structureList").find('tr[sensorChannel]').addClass('hide');
		$("#show-addbridgeList-structure").attr('cid',"");
		$("#structure-logicGroup").attr('cid',"");
		$("#structure-logicGroupOut").attr('cid',"");
		$("#show-structure").attr("cid","");
		$("#structure-sensor").attr('cid',"");
	}
}

function insertOrUpdateStructure(){
	var id = $("#show-structure").attr("cid");
	var bridgeId = $("#show-addbridgeList-structure").val() != "-1" ?  parseInt($("#show-addbridgeList-structure").val()) : null;
	var name = $("#structure-name").val();
	var threshold1 =parseFloat($("#structure-threshold-1").val());
	var threshold2 =parseFloat($("#structure-threshold-2").val());
	var threshold3 =parseFloat($("#structure-threshold-3").val());
	var threshold4 =parseFloat($("#structure-threshold-4").val());
	var threshold = threshold1 + "," + threshold2 + "," + threshold3 +","  + threshold4;
	var description = $("#structure-description").val() != "-1" ? $("#structure-description").val() : null;
	var typeFlag = false;
	
	var params = {
			name:name,
			threshold:threshold,
			description:description,
			bridgeId:bridgeId
	}
	
	if($("#choose-structure-type").children('button:eq(0)').hasClass('btn-primary')){//逻辑组输出
		var logicGroupId = $("#structure-logicGroup").val() != "-1" ? parseInt($("#structure-logicGroup").val()) : null; 
		var outputId = $("#structure-logicGroupOut").val() != "-1" ? parseInt($("#structure-logicGroupOut").val()) :null;
		params.logicGroupId =logicGroupId;
		params.outputId = outputId;
		params.sensorId = null;
	}else{//观测点通道
		typeFlag = true;
		var sensorId = $("#structure-sensor").val() != "-1" ? parseInt($("#structure-sensor").val()) : null; 
		var sensorChannelId = $("#structure-sensorChannel").val() != "-1" ? parseInt($("#structure-sensorChannel").val()) : null; 
		params.logicGroupId =null;
		params.outputId = null;
		params.sensorId = sensorId;
		params.sensorChannelId = sensorChannelId;
	}
	
	if(id != ""){  //修改
		params.id = parseInt($("#show-structure").attr("cid"));
		commonDataLoader(JSON.stringify(params),resInterface.updateStructureWarningById ,function(data){
			 if(data){
				 $('#addOrUpdat-structure').modal('hide');
				 getStructureListByPage(1,true,1);
			 }
		});
	}else{//添加
		commonDataLoader(JSON.stringify(params),resInterface.insertStructureWarning,function(data){
			 var valArr = data.rsData;
			 if(valArr.length > 0){
				 $('#addOrUpdat-structure').modal('hide');
				 getStructureListByPage(1,true,1);
			 }
		});
	}
}

function getLogicOutputList(type){
	var selStr = "<option value='-1'>请选择逻辑组输出</option>";
	var index = 0;
	var pageSize = pageSizeLlong;
	var logicGroupId = "-1";
	if(type == 1){
		logicGroupId  = $("#structure-logicGroup").val();
		 
	} 
		
	var params  = {
			logicGroupId:logicGroupId
	}
	commonDataLoader(JSON.stringify(params),resInterface.getLogicGroupOutputList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				selStr +="<option value="+valArr[i].id+" >"+valArr[i].name+"</option>";
			}
		} 
		if(type == 1){
			$("#structure-logicGroupOut").html(selStr);
			if($("#structure-logicGroupOut").attr('cid') != ""){
				$("#structure-logicGroupOut").val(parseInt($("#structure-logicGroupOut").attr('cid')));
			}
		} 
	})
}
 