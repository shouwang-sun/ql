$(function(){
	getByBridgeListByPage();
	getEvaluateProjectResultYear();
	getEvaluateProjectResultMonth();
})

function getEvaluateProjectResultList(flag,page){
	if(!page)page = 1;
	var index = (page - 1) * 10;
	page = page || 1;
	curPage = page;
	var bridgeId = $("#show-bridgeList-evaluateResult").val() != "-1" ? parseInt($("#show-bridgeList-evaluateResult").val()) : null;
	var projectYear = $("#show-year-evaluateResult").val() != "-1" ? parseInt($("#show-year-evaluateResult").val()) : null;
	var projectMonth = $("#show-month-evaluateResult").val()!="-1" ?parseInt($("#show-month-evaluateResult").val()) : null;
	 
	var params ={
			bridgeId:bridgeId,
			projectYear:projectYear,
			projectMonth:projectMonth
	};
	commonDataLoader(JSON.stringify(params),resInterface.getEvaluateProjectResultList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var tdSpaceD = '<div class="small-space"></div>';
		var tdST ='|__  '
		var str = "";
		var name = "";
		var healThyValue = "";
		var healThyRate = "";
		var levelStr = "";
		var advice = "";
		var updateTime = "";
		var id = "";
		var pid = "";
		var step ="";
		var dm = $("#show-evaluateResult");
		dm.html("");
		if(data.rsData.length > 0){
			var valArr = data.rsData;
			for(var i =0;i < valArr.length; i++){
				 id = valArr[i].evaluateProjectId;
				 pid = valArr[i].evaluateProjectPid;
				 name = valArr[i].evaluateProject.name;
				 healThyValue = (valArr[i].healthyValue).toFixed(2);
				 healThyRate = (valArr[i].evaluateProject.healthyRate).toFixed(2);
				 if(valArr[i].level == 1){
					 levelStr ="<b class='green'>A类</b>";
				 }else if(valArr[i].level == 2){
					 levelStr ="<b class='yellow'>B类</b>";
				 }else if(valArr[i].level == 3){
					 levelStr ="<b class='red'>C类</b>";
				 }else if(valArr[i].level == 4){
					 levelStr ="<b class='orange'>D类</b>";
				 }
				 advice = valArr[i].advice;
				 updateTime = dateFormat(valArr[i].lastUpdateTime);
				 str =$("<tr cid="+id+" pid="+pid+" step='0'><td style='text-align: left;'>"+ name +"</td><td>"+healThyValue+"</td><td>"+healThyRate+"</td><td>"+levelStr+"</td><td>"+updateTime+"</td></tr>");
				 if(pid == 0){
					 if(dm.children('tr').length > 1){
				 			str.insertBefore(dm.children('tr'));
				 		}else{
				 			dm.append(str);
				 		}
				 }else{
					 tdSpaceD = '';
					 if(dm.children('tr[cid='+pid+']').length > 0){
						 step = parseInt($(dm.children('tr[cid='+pid+']').get(0)).attr('step'));
						 step ++;
						 str.attr('step',step);
						 for(var j = 0;j < step ;j ++){
							 tdSpaceD += '<div class="small-space"></div>';
						 }
						 str.insertAfter(dm.children('tr[cid='+pid+']').get(0));
						 $(dm.children('tr[cid='+id+']').get(0)).children("td:first").prepend(tdSpaceD + tdST); 
					 }else{
						 dm.append(str);
					 }
				 }
			}
		} 
	})
}

function getByBridgeListByPage(){
	var index = 0;
	var pageSize = pageSizeLlong;
	commonDataLoader(null,resInterface.getBridgeList + "?index="+index+"&pageSize="+pageSize+"" ,function(data){
		var valArr = data.rsData;
//		var selStr = "<option value='-1'>-----</option>";
		var selStr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				selStr +="<option  value="+valArr[i].id+" >"+valArr[i].name+"</option>";
			});
		}
		$("#show-bridgeList-evaluateResult").html(selStr);
		getEvaluateProjectResultList(true,1);
//		$("#show-bridgeList-evaluateResult").val(1);
	});
}

function getEvaluateProjectResultYear(){
	commonDataLoader(null,resInterface.getEvaluateProjectResultYear,function(data){
		var valArr = data.rsData;
//		var selStr = "<option value='-1'>-----</option>";
		var selStr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				selStr +="<option  value="+v+" >"+v+"年</option>";
			})
		}
		$("#show-year-evaluateResult").html(selStr);
	});
}

function getEvaluateProjectResultMonth(){
	commonDataLoader(null,resInterface.getEvaluateProjectResultMonth,function(data){
		var valArr = data.rsData;
//		var selStr = "<option value='-1'>-----</option>";
		var selStr = "";
		if(valArr.length > 0){
			$.each(valArr,function(i,v){
				selStr +="<option  value="+v+" >"+v+"月</option>";
			})
		}
		$("#show-month-evaluateResult").html(selStr);
	});
}