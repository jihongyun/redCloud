// 侧边栏导航, 加载遍历
$(function () {
  $.ajax({
    url:'sys/menu/nav',
    type:'get',
    async: false,
    success: function(res){
        var htm = [];
        if (res.code === 0) {
            // 遍历
            for (var i = 0; i < res.data.length; i++) {
                var isSubMenu = res.data[i].children && res.data[i].children.length >= 1;
                htm.push('<li class="layui-nav-item" data-tab="' + !isSubMenu + '" data-name="' + res.data[i].title + '" data-id="' + res.data[i].id + '" data-url="' + res.data[i].url + '">');
                htm.push('  <a href="javascript:;">');
                htm.push('    <i class="site-sidebar__menu-icon layui-icon ' + res.data[i].icon + '"></i>');
                htm.push('    <span class="site-sidebar__menu-name">' + res.data[i].title + '</span>');
                htm.push('  </a>');
                if (isSubMenu) {
                    htm = htm.concat(fnCreateSubMenu(res.data[i].children));
                }
                htm.push('</li>');
            }
            // 动态添加tabs
            $('.site-sidebar__menu').append(htm.join('')).find('*[data-tab="true"]').on('click', function () {
                var id = 'id-' + $(this).data('id');
                if ($('.site-content--tabs .layui-tab-title > li[lay-id="' + id + '"]').length <= 0) {
                    layui.element.tabAdd('J_contentTabs', {
                        title: $(this).data('name'),
                        content: '<iframe src="' + $(this).data('url') + '" width="100%" height="100%" frameborder="0" scrolling="yes"></iframe>',
                        id: id
                    });
                }
                layui.element.tabChange('J_contentTabs', id);
            });
        }
    }
  });

    // 递归遍历子菜单
    function fnCreateSubMenu (menuList, htm) {
        htm = htm ? htm : [];
        for (var i = 0; i < menuList.length; i++) {
            var isSubMenu = menuList[i].children && menuList[i].children.length >= 1;
            htm.push('<dl class="layui-nav-child">');
            htm.push('  <dd data-tab="' + !isSubMenu + '" data-name="' + menuList[i].title + '" data-id="' + menuList[i].id + '" data-url="' + menuList[i].url + '">');
            htm.push('    <a href="javascript:;"><i class="site-sidebar__menu-icon layui-icon ' + menuList[i].icon + '"></i>' + menuList[i].title + '</a>');
            if (isSubMenu) {
                fnCreateSubMenu(menuList[i].children, htm);
            }
            htm.push('  </dd>');
            htm.push('</dl>');
        }
        return htm;
    }
});

// 侧边栏导航, 依赖element模块 和 折叠状态
$(function () {
    layui.use(['element', 'layer'], function() {
        var idx = 0;
        $('.site-sidebar .layui-nav-item').hover(
            function () {
                if ($('.site-wrapper').hasClass('site-sidebar--fold')) {
                    idx = layer.tips($('.site-sidebar__menu-name', this).html(), this, { time: 0 });
                }
            },
            function () {
                layer.close(idx);
            }
        ).on('click', function () {
            layer.close(idx);
            $('.site-wrapper').removeClass('site-sidebar--fold');
        })
    });
});

// 侧边栏导航, 切换
$(function () {
    var cls = 'site-sidebar--fold';
    $('.site-navbar__switch').on('click', function() {
        var $wrap = $('.site-wrapper');
        $wrap.hasClass(cls) ? $wrap.removeClass(cls) : $wrap.addClass(cls);
    });
    $(window).width() > 992 ? $('.site-wrapper').removeClass(cls) : $('.site-wrapper').addClass(cls);
    $(window).resize(function () {
        $(this).width() > 992 ? $('.site-wrapper').removeClass(cls) : $('.site-wrapper').addClass(cls);
    });
});

// 内容标签页, 前进后退
$(function () {
    var $contentTabs = $('.site-content--tabs');
    $('.content-tabs__header-perv, .content-tabs__header-next', $contentTabs).on('click', function () {
        var $tabTitle = $('.layui-tab-title', $contentTabs);
        if ($(this)[0].className === 'content-tabs__header-perv') {
            $tabTitle.stop(true).animate({ 'left': 0 });
        } else {
            var w = $tabTitle.outerWidth(true) - $tabTitle.width();
            $tabTitle.children('li').each(function () {
                w += $(this).outerWidth(true);
            });
            $tabTitle.stop(true).animate({
                'left': $contentTabs.outerWidth(true) >= w ? 0 : $contentTabs.outerWidth(true) - w
            })
        }
    });
});

// 内容标签页, 工具条
$(function () {
    layui.use(['element'], function() {
        var $contentTabs = $('.site-content--tabs');
        $('.content-tabs__header-tools', $contentTabs).hover(
            function () { $('ul', $(this)).stop(true).slideDown('fast') },
            function () { $('ul', $(this)).stop(true).slideUp('fast') }
        ).find('ul li').on('click', function () {
            var type = $(this).data('type');
            var $li = $('.layui-tab-title > li[lay-id!="home"]', $contentTabs);
            if (type === 'current') { // 关闭当前
                $li.each(function (i) {
                    if ($(this).hasClass('layui-this')) {
                        layui.element.tabDelete('J_contentTabs', $(this).attr('lay-id'));
                        layui.element.tabChange('J_contentTabs', $li.eq(i + 1 < $li.length ? i : i - 1).attr('lay-id'));
                        return false;
                    }
                });
            } else if (type === 'other') { // 关闭其它
                $li.each(function (i) {
                    if (!$(this).hasClass('layui-this')) {
                        layui.element.tabDelete('J_contentTabs', $(this).attr('lay-id'));
                    }
                });
            } else if (type === 'total') { // 关闭全部
                $li.each(function (i) {
                    layui.element.tabDelete('J_contentTabs', $(this).attr('lay-id'));
                });
            }
        })
    });
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        user: {},
        password: '',
        newPassword: ''
    },
    methods: {
        getUser: function () {
            $.getJSON("sys/user/info?_" + $.now(), function (r) {
                vm.user = r.user;
            });
        },
        updatePassword: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function () {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        }
    },
    created: function () {
        this.getUser();
    }
});