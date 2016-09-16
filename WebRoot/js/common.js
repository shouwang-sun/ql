$(function(){
	$(".btn-group-option").on('click',function(){
		$(this).addClass('btn-group-option-active').siblings().removeClass('btn-group-option-active');
	})
})

function jumpPage(page){
	window.location.href = page +  ".html";
}

function transferDate(time){
	var dt = new Date(time.replace(/-/,"/")).getTime();
	return dt;
}

function judgeCheckTotal(dom1,dom2){
	var len = $(dom1).find('tr').find('td:eq(0)').find('input[type="checkbox"]:checked').length;
	if( len == 1){
		var curItemId = parseInt($($(dom1).find('tr').find('td:eq(0)').find('input[type="checkbox"]:checked').get(0).closest('tr')).attr('cid'));
		$(dom2).attr('disabled',false).attr('cur-item-id',curItemId);
	}else{
		$(dom2).attr('disabled',true);
	}
}


//上传
function uploadFile(dom,fun){
	$(dom).zinoUpload({
    	method: "POST",
    	url:UPLOAD_PIC_SRC,
        name: "fileData",
        label:" ",
        submit:function(event, ui){
//        	
        },
        complete: function (event, ui) {
        	if(ui.response.data && ui.response.data.rsData.length){
        		 var filename = ui.response.data.rsData[0];
        		 $(dom).attr("url",filename);
        		 var fileType = filename.split(".")[1];
        		 var fileSize = ui.file[0].files[0].size;
        		 var fileUrl = filename;
        		 var params = {
        				 fileName : filename.toString(),
        				 fileType:fileType,
        				 fileSize:fileSize,
        				 fileUrl:fileUrl.toString()
        		 };
        		 
        		 commonDataLoader(JSON.stringify(params),resInterface.insertDocument,function(data){
        				if(data.rsData.length > 0){
        					 if(fun){
        						 fun(CUR_PROJECT_IP + data.rsData[0].fileName);
        					 }
        				}
        		});
        	}
        }
	});
}


//临时上传文件 上传观测点通道文件
function uploadSensorChannelData(dom,fun){
	$(dom).zinoUpload({
    	method: "POST",
    	url:UPLOAD_SENSOR_CHANNEL_DATA_SRC,
        name: "fileData",
        label:" ",
        submit:function(event, ui){
//        	
        },
        complete: function (event, ui) {
        	/*if(ui.response.data && ui.response.data.rsData.length){
        		 var filename = ui.response.data.rsData[0];
        		 $(dom).attr("url",filename);
        		 var fileType = filename.split(".")[1];
        		 var fileSize = ui.file[0].files[0].size;
        		 var fileUrl = filename;
        		 var params = {
        				 fileName : filename.toString(),
        				 fileType:fileType,
        				 fileSize:fileSize,
        				 fileUrl:fileUrl.toString()
        		 };
        		 
        		 commonDataLoader(JSON.stringify(params),resInterface.insertDocument,function(data){
        				if(data.rsData.length > 0){
        					 if(fun){
        						 fun(CUR_PROJECT_IP + data.rsData[0].fileName);
        					 }
        				}
        		});
        	}*/
        }
	});
}

//筛选类型
function setInputByType(type,value){
	 var input = "";
	 if(value == -1){
		 value = "";
	 }
	 switch(type){
	 	case "Date":
	 		input = '<input type="text"  class="form-control inline " value="'+value +'" onclick="WdatePicker({maxDate:\'%y-%M-%d\'})">';
	 		break; 
		default :
			input = "<input type='text' class='form-control inline' value='"+value +"'>";
			break;
	 }
	 return input ;
}

//添加桥梁属性,文档字段
function addAttributeOnItem(dom1,dom2,dom3,that){
	var extraDom = "";
	var chooseCid = "";
	var selVal = $(dom3).children('td:eq(1)').children('input:not(".hide")').val();
	if(that){
		var cloneHtml = $("#add-manualCheck-field").find("#show-manualCheckObjectList").parent('td').clone();
		chooseCid = $(that).closest('td').prev('td').children('select').val();
		extraDom = "<td>"+cloneHtml.html()+"</td>";
	}
	 var name = $(""+dom1+" option:selected").text();
	 var val = $(dom1).val();
	 var type = $(""+dom1+" option:selected").attr("tp");
	 if(val != "-2"){
		 var input = setInputByType(type,selVal);
		 $(dom1).find('option[value='+val+']').addClass('hide');
		 $(dom2).find(dom3).before('<tr ad nm ='+name+' cid='+val+' tp='+type+'><td>'+name+'</td><td>'+input+'</td>'+extraDom+'<td><button onclick="removeLine(this,\''+dom1+'\',\''+dom3+'\')" class="btn btn-default">  <span class="glyphicon glyphicon-minus"></span></button></td></tr>');
	 }else{
		 return;
	 }
	 var selVal = "";
	 if($(dom1).find('option:not(".hide")').length > 1 ){
		 selVal = $(""+dom1+" option:not('.hide'):eq(0)").val();
	 }else{
		 selVal = $(""+dom1+" option:last").val();
	 }
	 $(""+dom1+"").val(selVal);
	 if(that){
		 $(that).closest('tr').prev('tr').children('td:eq(2)').children('select').val(chooseCid);
	 }
}



function removeLine (that,dom,dom2){
	var name = $(that).closest('tr').attr('nm');
	var val = parseInt($(that).closest('tr').attr('cid'));
	$(dom).find('option[nm='+name+']').removeClass('hide');
	$(that).closest('tr').addClass('hide');
	if(dom2){
		$($(dom2).find('select')[0]).val(val);
	}
}

function dateToLong(DateStr){
	return new Date(Date.parse(DateStr.replace(/-/g, "/"))).getTime();
}

function openUrl(that,flag){
	var url = $(that).attr("url");
	url = url.replace(/#/g,"%23");
	if(flag){
		
	}else{
		url = resInterface.downloadHistoryFiles + "?url="+url+"";
	}
	
	window.location.href = url;
}

function showOrHideDom(dom){
	$(dom).removeClass('hide').siblings().addClass('hide');
}

function getWebRoot(){
	var href = window.location.href;
	if(href.indexOf("/" + defaultAppName + "/", 4) > 0 ){
		return href.substring(0, href.indexOf("/" + defaultAppName + "/", 8) + defaultAppName.length + 2);
	}else{
		return href.substring(0, href.indexOf("/", 8) + 1);
	}
}

function lineAction(tag,clonedom,dom,that){
	 if(tag == 1){
		 var tr = $(clonedom).clone();
		 $(tr).removeClass('hide');
		 $(dom).append(tr);
	 }else if(tag == 0){
		 $(that).closest('tr').remove();
	 }
}

function getDeleteIds(dom){
	var ids = [];
	 $.each($(dom).children('tr').find('td:first').find('input[type="checkbox"]:checked'),function(i,v){
		 ids.push(parseInt($(v).closest('tr').attr('cid')));
	 })
	 return ids;
}

function getDeleteNames(dom){
	var nameList = [];
	 $.each($(dom).children('tr').find('td:first').find('input[type="checkbox"]:checked'),function(i,v){
		 nameList.push($.trim($(v).closest('tr').attr('name')));
	 })
	 return nameList;
}

function removeIdsStr(dom){
	 $.each($(dom).children('tr').find('td:first').find('input[type="checkbox"]:checked'),function(i,v){
		  $(v).closest('tr').remove();
	 })
}

function getAbsoluteLink(str){
	if(str && str != null){
		if(str.indexOf("http://") == 0){
			return str;
		}
		return getWebRoot() + str; 		
	}else{
		return str;
	}
}

function cleanValue(dom1,dom2){
	$(dom1).find(":text").val("");
	$(dom1).find("select").val('-1');
	$(dom1).find("textArea").val("");
	$(dom2).find(":checkbox").prop('checked',false);
}
 
function hideLine(that){
	$(that).closest('tr').addClass('hide');
}

function judgeDateLocate(dom){
	 var date = new Date();
	 var year = date.getFullYear();
	 var month = date.getMonth()+1;
	 var day = date.getDate(); 
	 var hour = date.getHours(); 
	 var minutes = date.getMinutes();
	 var second = date.getSeconds();
	 if(month < 10){
   	 month = "0" +month;
     }
	 
	 if(day < 10){
	     day = "0" +day;
	  }
	 
	 var time = second * 1000 + minutes * 60 * 1000 + hour * 60 * 60 * 1000;
	 var hour_1,hour_2,hour_3,hour_4,hour_5,hour_6,hour_7,hour_8,hour_9,hour_10,hour_11,hour_12,hour_13,hour_14,hour_15,hour_16,hour_17,hour_18,hour_19,hour_20,hour_21,hour_22,hour_23,hour_24;
	 hour_1 = 1000 * 60 * 60; 
	 hour_2 = 1000 * 60 * 60 * 2; 
	 hour_3 = 1000 * 60 * 60 * 3; 
	 hour_4 = 1000 * 60 * 60 * 4; 
	 hour_5 = 1000 * 60 * 60 * 5; 
	 hour_6 = 1000 * 60 * 60 * 6; 
	 hour_7 = 1000 * 60 * 60 * 7; 
	 hour_8 = 1000 * 60 * 60 * 8; 
	 hour_9 = 1000 * 60 * 60 * 9; 
	 hour_10 = 1000 * 60 * 60 * 10; 
	 hour_11 = 1000 * 60 * 60 * 11; 
	 hour_12 = 1000 * 60 * 60 * 12; 
	 hour_13 = 1000 * 60 * 60 * 13; 
	 hour_14 = 1000 * 60 * 60 * 14; 
	 hour_15 = 1000 * 60 * 60 * 15; 
	 hour_16 = 1000 * 60 * 60 * 16; 
	 hour_17 = 1000 * 60 * 60 * 17; 
	 hour_18 = 1000 * 60 * 60 * 18; 
	 hour_19 = 1000 * 60 * 60 * 19; 
	 hour_20 = 1000 * 60 * 60 * 20; 
	 hour_21 = 1000 * 60 * 60 * 21; 
	 hour_22 = 1000 * 60 * 60 * 22; 
	 hour_23 = 1000 * 60 * 60 * 23; 
	 hour_24 = 1000 * 60 * 60 * 24; 
	var arr = [];
	if(time >= 0 && time < hour_1){
		arr[0] = "00:00:00";
		arr[1] = "01:00:00";
	}else if(time >= hour_1 && time < hour_2){
		arr[0] = "01:00:00";
		arr[1] = "02:00:00";
	}else if(time >= hour_2 && time < hour_3){
		arr[0] = "02:00:00";
		arr[1] = "03:00:00";
	}else if(time >= hour_3 && time < hour_4){
		arr[0] = "03:00:00";
		arr[1] = "04:00:00";
	}else if(time >= hour_4 && time < hour_5){
		arr[0] = "04:00:00";
		arr[1] = "04:00:00";
	}else if(time >= hour_5 && time < hour_6){
		arr[0] = "05:00:00";
		arr[1] = "06:00:00";
	}else if(time >= hour_6 && time < hour_7){
		arr[0] = "06:00:00";
		arr[1] = "07:00:00";
	}else if(time >= hour_7 && time < hour_8){
		arr[0] = "07:00:00";
		arr[1] = "08:00:00";
	}else if(time >= hour_8 && time < hour_9){
		arr[0] = "08:00:00";
		arr[1] = "09:00:00";
	}else if(time >= hour_9 && time < hour_10){
		arr[0] = "09:00:00";
		arr[1] = "10:00:00";
	}else if(time >= hour_10 && time < hour_11){
		arr[0] = "10:00:00";
		arr[1] = "11:00:00";
	}else if(time >= hour_11 && time < hour_12){
		arr[0] = "11:00:00";
		arr[1] = "12:00:00";
	}else if(time >= hour_12 && time < hour_13){
		arr[0] = "12:00:00";
		arr[1] = "13:00:00";
	}else if(time >= hour_13 && time < hour_14){
		arr[0] = "13:00:00";
		arr[1] = "14:00:00";
	}else if(time >= hour_14 && time < hour_15){
		arr[0] = "14:00:00";
		arr[1] = "15:00:00";
	}else if(time >= hour_15 && time < hour_16){
		arr[0] = "15:00:00";
		arr[1] = "16:00:00";
	}else if(time >= hour_16 && time < hour_17){
		arr[0] = "16:00:00";
		arr[1] = "17:00:00";
	}else if(time >= hour_17 && time < hour_18){
		arr[0] = "17:00:00";
		arr[1] = "18:00:00";
	}else if(time >= hour_18 && time < hour_19){
		arr[0] = "18:00:00";
		arr[1] = "19:00:00";
	}else if(time >= hour_19 && time < hour_20){
		arr[0] = "19:00:00";
		arr[1] = "20:00:00";
	}else if(time >= hour_20 && time < hour_21){
		arr[0] = "20:00:00";
		arr[1] = "21:00:00";
	}else if(time >= hour_21 && time < hour_22){
		arr[0] = "21:00:00";
		arr[1] = "22:00:00";
	}else if(time >= hour_22 && time < hour_23){
		arr[0] = "22:00:00";
		arr[1] = "23:00:00";
	}else if(time >= hour_23 && time < hour_24){
		arr[0] = "23:00:00";
		arr[1] = "00:00:00";
	}
		if(EchartFlag < 0){
			$(dom).data("initTime",arr[0]);
		}
		arr[0] = year+"-"+month+"-"+day+" "+arr[0];
		arr[1] = year+"-"+month+"-"+day+" "+arr[1];
		return arr;
}

function getParentNode(node,type){
	var itemId = node.id.split("_")[1];
	var itemType = node.id.split("_")[0];
	if(type == "channel"){
		switch(itemType){
			case "bridge":
				curChooseNode["bridgeId"] = itemId;
				break;
			case "senType":
				curChooseNode["sensorTypeId"] = itemId;
				break;
			case "workSec":
				curChooseNode["workSectionId"] = itemId;
				break;
			case "sen":
				curChooseNode["sensorId"] = itemId;
				break;
			case "senChan":
				curChooseNode["sensorChannelId"] = itemId;
				break;
			default:break;
		}
	}else if(type == "group"){
		switch(itemType){
			case "bridge":
				curChooseGroupNode["bridgeId"] = itemId;
				break;
			case "logicGroup":
				curChooseGroupNode["logicGroupId"] = itemId;
				break;
			case "logicOutput":
				curChooseGroupNode["logicGroupOutputId"] = itemId;
				break;
			default:break;
		}
	}else if(type == "structureWarningResult"){
		switch(itemType){
		case "bridge":
			curStructureWarningResultNode["bridgeId"] = itemId;
			break;
		default:break;
		}
	}
	
	
	if(node == null || itemType == "init"){
		return;
	}else{
		getParentNode(node.getParentNode(),type);
	}
}

function gentPage(pageParms){
	var total,event;
	if(pageParms.data || pageParms.data == 0 ){
		$(pageParms.p).data("total",pageParms.data);
		total = pageParms.data;
		$(pageParms.p).data("event",pageParms.event);
		event = pageParms.event;
	}else{
		total = $(pageParms.p).data("total");
		event = $(pageParms.p).data("event");
		if(total == null){
			total = 1;
		}
	}
	setNormalPage({total:total,cur:pageParms.cur,p:pageParms.p,tag:"li",curtag:"active",splite:"",event:event});
}

function setNormalPage(pageParms) {
	//var totalitem = pageParms.totalitem;
	var total = pageParms.total;
	var all = pageParms.all || 9;
	var half = parseInt(all / 2);
	var first = 1;
	var last = total;
	var center = parseInt(pageParms.cur);
	var cur = parseInt(pageParms.cur);
	var tag = pageParms.tag || "span";
	var curtag = pageParms.curtag || 1;
	var splite = "&nbsp;&nbsp;";
	if(typeof(pageParms.splite) != undefined){
		splite = pageParms.splite;
	}
	if (last > all) {
		if (center - half > 1) {
			first = center - half;
		}
		if (last - center < half) {
			first = last - all + 1;
		} else {
			last = first + all - 1;
		}
	}
	list = '';
	if (first != cur) {
		list += '<'+tag+' class="page_text"><a href="javascript:void(0);" onclick="'
				+ pageParms.event
				+ '1,this)">首页</a></'+tag+'>'+splite+'<'+tag+' class="page_text"><a href="javascript:void(0);" onclick="'
				+ pageParms.event + (cur - 1) + ',this)">上一页</a></'+tag+'>'+splite+splite+'';
	} else {
		list += '<'+tag+' class="page_text"><a href="javascript:void(0);">首页</a></'+tag+'>'+splite+'<'+tag+' class="page_text"><a href="javascript:void(0);">上一页</a></'+tag+'>'+splite+splite+'';
	}
	if (pageParms.total == 0) {
		list += '<'+tag+' class="page_focus '+curtag+'"><a href="javascript:void(0);">1</a></'+tag+'>'+splite+'';
	} else {
		for ( var i = first; i <= last; i++) {
			if (i == cur) {
				list += '<'+tag+' class="page_focus '+curtag+'"><a href="javascript:void(0);">'
					+ i + '</a></'+tag+'>'+splite+'';
			} else {
				list += '<'+tag+' class="page_num"><a href="javascript:void(0);" onclick="'
						+ pageParms.event + i + ',this)">' + i + '</a></'+tag+'>'+splite+'';
			}
		}
	}
	if (total != cur) {
		list += splite+'<'+tag+' class="page_text"><a href="javascript:void(0);" onclick="'
				+ pageParms.event
				+ (cur + 1)
				+ ',this)">下一页</a></'+tag+'>'+splite+'<'+tag+' class="page_text"><a href="javascript:void(0);" onclick="'
				+ pageParms.event + total + ',this)">末页</a></'+tag+'>';
	} else {
		list += splite+'<'+tag+' class="page_text"><a href="javascript:void(0);">下一页</a></'+tag+'>'+splite+'<'+tag+' class="page_text"><a href="javascript:void(0);">末页</a></'+tag+'>';
	}
	pageParms.p.empty().append(list);
}