package goahead.supermm2
package wiki
package routes

import akka.http.scaladsl.server.Route

final case class DeathStrandingWiki(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def otherRoute: Route = {
    path(webRoot / "deathStranding"){
      get {
        val h =
          s"""
             |<head>
             |    <link rel="shortcut icon" href="/static/static/img/short_icon.png">
             |    <title>《死亡搁浅》战斗介绍，带来多种不同战斗体验</title>
             |    <meta charset="utf-8">
             |    <script src="https://zz.bdstatic.com/linksubmit/push.js"></script><script src="https://hm.baidu.com/hm.js?4d1af199664c62480106ac2460b24ae2"></script><script src="/static/static/js/jquery-1.11.0.min.js"></script>
             |    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0,user-scalable=no">
             |    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
             |    <link rel="stylesheet" href="/static/static/css/normalize.css">
             |    <link rel="stylesheet" href="/static/static/css/common.css">
             |    <link rel="stylesheet" href="/static/static/css/page.min.css?v2">
             |    <meta name="referrer" content="never">
             |    <script>
             |        var _hmt = _hmt || [];
             |        (function () {
             |            var hm = document.createElement("script");
             |            hm.src = "https://hm.baidu.com/hm.js?4d1af199664c62480106ac2460b24ae2";
             |            var s = document.getElementsByTagName("script")[0];
             |            s.parentNode.insertBefore(hm, s);
             |        })();
             |    </script>
             |    <style>
             |        .avatar_with_decoration {
             |            z-index: 0;
             |            left: 0;
             |            top: 0;
             |            position: relative;
             |        }
             |
             |        .avatar_decoration {
             |            max-width: 39px;
             |            z-index: 1;
             |            position: absolute;
             |            top: -6px;
             |            left: -7px;
             |        }
             |        .hexagon {
             |            position: relative;
             |            background: var(--dynamic-bg);
             |            -webkit-clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
             |        }
             |        .hexagon::before {
             |            position: absolute;
             |            content: '';
             |            top: 2px;
             |            left: 2px;
             |            height: calc(100% - 4px);
             |            width: calc(100% - 4px);
             |            -webkit-clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
             |        }
             |        .image::before {
             |            background: var(--dynamic-img);
             |            background-position: center;
             |            background-size: cover;
             |        }
             |        .hexagon_normal {
             |            width: 45px;
             |            height: 55px;
             |            background-position: center;
             |            background-size: cover;
             |            margin-left: 2px;
             |        }
             |        .hexagon_side {
             |            width: 45px;
             |            height: 55px;
             |            background-position: center;
             |            background-size: cover;
             |            margin-left: 24px;
             |        }
             |    </style>
             |</head>
             |
             |<body style="-webkit-touch-callout: unset; -webkit-user-select: unset; background-color: white;">
             |<div class="container" style="background-color: white;">
             |    <div class="article_info" style="background-color: white;">
             |        <div class="header a_header">
             |            <p class="a_title">《死亡搁浅》战斗介绍，带来多种不同战斗体验</p>
             |
             |        </div>
             |
             |        <div class="wrapper" style="max-height: 9.0072e+15px;">
             |            <div class="content">
             |
             |                <p>游戏前期玩家遭遇最多的2种敌人就是“米尔人”和“BT”，这里为各位带来面对这2种敌人的打完&amp;心得。</p><p><span style="font-weight:bold;">米尔人&nbsp;&nbsp;</span></p><p>米尔人是游戏中玩家遭遇的人类敌人，设定上米尔人只对货物感兴趣，因此如果你身上没有任何货物的话，米尔人表示“完全不想理你”。反之，若你身上有货物的话，只要一踏入米尔人营地范围内，米尔人就会频繁探索营地范围内是否有货物存在，玩家一旦被锁定位置后，整个营地的米尔人将会一窝蜂地跑向玩家所在位置，如果你最终被米尔人捉住，他们会抢走你所有货物，然后把你丢出营地范围。所以遇到米尔人究竟该怎么办？</p><p>1、小心谨慎：你可以选择躲草丛潜行绕开米尔人的探测，并且玩家还可以使用绞合线来潜行击晕敌人。</p><p>2、无所畏惧：除了潜行之外，你当然也可以选择与米尔人正面对决，从而突出重围；虽然游戏为玩家提供了不同种类的远程武器，不过在游戏前期我还是推荐玩家尽量潜行绕开米尔人，因为虽然K.O.单个米尔人是非常轻松的，但问题是他们的数量实在是太多了，再加入米尔人AI不低，既有近战的，也有远程的，因此你在前期还没有解锁各位武器的情况下，一旦陷入持久战，最终等待你的就是被米尔人捉住。</p><p>3、风驰电掣：那我使用载具快速驶离米尔人营地范围可不可以？当然可以。不过正如刚才提到的那样，米尔人AI并不低，他们会突然在你的移动路线前方投掷电击标枪，而电击标枪则会让载具暂时无法使用。有很多次，我一度认为自己已经轻松甩开米尔人，正当我还在得意之时，前方突然闪现出电击标枪让我人仰马翻，再等我缓过神来，发现自己已经被米尔人包围了。所以你如果想要使用载具快速驶离米尔人营地范围的话，务必保持S型的移动路线。</p><p><img src="https://erbingeditor.diershoubing.com/6/2019/11/06/1819413267.jpg-web" style="max-width:100%;"></p><p><br></p><p><span style="font-weight:bold;">BT</span></p><p>除了米尔人之外，玩家遭遇更多的敌人就是BT了，遭遇BT分为“凝视者（Gazer）”和“捕捉者（Catcher）”2个阶段。首先是“凝视者”阶段，当玩家遭遇时间雨时，紧接而来就是这些浮在空中的“凝视者”，面对“凝视者”时玩家需要屏住呼吸&amp;蹲下潜行缓慢移动。此时有自动以及手动2种探索“凝视者”所在位置的方式，机械臂会自动探索玩家周围的“凝视者”，机械臂的转向显示“凝视者”大致位置，机械臂的挥动频率显示“凝视者”是否接近玩家，如果机械臂向电脑风扇那样高速转动，那么说明“凝视者”现在就在你的眼前！此时手动R1按键探索，则可以短时间的看到这些“凝视者”身影，需要注意的是玩家在移动过程中手动R1按键探索，是看不见“凝视者”身影的，必须在静止时使用才能看到“凝视者”身影。</p><p><br></p><p>如果你一不小心被“凝视者”发现，那么“凝视者”则会蜂拥而至来把你拽倒。此时先不要慌，打开地图你会看到一个圆圈，如果你成功逃离这个圆圈范围，那么你就安全了；反之，如果你被“凝视者”拽倒则会进入下一个阶段—“捕捉者”。在这个阶段你将会遭遇更加危险的“捕捉者”，首先是与上述方式一样，你也可以选择逃离圆圈范围，不过与“凝视者”相比，“捕捉者”的圆圈范围更大，而且整个场景都会陷入沼泽一样寸步难行，所以即使是逃离难度也非常大。如果你选择战斗，只要击败“捕捉者”，不仅将解除危机，甚至还可以获得丰厚的材料报酬，而游戏武器分为““对人类武器””和“对BT武器”，玩家必须使用特制的“对BT武器”才能对“捕捉者”造成伤害，另外就是主角山姆的血液及排泄物都可以成为对BT武器，如果一直处于疲劳状态，有可能会在小便时排出血尿，能成为对战BT时非常强力的武器。</p><p><br></p><p>而在战斗时也不需要担心资源不足，因为此时其他玩家将会向你提供帮助，投掷给你包括补给品、对BT武器等各种所需资源，因此只要你拥有足够的战斗技巧，你也可以通过其他玩家的帮助成功击败“捕捉者”。但需要注意的是，游戏后期你会逐渐遭遇更加强大的“捕捉者”，如果你被“捕捉者”击败吞噬，那么此时将会产生“虚爆“，不仅周遭场景会出现超大的坑洞，包括所有建造的设施、你的货物也都会被摧毁。</p><p><img src="https://erbingeditor.diershoubing.com/6/2019/11/06/1819525491.jpg-web" style="max-width:100%;">
             |            </p></div>
             |
             |        </div>
             |
             |        <div class="cut_line"></div>
             |
             |
             |    </div>
             |
             |</div>
             |
             |
             |<script>
             |    var src_from = 'other'
             |    function down_click() {
             |        if (src_from == 'other'){
             |            window.location.href = 'http://www.diershoubing.com/mobile/download.html';
             |        }else{
             |            window.location.href = '/feed_info/apex_download/';
             |        }
             |    }
             |
             |
             |    (function () {
             |        var bp = document.createElement('script');
             |        var curProtocol = window.location.protocol.split(':')[0];
             |        if (curProtocol === 'https') {
             |            bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
             |        }
             |        else {
             |            bp.src = 'http://push.zhanzhang.baidu.com/push.js';
             |        }
             |        var s = document.getElementsByTagName("script")[0];
             |        s.parentNode.insertBefore(bp, s);
             |    })();
             |
             |
             |    function unfold() {
             |        var doc = document;
             |        var wrap = doc.querySelector(".wrapper");
             |        var unfoldField = doc.querySelector(".unfold-field");
             |        unfoldField.onclick = function () {
             |            this.parentNode.removeChild(this);
             |            wrap.style.maxHeight = Number.MAX_SAFE_INTEGER.toString() + "px";
             |        };
             |        document.onreadystatechange = function () { //当内容中有图片时，立即获取无法获取到实际高度，需要用 onreadystatechange
             |            if (document.readyState === "complete") {
             |                var wrapH = doc.querySelector(".wrapper").offsetHeight;
             |                var contentH = doc.querySelector(".content").offsetHeight;
             |                if (contentH <= wrapH) {  // 如果实际高度大于我们设置的默认高度就把超出的部分隐藏。
             |                    unfoldField.style.display = "none";
             |                }
             |            }
             |        }
             |    }
             |
             |    unfold(); 
             |</script>
             |</body>
             |
             |""".stripMargin
        html(h)
      }
    }
  }


}
