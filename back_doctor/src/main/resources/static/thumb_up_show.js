/*点赞插件*/
/*  
 *  使用按钮
 * $(function () {
            $("#btn").click(function () {
                $.tipsBox({
                    obj: $(this),
                    str: "+1",
                    callback: function () {
                    }
                });
            });
        });
        */


(function ($) {
	         //必须要设置定位
			jQuery.fn.shake = function (times, offset, delay) {//次数,偏移,间隔
				//stop()可停止为完成的动作，当页面打开了但用户去浏览其他页面后回来，能有效防止其不断晃动的一个过程
				//先左移一定偏移量，再右移一定偏移量，最后一定要回到原本位置，不然当页面打开但未浏览时，它会自己跑掉~惊恐！
				    this.stop().each(function () {
				        var Obj = $(this);
				        var right = parseInt(Obj.css("right"));
//				        var right = Obj.offset().left;
				        Obj.animate({ "left": right + offset }, delay, function () {
				            Obj.animate({ "left": right - offset }, delay, function () {
				                Obj.animate({ "left": right }, delay, function () {
				                    times = times - 1;
				                    if (times > 0)
				                        Obj.shake(times, offset, delay);
				                })
				            })
				        });
				    });
				    return this;
			}
            $.extend({
                tipsBox: function (options) {
                    options = $.extend({
                        obj: null,  //jq对象，要在那个html标签上显示
                        str: "+1",  //字符串，要显示的内容;也可以传一段html，如: "<b style='font-family:Microsoft YaHei;'>+1</b>"
                        startSize: "12px",  //动画开始的文字大小
                        endSize: "30px",    //动画结束的文字大小
                        interval: 600,  //动画时间间隔
                        color: "red",    //文字颜色
                        callback: function () {
                        }    //回调函数
                    }, options);
                    $("body").append("<span class='num'>" + options.str + "</span>");
                    var box = $(".num");
                    var left = options.obj.offset().left + options.obj.width() / 2;
                    var top = options.obj.offset().top - options.obj.height();
                    box.css({
                        "position": "absolute",
                        "left": left + "px",
                        "top": top + "px",
                        "z-index": 9999,
                        "font-size": options.startSize,
                        "line-height": options.endSize,
                        "color": options.color
                    });
                    
                    box.animate({
                        "font-size": options.endSize,
                        "opacity": "0",
                        "top": top - parseInt(options.endSize) + "px"
                    }, options.interval, function () {
                        box.remove();
                        options.callback();
                    });
//                    let fontSize = parseInt(options.obj.css("font-size"))
//                    console.log(fontSize)
//                    options.obj.animate({
//                    	 "font-size": fontSize+10,
//                    },options.interval,function(){
////                    	 options.obj.animate({"font-size":fontSize},"fast");
//                    	  options.callback();
//                    })
                }
            });
        })(jQuery);