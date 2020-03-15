$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'act/model/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:100, title: '模型ID'},
            {field:'name', minWidth:100, title: '模型名称'},
            {field:'key', minWidth:70, title: '模型标识'},
            {field:'version', minWidth:70, title: '模型版本'},
            {field:'createTime', minWidth:100, title: '创建时间'},
            {field:'lastUpdateTime', minWidth:100, title: '更新时间'},
            {title: '操作', width:240, templet:'#barTpl',fixed:"right",align:"center"}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
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
        if(layEvent === 'del'){
            var ids = [data.id];
            vm.del(ids);
        }else if(layEvent === 'deploy'){
            vm.deploy(data.id);
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            name: null,
            key: null
		},
        model:{},
        showForm: false
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
            vm.model = {};

            var index = layer.open({
                title: "新增",
                type: 1,
                content: $("#editForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            vm.showForm = true;
            layer.full(index);
        },
        saveOrUpdate: function (event) {
            $.ajax({
                type: "POST",
                url: baseURL + "act/model/save",
                contentType: "application/json",
                data: JSON.stringify(vm.model),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            layer.closeAll();
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "act/model/delete",
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
        deploy: function (id) {
            confirm('确定要部署吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "act/model/deploy",
                    data: "id=" + id,
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    name: vm.q.name,
                    key: vm.q.key
                }
            });
		}
	}
});