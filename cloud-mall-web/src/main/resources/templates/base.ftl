<#--作用域变量-->
<#assign base=request.contextPath />

<#--定义宏 格式 macro 宏的名字 可以接受的一个或者多个参数，这个参数可以是作用域变量也可以是调用时传递进来的参数 -->
<#macro header>
    <div id="headers-region">
        <div class="header-left">
            <#if userInfo != null>
                <span>欢迎你, <a href="http://home.cloud.com/user/profile">${userInfo.nickname}</a>   <a href="http://passport.cloud.com/auth/logout">[退出]</a> </span>
            <#else>
                <span>欢迎来到云店,    <a href="http://passport.cloud.com:82/login">请登录</a>  <a href="http://passport.cloud.com/auth/register">请注册</a> </span>
            </#if>
        </div>
        <div class="header-right">
            <ul>
                <li><a href="http://order.cloud.com:85/order/list">我的订单</a></li>
                <li>
                    <a href="http://home.cloud.com">我的云店</a>
                    <div class="sub-nav">
                        <ul>
                            <li><a href="http://order.cloud.com:85/order/list">我的订单</a></li>
                            <li><a href="http://promotion.cloud.com/list">我的优惠券</a></li>
                            <li><a href="http://home.cloud.com/member/wishlist">我的心愿单</a></li>
                            <li><a href="http://home.cloud.com/member/favorites">我的收藏</a></li>
                            <li><a href="http://home.cloud.com/member/messages">我的消息</a></li>
                            <li><a href="http://order.cloud.com:85/order/list">待处理订单</a></li>
                            <li><a href="http://home.cloud.com/member/myReturnGoodList">返修退换货</a></li>
                            <li><a href="http://item.cloud.com:82/product/followProductList?reducePrice=true">降价商品</a></li>
                        </ul>
                    </div>
                </li>
                <li>客户服务</li>
                <li>网站导航</li>
            </ul>
        </div>
    </div>
</#macro>

<#macro footer>
    <div id="footer-region">
        ======================================
    </div>
</#macro>
