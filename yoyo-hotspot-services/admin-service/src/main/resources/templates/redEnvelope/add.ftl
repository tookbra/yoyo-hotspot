<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 红包 - 新增 </title>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="/redEnvelope/add">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">总金额(元)：</label>
                            <div class="col-sm-8">
                                <input id="money" name="money" class="form-control" type="tel"  placeholder="0.00">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否可用：</label>
                            <div class="col-sm-8">
                                <select name="state" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>

                        <#-- TODO 需要知道哪些红包类型 -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">红包类型：</label>
                            <div class="col-sm-8">
                                <select name="channel" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">支付押金</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">红包数量：</label>
                            <div class="col-sm-8">
                                <input id="count" name="count" class="form-control" type="number"  placeholder="0" min="1">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否已生成：</label>
                            <div class="col-sm-8">
                                <select name="isGenerate" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否是国际版优惠券：</label>
                            <div class="col-sm-8">
                                <select name="isEn" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">开始日期：</label>
                            <div class="col-sm-8">
                                <input id="beginTime" name="beginTime" class="laydate-icon form-control layer-date">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">过期时间：</label>
                            <div class="col-sm-8">
                                <input id="endTime" name="endTime" class="laydate-icon form-control layer-date">
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
            elem: '#beginTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        //外部js调用
        laydate({
            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        $("#frm").validate({
            rules:{
                money: 'required',
                state: 'required',
                channel: 'required',
                beginTime: 'required',
                endTime: 'required'
            },
            messages:{
                money:{
                    required:"请输入金额！"
                },
                state:{
                    required:"请选择是否可用！"
                },
                channel:{
                    required:"请选择红包类型！"
                },
                beginTime:{
                    required:"请选择开始时间！"
                },
                endTime:{
                    required:"请选择结束时间！"
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/redEnvelope/add",
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
