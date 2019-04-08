<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> 设置权限 </title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet" />
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet" />
    <link href="/css/animate.css" rel="stylesheet" />
    <link href="/css/style.css?v=4.1.0" rel="stylesheet" />
    <link href="/css/plugins/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>选择菜单</h5>
                    </div>
                    <div class="ibox-content">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                </div>
				<div class="col-sm-6 col-sm-offset-6">
					<button class="btn btn-primary" type="button" id="btnSave">提交</button>
				</div>
            </div>
        </div>
    </div>

    <!-- 全局js -->

    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>
    
    <script src="/js/content.js?v=1.0.0"></script>

    <script src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="/js/plugins/validate/messages_zh.min.js"></script>
    <script src="/js/plugins/layer/layer.min.js"></script>
    <script src="/js/plugins/layer/laydate/laydate.js"></script>
    <script src="/js/plugins/zTree/jquery.ztree.all.min.js"></script>

	<script type = "text/javascript">
        var setting = {
            check : {
                enable : true
            },
            data : {
                simpleData : {
                    enable : true
                }
            }
        };
        setting.check.chkboxType = {
            "Y" : "ps",
            "N" : "s"
        };

        $(document).ready(function() {
            $.ajax({
                url : "/role/getTreeMenuByRoleId/" + ${roleId},
                type : "GET",
                dataType : 'json',
                success : function(msg) {
                    $.fn.zTree.init($("#tree"), setting, msg);
                }
            });

            // 提交
            $("#btnSave").click(function (){
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                var nodes = treeObj.getCheckedNodes(true);
                var selectIds = "";
                for(var index in nodes){
                    var item = nodes[index];
                    selectIds += item.id + ",";
                }
                $.ajax({
                    url : "/role/grant/" + ${roleId} + "?t=Math.random()",
                    type : "POST",
                    dataType : "json",
                    data : {"menuIds" : selectIds.substr(0, selectIds.length-1)},
                    success : function(msg) {
                        layer.msg(msg.msg, {time: 2000},function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index);
                        });
                    },
                    error : function(r,s,m) {
                        alert(r.msg);
                    }
                });

            });

        });

    </script>
</body>
</html>
