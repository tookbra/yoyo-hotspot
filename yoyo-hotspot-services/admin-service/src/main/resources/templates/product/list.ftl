<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>套餐列表</title>

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
                        <h5>套餐列表</h5>
                    </div>
                    <div class="ibox-content">
                        <!-- 添加查询 -->
                        <form class="form-inline" id="search_form" name="search_form">
                            <div class="form-group">
                                <label class="control-label" for="productName">套餐名称:</label>
                                <input class="form-control" type="text" id="productName" name="productName"/>
                            </div>

                            <button style="margin-top: 5px;" type="button" id="search" class="btn btn-success" onclick="searchProduct()">
                                <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                            </button>
                        </form>
                    </div>
                    <div class="ibox-content">
                        <p><button class="btn btn-success " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button></p>
                        <hr>
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
                url: "/product/listProducts",
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
                    {title: "套餐名称", field: "productName"},
                    {title: "套餐类型 ", field: "productType", formatter: function(value, row, index) {
                            switch (value) {
                                case 1 :
                                    return '<span class="label label-warning">小时借</span>';
                                case 2 :
                                    return '<span class="label label-primary">包天借</span>';
                                case 3 :
                                    return '<span class="label label-success">包月借</span>';
                                default:
                                    return '<span class="label label-danger">其他</span>';
                            }
                        }
                    },
                    {title: "价格", field: "price" },
                    {title: "是否启用", field: "status", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "是否封顶 ", field: "caped", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "封顶价格", field: "capPrice" },
                    {title: "是否无限流量 ", field: "unlimited", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "最大流量", field: "limited" },
                    {title: "是否送流量 ", field: "gifted", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "流量大小", field: "limitNum" },
                    {title: "礼品消息(zh)", field: "giftMsg" },
                    {title: "描述(zh)", field: "description" },
                    {title: "套餐名称(en)", field: "productNameEn" },
                    {title: "价格(en)", field: "priceEn" },
                    {title: "描述(en)", field: "giftMsgEn" },
                    {title: "礼品消息(en)", field: "descriptionEn" },
                    {title: "操作", field: "empty",
                        formatter: function (value, row, index) {
                            var operateHtml = '\<button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+ row.id + '\'\)"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                            operateHtml = operateHtml + '\<button class="btn btn-danger btn-xs" type="button" onclick="del(\''+ row.id +'\'\)"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;';
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
                searchText: $('#search_form input[name=\'productName\']').val(),
            }
        }

        /**
         * 查询用户
         */
        function searchProduct() {
            // 很重要的一步，刷新url！
            $('#table_list').bootstrapTable(('refresh'));
            $('#search_form input[name=\'productName\']').val();
        }

        function add(){
            layer.open({
                type: 2,
                title: '添加套餐',
                shadeClose: true,
                shade: false,
                area: ['893px', '580px'],
                content: '/product/preAdd',
                end: function(index){
                    $('#table_list').bootstrapTable("refresh");
                }
            });
        }

        function edit(productId){
            layer.open({
                type: 2,
                title: '修改套餐',
                shadeClose: true,
                shade: false,
                area: ['893px', '580px'],
                content: '/product/preEdit/' + productId,
                end: function(index){
                    $('#table_list').bootstrapTable("refresh");
                }
            });
        }

        function del(productId){
            layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/product/delete/" + productId,
                    success: function(res){
                        layer.msg(res.msg, {time: 2000},function(){
                            $('#table_list').bootstrapTable("refresh");
                            layer.close(index);
                        });
                    }
                });
            });
        }

    </script>

</body>

</html>
