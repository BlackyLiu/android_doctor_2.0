var v = null;
$(()=>{
//    $(".xz ul>li").eq(1).addClass("liClick");
    var layer = null;
    var layedit = null;
    var layIndex = null;
  
    layui.use(['layer','jquery','laypage','layedit'],function(){
    	 $ = layui.jquery
         layer = layui.layer
         laypage = layui.laypage
         layedit = layui.layedit
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