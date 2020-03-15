var contentEdit;
var layedit = layui.layedit;
$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'sys/mailtemplate/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:100, title: '模板ID'},
            {field:'name', minWidth:100, title: '模板名称'},
            {field:'subject', minWidth:200, title: '邮件主题'},
            {field:'createDate', width:200, title: '创建时间'},
            {title: '操作', width:150, templet:'#barTpl',fixed:"right",align:"center"}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });

    layedit.set({
        uploadImage: {
            url: baseURL + "sys/oss/upload",
            type: 'post'
        }
    });

    layui.form.on('submit(saveConfig)', function(){
        vm.saveConfig();
        return false;
    });

    layui.form.on('submit(saveTemplate)', function(){
        vm.saveTemplate();
        return false;
    });

    layui.form.on('submit(sendSms)', function(){
        vm.sendSms();
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
            vm.updateTemplate(data.id);
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
            mobile: null,
            status: null
        },
        showForm: false,
        addForm: false,
        smsForm: false,
        template: {},
        config: {},
        mail: {}
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
            $.getJSON(baseURL + "sys/mailtemplate/config", function(r){
				vm.config = r.data;
            });
        },
        addTemplate: function(){
            vm.template = {};
            var index = layer.open({
                title: "新增模板",
                type: 1,
                content: $("#addForm"),
                end: function(){
                    vm.addForm = false;
                    layer.closeAll();
                }
            });

            contentEdit = layedit.build('content');

            layedit.setContent(contentEdit, null);

            vm.addForm = true;
            layer.full(index);
        },
        updateTemplate: function (id) {
            $.get(baseURL + "sys/mailtemplate/info/"+id, function(r){
                vm.template = r.data;

                layedit.setContent(contentEdit, vm.template.content);
            });

            var index = layer.open({
                title: "修改",
                type: 1,
                content: $("#addForm"),
                end: function(){
                    vm.addForm = false;
                    layer.closeAll();
                }
            });

            contentEdit = layedit.build('content');

            vm.addForm = true;
            layer.full(index);
        },
        saveTemplate: function (event) {
            var url = vm.template.id == null ? "sys/mailtemplate/save" : "sys/mailtemplate/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                data: {
                    id: vm.template.id,
                    name: vm.template.name,
                    subject: vm.template.subject,
                    content: layedit.getContent(contentEdit)
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
		saveConfig: function () {
			var url = baseURL + "sys/mailtemplate/saveConfig";
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
                title: "发送邮件",
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
            var l = layer.load();
            var url = baseURL + "sys/mailtemplate/send";
            $.ajax({
                type: "POST",
                url: url,
                data: {
                    templateId: vm.mail.templateId,
                    mailTo: vm.mail.mailTo,
                    mailCc: vm.mail.mailCc,
                    params: vm.mail.params
                },
                success: function(r){
                    layer.close(l);
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
                    url: baseURL + "sys/mailtemplate/delete",
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
                    name: vm.q.name
                }
            });
		}
	}
});