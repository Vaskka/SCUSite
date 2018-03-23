# SCUSite
一组爬取并格式化四川大学教务中心信息的接口

## com.vaskka.spiderscu.Spider
*  使用 用户名 和 密码 实例化Spider对象

    Spider spider = new Spider("2017141423333", "666666");
    
*  使用getCourseTable()方法得到格式化的课表对象

    CourseTable table = spider.getCourseTable();

*  然后使用CourseTable对象的getMonday(); ... getFriday(); ... 等方法就能返回包含这天全部Lession的List了

    List&lt;Lession&gt; Mondaylessions = table.getMonday();
  
### Lession的数据结构
>  星期数
>  节数（第几节）
>  课程名
>  上课地点（教学楼）
>  课序号
>  课程号
>  学分
>  课程属性
>  考察类型
>  教师
>  修读方式
>  选课状态
>  周次
>  校区
>  教师

## 依赖库:
    XPath引擎：https://github.com/zhegexiaohuozi/JsoupXpath
    
