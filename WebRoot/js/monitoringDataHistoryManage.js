var zTree = {};
var setting = {
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
			onExpand: onExpand,
			onClick: showChanelData
		}
};
var zNodes =[{ id:'init_1', pId:0, name:'桥梁', isParent:true , open:false}];

$(function(){
	chooseSensorType();
	searchByDate(true,1);
});

function chooseSensorType(){
	zTree = $.fn.zTree.getZTreeObj("treeBridge");
	$.fn.zTree.init($("#treeBridge"), setting, zNodes);
	$("#treeBridge").data("nodeList","[]");
	$('#show-chanData').html("");
}

function onExpand(event,treeId,treeNode){
	var showType = $("input[name='showType']:checked").attr("showType");
 	var nodeStr = (treeNode.id).toString();  
	var params = {
		"showType":showType,
		"nodeStr":nodeStr
	};
	
	zTree = $.fn.zTree.getZTreeObj("treeBridge");
	var nodeListStr = $("#treeBridge").data("nodeList");
	var nodeList = [];
	
	nodeList = JSON.parse(nodeListStr);
	
	for(var i = 0;i < nodeList.length;i++){
		if(nodeStr == nodeList[i]){
			return;
		}
	}
	commonDataLoader(JSON.stringify(params),resInterface.buildTree,function(data){
			var node = data.rsData;
			if(!nodeListStr){
				nodeList.push(nodeStr);
			}else{
				nodeList = JSON.parse(nodeListStr);
				nodeList.push(nodeStr);
			}
			nodeListStr = JSON.stringify(nodeList);
			$("#treeBridge").data("nodeList",nodeListStr);
			zTree.addNodes(treeNode,node);
	});
}

function showChanelData(event,treeId,treeNode){
  curChooseNode = {
		bridgeId:null,
		sensorTypeId:null,
		workSectionId:null,
		sensorId:null,
		sensorChannelId:null
	};
  
    getParentNode(treeNode,"channel");
    console.log(curChooseNode);
	var nodeStr = (treeNode.id).toString(); 
	var nodeType = nodeStr.split("_")[0];
	var nodeId = nodeStr.split("_")[1];
	
	$('#cur-choose-item').val(treeNode.name).attr({"nodeJson": JSON.stringify(curChooseNode)});
	$('#show-more-info').html('nodeType:  ' +nodeType + "   nodeId:  " + nodeId);
}

function searchByDate(flag,page){
	var startTime = $.trim($("#start-date").val()) != "" ? $.trim($("#start-date").val()): "-1";
	var endTime = $.trim($('#end-date').val()) != "" ? $.trim($('#end-date').val()): "-1";
	var nodeJson = $('#cur-choose-item').attr("nodeJson") != null ? $('#cur-choose-item').attr("nodeJson") : "-1";
	
	var params = {
			"createTime":startTime.toString(),
			"endTime":endTime.toString(),
			"nodeJson":nodeJson.toString()
	};
	
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".pagination")});
	var index = (page - 1) * 10;
	commonDataLoader(JSON.stringify(params),resInterface.getChannelDataHistoryFileList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str= "";
		var url = "";
		console.log(valArr);
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				url = valArr[i].datTypeUrl;
				str += '<tr>'+
				'<td><a href= "javascript:void(0)" url='+url+' onclick="openUrl(this,true)">'+valArr[i].name+'</a></td>' +
				'<td>'+valArr[i].dataSize / 1024+'kb</td>'+ 
				'<td>'+dateFormat(valArr[i].startTime)+'</td>'+ 
			'</tr>';
			}
			$('#show-historyData').html(str);
		}else{
			$('#show-historyData').html("没有数据");
		} 
		
		
		if(flag){
			if(data.rsCount > 10){
				$(".pagination").removeClass('hide');
			}else{
				$(".pagination").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".pagination"),event:"searchByDate(false,"});
		}
	});
}

