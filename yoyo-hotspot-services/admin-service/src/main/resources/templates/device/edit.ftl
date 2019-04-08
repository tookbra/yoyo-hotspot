<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 设备 - 修改 </title>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="/device/update">
                        <input type="hidden" id="id" name="id" value="${device.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">机柜ID：</label>
                            <div class="col-sm-8">
                                <input id="boxId" name="boxId" class="form-control" type="text" value="${device.boxId}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">机柜版本号：</label>
                            <div class="col-sm-8">
                                <input id="version" name="version" class="form-control" type="text" value="${device.version}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">剩余充电宝个数：</label>
                            <div class="col-sm-8">
                                <input id="remainNum" name="remainNum" class="form-control" type="tel" value="${device.remainNum}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">上次心跳时间：</label>
                            <div class="col-sm-8">
                                <input id="lastHeart" name="lastHeart" class="laydate-icon form-control layer-date" value="${device.lastHeart?string('yyyy-MM-dd HH:mm:ss')}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">服务器地址：</label>
                            <div class="col-sm-8">
                                <input id="serverAddress" name="serverAddress" class="form-control" type="text" value="<#if device.serverAddress??>${device.serverAddress}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">服务器端口：</label>
                            <div class="col-sm-8">
                                <input id="serverPort" name="serverPort" class="form-control" type="tel" value="<#if device.serverPort??>${device.serverPort}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">精度：</label>
                            <div class="col-sm-8">
                                <input id="longitude" name="longitude" class="form-control" type="tel" value="<#if device.longitude??>${device.longitude}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">纬度：</label>
                            <div class="col-sm-8">
                                <input id="latitude" name="latitude" class="form-control" type="tel" value="<#if device.latitude??>${device.latitude}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">存放地址：</label>
                            <div class="col-sm-8">
                                <input id="address" name="address" class="form-control" type="text" value="<#if device.address??>${device.address}</#if>">
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

        //外部js调用
        laydate({
            elem: '#lastHeart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        $("#frm").validate({
            rules: {
                boxId: {
                    required: true,
                    minlength: 2,
                    maxlength: 40
                },
                version: {
                    required: true,
                    minlength: 2,
                    maxlength: 40
                }
            },
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/device/update",
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
