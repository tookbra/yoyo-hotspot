<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 优惠券 - 新增 </title>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="/coupon/add">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">优惠券名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" class="form-control" type="text" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">使用类型：</label>
                            <div class="col-sm-8">
                                <select name="useType" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0">无门槛</option>
                                    <option value="1">满减</option>
                                </select>
                            </div>
                        </div>

                        <#-- TODO 需要查询渠道列表 -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">获取渠道：</label>
                            <div class="col-sm-8">
                                <select name="channel" class="form-control">
                                    <option value="0">请选择</option>
                                    <option value="1">酒店</option>
                                </select>
                            </div>
                        </div>

                        <#-- TODO 有哪些条件 -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">获取条件：</label>
                            <div class="col-sm-8">
                                <select name="conditions" class="form-control">
                                    <option value="0">请选择</option>
                                    <option value="1">绑定手机</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">优惠券金额(元)：</label>
                            <div class="col-sm-8">
                                <input id="money" name="money" class="form-control" type="number" placeholder="0.00">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有效：</label>
                            <div class="col-sm-8">
                                <select name="status" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0">无效</option>
                                    <option value="1">有效</option>
                                    <option value="2">过期</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量：</label>
                            <div class="col-sm-8">
                                <input id="num" name="num" class="form-control" type="number" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">开始日期：</label>
                            <div class="col-sm-8">
                                <input id="beginDate" name="beginDate" class="laydate-icon form-control layer-date">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">结束日期：</label>
                            <div class="col-sm-8">
                                <input id="endDate" name="endDate" class="laydate-icon form-control layer-date">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3">是否是国际版优惠券</label>
                            <div class="col-sm-8">
                                <select class="form-control"  name="langEn">
                                    <option value="">请选择</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" >描述</label>
                            <div class="col-sm-8">
                                <textarea id="description" name="description" class="form-control" rows="8"></textarea>
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
            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        //外部js调用
        laydate({
            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        $("#frm").validate({
            rules:{
                name: 'required',
                useType: 'required',
                channel: 'required',
                conditions: 'required',
                money: 'required',
                status: 'required',
                num: 'required',
                beginDate: 'required',
                endDate: 'required',
                langEn: 'required',
                description: 'required',
            },
            messages:{
                name:{
                    required:"请输入优惠券名称！"
                },
                useType:{
                    required:"请选择使用类型！"
                },
                channel:{
                    required:"请选择获取渠道！"
                },
                conditions:{
                    required:"请选择获取条件！"
                },
                money:{
                    required:"请输入金额！"
                },
                status:{
                    required:"请选择是否有效！"
                },
                num:{
                    required:"请输入总数！"
                },
                beginDate:{
                    required:"请选择开始时间！"
                },
                endDate:{
                    required:"请选择结束时间！"
                },
                langEn:{
                    required:"请选择是否是国际版优惠券！"
                },
                description:{
                    required:"请输入描述！"
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/coupon/add",
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
