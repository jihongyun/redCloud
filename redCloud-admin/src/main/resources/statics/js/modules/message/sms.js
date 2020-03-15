$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'sys/sms/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:100, title: 'ID'},
            {field:'platform', minWidth:100, title: '平台类型',  align:'center', templet: function(d){
                if(d.platform === 1){
                    return '阿里云';
                }else if(d.platform === 2){
                    return '腾讯云';
                }
            }},
            {field:'mobile', minWidth:100, title: '手机号'},
            {field:'params1', minWidth:100, title: '参数1'},
            {field:'params2', minWidth:100, title: '参数2'},
            {field:'params3', minWidth:100, title: '参数3'},
            {field:'params4', minWidth:100, title: '参数4'},
            {field:'status', minWidth:100, title: '状态',  align:'center', templet: function(d){
                if(d.status === 0){
                    return '成功';
                }else if(d.status === 1){
                    return '失败';
                }
            }},
            {field:'sendTime', width:200, title: '发送时间'}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });

    layui.form.on('radio(platform)', function(data){
        vm.config.platform = data.value;
    });

    layui.form.on('submit(saveOrUpdate)', function(){
        vm.saveOrUpdate();
        return false;
    });

    layui.form.on('submit(sendSms)', function(){
        vm.sendSms();
        return false;
    });

    layui.form.on('select(status)', function (data) {
        vm.q.status=data.value;
    });

    //批量删除
    $(".delBatch").click(function(){
        var ids = vm.selectedRows();
        if(ids == null){
            return;
        }

        vm.del(ids);
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            mobile: null,
            status: null
        },
        showForm: false,
        smsForm: false,
        config: {},
        sms: {}
	},
    created: function(){
        this.getConfig();
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
		getConfig: function () {
            $.getJSON(baseURL + "sys/sms/config", function(r){
				vm.config = r.data;
            });
        },
		addConfig: function(){
            var index = layer.open({
                title: "短信配置",
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
		saveOrUpdate: function () {
			var url = baseURL + "sys/sms/saveConfig";
			$.ajax({
				type: "POST",
			    url: url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
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
        sendForm: function(){
            var index = layer.open({
                title: "发送短信",
                type: 1,
                content: $("#smsForm"),
                end: function(){
                    vm.smsForm = false;
                    layer.closeAll();
                }
            });

            vm.smsForm = true;
            layer.full(index);
        },
        sendSms: function () {
            var url = baseURL + "sys/sms/send";
            $.ajax({
                type: "POST",
                url: url,
                data: "mobile="+vm.sms.mobile+"&params="+vm.sms.params,
                success: function(r){
                    if(r.code === 0){
                        alert('发送成功', function(){
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
                    url: baseURL + "sys/sms/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code === 0){
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
		reload: function () {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    mobile: vm.q.mobile,
                    status: vm.q.status
                }
            });
		}
	}
});