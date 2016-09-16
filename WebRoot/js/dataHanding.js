var zTree = {};
var myChart = null;
var myChartDetail = null;
var setTimeLag = 2000;
var setTimeFlag2 = 2000;

var interValEchart = null;
var interValList = null;
var EchartFlag = 0;
var option = null;
var series = null;
var curNode = null;
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
	console.log(new Date().getTime());
	init();
	
	interValEchart =setInterval(function(){
		getLogicRoupResultByPage(2,false,1,function(valArr){
			if(valArr.length > 0){
				$("#show-roseImage-area,#main-area").addClass('hide');
				if(valArr[0].picSrc != null){
					var src = valArr[0].picSrc;
					$("#show-roseImage-area").removeClass('hide');
					$("#show-roseImage-picture").attr('src',CUR_PROJECT_IP + src);
					return;
				}
				$("#main-area").removeClass('hide');
				
 			for(var i =0;i< valArr.length;i++){
 				var dataObj = {};
 				var dataList = [];
 				if(valArr.length > 0){
 					for(var i =0;i< valArr.length;i++){
 						 
 						if(valArr[i].addTime !=null && valArr[i].result != null){
 							dataObj = {
 								name:new Date(valArr[i].addTime).toString(),
 								value:[new Date(valArr[i].addTime),valArr[i].result.toFixed(2)]
 							}
 							dataList.push(dataObj);
 						}
 					}
 					  myChart.setOption({
 					    	 series: [{
 					             data: dataList
 					         }]
 					    });
	 				}
	 			}
			}
		})
	},setTimeLag); 
	
 	interValList =setInterval(function(){
 		getLogicRoupResultByPage(1,true,1);//实时数据列表显示
	},setTimeLag);
 	
});

function init(){
	zTree = $.fn.zTree.getZTreeObj("treeBridge");
	$.fn.zTree.init($("#treeBridge"), setting, zNodes);
	$("#treeBridge").data("nodeList","[]");
	$('#show-logicResult').html("");
}
 
function onExpand(event,treeId,treeNode){
 	var nodeStr = (treeNode.id).toString();  
	var params = {
		"showType":"logicGroup",
		"nodeStr":nodeStr
	};
	$("#main-area").attr('cid','');
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

function chooseShowStyle(){
	setTimeout(function(){
		 var showStyle = $($('input[name="showStyle"]:checked').get(0)).attr('showStyle');
		 var showWay = $($('input[name="showWay"]:checked').get(0)).attr('showWay');
		 $("#echart-realTimeData,#echart-historyData,#list-realTimeData,#list-historyData,#search-logicRoupResult-list,#search-logicRoupResult-echart").addClass('hide');
		 $("#show-search-area").addClass('visible');
		 
		 if(showStyle == "realTimeData"){
			 EchartFlag = 0;
			 if(showWay == "echart"){
				 $("#echart-realTimeData").removeClass('hide');
			 }else if(showWay == "list"){
				 $("#list-realTimeData").removeClass('hide');
			 }
		 }else if(showStyle == "historyData"){
			 $("#show-search-area").removeClass('visible'); 
			 if(showWay == "echart"){
				 EchartFlag = 0;
				 $("#search-logicRoupResult-echart").removeClass('hide');
				 $("#echart-historyData").removeClass('hide');
				 getLogicRoupResultByPage(4,true,1);
			 }else if(showWay == "list"){
				 $("#search-logicRoupResult-list").removeClass('hide');
				 $("#list-historyData").removeClass('hide');
				 getLogicRoupResultByPage(3,true,1);
			 }
		 }
	},200);
}

function showChanelData(event,treeId,treeNode){
	var nodeStr = (treeNode.id).toString(); 
	var nodeType = nodeStr.split("_")[0];
	var nodeId = nodeStr.split("_")[1];
	curNode = treeNode;
	curChooseGroupNode = {
		logicGroupId:treeNode.Id,
		logicGroupOutputId:treeNode.logicGroupId
	};
	getParentNode(treeNode,"group");
	$('#main-area').attr({"nodeJson": JSON.stringify(curChooseGroupNode),'cid':nodeId});
	$("#show-roseImage-area,#main-area").addClass('hide');
	if(nodeType == "logicOutput"){
		EchartFlag  = 0;
		$("#echart-realTimeData").addClass('hide');
//		$("#main-area").removeClass('hide');
	}else{
		$("#main-area").addClass('hide');
	}
}
 
function getLogicRoupResultByPage(type,flag,page,fun){
	var startTime = "";
	var endTime = "";
	var index = 0;
		page = page || 1;
	var	curPage = 0;
	var pageSize = 0;
	var params = {};
	var curTime = new Date().getTime();
	var formTime = dateFormat(curTime);
	if(type == 1){  //实时数据  list
		var dateArr = judgeDateLocate();
		startTime = dateArr[0];
		endTime =  formTime;
		index = (page - 1) * 10;
		curPage = page;
		pageSize = pageSizeTen;
		gentPage({cur:curPage,p:$(".page-logicRoupResult-list")});
		
	}else if(type == 2){ //实时数据 echart
		var initTime = $("#echart-realTimeData").data("initTime");
		if(!isNaN(initTime)){
			if(formTime.substring(11,13) != initTime.substring(0,2)){
				EchartFlag = 0;
			}
		}
		var dateArr = judgeDateLocate();
		if(EchartFlag < 0){
			startTime = dateArr[0];
		}else{
			startTime = dateArr[0];
		}
		endTime =  formTime;
		pageSize = pageSizeLlong;
		
	}else if(type == 3){ // 静态数据 list history
		index = (page - 1) * 10;
		curPage = page;
		pageSize = pageSizeTen;
		gentPage({cur:curPage,p:$(".page-historyLogicRoupResult-list")});
	    startTime =  $("#start-date").val() != "" ? dateFormat($("#start-date").val()) : "-1";
	    endTime =  $("#end-date").val() != "" ? dateFormat($("#end-date").val()) : "-1";
	    
	}else if(type == 4){ //静态数据 echart history
		pageSize = 1000;
		startTime =  $("#start-date").val() != "" ? dateFormat($("#start-date").val()) : "-1";;
	    endTime =  $("#end-date").val() != "" ? dateFormat($("#end-date").val()) : "-1";
	}
	
	var logicGroupResultId = $("#main-area").attr('cid');
	var nodeJson = $('#main-area').attr("nodeJson") != null ? $('#main-area').attr("nodeJson") : "-1";
	
	params = {
			createTime:dateToLong(startTime).toString(),
			endTime:dateToLong(endTime).toString(),
			nodeJson:nodeJson.toString()
	};
	
	if(logicGroupResultId == ""){
		return;
	}
	
	if(type == 1 || type == 3){
		flag = 2; //倒序
	}else{
		flag = 1; //顺序
	}
	
	commonDataLoader(JSON.stringify(params),resInterface.getGroupResultList + "?index="+index+"&pageSize="+pageSize+"&flag="+flag+"" ,function(data){
		var valArr = data.rsData;
		var str = "";

		var nodeStr = (curNode.id).toString(); 
		var nodeType = nodeStr.split("_")[0];
		var nodeId = nodeStr.split("_")[1];
		if(nodeType != "logicOutput"){
			return;
		}
		if(valArr.length > 0){
			if(valArr[0].picSrc){
				var src = valArr[0].picSrc;
				$("#show-roseImage-area").removeClass('hide');
				$("#show-roseImage-picture").attr('src',CUR_PROJECT_IP + src);
				return;
			}
			$("#main-area").removeClass('hide');
			for(var i=0;i<valArr.length;i++){
				if(type == 1 || type == 3){
				str +='<tr cid='+valArr[i].id+'>'+
						'<td>'+dateFormat(valArr[i].addTime)+'</td>' +
						'<td>'+(valArr[i].result).toString().substring(0,4)+'</td>'+
					 '</tr>';
				} 
			}
			if(type == 1){
				$("#show-logicRoupResult-list").html(str);
			}else if(type == 3){
				$("#show-histroyLogicRoupResult-list").html(str);
			}
		}else{
			if(type == 1){
				$("#show-logicRoupResult-list").html("没有数据");
			}else if(type == 3){
				$("#show-histroyLogicRoupResult-list").html("没有数据");
			}
		}
		
		if(flag){
			var pageDom = "";
			if(type == 1){
				pageDom = $(".page-logicRoupResult-list");
			}else if(type == 3){
				pageDom = $(".page-historyLogicRoupResult-list");
			}
			if(type == 1 || type == 3){
				if(data.rsCount > 10){
					pageDom.removeClass('hide');
				}else{
					pageDom.addClass('hide');
				}
				gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:pageDom,event:"getLogicRoupResultByPage("+type+",false,"});
			}
		}
		
		 if(valArr.length > 0){
			 
			 var showStyle = $($('input[name="showStyle"]:checked').get(0)).attr('showStyle');
			 var showWay = $($('input[name="showWay"]:checked').get(0)).attr('showWay');
			 $("#echart-realTimeData,#echart-historyData,#list-realTimeData,#list-historyData").addClass('hide');
			 
			 if(showStyle == "realTimeData"){ //实时数据Echart
				 if(showWay == "echart"){
					 $("#echart-realTimeData").removeClass('hide');
				 }else if(showWay == "list"){  //实时数据列表
					 $("#list-realTimeData").removeClass('hide');
				 }
			 }else if(showStyle == "historyData"){
				 if(showWay == "echart"){  //静态数据Echart
					 $("#echart-historyData").removeClass('hide');
				 }else if(showWay == "list"){ //静态数据列表
					 $("#list-historyData").removeClass('hide');
				 }
			 }
			 
			 dateArr = [dateToLong(dateArr[0]),dateToLong(dateArr[1])]
    		if(EchartFlag < 1){
    			if(showStyle == "realTimeData" && type == 2){
        			EchartFlag ++;
        			initmonitorChart(valArr,"echart-realTimeData",dateArr);
    			}else if(showStyle == "historyData" && type == 4){
    				initmonitorChart(valArr,"echart-historyData",dateArr);
    			}
			}else{
				if(fun){
					fun(valArr);
				}
			}
    		
    		if(type == 5){
    			initmonitorChart(valArr,"echart-deatil");
    		}
		}
	});
}
 
function initmonitorChart(valArr,dom,dateArr){
	var dataList = [];
	var dataObj = {};
	var name =  null;
	var maxNum = 0;
	var minNum = 0;
	var thresholdArrTotalArr = [];
	var unit = "";
	var logicGroupOutputName = "";
	if(valArr.length > 0){
		$.each(valArr, function(i, v){
			if(v.logicGroupOutput != null){
				logicGroupOutputName = valArr[i].logicGroupOutput.name;
				if(v.logicGroupOutput.unit != null){
					unit = v.logicGroupOutput.unit;
				}
			}
			
			if(valArr[i].result != null && valArr[i].result > maxNum){
				maxNum = valArr[i].result;
			}
			
			if(valArr[i].result != null && valArr[i].result < minNum){
				minNum = valArr[i].result;
			}
			
			
			if(valArr[i].addTime !=null && valArr[i].result != null){
				dataObj = {
					name:new Date(valArr[i].addTime).toString(),
					value:[new Date(valArr[i].addTime),valArr[i].result.toFixed(2)]
				}
				dataList.push(dataObj);
			}
		 
			if(valArr[i].threshold != null){
				thresholdArrTotalArr = valArr[i].threshold.split(",");
			}
		});
		
//		if(dom == "echart-deatil"){
//			myChartDetail = echarts.init(document.getElementById(dom));
//		}else{
			myChart = echarts.init(document.getElementById(dom));
//			myChart.on(configSetting().EVENT.CLICK, eConsole);
//		}
	/** echarts start*/ 
		option = {
				title:{
				},
				tooltip : {
					trigger: 'axis',
					formatter: function (params) {
		                return dateFormat(params[0].value[0]) + ' - ' + params[0].value[1];
		            }
				},
				toolbox: {
					feature: {
			            dataZoom: {show: true},
			            restore: {show: true},
			            dataView: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
				xAxis : [
				         {
				        	 type: 'time',
				             splitLine: { show: false },
				             position:'bottom',
				             axisLine:{onZero:false}
				         	}
				         ],
				yAxis :[
				        {
				     	 type: 'value',
		                 name: unit,
		                 boundaryGap: [0, '100%'],
		                 splitLine: { show: false }
	                  }
				     ],
				     series : [{
	                	name:logicGroupOutputName,
	                	type:'line',
	                	data:dataList,
	                	showSymbol: false,
	                    hoverAnimation: false,
	                	itemStyle: {
	                         normal: {
	                             color: 'rgba(20,100,40,0.8)'
	                         }
	                     }
	                }  
	            ] 
			};   
		
		if(dateArr){
			option.xAxis[0].min  = dateArr[0];
			option.xAxis[0].max  = dateArr[1];
		}
		 
		 if(thresholdArrTotalArr.length > 0){
			 var markLine = {};
			 var arr = [];
			 var threadArr = [];
			 var val = null;
			 arr.push(thresholdArr1[0]);
			 arr.push(thresholdArr2[0]);
			 arr.push(thresholdArr3[0]);
			 arr.push(thresholdArr4[0]);
			 var obj = {};
			 var lineColor = '';
			 for(var i = 0;i<thresholdArrTotalArr.length ; i ++){
				 if(!isNaN(thresholdArrTotalArr[i])){
					 val = parseInt(thresholdArrTotalArr[i]);
					 if( i == 0 || i == 3){
						 lineColor = 'rgba(255,0,0,0.6)';
					 }else{
						 lineColor = 'rgba(255,255,0,0.6)';
					 }
						 if(dateArr){
							   threadArr.push([
							                   	{
								                   		coord:[dateArr[0],val],
								                   		value:val,
								                   		itemStyle:{normal:{color:lineColor}}
							                   	},{
								                   		coord:[dateArr[1],val],
								                   		value:val,
								                   		itemStyle:{normal:{color:lineColor}}
							                   	}
							                  ])
						 }
				 	}
			 	}
			 if(!isNaN(thresholdArrTotalArr[0])){
				 if(thresholdArrTotalArr[0] > maxNum){
					 maxNum = thresholdArrTotalArr[0];
				 }
				 option.yAxis[0].max = parseInt(maxNum) + Math.abs(maxNum) * 0.2;
			 }
			 
			 if(!isNaN(thresholdArrTotalArr[3])){
				 if(thresholdArrTotalArr[3] < minNum){
					 minNum = thresholdArrTotalArr[3];
				 }
				 option.yAxis[0].min = parseInt(minNum) - Math.abs(minNum) * 0.2;
			 }
			 
			 markLine.data = threadArr;
			 option.series[0].markLine = markLine;
		 }
		}
		
//		if(dom == "echart-deatil"){
//			if (option && typeof option === "object") {
//				myChartDetail.setOption(option, true);
//			}
//		}else{
			if (option && typeof option === "object") {
				myChart.setOption(option, true);
			}
//		}
} 

//function eConsole(param) {
//    var time = param.name;
//    $("#echart-deatil").attr("detailTime",time);
//    $("#detail-time").text(time);
//    $("#show-echart-point-data").modal("show");
//    getLogicRoupResultByPage(5,1);
//}
