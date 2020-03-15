$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'sys/log/list',
        cols: [[
            {field:'id', width:50, title: 'ID'},
            {field:'username', width:100, title: '用户名'},
            {field:'operation', width:150, title: '用户操作'},
            {field:'method', minWidth:100, title: '请求方法'},
            {field:'params', minWidth:100, title: '请求参数'},
            {field:'time', width:130, title: '执行时长(毫秒)'},
            {field:'ip', width:120, title: 'IP地址'},
            {field:'createDate', width:170, title: '创建时间'}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
        exports: function () {
            var url = baseURL + 'sys/log/export';
            if(vm.q.key != null){
                url += '?key='+vm.q.key;
            }
            window.location.href = url;
        },
		reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    key: vm.q.key
                }
            });
		}
	}
});