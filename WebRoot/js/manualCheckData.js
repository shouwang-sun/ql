 $(function(){
	 getByManualCheckDataByPage(true,1);
	 getByBridgeListByPage();
	 $("#show-manualCheckFieldList").on('change',function(){
		if(parseInt($(this).val()) == -2){
			$("#add-manualCheckField").modal('show');
			getManualCheckFieldListByPage(true,1);
			$(this).val($(this).children('option:not(.hide):eq(0)').val());
		} 
	});
 });
 
 function manualCheckObjectChange(that){
	 if(parseInt($(that).val()) == -2){
			$("#add-manualCheckObject").modal('show');
			$(that).val("-1");
			getManualCheckObjectByPage(true,1);
		}
 }
 
 function getByManualCheckDataByPage(flag,page){
	 	var bridgeId = $("#show-bridgeList-manualCheckData").val();
		var startTime = $.trim($("#start-date-manualCheckData").val()) != "" ? $.trim($("#start-date-manualCheckData").val()): "-1";
		var endTime = $.trim($('#end-date-manualCheckData').val()) != "" ? $.trim($('#end-date-manualCheckData').val()): "-1";
	 	var params = {
				"createTime":startTime,
				"endTime":endTime,
				"bridgeId":bridgeId
		};
	 	page = page || 1;
		curPage = page;
		gentPage({cur:curPage,p:$(".page-manualCheckData")});
		var index = (page - 1) * 10;
		$('#show-manualCheckData').html("");
	    commonDataLoader(JSON.stringify(params),resInterface.getManualCheckDataList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
			var valArr = data.rsData;
			var str= "";
			var tr = "";
			var manualCheckName= "";
			console.log(valArr);
			if(valArr.length > 0){
				for(var i=0;i<valArr.length;i++){
					str = '<tr cid='+valArr[i].id+'>'+
								'<td><input type="checkbox" onclick="judgeCheckTotal(\'#show-manualCheckData\',\'#manualCheckData-update-btn\')"></td>'+
								'<td>'+valArr[i].name+'</td>' +
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
					tr = $(str).data("data",valArr[i]);
					$('#show-manualCheckData').append(tr);
				}
			}else{
				$('#show-manualCheckData').html("没有数据");
			} 
			
			if(flag){
				if(data.rsCount > 10){
					$(".page-manualCheckData").removeClass('hide');
				}else{
					$(".page-manualCheckData").addClass('hide');
				}
				gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-manualCheckData"),event:"getByManualCheckDataByPage(false,"});
			} 
		});
 }
 
 
 function createOrUpdateManualCheckData(that){
	 cleanValue("#addOrUpdat-manualCheckData","#show-manualCheckData");
	 $("#add-manualCheckData-bridgeList").empty().append($("#show-bridgeList-manualCheckData").children().clone());
	 $("#show-add-manualCheckData").find('tr:gt(1):not("#add-manualCheck-field")').remove();
	 if(that){
		 setTimeout(function(){
			 $("#manualCheckData-update-btn").prop('disabled',true);
		 },500);
		 var itemId = $(that).attr('cur-item-id');
		 var data = $($("#show-manualCheckData").find('tr[cid='+itemId+']').get(0)).data('data');
		 var name = data.name;
		 var bridgeId = data.bridgeId;
		 var id = data.id;
		 $("#show-manualCheckData").attr('cid',id);
		 $("#add-manualCheckData-name").val(name).attr("originName",data.name);
		 $("#add-manualCheckData-bridgeList").attr('cid',parseInt(bridgeId)).val(bridgeId);
		 initManualCheckObjectList(2);
	 }else{
		 $("#show-manualCheckData").attr('cid','');
		 $("#add-manualCheckData-name").attr("originName",'');
		 $("#add-manualCheckData-bridgeList").attr('cid','');
		 initManualCheckFieldList(1);
		 initManualCheckObjectList(1);
	 }
 }
 
 function addOrUpdateManualCheckField(that){
	 cleanValue("#addOrUpdat-manualCheckField","#add-manualCheckField");
	 if(that){ //修改
		 setTimeout(function(){
			 $("#manualCheckField-update-btn").prop('disabled',true);
		 },500);
		 var itemId = $(that).attr('cur-item-id');
		 var data = $($("#add-manualCheckField").find('tr[cid='+itemId+']').get(0)).data('data');
		 var id = data.id;
		 var name = data.name;
		 var type = data.type;
		 $("#add-manualCheckField-name").val(name).attr('originName',name);
		 $("#show-manualCheckFieldTypeList").val(type);
	 }else{
		 $("#show-add-manualCheckFieldList").attr('cid','');
		 $("#add-manualCheckField-name").attr('originName','');
		 $("#show-manualCheckFieldTypeList").val("-1");
	 }
 }
 
 
 function addOrUpdateManualCheckObject(that){
	 cleanValue("#addOrUpdat-manualCheckObject","#add-manualCheckObject");
	 if(that){ //修改
		 setTimeout(function(){
			 $("#manualCheckObject-update-btn").prop('disabled',true);
		 },500);
		 var itemId = $(that).attr('cur-item-id');
		 var data = $($("#add-manualCheckObject").find('tr[cid='+itemId+']').get(0)).data('data');
		 var name = data.name;
		 $("#add-manualCheckObject-name").val(name).attr('originName',name);
	 }else{
		 $("#add-manualCheckObject-name").attr('originName','');
	 }
 }
 
 function insertOrUpdateManualCheckObject(){
	 var originName = $("#add-manualCheckObject-name").attr('originName');
	 var name = $("#add-manualCheckObject-name").val();
	 var obj = {
			name : name
	 }
	 if(originName != ""){
		 obj.originName = originName;
		 commonDataLoader(JSON.stringify(obj),resInterface.updateManualCheckObjectById,function(data){
				if(data){
					$('#addOrUpdat-manualCheckObject').modal('hide');
					initManualCheckObjectList(3);
					getManualCheckObjectByPage(true,1);
					
				}
			});
	 }else{
		 commonDataLoader(JSON.stringify(obj),resInterface.insertManualCheckObject,function(data){
				if(data){
					$('#addOrUpdat-manualCheckObject').modal('hide');
					initManualCheckObjectList(3);
					getManualCheckObjectByPage(true,1);
				}
			});
	 }
 }
 
 
function initManualCheckFieldList(type,flag){
	var index = 0;
	var pageSize = 0;
	var params = {};
	var manualCheckDataId = "";
	if(type == 1){
		$('#show-manualCheckFieldList').html("");
		pageSize = pageSizeLlong;
		manualCheckDataId = "-1";
	}else if(type == 2 || type == 3){
		pageSize = pageSizeLlong;
		manualCheckDataId = $("#show-manualCheckData").attr('cid');
	}
	params = {
			manualCheckDataId:manualCheckDataId
	}
	commonDataLoader(JSON.stringify(params),resInterface.getManualCheckFieldList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "";
		var str = "";
		var manualCheckObjectId = "";
		if(type == 3){
			var objectClone = $("#show-manualCheckObjectList").closest('td').clone();
		}
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(type == 1){
					sel +="<option nm="+valArr[i].name+"  tp="+valArr[i].type+" value="+valArr[i].id+" >"+valArr[i].name+"</option>";
				}else if(type == 2){
					str += "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td colspan=2>"+setInputByType(valArr[i].type,valArr[i].value )+"</td><td><button onclick='removeLine(this,\"#show-manualCheckFieldList\")' class='btn btn-default'><span class='glyphicon glyphicon-minus'></span></button></td></tr>";
				}else if(type == 3){
					manualCheckObjectId = valArr[i].manualCheckObjectId;
					str = "<tr attr-init  nm ="+valArr[i].name+" cid="+valArr[i].id+" tp="+valArr[i].type+"><td>"+valArr[i].name+"</td><td>"+setInputByType(valArr[i].type,valArr[i].value )+"</td>"+objectClone.prop('outerHTML')+"<td><button onclick='removeLine(this,\"#show-manualCheckFieldList\")' class='btn btn-default'><span class='glyphicon glyphicon-minus'></span></button></td></tr>";
					$("#add-manualCheck-field").before(str);
					$("#add-manualCheck-field").prev('tr').find('select').val(manualCheckObjectId);
				}
			}
			if(type == 1){
				sel += '<option value="-2">==检测字段管理==</option>';
				$("#add-manualCheck-field").find("#show-manualCheckFieldList").html(sel);
				if(flag){
					$.each($("#show-add-manualCheckData").find('tr[attr-init]'),function(i,v){
						$("#show-manualCheckFieldList").find('option[nm='+$(v).find('td:first').text()+']').addClass('hide');
					})
				}
			}else if(type == 2){
				$("#add-manualCheck-field").before(str);
				initManualCheckFieldList(1,flag);
			}else if(type == 3){
				initManualCheckFieldList(1,flag);
			}
		}else{
			if(type == 1){
				sel += '<option value="-1" tp="-1">请选择自定义属性</option><option value="-2">==检测字段管理==</option>';
				$("#show-manualCheckFieldList").html(sel);
			}else if(type == 2){
				initManualCheckFieldList(1);
			}
		} 
	})
}

function initManualCheckObjectList(type){
	var index = 0;
	var pageSize = 0;
	if(type == 1 || type == 2 || type == 3){
		$('#show-manualCheckObjectList').html("");
		pageSize = pageSizeLlong;
	} 
	 
	commonDataLoader(null,resInterface.getManualCheckObjectList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = '<option value="-1" tp="-1">请选择自定义对象</option>';
		var str = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(type == 1 || type == 2 || type == 3){
					sel +="<option nm="+valArr[i].name+"  value="+valArr[i].id+" >"+valArr[i].name+"</option>";
				}
			}
			if(type == 1 || type  == 2){
				sel += '<option value="-2">==检测对象管理==</option>';
				$("#add-manualCheck-field").find("#show-manualCheckObjectList").html(sel);
				if(type == 2){
					 initManualCheckFieldList(3,true);
				}
			} 
			if(type == 3){
				$(".select-auto-object").html(sel);
			}
			
		}else{
			if(type == 1 || type  == 2){
				sel = '<option value="-2">==检测对象管理==</option>';
				$("#add-manualCheck-field").find("#show-manualCheckObjectList").html(sel);
			}
			if(type == 3){
				$(".select-auto-object").html(sel);
			}
		} 
	})
}
 
function deleteByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-manualCheckData");
	 }
	 commonDataLoader(JSON.stringify(ids),resInterface.deleteManualCheckDataByIds ,function(data){
		 if(data){
			 getByManualCheckDataByPage(true,1);
		 }
	 })
 }

function insertOrUpdateManualCheckData(){
	var id = $("#show-manualCheckData").attr('cid');
	var name = $("#add-manualCheckData-name").val();
	var bridgeId = parseInt($("#add-manualCheckData-bridgeList").val());
	var params = {
		name:name,
		bridgeId:bridgeId
	};
	if(id != ""){//修改
		params.id = parseInt(id);
		commonDataLoader(JSON.stringify(params),resInterface.updateManualCheckDataById ,function(data){
			 if(data){
				 getByManualCheckDataByPage(true,1);
				 insertOrUpdateManualCheckField(3);
			 }
		 })
		
	}else{
		commonDataLoader(JSON.stringify(params),resInterface.insertManualCheckData ,function(data){
			 if(data.rsData.length > 0){
				 $("#show-manualCheckData").attr("cid",data.rsData[0].id);
				 getByManualCheckDataByPage(true,1);
				 insertOrUpdateManualCheckField(3);
			 }
		 })
	}
}

function insertOrUpdateManualCheckField(type){
	var delIdsArr = [];
	var updateArr = [];
	var insertArr = [];
	var singleParam = {};
	var totalParam = {};
	if(type == 1){
		var originName = $("#add-manualCheckField-name").attr("originName");
		var wordType = $("#show-manualCheckFieldTypeList").val();
		var name = $("#add-manualCheckField-name").val();
		singleParam = {
			name:name,
			type:wordType,
			value:null,
			manualCheckDataId:"-1"
		};
		
		if(originName != ""){
			singleParam.originName = $.trim(originName);
			type = 2;
		} 
	}else if(type == 3){
		//删除
		if($("#show-add-manualCheckData").find('tr.hide').length > 0){
			$.each($("#show-add-manualCheckData").find('tr.hide'),function(i,v){
				delIdsArr.push(parseInt($(v).attr("cid")));
			})
		}
		
		//更新
		if($("#show-add-manualCheckData").find('tr[attr-init]:not(".hide")').length > 0){
			var obj = {};
			$.each($("#show-add-manualCheckData").find('tr[attr-init]:not(".hide")'),function(i,v){
				obj = {
					id:parseInt($(v).attr("cid")),
					value:$(v).children('td:eq(1)').find('input').val(),
					manualCheckObjectId:parseInt($(v).find('td:eq(2)').children('select').val())
				}
				updateArr.push(obj);
			})
		}
		//添加
		if($("#show-add-manualCheckData").find('tr[ad]').length > 0){
			 var obj = {};
			 var manualCheckDataId = parseInt($("#show-manualCheckData").attr("cid"));
			 $.each($("#show-add-manualCheckData").find('tr[ad]'),function(i,v){
				 obj ={
					name:$(v).children('td:eq(0)').text(),
					type:$(v).attr('tp'),
					value:$(v).children('td:eq(1)').find('input').val(),
					manualCheckDataId:manualCheckDataId,
					manualCheckObjectId:parseInt($(v).find('td:eq(2)').children('select').val())
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
	commonDataLoader(JSON.stringify(totalParam),resInterface.changeManualCheckField + "?type="+type+"",function(data){
		if((type == 1 || type == 2 )&& data){
			$('#addOrUpdat-manualCheckField').modal('hide');
 			initManualCheckFieldList(1);
 			getManualCheckFieldListByPage(true,1);
		}else if (type == 3){
			$("#addOrUpdat-manualCheckData").modal("hide");
		}
	});
}

function getByBridgeListByPage(){
	var index = 0;
	var pageSize = pageSizeLlong;
	commonDataLoader(null,resInterface.getBridgeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var sel = "<option value='-1'>请选择桥梁</option>";
		var str = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				sel +="<option url='"+valArr[i].image+"' value="+valArr[i].id+" >"+valArr[i].name+"</option>";
			}
		} 
		$('#show-bridgeList-manualCheckData').html(sel);
	})
 }

function deleteManualCheckFieldByName(Name){
	 var nameList =[];
	 var params = {};
	 if(Name){
		 nameList.push(id);
	 }else{
		 nameList = getDeleteNames("#show-add-manualCheckFieldList");
	 }
	params = {
		"nameList":JSON.stringify(nameList)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteManualCheckFieldByName ,function(data){
		 if(data){
			 initManualCheckFieldList(1);
	 		 getManualCheckFieldListByPage(true,1);
	 		 for(var i =0 ; i< nameList.length ; i ++){
	 			 $("#show-add-manualCheckData").find('tr[nm="'+nameList[i]+'"]').remove();
	 		 }
		 }
	 })
}

function deleteManualCheckObjectByName(Name){
	 var nameList =[];
	 var params = {};
	 if(Name){
		 nameList.push(id);
	 }else{
		 nameList = getDeleteNames("#show-add-manualCheckObjectList");
	 }
	params = {
		"nameList":JSON.stringify(nameList)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteManualCheckObjectByName ,function(data){
		 if(data){
			 initManualCheckObjectList(3);
			 getManualCheckObjectByPage(1,true);
		 }
	 })
}

function getManualCheckFieldListByPage(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-manualCheckField")});
	$("#show-add-manualCheckFieldList").html("");
	var manualCheckDataId = "-1";
	params = {bridgeId:manualCheckDataId}
	commonDataLoader(JSON.stringify(params),resInterface.getManualCheckFieldList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		var tr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				str ="<tr cid="+v.id+" name="+v.name+"><td><input  type='checkbox' onclick='judgeCheckTotal(\"#show-add-manualCheckFieldList\",\"#manualCheckField-update-btn\")'></td><td>"+v.name+"</td><td>"+v.type+"</td></tr>";
				tr = $(str).data('data',v);
				$("#show-add-manualCheckFieldList").append(tr);
			});
		}else{
			$("#show-add-manualCheckFieldList").html("没有数据");
		}
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-manualCheckField").removeClass('hide');
			}else{
				$(".page-manualCheckField").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-manualCheckField"),event:"getManualCheckFieldListByPage(false,"});
		} 
	})
}

function getManualCheckObjectByPage(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-manualCheckObject")});
	$("#show-add-manualCheckObjectList").html("");
	commonDataLoader(null,resInterface.getManualCheckObjectList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		var tr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				str ="<tr cid="+v.id+" name="+v.name+"><td><input  type='checkbox' onclick='judgeCheckTotal(\"#show-add-manualCheckObjectList\",\"#manualCheckObject-update-btn\")'></td><td>"+v.name+"</td></tr>";
				tr = $(str).data('data',v);
				$("#show-add-manualCheckObjectList").append(tr);
			});
		}else{
			$("#show-add-manualCheckObjectList").html("没有数据");
		}
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-manualCheckObject").removeClass('hide');
			}else{
				$(".page-manualCheckObject").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-manualCheckObject"),event:"getManualCheckObjectByPage(false,"});
		} 
	})
}