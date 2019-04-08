<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 设备 - 设置服务器地址 </title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" method="post" action="/operate/setServer">
                        <input type="hidden" id="boxId" name="boxId" value="${boxId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">服务器地址：</label>
                            <div class="col-sm-8">
                                <input id="address" name="address" class="form-control" type="text" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">服务器端口：</label>
                            <div class="col-sm-8">
                                <input id="port" name="port" class="form-control" type="text" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">心跳间隔(1~255 有效)：</label>
                            <div class="col-sm-8">
                                <input id="heartbeat" name="heartbeat" class="form-control" type="tel">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-5">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
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
<script src="/js/plugins/layer/layer.min.js"></script>
<script src="/js/plugins/layer/laydate/laydate.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

        $("#frm").validate({
            rules: {
                address: {
                    required: true,
                    minlength: 1,
                    maxlength: 40
                },
                port: {
                    required: true,
                    minlength: 1,
                    maxlength: 20
                },
                heartbeat: {
                    required: true,
                    minlength: 1,
                    maxlength: 3
                }
            },
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/operate/setServer",
                    data: $(form).serialize(),
                    success: function(res){
                        layer.msg(res.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index);
                        });
                    }
                });
            }
        });
    });
</script>

</body>

</html>
