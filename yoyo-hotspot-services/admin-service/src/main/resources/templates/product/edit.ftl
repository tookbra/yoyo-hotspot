<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> 套餐 - 修改 </title>
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
                    <form class="form-horizontal m-t" id="frm" method="post" action="/product/update">
                        <input type="hidden" id="id" name="id" value="${product.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">套餐名称：</label>
                            <div class="col-sm-8">
                                <input id="productName" name="productName" class="form-control" type="text" value="${product.productName}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">套餐类型：</label>
                            <div class="col-sm-8">
                                <select name="productType" class="form-control">
                                    <option value="">请选择套餐类型</option>
                                    <option value="1" <#if product.productType == 1>selected="selected"</#if>>小时借</option>
                                    <option value="2" <#if product.productType == 2>selected="selected"</#if>>包天借</option>
                                    <option value="3" <#if product.productType == 4>selected="selected"</#if>>包月借</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">价格(元)：</label>
                            <div class="col-sm-8">
                                <input id="price" name="price" class="form-control" type="tel" value="${product.price}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否启用：</label>
                            <div class="col-sm-8">
                                <select name="status" class="form-control">
                                    <option value="0">请选择</option>
                                    <option value="0" <#if product.status == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if product.status == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否封顶：</label>
                            <div class="col-sm-8">
                                <select name="caped" class="form-control">
                                    <option value="0">请选择是否封顶</option>
                                    <option value="0" <#if product.caped == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if product.caped == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">封顶价格(元)：</label>
                            <div class="col-sm-8">
                                <input id="capPrice" name="capPrice" class="form-control" type="tel" value="${product.capPrice}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否无限流量：</label>
                            <div class="col-sm-8">
                                <select name="unlimited" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0" <#if product.unlimited == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if product.unlimited == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">最大流量：</label>
                            <div class="col-sm-8">
                                <input id="limited" name="limited" class="form-control" type="tel" value="${product.limited}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否送流量</label>
                            <div class="col-sm-8">
                                <select class="form-control"  ng-model="gifted">
                                    <option value="">请选择</option>
                                    <option value="0" <#if product.gifted == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if product.gifted == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">流量大小：</label>
                            <div class="col-sm-8">
                                <input id="limitNum" name="limitNum" class="form-control" type="text" value="<#if product.limited??>${product.limited}</#if>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">礼品消息(zh)：</label>
                            <div class="col-sm-8">
                                <input id="giftMsg" name="giftMsg" class="form-control" type="text" value="<#if product.giftMsg??>${product.giftMsg}</#if>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">描述(zh)：</label>
                            <div class="col-sm-8">
                                <textarea id="description" name="description" class="form-control" rows="8"><#if product.description??>${product.description}</#if></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">套餐名称(en)</label>
                            <div class="col-sm-8">
                                <input id="productNameEn" name="productNameEn" class="form-control" type="text" value="<#if product.productNameEn??>${product.productNameEn}</#if>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">价格(en)</label>
                            <div class="col-sm-8">
                                <input id="priceEn" name="priceEn" class="form-control" type="number" value="<#if product.priceEn??>${product.priceEn}</#if>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label" >礼品消息(en)</label>
                            <div class="col-sm-8">
                                <input id="giftMsgEn" name="giftMsgEn" class="form-control" type="text" value="<#if product.giftMsgEn??>${product.giftMsgEn}</#if>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label" >描述(en)</label>
                            <div class="col-sm-8">
                                <textarea id="descriptionEn" name="descriptionEn" class="form-control" rows="8"><#if product.descriptionEn??>${product.descriptionEn}</#if></textarea>
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
            rules:{
                productName: 'required',
                productType: 'required',
                price: 'required',
                status: 'required',
                caped: 'required',
                capPrice: 'required',
                unlimited: 'required',
                limited: 'required',
                description: 'required',
                productNameEn: 'required',
                priceEn: 'required',
                descriptionEn: 'required'
            },
            messages:{
                productName:{
                    required:"请输入套餐名称！"
                },
                productType:{
                    required:"请输入套餐类型！"
                },
                price:{
                    required:"请输入价格！"
                },
                status:{
                    required:"请选择状态！"
                },
                caped:{
                    required:"请选择是否封顶！"
                },
                capPrice:{
                    required:"请输入封顶价格！"
                },
                unlimited:{
                    required:"请选择是否无限流量！"
                },
                limited:{
                    required:"请输入最大流量！"
                },
                description:{
                    required:"请输入描述！"
                },
                productNameEn:{
                    required:"请输入英文套餐名称！"
                },
                priceEn:{
                    required:"请输入价格！"
                },
                descriptionEn:{
                    required:"请输入英文机描述！"
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/product/update",
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
