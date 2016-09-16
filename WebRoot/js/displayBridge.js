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
			onClick: showLeftData
		}
};

var zNodes =[{ id:'#display-bridge', pId:0, name:'自定义桥梁', isParent:false , open:false},
             { id:'#display-workSection', pId:0, name:'自定义观测截面', isParent:false , open:false},
             { id:'#display-sensor', pId:0, name:'自定义观测点', isParent:false , open:false}];

$(function(){
	zTree = $.fn.zTree.getZTreeObj("treeBridge");
	$.fn.zTree.init($("#treeBridge"), setting, zNodes);
	getByBridgeListByPage(1,true,1);
	uploadFile("#uploader-file",function(data){
		$("#show-bridgeImg").attr('src',data);
	});
	
	$("#show-attributeList").on('change',function(){
		
		if(parseInt($(this).val()) == -2){
			$("#add-attribute").modal('show');
			getAttributeListByPage(true,1);
			$(this).val($(this).children('option:not(.hide):eq(0)').val());
		}
		
		if($("#show-attributeList").children('option[value="'+$(this).val()+'"]').attr('tp') == "Date"){
			$("#add-bridge-attribute").children('td:eq(1)').children('input[date]').removeClass('hide');
			$("#add-bridge-attribute").children('td:eq(1)').children('input[str]').addClass('hide');
		}else{
			$("#add-bridge-attribute").children('td:eq(1)').children('input[date]').addClass('hide');
			$("#add-bridge-attribute").children('td:eq(1)').children('input[str]').removeClass('hide');
		}
	});
});

//jquery 延迟对象
/*function promise(){
	var wait = function (){
		var dtd = $.Deferred();
		var tasks = function(){
			alert("执行完毕!");
			dtd.resolve();
		}
		
		setTimeout(tasks,5000);
		return dtd.promise();
	}
	
	$.when(wait()).done(function(){
		alert("哈哈,成功了!");
	}).fail(function(){
		alert("失败了")
	})
}*/

function sureMapPoinit(){
	$("#positionX").val($("#map_x").text());
	$("#positionY").val($("#map_y").text());
	$("#show-baiduMap").modal("hide");
}

function initAttributeList(type,flag){
	var index = 0;
	var pageSize = pageSizeLlong;
	var params = {}
	var bridgeId =  "-1";
	if(type == 1){
		//
	}else if(type == 2){
		bridgeId =  $("#show-bridge").attr('cid');
	}
	params = {bridgeId:bridgeId};
	commonDataLoader(JSON.stringify(params),resInterface.getAttributeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "";
		var str = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				if(type == 1){
					sel +="<option nm="+v.name+" value="+v.id+" tp="+v.type+">"+v.name+"</option>";
				}else if(type == 2){
					if($("#bridge-update-btn").attr('Flag') == "1"){
						str += "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td>"+setInputByType(valArr[i].type,valArr[i].value )+"</td><td><button onclick='removeLine(this,\"#show-attributeList\")' class='btn btn-default'><span class='glyphicon glyphicon-minus'></span></button></td></tr>";
					}else{
						str += "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td>"+setInputByType(valArr[i].type,valArr[i].value )+"</td><td></td></tr>";
					}
				}
			});
			if(type == 1){
				sel += '<option value="-2">==桥梁属性管理==</option>';
				$("#show-attributeList").html(sel);
				if(flag){
					$.each($("#show-addBridge").find('tr[attr-init]'),function(i,v){
						$("#show-attributeList").find('option[nm='+$(v).find('td:first').text()+']').addClass('hide');
					})
				}
			}else if(type == 2){
				$("#add-bridge-attribute").before(str);
				initAttributeList(1,flag);
			}
//			$("#add-bridge-attribute").find("#show-attributeListUpdate").html(str);
		}else{
			if(type == 1){
				sel += '<option value="-1" tp="-1">请选择自定义属性</option><option value="-2">==桥梁属性管理==</option>';
				$("#show-attributeList").html(sel);
			}else if(type == 2){
				initAttributeList(1);
			} 
		}
	})
}

function showLeftData(event,treeId,treeNode){
	showOrHideDom(treeNode.id);
	 switch (treeNode.id) {
		case "#display-bridge":
			getByBridgeListByPage(1,true,1);
		break;
		case "#display-workSection":
			getByBridgeListByPage(2);
			getWorkSectionListByPage(1,true,1);
		break;	
		case "#display-sensor":
			getByBridgeListByPage(4);
			getSensorListByPage(1,true,1);
		break;	
	}
}


function addOrUpdateBridge(that,flag){
	cleanValue("#addOrUpdate-bridge","#display-bridge");
	$("#map_x").text("");
	$("#map_y").text("");
	$("#show-addBridge").find('tr:gt(4):not("#add-bridge-attribute")').remove();
	$("#addOrUpdate-bridge").find('.modal-header').children('button').prop('disabled',false);
	
	if(!flag){
		$("#addOrUpdate-bridge").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdate-bridge").find('.modal-footer').removeClass('hide');
		$("#uploader-file").removeClass('hide');
		$("#add-bridge-attribute").removeClass('hide');
		$("#bridge-update-btn").attr('Flag',1);
	}else{
		$("#uploader-file").addClass('hide');
		$("#add-bridge-attribute").addClass('hide');
		$("#bridge-update-btn").attr('Flag',0);
	}
	$("#show-bridgeImg").attr('src',"");
	if(that || flag){ //修改
		setTimeout(function(){
			 $("#bridge-update-btn").prop('disabled',true);
		 },500);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-bridge").find('tr[cid='+itemId+']').get(0)).data('data');
		$("#show-bridge").attr('cid',data.id);
		$("#add-bridgeName").val(data.name);
		$("#uploader-file").attr('url',data.image);
		$("#show-bridgeImg").attr('src',CUR_PROJECT_IP + data.image);
		$("#add-description").val(data.description);
		$("#positionX").val(data.positionX);
		$("#positionY").val(data.positionY);
		initAttributeList(2,true);
//	    $("#bridge-update-btn").attr("disabled",true); 
	}else{//添加
		$(that).attr('cur-item-id',"");
		$("#show-bridge").attr('cid',"");
		initAttributeList(1);
	}
}

function addOrUpdateAttribute(that){
	cleanValue("#show-attributeType","#add-attribute");
	if(that){
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-add-attributeList").find('tr[cid='+itemId+']').get(0)).data('data');
		$("#add-attribute-name").val(data.name);
		$("#show-attributeType").attr("originName",data.name);
		$("#show-attributeTypeList").val(data.type);
	}else{
		$("#show-add-attributeList").attr('cid',"");
		$("#show-attributeType").attr("originName","");
		$("#show-attributeTypeList").val("-1");
	}
}

function insertOrUpdateBridge(){
	var cid = $("#show-bridge").attr('cid');
	var name = $("#add-bridgeName").val();
	var description = $("#add-description").val();
	var positionX = parseFloat($("#positionX").val());
	var positionY = parseFloat($("#positionY").val());
	var image = $("#uploader-file").attr("url");
	var params = {
			name:name,
			positionX:positionX,
			positionY:positionY,
			image:image,
			description:description
	}
	 
	if(cid != ""){
		params.id = parseInt(cid);
		commonDataLoader(JSON.stringify(params),resInterface.updateBridgeById,function(data){
			if(data){
				insertOrUpdateAattribute(3);
				getByBridgeListByPage(1,true,1);
			}
		}); 
	}else{
		commonDataLoader(JSON.stringify(params),resInterface.insertBridge,function(data){
			if(data.rsData.length > 0){
				$("#show-bridge").attr('cid',data.rsData[0].id)
				insertOrUpdateAattribute(3);
				getByBridgeListByPage(1,true,1);
			}
		});
	}
}

function getByBridgeListByPage(type,flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	if(type == 1){
		gentPage({cur:curPage,p:$(".page-bridge")});
		$('#show-bridge').html("");
		pageSize = pageSizeTen;
	}else if(type == 3){
		$('#show-add-bridge-work').html("");
		pageSize = pageSizeLlong;
	}
	commonDataLoader(null,resInterface.getBridgeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		if(type == 1){
			var str= "";
			var td = "";
		}else if(type == 2 || type == 3 || type == 4 || type == 5 || type == 6 || type == 7){
			var selStr = "<option value='-1'>请选择桥梁</option>";
		}
		var flagT = false;
		if(parseInt($("#show-add-addWorkSection").attr('tag')) == 2){
			flagT = true;
		}
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(flagT){
					if(parseInt($("#show-add-bridge-work").attr('cid')) == valArr[i].id){
						$("#picture-area").attr('src',CUR_PROJECT_IP + valArr[i].image);
					}
				}
				if(type == 1){
					str = '<tr cid='+valArr[i].id+'>'+
							'<td><input type="checkbox" onclick="judgeCheckTotal(\'#show-bridge\',\'#bridge-update-btn\')"></td>'+
							'<td>'+valArr[i].id+'</td>'+
							'<td><a href="javascript:void(0)" onclick="showBridgeInfo(this)"><span name>'+valArr[i].name+'</span></a></td>' +
							'<td class="text-left width-4">'+valArr[i].description+'</td>'+
							'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
						'</tr>';
					td = $(str).data("data",valArr[i]);
					$('#show-bridge').append(td);
				}else if(type == 2 || type == 3 || type == 4  || type == 5 || type ==6 || type == 7){
					selStr +="<option url='"+valArr[i].image+"' value="+valArr[i].id+" >"+valArr[i].name+"</option>";
				}
			}
			if(type == 2){
				$('#show-bridge-list').html(selStr);
			}
			
		}else{
			if(type == 1){
				$('#show-bridge').html("没有数据");
			}
		} 
		
		if(type == 3){
			$('#show-add-bridge-work').html(selStr);
			if($("#show-add-bridge-work").attr('cid') != ''){
				$("#show-add-bridge-work").val(parseInt($("#show-add-bridge-work").attr('cid')));
			}
		}
		if(type == 4){
			$('#show-bridgeList-sensor').html(selStr);
		}
		
		if(type == 5){
			$('#show-add-bridge-sensor').html(selStr);
			if($("#show-add-bridge-sensor").attr('cid') != ''){
				var cid = parseInt($("#show-add-bridge-sensor").attr('cid'));
				$("#show-add-bridge-sensor").val(cid);
				var url = $("#show-add-bridge-sensor").find('option[value='+cid+']').attr('url');
//				$("#picture-area-sensor").attr('src', CUR_PROJECT_IP + url);
			}
		}
		
		if(type == 6){
			$('#show-bridgeList-logicGroup').html(selStr);
		}
		
		if(type == 7){
			$('#show-add-bridge-logicGroup').html(selStr);
			var params  = {
					sensorId:null,
					sensorTypeId:null,
					bridgeId:parseInt($('#show-add-bridge-logicGroup').val()) != -1 ? parseInt($('#show-add-bridge-logicGroup').val()):null,
					workSectionId:null
			}
			getSensorChannelList(2,params);
		}
		
		if(flag && type == 1){
			
			var height = document.documentElement.clientHeight || document.body.clientHeight;
			height = height - document.getElementById("top-navbar").offsetHeight - 40;
			$(".main-cintainer-left").css('height',height + "px");
			$(".main-cintainer-right").css('height',height + "px");
			
			if(data.rsCount > 10){
				$(".page-bridge").removeClass('hide');
			}else{
				$(".page-bridge").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:$(".page-bridge"),event:"getByBridgeListByPage("+type+",false,"});
		}
	});
}

function showBridgeInfo(that){
	$("#bridge-update-btn").attr('cur-item-id',$(that).closest('tr').attr('cid'));
	$("#addOrUpdate-bridge").modal('show');
	$("#addOrUpdate-bridge").find('button,input,select,textarea').prop('disabled',true);
	$("#addOrUpdate-bridge").find('.modal-footer').addClass('hide');
	addOrUpdateBridge("#bridge-update-btn",true);
}

function deleteBridgeListByIds(id){
	var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-bridge");
	 }
	 
	params = {
		"ids":JSON.stringify(ids)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteBridgeByIds ,function(data){
		 if(data){
			 getByBridgeListByPage(1,true,1);
		 }
	 })
}

function insertOrUpdateAattribute(type){
	var delIdsArr = [];
	var updateArr = [];
	var insertArr = [];
	var singleParam = {};
	var totalParam = {};
	if(type == 1){
		var originName = $("#show-attributeType").attr("originName");
		var wordType = $("#show-attributeTypeList").val();
		var name = $("#add-attribute-name").val();
		singleParam = {
			name:name,
			type:wordType,
			value:null,
			bridgeId:"-1"
		};
		
		if(originName != ""){
			singleParam.originName = $.trim(originName);
			type = 2;
		} 
	}else if(type == 3){
		if($("#show-addBridge").find('tr.hide').length > 0){
			$.each($("#show-addBridge").find('tr.hide'),function(i,v){
				delIdsArr.push(parseInt($(v).attr("cid")));
			})
		}
		
		if($("#show-addBridge").find('tr[attr-init]:not(".hide")').length > 0){
			var obj = {};
			$.each($("#show-addBridge").find('tr[attr-init]:not(".hide")'),function(i,v){
				obj = {
					id:parseInt($(v).attr("cid")),
					value:$(v).children('td:eq(1)').find('input').val() 		
				}
				updateArr.push(obj);
			})
		}
		
		if($("#show-addBridge").find('tr[ad]').length > 0){
			 var obj = {};
			 var bridgeId = parseInt($("#show-bridge").attr("cid"));
			 $.each($("#show-addBridge").find('tr[ad]'),function(i,v){
				 obj ={
					name:$(v).children('td:eq(0)').text(),
					type:$(v).attr('tp'),
					value:$(v).children('td:eq(1)').find('input').val(),
					bridgeId:bridgeId
				 }
				 insertArr.push(obj);
			 })
		}
	}
	totalParam = {
			delIdsArr:JSON.stringify(delIdsArr),
			updateArr:JSON.stringify(updateArr),
			insertArr:JSON.stringify(insertArr),
			singleParam:JSON.stringify(singleParam)
	}
	commonDataLoader(JSON.stringify(totalParam),resInterface.changeAttribute + "?type="+type+"",function(data){
		if((type == 1 || type == 2 )&& data){
			$('#addOrUpdat-attribute').modal('hide');
			initAttributeList(1);
			getAttributeListByPage(true,1);
			if(type == 2){
				$("#show-addBridge").find('tr[nm="'+originName+'"]').attr('nm',name).children('td:first').text(name);
			}
		}else if (type == 3){
			$("#addOrUpdate-bridge").modal("hide");
		}
	});
}

 
 function deleteAttributeByName(Name){
	 var nameList =[];
	 var params = {};
	 if(Name){
		 nameList.push(id);
	 }else{
		 nameList = getDeleteNames("#show-add-attributeList");
	 }
	params = {
		"nameList":JSON.stringify(nameList)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteAttributeByName ,function(data){
		 if(data){
			 initAttributeList(1);
			 getAttributeListByPage(true,1);
			 for(var i =0;i < nameList.length; i ++){
				 $("#show-addBridge").find('tr[nm="'+nameList[i]+'"]').remove();
			 }
		 }
	 })
}
 
function getAttributeListByPage(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-attribute")});
	$("#show-add-attributeList").html("");
	var bridgeId = "-1";
	params = {bridgeId:bridgeId}
	commonDataLoader(JSON.stringify(params),resInterface.getAttributeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		var tr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				str ="<tr cid="+v.id+" name="+v.name+"><td><input  type='checkbox' onclick='judgeCheckTotal(\"#show-add-attributeList\",\"#attribute-update-btn\")'></td><td>"+v.name+"</td><td>"+v.type+"</td></tr>";
				tr = $(str).data('data',v);
				$("#show-add-attributeList").append(tr);
			});
		}else{
			$("#show-add-attributeList").html("没有数据");
		}
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-attribute").removeClass('hide');
			}else{
				$(".page-attribute").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-attribute"),event:"getAttributeListByPage(false,"});
		} 
	})
}
