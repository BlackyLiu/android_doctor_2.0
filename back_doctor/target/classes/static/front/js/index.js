var v = null;
var carousel = null;
var layer = null;
var laypage = null;
//$(".label_g").hide();
var label_top = 0//标签的top
var start_top =  0 //左部类别最顶端的top
$(()=>{
    layui.use(['carousel','laypage','layer'], function(){
        carousel = layui.carousel;
        laypage = layui.laypage;
        layer = layui.layer;
        //vue started
        v = new Vue({
            el: "#main",
            data: function () {
                return {
                	//文章
                	page:1,
                	size:5,
                	total:0,
                	acticles:[],
//                	queryArticleData:{page:1,size:5},  //查询文章数据
                    //推荐模块
                	recommends:[],
                	//通知模块
                	notices:[],
                	//轮播图
                	banners:[],
                	//登陆的用户
                	user:{},
                	isLogin:false,
                	//类型数据
                	types:[
//                		{"typeName":"web前端"},{"typeName":"java开发"},{"typeName":"数据库"}
//                	,{"typeName":"linux"}
                	],
                	tags:[],
                	tagClickObj:{},
                
                	
                }
            },
            methods: {
            	tagClick(name){
            		return name == v.tagClickObj.name
            	},
            	selectLabel(name,e){
            		 let obj = e.srcElement
                     //初始化所有标签的颜色
//            		 $(".my_label").css({"background-color":"#51aded"});
//            		 $("i.layui-icon-triangle-r").css({"color":"#51aded"});
            		 //用来判断dom
            		 v.tagClickObj['name'] = name
            		 getActicles({tagName:name},false)
            	},
            	selectType(item,type,e){
//            		 console.log(e)
//            		 layer.msg(type)
            		 getTag(item.id); 
            		 let obj = e.srcElement
            		 let curr_top =  $(obj).offset().top //点击元素的top
            		 let label_top = curr_top - start_top +init_top;
            		 if(type=="mouseover"){
            			   $(".left .ul>.li").css({"background-color":"white","color":'black'});
            			   $(obj).css({"background-color":"#378DDB","color":'white'});
            			   $(".label_g").show();
            			   $(".label_g").css({top:label_top})
            		 }else if(type=="click"){
            			   $(obj).css({"background-color":"#378DDB","color":'white'});
            		 }
            	},
            	showLabel(){
            		$(".label_g").show();
            	},
            	hideLabel(e){
            		 let obj = e.srcElement
            		$(".label_g").hide();
            	},
            	toDetail(acticle){
//            		localStorage.setItem("toDetailActicle",JSON.stringify(acticle));
            		localStorage.setItem("acticleId",acticle.id);
            		window.open("/toDetail","_blank");
            	},
            	searchKey(){
//            		 layer.msg("功能暂未实现,抱歉(┬＿┬)")
            		let title = $(".ss_it").val();
            		if(!title){  //没有的话 重新查询全部的数据
            			getActicles({},true)
            		}else{ //有的话 在之前的基础上开始查找数据
            			v.queryArticleData['title'] = title
            			getActicles(v.queryArticleData,false)
            		}
            	}
            },
            mounted(){

            },
            created(){
            	this.$nextTick(function(){
            	  	v = this
            	  	v.queryArticleData= {page:v.page,size:v.size} 
                	getActicles(v.queryArticleData,true);
                	getNotices();
                	getRecommends();
                	getBanners();
                	isLogin();
                	getTypes();
                	//初始化dom
                	init_top =80 -$(".label_g").height()/2
                	start_top= $(".left").offset().top
                	
            	})
          
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
              }
            
        });
         //vue end 

    });

 

    //查询文章
    function getActicles(article,isRender){
    	if(article){
    		article['page'] = v.page
    		article['size'] = v.size
    		v.queryArticleData = article;
    	}
    	post("fActicle/getActicles",v.queryArticleData,(res)=>{
       	   console.log(res);
       	   if(res.code == 200){
       		   let data = res.data
       		   v.total = data.total
       		   v.acticles = data.data
       		   if(v.total > v.size && isRender){
                 laypage.render({
                     elem: 'page',
                     count: v.total,
                     curr:v.page,
                     limit:v.size,
                     // ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
                     layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip'],
                     jump: function(obj,frist){
                         console.log(obj)
                         if(!frist){
                        	 v.page = obj.curr;
                        	 v.size = obj.limit; 
                        	 getActicles(v.queryArticleData,false);
                        	 scrollTop($('#middle'),100);
                         }
                     }
                     // ,theme:"#dddee0"
                 });
             }else if(v.total < v.size){
            	  $("#page").empty();
             }
       	   }else{
       		   layer.msg("服务器错误,获取文章数据失败");
       	   }
       });
    }
  
    
    //查询通告模块
    function getNotices(){
    	post("fNotice/getNotices",(res)=>{
    		console.log(res)
    		if(res.code == 200){
    			  v.notices = res.data
    		} 
    	});
    }
    
    
    function getRecommends(){
    	post("fRecommend/getRecommends",(res)=>{
    		if(res.code == 200){
    			  v.recommends = res.data
    		} 
    	});
    }
    

    
    function getBanners(){  //获取轮播图
    	post("fBanner/getBanners",(res)=>{
    		if(res.code == 200){
    			  v.banners = res.data
    		}else{
    			 layer.msg("服务器错误,获取轮播图失败");
    		} 
    		//解决获取数据之前，页面已经初始化了 不能渲染轮播图的问题
    		setTimeout(() => {  //等待vue进程先行渲染数据,然后layui进行轮播渲染
    		    carousel.render({
    	            elem: '#banner'
    	            ,width: '100%' //设置容器宽度
    	            ,arrow: 'always' //始终显示箭头
    	            // ,interval:800//自动切换的时间间隔，单位：ms（毫秒），不能低于800
    	            //,anim: 'updown' //切换动画方式
    	        });
			}, 1);
    	 
    	});
    }
    
    //获取所有的类型
    function getTypes(){
    	post("type/queryAllData",(res)=>{
    		if(res.code == 200){
    			 v.types = res.data
    		}else{
    			layer.msg("服务器错误,获取类型数据失败");
    		}
    	})
    }
    
    
    function getTag(typeId){
    	  post("tag/queryAllTag",{typeId:typeId},(res)=>{
    		   if(res.code == 200){
    			    v.tags = res.data
    		   }else{
       			layer.msg("服务器错误,获取标签数据失败");
       		  }
    	  });
    }



});

