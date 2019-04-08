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
                            <label class="control-label" for="powerBankId">模块IMEI:</label>
                            <input class="form-control" type="powerBankId" id="powerBankId" name="powerBankId"/>
                        </div>

                        <div class="form-group">
                            <label class="control-label" for="leaseNo">租借编号:</label>
                            <input class="form-control" type="text" id="leaseNo" name="leaseNo"/>
                        </div>

                        <div class="form-group">
                            <label class="control-label" for="returned">是否归还:</label>
                            <select name="returned" class="form-control">
                                <option value="">请选择</option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">租借时间:</label>
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
    $(document).ready(function () {

        // 初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            // 使用get请求到服务器获取数据
            method: "GET",
            // 必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            // 获取数据的Servlet地址
            url: "/lease/listLeaseView",
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
                {title: "名称", field: "name" },
                {title: "号码", field: "phone" },
                {title: "租赁单号", field: "leaseNo" },
                {title: "机柜ID", field: "deviceId" },
                {title: "模块IMEI", field: "powerBankId" },
                {title: "套餐名称", field: "productName" },
                {title: "套餐类型 ", field: "productType", formatter: function(value, row, index) {
                        switch (value) {
                            case 1 :
                                return '<span class="label label-warning">小时借</span>';
                            case 2 :
                                return '<span class="label label-info">包天借</span>';
                            case 3 :
                                return '<span class="label label-success">包月借</span>';
                            default:
                                return '<span class="label label-danger">其他</span>';
                        }
                    }
                },
                {title: "密码", field: "passowrd"},
                {title: "槽位号", field: "slot"},
                {title: "上一个租借编号", field: "lastLeaseNo" },
                {title: "订单编号", field: "orderNo" },
                {title: "租赁时间", field: "rentTime"},
                {title: "是否归还", field: "returned", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "归还时间", field: "returnTime"},
                {title: "预计过期时间", field: "expectedReturnTime"},
                {title: "是否国际版", field: "langEn", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "是否结束租赁", field: "overed", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "租赁地址", field: "address"},
                {title: "设备状态 ", field: "state", formatter: function(value, row, index) {
                        switch (value) {
                            case 0 :
                                return '<span class="label label-warning">初始</span>';
                            case 1 :
                                return '<span class="label label-info">成功</span>';
                            case 2 :
                                return '<span class="label label-danger">失败</span>';
                            default:
                                return '<span class="label label-danger">其他</span>';
                        }
                    }
                },
                {title: "到期短信是否发送", field: "expiredSms", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "封顶短信提醒", field: "expiredSms", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "创建时间", field: "createTime"},
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
            powerBankId: $('#search_from input[name=\'powerBankId\']').val(),
            leaseNo: $('#search_from input[name=\'leaseNo\']').val(),
            returned: $('#search_from select[name=\'returned\']').val(),
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
        $('#search_from input[name=\'powerBankId\']').val();
        $('#search_from input[name=\'leaseNo\']').val();
        $('#search_from select[name=\'returned\']').val();
        $('#search_from input[name=\'startTime\']').val();
        $('#search_from input[name=\'endTime\']').val();
    }

</script>

</body>

</html>
