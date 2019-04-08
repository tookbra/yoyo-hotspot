<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 渠道 - 修改 </title>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="/channel/update">
                        <input type="hidden" id="id" name="id" value="${channel.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">渠道名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" class="form-control" type="text" value="${channel.name}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">渠道编码：</label>
                            <div class="col-sm-8">
                                <input id="token" name="token" class="form-control" type="text" value="${channel.token}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">店家名称：</label>
                            <div class="col-sm-8">
                                <input id="storeName" name="storeName" class="form-control" type="text" value="<#if channel.storeName??>${channel.storeName}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">视频地址：</label>
                            <div class="col-sm-8">
                                <input id="storeVideo" name="storeVideo" class="form-control" type="text" value="<#if channel.storeVideo??>${channel.storeVideo}</#if>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有活动：</label>
                            <div class="col-sm-8">
                                <select name="activityed" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0" <#if channel.activityed == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if channel.activityed == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有押金：</label>
                            <div class="col-sm-8">
                                <select name="deposit" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0" <#if channel.deposit == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if channel.deposit == 1>selected="selected"</#if>>是</option>
                                </select>
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

<script type="text/javascript">
    $(document).ready(function () {

        $("#frm").validate({
            rules: {
                name: {
                    required: true,
                },
                token: {
                    required: true,
                },
                activityed: {
                    required: true,
                },
                deposit: {
                    required: true,
                }
            },
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/channel/update",
                    data: $(form).serialize(),
                    success: function(res){
                        if (res.code != 1) {
                            alert(res.msg);
                        } else {
                            layer.msg(res.msg, {time: 2000},function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index);
                            });
                        }
                    }
                });
            }
        });

    });
</script>

</body>

</html>
