$(function(){
	var oX = document.getElementById('map_x');
	var oY = document.getElementById('map_y');
	
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var map = new BMap.Map("baidu-map");
//	var point = new BMap.Point();
//	map.addControl(new BMap.OverviewMapControl({isOpen:true}));
//	var marker = new BMap.Marker(/point);  // 创建标注
	//map.addOverlay(marker);               // 将标注添加到地图中
	//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	
//	map.centerAndZoom(point,12); // 初始化地图,设置城市和地图级别。
//	var point = new BMap.Point(116.404, 39.915);
//	map.centerAndZoom(new BMap.Point(121.515306, 31.314653), 20);

 	map.addEventListener("click",function(e){
		//alert(e.point.lng + "," + e.point.lat);
 		oX.innerHTML = (e.point.lng).toFixed(4);
		oY.innerHTML = (e.point.lat).toFixed(4);
		
	});//单机获取点的坐标
	 //////////////////////////////////////////////////////////////////   
	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "suggestId"
		,"location" : map
	});

	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
	var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		setPlace();
		initMapData();
	});

	function setPlace(){
		map.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			$("#map_x").html(pp.lng);
			$("#map_y").html(pp.lat);
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp));    //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
	///////////////////////////////////////////////////////////////
	map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
	////////////////////////////////////////////////////////////////
	var navigationControl = new BMap.NavigationControl({
    // 靠左上角位置
    anchor: BMAP_ANCHOR_TOP_LEFT,
    // LARGE类型
    type: BMAP_NAVIGATION_CONTROL_LARGE,
    // 启用显示定位
    enableGeolocation: false
  });
  map.addControl(navigationControl);
  // 添加定位控件
  var geolocationControl = new BMap.GeolocationControl();
  geolocationControl.addEventListener("locationSuccess", function(e){
    // 定位成功事件
    var address = '';
    address += e.addressComponent.province;
    address += e.addressComponent.city;
    address += e.addressComponent.district;
    address += e.addressComponent.street;
    address += e.addressComponent.streetNumber;
    //alert("当前定位地址为：" + address);
  });    
  geolocationControl.addEventListener("locationError",function(e){
    // 定位失败事件
    //alert(e.message);
  });
//  map.addControl(geolocationControl);
  /////////////////////////////////////////////////////////////////
//  marker.enableDragging();//设置点可拖拽
  /////////////////////////////////////////////////////////////////   
  
 
  	var json_data=[];
  	var icon_good = new BMap.Icon("../ql/file/icon_green.png", new BMap.Size(25,25),{
    anchor: new BMap.Size(10, 10)
});
  	var icon_warning = new BMap.Icon("../ql/file/icon_yellow.png", new BMap.Size(25,25),{
    anchor: new BMap.Size(10, 10)
});
  	var icon_dangerous = new BMap.Icon("../ql/file/icon_red.png", new BMap.Size(25,25),{
    anchor: new BMap.Size(10, 10)
});
  	
//    content = "<span class='span_normal'>健康状况: </span><span id='span_good'>良好</span>";
//	  content = "<span class='span_normal'>健康状况: </span><span id='span_warning'> 警告</span>";
//	  content = "<span class='span_normal'>健康状况:  </span><span id='span_dangerous'>危险</span>";
  	initMapData();
  	function initMapData(){
  		var index = 0;
  		var pageSize = 100;
  		commonDataLoader(JSON.stringify(null),resInterface.getStructureWarningResultListGroupByBridgeId +"?index="+index+"&pageSize="+pageSize+"",function(data){
  	    	if(data.rsData.length > 0){
  	    		var info = data.rsData;
  	    		var str = "";
  	    		var marker = "";
  	    		var pointArray = new Array();
  	    		var firstPointX = info[0].bridge.positionX;
  	    		var firstPointY = info[0].bridge.positionY;
  	    		var firstpoint = new BMap.Point(firstPointX,firstPointY);
  	    		var firstContent = "";
  	    		var firstOpt = "";
  	    		var bridge = {};
  	    		var stList = [];
  	    		var pointx = "";
  				var pointy = "";
  				var healthyValue = "未统计";
  				var titleStyle= "";
  				var threshold = "";
  				var threshold1 = "";
  				var threshold2 = "";
  				var threshold3 = "";
  				
  	    		$.each(info,function(i,v){
  	    			var str = "";
  	    			var content = "";
  	    			bridge = v.bridge;
  	    			stList = v.list;
  	    			pointx = bridge.positionX;
  	    			pointy = bridge.positionY;
  	    			
  	    			if(v.evaluateProjectResult.length > 0){
  	    				if(v.evaluateProjectResult[0].healthyValue!= null){
  	    					healthyValue = parseFloat((v.evaluateProjectResult[0].healthyValue).toFixed(2));
  	    				}
  	    				if(v.evaluateProjectResult[0].evaluateProject.threshold != null){
  	  	    				threshold = v.evaluateProjectResult[0].evaluateProject.threshold;
  	  	    				threshold1 = parseFloat(threshold.split(",")[0]);
  	  	    				threshold2 = parseFloat(threshold.split(",")[1]);
  	  	    				threshold3 = parseFloat(threshold.split(",")[2]);
  	  	    				
  	  	    				if(healthyValue >= threshold3){
  	  	  	    				titleStyle ="<b class='green'> "+healthyValue+" 分</b>";
  	  	  						marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_good}); 
  	  	  						
  	  	  					}else if(healthyValue >= threshold2 && healthyValue < threshold3){
  	  	  						titleStyle ="<b class='yellow'> "+healthyValue+" 分</b>";
  	  	  						marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_warning}); 
  	  	  						
  	  	  					}else if(healthyValue >=threshold1 && healthyValue < threshold2){
  	  	  						titleStyle ="<b class='red'> "+healthyValue+" 分</b>";
  	  	  						marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_dangerous}); 
  	  	  						
  	  	  					}else if(healthyValue < threshold1){
  	  	  						titleStyle ="<b class='orange'> "+healthyValue+" 分</b>";
  	  	  						marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_dangerous}); 
  	  	  					}
  	  	    			}else{
  	  	    				marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_dangerous}); 
  	  	    			}
  	    			}else{
  	    				marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_dangerous}); 
  	    			}
  	    			
  	    			str += "<table class='table table-striped baidu-windowInfo'>";
  	    		    str += "<tr><th>名称</th><th>预警类型/名称</th><th>最后更新时间</th><th>处理结果</th></tr>";
  	    		    var result = "";
  	    		    var structureType = "";
  	    		    var structureName= "";
  	    		    
  	    		    if(stList.length > 0){
  	    		    	$.each(stList,function(k,j){
  	    		    		switch(j.dealResult){
  	    		    			case 1:
  	    		    				result = "已处理";
  	    		    			case 2:
  	    		    				result = "正在处理";
  	    		    			case 0:
  	    		    				result = "未处理";	
  	    		    		}
  	    		    		
  	    		    		if(j.logicGroupId != null){
  	    						if(j.logicGroup  && j.logicGroup.name && j.logicGroupOutput && j.logicGroupOutput.name){
  	    							structureType = j.logicGroup.name + "/" + j.logicGroupOutput.name;
  	    						}
  	    					}else{
  	    						if(j.sensor  && j.sensor.name && j.sensorChannel && j.sensorChannel.name){
  	    							structureType = j.sensor.name + "/" + j.sensorChannel.name;
  	    						}
  	    					}
  	    		    	 
  	    		    		structureName = j.structureWarning != null ? j.structureWarning.name : "";
  	    		    		str +="<tr>" +
  	    		    				"<td><a href='javascript:void(0)'  data-toggle='modal' data-target='#show-securityMonitoringInfo' onclick='showTotalInfo(this,"+j.id+")' ><span class='ellipsis width-9'>"+ structureName +"</span></a></td>" +
  	    		    				"<td><span class='ellipsis width-9'>"+structureType+"</span></td>" + 
  	    		    				"<td><span class='ellipsis width-9'>"+dateFormat(j.lastUpdateTime)+"</span></td>" +
  	    		    				"<td><span class='ellipsis width-9'>"+result+"</span></td>" +
  	    		    			  "</tr>";
  	        				});
  	    		    	}
  	    			str += "</table>";
  	    			content = str;
  	    			 var opts = {
  	    				width : 600,     // 信息窗口宽度
  	    				height: 200,     // 信息窗口高度
  	    				enableAutoPan : true, //自动平移
  	    				title : "<span>"+bridge.name+"</span><span>"+titleStyle+"</span>  </br>  <div style='border:1px #CCCCCC solid;'></div>"// 信息窗口标题
  	    				//enableMessage:true//设置允许信息窗发送短息
  	    			   };
  	    			  if(i == 0){
  	    				  firstContent = content;
  	    				  firstOpt = opts;
  	    			}
  	    			map.addOverlay(marker);
  	    			var point = new BMap.Point(pointx,pointy);
  	    			pointArray.push(point);
  	    			addClickHandler(content,marker,point,opts);
  	    			map.centerAndZoom(point,15); //设置视角		
  	    			//自动触发
  	    			//var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
  	    			 
  	    		})
  	    		map.setViewport(pointArray);
  	    		var infoWindow = new BMap.InfoWindow(firstContent,firstOpt);  // 创建信息窗口对象   展示第一座桥
  	    		map.openInfoWindow(infoWindow,firstpoint); //开启信息窗口
  	    		$(".baidu-window").closest('div').closest('div').css("overflow-y!important","scroll");
  	    		
  	    		map.addEventListener("tilesloaded", function () {
  	    			$("#baidu-map-loading").addClass('hide');
  	    		});
  	    	}
  	    });  
  	}
  
  	
	function addClickHandler(content,marker,point,opts){
    	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		marker.addEventListener("click",function(){
			  return map.openInfoWindow(infoWindow,point); //开启信息窗口
			}
		);
	}
	
	$("#show-structureWarningDealResult").on('change',function(){
		if($(this).val() != "1"){
			 $("#show-structureWarningDescription").prop("disabled",true);
		 }else{
			 $("#show-structureWarningDescription").prop("disabled",false);
		 }
	})
});

function showTotalInfo(that,id){
	$("#show-securityMonitoringInfo").modal("show");
	getSecurityWarningResultById(id);
}

function updateDealResultAndDescriptionById(){
	 var params  ={
			 id:parseInt($("#show-result-detail").attr('cid')),
			 description:$("#show-structureWarningDescription").val(),
			 dealResult:$("#show-structureWarningDealResult").val()
	 };
	 
	 commonDataLoader(JSON.stringify(params),resInterface.updateStructureWarningResultById,function(data){
		 if(data){
			 $("#show-securityMonitoringInfo").modal("hide");
		 }
	});
	 
}

function getSecurityWarningResultById(id){
	commonDataLoader(null,resInterface.getStructureWarningResultById +"?id="+id+"",function(data){
		if(data.rsData.length > 0){
			var data = data.rsData[0];
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
			 var level = data.level;
			 
			 if(data.level == 0){
				 levelStr ="<b class='green'>绿色</b>";
			 }else if(data.level == 1){
				 levelStr ="<b class='yellow'>黄色</b>";
			 }else if(data.level == 2){
				 levelStr ="<b class='red'>红色</b>";
			 } 
			 $("#show-structureWarningLevel").html(levelStr);
			 $("#show-result-detail").attr('cid',id);
			 $("#show-structureWarningName").text(structureWarningName);
			 $("#show-structureWarningType").text(typeStyle);
			 $("#show-structureWarningThreshold").text(threshold);
			 $("#show-structureWarningValue").text(value.toFixed(2));
			 $("#show-structureWarningStartTime").text(startTime);
			 $("#show-structureWarningLastUpdateTime").text(lastUpdateTime);
			 $("#show-structureWarningDealResult").val(dealResult);
			 $("#show-structureWarningDescription").val(description);
			 if($("#show-structureWarningDealResult").val() != "1"){
				 $("#show-structureWarningDescription").prop("disabled",true);
			 }
		}
	})
}
