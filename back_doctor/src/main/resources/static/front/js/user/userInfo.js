var v = null;
$(()=>{
    var layer = null;
    var layedit = null;
    var layIndex = null;
  
    layui.use(['layer','jquery','laypage','layedit','upload','laydate'],function(){
    	 $ = layui.jquery
         layer = layui.layer
         laypage = layui.laypage
         layedit = layui.layedit
         upload = layui.upload,
         laydate = layui.laydate
         //上传头像
         setTimeout(() => {
             upload.render({
                 elem: '.userFaceBtn',
                 url: '/uploadImg',
                 done: function(res, index, upload){
                     var num = parseInt(4*Math.random());  //生成0-4的随机数，随机显示一个头像信息
                     console.log(res)
                     $('#userFace').attr('src',res.data.imgPath);
//                     window.sessionStorage.setItem('userFace',res.data[num].src);
                 }
             });
		}, 1);

         
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
        		 updateUser(){
        			 let userHeader =  $('#userFace').prop('src');
        			 let showName = $("#showName").val()
        			 let email = $("#email").val()
        			 let realName = $("#realName").val()
        			 if(!showName){
        				 layer.msg("用户显示名不能为空");
        				 return false
        			 }
        			 if(!email){
        				 layer.msg("用户邮箱不能为空");
        				 return false
        			 }
        			 if(!realName){
        				 layer.msg("用户真实姓名不能为空");
        				 return false
        			 }
        			 post("/fuser/updateUser",{id:v.user.id,userHeader:userHeader,email:email,realName:realName},(res)=>{
        					   if(res.code == 200){
        						    layer.msg("用户更新成功");
        						    isLogin();
        						    
        					   }else{
        						   layer.msg("服务器错误");
        					   }
        			 })
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