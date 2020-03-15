$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'act/process/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:100, title: '流程ID'},
            {field:'deploymentId', minWidth:70, title: '部署ID'},
            {field:'name', minWidth:100, title: '流程名称'},
            {field:'key', minWidth:70, title: '流程标识'},
            {field:'version', minWidth:70, title: '流程版本'},
            {field: 'resourceName', minWidth:120, title: '流程XML',  align:'center', templet: function(d){
                return '<a target="_blank" href="'+ baseURL +'act/process/resource?deploymentId='+d.deploymentId+'&resourceName='+d.resourceName+'" style="color:#01AAED;">'+d.resourceName+'</a>';
            }},
            {field: 'diagramResourceName', minWidth:100, title: '流程图片',  align:'center', templet: function(d){
                return '<a target="_blank" href="'+ baseURL +'act/process/resource?deploymentId='+d.deploymentId+'&resourceName='+d.diagramResourceName+'" style="color:#01AAED;">'+d.diagramResourceName+'</a>';
            }},
            {field:'deploymentTime', minWidth:100, title: '部署时间'},
            {title: '操作', width:200, templet:'#barTpl',fixed:"right",align:"center"}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });

    //文件上传
    layui.upload.render({
        elem: '#upload',
        url: baseURL + "act/process/deploy",
        accept: 'file',
        exts: 'zip|bar|bpmn|bpmn20.xml',
        field: 'processFile',
        before: function(){
            layer.load();
        },
        done: function(r){
            layer.closeAll('loading');
            if(r.code == 0){
                vm.showForm = false;

                layer.confirm('部署成功', {
                    closeBtn: 0,
                    btn: ['确定']
                }, function(){
                    layer.closeAll();

                    vm.reload();
                });
            }else{
                alert(r.msg);
            }
        }
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
            var ids = [data.deploymentId];
            vm.del(ids);
        }else if(layEvent === 'active'){
            vm.active(data.id);
        }else if(layEvent === 'suspend'){
            vm.suspend(data.id);
        }else if(layEvent === 'convertToModel'){
            vm.convertToModel(data.id);
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            processName: null,
            key: null
		},
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
                ids.push(item.deploymentId);
            });
            return ids;
        },
		query: function () {
			vm.reload();
		},
		del: function (ids) {
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "act/process/delete",
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
        active: function (id) {
            confirm('确定要激活吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "act/process/active",
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
        suspend: function (id) {
            confirm('确定要挂起吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "act/process/suspend",
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
        convertToModel: function (id) {
            confirm('确定要转换为模型吗？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "act/process/convertToModel",
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
        deploy: function(){
            var index = layer.open({
                title: "部署流程文件",
                area: ['400px', '300px'],
                type: 1,
                resize:false,
                content: $("#deployForm"),
                end: function(){
                    vm.showForm = false;
                    layer.closeAll();
                }
            });

            vm.showForm = true;
        },
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    processName: vm.q.processName,
                    key: vm.q.key
                }
            });
		}
	}
});