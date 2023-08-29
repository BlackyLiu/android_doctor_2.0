var v = null;
$(()=>{
    var layer = null;
    var layedit = null;
    var layIndex = null;
  
    layui.use(['layer','jquery','form','laypage','layedit'],function(){
    	 $ = layui.jquery
    	 form = layui.form
         layer = layui.layer
         laypage = layui.laypage
         layedit = layui.layedit
         
         
         //添加验证规则
         form.verify({
             oldPwd : function(value, item){
                 if(value.trim() == ""){
                     return "旧密码不能为空！";
                 }
             },
             newPwd : function(value, item){
                 if(value.length < 6){
                     return "密码长度不能小于6位";
                 }
             },
             confirmPwd : function(value, item){
                 if(!new RegExp($("#np").val()).test(value)){
                     return "两次输入密码不一致，请重新输入！";
                 }
             }
         })

         //vue started
         v = new Vue({ 
        	 el: "#main",
        	 data:function(){
        		  return{
        			  isLogin:false,
        			  user:{
        				  id:1,
                          loginUser:'',
                          userHeader:""
        			  }
        		  }
        	 },
        	 methods:{
        		 update(){
        			   let op = $("#op").val();
        			   let np =  $("#np").val();
        			   post("/fuser/updatePwd",{id:v.user.id,oldPass:op,newPass:np},(res)=>{
        				   layer.msg(res.msg);
        			   });
        			   return false;
        		 },
        		searchKey(){
            		 layer.msg("功能暂未实现,抱歉(┬＿┬)")
            	}
        	 },
        	 created(){
             	v = this
             	//$nextTick 会拿到dom更新之后的视图
             	this.$nextTick(function(){
             	
             	    //获取文章   localstorage 数据库不能同步
 	         	    isLogin();//获取登录信息
             	})
             }
         }); 
    	 //vue end
    });
});