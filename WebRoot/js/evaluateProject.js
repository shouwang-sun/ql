var expandNode = null;
var expandNodeId = null;
var clickNode = null;
var clickNodeId = null;
var zTreeEvaluate = {};
var settingEvaluate = {
		view: {
			dblClickExpand: false,
			showLine: false
		},
		async: {
			enable: true,  // 是否开启异步加载模式
			url:getUrl,
			dataFilter: filterEvaluate
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pid"
			}
		},
		check: {
			enable: false
		},
		callback: {
			onExpand:onExpandEvaluate,
			onClick:onclickEvaluate,
			beforeExpand:beforeExpandEvaluate,
			beforeClick:beforeClickEvaluate,
			onAsyncSuccess:onAsyncSuccessEvaluate
		}
};

function getUrl(){
	var treeNodeId = 0;
	if(expandNode){
		treeNodeId = expandNodeId;
	}
	return resInterface.getEvaluateProjectList + "?index=0&pageSize=9999&pid="+treeNodeId+""; //获取数据的url地址
}

var zNodesEvaluate =[{ id:0, pid:-1, name:'桥梁', isParent:true , open:false}];
 

function beforeExpandEvaluate(treeId, treeNode, clickFlag){
	expandNode = treeNode;
	expandNodeId = treeNode.id;
}

function onExpandEvaluate(event,treeId,treeNode){
	expandNode = treeNode;
	expandNodeId = treeNode.id;
}

function beforeClickEvaluate(treeId, treeNode, clickFlag){
	clickNode = treeNode;
	clickNodeId = treeNode.id;
}

function onclickEvaluate(event, treeId, treeNode, clickFlag){
	clickNode = treeNode;
	clickNodeId = treeNode.id;
	if(clickNodeId == 0){
		$("#choose-evaluate").val("").attr({'cid':"",'bid':"","pid":""});
	}else{
		$("#choose-evaluate").val(clickNode.name).attr({'cid':clickNodeId,'bid':clickNode.bridgeId,"pid":clickNode.pid});
		getEvaluateListByPage(2,true,1);
	}
}

function filterEvaluate(treeId, parentNode, childNodes){
	if(childNodes.code == 200){
		if (!childNodes || !childNodes.data) return null;
		var result = new Array();
		result = childNodes.data.rsData;
		for(var i = 0;i<result.length; i ++){
			result[i].isParent = true;
		}
		if(parentNode && result.length  == 0){
			parentNode.isParent = false;
		}else{
			return result;
		}
	}
}

function onAsyncSuccessEvaluate(event, treeId, treeNode, msg){
    var zTree = $.fn.zTree.getZTreeObj("evaluate-tree");
	if(treeNode && treeNode.children.length == 0){
		treeNode.isParent = false;
		zTree.updateNode(treeNode);		
	}else{
		zTree.expandNode(treeNode,true) ;
	}
}

$(function(){
	 
})

function judgeHealthy(that){
	var val = $(that).val();
	if(isNaN(val)){
		alert("非法的数字");
		$(that).val("");
	}else if(parseFloat(val) > 1 ||  parseFloat(val) < 0 ){
		alert("请输入0到1的一个小数");
		$(that).val("");
	}
}

function changeLogicGroup(that){
	var val = $(that).val();
	var index = $(that).closest('tr').attr('index');
	$("#show-evaluateList").attr('cur-sel-logicGroup-index',index);
 	getLogicGroupOutPutByPage(2,val);
}

function getEvaluateListByPage(type,flag,page){
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-evaluate")});
	var index = "";
	var pageSize = "";	
	var bridgeId = "";
	var startTime = "";
	var endTime = "";
	var pid = "";
	var params = {};
	if(type == 1){
		index = (page - 1) * 10;
		pageSize = pageSizeTen;
		$('#show-evaluate').html("");
		bridgeId = $("#show-bridgeList-evaluate").val();
		startTime = $.trim($("#start-date-evaluate").val()) != "" ? $.trim($("#start-date-evaluate").val()): "-1";
		endTime = $.trim($('#end-date-evaluate').val()) != "" ? $.trim($('#end-date-evaluate').val()): "-1";
		params = {
				bridgeId:bridgeId,
				startTime:startTime,
				endTime:endTime
		}
		params = JSON.stringify(params);
	}else if(type == 2 || type == 3){
		params = null ;
		index = (page - 1) * 10;
		pageSize = pageSizeLlong;
		pid = $("#choose-evaluate").attr('cid');
	}
	
	if(parseInt(pid) == 0){
		return;
	}
	commonDataLoader(params,resInterface.getEvaluateProjectList + "?index="+index+"&pageSize="+pageSize+"&pid="+pid+"" ,function(data){
		var valArr = data.rsData;
		var str= "";
		var td = "";
		console.log(valArr);
		var evaluateProjectName = "";
		var dlStr = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(type == 1){
					evaluateProjectName = valArr[i].parentEvaluateProject != null ? valArr[i].parentEvaluateProject.name : "" ;
					str = '<tr cid='+valArr[i].id+'>'+               
								'<td><input type="checkbox"  onclick="judgeCheckTotal(\'#evaluation-preserve\',\'#structure-evaluate-btn\')"></td>'+
								'<td><a href="javascript:void(0)" onclick="showEvaluateProject(this)">'+valArr[i].name+'</a></td>' +
								'<td class="text-left width-4">'+valArr[i].description+'</td>' +
								'<td>'+valArr[i].healthyRate+'</td>' +
								'<td>'+evaluateProjectName+'</td>' +
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
					td = $(str).data("data",valArr[i]);
					$('#show-evaluate').append(td);
				}else if(type == 2){
					dlStr += '<dt cid='+valArr[i].id+' originHealthy="'+valArr[i].healthyRate+'">'+valArr[i].name+'</dt><dd cid='+valArr[i].id+' healthy><input onchange="judgeHealthy(this)" class="form-control" value="'+valArr[i].healthyRate+'"></dd>';
				}
			}
		}else{
			if(type == 1){
				$('#show-evaluate').html("没有数据");
			}
		} 
		
		if(flag && type == 1){
			if(data.rsCount > 10){
				$(".page-evaluate").removeClass('hide');
			}else{
				$(".page-evaluate").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-evaluate"),event:"getEvaluateListByPage("+type+",false,"});
		}else if(type == 2){
			$("#show-other-evaludate-healthyList").html(dlStr);
			if($("#show-evaluate").attr('cid') != ""){
				$("#show-other-evaludate-healthy").find('dt[cid='+parseInt($("#show-evaluate").attr('cid'))+']').remove();
				$("#show-other-evaludate-healthy").find('dd[cid='+parseInt($("#show-evaluate").attr('cid'))+']').remove();
			}
		} 
	});
}


function showEvaluateProject(that){
	$("#structure-evaluate-btn").attr('cur-item-id',$(that).closest('tr').attr('cid'));
	$("#addOrUpdat-evaluate").modal('show');
	$("#addOrUpdat-evaluate").find('button,input,select,textarea').prop('disabled',true);
	$("#addOrUpdat-evaluate").find('.modal-header button').prop('disabled',false);
	$("#addOrUpdat-evaluate").find('.modal-footer').addClass('hide');
	createOrUpdateEvaluate("#structure-evaluate-btn",true);
}

function deleteEvaluateListByIds(id){
	 var ids =[];
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-evaluate");
	 }
	 
	 commonDataLoader(JSON.stringify(ids),resInterface.deleteEvaluateProjectByIds ,function(data){
		 if(data){
			 getEvaluateListByPage(1,true,1);
		 }
	 })
}

function createOrUpdateEvaluate(that,flag){
	$("#show-evaluateList").find('tr:gt(3)').remove();
	cleanValue("#addOrUpdat-evaluate","#evaluation-preserve");
	zTreeEvaluate = $.fn.zTree.getZTreeObj("evaluate-tree");
	$("#show-other-evaludate-healthyList").html("");
	$.fn.zTree.init($("#evaluate-tree"), settingEvaluate, zNodesEvaluate);
	
	if(!flag){
		$("#addOrUpdat-evaluate").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdat-evaluate").find('.modal-footer').removeClass('hide');
	}
	
	if(that){//修改
		setTimeout(function(){
			 $("#evaluate-update-btn").prop('disabled',true);
		 },500);
		$("#choose-evaluate").prop('disabled',true);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-evaluate").find('tr[cid='+itemId+']').get(0)).data('data');
		
		if(data.pid == 0 && !flag){
			$("#show-evaluateList").find('button,input,select,textarea').prop('disabled',true);
			$("#structure-threshold-group").find('input').prop('disabled',false);
		}
		
		cloneAddEvaluateTable();
		var name = data.name;
		var description = data.description;
		var healthyRate = data.healthyRate;
		var logicGroupId = data.logicGroupId;
		var outPutId = data.outputId;
		var pid = data.pid;
		var id = data.id;
		var evaluateProjectName = "";
		if(data.parentEvaluateProject != null){
			evaluateProjectName = data.parentEvaluateProject.name;
		}
		
		var threshold = data.threshold;
		if(threshold != null){
			$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(3)').find(".threshold-1").val(threshold.split(",")[0]);
			$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(3)').find(".threshold-2").val(threshold.split(",")[1]);
			$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(3)').find(".threshold-3").val(threshold.split(",")[2]);
		}
		
		$("#evaluate-tree").addClass('hide');
		$("#choose-evaluate").val(evaluateProjectName).attr('cid',pid);
		$("#show-evaluate").attr({'cid':id,'pid':pid})
		$("#clone-evaluate-button").addClass('hide');
		
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(0)').children('input').val(name);
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(1)').children('input').val(description);
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(2)').children('input').val(healthyRate);
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(4)').children('select').attr('cid',logicGroupId);
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(5)').children('select').attr('cid',outPutId);
		$("#show-evaluateList").find('tr:eq(4)').find('td:eq(1)').addClass('hide');
//		$("#show-evaluateList").find('dd[sellogicgroup],dd[sellogicgroupoutput]').prop('disabled',true);
		
		getLogicGroupListByPage(4,true,1);
		getLogicGroupOutPutByPage(3,logicGroupId);
		getEvaluateListByPage(2,true,1);
	}else{
//		$("#show-evaluateList").find('dd[sellogicgroup],dd[sellogicgroupoutput]').prop('disabled',false);
		$("#evaluate-tree").removeClass('hide');
		$("#choose-evaluate").val("").attr('cid',"");
		$("#show-evaluate").attr({'cid':'','pid':''});
		$("#clone-evaluate-button").removeClass('hide');
		$("#show-evaluateList").find('tr:eq(4)').find('td:eq(1)').removeClass('hide');
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(4)').children('select').attr('cid','');
		$("#show-evaluateList").find('tr:eq(4)').find('dd:eq(5)').children('select').attr('cid','');
		getLogicGroupListByPage(3,true,1);
	}
}

function cloneAddEvaluateTable(){
	var cloneLine = $("#batch-addOrUpdateEvaluate").clone();
	$(cloneLine).removeClass('hide');
	$(cloneLine).attr('index',$("#show-evaluateList").find('tr[ad]').length + 1);
	$("#show-evaluateList").append(cloneLine);
}

function insertOrUpdateEvaluate(){
	//判断所有权值加起来是否等于1
//	$.each($("#addOrUpdat-evaluate").find('dd[]'))
	var totalHealthy = 0;
	var healVal = 0 ;
	if($("#show-other-evaludate-healthyList").find('dt').length > 0){
		$.each($("#show-other-evaludate-healthyList").find('dd[healthy]'),function(i,v){
			healVal = $(v).children('input').val()
			if( healVal != null && healVal != "" && healVal != "null"){
				totalHealthy += parseFloat(healVal);
			}
		})
	}
	
	if($("#show-evaluateList").find('tr[ad]:not(".hide")').length > 0){
		$.each($("#show-evaluateList").find('tr[ad]:not(".hide")'),function(i,v){
			healVal = $(v).find("dd[healthy]").children('input').val();
			if( healVal != null && healVal != "" && healVal != "null"){
				totalHealthy += parseFloat(healVal);
			}
		})
	}
	if(totalHealthy > 1){
		alert("所有节点权重之和为"+totalHealthy+",该数大于1，请修改!");
		return;
	}else if(totalHealthy < 1){
		alert("所有节点权重之和为"+totalHealthy+",该数小于1，请修改!");
		return;
	}
	
	var id = $("#show-evaluate").attr('cid');
	var pid = parseInt($("#choose-evaluate").attr("cid"));
	var bridgeId = parseInt($("#choose-evaluate").attr("bid"));
	var totalParam = {};
	var singleEvaluate = {};
	var insertArr = [];
	var updateArr = [];
	var updateSingle ={};
	var curHealthy = "";
	var originHealthy = "";
	var threshold1 = "";
	var threshold2 = "";
	var threshold3 = "";
	var threshold = "";
	if(id == ""){ //批量添加
		if($("#show-evaluateList").find('tr[ad]:not(".hide")').length > 0){
			 var dom = "";
			 $.each($("#show-evaluateList").find('tr[ad]:not(".hide")'),function(i,v){
				 dom = $(v).children('td:eq(0)').children('dl:eq(0)');
				 threshold1 = dom.children('dd:eq(3)').find(".threshold-1").val();
				 threshold2 = dom.children('dd:eq(3)').find(".threshold-2").val();
				 threshold3 = dom.children('dd:eq(3)').find(".threshold-3").val();
				 threshold = threshold1 + "," + threshold2 + "," + threshold3;
				 obj = {
				    pid	: pid,
					name : 	dom.children('dd:eq(0)').children('input').val(),
					description : dom.children('dd:eq(1)').children('input').val(),
					healthyRate : parseFloat(dom.children('dd:eq(2)').children('input').val()),
					bridgeId : bridgeId,
					logicGroupId:parseInt(dom.children('dd:eq(4)').children('select').val()),
					outputId:parseInt(dom.children('dd:eq(5)').children('select').val()),
					threshold:threshold
				 }
				 insertArr.push(obj);
			 })
		 }
	}else{//单个修改
		var dom = $("#show-evaluateList").find('tr:eq(4)').find('dd');
		 threshold1 = dom.eq(3).find(".threshold-1").val();
		 threshold2 = dom.eq(3).find(".threshold-2").val();
		 threshold3 = dom.eq(3).find(".threshold-3").val();
		 threshold = threshold1 + "," + threshold2 + "," + threshold3;
		updateSingle = {
				    id:parseInt(id) ,
				 	pid	: pid,
					name : 	dom.eq(0).children('input').val(),
					description : dom.eq(1).children('input').val(),
					healthyRate : parseFloat(dom.eq(2).children('input').val()),
					bridgeId : bridgeId,
					logicGroupId:parseInt(parseInt(dom.eq(4).children('select').val())),
					outputId:parseInt(parseInt(dom.eq(5).children('select').val())),
					threshold:threshold
		}
	}
	
	//批量修改 
	if($("#show-other-evaludate-healthyList").find('dt').length > 0){
		$.each($("#show-other-evaludate-healthyList").find('dt'),function(i,v){
			curHealthy = parseFloat($("#show-other-evaludate-healthyList").find('dd:eq('+i+')').children('input').val());
			originHealthy =  parseFloat($(v).attr('originHealthy'));
			if( curHealthy !=  originHealthy){
				singleEvaluate = {
					id:parseInt($(v).attr('cid')),
					healthyRate:curHealthy
				}
				updateArr.push(singleEvaluate);
			}
		})
	}
	
	totalParam = {
			insertArr:JSON.stringify(insertArr),
			updateArr:JSON.stringify(updateArr),
			updateSingle:JSON.stringify(updateSingle)
	}
	
	console.log(totalParam);
	commonDataLoader(JSON.stringify(totalParam),resInterface.changeEvaluateProject ,function(data){
		 if(data){
			 $('#addOrUpdat-evaluate').modal('hide');
			 getEvaluateListByPage(1,true,1);
		 }
	 })
}

