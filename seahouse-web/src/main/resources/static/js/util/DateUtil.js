/**用于查询使用的参数对象
 *
 * beginTime   yyyy-MM-dd
 * endTime     yyyy-MM-dd
 * zae149      dic
 *
 * */
var params = {};
/**
 *
 * 格式化date类型
 *
 * var start = new Date();
 *  start.format('yyyy/MM/dd w'); //格式化
 *
 * js时间对象的格式化;
 * eg:format="yyyy-MM-dd hh:mm:ss";
 *
 *
 * author : majx
 *
 */
Date.prototype.formatDate = function (format) {
    var o = {
        "M+": this.getMonth() + 1,  //month
        "d+": this.getDate(),     //day
        "h+": this.getHours(),    //hour
        "m+": this.getMinutes(),  //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };
    var week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(w+)/.test(format)) {
        format = format.replace(RegExp.$1, week[this.getDay()]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 * 修改date类型的时间
 *
 *  var start = new Date();
 *  start.add("d", -1); //昨天
 *  start.add("m", -1); //上月
 *
 *js中更改日期
 * y年， m月， d日， h小时， n分钟，s秒
 *
 * author : majx
 *
 */
Date.prototype.addDate = function (part, value) {
    value *= 1;
    if (isNaN(value)) {
        value = 0;
    }
    switch (part) {
        case "y":
            this.setFullYear(this.getFullYear() + value);
            break;
        case "m":
            this.setMonth(this.getMonth() + value);
            break;
        case "d":
            this.setDate(this.getDate() + value);
            break;
        case "h":
            this.setHours(this.getHours() + value);
            break;
        case "n":
            this.setMinutes(this.getMinutes() + value);
            break;
        case "s":
            this.setSeconds(this.getSeconds() + value);
            break;
        default:
    }
};

