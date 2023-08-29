layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

     $img = $("#imgCode img");
     $img.click(()=>{
    	 $img[0].src = '/code?ticket='+ Math.random();
     });

    $(".loginBody .seraph").click(function(){
        layer.msg("This is just a style. As for the function, which backstage have you seen that can log in like this? Let's honestly ask the administrator to register.",{
            time:5000
        });
    })

    $("#login").click(function(){
    	  $(this).text("Logging in...").attr("disabled","disabled");
          username = $("#username").val()
          password = $("#password").val()
          code = $("#code").val()
          if(!username){
         	 layer.msg("Please enter a user name",{
                  time:5000
              });
         	 $(this).text("log in").attr("disabled",false);
         	 return false;
          }
          if(!password){
         	 layer.msg("Please enter the password.",{
                  time:5000
              });
         	 $(this).text("log in").attr("disabled",false);
         	 return false;
          }
          if(!code){
         	 layer.msg("Please enter the verification code.",{
                  time:5000
              });
         	 $(this).text("log in").attr("disabled",false);
         	 return false;
          }
          setTimeout(() => {
        		$.post('/start_login',{
        			data:JSON.stringify({
        			  admin:{
        				  username:username,
        				  password:password
        			  },
        			  code:code
        		  })
        		},function(result){
        			if(result.code == 200){
                        localStorage.setItem("admin",JSON.stringify(result.data))
                        window.location.href = "/jump/toIndex";
        			}else{
        				layer.msg(result.msg);
        				$("#login").text("log in").attr("disabled",false);
        			}
        		});
		}, 1000);

    });

    //登录按钮
//    form.on("submit(login)",function(data){
////        $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
//        $(this).text("登录中...").attr("disabled","disabled");
//        console.log(data);
//        setTimeout(function(){
//            window.location.href = "/layuicms2.0";

//        	$.post('/start_login',);

//        },1000);
//        return false;
//    })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
