<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>后台管理 - 主页</title>

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet"/>
    <link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet"/>
    <link href="/css/animate.min.css" rel="stylesheet"/>
    <link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet"/>

</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i></div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <#-- 头像信息 -->
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                                <span class="block m-t-xs" style="font-size:20px;">
                                    <strong class="font-bold">MeFi管理系统</strong>
                                </span>
                            </span>
                        </a>
                    </div>
                    <div class="logo-element">MeFi</div>
                </li>
                <#-- 结束 -->

                <#-- 菜单开始 -->
                <li>
                    <a class="J_menuItem" href="/main">
                        <i class="fa fa-home"></i><span class="nav-label">首页</span>
                    </a>
                </li>
                    <!-- 递归  宏定义 -->
                <#macro bpTree children>
                    <#if children?? && children?size gt 0>
                        <#list children as child>
                            <#if child.childs?? && child.childs?size gt 0>
                                <li>
                                    <a href="javascript:void(0)">
                                        <i class="fa ${(child.icon=='')?string('', child.icon)}"></i>
                                        <span class="nav-label">${child.title}</span>
                                        <span class="fa arrow"></span>
                                    </a>
                                    <ul class="nav nav-second-level">
                                        <@bpTree children=child.childs />
                                    </ul>
                                </li>
                            <#else>
                                <li>
                                    <a class="J_menuItem" href="${child.href}">
                                        <i class="fa ${(child.icon=='')?string('', child.icon)}"></i>
                                        <span class="nav-label">${child.title}</span>
                                    </a>
                                </li>
                            </#if>
                        </#list>
                    </#if>
                </#macro>
                    <!-- 调用宏 生成递归树 -->
                <@bpTree children = menus />
                <#-- 菜单结束 -->

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->

    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="http://www.zi-han.net/theme/hplus/search_results.html">
                        <div class="form-group">
                            <input type="text" class="form-control" name="top-search">
                        </div>
                    </form>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-user"></i><span class="label label-success"><h3>${admin.name}</h3></span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <#--<a href="javascript:;" class="active J_menuTab" data-id="/admin/main">首页</a>-->
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作
                    <span class="caret"></span>
                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
            <a href="/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>

        <div class="row J_mainContent" id="content-main">
            <iframe src="/main" class="J_iframe" name="iframe0" width="100%" height="100%" frameborder="0" seamless></iframe>
        </div>

        <div class="footer">
            <div class="pull-right">&copy; 2014-2015 <a href="http://www.zi-han.net/" target="_blank">zihan's blog</a></div>
        </div>
    </div>
    <!--右侧部分结束-->
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/plugins/layer/layer.min.js"></script>
<script src="/js/hplus.min.js"></script>
<script src="/js/contabs.min.js"></script>
<script src="/js/plugins/pace/pace.min.js"></script>

</body>

</html>
