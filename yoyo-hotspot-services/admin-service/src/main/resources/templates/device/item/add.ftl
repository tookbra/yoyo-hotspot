<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 设备明细 - 新增 </title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/multiselect/bootstrap-multiselect.css" rel="stylesheet" />

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" method="post" action="/item/add">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">机柜ID：</label>
                            <div class="col-sm-8">
                                <select id="deviceId" name="deviceId" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" title='请选择机柜ID(必选)' required data-live-search="true">
                                <#foreach device in devices>
                                    <option value="${device.boxId}">${device.boxId}</option>
                                </#foreach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">充电宝ID：</label>
                            <div class="col-sm-8">
                                <input id="powerBankId" name="powerBankId" class="form-control" type="text" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">槽位编号：</label>
                            <div class="col-sm-8">
                                <input id="slot" name="slot" class="form-control" type="text" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">电量：</label>
                            <div class="col-sm-8">
                                <select name="level" class="form-control">
                                    <option value="4">电量选择(默认为100%)</option>
                                    <option value="0">20%</option>
                                    <option value="1">40%</option>
                                    <option value="2">60%</option>
                                    <option value="3">80%</option>
                                    <option value="4">100%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">设备类型：</label>
                            <div class="col-sm-8">
                                <input id="itemType" name="itemType" class="form-control" type="tel" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否被租借：</label>
                            <div class="col-sm-8">
                                <select name="leased" class="form-control">
                                    <option value="0">请选择</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
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
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/plugins/multiselect/bootstrap-multiselect.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

        $("#frm").validate({
            rules: {
                powerBankId: {
                    required: true,
                    minlength: 2,
                    maxlength: 40
                },
                slot: {
                    required: true,
                    minlength: 1,
                    maxlength: 2
                },
                level: {
                    required: true
                }
            },
            messages: {},
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/item/add",
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
