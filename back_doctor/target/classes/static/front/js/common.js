setTimeout(() => {
	$(function () {

	    //设置网站的访问次数
//	    let fwCount = sessionStorage.getItem("fwCount");
//	    if(!fwCount){
//	        fwCount =1;
//	    }
//	    else{
//	        fwCount = parseInt(fwCount)+1;
//	    }
//	    sessionStorage.setItem("fwCount",fwCount)

	    // 标题栏点击
	    $lis = $(".xz ul>li")
	    $lis.removeClass("liClick");
	    $lis.each((index,item)=>{ //绑定点击事件
	        let $item = $(item);
	        $item.click(() => {
	            let itemHtml =  $item.html();
	            if(itemHtml == "首页"){
	                window.location = "/"
	    	        }else if(itemHtml == "工具"){
	    	            window.location = "/tool"
	    	        }else if(itemHtml == "贴吧"){
	    	             window.location = "/tb"
	    	        }else if(itemHtml == "留言板"){
	    	        	  window.location =  "/toMessageBoard";
	    	     }
	        })
	    });
	    
	    let link_datas = window.location.href.split("/")
	    if(link_datas[3]==""){
	    	$lis.eq(0).addClass("liClick")
	    }else if(link_datas[3]=="tool"){
	    	$lis.eq(1).addClass("liClick")
	    }else if(link_datas[3]=="toMessageBoard"){
	    	$lis.eq(2).addClass("liClick")
	    }
	    
	    
	    
	       //加载上面的显示栏
	   var layerLoginIndex = -1;
	    // 添加点击登录窗口,当子类元素是生成的这个可以绑定事件
//	   $(document).on("click","span[class='login']",()=>{
//		    layerLoginIndex =layer.open({
//	            type: 1,
//	            content:  $('.login_flag'),
//	        });
//	    })
	   let $login = $(".login")
	     $login.click(()=>{
	         layerLoginIndex =layer.open({
	              type: 1,
	              content:  $('.login_flag'),
	              // loseBtn: 0,
	              // contemt:"test",
	              // btn: ['按钮一', '按钮二', '按钮三']
	          });
	    })
	    //关闭登录窗口
	    let $close = $(".close")
	    $close.click(()=>{
	        layer.close(layerLoginIndex)
	        // layer.closeAll();
	    })

	    //表单输入效果
	    $(".login_div .input-item").click(function(e){
	        e.stopPropagation();
	        $(this).addClass("layui-input-focus").find(".layui-input").focus();
	    })
	    $(".login_div .layui-form-item .layui-input").focus(function(){
	        $(this).parent().addClass("layui-input-focus");
	    })
	    $(".login_div .layui-form-item .layui-input").blur(function(){
	        $(this).parent().removeClass("layui-input-focus");
	        if($(this).val() != ''){
	            $(this).parent().addClass("layui-input-active");
	        }else{
	            $(this).parent().removeClass("layui-input-active");
	        }
	    })

	    //注册
	    var closeIndex  = -1;
	     $(document).on("click",".reg",()=>{
		       closeIndex =layer.open({
		       type: 1,
		       content:  $('.reg_flag'),
		      });
	     });
	   
	    $(".regClose").click(()=>{
	        layer.close(closeIndex)
	    })

	    //发送手机号验证码
	    let $send = $(".send")
	    var jsq = null; // 计时器
	    $send.click(()=>{
	          if(jsq){
	              layer.alert('验证码已发送,请勿重复操作', {icon: 1})
	          }else{
	        	let regPhone = $("#regPhone").val();
	        	if(!regPhone){
	        		layer.msg("请填写注册手机号");
	        		return false
	        	}
	        	if(!verifyTel(regPhone)){
	        		layer.msg("手机号输入不正确,请重新输入");
	        		return false
	        	}
	        	loadIndex= layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8})	
	        	post("/sendPhoneMsg",{phoneNumbers:regPhone},(res)=>{
	        		  if(res.code == 200){
	        			  layer.alert('验证码已发送,有效时间60s', {icon: 1})
	        			   $("#regPhone").addClass("layui-disabled");
	        	            var time = 60
	        	            $send.addClass("layui-btn-disabled");
	        	            jsq = setInterval(()=>{
	        	                if(time<=0){
	        	                    $send.html("发送")
	        	                    $send.removeClass("layui-btn-disabled");
	        	                    clearInterval(jsq)
	        	                    jsq = null
	        	                }else {
	        	                    $send.html(+time+"S");
	        	                    time = time -1;
	        	                }
	        	            },1000)
	        		  }else{
	        			    layer.alert('服务器错误,请稍后再试', {icon: 1});
	        		  }
	        		  layer.close(loadIndex);
	        	});  
	           
	          }
	        return false;
	    })
	     //登陆
	    $img = $(".code");
	    $img.click(()=>{
	   	 $img[0].src = '/code?ticket='+ Math.random();	 
	    });
	    $("#logBtn").click(()=>{
	    	  loadIndex= layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8})
	    	  let logUserName = $("#logUserName").val()
	    	  let logPassword = $("#logPassword").val()
	    	  let logCode = $("#logCode").val()
	    	  let data = {data:JSON.stringify({code:logCode,user:{loginUser:logUserName,password:logPassword}})}
	    	  post("fLogin/login",data,(res)=>{
	    		  layer.close(loadIndex);
	    		   if(res.code == 200){
	    			    v.user = res.data
	    			    v.isLogin = true
	    			    layer.close(layerLoginIndex)
	    			    setTimeout(() => {
	    			    	layer.msg("登陆成功");
						}, 100);
	    		   }else if(res.code == 400_006){
	    			   layer.msg(res.msg);
	    		   }else{
	    			   layer.msg("服务器错误"); 
	    		   }
	    	  });
	    	 return false; 
	    });
	    
	   //修改密码的功能
	    $(".changePwd").click(()=>{
	    	   window.location = "toChangePwd";
	    });
	    
	    //去个人中心
	    $(".grzx").click(()=>{
	    	window.location = "toGrzx";
	    });
	    
	    
	    //注销的功能
	    $(".logout").click(()=>{
	    	 layer.confirm('是否注销该账号',{icon:3, title:'提示信息'},function(index){
//	    		 	loadIndex= layer.msg('注销中，请稍候',{icon: 16,time:false,shade:0.8})
	    	    	post("fLogin/logout",(res)=>{
	    	    		  if(res.code == 200){
	    	    			   v.isLogin  = false;
	    	    			   v.user ={}
	    	    			   layer.msg("注销成功");
	    	    			   setTimeout(() => {
	    	    				   window.location="/"
 							 }, 100);
	    	    		  }else{
	    	    			   layer.msg("服务器错误");
	    	    		  }
	    	    	});
	         });
	    });
	    
	    
	    //
	    
	    //注冊
	    $("#regBtn").click(()=>{
	    	    let regUserName=$("#regUserName").val();
	    	    let regPassword = $("#regPassword").val();
	    	    let regCheckPassword = $("#regCheckPassword").val();
	    	    let regPhone = $("#regPhone").val();
	    	    if(regPassword !=regCheckPassword ){
	    	    	 layer.msg("两次输入的密码不一样,请仔细核对密码");
	    	    	 return false
	    	    }
	    	    let regCode = $("#regCode").val()
	    	    if(!regCode){
	    	    	layer.msg("请填写注册码");
	    	    	return false;
	    	    }
	    	    let data = {data:JSON.stringify({user:{loginUser:regUserName,password:regPassword,telephone:regPhone},code:regCode})}
	    	    post("fLogin/register",data,(res)=>{
	    	    	  if(res.code == 200){
	    	    		  layer.close(closeIndex)
	    	    		  v.isLogin = true
	    	    		  v.user = {loginUser:regUserName,password:regPassword,telephone:regPhone,showName:regUserName}
	    	    	  }
	    	    	  layer.msg(res.msg);
	    	    });
	    	    return false
	    });




	    // 移动到登录头像
	    $dl = $("#top .dl")
	    $xl = $("#top .xl")
	    $dl.hover(() => {
	        $xl.show()
	    }, () => {
	    });
	    $xl.hover(() => {
	    }, () => {
	        $xl.hide()
	    });
//	    $(obj).on("mouseover mouseout",function(event){
//	    	 if(event.type == "mouseover"){
//	    	  //鼠标悬浮
//	    	 }else if(event.type == "mouseout"){
//	    	  //鼠标离开
//	    	 }
//	    	})
//	    $(document).on("mouseover","#top .dl",function(event){
//	    	$("#top .xl").show();  //元素是实时生成的 最好每次都找一次
//	    });  

//	    $(document).on("mouseout","#top .xl",function(event){
////	    	console.log(event.type);
////	    	console.log(event.toElement.className);
//	    	if(event.toElement.className == ""){
//	    	  $("#top .xl").hide();
//	    	}
//	    });  


	    /**
	     * 滚动到顶部
	     * */
	    $("#toTop").hide();
	    $(window).scroll(function () {
	        if ($(window).scrollTop() > 100) {
	            $("#toTop").fadeIn(500);
	        }
	        else {
	            $("#toTop").fadeOut(500);
	        }
	    });

	    $("#toTop a").click(function () {
	        $('body,html').animate({scrollTop: 0}, 1000);
	        return false;
	    });
	    
	    
	  
	    



	});


//thmeleaf 的模板加载 加载出来 jquery会扫描不到dom 先让thymeleaf模板加载
},1000);

setTimeout(() => {
	  //需保险req.js在common.js的前面
    post("mrmy/queryRandom",(res)=>{
    	      console.log(res)
    	      if(res.code == 200){
    	    	   let data = res.data
    	    	   $(".mingyan .jz").html(data.content)
    	    	   if(data.author.length > 4){
    	    		   $(".mingyan .rw").html("-"+data.author.substr(0,3)+"...");
//    	    		   $(".mingyan .rw").prop({"title":"-"+data.author});
    	    	   }else{
    	    		   $(".mingyan .rw").html("-"+data.author)
//    	    		   $(".mingyan .rw").prop({"title":+data.author});
    	    	   }
    	      }
    });
}, 100);

function isLogin(successFunc){
	   post("fLogin/isLogin",(res)=>{
		     if(res.code == 200){
		    	   if(res.data){
		    		    v.user = res.data
		    		    v.isLogin  = true;
		    	   }
		    	   if(successFunc){
	    		    	successFunc()
	    		    }
		     }else{
	    		    layer.msg("服务器错误,获取登录信息失败");
	    	   }
	   });
}

var $scrollTop =  $('body,html');
function scrollTop(jqueryObj,time){
	let os = jqueryObj.offset();
	if(!time){
		 time = 1000
	}
	$scrollTop.animate({scrollTop: os.top}, time);
}
