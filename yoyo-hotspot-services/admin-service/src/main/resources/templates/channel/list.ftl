<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>渠道列表</title>

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
                        <h5>渠道列表</h5>
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
                url: "/channel/listChannels",
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
                // 是否显示刷新按钮
                showRefresh: true,
                // 是否启用详细信息视图
                detailView:false,
                // 表示服务端请求
                sidePagination: "server",
                queryParamsType: "undefined",
                // json数据解析
                responseHandler: function(res) {
                    return {
                        "rows": res.data,
                        "total": res.totalCount
                    };
                },
                // 数据列
                columns: [
                    {title: "渠道名称", field: "name"},
                    {title: "渠道码", field: "token" },
                    {title: "店家名称", field: "storeName" },
                    {title: "视频地址", field: "storeVideo" },
                    {title: "是否有活动", field: "activityed", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "是否有押金", field: "deposit", formatter: function(value, row, index) {
                            if (value == '1') {
                                return '<span class="label label-warning">是</span>';
                            } else {
                                return '<span class="label label-primary">否</span>';
                            }
                        }
                    },
                    {title: "创建时间", field: "createTime"},
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

        function add(){
            layer.open({
                type: 2,
                title: '添加渠道',
                shadeClose: true,
                shade: false,
                area: ['893px', '580px'],
                content: '/channel/preAdd',
                end: function(index){
                    $('#table_list').bootstrapTable("refresh");
                }
            });
        }

        function edit(channelId){
            layer.open({
                type: 2,
                title: '修改渠道',
                shadeClose: true,
                shade: false,
                area: ['893px', '580px'],
                content: '/channel/preEdit/' + channelId,
                end: function(index){
                    $('#table_list').bootstrapTable("refresh");
                }
            });
        }

        function del(channelId){
            layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/channel/delete/" + channelId,
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
