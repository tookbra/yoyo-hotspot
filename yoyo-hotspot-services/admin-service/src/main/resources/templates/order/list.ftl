<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>订单列表</title>

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
                        <h5>订单列表</h5>
                    </div>
                    <div class="ibox-content">
                        <!-- 添加查询 -->
                        <form class="form-inline" id="search_from" name="search_from">
                            <div class="form-group">
                                <label class="control-label" for="orderNo">订单编号:</label>
                                <input class="form-control" type="text" id="orderNo" name="orderNo"/>
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="payChannel">支付渠道:</label>
                                <select name="payChannel" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">账户余额</option>
                                    <option value="2">微信支付</option>
                                    <option value="3">支付宝</option>
                                    <option value="4">paypal</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="orderType">订单类型:</label>
                                <select name="orderType" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">押金</option>
                                    <option value="2">充值</option>
                                    <option value="3">租借</option>
                                    <option value="4">续费</option>
                                    <option value="5">归还</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="state">订单状态:</label>
                                <select name="state" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="0">订单生成</option>
                                    <option value="1">支付中</option>
                                    <option value="2">支付成功</option>
                                    <option value="3">支付失败</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">创建时间:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="startTime" name="startTime" class="laydate-icon form-control layer-date" placeholder="开始日期" />
                                    <input type="text" id="endTime" name="endTime"  class="laydate-icon form-control layer-date"  placeholder="结束日期">
                                </div>
                            </div>

                            <button style="margin-top: 5px;" type="button" id="search" class="btn btn-success" onclick="searchUser()" >
                                <span class="glyphicon glyphicon-search" aria-hidden="true"> 查询</span>
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

    <script src="/js/plugins/layer/laydate/laydate.js"></script>

    <!-- 自定义js -->
    <script src="/js/content.min.js?v=1.0.0"></script>

    <!-- Page-Level Scripts -->
    <script>
        // 初始化查询时间
        var start = {
            elem: "#startTime",
            format: "YYYY-MM-DD",
            max: "2099-12-31",
            choose: function (datas) {
                end.min = datas;
                end.start = datas
            }
        };
        var end = {
            elem: "#endTime",
            format: "YYYY-MM-DD",
            max: "2099-12-31",
            choose: function (datas) {
                start.max = datas
            }
        };
        laydate(start);
        laydate(end);
        // end ---------

        $(document).ready(function () {

            // 初始化表格,动态从服务器加载数据
            $("#table_list").bootstrapTable({
                // 使用get请求到服务器获取数据
                method: "GET",
                // 必须设置，不然request.getParameter获取不到请求参数
                contentType: "application/x-www-form-urlencoded",
                // 获取数据的Servlet地址
                url: "/order/listOrders",
                // 表格显示条纹
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
                // 是否启用详细信息视图
                // detailView: false,
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
                // 数据列
                columns: [
                    {title: "订单编号", field: "orderId" },
                    {title: "订单状态 ", field: "state", formatter: function(value, row, index) {
                            switch (value) {
                                case 0 :
                                    return '<span class="label label-warning">订单生成</span>';
                                case 1 :
                                    return '<span class="label label-info">支付中</span>';
                                case 2 :
                                    return '<span class="label label-success">支付成功</span>';
                                default:
                                    return '<span class="label label-danger">支付失败</span>';
                            }
                        }
                    },
                    {title: "手机号码", field: "phone"},
                    {title: "是否国际版本", field: "en", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "套餐名称", field: "productName"},
                    {title: "创建时间", field: "createTime"},
                    {title: "支付成功时间", field: "paySuccessTime"},
                    {title: "设备编号", field: "powerBankId"},
                    {title: "密码", field: "passowrd"},
                    {title: "是否归还", field: "returned", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "订单类型", field: "orderType", formatter: function(value, row, index) {
                            switch (value) {
                                case 1 :
                                    return '<span class="label label-warning">押金</span>';
                                case 2 :
                                    return '<span class="label label-success">充值</span>';
                                case 3 :
                                    return '<span class="label label-info">租借</span>';
                                case 4 :
                                    return '<span class="label label-primary">续费</span>';
                                case 5 :
                                    return '<span class="label label-inverse">归还</span>';
                                default:
                                    return '<span class="label label-danger">其他</span>';
                            }
                        }
                    },
                    {title: "币种", field: "currency"},
                    {title: "支付金额", field: "amount"},
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
                searchText: $('#search_from input[name=\'orderNo\']').val(),
                payChannel: $('#search_from select[name=\'payChannel\']').val(),
                orderType: $('#search_from select[name=\'orderType\']').val(),
                state: $('#search_from select[name=\'state\']').val(),
                startTime:  $('#search_from input[name=\'startTime\']').val(),
                endTime:  $('#search_from input[name=\'endTime\']').val(),
            }
        }

        /**
         * 查询订单
         */
        function searchUser() {
            // 很重要的一步，刷新url！
            $('#table_list').bootstrapTable(('refresh'));
            $('#search_from input[name=\'orderNo\']').val();
            $('#search_from select[name=\'payChannel\']').val();
            $('#search_from select[name=\'orderType\']').val();
            $('#search_from select[name=\'state\']').val();
            $('#search_from input[name=\'startTime\']').val();
            $('#search_from input[name=\'endTime\']').val();
        }

    </script>

</body>

</html>
