$(function () {
    gridTable = layui.table.render({
        id: "gridid",
        elem: '#grid',
        url: baseURL + 'sys/loginlog/list',
        cols: [[
            {type:'checkbox'},
            {field:'id', minWidth:100, title: 'ID'},
            {field:'username', minWidth:100, title: '用户名'},
            {field:'operation', minWidth:100, title: '用户操作'},
            {field:'status', minWidth:100, title: '状态',  align:'center', templet: function(d){
                if(d.status === 0){
                    return '登录成功';
                }else if(d.status === 1){
                    return '登录失败';
                }else if(d.status === 2){
                    return '账号已锁定';
                }
            }},
            {field:'ip', minWidth:100, title: 'IP地址'},
            {field:'createDate', minWidth:100, title: '登录时间'}
        ]],
        page: true,
        loading: true,
        limits: [20, 50, 100, 200],
        limit: 20
    });


    layui.form.on('select(status)', function (data) {
        vm.q.status=data.value;
    });

    layui.form.render();
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: '',
            status: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        exports: function () {
            var url = baseURL + 'sys/loginlog/export?username='+vm.q.username + '&status=' + vm.q.status;
            window.location.href = url;
        },
        reload: function (event) {
            layui.table.reload('gridid', {
                page: {
                    curr: 1
                },
                where: {
                    username: vm.q.username,
                    status: vm.q.status
                }
            });
        }
    }
});