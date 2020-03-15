var contentEdit;
var layedit = layui.layedit;
$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'demo/news/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:150, title: 'ID'},
            {field:'title', minWidth:200, title: '标题'},
            {field:'pubTime', width:200, title: '发布时间'},
            {field:'createDate', width:200, title: '创建时间'},
            {title: '操作', width:150, templet:'#barTpl',fixed:"right",align:"center"}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });

    layui.laydate.render({
        elem: '#startDate',
        type: 'date',
        done: function(value, date, endDate){
            vm.q.startDate = value;
        }
    });

    layui.laydate.render({
        elem: '#endDate',
        type: 'date',
        done: function(value, date, endDate){
            vm.q.endDate = value;
        }
    });

    layui.laydate.render({
        elem: '#pubTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function(value, date, endDate){
            vm.news.pubTime = value;
        }
    });

    layedit.set({
        uploadImage: {
            url: baseURL + "sys/oss/upload",
            type: 'post'
        }
    });

    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });


    //批量删除
    $(".delBatch").click(function(){
        var ids = vm.selectedRows();
        if(ids == null){
            return;
        }

        vm.del(ids);
    });

    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){
            vm.update(data.id);
        } else if(layEvent === 'del'){
            var ids = [data.id];
            vm.del(ids);
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			title: null,
            startTime: null,
            endTime: null
		},
        showForm: false,
		news: {}
	},
    updated: function(){
        layui.form.render();
    },
	methods: {
        selectedRows: function () {
            var list = layui.table.checkStatus('gridid').data;
            if(list.length == 0){
                alert("请选择一条记录");
                return ;
            }

            var ids = [];
            $.each(list, function(index, item) {
                ids.push(item.id);
            });
            return ids;
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.news = {};
            var index = layer.open({
                title: "新增",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            contentEdit = layedit.build('content');

            layedit.setContent(contentEdit, null);

            vm.showForm = true;
            layer.full(index);
		},
		update: function (id) {
			$.get(baseURL + "demo/news/info/"+id, function(r){
                vm.news = r.data;

                layedit.setContent(contentEdit, vm.news.content);
            });

            var index = layer.open({
                title: "修改",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            contentEdit = layedit.build('content');

            vm.showForm = true;
            layer.full(index);
		},
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "demo/news/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.news.id == null ? "demo/news/save" : "demo/news/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    data: {
				    id: vm.news.id,
                    title: vm.news.title,
				    content: layedit.getContent(contentEdit),
                    pubTime: vm.news.pubTime
                },
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
                            layer.closeAll();
                            vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    title: vm.q.title,
                    startDate: vm.q.startDate,
                    endDate: vm.q.endDate
                }
            });
		}
	}
});