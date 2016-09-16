function openMap(){
		baiduMapCount ++;
		if(baiduMapCount > 1){
			return;
		}
		var oX = document.getElementById('map_x');
		var oY = document.getElementById('map_y');

		var map = new BMap.Map("baidu-map");
//		var point = new BMap.Point();
//		map.addControl(new BMap.OverviewMapControl({isOpen:true}));
//		var marker = new BMap.Marker(/point);  // 创建标注
		//map.addOverlay(marker);               // 将标注添加到地图中
		//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		
//		map.centerAndZoom(point,12); // 初始化地图,设置城市和地图级别。
//		var point = new BMap.Point(116.404, 39.915);
//		map.centerAndZoom(new BMap.Point(121.515306, 31.314653), 20);

	 	map.addEventListener("click",function(e){
			//alert(e.point.lng + "," + e.point.lat);
			oX.innerHTML = (e.point.lng).toFixed(4);
			oY.innerHTML = (e.point.lat).toFixed(4);
			
		});//单机获取点的坐标
		 //////////////////////////////////////////////////////////////////    
//	 	$("#map-input").html('<input type="text" id="autoInput" placeholder=" ===  请输入地址  ===" class="form-control" /><br/><span>位置</span> X:<span id="map_x"></span> Y:<span id="map_y"></span>');
	 	
		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "suggestId"
			,"location" : map
		});

		ac.addEventListener("onhighlight", function(e) {//鼠标放在下拉列表上的事件
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
	  	var icon_good = new BMap.Icon("../ql/file/icon_green.png", new BMap.Size(15,15),{
	    anchor: new BMap.Size(10, 10)
	});
	  	var icon_warning = new BMap.Icon("../ql/file/icon_yellow.png", new BMap.Size(15,15),{
	    anchor: new BMap.Size(10, 10)
	});
	  	var icon_dangerous = new BMap.Icon("../ql/file/icon_red.png", new BMap.Size(15,15),{
	    anchor: new BMap.Size(10, 10)
	});
	  	
//	    content = "<span class='span_normal'>健康状况: </span><span id='span_good'>良好</span>";
//		  content = "<span class='span_normal'>健康状况: </span><span id='span_warning'> 警告</span>";
//		  content = "<span class='span_normal'>健康状况:  </span><span id='span_dangerous'>危险</span>";
	  	initMapData();
	  	function initMapData(){
	  		commonDataLoader(JSON.stringify(null),resInterface.getStructureWarningResultListGroupByBridgeId,function(data){
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
		    		$.each(info,function(i,v){
		    			var str = "";
		    			var content = "";
		    			bridge = v.bridge;
		    			stList = v.list;
		    			pointx = bridge.positionX;
		    			pointy = bridge.positionY;
		    			if(v.evaluateProjectResult[0].healthyValue!= null){
		    				healthyValue = (v.evaluateProjectResult[0].healthyValue).toFixed(2);
		    			}
		    			
		    			if(healthyValue >= 90){
		    				titleStyle ="<b style='color:green'> "+healthyValue+" 分</b>";
							marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_good}); 
						}
						else if(healthyValue >= 70 && healthyValue < 90){
							titleStyle ="<b style='color:warning'> "+healthyValue+" 分</b>";
							marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_warning}); 
						}
						else if(healthyValue < 70){
							titleStyle ="<b style='color:red'> "+healthyValue+" 分</b>";
							marker = new BMap.Marker(new BMap.Point(pointx,pointy),{icon:icon_dangerous}); 
						}
	 	    			content ="<span>经度:"+pointx+"</span><br><span>纬度"+pointy+"</span>";
		    			 var opts = {
		    				width : 200,     // 信息窗口宽度
		    				height: 50,     // 信息窗口高度
		    				enableAutoPan : true, //自动平移
		    				title :"<span><b>"+bridge.name+"</b></span>"// 信息窗口标题
		    				//enableMessage:true//设置允许信息窗发送短息
		    			   };
		    			map.addOverlay(marker);
		    			var point = new BMap.Point(pointx,pointy);
		    			pointArray.push(point);
		    			addClickHandler(content,marker,point,opts);
		    			map.centerAndZoom(point,15); //设置视角		
		    			//自动触发
		    			//var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		    			 
		    		})
		    		map.setViewport(pointArray);
		    		map.addEventListener("tilesloaded", function () {
		    			$("#baidu-map-loading").addClass('hide');
//		    			$("#baiduMap-out-div,#baidu-map,.BMap_mask").css('height','600px');
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
}


// 百度地图API功能
function G(id) {
	return document.getElementById(id);
}
 
/*function addClickHandler(content,marker,point,opts){
	 marker.addEventListener("click",function(event){ 
	    	alert(1);
		})
}*/