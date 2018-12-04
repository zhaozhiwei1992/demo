/*document.write(<div id="navgation">
    <div class="nav-content">
        <h1 class="nav-logo"><a href="index.html"><img src="//cdn.lx.com/common/images/nav_logo.png" title="咕泡学院" alt="咕泡学院"></a></h1>
        <div class="nav-options">
            <ul>
                <li><a  href="index.html" class="active">首页</a></li>
                <li><a target="_blank" href="about.html">关于咕泡</a></li>
                <li class="nav-course"><a href="javascript:void(0);">课程体系<i class="iconfont icon-jiantouarrow483"></i></a>
                    <div class="course">
                        <a target="_blank" href="course-java.html" ><img src="//cdn.lx.com/common/images/java_ico.png" alt="">Java</a>
                        <a target="_blank" href="course-bigdata.html" ><img src="//cdn.lx.com/common/images/big_ico.png" alt="">大数据</a>
                        <a target="_blank" href="course-test.html" ><img src="//cdn.lx.com/common/images/rjcs_ico.png" alt="">软件测试</a>
                        <a target="_blank" href="course-chain.html" ><img src="//cdn.lx.com/common/images/chain_ico.png" alt="">区块链</a>
                    </div>
                </li>
                <li><a target="_blank" href="team.html">教师团队</a></li>
                <!--<li><a href="video.html">视频下载</a></li>-->
                <li><a target="_blank" href="join.html">报名流程</a></li>
                <li><a target="_blank" href="aliyun.html">阿里云优惠</a></li>
                <li><a target="_blank" href="//bbs.lx.com/">咕泡社区</a></li>
            </ul>
        </div>
        <div class="nav-search">

            <form id="scbar_form" methods="post" autocomplete="off" onsubmit="searchFocus($('scbar_txt'))" action="//bbs.lx.com/search.php?mod=forum" target="_blank">
                <input type="hidden" name="mod" id="scbar_mod" value="forum">
                <!--<input type="hidden" name="formhash" value="a904ab09">
                <input type="hidden" name="srchtype" value="title">
                <input type="hidden" name="srhfid" value="">
                <input type="hidden" name="srhlocality" value="forum::index">-->


                <input type="text" name="srchtxt" id="scbar_txt" placeholder="请输入搜索内容" autocomplete="off" x-webkit-speech speech class=" xg1" placeholder="请输入搜索内容">

                <button type="submit" name="searchsubmit" id="scbar_btn" sc="1" class="pn pnc" value="true">
                    <i class="iconfont icon-sousuo"></i>
                </button>

            </form>



        </div>
    </div>
    <div class="meui"></div>
</div>`);*/

var str = `<div id="navgation">
    <div class="nav-content">
        <h1 class="nav-logo">
            <a href="index.html">
                <img src="//cdn.lx.com/common/images/nav_logo.png" title="咕泡学院" alt="咕泡学院"></a></h1>
        <div class="nav-options">
            <ul>
                <li><a href="index.html" class="active">首页</a></li>
                <li><a target="_blank" href="about.html">关于咕泡</a></li>
                <li class="nav-course"><a href="javascript:void(0);">课程体系<i
                        class="iconfont icon-jiantouarrow483"></i></a>
                    <div class="course"><a target="_blank" href="course-java.html"><img
                            src="//cdn.lx.com/common/images/java_ico.png" alt=""><span>Java架构师</span></a> <a
                            target="_blank" href="course-bigdata.html"><img
                            src="//cdn.lx.com/common/images/big_ico.png" alt=""><span>大数据</span></a> <a
                            target="_blank" href="course-test.html"><img
                            src="//cdn.lx.com/common/images/rjcs_ico.png" alt=""><span>软件测试</span></a> <a
                            target="_blank" href="course-chain.html"><img
                            src="//cdn.lx.com/common/images/chain_ico.png" alt=""><span>区块链</span></a></div>
                </li>
                <li><a target="_blank" href="team.html">教师团队</a></li>
                <li><a href="video.html">视频下载</a></li>
                <li><a target="_blank" href="join.html">报名流程</a></li>
                <li><a target="_blank" href="aliyun.html">阿里云优惠</a></li>
                <li><a target="_blank" href="//bbs.lx.com/">咕泡社区</a></li>
            </ul>
        </div>
        <div class="nav-search">
            <form id="scbar_form" methods="post" autocomplete="off" onsubmit="searchFocus($(scbar_txt))"
                  action="//bbs.lx.com/search.php?mod=forum" target="_blank"><input type="hidden" name="mod"
                                                                                               id="scbar_mod"
                                                                                               value="forum">
                <!--<input type="hidden" name="formhash" value="a904ab09">                <input type="hidden" name="srchtype" value="title">                <input type="hidden" name="srhfid" value="">                <input type="hidden" name="srhlocality" value="forum::index">-->
                <input type="text" name="srchtxt" id="scbar_txt" placeholder="请输入搜索内容" autocomplete="off"
                       x-webkit-speech speech class=" xg1" placeholder="请输入搜索内容">
                <button type="submit" name="searchsubmit" id="scbar_btn" sc="1" class="pn pnc" value="true"><i
                        class="iconfont icon-sousuo"></i></button>
            </form>
        </div>
    </div>
    <div class="meui"></div>
</div>
`;



var str1 = '<div id="footerCode"></div><div class="flot fixed">    <div class="flot-content">        <ul>            <li><img src="//cdn.lx.com/index/images/logo.png" alt="">咕泡学院 只为更好的你！</li>            <li>在线学习人数11万+</li>            <li>VIP学员人数3300+</li>            <li><a target="_blank" href="//wpa.qq.com/msgrd?v=3&uin=800185235&site=qq&menu=yes">VIP试听</a></li>        </ul>    </div></div><div class="float-fixed"></div><div id="footer">    <div class="footer-contert">        <div class="footer-content-logo">            <img src="//cdn.lx.com/common/images/footer-logo.jpg" alt="底部LOGO">            <span>做技术人的指路明灯，职场生涯的精神导师</span>        </div>        <div class="footer-content-aboutUs">            <ul>                <li>关于我们</li>                <li><a href="about.html?con=xueyjianjie">学院简介</a></li>                <li><a href="about.html?con=lianxiwomen">联系我们</a></li>                <li><a href="about.html?con=chengpycai">加入我们</a></li>            </ul>        </div>        <div class="footer-content-service">            <ul>                <li>服务与支持</li>                <li><a href="qa.html">常见问题</a></li>                <li><a href="about.html?con=shangwuhez">商务合作</a></li>            </ul>        </div>        <div class="footer-content-img">            <div class="footer-content-img-wx">                <span><img src="//cdn.lx.com/common/images/foot-weix.png" alt="微信"></span>                <span>官方微信</span>            </div>            <div class="footer-content-img-wb">                <a href="//weibo.com/lx.?is_hot=1" target="_blank">                    <span class="weibo"><img src="//cdn.lx.com/common/images/foot-web.png" alt="微博"></span>                    <span>官方微博</span>                </a>            </div>        </div>    </div>    <div class="footer-copy">        Copyright©2017-2020 lx.com All Rights Reserved. 版权所有 <a href="//www.miitbeian.gov.cn" target="_blank">沪ICP备16036932号-2</a>    </div></div>';



$(function(){
    $('header').html(str);
    $('footer').html(str1);
})


