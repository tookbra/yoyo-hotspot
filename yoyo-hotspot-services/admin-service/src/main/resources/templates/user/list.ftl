<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>会员列表</title>

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
                    <h5>会员列表</h5>
                </div>
                <div class="ibox-content">
                    <!-- 添加查询 -->
                    <form class="form-inline" id="search_from" name="search_from">
                        <div class="form-group">
                            <label class="control-label" for="phone">手机号:</label>
                            <input class="form-control" type="text" id="phone" name="phone"/>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">注册时间:</label>
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

<!-- 自定义js -->
<script src="/js/content.min.js?v=1.0.0"></script>

<script src="/js/plugins/layer/laydate/laydate.js"></script>

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
            url: "/user/listUserView",
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
                {title: "会员名称", field: "name" },
                {title: "号码", field: "phone" },
                {title: "国家", field: "country" },
                {title: "是否国际版本", field: "en", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "注册时间", field: "registerTime"},
                {title: "是否交纳押金", field: "deposited", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "是否禁用", field: "state", formatter: function(value, row, index) {
                        if (value == '1') {
                            return '<span class="label label-warning">是</span>';
                        } else {
                            return '<span class="label label-primary">否</span>';
                        }
                    }
                },
                {title: "最后登录ip", field: "lastLoginIp" },
                {title: "操作", field: "empty",
                    formatter: function (value, row, index) {
                        var operateHtml = '\<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+ row.id + '\'\)"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                        operateHtml = operateHtml + '\<button class="btn btn-info btn-xs" type="button" onclick="info(\''+ row.id +'\'\)"><i class="fa fa-info"></i>&nbsp;详情</button> &nbsp;';
                        operateHtml = operateHtml + '\<button class="btn btn-danger btn-xs" type="button" onclick="redEnvelope(\''+ row.id +'\'\)"><i class="fa fa-inbox"></i>&nbsp;红包</button> &nbsp';
                        operateHtml = operateHtml + '\<button class="btn btn-success btn-xs" type="button" onclick="coupon(\''+ row.id +'\'\)"><i class="fa fa-gift"></i>&nbsp;优惠券</button>';
                        return operateHtml;
                    }
                }
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
            searchText: $('#search_from input[name=\'phone\']').val(),
            startTime:  $('#search_from input[name=\'startTime\']').val(),
            endTime:  $('#search_from input[name=\'endTime\']').val(),
        }
    }

    /**
     * 查询用户
     */
    function searchUser() {
        // 很重要的一步，刷新url！
        $('#table_list').bootstrapTable(('refresh'));
        $('#search_from input[name=\'phone\']').val();
        $('#search_from input[name=\'startTime\']').val();
        $('#search_from input[name=\'endTime\']').val();
    }

    // 修改会员信息
    function edit(userId){
        layer.open({
            type: 2,
            title: '会员信息修改',
            shadeClose: true,
            shade: false,
            area: ['893px', '580px'],
            content: '/user/preEdit/' + userId,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    // 会员账户详情
    function info(userId){
        layer.open({
            type: 2,
            title: '会员账户信息',
            shadeClose: true,
            shade: false,
            area: ['893px', '580px'],
            content: '/account/getAccountInfo/' + userId,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    // 会员红包
    function redEnvelope(userId){
        layer.open({
            type: 2,
            title: '会员红包',
            shadeClose: true,
            shade: false,
            area: ['893px', '580px'],
            content: '/user/redEnvelope/' + userId,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    // 会员优惠券
    function coupon(userId){
        layer.open({
            type: 2,
            title: '会员优惠券',
            shadeClose: true,
            shade: false,
            area: ['893px', '580px'],
            content: '/user/coupon/' + userId,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

</script>

</body>

</html>
