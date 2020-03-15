$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'sys/maillog/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', width:80, title: 'ID'},
            {field:'templateId', width:80, title: '模板ID'},
            {field:'mailFrom', minWidth:100, title: '发送者'},
            {field:'mailTo', minWidth:100, title: '收件人'},
            {field:'mailCc', minWidth:100, title: '抄送者'},
            {field:'subject', minWidth:100, title: '邮件主题'},
            {field:'status', minWidth:100, title: '状态',  align:'center', templet: function(d){
                if(d.status === 0){
                    return '成功';
                }else if(d.status === 1){
                    return '失败';
                }
            }},
            {field:'sendTime', width:180, title: '发送时间'}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
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
    layui.form.render();
});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            templateId: null,
            mailTo: null,
            status: null
        }
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
        del: function (ids) {
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/maillog/delete",
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
                    templateId: vm.q.templateId,
                    mailTo: vm.q.mailTo,
                    status: vm.q.status
                }
            });
		}
	}
});