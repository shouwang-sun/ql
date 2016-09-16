$(function(){
	getStructureWarningResultListByPage(true,1);
	getByBridgeListByPage();
	
	$("#show-structureWarningDealResult").on('change',function(){
		if($(this).val() != "1"){
			 $("#show-structureWarningDescription").prop("disabled",true);
		 }else{
			 $("#show-structureWarningDescription").prop("disabled",false);
		 }
	})
});


function getByBridgeListByPage(){
	var index = 0;
	var pageSize = pageSizeLlong;
	commonDataLoader(null,resInterface.getBridgeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "<option value='-1'>请选择桥梁</option>";
		var str = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				sel +="<option  value="+valArr[i].id+" >"+valArr[i].name+"</option>";
			}
		} 
		$('#show-bridgeList-securityMonitoring').html(sel);
	})
 } 

 function getStructureWarningResultListByPage(flag,page){
	var startTime = $.trim($("#start-date").val()) != "" ? $.trim($("#start-date").val()): "-1";
	var endTime = $.trim($('#end-date').val()) != "" ? $.trim($('#end-date').val()): "-1";
	var dealResult = $.trim($("#show-dealResult-securityMonitoring").val()) != "-1" ? $.trim($("#show-dealResult-securityMonitoring").val()) : "-1";
	var bridgeId = $('#show-bridgeList-securityMonitoring').val();
	var params = {
			"createTime":startTime.toString(),
			"endTime":endTime.toString(),
			"bridgeId":bridgeId,
			"dealResult":dealResult
	};
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".pagination")});
	var index = (page - 1) * 10;
	var pageSize = pageSizeTen;
	$('#show-result').html("");
	commonDataLoader(JSON.stringify(params),resInterface.getStructureWarningResultList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str= "";
		var alertLevel = "";
		var tr = "";
		var typeStyle="";
		var dealResult = "";
		var structureName = "";
		if(valArr.length > 0){
			str = "";
			for(var i=0;i<valArr.length;i++){
				structureName = valArr[i].structureWarning != null ? valArr[i].structureWarning.name : "";
				switch(valArr[i].level){
					case 1:
						alertLevel = "<b class='yellow'>黄色</b>";
						break;
					case 2:
						alertLevel = "<b class='red'>红色</b>";
						break;
				}
				switch(valArr[i].dealResult){
					case 0:
						dealResult = "未处理";
						break;
					case 1:
						dealResult = "已处理";
						break;
					case 2:
						dealResult = "处理中";
						break;
				}
				
				if(valArr[i].logicGroupId != null){
					if(valArr[i].logicGroup  && valArr[i].logicGroup.name && valArr[i].logicGroupOutput && valArr[i].logicGroupOutput.name){
						typeStyle = valArr[i].logicGroup.name + "/" + valArr[i].logicGroupOutput.name;
					}
				}else{
					if(valArr[i].sensor  && valArr[i].sensor.name && valArr[i].sensorChannel && valArr[i].sensorChannel.name){
						typeStyle = valArr[i].sensor.name + "/" + valArr[i].sensorChannel.name;
					}
				}
				str = '<tr cid='+valArr[i].id+'>'+
							'<td><a href="javascript:void(0)" data-toggle="modal" data-target="#show-securityMonitoringInfo"  onclick="showResultDetail(this)">' + structureName +'<a/></td>'+
							'<td>'+typeStyle+'</td>' +
							'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>'+ 
							'<td>'+alertLevel+'</td>'+ 
							'<td>'+dealResult+'</td>'+ 
						'</tr>';
				tr = $(str).data("data",valArr[i]);
				$('#show-result').append(tr);
			}
		}else{
			$('#show-result').html("没有数据");
		} 
		
		if(flag){
			if(data.rsCount > 10){
				$(".pagination").removeClass('hide');
			}else{
				$(".pagination").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".pagination"),event:"getStructureWarningResultListByPage(false,"});
		} 
	});
}

 function showResultDetail(that){
	 var data = $(that).closest('tr').data("data");
	 var typeStyle = "";
	  if(data.logicGroupId != null){
			typeStyle ="逻辑组/" + data.logicGroup.name;
		}else{
			typeStyle ="观测点通道/" + data.sensorChannel.name;
		}
	 var structureWarningName = data.structureWarning.name;
	 var threshold = data.threshold;
	 var value = data.value;
	 var startTime = dateFormat(data.startTime);
	 var lastUpdateTime = dateFormat(data.lastUpdateTime);
	 var dealResult = data.dealResult;
	 var description = data.description;
	 var id = data.id;
	 $("#show-result-detail").attr('cid',id);
	 $("#show-structureWarningName").text(structureWarningName);
	 $("#show-structureWarningType").text(typeStyle);
	 $("#show-structureWarningThreshold").text(threshold);
	 $("#show-structureWarningValue").text((value).toFixed(2));
	 $("#show-structureWarningStartTime").text(startTime);
	 $("#show-structureWarningLastUpdateTime").text(lastUpdateTime);
	 $("#show-structureWarningDealResult").val(dealResult);
	 $("#show-structureWarningDescription").val(description);
	 if($("#show-structureWarningDealResult").val() != "1"){
		 $("#show-structureWarningDescription").prop("disabled",true);
	 }
 }
 
 function updateDealResultAndDescriptionById(){
	 var params  ={
			 id:parseInt($("#show-result-detail").attr('cid')),
			 description:$("#show-structureWarningDescription").val(),
			 dealResult:$("#show-structureWarningDealResult").val()
	 }
	 commonDataLoader(JSON.stringify(params),resInterface.updateStructureWarningResultById,function(data){
		 if(data){
			 getStructureWarningResultListByPage(true,1);
			 $("#show-securityMonitoringInfo").modal("hide");
		 }
	})
 }