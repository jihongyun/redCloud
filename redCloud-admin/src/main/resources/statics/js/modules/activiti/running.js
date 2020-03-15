$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'act/process/running',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:100, title: 'ID'},
            {field:'processInstanceId', minWidth:70, title: '流程实例ID'},
            {field:'processDefinitionKey', minWidth:100, title: '流程定义Key'},
            {field:'processDefinitionId', minWidth:100, title: '流程定义ID'},
            {field:'processDefinitionName', minWidth:70, title: '流程定义名称'},
            {field:'activityId', minWidth:70, title: '当前环节'},
            {field:'suspended', minWidth:100, title: '是否挂起'},
            {title: '操作', width:70, templet:'#barTpl',fixed:"right",align:"center"}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });


    //操作
    layui.table.on('tool(grid)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'del'){
            vm.del(data.processInstanceId);
        }
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            instanceId: null,
            definitionKey: null
		},
        showForm: false
	},
    updated: function(){
        layui.form.render();
    },
	methods: {
		query: function () {
			vm.reload();
		},
		del: function (instanceId) {
			confirm('确定要删除？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "act/process/deleteInstance",
				    data: "instanceId="+instanceId,
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
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    instanceId: vm.q.instanceId,
                    definitionKey: vm.q.definitionKey
                }
            });
		}
	}
});