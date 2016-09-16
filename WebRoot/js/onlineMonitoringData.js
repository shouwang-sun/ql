var zTree = {};
var myChart = null;
var myChartDetail = null;
var setTimeFlag = 2000;
var setTimeFlag2 = 2000;

var interValEchart = null;
var interValList = null;
var EchartFlag = 0;
var workSectionInterVal = null;
var sensorTypeInterVal = null;
var curNode = {};
var option = null;
var series = null;
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
	uploadSensorChannelData("#uploader-file",function(data){
		 console.log(data);
	});
	
	
	chooseSensorType();
	interValEchart = setInterval(function(){
		getChannelDataByPage(2,false,1,function(valArr){
			var dataObj = {};
			var dataList = [];
			if(valArr.length > 0){
				for(var i =0;i< valArr.length;i++){
					 
					if(valArr[i].time !=null && valArr[i].value != null){
						dataObj = {
							name:new Date(valArr[i].time).toString(),
							value:[new Date(valArr[i].time),valArr[i].value.toFixed(2)]
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
		})
	},setTimeFlag); 
	
 	interValList =setInterval(function(){
 		getChannelDataByPage(1,true,1);//实时数据列表显示
	},setTimeFlag); 
});

function chooseSensorType(){
	zTree = $.fn.zTree.getZTreeObj("treeBridge");
	$.fn.zTree.init($("#treeBridge"), setting, zNodes);
	$("#treeBridge").data("nodeList","[]");
	$('#show-chanData').html("");
	$("#main-area,#main-picture-area").addClass('hide');
}

function chooseShowStyle(){
	setTimeout(function(){
		 var showStyle = $($('input[name="showStyle"]:checked').get(0)).attr('showStyle');
		 var showWay = $($('input[name="showWay"]:checked').get(0)).attr('showWay');
		 $("#echart-realTimeData,#echart-historyData,#list-realTimeData,#list-historyData,#search-channelData-list,#search-channelData-echart").addClass('hide');
		 $("#show-search-area").addClass('visible');
		 if(showStyle == "realTimeData"){ //实时数据Echart
			 if(showWay == "echart"){
				 EchartFlag = 0;
				 $("#echart-realTimeData").removeClass('hide');
			 }else if(showWay == "list"){  //实时数据列表
				 $("#list-realTimeData").removeClass('hide');
			 }
		 }else if(showStyle == "historyData"){
			 $("#show-search-area").removeClass('visible'); 
			 if(showWay == "echart"){  //静态数据Echart
				 EchartFlag = 0;
				 $("#search-channelData-echart").removeClass('hide');
				 $("#echart-historyData").removeClass('hide');
				 getChannelDataByPage(4,true,1);
			 }else if(showWay == "list"){ //静态数据列表
				 $("#search-channelData-list").removeClass('hide');
				 $("#list-historyData").removeClass('hide');
				 getChannelDataByPage(3,true,1);
			 }
		 }
	},200);
}

//节点展开事件
function onExpand(event,treeId,treeNode){
//	1 --观测点类别 2,--观测点观测截面
//	init--初始化
// 	bridge--桥梁
// 	sen_type-- 观测点类型
// 	work_s ec-- 观测观测截面
// 	sen--观测点
// 	sen_chan--观测点通道
// 	chan_data--观测点数据
	var showType = $("input[name='showType']:checked").attr("showType");
 	var nodeStr = (treeNode.id).toString(); //bridge
	var params = {
		"showType":showType,
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

function showChanelData(event,treeId,treeNode){
	var nodeStr = treeNode.id; 
	var nodeType = nodeStr.split("_")[0];
	var nodeId = nodeStr.split("_")[1];
	var showType = $("input[name='showType']:checked").attr("showType");
	$("#main-area").attr('cid',nodeId);
	
	$("#select-sensorTypeList-all").prop('checked',false).closest('div').addClass('hide');
	$("#select-workSection-all").prop('checked',false);
	$("#show-sensorTypeById").addClass('hide').html("");
	$("#show-workSectionById").removeClass('hide').html("");
	$("#main-picture-area").find('div[po]').remove();
	
	if(nodeType == "senChan"){
		EchartFlag = 0;
		$("#echart-realTimeData").addClass('hide');
		$("#main-area").removeClass('hide');
		$("#main-picture-area").addClass('hide').attr('unit',treeNode.unit);
	}else{
		$("#main-area").addClass('hide');
		$("#main-picture-area").removeClass('hide').attr('unit','');
	} 
	window.clearInterval(workSectionInterVal);
	window.clearInterval(sensorTypeInterVal);
	
	if(nodeType == "bridge" && showType == "senWork"){
		if(nodeType == "bridge"){
			$("#select-workSection-all").closest('div').removeClass('hide');
			$("#select-sensorTypeList-all").closest('div').addClass('hide');
			$("#show-point-area").removeClass('hide');
			$("#show-point-sensor-area").addClass('hide');
			workSectionInterVal = setInterval(function(){
				drawImageByWorkSection(treeNode);
			},setTimeFlag2); //点击桥梁的时候给界面上的观测点通道添加观测点
		}else{
			$("#select-workSection-all").closest('div').addClass('hide');
			$("#select-sensorTypeList-all").closest('div').removeClass('hide');
			$("#show-point-area").addClass('hide');
			$("#show-point-sensor-area").removeClass('hide');
		}
	}
	
	if(nodeType == "workSec" && showType == "senWork"){
		sensorTypeInterVal = setInterval(function(){
			showSensorImgDraw(nodeId,treeNode.imageUrl);
		},setTimeFlag2); //点击观测截面的时候给观测点上的观测点通道添加定时器
	}
}

function drawImageByWorkSection(treeNode){
	var nodeStr = treeNode.id; 
	var nodeId = nodeStr.split("_")[1];
	var url = treeNode.imageUrl;
	var pageSize = pageSizeLlong;
	var index = 0;
	
	if(url != null){
		$("#front-worksection-picture-area").attr('src',CUR_PROJECT_IP + url);
	}
	
	var params = {
			"bridgeId": nodeId
		};
	
	commonDataLoader(null,resInterface.findSensorChannelAndChannelDataByBridgeId + "?id="+nodeId+"" ,function(data){
		var valArr = data.rsData;
		if(valArr.length > 0){
			var obj = valArr[0];
			showSensorChannelByBridgeId(obj);
		}
	});
}

function showSensorChannelByBridgeId(obj){
	var workSectionId = "";
	var pointX = "";
	var pointY = "";
	var str = "";
	var checkStr = "";
	var ifHide = "";
	var tag = false;
	if(obj.workSectionList.length > 0){
		initImage(obj.workSectionList,'#front-worksection-picture-area','point-work','work',1);
		$.each(obj.workSectionList,function(i,v){
			tag = false;
			workSectionId = v.id;
			pointX = parseFloat($("#point-work-"+workSectionId+"").attr("px"));
			pointY = parseFloat($("#point-work-"+workSectionId+"").attr("py"));
			str = "<div id='tb-work-"+workSectionId+"' class='p-table hide'><div class='small-tb-border' style='left:"+(pointX + 6)+"px;top:"+(pointY + 12)+ "px'></div><div class='small-tb' style='left:"+(pointX + 6)+"px;top:"+(pointY+31)+ "px' ><table class='table table-condensed table-bordered' cid="+workSectionId+"></div>";
			if(v.sensorList.length > 0){
				$.each(v.sensorList,function(ii,vv){
					if(vv.sensorChannelList.length > 0){
						$.each(vv.sensorChannelList,function(iii,vvv){
							if(vvv.latestChannelData.value != null){
								str +="<tr><td>"+vv.name + "-" + vvv.name+"</td><td>"+(vvv.latestChannelData.value).toString().substring(0,4)+"</td></tr>";
								tag = true;
							}
						})
					}
				})
			}
			str +="</table></div>";
			$("#work-" +workSectionId+"").append(str);
			if(!tag){
				$("#tb-work-" +workSectionId+"").remove();
			}else{
				var tb = $("#tb-work-" +workSectionId+"").find('.small-tb');
				var length = tb.find('tbody').children('tr').length;
				if(length > 0){
					tb.css('height',parseInt(tb.css('height'))* length + 1 +  "px");
				}
			}
			checkStr += '<div class="checkbox text-left" cid='+v.id+'><label><input type="checkbox"  onclick="chowChannelByWork(this,'+v.id+')"><span>'+v.name+'</span></label></div>';
		});
		
		if(checkStr != "" && $("#show-workSectionById").html() == ""){
			$("#show-workSectionById").html(checkStr);
		}else{
			var tg = $("#show-workSectionById").children('div');
			var tgc = "";
			for(var i = 0 ; i <tg.length;i++){
				tgc = tg.eq(i).find('input[type="checkbox"]');
				if(tgc.prop('checked') == true){
					$("#tb-work-" +parseInt(tg.eq(i).attr('cid'))+"").removeClass('hide');
				}
			}
		}
	}
}

function showSensorChannelByWorkSectionId(obj){
	var sensorId = "";
	var pointX = "";
	var pointY = "";
	var str = "";
	var checkStr = "";
	var tag = false;
	if(obj.sensorList.length > 0){
		initImage(obj.sensorList,'#front-sensor-picture-area','point-sensor','sensor',1);
		$.each(obj.sensorList,function(i,v){
			tag = false;
			sensorId = v.id;
			pointX = parseFloat($("#point-sensor-"+sensorId+"").attr("px"));
			pointY = parseFloat($("#point-sensor-"+sensorId+"").attr("py"));
			
			str = "<div id='tb-sensor-"+sensorId+"'  sensorTypeId="+v.sensorType.id+" class='p-table hide'><div class='small-tb-border' style='left:"+(pointX + 6)+"px;top:"+(pointY + 12)+ "px'></div><div class='small-tb' style='left:"+(pointX + 6)+"px;top:"+(pointY+31)+ "px' ><table class='table table-condensed table-bordered' cid="+sensorId+"></div>";
			if(v.sensorChannelList.length > 0){
				$.each(v.sensorChannelList,function(ii,vv){
					if(vv.latestChannelData.value != null){
						str +="<tr><td>"+v.name + "-" + vv.name+"</td><td>"+(vv.latestChannelData.value).toString().substring(0,4)+"</td></tr>";
						tag = true;
					}
				});
			}
			str +="</table></div>";
			$("#sensor-" +sensorId+"").append(str);
			if(!tag){
				$("#tb-sensor-" +sensorId+"").remove();
			}else{
				var tb = $("#sensor-" +sensorId+"").find('.small-tb');
				var length = tb.find('tbody').children('tr').length;
				if(length > 0){
					tb.css('height',parseInt(tb.css('height'))* length + 1 + "px");
				}
			}
			checkStr += '<div class="checkbox text-left" cid='+v.sensorType.id+'><label><input type="checkbox"  onclick="chowChannelBySensorType(this,'+v.sensorType.id+')"><span>'+v.sensorType.name+'</span></label></div>';
		});
		
		if(checkStr != "" && $("#show-sensorTypeById").html() == ""){
			$("#show-sensorTypeById").html(checkStr);
		}else{
			var tg = $("#show-sensorTypeById").children('div');
			var tgc = "";
			var typeId = "";
			for(var i = 0 ; i <tg.length;i++){
				tgc = tg.eq(i).find('input[type="checkbox"]');
				typeId = parseInt(tg.eq(i).attr('cid'));
				if(tgc.prop('checked') == true){
					$("#show-point-sensor-area").find('div[sensortypeid="'+typeId+'"]').removeClass('hide');
				}
			}
		}
	}
}

function chowChannelBySensorType(that,id){
	var checked = that.checked;
	if(id){
		if(checked){
			$("#show-point-sensor-area").find("div[sensorTypeId="+id+"]").removeClass('hide');
		}else{
			$("#show-point-sensor-area").find("div[sensorTypeId="+id+"]").addClass('hide');
		}
	}else{
		if(checked){
			$("#show-point-sensor-area").find(".p-table").removeClass('hide');
		}else{
			$("#show-point-sensor-area").find(".p-table").addClass('hide');
		}
		$("#show-sensorTypeById").children('div').find('input[type="checkbox"]').prop('checked',checked);
	}
}

function chowChannelByWork(that,id){
	var checked = that.checked;
	if(id){
		if(checked){
			$("#tb-work-"+id+"").removeClass('hide');
		}else{
			$("#tb-work-"+id+"").addClass('hide');
		}
	}else{
		if(checked){
			$("#show-point-area").find('.p-table').removeClass('hide');
		}else{
			$("#show-point-area").find('.p-table').addClass('hide');
		}
		$("#show-workSectionById").children('div').find('input[type="checkbox"]').prop('checked',checked);
	}
}

function getChannelDataByPage(type,flag,page,fun){
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
		var dateArr = judgeDateLocate("#echart-realTimeData");
		startTime = dateArr[0];
		endTime =  formTime;
		index = (page - 1) * 10;
		curPage = page;
		pageSize = pageSizeTen;
		gentPage({cur:curPage,p:$(".page-channelData-list")});
		
	}else if(type == 2){ //实时数据 echart
		var initTime = $("#echart-realTimeData").data("initTime");
		if(!isNaN(initTime)){
			if(formTime.substring(11,13) != initTime.substring(0,2)){
				EchartFlag = 0;
			}
		}
		var dateArr = judgeDateLocate("#echart-realTimeData");
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
		gentPage({cur:curPage,p:$(".page-historyChannelData-list")});
	    startTime =  $("#start-date").val() != "" ? dateFormat($("#start-date").val()) : "-1";
	    endTime =  $("#end-date").val() != "" ? dateFormat($("#end-date").val()) : "-1";
	    
	}else if(type == 4){ //静态数据 echart history
		pageSize = 1000;
		startTime =  $("#start-date").val() != "" ? dateFormat($("#start-date").val()) : "-1";;
	    endTime =  $("#end-date").val() != "" ? dateFormat($("#end-date").val()) : "-1";
	}
	
	var sensorChannelId = $("#main-area").attr('cid');
	params = {
			createTime:dateToLong(startTime).toString(),
			endTime:dateToLong(endTime).toString(),
			sensorChannelId:sensorChannelId				
	};
	
	if(sensorChannelId == ""){
		return;
	}
	
	if(type == 1 || type == 3){
		flag = 2; //倒序
	}else{
		flag = 1; //顺序
	}
	
	commonDataLoader(JSON.stringify(params),resInterface.getChannelDataList + "?index="+index+"&pageSize="+pageSize+"&flag="+flag+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(type == 1 || type == 3){
				str +='<tr cid='+valArr[i].id+'>'+
						'<td>'+dateFormat(valArr[i].time)+'</td>' +
						'<td>'+(valArr[i].value).toString().substring(0,4)+'</td>'+
					 '</tr>';
				} 
			}
			if(type == 1){
				$("#show-channelData-list").html(str);
			}else if(type == 3){
				$("#show-histroyChannelData-list").html(str);
			}
		}else{
			if(type == 1){
				$("#show-channelData-list").html("没有数据");
			}else if(type == 3){
				$("#show-histroyChannelData-list").html("没有数据");
			}
		}
		
		if(flag){
			var pageDom = "";
			if(type == 1){
				pageDom = $(".page-channelData-list");
			}else if(type == 3){
				pageDom = $(".page-historyChannelData-list");
			}
			if(type == 1 || type == 3){
				if(data.rsCount > 10){
					pageDom.removeClass('hide');
				}else{
					pageDom.addClass('hide');
				}
				gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:pageDom,event:"getChannelDataByPage("+type+",false,"});
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

function initImage(arr,dom,childDom,con,rate){
	$(dom).nextAll('div[po]').remove();
	if(arr.length == 0){
		return;
	}
	var pointX = "";
	var pointY = "";
	var cid = "";
	var span = "";
	var domId = "";
	var image = "";
	for(var i = 0; i < arr.length ;i ++){
		pointX = parseFloat(arr[i].positionX) * rate;
		pointY = parseFloat(arr[i].positionY) * rate;
		cid = arr[i].id;
		domId = childDom + "-" + cid;
		if(arr[i].image != "" && arr[i].image != null){
			image = arr[i].image;
		}
 		$span = $("<div po id='"+con+"-"+cid+"'><div onclick='showSensorImgDraw("+cid+",\""+image+"\",this)' title='"+arr[i].name+"' id="+domId+" class='img-point' px="+pointX+" py="+pointY+"></div></div>");
 		$(dom).after($span);
 		$('#' + childDom+'-'+cid+'').css({'position':'absolute','left':''+(pointX) +'px','top':''+(pointY)+'px'});
	}
}

function showSensorImgDraw(id,image,that){
	if(that){
		if($(that).attr('id').indexOf("sensor") != -1){
			return;
		}
	}
	$("#select-sensorTypeList-all").closest('div').removeClass('hide');
	$("#select-workSection-all").prop('checked',false).closest('div').addClass('hide');
 	$("#show-sensorTypeById").removeClass('hide');
	$("#show-workSectionById").addClass('hide').html("");
	
	$("#show-point-area").addClass('hide');
	$("#show-point-sensor-area").removeClass('hide');
	if(image != ""){
		$("#front-sensor-picture-area").attr("src",CUR_PROJECT_IP + image);
	}
	commonDataLoader(null,resInterface.findSensorChannelAndChannelDataByWorkSectionId + "?id="+id+"" ,function(data){
		var valArr = data.rsData;
		if(valArr.length > 0){
			var obj = valArr[0];
			showSensorChannelByWorkSectionId(obj);
		}
	});
}

//require.config({
//    paths:{ 
//        'echarts' : 'js/echarts'
//    }
//});	

function initmonitorChart(valArr,dom,dateArr){
//	require(["echarts/echarts"],function(ec){
		var dataList = [];
		var dataObj = {};
		var maxNum = 0;
		var minNum = 0;
		var name =  null;
		var thresholdArrTotalArr = [];
		var unit = $("#main-picture-area").attr('unit');
		var sensorChannelName = "";
		if(valArr.length > 0){
			$.each(valArr, function(i, v){
				if(valArr[i].value != null && valArr[i].value > maxNum){
					maxNum = valArr[i].value;
				}
				
				if(valArr[i].value != null && valArr[i].value < minNum){
					minNum = valArr[i].value;
				}
				
				sensorChannelName = valArr[i].sensorChannelName;
				if(valArr[i].time !=null && valArr[i].value != null){
					dataObj = {
						name:new Date(valArr[i].time).toString(),
						value:[new Date(valArr[i].time),valArr[i].value.toFixed(2)]
					}
					dataList.push(dataObj);
				}
				
				if(valArr[i].threshold != null){
					thresholdArrTotalArr = valArr[i].threshold.split(",");
				}
			});
			
//			if(dom == "echart-deatil"){
//				myChartDetail = echarts.init(document.getElementById(dom));
//			}else{
				myChart = echarts.init(document.getElementById(dom));
//				myChart.on(configSetting().EVENT.CLICK, eConsole);
//			}
			
		/** echarts start*/ 
		option = {
				title:{
				},
				tooltip : {
					trigger: 'axis',
					axisPointer: {animation: true},
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
				             splitLine: {show: false},
				             position:'bottom',
				             axisLine:{onZero:false}
				         	}
				         ],
				yAxis :[
				        {
				     	 type: 'value',
		                 name: unit,
		                 boundaryGap: [0, '100%'],
		                 splitLine: {show: false}
	                  }
				     ],
				     series : [{
	                	name:sensorChannelName,
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
			 var threadArr = [];
			 var val = null;
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
//	})
} 

//
//function eConsole(param) {
//     var time = param.name;
//     $("#echart-deatil").attr("detailTime",time);
//     $("#detail-time").text(time);
//     $("#show-echart-point-data").modal("show");
//     getChannelDataByPage(5,1);
//}

