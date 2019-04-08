<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>会员账户充值记录列表</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

    <!-- Data Tables -->
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>账户充值记录列表</h5>
                </div>
                <div class="ibox-content">
                    <!-- 添加查询 -->
                    <form class="form-inline" id="search_form" name="search_form">
                        <div class="form-group">
                            <label class="control-label" for="orderNo">订单编号:</label>
                            <input class="form-control" type="text" id="orderNo" name="orderNo"/>
                        </div>

                        <button style="margin-top: 5px;" type="button" id="search" class="btn btn-success" onclick="searchRecharge()">
                            <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                        </button>
                    </form>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Card View -->
                            <div class="example-wrap">
                                <div class="example">
                                    <table id="table_list" class="table table-bordered table-striped"></table>
                                </div>
                            </div>
                            <!-- End Example Card View -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="/js/jquery.min.js?v=2.1.4"></script>
<script src="/js/bootstrap.min.js?v=3.3.6"></script>

<!-- Bootstrap table -->
<script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- Peity -->
<script src="/js/plugins/peity/jquery.peity.min.js"></script>

<script src="/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="/js/content.min.js?v=1.0.0"></script>

<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        // 初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            // 使用get请求到服务器获取数据
            method: "GET",
            // 必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            // 获取数据的Servlet地址
            url: "/recharge/listRecharges",
            //表格显示条纹
            striped: true,
            // 启动分页
            pagination: true,
            // 每页显示的记录数
            limit: 10,
            // 当前第几页
            page: 1,
            // 记录数可选列表
            pageList: [10, 20, 50],
            // 是否启用查询
            search: false, //显示搜索框
            // 是否显示刷新按钮
            showRefresh: false,
            // 表示服务端请求
            sidePagination: "server",
            // 得到查询的参数
            queryParams : queryParams,
            // json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.data,
                    "total": res.totalCount
                };
            },
            //数据列
            columns: [
                {title: "手机号码", field: "phone"},
                {title: "国家", field: "country"},
                {title: "是否国际版本", field: "en", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "订单编号", field: "orderNo"},
                {title: "充值金额", field: "amount"},
                {title: "充值状态", field: "status", formatter: function(value, row, index) {
                        switch (value) {
                            case 0 :
                                return '<span class="label label-info">初始</span>';
                            case 1 :
                                return '<span class="label label-primary">处理中</span>';
                            case 2 :
                                return '<span class="label label-success">成功</span>';
                            default:
                                return '<span class="label label-danger">失败</span>';
                        }
                    }
                },
                {title: "支付方式 ", field: "payChannel", formatter: function(value, row, index) {
                        switch (value) {
                            case 1 :
                                return '<span class="label label-warning">账户余额</span>';
                            case 2 :
                                return '<span class="label label-info">微信支付</span>';
                            case 3 :
                                return '<span class="label label-primary">支付宝</span>';
                            case 4 :
                                return '<span class="label label-inverse">paypal</span>';
                            default:
                                return '<span class="label label-danger">其他</span>';
                        }
                    }
                },
                {title: "创建时间", field: "createTime" }
            ]
        });
    });

    /**
     * 设置额外BootstrapTable请求参数
     **/
    function queryParams(params) {
        return {
            // 不能写数字，需要用params
            pageSize: params.limit,
            // 当前第几页
            pageNumber: (params.offset / params.limit) + 1,
            searchText: $('#search_form input[name=\'orderNo\']').val(),
        }
    }

    /**
     * 查询
     */
    function searchRecharge() {
        // 很重要的一步，刷新url！
        $('#table_list').bootstrapTable(('refresh'));
        $('#search_form input[name=\'orderNo\']').val();
    }

</script>

</body>

</html>
