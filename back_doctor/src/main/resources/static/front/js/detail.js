var v = null;
$(()=>{
    var layer = null;
    var layedit = null;
    var layIndex = null;
    layui.use(['layer','jquery','laypage','layedit'],function () {
        $ = layui.jquery
        layer = layui.layer
        laypage = layui.laypage
        layedit = layui.layedit
      
        v = new Vue({
            el: "#main",
            data: function () {
                return {
                	//回复模块
                    replyPage:1,
                    replySize:5,
                    replyTotal:2,
                    //在回复的分页数
                    commentPage:1,
                    commentSize:5,
                   
                    //文章的作者信息
                    author:{
                        id:1,
                        loginUser:'',
                        userHeader:""
                    },
                    //自己登陆用户信息
                    user:{
                        id:2,
                        loginUser:'',
                        userHeader:""
                    },
                	isLogin:false,
                    //默认的回复id
                    rootId:-1,
                    repeatId:-1,
                    hfLen:-1,
                    hfIndex:-1,
                    isCheckBigPage:false,  //是否取消分页
                    clickDom:null, //当前点击回复的dom位置
                    //回复数据
                    replyDatas:[],
                    //回复模块end
                    //文章的实体
                    acticle:{
                    	id:1
                    },
//                    isZan:false,
//                    isCollection:false,
                    //文章是否点赞实体
                    acticleOper:{
                    	
                    },
                    //文章推荐
                    recommends:[],
                    //相关文章
                    aboutArticles:[],
                    //上一篇和下一篇文章
                    sxArticles:{},
                   
                }
            },
            methods: {
            	searchKey(){
            		 layer.msg("方法暂未实现");
            	},
            	toDetail(articleId){
            		localStorage.setItem("acticleId",articleId);
            		window.open("/toDetail","_blank");
            	},
            	collection(e){
            		let $this = $(e.srcElement)
            		if( !v.acticleOper.collection){
	                  	  $.tipsBox({
	                            obj: $this,
	                            str: "+1",
	                            callback: function () {
	                            	post("fActicle/collection",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
	                            		 if(res.code == 200){
	                            			 v.acticle.collectCount = v.acticle.collectCount+1;
	                            			 v.acticleOper.collection = true
	                            		 }  
	                            		layer.msg(res.msg);
	                            	});
	                            }
	                        });
	            	}else{
	            		 layer.confirm('是否取消收藏',{icon:3, title:'提示信息'},function(index){
	            			 $.tipsBox({
	                             obj: $this,
	                             str: "-1",
	                             callback: function () {
	                             	post("fActicle/collection",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
	                             		 if(res.code == 200){
	                            			 v.acticle.collectCount = v.acticle.collectCount-1;
	                            			 v.acticleOper.collection = false
	                             		 }  
	                             		layer.msg(res.msg);
	                             	});
	                             }
	                         });
	            	     });	
            	}
            	},
            	zan(e){
            		let $this = $(e.srcElement)
	            	if(!v.acticleOper.zan){
	                  	  $.tipsBox({
	                            obj: $this,
	                            str: "+1",
	                            callback: function () {
	                            	post("fActicle/zan",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
	                            		 if(res.code == 200){
	                            			 v.isZan = true
	                            			 v.acticleOper.zan = true
	                            		 }  
	                            		layer.msg(res.msg);
	                            	});
	                            }
	                        });
	            	}else{
	            		 layer.confirm('是否取消点赞',{icon:3, title:'提示信息'},function(index){
	            			 $.tipsBox({
	                             obj: $this,
	                             str: "-1",
	                             callback: function () {
	                             	post("fActicle/zan",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
	                             		 if(res.code == 200){
	                             		     v.isZan = false
	                             		     v.acticleOper.zan = false
	                             		 }  
	                             		layer.msg(res.msg);
	                             	});
	                             }
	                         });
	            	     });	
            	}
	            },
	            hf(){
	                var loadIndex;
//	               $("#hfBtn").click(()=>{
	        	      loadIndex= layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8})
	        	      let content = layedit.getContent(layIndex)
	                  if(v.hfLen == -1){ //点击回复按钮
	                	  //默认回复作者的id
	                	  addActicleComment(-1,v.author.id,content);
	                  }else{
	                	  content = content.substr(v.hfLen)
	                	  addActicleComment(v.rootId,v.repeatId,content);
	                  }     
//	              })
	            },
            	deletePl(acId){
            		 layer.confirm('确定删除该回复吗',{icon:3, title:'提示信息'},function(index){
            			    post("fActicleComment/delete",{id:acId},(res)=>{
            			    	 if(res.code == 200){
            			    		  layer.msg("删除成功");
            			    		  getActicleComments(v.replyPage,v.replySize,v.acticle.id)
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
            mounted(){
            	v = this
            },
            filters: {  //对字符进行过滤操作
                //日期过滤
                formatDate(time) {
                  if (time) {
                    let date = new Date(time);
//                    return formatDate(date, 'yyyy-MM-dd');
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
              },
            created(){
            	v = this
            	//$nextTick 会拿到dom更新之后的视图
            	this.$nextTick(function(){
            	    //获取文章   localstorage 数据库不能同步
	            	let acticleId = localStorage.getItem("acticleId")
            		v.acticle.id = acticleId;
	         	    isLogin(()=>{
	         	    	//依赖用户和文章的对象的请求
		         	   	getArticleById(acticleId,()=>{
	            			updateViewCount();
	            			getAbortArticle();
	            			isZan();
	    	         		isCollection();
	            		})
	            		getActicleComments(v.replyPage,v.replySize,v.acticle.id)
	         	    });//获取登录信息
	         	    //获取文章评论
	         		getRecommends();
	         		getBeforeAndLastArticle();
            	})
            }
        });
        
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
        
        

      
    });
    

   
       
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
//	    	                    console.log(obj)
	    	                    if(!first){
	    	                        let size = obj.limit;
	    	                        let page = obj.curr;
	    	                        v.replyPage = page;
	    	                        getActicleComments(page,size,v.acticle.id);
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
	  	//                    limit:1,
	  	                    // limits:[5,10,20],
	  	                    layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
	  	                    ,jump: function(obj, first){
	  	                        // first 当页面第一次加载的时候 就会出现true 其他的时候就是null
	  	                        if(!first){
	  	                            let selecter = obj.elem.split(",")
	  	                            let i = selecter[1]
	  	                            let item = v.replyDatas[i];
	  	                            let size = obj.limit;
	  	                            let page = obj.curr;
	  	                            getActicleCommentChilds(page,size,v.acticle.id,item.id,i);
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
    function getActicleComments(page,size,acId) {
             post("fActicleComment/getActicleComments",{page:page,size:size,acId:acId},(res)=>{
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
    function getActicleCommentChilds(page,size,acId,rootId,index) {
    	post("fActicleComment/getActicleCommentChilds",{commentPage:page,commentSize:size,acId:acId,rootId:rootId},(res)=>{
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


    function addActicleComment(rootId,repeatId,content){
          post("acticleComment/add",{userId:v.user.id,content:content,repeatId:repeatId,rootId:rootId,articleId:v.acticle.id},(res)=>{
                if(res.code == 200){
                     layer.msg("添加成功");
                     //初始化回复的值
                     v.rootId = -1;
                     v.repeatId = -1;
                     v.hfLen = -1;
                     $("iframe").contents().eq(0).find("body").html("")
                     getActicleComments(v.replyPage,v.replySize,v.acticle.id);
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
    


    
    function getRecommends(){
    	post("fRecommend/getRecommends",(res)=>{
    		if(res.code == 200){
    			  v.recommends = res.data
    		}else{
    			layer.msg("服务器错误,未能获取文章推荐");
    		} 
    	});
    }
    
    function updateViewCount(){
    	     v.acticle.viewCount =  v.acticle.viewCount+1
    	     post("article/update",{
    	    	  id:v.acticle.id,
    	    	  viewCount:v.acticle.viewCount
    	     },(res)=>{
    	    	    if(res.code != 200){
    	    	    	   layer.msg("服务器错误,未能更新浏览数");
    	    	    }
    	     });
    }
    
    function getAbortArticle(){
    	   post("fActicle/getAbortArticle",{tagName:v.acticle.tagName,id:v.acticle.id},(res)=>{
    		      if(res.code == 200){
    		    	   v.aboutArticles = res.data
    		      }else{
    		    	  layer.msg("服务器错误,未能获取相关文章数");
    		      }
    	   });
    }
    
    
    function isZan(){
    	 post("fActicle/isZan",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
    		    if(res.code == 200){
    		    	 v.isZan = res.data
    		    }
    	 })
    }
    
    function isCollection(){
   	 post("fActicle/isCollection",{userId:v.user.id,articleId:v.acticle.id},(res)=>{
   		    if(res.code == 200){
   		    	 v.isCollection = res.data
   		    }
   	 })
   }

    function getArticleById(id,successFunc){
    	  post("fActicle/get",{id:id},(res)=>{
    		    if(res.code == 200){
    		    	 v.acticle = res.data
    		    	 if(successFunc){
    		    		 successFunc();
    		    	 }
    		    }else{
    		    	 layer.msg("加载文章详情数据错误")
    		    }
    	  });
    } 
     
    function getArticleOper(){
    	  post("acticleOper/queryActicleOper",{articleId:v.acticle.id,userId:v.user.id},(res)=>{
   		   if(res.code == 200){
   			     v.acticleOper = res.data
   		   }else{
   			     layer.msg("获取文章相关点赞收藏失败,请刷新重试");
   		   }
   	  });
    }
    
    function getBeforeAndLastArticle(){
    	  post("fActicle/getBeforeAndLastArticle",{articleId:v.acticle.id},(res)=>{
    		   if(res.code == 200){
    			     v.sxArticles = res.data
    		   }else{
    			   layer.msg("获取文章上一条和下一条文章失败");
    		   }
    	  });
    }
    

})