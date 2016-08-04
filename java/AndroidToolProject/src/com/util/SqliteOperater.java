package com.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SqliteOperater {
    static char[] jianArray = "颱要畅覆见暂暪规嗽暮觅嗡暴视嗥嗦风嗓暇晕亲呛暖瞧嗇暗觉览瞭饮旷观晒饰饱矛更飧角曲乔饭单曾替书丧历石解食喝喜唤晓飞喘飘善知矢喂短喊触喉频颓啼真是映复春眉看头省领眾明昏昌眼昂预顽顿昆褥眷须褸星问易顶眠项商顺普景睛晦唱显襟售智顾晴晏类衬愿袜颠睹晋时额题昼西顏晚睨唇睦睥睡袭皂员架皆枷皇袖的馨哪）（马，哭驮驯袋：；袍枯？析哀品袱哄枕哆林哈果枝皮首枉被香皱皿盆咧裕盃駡柳柱补柿装咬益盈查咳裁骆裂盟盏咽盗盛柔盘某裳柑尽驳染监和目製盪裸直驾驹裡咒柏驼盾驶相疯馆周瘁朵未末木瘟蝎呼本命痪味蝇蛮养望告朝期疮呀蚕朗疟饼呆瘦餚月有餘朋服蠢最饿餐会板衝松含卫吧否街疗术东吠行杯吼吻吸吹吵癒血名同杜吏束后馈吉合吊衷各馒杖村材吃衰痒发吝白吞李百杏衫表登君饔饶吐瘫衣向癲饥疲域疾疼捞撕撒挠撞城疫埋撤执疑读播撬变抚基谗让扑骯捡讚骨拥脏择体擅挡高击操垂垃担据型挤擢痛拧搁垢扩病症痊摆发扰搏损骗男甲申坐田坑由騍坎搞骑谨用摇搔產骡谜搬甜生谎甘甚瓮骚坷谓谋腾謁谢讲谦谤坦抢坡惊团土译园圆异当图骄警验国画番摘圈搂誉围摒议驱护略圄摔摩留证畜圾挚地摸在识界畏驴斗斑谁斜调谊料囊诽诵文误嘱诬环敛诚説回说四囚断新因誓於语鲜施囱诱方斧斥困认誌固囹瓢讳瓦旗鯊诸鲤诺旁嚕旅谐瓶旋旌諮諭族谅旱论旺咙既旦日谈严请早旭鯿嚼旬瓜携鬍拦鬆诉许设鬚访攉琴琵琶攀政放讼攻噪改器斗收理闹球训鬼支讨搅计讯攒言鬱订讣嘉教敞救详魂该话败效鱼诗敏瑰询呕试故魔尝数啸词诈敲评敌整嘻瑞敬敢散敦嘴瑕冽跎拮跌跋冷鸣凤冰冬拼拽珍跟拾距拿跚跛鳩鳧冥鸟冠拱拳跑现拍路拌拋跨拉跪跤拄跡拂冒拜班再册拚招拖拔拒拐珠跳越披出凯凰趁超凶玉王押抽凡抱率赶把凛趣技玫凋折玩冻抓抑足抗投鱷儷獐储扭狮狱扶扼承奖找优赤扇赦猎获所鱒偿才献手儘兽赫赴起扔打走仪扛鱉俭独托共兵其具典赠猛猜兼赢赃战戏入赎戴鰥内户两全八六公房儿恋犹猴兔戈猿元戒我成兇或兆充兄克戚光先免兑赐赏援家狂状挥倾揭鹰傻贼狗伤贿资债传贺傲握赂狐鷲购赛赚扬换赖提狠鷂插狼描赌备伞质狸赋贱杰卖鷓贤赔贞鶯犀猫僧僻价掩推貌探採控鹤接掘挣费鶉掛犬贴像犯掐排犊买仅贵牺贯掉贪授贩货贮掌掏责贡财扫负僕贫掇豕片捷艳墙鵡捲丰鵪豚捧豆鹊豌据值牛捨牙捕牧俩牡捐牢倍仓物豺个豹豸倖捆们牲倒象鹃牵猪鹅借豪候特捉鴣爆健鸯挺鸭挽烁炉偶偷挫烂挨鸿振鸽挑谷假伟鸦爪争爬偏偎持爵偕父指鸵爸鸳爷做停尔默厚迫原迭点黑情迦追惟惜烛述黄厄燥迷烫去迎营恶烧厉燕迅灯惹惻黯厌恼燃近想厥返叟辞辩麟叛悉受取悦丽叔办辣热麒熬反及友熨又叉悔辰悒参辱农叼熟麼麻司麵麴患右史召可叩辛只熊叫古另闷麦悱悲丛口恆化北匕匙恐照恕焕包烦恙煮恢轆耻恨匹恩烟恪转恬恭鹿盐辙息匿匠恰炼匪轰煌煎然南协卒卓焰卑卜博辈轮鸚升怖焦怕千怒十无区思半输午卵急性印怡危较即卿怨怪焚载却轻卦輟辉剑剧应懈力懊功恳懺努劫悬烈助乌软惩劣加轩懒怀烙烘车军轨懦炭龙躺勇怜躲凭勃胜躬劳龟身务炸惫為动勒忧炎炊勤势勿跃憬劝憧躁刀蹲蹶切分斋齐惨火刊慟齿灰刑蹣划踪灶列灸慈态齟初灾齜慎龄齠慰庆别判利刮齬灌慢惯到洒制刷慧蹇刺蹈蹉刻刹虑则踵剃前鼎感爱愚踢鼓愁剥澜意愈愉刚踩鼠副剪创濒割瀟鼾踏瀛鼻游逮浑连逢造渴脖脉逸逾测逼港脂进渡脆胁胀减逍透渝嵌脾送退逃逅逆缺这通脱逛逝速逐途逗清遮遭崩适腐远肾避汤遗选罔罕迁罐迟遍置崎遏腿过腹罪肠罩运游崇湖遇腰脚遂遝递遥肿崖达岗违脑骂罚道巡那巢邦工罗羆溶左巧邪羈巨溺羊膘巫差美膝己巳溢已膀巴羔膊温巾羞邀邂源还準群溘边义胆溜脓沟腻羸羽翅翁臟滚郭满腊部翔邮习都脸致嶇滔滑至臼郎臺卧灭臣翱臭翼翻临嶙自缚漫而耍汉耀邻老山考者渐属屡涨耐屠耕履耗层屙总绩漏纵漂展漆屈缝乡屋尸耽屎耳局居演潮绕尾尿尼潭酸聊潦润织酷潢就酬闻酪聚酥尥圣尤繁继联绎尚酗尖酒少对茧聋小配听导绳专繫潜寻绘尊职射洁将泼封绣声聪澡肇泽峻肉肌肓肚澹医酱缠续肢涩醒股澄肥浇峙肩肯丑育醋醉峋釵岳胃背胎岸胖釧钓胞胚岩浊激浓胡金针釜采湿胳野量里济能重释胸钝累扎乾弥紫弹彆茶乳氓索纺民氏毡茫绅细纳九也乞彩永乘红水约形彼乍乎乏纸役级茉纷之纯久茄纱茂影气主弊丹污江式汝络汗给荷弄绢铅弟鉞中求丝弓统汁并弗弔引弩丘决汽丢丐出荒终且荔弦世荆丈叁上下草不绞结池一丁弱污七张绝强冲件铜必绸银没心沐仰绿仿綬忌维份忍任沙志以令代网荚绵綾衔绰莫忙沉忘茎仔仕他仗忠莓绑忡河銬沾沿付油仙治快念销仇锐仁什经仃仍忽庄莉銹今铝缘往征待法人律编很缓鋌后徒况亡徐锋得亦练交菩緻享泊泉亨从亮亭华泰互泳五井铺復紧些菜微泡泣绪波乱菇泥徵德了彻菊注予事锯线二钢菌于签帘般佯帜航佩锤洋幔你使洞簫船簧洗录简佳几舌低舍住幽位洪兴举旧幸佈洩干但年津与佃平派舟错作舞帮佛活锡何锦洲幡佑钱舒锻帚帛帝良流吁米舰帕锅鍊伸似希色笼布艰市伴帆伶浪常伉浮伏钟伍帽帐企带艇师伙籍席筹休伐海鍥浴锁芥信粳庙芬精修涉消厨俱芳厕花芽厦廷粗延促厅粒系镍粟俏涯俊建俗废广俐厂镇保俘涸粉底苦店若系淋铲淌府侯侮纠糠淑英侵侣庇粮淘床泪糯便淡康糖来鏷糕侄例凌净侈庸锈糟粪侍镜鏢深度座混苗糊浅库供添淹庭依伙大笔鑣够梦笋夭央天等筋夫太策夷镶筐橡失钥凿钻横铸夏机蕾外橄筵夕萧夜多桥荡夙蔓蔑奢卜树朴夺笏奋笑模女样奴奶铁奸鐺笙她笛好楼樑奇标蔽奏第乐葱套奔鐘荫艺妨筑妥篇节藏蓝妾妹藉妻櫚妄篤如妃妆妙篷药妖妒柠槛箏薑檳姦姻檬姿检算薄始箭薰管萨薪姓箱姑姐荐武穗歧此场步长止正报欢歪叶歷着岁穀积死堪归究葡歌叹葬穿葫空坚堆欧秽堂稳穫欣欠次萄尘莱萌萍萎欲稀税填程欺款稍钦塑称塔涂塘权稻稼塞万稿欖块塌落种竟毫盖站蓄坟窃立墨蓖蓑闢蓓母莲坠每蓬关增竹毅闯殴竞毁墓竭端阔毛比闆童毗章毒境寿阅蒂窗杀蒙士蒜殿壮壳阂阁突殷段窥殊坏间閒压闲残开窝蒸闭穷苍闪壁门坛陀桑附蝴祥桔蝶蜗陋票降陌祭娇限桂桃虾蝨案禄桌阵祀院蝗桶除陪祈蝙蝟婴陈阴陷祖陵祝神祟阳栗栓蜻字孔孕了子社孝示存孤蜓蜒阡栽格蜜根孩孙蜘孵孰防蜂校蜍学蜊阻碍棚蟹棘守秩安蚁宏完蟾雀雁它虫雄枣宇秤集棕雇宅棋移双定棉雏官杂鸡宜雕宗虽弃雪秉棺雨秋宫秀离蟒私难客宣室棵容电宸秘宿租森科宰云害雷零蟆宴家蛰礼阶富条队螺螻密寄隆螳察秃禾障禽螫际隔梅寒梁祷寐宽蚂祸福险审随写实寧禁寡寝梳梯隻梨寸寺宠螄宝螃还螂隐青亏娘杨娱娜非静威靂枫娃虹灵硬楣靴娶虎业靡靠面处虔极娥革娩虚虏号虞破椅霞植霜兰砸婚震霆婆婀椒需萝霍婷芦研露霹苹妇雾蘑砂婢砍婪椹构槊枪蛭韩蛤磨蛾蛋妈响媳音磐蛄蛇磊蛙蛛韭磁蚯码碾鞘确蚤蚣碰碳嫌榜鞋嫉榔嫁蚰碟荣蚊碗碎鞭碌榻蚓"
            .toCharArray();
    static char[] fanArray = "颱要暢覆見暫暪規嗽暮覓嗡暴視嗥嗦風嗓暇暈親嗆暖瞧嗇暗覺覽瞭飲曠觀曬飾飽矛更飧角曲喬飯單曾替書喪曆石解食喝喜喚曉飛喘飄善知矢喂短喊觸喉頻頹啼真是映複春眉看頭省領眾明昏昌眼昂預頑頓昆褥眷須褸星問易頂眠項商順普景睛晦唱顯襟售智顧晴晏類襯願襪顛睹晉時額題晝西顏晚睨唇睦睥睡襲皂員架皆枷皇袖的馨哪）（馬，哭馱馴袋：；袍枯？析哀品袱哄枕哆林哈果枝皮首枉被香皺皿盆咧裕盃駡柳柱補柿裝咬益盈查咳裁駱裂盟盞咽盜盛柔盤某裳柑盡駁染監和目製盪裸直駕駒裡咒柏駝盾駛相瘋館周瘁朵未末木瘟蠍呼本命瘓味蠅蠻養望告朝期瘡呀蠶朗瘧餅呆瘦餚月有餘朋服蠢最餓餐會板衝松含衛吧否街療術東吠行杯吼吻吸吹吵癒血名同杜吏束后饋吉合吊衷各饅杖村材吃衰癢發吝白吞李百杏衫表登君饔饒吐癱衣向癲饑疲域疾疼撈撕撒撓撞城疫埋撤執疑讀播撬變撫基讒讓撲骯撿讚骨擁髒擇體擅擋高擊操垂垃擔據型擠擢痛擰擱垢擴病症痊擺髮擾搏損騙男甲申坐田坑由騍坎搞騎謹用搖搔產騾謎搬甜生謊甘甚甕騷坷謂謀騰謁謝講謙謗坦搶坡驚團土譯園圓異當圖驕警驗國畫番摘圈摟譽圍摒議驅護略圄摔摩留證畜圾摯地摸在識界畏驢斗斑誰斜調誼料囊誹誦文誤囑誣環斂誠説回說四囚斷新因誓於語鮮施囪誘方斧斥困認誌固囹瓢諱瓦旗鯊諸鯉諾旁嚕旅諧瓶旋旌諮諭族諒旱論旺嚨既旦日談嚴請早旭鯿嚼旬瓜攜鬍攔鬆訴許設鬚訪攉琴琵琶攀政放訟攻噪改器鬥收理鬧球訓鬼支討攪計訊攢言鬱訂訃嘉教敞救詳魂該話敗效魚詩敏瑰詢嘔試故魔嘗數嘯詞詐敲評敵整嘻瑞敬敢散敦嘴瑕冽跎拮跌跋冷鳴鳳冰冬拼拽珍跟拾距拿跚跛鳩鳧冥鳥冠拱拳跑現拍路拌拋跨拉跪跤拄跡拂冒拜班再冊拚招拖拔拒拐珠跳越披出凱凰趁超凶玉王押抽凡抱率趕把凜趣技玫凋折玩凍抓抑足抗投鱷儷獐儲扭獅獄扶扼承獎找優赤扇赦獵獲所鱒償才獻手儘獸赫赴起扔打走儀扛鱉儉獨托共兵其具典贈猛猜兼贏贓戰戲入贖戴鰥內戶兩全八六公房兒戀猶猴兔戈猿元戒我成兇或兆充兄克戚光先免兌賜賞援傢狂狀揮傾揭鷹傻賊狗傷賄資債傳賀傲握賂狐鷲購賽賺揚換賴提狠鷂插狼描賭備傘質狸賦賤傑賣鷓賢賠貞鶯犀貓僧僻價掩推貌探採控鶴接掘掙費鶉掛犬貼像犯掐排犢買僅貴犧貫掉貪授販貨貯掌掏責貢財掃負僕貧掇豕片捷豔牆鵡捲豐鵪豚捧豆鵲豌据值牛捨牙捕牧倆牡捐牢倍倉物豺個豹豸倖捆們牲倒象鵑牽豬鵝借豪候特捉鴣爆健鴦挺鴨挽爍爐偶偷挫爛挨鴻振鴿挑谷假偉鴉爪爭爬偏偎持爵偕父指鴕爸鴛爺做停爾默厚迫原迭點黑情迦追惟惜燭述黃厄燥迷燙去迎營惡燒厲燕迅燈惹惻黯厭惱燃近想厥返叟辭辯麟叛悉受取悅麗叔辦辣熱麒熬反及友熨又叉悔辰悒參辱農叼熟麼麻司麵麴患右史召可叩辛只熊叫古另悶麥悱悲叢口恆化北匕匙恐照恕煥包煩恙煮恢轆恥恨匹恩煙恪轉恬恭鹿鹽轍息匿匠恰煉匪轟煌煎然南協卒卓焰卑卜博輩輪鸚升怖焦怕千怒十無區思半輸午卵急性印怡危較即卿怨怪焚載卻輕卦輟輝劍劇應懈力懊功懇懺努劫懸烈助烏軟懲劣加軒懶懷烙烘車軍軌懦炭龍躺勇憐躲憑勃勝躬勞龜身務炸憊為動勒憂炎炊勤勢勿躍憬勸憧躁刀蹲蹶切分齋齊慘火刊慟齒灰刑蹣划蹤灶列灸慈態齟初災齜慎齡齠慰慶別判利刮齬灌慢慣到灑制刷慧蹇刺蹈蹉刻刹慮則踵剃前鼎感愛愚踢鼓愁剝瀾意愈愉剛踩鼠副剪創瀕割瀟鼾踏瀛鼻游逮渾連逢造渴脖脈逸逾測逼港脂進渡脆脅脹減逍透渝嵌脾送退逃逅逆缺這通脫逛逝速逐途逗清遮遭崩適腐遠腎避湯遺選罔罕遷罐遲遍置崎遏腿過腹罪腸罩運遊崇湖遇腰腳遂遝遞遙腫崖達崗違腦罵罰道巡那巢邦工羅羆溶左巧邪羈巨溺羊膘巫差美膝己巳溢已膀巴羔膊溫巾羞邀邂源還準群溘邊義膽溜膿溝膩羸羽翅翁臟滾郭滿臘部翔郵習都臉致嶇滔滑至臼郎臺臥滅臣翱臭翼翻臨嶙自縛漫而耍漢耀鄰老山考者漸屬屢漲耐屠耕履耗層屙總績漏縱漂展漆屈縫鄉屋屍耽屎耳局居演潮繞尾尿尼潭酸聊潦潤織酷潢就酬聞酪聚酥尥聖尤繁繼聯繹尚酗尖酒少對繭聾小配聽導繩專繫潛尋繪尊職射潔將潑封繡聲聰澡肇澤峻肉肌肓肚澹醫醬纏續肢澀醒股澄肥澆峙肩肯醜育醋醉峋釵岳胃背胎岸胖釧釣胞胚岩濁激濃胡金針釜采濕胳野量里濟能重釋胸鈍累紮乾彌紫彈彆茶乳氓索紡民氏氈茫紳細納九也乞彩永乘紅水約形彼乍乎乏紙役級茉紛之純久茄紗茂影氣主弊丹汙江式汝絡汗給荷弄絹鉛弟鉞中求絲弓統汁並弗弔引弩丘決汽丟丐絀荒終且荔弦世荊丈三上下草不絞結池一丁弱污七張絕強沖件銅必綢銀沒心沐仰綠仿綬忌維份忍任沙志以令代網莢綿綾銜綽莫忙沉忘莖仔仕他仗忠莓綁忡河銬沾沿付油仙治快念銷仇銳仁什經仃仍忽莊莉銹今鋁緣往征待法人律編很緩鋌後徒況亡徐鋒得亦練交菩緻享泊泉亨從亮亭華泰互泳五井鋪復緊些菜微泡泣緒波亂菇泥徵德了徹菊注予事鋸線二鋼菌于簽簾般佯幟航佩錘洋幔你使洞簫船簧洗錄簡佳幾舌低舍住幽位洪興舉舊幸佈洩幹但年津與佃平派舟錯作舞幫佛活錫何錦洲幡佑錢舒鍛帚帛帝良流籲米艦帕鍋鍊伸似希色籠布艱市伴帆伶浪常伉浮伏鍾伍帽帳企帶艇師伙籍席籌休伐海鍥浴鎖芥信粳廟芬精修涉消廚俱芳廁花芽廈廷粗延促廳粒係鎳粟俏涯俊建俗廢廣俐廠鎮保俘涸粉底苦店若系淋鏟淌府侯侮糾糠淑英侵侶庇糧淘床淚糯便淡康糖來鏷糕侄例淩淨侈庸鏽糟糞侍鏡鏢深度座混苗糊淺庫供添淹庭依夥大筆鑣夠夢筍夭央天等筋夫太策夷鑲筐橡失鑰鑿鑽橫鑄夏機蕾外橄筵夕蕭夜多橋蕩夙蔓蔑奢蔔樹樸奪笏奮笑模女樣奴奶鐵奸鐺笙她笛好樓樑奇標蔽奏第樂蔥套奔鐘蔭藝妨築妥篇節藏藍妾妹藉妻櫚妄篤如妃妝妙篷藥妖妒檸檻箏薑檳姦姻檬姿檢算薄始箭薰管薩薪姓箱姑姐薦武穗歧此場步長止正報歡歪葉歷著歲穀積死堪歸究葡歌歎葬穿葫空堅堆歐穢堂穩穫欣欠次萄塵萊萌萍萎欲稀稅填程欺款稍欽塑稱塔塗塘權稻稼塞萬稿欖塊塌落種竟毫蓋站蓄墳竊立墨蓖蓑闢蓓母蓮墜每蓬關增竹毅闖毆競毀墓竭端闊毛比闆童毗章毒境壽閱蒂窗殺蒙士蒜殿壯殼閡閣突殷段窺殊壞間閒壓閑殘開窩蒸閉窮蒼閃壁門壇陀桑附蝴祥桔蝶蝸陋票降陌祭嬌限桂桃蝦蝨案祿桌陣祀院蝗桶除陪祈蝙蝟嬰陳陰陷祖陵祝神祟陽栗栓蜻字孔孕孓子社孝示存孤蜓蜒阡栽格蜜根孩孫蜘孵孰防蜂校蜍學蜊阻礙棚蟹棘守秩安蟻宏完蟾雀雁它蟲雄棗宇秤集棕雇宅棋移雙定棉雛官雜雞宜雕宗雖棄雪秉棺雨秋宮秀離蟒私難客宣室棵容電宸秘宿租森科宰雲害雷零蟆宴家蟄禮階富條隊螺螻密寄隆螳察禿禾障禽螫際隔梅寒梁禱寐寬螞禍福險審隨寫實寧禁寡寢梳梯隻梨寸寺寵螄寶螃寰螂隱青虧娘楊娛娜非靜威靂楓娃虹靈硬楣靴娶虎業靡靠面處虔極娥革娩虛虜號虞破椅霞植霜蘭砸婚震霆婆婀椒需蘿霍婷蘆研露霹蘋婦霧蘑砂婢砍婪椹構槊槍蛭韓蛤磨蛾蛋媽響媳音磐蛄蛇磊蛙蛛韭磁蚯碼碾鞘確蚤蚣碰碳嫌榜鞋嫉榔嫁蚰碟榮蚊碗碎鞭碌榻蚓"
            .toCharArray();
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }
    static String toJianT(String value) {
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (isChinese(c)) {
                int index = -1;
                for (int i = 0; i < fanArray.length; i++) {
                    if (fanArray[i] == c) {
                        index = i;
                        break;
                    }
                }
                sb.append(index > -1 ? jianArray[index] : c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:skyone-fan.db");
        Statement stat = conn.createStatement();
        conn.setAutoCommit(false);
        /* stat.executeUpdate("drop table if exists people;");
         stat.executeUpdate("create table people (name, occupation);");
         PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
         prep.setString(1, "Gandhi");
         prep.setString(2, "politics");
         prep.addBatch();
         prep.setString(1, "Turing");
         prep.setString(2, "computers");
         prep.addBatch();
         prep.setString(1, "Wittgenstein");
         prep.setString(2, "smartypants");
         prep.addBatch();
         conn.setAutoCommit(false);
         prep.executeBatch();
         conn.setAutoCommit(true);
         ResultSet rs = stat.executeQuery("select * from people;");
         while (rs.next()) {
             // System.out.println("name = " + rs.getString("name"));
             // System.out.println("job = " + rs.getString("occupation"));
         }*/
        Set<Character> chinese = new HashSet<Character>();
        Set<String> tables = new HashSet<String>();
        ResultSet rs = stat.executeQuery("select * from sqlite_master  where type='table'");
        while (rs.next()) {
            tables.add(rs.getString("name"));
        }
        for(String talbeName:tables){
            System.out.println(talbeName);
        }
        rs.close();
        if(true) return;
        for (String tableName : tables) {
            rs = stat.executeQuery("select * from  " + tableName);
            Set<Integer> textColumns = null;
            String idColumn = null;
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                if (textColumns == null) {
                    textColumns = new HashSet<Integer>();
                    int columnCount = meta.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        if ("text".equals(meta.getColumnTypeName(i))) {
                            textColumns.add(i);
                        }
                    }
                    idColumn = meta.getColumnName(1);
                }
                long id = rs.getLong(1);
                Map<String, String> values = new HashMap<String, String>();
                for (int index : textColumns) {
                    boolean hasChineseChar = false;
                    String value = rs.getString(index);
                    if (value != null) {
                        for (char c : value.toCharArray()) {
                            if (isChinese(c)) {
                                chinese.add(c);
                                hasChineseChar = true;
                            }
                        }
                        if (hasChineseChar) {
                            String columnName = meta.getColumnName(index);
                            String convertedValue = toJianT(value);
                            if (!value.equals(convertedValue)) {
                                values.put(columnName, convertedValue);
                            }
                        }
                    }
                    //System.out.println(value);
                }
                if (values.size() > 0) {
                    StringBuilder sql = new StringBuilder("update " + tableName + " set ");
                    Set<String> set = values.keySet();
                    int index = 0;
                    for (String key : values.keySet()) {
                        sql.append(key).append(" = ? ");
                        if ((index++) < set.size() - 1) {
                            sql.append(" ,");
                        }
                    }
                    sql.append(" where " + idColumn + " = " + id);
                    PreparedStatement prep = conn.prepareStatement(sql.toString());
                    index = 1;
                    for (String key : set) {
                        prep.setString(index++, values.get(key));
                    }
                    System.out.println(sql.toString());
                    prep.execute();
                    conn.commit();
                }
                //break;
            }
            rs.close();
            //System.out.println("-----------------");
            //break;
        }
        //System.out.println(tables);
        conn.close();
        StringBuilder sb = new StringBuilder();
        for (char c : chinese) {
            sb.append(c);
        }
        //System.out.println(sb);
    }
}
