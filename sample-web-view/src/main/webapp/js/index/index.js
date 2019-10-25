$(function() {
	$.get("pages/home/home.html",function(data){
		$("#indexFluid").html(data);//初始化加载界面
	});
});

$('.sidebar-list-item a').click(function(){//点击的时候给当前这个加上，其他的移除
	$(this).addClass("active").siblings("a").removeClass("active");
});
	
function changeMenu(url,name) {
	$.get(url,function(data){
		$("#indexFluid").html(data);//初始化加载界面
	});
}


