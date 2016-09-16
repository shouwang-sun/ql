var res = "res/";
var curPage = 1;
var pageSize = 10;
var pageSizeTen = 10;
var pageSizeLlong = 99999999;
var curChooseNode = {};
var curChooseGroupNode = {};
var curStructureWarningResultNode = {};

var locationHref = window.location.href;
	if(locationHref.indexOf("facilityManagement")!= -1){
//		return;
	}else{
		var height = document.documentElement.clientHeight || document.body.clientHeight;
		height = height - document.getElementById("top-navbar").offsetHeight - 40;
		$(".main-cintainer-left").css('height',height + "px");
		$(".main-cintainer-right").css('height',height + "px");
		if(location.href.indexOf("index" != -1)){
			$("#baidu-map").css('height',(height - 27) + "px");
		}
		$('#footer').removeClass('hide'); 
	}

	window.onresize = function(){
		var width = document.documentElement.clientWidth || document.body.clientWidth;
		if(width < 746){
 			$('#navbar').removeClass('visible-lg');
		}else{
			$('#navbar').addClass('visible-lg');
		}
		
		var height = document.documentElement.clientHeight || document.body.clientHeight;
		height = height - document.getElementById("top-navbar").offsetHeight - 40;
		$(".main-cintainer-left").css('height',height + "px");
		$(".main-cintainer-right").css('height',height + "px");
		if(location.href.indexOf("index" != -1)){
			$("#baidu-map").css('height',(height - 27) + "px");
		}
	}


//var UPLOAD_PIC_SRC = "http://192.168.1.122:8080/ql/res/file/uploadFile";
//var CUR_PROJECT_IP ="http://192.168.1.122:8080/ql/file/";
	
//	windows
//	var UPLOAD_PIC_SRC = "http://192.168.1.114:8080/ql/res/file/uploadFile";
//	var UPLOAD_SENSOR_CHANNEL_DATA_SRC = "http://192.168.1.114:8080/ql/res/file/uploadSensorChannelData";
//	var CUR_PROJECT_IP ="http://192.168.1.114:8080/ql/file/";
	
//	linux
	var UPLOAD_PIC_SRC = "http://121.42.9.83:8080/ql/res/file/uploadFile";
	var UPLOAD_SENSOR_CHANNEL_DATA_SRC = "http://121.42.9.83:8080/ql/res/file/uploadSensorChannelData";
	var CUR_PROJECT_IP ="http://121.42.9.83:8080/ql/file/";
	
//var CUR_IMG_URL = "F:/Java/apache-tomcat-7.0.64/webapps/ql/file/pic";

	
var resInterface = {
		buildTree:res + "bridge/buildTree",
		getBridgeList:res +"bridge/getBridgeList",
		insertBridge:res + "bridge/insertBridge",
		findBridgeById: res + "bridge/findBridgeById",
		updateBridgeById: res + "bridge/updateBridgeById",
		deleteBridgeByIds: res + "bridge/deleteBridgeByIds",
		findSensorChannelAndChannelDataByBridgeId:res + "bridge/findSensorChannelAndChannelDataByBridgeId",
		
		getAttributeList:res + "attribute/getAttributeList",
		changeAttribute:res +"attribute/changeAttribute",
		deleteAttributeByName :res + "attribute/deleteAttributeByName",
		
		getSensorTypeAttributeList:res + "sensorTypeAttribute/getSensorTypeAttributeList",
		changeSensorTypeAttribute:res +"sensorTypeAttribute/changeSensorTypeAttribute",
		deleteSensorTypeAttributeByName :res + "sensorTypeAttribute/deleteSensorTypeAttributeByName",
		
		getManualCheckFieldList:res +"manualCheckField/getManualCheckFieldList",
		changeManualCheckField:res +"manualCheckField/changeManualCheckField",
		deleteManualCheckFieldByName:res + "manualCheckField/deleteManualCheckFieldByName",
		
		getManualCheckObjectList:res +"manualCheckObject/getManualCheckObjectList",
		deleteManualCheckObjectByName:res + "manualCheckObject/deleteManualCheckObjectByName",
		insertManualCheckObject:res +"manualCheckObject/insertManualCheckObject",
		updateManualCheckObjectById:res +"manualCheckObject/updateManualCheckObjectById",
		
		getManualCheckDataList:res + "manualCheckData/getManualCheckDataList",
		insertManualCheckData:res+"manualCheckData/insertManualCheckData",
		findManualCheckDataById:res+"manualCheckData/findManualCheckDataById",
		updateManualCheckDataById:res+"manualCheckData/updateManualCheckDataById",
		deleteManualCheckDataByIds:res+"manualCheckData/deleteManualCheckDataByIds",
		
		getWorkSectionList:res + "workSection/getWorkSectionList",
		insertWorkSecton:res + "workSection/insertWorkSecton",
		findWorkSectionById: res + "workSection/findWorkSectionById",
		updateWorkSectionById: res + "workSection/updateWorkSectionById",
		deleteWorkSectionByIds:res + "workSection/deleteWorkSectionByIds",
		showWorksectionNextChildType:res +  "workSection/showWorksectionNextChildType",
		findSensorChannelAndChannelDataByWorkSectionId:res + "workSection/findSensorChannelAndChannelDataByWorkSectionId",
		
		getSensorList:res + "sensor/getSensorList",
		insertSensor:res + "sensor/insertSensor",
		findSensorById: res + "sensor/findSensorById",
		updateSensorById: res + "sensor/updateSensorById",
		deleteSensorByIds:res + "sensor/deleteSensorByIds",
		
		getSensorTypeList:res + "sensorType/getSensorTypeList",
		insertSensorType:res + "sensorType/insertSensorType",
		findSensorTypeById: res + "sensorType/findSensorTypeById",
		updateSensorTypeById: res + "sensorType/updateSensorTypeById",
		deleteSensorTypeByIds:res + "sensorType/deleteSensorTypeByIds",
		
		getSensorChannelList:res + "sensorChannel/getSensorChannelList",
		changeSensorChannel :res +"sensorChannel/changeSensorChannel",
		findSensorChannelById: res + "sensorChannel/findSensorChannelById",
		updateSensorChannelLogicGroupIdByIds :res + "sensorChannel/updateSensorChannelLogicGroupIdByIds",
		updateSensorChannelThresholdById :res + "sensorChannel/updateSensorChannelThresholdById",
		
		getLogicGroupList:res + "logicGroup/getLogicGroupList",
		insertLogicGroup:res + "logicGroup/insertLogicGroup",
		findLogicGroupById: res + "logicGroup/findLogicGroupById",
		updateLogicGroupById : res +"logicGroup/updateLogicGroupById",
		deleteLogicGroupByIds:res + "logicGroup/deleteLogicGroupByIds",
		
		getStructureWarningList:res + "structureWarning/getStructureWarningList",
		insertStructureWarning:res +"structureWarning/insertStructureWarning",
		deleteStructureWarningByName :res + "structureWarning/deleteStructureWarningByName",
		deleteStructureWarningByIds :res + "structureWarning/deleteStructureWarningByIds",
		updateStructureWarningByName : res +"structureWarning/updateStructureWarningByName",
		updateStructureWarningById : res +"structureWarning/updateStructureWarningById",
		
		getChannelDataList:res + "channelData/getChannelDataList",
		
		getEvaluateProjectList:res + "evaluateProject/getEvaluateProjectList",
		changeEvaluateProject:res +"evaluateProject/changeEvaluateProject",
		findEvaluateProjectById: res + "evaluateProject/findEvaluateProjectById",
		updateEvaluateProjectById:res + "evaluateProject/updateEvaluateProjectById",
		deleteEvaluateProjectByIds:res + "evaluateProject/deleteEvaluateProjectByIds",
		
		getEvaluateProjectResultYear:res +"evaluateProjectResult/getEvaluateProjectResultYear",
		getEvaluateProjectResultMonth:res +"evaluateProjectResult/getEvaluateProjectResultMonth",
		getEvaluateProjectResultList:res +"evaluateProjectResult/getEvaluateProjectResultList",
		
		getChannelDataHistoryFileList:res + "channelDataHistory/getChannelDataHistoryFileList",
		
		downloadHistoryFiles:res + "file/downloadHistoryFiles",//下载
		getGroupResultList:res + "logicGroupResult/getLogicResultList",
		
		getLogicGroupOutputList:res + "logicGroupOutput/getLogicGroupOutputList",
		changelogicGroupOutput :res + "logicGroupOutput/changelogicGroupOutput",
		
		getLogicGroupConstantList:res +"logicGroupConstant/getLogicGroupConstantList",
		changelogicGroupConstant:res +"logicGroupConstant/changelogicGroupConstant",
		
		getStructureWarningResultList:res + "structureWarningResult/getStructureWarningResultList",
		updateStructureWarningResultById:res + "structureWarningResult/updateStructureWarningResultById",
		getStructureWarningResultListGroupByBridgeId :res + "structureWarningResult/getStructureWarningResultListGroupByBridgeId",
		getStructureWarningResultById:res +"structureWarningResult/getStructureWarningResultById",
		
		insertDocument : res + "document/insertDocument",
		getDocumentList : res + "document/getDocumentList",
		deleteDocumentByIds :res + "document/deleteDocumentByIds",
		findDocumentTypeList:res +"document/findDocumentTypeList",
		
		getUserList:res +"user/getUserList",
		insertUser:res +"user/insertUser",
		findUserById:res +"user/findUserById",
		updateUserById:res +"user/updateUserById",
		deleteUserByIds:res +"user/deleteUserByIds",
		
		getLogicGroupSensorChannelList : res + "logicGroupSensorChannel/getLogicGroupSensorChannelList",
		changeLogicGroupSensorChannel:res + "logicGroupSensorChannel/changeLogicGroupSensorChannel"
		
		
};

$(".total-check").on('click',function(){
	$(this).closest('thead').next('tbody').find('input[type="checkbox"]').prop("checked",this.checked);
})
 

//空白处不关闭,esc键盘不关闭
//$('.modal').modal({backdrop: 'static', keyboard: false});

function commonDataLoader(params,url,callback){
		$.ajax({
	        type: "POST",
	        data:{
	        	params:params
	        },
	        url: url,
	        cache: false,
	        success : function(data){
	        	data = dealJsonCallback(data);
	        	if(data){
	        		if(typeof(callback) != "undefined"){
	        			callback(data);
	        		}
	        	}
	        }
	    });
}

function dealJsonCallback(data){
	if(typeof(data) == "string"){
		try{
			data = JSON.parse(data);
		}catch(e){
			return data.toString();
		}
		
	}
	
	if(data.code){
		if(data.code == 200){
			if(!data.data && data.data != 0){
				return true;
			}
			if(data.data.length == 0){
				return true;
			}
			return data.data;
		}else if(data.code == 400){
			return data.code;
		}else{
			if(parent){
				alert(data.msg);
//				parent.showToast(data.msg);
			}else{
				alert(data.msg);
//				showToast(data.msg);	
			}
			return false;
		}
	}else{
		return data;
	}
}

function dateFormat(date){
	var d = new Date(date);
    var year = d.getFullYear();
    var month = d.getMonth()+1;
    if(month < 10){
    	month = "0" +month;
    }
    var day = d.getDate(); 
    if(day < 10){
    	day = "0" +day;
    }
    var hour = d.getHours(); 
    if(hour < 10){
    	hour = "0" +hour;
    }
    var minutes = d.getMinutes();
    if(minutes < 10){
    	minutes = "0" +minutes;
    }
    var second = d.getSeconds();
    if(second < 10){
    	second = "0" +second;
    }
    date= year+"-"+month+"-"+day+" "+hour+":"+minutes +":"+second;
    return date;
}

