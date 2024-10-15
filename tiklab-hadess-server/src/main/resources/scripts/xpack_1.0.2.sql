INSERT INTO pack_scan_scheme (id, scheme_name, language,describe,scheme_type,create_time) VALUES
('0230645439', 'Maven推荐扫描方案','maven','默认maven扫描方案','default','2022-12-12 11:30:00');

INSERT INTO pack_scan_scheme_hole (id, scan_scheme_id, scan_hole_id,create_time) VALUES
    ('123456781000', '0230645439','123456781000','2022-10-1 11:30:00'),
    ('123456781002', '0230645439','123456781001','2022-10-1 12:30:00'),
    ('123456781003', '0230645439','123456781002','2022-10-1 13:30:00');

INSERT INTO pack_scan_hole(id,vendor,product,version,language,hole_name,hole_number,hole_level,create_time,suggestion,describe) VALUES
('123456781000', 'com.alibaba','fastjson','[1.2.62,1.3.0)','java','fastjson安全漏洞','CWE-502',2,'2020-06-03','更新到1.2.69或更高版本','fastjson受影响的版本存在反序列化漏洞'),
('123456781001', 'com.alibaba','fastjson','[2.0-beta9,2.12.2)||[1.2.62,1.3.0)','java','Fastjson 反序列化漏洞','CVE-2022-25845、CNNVD-202206-1037',1,'2022-06-10','官方已修复该漏洞，建议升级到1.2.83及以上版本。其他修复方案：
(1)开启safeMode，fastjson在1.2.68及之后的版本中引入了safeMode，配置safeMode后，无论白名单和黑名单，都不支持autoType，可杜绝反序列化Gadgets类变种攻击（关闭autoType注意评估对业务的影响）。三种开启方法：代码中ParserConfig.getGlobalInstance().setSafeMode(true);启动参数-Dfastjson.parser.safeMode=true;fastjson.properties文件配置fastjson.parser.safeMode=true
(2)升级到fastjson v2，fastjson已经开源2.0版本，在2.0版本中，不再为了兼容提供白名单，提升了安全性。fastjson v2代码已经重写，性能有了很大提升，不完全兼容1.x，升级需要做认真的兼容测试。
(3)升级到noneautotype版本，为了方便使用老版本用户兼容安全加固需求，提供了noneautotype版本，效果和1.2.68的safeMode效果一样，完全禁止autotype功能。目前发布的版本：1.2.8_noneautotype、1.2.48_noneautotype、1.2.50_noneautotype、1.2.54_noneautotype、1.2.60_noneautotype、1.2.71_noneautotype','Fastjson使用黑白名单用于防御反序列化漏洞，这个漏洞在特定条件下可绕过默认autoType关闭限制，攻击远程服务器，风险影响较大'),
('123456781002', 'com.google.guava','guava','[11.0,24.x)||[24.1.1,24.1.2)','java','Google Guava 代码问题漏洞','CVE-2018-10237、CNNVD-201804-1461、CNVD-2018-10064、CWE-770',3,'2018-04-26','目前厂商已发布升级补丁以修复漏洞，补丁获取链接：
https://groups.google.com/forum/#!topic/guava-announce/xqWALw4W1vs/discussion','Google Guava是美国谷歌（Google）公司的一款包括图形库、函数类型、I/O和字符串处理等的Java核心库。
Google Guava 11.0版本至24.1.1版本（不包括24.1.1版本）中存在代码问题漏洞。该漏洞源于网络系统或产品的代码开发过程中存在设计或实现不当的问题。');