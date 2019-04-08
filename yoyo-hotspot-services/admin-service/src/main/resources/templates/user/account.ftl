<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 帐户信息 </title>
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
                    <form class="form-horizontal m-t" id="frm">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称:</label>
                            <div class="col-sm-5">
                                <input id="name" name="name" class="form-control" type="text" value="${account.name}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">号码:</label>
                            <div class="col-sm-5">
                                <input id="phone" name="phone" class="form-control" type="text" value="${account.phone}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户余额(￥):</label>
                            <div class="col-sm-5">
                                <input id="balance" name="balance" class="form-control" type="text" value="${account.balance}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户余额($):</label>
                            <div class="col-sm-5">
                                <input id="balance" name="balance" class="form-control" type="text" value="${account.balanceEn}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">押金(￥):</label>
                            <div class="col-sm-5">
                                <input id="balance" name="balance" class="form-control" type="text" value="${account.deposit}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">押金($):</label>
                            <div class="col-sm-5">
                                <input id="balance" name="balance" class="form-control" type="text" value="${account.depositEn}" disabled="disabled" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">红包数量：</label>
                            <div class="col-sm-5">
                                <span class="block m-t-xs"><strong class="font-bold"> <h3>${account.redEnvelopeNum}</h3></strong></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">优惠券数量：</label>
                            <div class="col-sm-5">
                                <span class="block m-t-xs"><strong class="font-bold"> <h3>${account.couponNum}</h3></strong></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">积分：</label>
                            <div class="col-sm-5">
                                <input id="integral" name="integral" class="form-control" type="text" value="${account.integral}" disabled="disabled" >
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

</body>

</html>
