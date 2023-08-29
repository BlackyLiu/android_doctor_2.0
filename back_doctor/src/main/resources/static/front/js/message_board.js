var v = null;
$(()=>{
    var layer = null;
    var layedit = null;
    var layIndex = null;
    var loadIndex = 0;
    layui.use(['layer','jquery','laypage','layedit'],function(){
    	 $ = layui.jquery
         layer = layui.layer
         laypage = layui.laypage
         layedit = layui.layedit
         //vue started
         v = new Vue({ 
        	 el: "#main",
        	 data:function(){
        		  return {
        			  isLogin:false,
        			  user:{
        				  id:2,
                          loginUser:'',
                          userHeader:""
        			  },
        			  //回复模块
                      replyPage:1,
                      replySize:5,
                      replyTotal:2,
                      //在回复的分页数
                      commentPage:1,
                      commentSize:5,
        			  rootId:-1,
                      repeatId:-1,
                      hfLen:-1,
                      hfIndex:-1,
                      isCheckBigPage:false,  //是否取消分页
                      clickDom:null, //当前点击回复的dom位置
                      //回复数据
                      replyDatas:[],
                      
                      //公告
                      notices:[{
                    	  title:"本博客暂时还有很多功能尚未完善和bug尚未在找出,如果您有建议请务必留言,感谢o(∩_∩)o",
                    	  createTime:"2020-07-11"
                      }],
        		  }
        	 },
        	 methods:{
        		 searchKey(){
            		 layer.msg("功能暂未实现,抱歉(┬＿┬)")
            	 },
        		 hf(){
        		     	      loadIndex= layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8})
        		     	      let content = layedit.getContent(layIndex)
        		               if(v.hfLen == -1){ //点击回复按钮
        		             	  //默认回复自己的id
        		             	  addComment(-1,-1,content);
        		               }else{
        		             	  content = content.substr(v.hfLen)
        		             	  addComment(v.rootId,v.repeatId,content);
        		               }     
        		 },
        		 deletePl(id){
            		 layer.confirm('确定删除该回复吗',{icon:3, title:'提示信息'},function(index){
            			    post("messageBoardComment/delete",{id:id},(res)=>{
            			    	 if(res.code == 200){
            			    		  layer.msg("删除成功");
            			    		  getComments(v.replyPage,v.replySize)
            			    	 }else{
            			    		  layer.msg("服务器错误");
            			    	 }
            			    });
            	        });
            	},
            	packUp(e){ //收起
            		 var $this = $(e.srcElement)
            	     let hC = $this.html();
        	        let $p = $this.parent().parent().find(".child_reply")
        	        if(hC == "收起"){
        	            $this.html("展开");
        	            $p.hide("slow")
        	        }else{
        	            $this.html("收起");
        	            $p.show("slow")
        	        }
            	},
            	//rootId文章根元素 文章id  repeatId 回复的是谁 用户id
                toHf(rootId,repeatId,repeatName,hfIndex,e){ //回复
                     v.rootId = rootId;
                     v.repeatId = repeatId;
                     v.hfIndex=  hfIndex;
                     console.log(e)
                     v.clickDom =  $(e.srcElement)
                     scrollTop($('#hfBtn'),500);
                    str ="回复"+repeatName+":"
                    v.hfLen = str.length
                    $("iframe").contents().eq(0).find("body").html(str)
                }
        	 },
        	 created(){
             	v = this
             	//$nextTick 会拿到dom更新之后的视图
             	this.$nextTick(function(){
             	
             	    //获取文章   localstorage 数据库不能同步
 	         	    isLogin();//获取登录信息
 	         	    getComments(v.replyPage,v.replySize)
             	})
             },
             filters: {  //对字符进行过滤操作
                 //日期过滤
                 formatDate(time) {
                   if (time) {
                     let date = new Date(time);
//                     return formatDate(date, 'yyyy-MM-dd');
                    return dateFormat('yyyy-MM-dd',date)
                   }
                   return time;
                 },
                 //当渲染的文字超出30字后显示省略号
                 ellipsis(value) {
                   if (!value) return "";
                   if (value.length > 150) {
                     return value.slice(0, 150) + "...";
                   }
                   return value;
                 }
               }
         }); 
    	 //vue end
    	 layIndex = layedit.build('hf',{
             tool: [
                 'strong' //加粗
                 ,'italic' //斜体
                 ,'underline' //下划线
                 ,'del' //删除线
                 ,'|' //分割线
                 ,'left' //左对齐
                 ,'center' //居中对齐
                 ,'right' //右对齐
                 ,'link' //超链接
                 ,'unlink' //清除链接
                 ,'face' //表情
                 // ,'image' //插入图片
             ],
             height:100
         })
         
         
         function renderChildPage(){
 	    	layui.use(['laypage'],function(){
 	    		  laypage = layui.laypage
 	    		   //回复模块
 	    	       if(v.replyTotal >v.replySize){
 	    	            laypage.render({
 	    	                elem: 'reply_cont_page'
 	    	                ,count: v.replyTotal,
 	    	                limit:v.replySize,
 	    	                curr: v.replyPage,
 	    	                // limits:[5,10,20],
 	    	                layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
 	    	                ,jump: function(obj, first){
 	    	                    // first 当页面第一次加载的时候 就会出现true 其他的时候就是null
// 	    	                    console.log(obj)
 	    	                    if(!first){
 	    	                        let size = obj.limit;
 	    	                        let page = obj.curr;
 	    	                        v.replyPage = page;
 	    	                        getComments(page,size);
 	    	                    }
 	    	                }
 	    	            });
 	    	        }else{
 		    	      	  let selectText = "#reply_cont_page"
 	  	            	  $(selectText).empty();
 	    	        }
 	    		  
 	    		  for(let i =0;i<v.replyDatas.length;i++){
 	  	            let item = v.replyDatas[i];
 	  	            elem = 'child_reply_page,'+i
 	  	            if(item.commentCount>v.commentSize){
 	  	                laypage.render({
 	  	                    elem: elem
 	  	                    ,count: item.commentCount,
 	  	                     limit:v.commentSize,
 	  	                    layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
 	  	                    ,jump: function(obj, first){
 	  	                        // first 当页面第一次加载的时候 就会出现true 其他的时候就是null
 	  	                        if(!first){
 	  	                            let selecter = obj.elem.split(",")
 	  	                            let i = selecter[1]
 	  	                            let item = v.replyDatas[i];
 	  	                            let size = obj.limit;
 	  	                            let page = obj.curr;
 	  	                            getCommentChilds(page,size,item.id,i);
 	  	                        }
 	  	                    }
 	  	                });
 	  	            }else{ //删除元素的时候 动态将分页给取消
 	  	            	  let selectText = "div[id^='"+elem+"']"
 	  	            	  $(selectText).empty();
 	  	            }
 	  	        }
 	    	});
 	     //最后一个元素加一点点边距	
     	 let $reply_cont = $(".reply_cont");
 	     $reply_cont.eq($reply_cont.length-1).css({"padding-bottom":"1rem"});
 	  	 
 	  }
     

     //查询一级评论
     function getComments(page,size) {
              post("fMBComment/getComments",{page:page,size:size},(res)=>{
             	      if(res.code == 200){
             	    	  let data = res.data
                  	     v.replyTotal = data.total
                  	     v.replyDatas = data.data
                           setTimeout(()=>{  //防止vue元素 还没渲染出来 layui就开始处理数据
                               renderChildPage();
                           },1)

             	      }else{
             	    	   layer.msg("服务器错误");
             	      }
             	   
              });
     }

     //查询二级的回复评论
     function getCommentChilds(page,size,rootId,index) {
     	post("fMBComment/getCommentChilds",{commentPage:page,commentSize:size,rootId:rootId},(res)=>{
     		     if(res.code == 200){
     		    	   let data = res.data
     	    		    let item = v.replyDatas[index];
     			   	    item.commentCount =data.total;
     			   	    item.hfs =  data.data
     		     }else{
       	    	   layer.msg("服务器错误");
       	         }
         });
      }


     function addComment(rootId,repeatId,content){
           post("messageBoardComment/add",{userId:v.user.id,content:content,repeatId:repeatId,rootId:rootId},(res)=>{
                 if(res.code == 200){
                      layer.msg("添加成功");
                      //初始化回复的值
                      v.rootId = -1;
                      v.repeatId = -1;
                      v.hfLen = -1;
                      $("iframe").contents().eq(0).find("body").html("")
                      getComments(v.replyPage,v.replySize);
                      if(v.clickDom){
                     	 scrollTop(v.clickDom,500);
                     	 v.clickDom = null;
                      }else{
                     	 scrollTop($(".syhf"),500);
                      }
                 }else{
                      layer.msg("添加失败")
                 }
                 layer.close(loadIndex);
           })
     }


    }); 
    /*layuiend*/
    
});