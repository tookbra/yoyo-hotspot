<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title> 悠 - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/login.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>

</head>

<body class="signin">

    <div class="signinpanel">
        <div class="middle-box text-center loginscreen  animated fadeInDown">
            <div class="col-sm-12">
                <#--<h2>MeFi管理系统</h2>-->
                <#--<br/>-->
                <span class="block m-t-xs" style="font-size:30px;">
                    <strong class="font-bold">MeFi管理系统</strong>
                </span>

                <#if message?exists >
                    <div class="alert alert-danger">
                        ${message!}
                    </div>
                </#if>

                <form class="m-t" role="form" action="/login" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="name" placeholder="账号" required="">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password"  placeholder="密码" required="">
                    </div>
                    <button type="submit" class="btn btn-info block full-width m-b">登 录</button>
                </form>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="/js/content.js?v=1.0.0"></script>

    <!-- jQuery Validation plugin javascript-->
    <script src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="/js/plugins/validate/messages_zh.min.js"></script>

    <script type="text/javascript">

        $().ready(function() {
            // 在键盘按下并释放及提交后验证提交表单
            $("#frm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 2
                    },
                    password: {
                        required: true,
                        minlength: 5
                    }
                },
                messages: {
                    name: {
                        required: "请输入用户名",
                        minlength: "用户名必需由两个字母组成"
                    },
                    password: {
                        required: "请输入密码",
                        minlength: "密码长度不能小于6位"
                    }
                },
                submitHandler:function(form){
                    form.submit();
                }
            });
        });

    </script>
</body>

</html>
