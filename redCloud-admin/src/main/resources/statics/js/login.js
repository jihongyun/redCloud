$(function () {
    $('#codeimage').on('click', function() {
        this.src="captcha.jpg?t=" + $.now();
    });

    layui.form.on('submit(login)', function(data) {
        var data = "username="+data.field.username+"&password="+data.field.password+"&captcha="+data.field.captcha;
        $.ajax({
            type: "POST",
            url: "sys/login",
            data: data,
            dataType: "json",
            async: false,
            success: function(result){
                debugger
                console.log(result)
                if(result.code == 0){//登录成功
                    layer.msg('登录成功', {
                        icon: 1,
                        time: 1000
                    });
                    window.location.href = 'index.html';
                }else if(result.code == 501){//验证码错误
                    layer.tips(result.msg, $('#captcha'), {
                        tips: [3, '#FF5722']
                    });
                }else{
                    layer.tips(result.msg, $('#password'), {
                        tips: [3, '#FF5722']
                    });
                }
                $('#codeimage').attr("src", "captcha.jpg?t=" + $.now());
            }
        });
        return false;
    });
});
