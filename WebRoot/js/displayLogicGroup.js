var zTreeMenu = {};
var settingMenu = {
		view: {
			dblClickExpand: false,
			showLine: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		check: {
			enable: false
		},
		callback: {
			onClick: showLeftData
		}
};	


var zNodesMenu =[{ id:'#display-logicGroup', pId:0, name:'自定义逻辑组', isParent:false , open:false},
                 { id:'#structure-setting', pId:0, name:'预警结构设置', isParent:false , open:false},
                 { id:'#evaluation-preserve', pId:0, name:'评估体系维护', isParent:false , open:false}];

function showLeftData(event,treeId,treeNode){
	showOrHideDom(treeNode.id);
	 switch (treeNode.id) {
	 	case "#display-logicGroup":
			getByBridgeListByPage(1);
			getLogicGroupListByPage(1,true,1);
		break;	
	 	case "#structure-setting":
			getByBridgeListByPage(3);
			getStructureListByPage(1,true,1);
		break
		case "#evaluation-preserve":
			getByBridgeListByPage(4);
			getEvaluateListByPage(1,true,1);
		break;
	}
}

$(function(){
	zTree = $.fn.zTree.getZTreeObj("treeMenu");
	$.fn.zTree.init($("#treeMenu"), settingMenu, zNodesMenu);
	getByBridgeListByPage(1);
	getLogicGroupListByPage(1,true,1);
	uploadFile("#add-logicGroup-algorithm");
	
	$("#show-add-bridge-logicGroup").on('change',function(){
			$("#show-add-sensorChannel-timeIntervalUnit").val(1000);
		    if(parseInt($(this).val()) == -1){
		    	$("#show-add-bridge-logicGroup").closest('tr').nextAll('tr').find('input,textArea,select,button').prop('disabled',true);
			}else{
				$("#show-add-bridge-logicGroup").closest('tr').nextAll('tr').find('input,textArea,select,button').prop('disabled',false);
				var params  = {
						sensorId:null,
						sensorTypeId:null,
						bridgeId:parseInt($('#show-add-bridge-logicGroup').val()) != -1 ? parseInt($('#show-add-bridge-logicGroup').val()):null,
						workSectionId:null,
						logicGroupId:null
				}
				getSensorChannelList(1,params);
		} 
	})
})

function changeLogicGroupInYype(that){
	if($(that).val() == "1"){//常量
		$(that).closest('tr').attr('pp','constant');
		$(that).closest('tr').children('td:eq(2)').children('input').prop('disabled',false);
		$(that).closest('tr').children('td[type]').children('input').removeClass('hide');
		$(that).closest('tr').children('td[type]').children('select').addClass('hide');
	}else if($(that).val() == "2"){//观测点通道
		$(that).closest('tr').attr('pp','channel');
		$(that).closest('tr').children('td:eq(2)').children('input').prop('disabled',true);
		$(that).closest('tr').children('td[type]').children('input').addClass('hide');
		$(that).closest('tr').children('td[type]').children('select').removeClass('hide');
	}
}

function getSensorChannelList(type,obj){
	var index = 0;
	var pageSize = pageSizeLlong;
	var params  = {
			sensorId:obj.sensorId,
			sensorTypeId:obj.sensorTypeId,
			bridgeId:obj.bridgeId,
			workSectionId:obj.workSectionId,
			logicGroupId:obj.logicGroupId
	}
	commonDataLoader(JSON.stringify(params),resInterface.getSensorChannelList + "?index="+index+"&pageSize="+pageSize+"",function(data){
		var str = "";
		var sel = "<option value = '-1'>请选择观测点通道</option>";
		var nameStr = "";
		var checked = "";
		var cloneSenGroup = $("#add-logicGroup-constant-sensorChannel").children('tr').children('td[type]').children('select');
		if(data.rsData.length > 0){
			 data = data.rsData;
			 $.each(data,function(i,v){
				 if(v.sensor != null){
					 nameStr = v.sensor.name+"-"+v.name;
				 }else{
					 nameStr ="-"+v.name;
				 }
				 if(type == 1 || type == 2){
					 sel +="<option value="+v.id+">"+nameStr+"</option>";
				 }else if(type == 3){
					 sel +="<option value="+v.id+">"+v.name+"</option>";
				 }
			 })
			 if(type == 1 || type == 2){
				 cloneSenGroup.html(sel);
			 }
			 
			 if(type == 2){
				 getLogicGroupSensorChannelList();
				 getLogicGroupConstantPutByPage();
			 }			 
			 if(type == 3){
				 $("#structure-sensorChannel").html(sel);
				 if($("#structure-sensorChannel").attr('cid') != ""){
					 $("#structure-sensorChannel").val(parseInt($("#structure-sensorChannel").attr('cid')));
				 }
			 }
			 var ids = $("#show-add-bridge-logicGroup").data('ids');
			 
			 if(ids != null){
				 for(var i = 0;i<ids.length;i++){
//					 $("#show-add-sensorChannel-logicGroup li[cid="+ids[i].sensorChannelId+"]").attr('lgscId',ids[i].id).find('input[type="checkbox"]').prop('checked',true);
				 }
			 }
			 
		 }else {
			 if(type == 1 || type == 2){
				 cloneSenGroup.html(sel);
			 }else if(type == 3){
				 $("#structure-sensorChannel").val("-1");
			 }
		 }
	 })
}

function getLogicGroupSensorChannelList(){
	var logicGroupId = parseInt($("#show-logicGroup").attr('cid'));
	var index = 0;
	var pageSize = pageSizeLlong;
	var param = {
			logicGroupId:logicGroupId,
			sensorChannelId:null
	}
	commonDataLoader(JSON.stringify(param),resInterface.getLogicGroupSensorChannelList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		if(data.rsData.length > 0){
			 data = data.rsData;
			 var str = "";
			 var tr = "";
			 var selTr = "";
			 var cloneHtml = $("#add-logicGroup-constant-sensorChannel").children('tr').children('td[type]').children('select').prop('outerHTML');
			 var selStr =  "";
			 if($("#logicGroup-update-btn").attr('Flag') == 1){
				 selStr = '<td><button  onclick="hideLine(this)" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button></td>';
			 }
			 $.each(data,function(i,v){
					 str ='<tr pp="channel" tp cid='+v.id+'>'+
							 '<td>'+
								'<select id="select-logicGroupIn-type" class="form-control" onchange="changeLogicGroupInYype(this)" disabled>'+
									'<option value="1">常量</option>'+
									'<option value="2" selected>观测点通道</option>'+
								'</select>'+
							'</td>'+
					 		'<td><input type="text" class="form-control" value='+v.nickName+'></td>'+
					 		'<td><input type="text" class="form-control" disabled></td>'+
					 		'<td type ><input type="text" class="form-control hide">'+cloneHtml+'</td>'+
					 		selStr + 
					 		'</tr>';
					 tr = $(str);
					 $("#add-logicGroup-constant-sensorChannel").append(tr);
					 selTr = $(tr.children('td:eq(3)')).find('select');
					 selTr.removeClass('hide');
					 selTr.val(''+v.sensorChannelId+'');
			 })
		 } 
	})
}

function getLogicGroupConstantPutByPage(){
	var logicGroupId = parseInt($("#show-logicGroup").attr('cid'));
	var index = 0;
	var pageSize = pageSizeLlong;
	var params = {
			logicGroupId : logicGroupId.toString()
	}
	commonDataLoader(JSON.stringify(params),resInterface.getLogicGroupConstantList  + "?index="+index+"&pageSize="+pageSize+""  ,function(data){
		var str = "";
		if(data.rsData.length > 0){
			 data = data.rsData;
			 var selStr = "";
			 if($("#logicGroup-update-btn").attr('Flag') == 1){
				 selStr = '<td><button  onclick="hideLine(this)" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button></td>';
			 }
			 var cloneHtml = $("#add-logicGroup-constant-sensorChannel").children('tr').children('td[type]').children('select').prop('outerHTML');
			 $.each(data,function(i,v){
				 str +='<tr pp="constant" tp cid='+v.id+'>'+
						 '<td>'+
							'<select id="select-logicGroupIn-type" class="form-control" onchange="changeLogicGroupInYype(this)" disabled>'+
								'<option value="1">常量</option>'+
								'<option value="2">观测点通道</option>'+
							'</select>'+
						'</td>'+
				 		'<td><input type="text" class="form-control" value='+v.name+'></td>'+
				 		'<td><input type="text" class="form-control" value='+v.description+'></td>'+
				 		'<td type><input type="text" class="form-control" value='+v.value+'>'+cloneHtml+'</td>'+
				 		selStr + 	
				 	'</tr>';
			 })
				 $("#add-logicGroup-constant-sensorChannel").append(str);
		 } 
	 }) 
	}

 function getByBridgeListByPage(type,flag,page){
	if(!page) page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	if(type == 1 || type == 2 || type == 3 || type == 4){
		pageSize = pageSizeLlong;
	}
	commonDataLoader(null,resInterface.getBridgeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "<option value='-1'>请选择桥梁</option>";
		var str = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				sel +="<option url='"+valArr[i].image+"' value="+valArr[i].id+" >"+valArr[i].name+"</option>";
			}
		} 
		if(type == 1){
			$('#show-bridgeList-logicGroup').html(sel);
		}else if(type == 2){
			$("#show-add-bridge-logicGroup").html(sel);
			var cid = $("#show-add-bridge-logicGroup").attr('cid');
			if(cid != ""){
				$("#show-add-bridge-logicGroup").val(cid);
			}
		}else if(type == 3){
			$('#show-bridgeList-structure').html(sel);
		}else if(type == 4){
			$('#show-bridgeList-evaluate').html(sel);
		}
		
	})
 }

 function getLogicGroupListByPage(type,flag,page){
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-logicGroup")});
	var index = (page - 1) * 10;
	var pageSize  = "";
	var params  = {};
	if(type == 1){
		pageSize = pageSizeTen;
		$('#show-logicGroup').html("");
		var bridgeId = $("#show-bridgeList-logicGroup").val() != "-1" ? $("#show-bridgeList-logicGroup").val() : "-1" ;
		var startTime = $.trim($("#start-date-logicGroup").val()) != "" ? $.trim($("#start-date-logicGroup").val()): "-1";
		var endTime = $.trim($('#end-date-logicGroup').val()) != "" ? $.trim($('#end-date-logicGroup').val()): "-1";
		
		params  = {
				bridgeId:bridgeId,
				startTime:startTime,
				endTime:endTime
		}
	}else if(type == 2){
		pageSize = pageSizeLlong;
		params  = {
				bridgeId: $("#show-addbridgeList-structure").val(),
				startTime:"-1",
				endTime:"-1"
		}
	}else if(type == 3 || type == 4){
		pageSize = pageSizeLlong;
		params  = {
				bridgeId: "-1",
				startTime:"-1",
				endTime:"-1"
		}
	}
	
	commonDataLoader(JSON.stringify(params),resInterface.getLogicGroupList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str= "";
		var url = "";
		var td = "";
		var sel = "<option value='-1'>请选择逻辑组</option>";
		console.log(valArr);
		var unitStr = "";
		var unit = "";
		var intervalTime = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				unit = valArr[i].timeIntervalUnit;
				intervalTime = valArr[i].timeInterval;
				if(unit == 1000){
					unitStr = intervalTime / 1000 + " ( 秒 )";
				}else if(unit == 60000){
					unitStr = intervalTime / 60000 + " ( 分 )";
				}else if(unit == 3600000){
					unitStr = intervalTime / 3600000 + " ( 时 )";
				}else if(unit == 86400000){
					unitStr = intervalTime / 86400000 + " ( 天 )";
				}
				if(type == 1){
					str = '<tr cid='+valArr[i].id+'>'+
								'<td><input type="checkbox"  onclick="judgeCheckTotal(\'#show-logicGroup\',\'#logicGroup-update-btn\')"></td>'+
								'<td><a href="javascript:void(0)" onclick="showLogicInfo(this)"><span name>'+valArr[i].name+'</span><a></td>' +
								'<td>'+ unitStr+'</td>' +
								'<td class="text-left width-4">'+valArr[i].description+'</td>' +
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
					td = $(str).data("data",valArr[i]);
					$('#show-logicGroup').append(td);
				}else{
					sel +="<option value="+valArr[i].id+" >"+valArr[i].name+"</option>";
				}
			}
		}else{
			$('#show-logicGroup').html("没有数据");
		} 
		if(flag && type == 1){
			if(data.rsCount > 10){
				$(".page-logicGroup").removeClass('hide');
			}else{
				$(".page-logicGroup").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-logicGroup"),event:"getLogicGroupListByPage("+type+",false,"});
		}else{
			if(type == 2){
				$("#structure-logicGroup").html(sel);
				if($("#structure-logicGroup").attr('cid') != ""){
					$("#structure-logicGroup").val(parseInt($("#structure-logicGroup").attr('cid')));
				}
			}else if(type == 3){
				$.each($("#batch-addOrUpdateEvaluate").find('dd[selLogicGroup]'),function(i,v){
					if($(v).children('select').find('option').length == 1){
						$(v).find('select').html(sel);
					}
				})
				
				if($("#show-evaluateList").find('tr[ad].hide').length == 1){
					cloneAddEvaluateTable();
				}
			}else if(type == 4){
				var selDom = $("#show-evaluateList").find('tr:eq(4)').find('dd:eq(4)').children('select');
				selDom.html(sel);
				if(selDom.attr('cid') != ""){
					selDom.val(parseInt(selDom.attr('cid')));
				}
			}
		}
	});
}

 function showLogicInfo(that){
		$("#logicGroup-update-btn").attr('cur-item-id',$(that).closest('tr').attr('cid'));
		$("#addOrUpdat-logicGroup").modal('show');
		$("#addOrUpdat-logicGroup").find('button,input,select,textarea').prop('disabled',true);
		$("#addOrUpdat-logicGroup").find('.modal-footer').addClass('hide');
		createOrUpdateLogicGroup("#logicGroup-update-btn",true);
	}
 
function deleteLogicGroupListByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-logicGroup");
	 }
	 
	 commonDataLoader(JSON.stringify(ids),resInterface.deleteLogicGroupByIds ,function(data){
		 if(data){
			 getLogicGroupListByPage(1,true,1);
		 }
	 })
}

function createOrUpdateLogicGroup(that,flag){
	cleanValue("#addOrUpdat-logicGroup","#display-logicGroup");
//	$("#show-add-sensorChannel-logicGroup").find('input[type="checkbox"]').prop('checked',false);
	$("#show-add-bridge-logicGroup").empty().append($("#show-bridgeList-logicGroup").children().clone());
	$("#add-logicGroup-outPut").find('tr:gt(1)').remove();
	$("#add-logicGroup-constant-sensorChannel").find('tr:gt(1)').remove();
//	$("#show-add-sensorChannel-logicGroup").empty();
	
	$("#addOrUpdat-logicGroup").find('.modal-header').children('button').prop('disabled',false);
	if(!flag){
		$("#addOrUpdat-logicGroup").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdat-logicGroup").find('.modal-footer').removeClass('hide');
		$("#add-logicGroup-algorithm").removeClass('hide');
		$("#logicGroup-update-btn").attr('Flag',1);
		$("#show-add-addLogicGroup").find('button').prop('disabled',false);
//		$("#add-logicGroup-constant-sensorChannel").prev('thead').find('th:eq(4)').prop('disabled',false);
		$("#show-add-display-input").removeClass('hide');
		$("#show-add-display-output").removeClass('hide');
	}else{
		$("#show-add-display-input").addClass('hide');
//		$("#add-logicGroup-constant-sensorChannel").prev('thead').find('th:eq(4)').prop('disabled',true);
		$("#show-add-display-input").addClass('hide');
		$("#show-add-display-output").addClass('hide');
		$("#show-add-addLogicGroup").find('button').prop('disabled',true);
		$("#add-logicGroup-algorithm").addClass('hide');
		$("#logicGroup-update-btn").attr('Flag',0);
	}
	
	if(that){//修改
		setTimeout(function(){
			 $("#logicGroup-update-btn").prop('disabled',true);
		 },500);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-logicGroup").find('tr[cid='+itemId+']').get(0)).data('data');
		var bridgeId = data.bridgeId;
		var code = data.code;
		var name = data.name;
		var timeInterval = data.timeInterval;
		var timeIntervalUnit = data.timeIntervalUnit;
		var logicGroupSensorChannelList = data.logicGroupSensorChannelList;
		if(logicGroupSensorChannelList != null && logicGroupSensorChannelList.length > 0){
			$("#show-add-bridge-logicGroup").data('ids',logicGroupSensorChannelList);
		}else{
			$("#show-add-bridge-logicGroup").data('ids','');
		}
		var description = data.description;
		var id = data.id;
		var algorithm = data.algorithm;
		$("#show-add-bridge-logicGroup").val(bridgeId).attr('cid',bridgeId);
		$("#show-logicGroup").attr('cid',id);
		$("#add-logicGroup-code").val(code);
		$("#show-add-sensorChannel-timeInterval").val(timeInterval / timeIntervalUnit);
		$("#add-logicGroup-name").val(name);
		$("#add-logicGroup-description").val(description);
		$("#add-logicGroup-code").val(code);
		$("#show-add-sensorChannel-timeIntervalUnit").val(timeIntervalUnit);
		$("#add-logicGroup-algorithm").attr('url',algorithm);
		$("#add-logicGroup-outPut").find('tr:eq(1)').addClass('hide');
		$("#add-logicGroup-constant-sensorChannel").find('tr:eq(1)').addClass('hide');
		$("#show-add-addLogicGroup").find('button').prop('disabled',false);
		$("#show-add-bridge-logicGroup").prop('disabled',true);
 		getLogicGroupOutPutByPage(1,id);
 		var obj  = {
 				sensorId:null,
 				sensorTypeId:null,
 				bridgeId:bridgeId,
 				workSectionId:null,
 				logicGroupId:null
 		}
 		getSensorChannelList(2,obj);
	}else{
		$("#show-add-bridge-logicGroup").data('ids','');
		$("#add-logicGroup-outPut").children('tr:eq(1)').removeClass('hide');
		$("#add-logicGroup-constant-sensorChannel").children('tr:eq(1)').removeClass('hide');
		$("#show-add-addLogicGroup").find('button').prop('disabled',true);
		$("#show-add-sensorChannel-timeIntervalUnit").val(1000);
		$("#show-logicGroup").attr('cid',"");
		$("#show-add-addLogicGroup").find('tr').find(':text,textArea,select').prop('disabled',true);
		$("#show-add-bridge-logicGroup").prop('disabled',false);
		$("#add-logicGroup-constant-sensorChannel").find('tr:eq(1)').find('#select-logicGroupIn-type').val("1");
		$("#show-add-addLogicGroup").attr('cid','');
	} 
}

function getLogicGroupOutPutByPage(type,logicGroupId){
	var index = 0;
	var pageSize = pageSizeLlong;
	var params = {
			logicGroupId : logicGroupId != null ? logicGroupId.toString() : "-1"
	}
	commonDataLoader(JSON.stringify(params),resInterface.getLogicGroupOutputList  + "?index="+index+"&pageSize="+pageSize+""  ,function(data){
		var str = "";
		var sel = "<option value='-1'>请选择逻辑组输出</option>";
		if(data.rsData.length > 0){
			 data = data.rsData;
			 var selStr = "";
			 if($("#logicGroup-update-btn").attr('Flag') == 1){
				 selStr = '<td><button  onclick="hideLine(this)" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button></td>';
			 }
			 $.each(data,function(i,v){
				 if(type == 1){
					 str +='<tr tp cid='+v.id+'>'+
						 		'<td><input type="text" class="form-control" value='+v.name+'></td>'+
						 		'<td><input type="text" class="form-control" value='+v.description+'></td>'+
						 		'<td><input type="text" class="form-control" value='+v.unit+'></td>'+
						 		selStr + 
						 	'</tr>';
				 }else if(type == 2 || type == 3){
					 sel+="<option value="+v.id+">"+v.name+"</option>";
				 } 
			 })
			 if(type == 1){
				 $("#add-logicGroup-outPut").append(str);
			 }else if(type == 2){
				 var curSelLogicGroupIndex = parseInt($("#show-evaluateList").attr('cur-sel-logicGroup-index'));
				 $("#show-evaluateList").find('tr[index='+curSelLogicGroupIndex+']').find('dd[selLogicGroupOutPut]').children('select').html(sel);
			 }else if(type == 3){
				 var selDom = $("#show-evaluateList").find('tr:eq(4)').find('dd:eq(5)').children('select');
				 selDom.html(sel);
				 if(selDom.attr('cid') != ""){
					selDom.val(parseInt(selDom.attr('cid')));
				}
			 }
		 }else {
			  
		 }
	 }) 
}

function insertOrUpdateLogicGroup(){
	var id = $("#show-logicGroup").attr('cid');
	var bridgeId = parseInt($("#show-add-bridge-logicGroup").val());
	var code = $("#add-logicGroup-code").val();
	var name = $("#add-logicGroup-name").val();
	var description = $("#add-logicGroup-description").val();
	var timeInterval = parseFloat($("#show-add-sensorChannel-timeInterval").val());
	var algorithm  = $("#add-logicGroup-algorithm").attr('url');
	var timeIntervalUnit = parseInt($("#show-add-sensorChannel-timeIntervalUnit").val());
	timeInterval = timeInterval * timeIntervalUnit;
	var logicGroup  = {
    		 bridgeId : bridgeId,
    		 code : code,
    		 name : name,
    		 description : description,
    		 timeInterval : timeInterval,
    		 algorithm: algorithm,
    		 timeIntervalUnit:timeIntervalUnit
     };
	
	var sensorChannelIds = {
			 addIds:[],
			 deleteIds:[] 
	}
	var checkId = "";
     
     if(id != ""){
    	logicGroup.id = parseInt(id);//修改
    	commonDataLoader(JSON.stringify(logicGroup),resInterface.updateLogicGroupById ,function(data){
      		 if(true){
      			insertOrUpdateLogicGroupConstantPutAndLogicGroupSensorChannel(id);
      			insertOrUpdateLogicGroupOutPut();
      			$('#addOrUpdat-logicGroup').modal('hide');
      			getLogicGroupListByPage(1,true,1);
      		 }
      	 }) 
    }else{
    	commonDataLoader(JSON.stringify(logicGroup),resInterface.insertLogicGroup ,function(data){
   		 if(data.rsData.length > 0){
   			insertOrUpdateLogicGroupConstantPutAndLogicGroupSensorChannel(data.rsData[0].id);
   			insertOrUpdateLogicGroupOutPut(data.rsData[0].id);
   			$('#addOrUpdat-logicGroup').modal('hide');
   			getLogicGroupListByPage(1,true,1);
   		 }
   	 }) 
   }
}


//添加或修改逻辑组常量和逻辑组观测点关系
function insertOrUpdateLogicGroupConstantPutAndLogicGroupSensorChannel(logicGroupId){
	var con_obj = {};
	var con_delIdsArr = [];
	var con_updateArr = [];
	var con_insertArr = [];
	var con_totalParam = {};
	
	var log_sen_obj = {};
	var log_sen_delIdsArr = [];
	var log_sen_updateArr = [];
	var log_sen_insertArr = [];
	var log_sen_totalParam = {};
 
 	if($("#add-logicGroup-constant-sensorChannel").find('tr[ad]:not(".hide")').length > 0){
		 $.each($("#add-logicGroup-constant-sensorChannel").find('tr[ad]:not(".hide")'),function(i,v){
			 if($(v).attr('pp') == "constant"){
				 con_obj = {
							name : 	$(v).children('td:eq(1)').find('input').val(),
							description : $(v).children('td:eq(2)').find('input').val(),
							value : $(v).children('td[type]').find('input').val(),
							logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
						 }
				con_insertArr.push(con_obj);
			 }else if($(v).attr('pp') == "channel"){
				 log_sen_obj = {
						 	nickName : 	$(v).children('td:eq(1)').find('input').val(),
						 	sensorChannelId :parseInt($(v).children('td[type]').find('select').val()),
							logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
						 }
				 log_sen_insertArr.push(log_sen_obj);
			 }
		 })
	 }
 	
	 if($("#add-logicGroup-constant-sensorChannel").find('tr[tp].hide').length > 0){
		 $.each($("#add-logicGroup-constant-sensorChannel").find('tr[tp].hide'),function(i,v){
			 if($(v).attr('pp') == "constant"){
				 con_delIdsArr.push(parseInt($(v).attr('cid')));
				 
			 }else if($(v).attr('pp') == "channel"){
				 log_sen_delIdsArr.push(parseInt($(v).attr('cid')));
			 }
		 })
		 
	 }
	 
	 if($("#add-logicGroup-constant-sensorChannel").find('tr[tp]:not(".hide")').length > 0){
		$.each($("#add-logicGroup-constant-sensorChannel").find('tr[tp]:not(".hide")'),function(i,v){
			if($(v).attr('pp') == "constant"){
				 con_obj = {
						 	id:	parseInt($(v).attr('cid')),
							name : 	$(v).children('td:eq(1)').find('input').val(),
							description : $(v).children('td:eq(2)').find('input').val(),
							value : $(v).children('td[type]').find('input').val(),
							logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
						 }
				 con_updateArr.push(con_obj);
			 }else if($(v).attr('pp') == "channel"){
				 log_sen_obj = {
						 	id:parseInt($(v).attr('cid')),
						 	nickName : 	$(v).children('td:eq(1)').find('input').val(),
						 	sensorChannelId :parseInt($(v).children('td[type]').find('select').val()),
						 	logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
						 }
				 log_sen_updateArr.push(log_sen_obj);
			 }
		}) 
	 }
	 
	 con_totalParam = {
		delIdsArr:JSON.stringify(con_delIdsArr),
		updateArr:JSON.stringify(con_updateArr),
		insertArr:JSON.stringify(con_insertArr)
	}
		
	 
	 log_sen_totalParam = {
				delIdsArr:JSON.stringify(log_sen_delIdsArr),
				updateArr:JSON.stringify(log_sen_updateArr),
				insertArr:JSON.stringify(log_sen_insertArr)
			}
	 
	commonDataLoader(JSON.stringify(con_totalParam),resInterface.changelogicGroupConstant,function(data){
		 if(data){
//			 $('#addOrUpdat-logicGroup').modal('hide');
		 }
	 })
	 
	 commonDataLoader(JSON.stringify(log_sen_totalParam),resInterface.changeLogicGroupSensorChannel,function(data){
		 if(data){
//			 $('#addOrUpdat-logicGroup').modal('hide');
		 }
	 })
	
}

//添加或修改逻辑组输出
function insertOrUpdateLogicGroupOutPut(logicGroupId){
	var obj = {};
	var delIdsArr = [];
	var updateArr = [];
	var insertArr = [];
	var totalParam = {};
 
 	if($("#add-logicGroup-outPut").find('tr[ad]:not(".hide")').length > 0){
		 $.each($("#add-logicGroup-outPut").find('tr[ad]:not(".hide")'),function(i,v){
			 obj = {
				name : 	$(v).children('td:eq(0)').find('input').val(),
				description : $(v).children('td:eq(1)').find('input').val(),
				unit : $(v).children('td:eq(2)').find('input').val(),
				logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
			 }
			 insertArr.push(obj);
		 })
	 }
 	
	 if($("#add-logicGroup-outPut").find('tr[tp].hide').length > 0){
		 $.each($("#add-logicGroup-outPut").find('tr[tp].hide'),function(i,v){
			 delIdsArr.push(parseInt($(v).attr('cid')));
		 })
		 
	 }
	 
	 if($("#add-logicGroup-outPut").find('tr[tp]:not(".hide")').length > 0){
		$.each($("#add-logicGroup-outPut").find('tr[tp]:not(".hide")'),function(i,v){
			obj = {
				id: parseInt($(v).attr('cid')),
				name : 	$(v).children('td:eq(0)').find('input').val(),
				description : $(v).children('td:eq(1)').find('input').val(),
				unit : $(v).children('td:eq(2)').find('input').val(),
				logicGroupId:logicGroupId != null ? logicGroupId : parseInt($("#show-logicGroup").attr('cid'))
			}
			updateArr.push(obj);
		}) 
	 }
	 
	totalParam = {
		delIdsArr:JSON.stringify(delIdsArr),
		updateArr:JSON.stringify(updateArr),
		insertArr:JSON.stringify(insertArr)
	}
		
	commonDataLoader(JSON.stringify(totalParam),resInterface.changelogicGroupOutput,function(data){
		 if(data){
//			 $('#addOrUpdat-logicGroup').modal('hide');
		 }
	 })
}

