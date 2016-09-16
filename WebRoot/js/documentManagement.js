$(function(){
	uploadFile("#uploader-update-file",function(){
		getDocumentListByPage(true,1);
		$('#upload-file').modal('hide');
		findDocumentTypeList();
	});
	getDocumentListByPage(true,1);
	findDocumentTypeList();
})

function findDocumentTypeList(){
	commonDataLoader(null,resInterface.findDocumentTypeList,function(data){
		var valArr = data.rsData;
		var sel = "<option value='-1'>请选择文件类型</option>";
		var tr = "";
		if(valArr.length > 0){
			for(var i=0;i<valArr.length;i++){
				sel +="<option value="+valArr[i]+">"+valArr[i]+"</option>";
			}
		}
		$("#file-type").html(sel);
	}); 
}

function deleteDocumentListByIds(){
	var ids = getDeleteIds("#show-documentList");
	commonDataLoader(JSON.stringify(ids),resInterface.deleteDocumentByIds,function(data){
		if(data){
			removeIdsStr("#show-documentList");
		}
	}); 
}

function getDocumentListByPage(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	gentPage({cur:curPage,p:$(".page-document")});
	var startSize = $("#start-size").val();
	var endSize = $("#end-size").val();
	var startTime = $("#start-date").val() !="" ? $("#start-date").val() : "-1";
	var endTime = $("#end-date").val() !="" ? $("#end-date").val() : "-1";
	var fileType = $("#file-type").val();
	params = {
			startSize:startSize,
			endSize:endSize,
			startTime:startTime,
			endTime:endTime,
			fileType:fileType
	}
	$("#show-documentList").html("");
	commonDataLoader(JSON.stringify(params),resInterface.getDocumentList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
		var str = "";
		var tr = "";
		var fileSize = 0;
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				fileSize = (v.fileSize / 1000).toFixed(2) + "kb";
				str ="<tr cid="+v.id+" ><td><input  type='checkbox'></td><td><a href='javascript:void(0)' url= "+v.fileUrl+" onclick='openUrl(this)'>"+v.fileName+"</td><td>"+fileSize+"</td><td>"+dateFormat(v.uploadTime)+"</td></tr>";
				tr = $(str).data('data',v);
				$("#show-documentList").append(tr); 
			});
		}else{
			$("#show-documentList").html("没有数据");
		}
		
		if(flag){
			if(data.rsCount > 10){
				$(".page-document").removeClass('hide');
			}else{
				$(".page-document").addClass('hide');
			}
			gentPage({data:Math.ceil(data.rsCount / pageSize),cur:curPage,p:$(".page-document"),event:"getDocumentListByPage(false,"});
		} 
	})
	
}