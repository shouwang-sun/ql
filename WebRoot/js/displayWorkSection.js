var zTree_workSection = {};
var setting_workSection = {
		view: {
			dblClickExpand: false,
			showLine: false
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
			onExpand: onExpand,
			onClick: chooseWorkSection
		}
};

var baiduMapCount = 0;

 $(function(){
	uploadFile("#uploader-workSection-file",function(data){
		$("#show-workSectionImg").attr('src',data);
	});
	
	$("#show-add-bridge-work").on('change',function(){
		$("#workSection-tree").data("nodeList","[]");
		drawBridge();
//		zTree_workSection = $.fn.zTree.getZTreeObj("workSection-tree");
//		$.fn.zTree.init($("#workSection-tree"), setting_workSection, zNodes_workSection);
		getWorkSectionListByPage(2);
		$("#picture-area").nextAll('div').remove();
		$("#show-add-bridge-work").attr('cid','');
		$("#choose-workSection").attr('cid','').val('');
		var cid = parseInt($("#show-add-bridge-work").val());
		var url = $("#show-add-bridge-work").find('option[value='+cid+']').attr("url");
		$("#picture-area").attr('src',CUR_PROJECT_IP + url);
	})
	
	$("#cur-pos-x").change(function(){
		$('#cur-point').css({'left':parseInt($(this).val())}).attr({'px':parseInt($(this).val())})
	});
	
	$("#cur-pos-y").change(function(){
		$('#cur-point').css({'top':parseInt($(this).val())}).attr({'py':parseInt($(this).val())})
	});
	
	var params ={
 			curDom:'#cur-point', //当前选中的节点
 			curDom_:'cur-point',
 			curPosX:'#cur-pos-x',//当前显示的坐标X
 			curPosY:'#cur-pos-y',//挡墙显示的坐标Y
 			imgArea:'#picture-area',//画布的Id
 			movePx:'#pos-x', //移动时显示的坐标X
 			movePy:'#pos-y'//移动时显示的坐标y
 	}
	  keyDownOnImg(params);
}) 

	function keyDownOnImg(params){
	 	var curPointX = 0;
		var curPointY = 0;
		$('body').on('keydown',function(event){
			var key = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
			if($(params.curDom).length){
				curPointX = parseInt($(params.curDom).css("left"));
				curPointY = parseInt($(params.curDom).css("top"));
				curPointY_ = parseInt($(params.curDom).attr("py"));
				switch(key){
					case 39://right
						++ curPointX;
						$(params.curPosX).val(curPointX);
						break;
					case 37://left
						-- curPointX;
						$(params.curPosX).val(curPointX);
						break;
					case 40://up
						++ curPointY;
						++ curPointY_;
						$(params.curPosY).val(curPointY_);
						break;
					case 38://down
						-- curPointY;
						-- curPointY_;
						$(params.curPosY).val(curPointY_);
						break;
				}
				$(params.curDom).css({'left':''+(curPointX) +'px','top':''+(curPointY)+'px'}).attr({"px":curPointX,"py":curPointY_,"title":'x:'+curPointX+' y:'+curPointY_+''});
			}
		})
		
		$(params.imgArea).mousemove(function(event){
			if($(event.target).closest(params.imgArea)){
				$(params.movePx).text(event.offsetX);
				$(params.movePy).text(event.offsetY);
			}
		}).mouseout(function(){
			$(params.movePx).text(0);
			$(params.movePy).text(0);
		});
		
		$(params.imgArea).on('click',function(event){
			if($(params.imgArea).attr('cid') != ''){
				$(params.curDom).nextAll('div[po]').remove();
			}
			
			if($(params.imgArea).closest('div').find(params.curDom).length){
				$(params.curDom).remove();
			}
			
			var pointX = event.offsetX;
			var pointY = event.offsetY;
			var pointY_ = event.offsetY + 24;
			var randomNum = new Date().getTime();
			var $span = $("<div id='"+params.curDom_+"'  px="+pointX+" py="+pointY+"  title='x:"+pointX+" y:"+pointY+"' class='point-"+randomNum+" img-point' ></div>");
			$(params.imgArea).after($span);
			$('.point-'+randomNum+'').css({'position':'absolute','left':''+(pointX) +'px','top':''+(pointY_)+'px'});
			$(params.curPosX).val(pointX);
			$(params.curPosY).val(pointY);
		});
	}

function insertOrUpdateWorkSection(){
	var tag = parseInt($("#show-add-addWorkSection").attr('tag'));
	var name = $("#add-work-name").val();
	var positionX = parseFloat($("#cur-pos-x").val());
	var positionY = parseFloat($("#cur-pos-y").val());
    var description = $("#add-work-description").val();
    var image = $("#uploader-workSection-file").attr("url");
    var pid = parseInt($("#choose-workSection").attr("cid"));
    var bridgeId = parseInt($("#show-add-bridge-work").val())
    var params = {
    		name:name,
    		positionX:positionX,
    		positionY:positionY,
    		description:description,
    		image:image,
    		pid:pid,
    		bridgeId : bridgeId
    }; 
    //2 更新 
    if(tag == 2){
    	params.id = parseInt($("#show-add-addWorkSection").attr('cid'));
    	commonDataLoader(JSON.stringify(params),resInterface.updateWorkSectionById,function(data){
        	if(true){
        		$('#addOrUpdat-workSection').modal('hide');
        		getWorkSectionListByPage(1,true,1);
        	}
        });
    // 1 添加
    }else{
    	commonDataLoader(JSON.stringify(params),resInterface.insertWorkSecton,function(data){
        	if(data.rsData.length > 0){
        		$('#addOrUpdat-workSection').modal('hide');
        		getWorkSectionListByPage(1,true,1);
        	}
        });
    	}
    }

function createOrUpdateWorkSection(that,flag){
	$("#picture-area").attr('src',"");
	cleanValue("#addOrUpdat-workSection","#display-workSection");
	$("#picture-area").nextAll('div').remove();
	
	$("#addOrUpdat-workSection").find('.modal-header').children('button').prop('disabled',false);
	if(!flag){
		$("#addOrUpdat-workSection").find('button,input,select,textarea').prop('disabled',false);
		$("#addOrUpdat-workSection").find('.modal-footer').removeClass('hide');
		$("#uploader-workSection-file").removeClass('hide');
	}else{
		$("#uploader-workSection-file").addClass('hide');
	}
	
	$("#show-workSectionImg").attr('src','');
	if(that || flag){
		$("#workSection-tree").addClass('hide');
		setTimeout(function(){
			 $("#workSection-update-btn").prop('disabled',true);
		 },500);
		var itemId = $(that).attr('cur-item-id');
		var data = $($("#show-workSection").find('tr[cid='+itemId+']').get(0)).data('data');
		var pointX = data.positionX;
		var pointY = data.positionY;
		var bridgeId = parseInt(data.bridgeId);
 	    var pid = parseInt(data.pid);
 	    var bridgeName = "";
 	    var workName = "";
 	    
		var pointStr = "<div id='cur-point'  px="+pointX+" py="+pointY+"  title='x:"+pointX+" y:"+pointY+"' class='img-point' ></div>";
//		$("#workSection-tree").addClass('hide');
		$("#show-point-area").append(pointStr);
		$('#cur-point').css({'position':'absolute','left':''+(pointX) +'px','top':''+(pointY + 24)+'px'});
		$("#add-work-name").val(data.name);
		$("#cur-pos-x").val(pointX);
		$("#cur-pos-y").val(pointY);
	    $("#add-work-description").val(data.description);
	    $("#uploader-workSection-file").attr("url",data.image);
	    $("#show-workSectionImg").attr('src',CUR_PROJECT_IP + data.image);
 	    $("#show-add-bridge-work").attr('cid',bridgeId);
 	    $.each($('#show-bridge-list').children('option'),function(i,v){
	    	if(parseInt($(v).val()) == parseInt(bridgeId)){
	    		bridgeName = $(v).text();
	    		return false;;
	    	}
		});
 	    
 	    $("#choose-workSection").attr('cid',pid); 
 	    if(pid == -1){
 	    	$("#choose-workSection").val(bridgeName);
	    }else{
	    	  getWorkSectionListByPage(2);
	    } 
 	    $("#show-add-addWorkSection").attr({'cid':data.id,'tag':2});
 	    $("#show-add-bridge-work").prop('disabled',true);
	}else{
		$("#show-add-bridge-work").prop('disabled',false);
		$("#workSection-tree").removeClass('hide');
//		$("#workSection-tree").removeClass('hide');
		$("#show-add-addWorkSection").attr({'cid':'','tag':1});
		$("#choose-workSection").attr('cid','');
		$("#show-add-bridge-work").attr('cid','');
		$("#choose-workSection").attr('cid','');
	} 
	$("#choose-workSection").prop('disabled',true);
//	zTree_workSection = $.fn.zTree.getZTreeObj("workSection-tree");
//	$.fn.zTree.init($("#workSection-tree"), setting_workSection, zNodes_workSection);
	$("#workSection-tree").data("nodeList","[]");
 	getByBridgeListByPage(3);
}

//节点展开事件
function onExpand(event,treeId,treeNode){
 	var nodeStr = (treeNode.id).toString(); //bridge
	var nodeType = nodeStr.split("_")[0];
	var pId = treeNode.pId != null ? treeNode.pId.split("_")[0] : null;
 	if(nodeType !="init" && nodeType != "bridge" && nodeType != "workSec" || pId == "workSec"){
		return;
	} 
 	var bridgeId = '';
 	if($("#show-add-bridge-work").val() != '' && $("#show-add-bridge-work").val() != -1){
 		bridgeId = parseInt($("#show-add-bridge-work").val());
 	}
 	 
	var params = {
		"showType":"senWork",
		"nodeStr":nodeStr,
		"extra":"1_"+bridgeId+""
	};
	if(bridgeId == ""){
		return;
	}
	zTree_workSection = $.fn.zTree.getZTreeObj("workSection-tree");
	var nodeListStr = $("#workSection-tree").data("nodeList");
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
			$("#workSection-tree").data("nodeList",nodeListStr);
			zTree_workSection.addNodes(treeNode,node);
	});
}


function drawBridge(){
	var tag = parseInt($("#show-add-addWorkSection").attr('tag'));
	var bridgeId = '';
 	if($("#show-add-bridge-work").val() != '' && $("#show-add-bridge-work").val() != -1){
 		bridgeId = parseInt($("#show-add-bridge-work").val());
 	}
 	var params = {};
 	//添加
	if(tag == 1){
		params = {
				"showType":"senWork",
				"nodeStr":"init_1",
				"extra":"1_"+bridgeId+""
			};
	//修改
	}else{
		var cid = $("#choose-workSection").attr('cid'); 
		//截面不为空
		if(cid != ""){
			  params = {
					"showType":"senWork",
					"nodeStr":"bridge_"+bridgeId+"",
					"extra":"1_"+bridgeId+""
				};
		//截面为空
		}else{
			params = {
					"showType":"senWork",
					"nodeStr":"init_1",
					"extra":"1_"+bridgeId+""
				};
			$("#workSection-tree").removeClass('hide');
		}
	}
	
	if(bridgeId == ""){
		return;
	}
	
	commonDataLoader(JSON.stringify(params),resInterface.buildTree,function(data){
//		zTree_workSection = $.fn.zTree.getZTreeObj("workSection-tree");
		var node = data.rsData;
//		var zNodes_workSection = JSON.stringify(node);
//		var zNodes_workSection =[{ id:'init_1', pId:0, name:'桥梁', isParent:true , open:false}];
		$.fn.zTree.init($("#workSection-tree"), setting_workSection, node);
	});
}

function showWorkSectionInfo(that){
	$("#addOrUpdat-workSection").attr('cur-item-id',$(that).closest('tr').attr('cid'));
	$("#addOrUpdat-workSection").modal('show');
	$("#addOrUpdat-workSection").find('button,input,select,textarea').prop('disabled',true);
	$("#addOrUpdat-workSection").find('.modal-footer').addClass('hide');
	createOrUpdateWorkSection("#addOrUpdat-workSection",true);
	 
/*	 commonDataLoader(JSON.stringify(data.id),resInterface.showWorksectionNextChildType ,function(data){
		 if(data){
			 var str = "";
			 if(data.rsCount  == 1){
				 str = "<tr><td>子观测截面</td></tr>";
			 }else if(data.rsCount == 2){
				 str = "<tr><td>子观测点</td></tr>";
			 }
			 $.each(data.rsData,function(i,v){
				 str += "<tr><td>"+v.name+"</td></tr>";
			 })
			 $("#show-workSection-info").append(str);
		 }
	 })*/
}

function deleteWorkSectionByIds(id){
	 var ids =[];
	 var params = {};
	 if(id){
		 ids.push(id);
	 }else{
		 ids = getDeleteIds("#show-workSection");
	 }
	 
	params = {
		"ids":JSON.stringify(ids)	 
	 };
	 commonDataLoader(JSON.stringify(params),resInterface.deleteWorkSectionByIds ,function(data){
		 if(data){
			 getWorkSectionListByPage(1,true,1);
		 }
	 })
}


function getWorkSectionListByPage(type,flag,page){
	if(!page)page = 1;
	var bridgeId = "";
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	if(type == 1){
		gentPage({cur:curPage,p:$(".page-workSection")});
		bridgeId = $("#show-bridge-list").val();
		pageSize = pageSizeTen;
		$('#show-workSection').html("");
	}else if(type == 2){
		bridgeId = $("#show-add-bridge-work").val();
		pageSize = pageSizeLlong;
	}else if(type == 3){
		pageSize = pageSizeLlong;
		bridgeId = $("#show-bridgeList-sensor").val();
	}else if(type == 4){
		pageSize = pageSizeLlong;
		bridgeId = $("#show-add-bridge-sensor").val();
	}
	var params = {
		"bridgeId": bridgeId
	};
	
	commonDataLoader(JSON.stringify(params),resInterface.getWorkSectionList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var tr = "";
		var str = "";
		var strSel = '';
		if(type == 3 || type == 4){
			strSel = "<option value='-1'>请选择观测观测截面</option>";
		}
		var flagT = false;
		if(parseInt($("#show-add-addSensor").attr('tag')) == 2){
			flagT = true;
		}
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				if(flagT){
					if(parseInt($("#show-add-work-sensor").attr('cid')) == valArr[i].id){
						$("#picture-area-sensor").attr('src',CUR_PROJECT_IP + valArr[i].image);
					}
				}
				if(type == 1){
					str = '<tr cid='+valArr[i].id+'>'+
								'<td><input type="checkbox" onclick="judgeCheckTotal(\'#show-workSection\',\'#workSection-update-btn\')"></td>'+
								'<td><a href="javascript:void(0)" data-toggle="modal" data-target="#show-workSectionInfo"  onclick="showWorkSectionInfo(this)">'+valArr[i].name+'</a></td>' +
								'<td class="text-left width-4">'+valArr[i].description+'</td>'+
								'<td>'+dateFormat(valArr[i].lastUpdateTime)+'</td>' +
							'</tr>';
						tr = $(str).data("data",valArr[i]);
						$('#show-workSection').append(tr);
				}else if(type == 2){
					if(parseInt($("#choose-workSection").attr('cid')) == valArr[i].id){
						$("#choose-workSection").val(valArr[i].name);
					}
					strSel += '<div class="checkbox text-left" cid='+valArr[i].id+'><label><input type="checkbox"  onclick="chowChannelByWork( this,'+valArr[i].id+')">'+valArr[i].name+'</label></div>';
				}else if(type == 3 || type == 4){
					strSel +="<option value="+valArr[i].id+" url='"+valArr[i].image+"'>"+valArr[i].name+"</option>";
				}
			}
			if(type == 3){
				$("#show-workSectionList-sensor").html(strSel);
			}
			if(type == 4){
				$("#show-add-work-sensor").html(strSel);
				if($("#show-add-work-sensor").attr('cid') !=''){
					$("#show-add-work-sensor").prop('disabled',true);
				}
				if($('#show-add-work-sensor').attr('cid') !=  ""){
					$("#show-add-work-sensor").val($('#show-add-work-sensor').attr('cid'));
					var url = $("#show-add-work-sensor").find('cid['+$('#show-add-work-sensor').attr('cid')+']').attr('url');
					$("#picture-area-sensor").attr('url',url);
					if($("#addOrUpdat-sensor").find('.modal-footer').hasClass('hide')){
						return;
					}
					$("#show-add-work-sensor").prop('disabled',false);
				}
			}
			
		}else{
			if(type == 1){
				$("#show-workSection").html("没有数据");
			}else if(type == 3){
				$("#show-workSectionList-sensor").html(strSel);
			}else if(type == 4){
				$("#show-add-work-sensor").html("<option value='-1'>请选择观测观测截面</option>");
				if($("#show-add-work-sensor").attr('cid') !=''){
					$("#show-add-work-sensor").prop('disabled',true);
				}
			}
		}
		
		if(flag && type == 1){
			if(data.rsCount > 10){
				$(".page-workSection").removeClass('hide');
			}else{
				$(".page-workSection").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSizeTen),cur:curPage,p:$(".page-workSection"),event:"getWorkSectionListByPage("+type+",false,"});
		}else if(type == 2){
			if($("#choose-workSection").attr('cid') == ''){
				if($("#show-add-addWorkSection").attr('tag') == '1'){
					initImage(valArr,'#picture-area','point','con');
		  			var cid = parseInt($("#show-add-bridge-work").val());
				}
			}
		}else if(type == 4){
			if($("#addOrUpdat-sensor").find('.modal-footer').hasClass('hide')){
				return;
			}
			if($("#show-add-work-sensor").val() == '-1'){
				$("#show-add-work-sensor").closest('tr').nextAll('tr').find('input,textArea').prop('disabled',true);
				$("#addOrUpdat-sensor").find('button').prop('disabled',true);
			}else{
				$("#show-add-work-sensor").closest('tr').nextAll('tr').find('input,textArea').prop('disabled',false);
				$("#addOrUpdat-sensor").find('button').prop('disabled',false);
			}
			if($("#show-add-work-sensor").attr('cid') !=''){
				$("#show-add-work-sensor").prop('disabled',true);
			}
		} 
	})
}
function initImage(arr,dom,childDom,con){
	$(dom).nextAll('div[po]').remove();
	if(arr.length == 0){
		return;
	}
	var pointX = "";
	var pointY = "";
	var cid = "";
	var span = "";
	var domId = "";
	for(var i = 0; i < arr.length ;i ++){
		pointX = parseFloat(arr[i].positionX);
		pointY = parseFloat(arr[i].positionY) +  24;
		cid = arr[i].id;
		domId = childDom + "-" + cid;
 		$span = $("<div po id='"+con+"-"+cid+"'><div title='x:"+pointX+" y:"+pointY+"' id="+domId+" class='img-point' px="+pointX+" py="+pointY+"></div></div>");
 		$(dom).after($span);
 		$('#' + childDom+'-'+cid+'').css({'position':'absolute','left':''+(pointX) +'px','top':''+(pointY)+'px'});
	}
}

function chooseWorkSection(event,treeId,treeNode){
	var nodeStr = treeNode.id; 
	var nodeType = nodeStr.split("_")[0];
	var id = parseInt(nodeStr.split("_")[1]);
	if(nodeType =="init"){
		$("#choose-workSection").val("").attr("cid",null);
	}else{
		$("#choose-workSection").val(treeNode.name);
		if(nodeType =="bridge"){
			$("#choose-workSection").attr("cid",-1);
		}else{
			$("#choose-workSection").attr("cid",id);
		}
	}
}

