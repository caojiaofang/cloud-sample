<link href="base/css/lib/bootstraptable/bootstrap-table.css" rel="stylesheet">
<link href="base/css/lib/toastr/toastr.min.css" rel="stylesheet">
<link href="base/css/lib/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="base/css/lib/AdminLTE/AdminLTE.css">
<link rel="stylesheet" href="base/css/lib/font-awesome/css/font-awesome.min.css">
<div class="row" id="_panel1">
	<div class="col-12">
		<div class="box box-info" style="height: auto;">
			<div class="box-header with-border" style="margin-top: -6px;">
				<h3 class="box-title">查询条件</h3>
				<div class="box-tools pull-right" style="margin-top: -6px;">
					<button type="button" onclick="myTableReload()" class="btn btn-info waves-effect waves-light m-r-10"><i class="ti-search"></i>&nbsp;&nbsp;查询</button>	
					<button type="button" onclick="queryReset()" class="btn btn-inverse waves-effect waves-light">重 置</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-box-tool" id="fold">
						<i class="fa fa-chevron-down"></i>
					</button>
					<!-- <button type="button" class="btn btn-box-tool"
						data-widget="remove">
						<i class="fa fa-times"></i>
					</button> -->
				</div>
			</div>
			<!-- /.box-header -->
			<div class="card-body">
				<div class="container-fluid">
					<form>
						<table width="100%" style="border-collapse: separate; border-spacing: 10px;">
							<#-- 循环生成查询表格 -->
	  						<#list columns as col>
	  							<#if (col_index%3==0)>
								<tr>
								</#if>
									<td>${col.culumnExplain}:</td>
									<td><input type="text"
										class="form-control input-sm col-sm-10" name="query_${col.columnName}" id="query_${col.columnName}" />
									</td>
								<#if (col_index%3==2) || !col_has_next>
								</tr>
								</#if>
							</#list>
						</table>
						<#-- 
						<div class="form-group row m-b-0">
							<div class="offset-sm-5 col-sm-9 ">
								<button type="button" onclick="myTableReload()"
									class="btn btn-success waves-effect waves-light m-r-10">查
									询</button>
								<button type="button" onclick="()"
									class="btn btn-inverse waves-effect waves-light">重 置</button>
							</div>
						</div>
						-->
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-12">
		<div class="card">
			<div class="card-body">
				<div class="table-responsive">
					<div id="toolbar" class="btn-group">
						<button id="btn_add" type="button" class="btn btn-primary m-l-5">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
						</button>&nbsp;
						<button id="btn_edit" type="button" class="btn btn-success m-l-5" onclick="showModify('modify')">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改
						</button>&nbsp;
						<button id="btn_delete" type="button" class="btn btn-danger m-l-5" onclick="del('/uppcrd/doDel','myTable')">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						</button>&nbsp;
						<button id="btn_info" type="button" class="btn btn-info m-l-5" data-toggle="modal" onclick="showModal()">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>浏览
						</button>&nbsp;
					</div>
					<table id="myTable" class="table table-hover"
						data-filter-control="true"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" id="viewModal">
  <div class="modal-dialog modal-lg" role="document" style="height:530px;width: 900px;">
    <div class="modal-content">
          <form action="javascript:;" novalidate="novalidate">
              <div class="modal-header card-success">
	              <h5 class="modal-title" style="color: #fff">详细信息</h5>
	              <button type="button" class="btn btn-box-tool" data-dismiss="modal" aria-label="Close">
					  <i class="fa fa-times"></i>
				  </button>
              </div>
              <div class="modal-body" style="overflow: auto;height: 400px;">
                  <div class="">
                      <table width="100%" class="table table-bordered">
                  	 	<#-- 循环生成查询表格 -->
  						<#list columns as col>
  							<#if (col_index%2==0)>
							<tr>
							</#if>
								<td align="right" width="15%">${col.culumnExplain}:</td>
								<td id="view_${col.columnName}" align="left"></td>
							<#if (col_index%2==1) || !col_has_next>
							</tr>
							</#if>
						</#list>
                      </table>
                                  
                  </div>

              </div>
              <div class="modal-footer">
              <button type="submit" class="btn btn-primary">提交</button>
              <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
              </div>
          </form>
    </div>
  </div>
</div>
<!--添加/修改窗口-->
<div class="modal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true" id="addOrUpModal">
	<div class="modal-dialog modal-lg" role="document" style="height: 500px; width: 600px;">
		<div class="modal-content">
			<form action="javascript:;" class="form-valide" id="addOrUpForm">
				<input type="hidden" name="id" id="id"/>
				<div class="modal-header card-success">
					<h5 class="modal-title" style="color: #fff" id="addOrUpModalTitle">新增</h5>
					<button type="button" class="btn btn-box-tool" data-dismiss="modal" aria-label="Close">
						<i class="fa fa-times"></i>
					</button>
				</div>
				<div class="modal-body" style="overflow-x: hidden;overflow-y: auto; height: 300px;">
					<#list columns as col>
					<div class="form-group row">
						 <label class="col-sm-3 control-label" style="margin-top: 8px;" for="${col.columnName}">${col.culumnExplain} <span class="text-danger">*</span>:</label>
						<div class="col-lg-8">
							<input type="text" id="${col.columnName}" name="${col.columnName}" class="form-control input-default" >
						</div>
					</div>
					</#list>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="addOrUpdate()" id="addOrUpBtn">提交</button>&nbsp;
					<button type="button" class="btn btn-secondary" data-dismiss="modal"
						onclick="hideModal()">取消</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- All Jquery -->
<script src="base/js/lib/bootstraptable/bootstrap-table.min.js"></script>
<script src="base/js/lib/toastr/toastr.min.js"></script>
<script src="base/js/lib/sweetalert/sweetalert.min.js"></script>
<script src="base/js/lib/bootstraptable/bootstrap-table-locale-all.min.js"></script>
<script src="base/js/common.js"></script>

<script>
//显示浏览窗口
function showModal() {
	//得到选中行的数据
    var rows = $('#myTable').bootstrapTable('getSelections');
    if (rows.length <= 0 || rows.length > 1) {
    	toastr.warning('请选择一条信息浏览详情！');
        return;
    }
	$.ajax({
		url: "/${url}/${rpFlg}View?${urlParam}",
		cache: false, //是否启用缓存
		type: "POST", //提交方式
		data: "",
		//contentType:"application/json",
		dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		success: function(obj){
			for(var key in obj){
				$("#view_"+key).html(obj[key]);
			}
		}
	});
    $('#viewModal').modal('show');
}
$(document).ready(function() {
		$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);

		var mytable = $('#myTable').bootstrapTable({
			url : "/${url}/${rpFlg}Main", //请求后台的URL（*）
			method : 'post', //请求方式（*）
			toolbar : '#toolbar', //工具按钮用哪个容器
			//striped: true,                      //是否显示行间隔色
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			sortable : true, //是否启用排序
			sortOrder : "asc", //排序方式
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页,并记录
			pageSize : 10, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			search : false, //是否显示表格搜索
			//strictSearch: true,
			showColumns : true, //是否显示所有的列（选择显示的列）
			showRefresh : true, //是否显示刷新按钮
			//minimumCountColumns: 2,             //最少允许的列数
			clickToSelect : true, //是否启用点击选中行
			//height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "${columns[0].columnName}", //每一行的唯一标识，一般为主键列
			//showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
			//cardView: true,                    //是否显示详细视图
			detailView : false, //是否显示父子表
			striped : true, //隔行变色
			queryParamsType : '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
			// 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
			//得到查询的参数
			queryParams : function(params) {
				//这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
				var temp = {
					pageSize : params.pageSize, //页面大小
					pageIndex : params.pageNumber, //页码
					sortName : params.sortName, //排序列名  
					sortOrder : params.sortOrder,
					<#-- 循环生成跟查询对应的设置 -->
			  		<#list columns as col>
					"${col.columnName}" : $("#query_${col.columnName}").val(),
					</#list>
				};
				return temp;
			},
			columns : [ {
				checkbox : true,
				visible : true
			//是否显示复选框  
			}, 
			<#-- 循环生成表格头部 -->
	  		<#list columns as col>
			{
				field : '${col.columnName}',
				title : '${col.culumnExplain}',
			},
			</#list>
			{
				field : 'pfAcctId',
				title : '操作',
				align : 'center',
			//events:operateEvents,//给按钮注册事件
			//formatter:addFunctionAlty//表格中增加按钮
			}, ],
			onLoadSuccess : function() {
			},
			onLoadError : function() {
				//showTips("数据加载失败！");
			},
			onDblClickRow : function(row, $element) {
				var id = row.nameid;
				//EditViewById(id, 'view');
			},
		});

		//三个参数，value代表该列的值
		function operateFormatter(value, row, index) {
			if (value / 2 == 0) {
				return '<i class="fa fa-lock" style="color:red"></i>'
			} else {
				return '<i class="fa fa-unlock" style="color:green"></i>'
			}
		}

	});
	function myTableReload() {
		$('#myTable').bootstrapTable('refresh', "");
	}
	// 修改按钮、删除按钮
	function addFunctionAlty(value, row, index) {
		return [
				'<button type="button" id="btn_edit" class="btn btn-default" data-toggle="modal" data-target="#ModalInfo">修改</button>  ',
				'<button id="btn_delete" class="btn btn-warning">删除</button>' ]
				.join('');
	}
	window.operateEvents = {
		// 点击修改按钮执行的方法
		'click #btn_edit' : function(e, value, row, index) {
			// 写自己的方法。。。
		},
		// 点击删除按钮执行的方法
		'click #btn_delete' : function(e, value, row, index) {
			// 写自己的方法。。。
		}
	}
	//重置
	function queryReset() {
		<#-- 循环生成重置查询表格的js -->
	  	<#list columns as col>
	  		$('#query_${col.columnName}').val('');
	  	</#list>
	}
	
	/**
	 * 删除
	 */
	function remove() {
		//得到选中行的数据
	    var rows = $('#myTable').bootstrapTable('getSelections');
	    if (rows.length <= 0) {
	    	toastr.warning('请选择有效的数据！');
	        return;
	    }
	    var ids="";
	    for(var i=0;i<rows.length;i++){
	    	ids += rows[i].id+",";
	    }
	    ids = ids.substring(0,ids.length-1);
	    swal({ 
			  title: "确定要删除吗？", 
			  //text: "你将无法恢复该虚拟文件！", 
			  type: "warning",
			  showCancelButton: true, 
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "确定", 
			  cancelButtonText: '取消',
			  closeOnConfirm: false
		},function(){
			$.ajax({
				url: "/${url}/${rpFlg}Delete?${urlParam}",
				cache: false, //是否启用缓存
				type: "GET", //提交方式
				data: "",
				//contentType:"application/json",
				dataType:'text',    //返回的数据格式：json/xml/html/script/jsonp/text
				success: function(obj){
					if(obj == '1' || obj == 1){
						swal('提示信息','操作成功！','success');
						myTableReload();
					}
				}
			});
		});
	}
	//隐藏窗口并重置表单
	function hideModal(){
		$("#addOrUpForm")[0].reset();
		$("#addOrUpModal").modal('hide');
	}
	//添加或者删除
	function addOrUpdate(){
		if (!$("#addOrUpForm").valid()) {
			return;
		}
		$('#addOrUpBtn').prop('disabled', true);
		var modalTile = $('#addOrUpModalTitle').text();
		var url = '/${url}/${rpFlg}Add';
		if(modalTile == '修改'){
			url = '/${url}/${rpFlg}Update';
		}
		var jsonObj = $("#addOrUpForm").serializeObject();
		var jsonData = JSON.stringify(jsonObj);
		$.ajax({
			type : 'post',
			url : url,
			cache : false,
			data : jsonData,
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			success : function(data) {
				swal('提示信息','操作成功！','success');
				
				hideModal();
				
				$('#addOrUpBtn').prop('disabled', false);
				myTableReload();
			},
			error : function() {
				swal('提示信息','操作失败!','error');
				$('#addOrUpBtn').prop('disabled', false);
			}
		});
	}
	//显示添加窗口
	function showAdd(flag) {
		$("#addOrUpForm")[0].reset();
		if(flag=='add'){
			$("#addOrUpModalTitle").html("新增");
		}
		//得到选中行的数据
		$.ajax({
			url: "/${url}/${rpFlg}Add",
			cache: false, //是否启用缓存
			type: "post", //提交方式
			data: "",
			contentType:"application/json",
			dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			success: function(obj){
				genOption(obj.state,"state",null);
				genOption(obj.branchType,"dbtrBranch",null);
				genOption(obj.branchType,"cdtrBranch",null);
				genOption(obj.clearFlg,"clearFlg",null);
				$('#addOrUpModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			},
			error : function() {
				swal('提示信息','请求数据失败!','error');
			}
		});
	}
	//显示修改窗口
	function showModify(flag) {
		$("#addOrUpForm")[0].reset();
		//得到选中行的数据
	    var rows = $('#myTable').bootstrapTable('getSelections');
	    if (rows.length <= 0 || rows.length > 1) {
	    	toastr.warning('请选择一条信息进行修改操作！');
	        return;
	    }
		if(flag=='modify'){
			$("#addOrUpModalTitle").html("修改");
		}
		//得到选中行的数据
		$.ajax({
			url: "/${url}/${rpFlg}Update?${urlParam}",
			cache: false, //是否启用缓存
			type: "post", //提交方式
			data: "",
			contentType:"application/json",
			dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			success: function(obj){
				for(var key in obj.cfgDo){
					$("#"+key).val(obj.cfgDo[key]);
				}
				genOption(obj.state,"state",obj.cfgDo.state);
				genOption(obj.branchType,"dbtrBranch",obj.cfgDo.dbtrBranch);
				genOption(obj.branchType,"cdtrBranch",obj.cfgDo.cdtrBranch);
				genOption(obj.clearFlg,"clearFlg",obj.cfgDo.clearFlg);
				$("#addOrUpForm").valid();
				$('#addOrUpModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			},
			error : function() {
				swal('提示信息','请求数据失败!','error');
			}
		});
	}
	var form_validation = function() {
	    var e = function() {
	            jQuery(".form-valide").validate({
	                ignore: [],
	                errorClass: "invalid-feedback animated fadeInDown",
	                errorElement: "div",
	                errorPlacement: function(e, a) {
	                    jQuery(a).parents(".form-group > div").append(e)
	                },
	                highlight: function(e) {
	                    jQuery(e).closest(".form-group").removeClass("is-invalid").addClass("is-invalid")
	                },
	                success: function(e) {
	                    jQuery(e).closest(".form-group").removeClass("is-invalid"), jQuery(e).remove()
	                },
	                rules: {
	                    "需要验证的元素ID": {
	                    	required: !0,
	                    	digits: !0,
	                    	maxlength: 768
	                    }
	                },
	                messages: {
		                "需要验证的元素ID": {
	                    	required: "匹配规则编号不能为空!",
	                    	digits: "只能输入数字！!",
	                    	maxlength: "字符长度不能超过768个!"
	                    }
	                }
	            })
	        }
	    return {
	        init: function() {
	            e(), a(), jQuery(".js-select2").on("change", function() {
	                jQuery(this).valid()
	            })
	        }
	    }
	}();
	jQuery(function() {
	    form_validation.init()
	});
</script>
