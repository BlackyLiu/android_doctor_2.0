layui.use(['form','layer','table','laypage','util'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        util = layui.util,
        table = layui.table;
       laypage = layui.laypage;


    page=1;
    size =10;
    count=0;
    data = [];
    searchObj = {},
    //设置搜索条件 和分页数
    tableIns = null;
    queryData(page,size,searchObj,true);
    function queryData(p,s,searchObj,isRenderPage){
        $.post("/doctor/page",{
            	"doctor":searchObj,
            	"page":p,
            	"size":s
        },function(res){
    		     console.log(res)
//    		     let res = JSON.parse(resStr)
        		if(res.code != 200){
        			layer.msg("Server error, please reload the page.");
        			return false;
        		}
    		     count  = res.data.total;
    		     data = res.data.records
    		    //用户列表 重新渲染表格
    		    tableIns = table.render({
    		         elem: '#doctorList',
    		         data:data,
    		         cellMinWidth : 95,
    		         height : "full-200",
    		         cols : [[
    		             {type: "checkbox", fixed:"left"},
                          {field: 'jobNumber', title: 'Unique medical number', align:"center"},
                          {field: 'email', title: 'mailbox', align:"center"},
                          {field: 'password', title: 'password', align:"center"},
                          {field: 'realName', title: '(full) name', align:"center"},
                          {field: 'contactInformation', title: 'contact information', align:"center"},
                          {field: 'departments', title: 'administrative or technical offices', align:"center"},
                            {field: 'createTime', title: 'creation time', align:"center",templet:function(d){
			    		            	 return util.toDateString(new Date(d.createTime).getTime(), "yyyy-MM-dd HH:mm:ss");
			    		   }},
                            {field: 'updateTime', title: 'turnover time', align:"center",templet:function(d){
			    		            	 return util.toDateString(new Date(d.updateTime).getTime(), "yyyy-MM-dd HH:mm:ss");
			    		   }},
    		             {title: 'operate', templet:'#doctorListBar',width:200,fixed:"right",align:"center"}
    		         ]]
    		     });
    		     if(isRenderPage){
    			     laypage.render({
     		    	    elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
     		    	    ,count: count, //数据总数，从服务端得到
     		    	    limit:size,
     		    	   layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip'],
     		    	    jump: function(obj, first){
     		    	        if(!first){
     		    	            page = obj.curr;
     		    	            size = obj.limit;
     		    	        	queryData(page,size,searchObj,false);
     		    	        }
     		    	    }
     		     });
    		     }
    	})
    }





    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
    	let val = $(".searchVal").val();
        if(val){
        	searchObj = {real_name:val}
        	queryData(page,size,searchObj,false);
        }else{
//            layer.msg("请输入搜索的内容,暂支持用户名的模糊搜索");
        	searchObj = {}
        	queryData(page,size,searchObj,false);
        }
    });

    //添加用户
    function addDoctor(edit){
        var index = layui.layer.open({
            title : "Add users",
            type : 2,
            content : "/jump/toDoctorAdd",
            end:function(){
              	 queryData(page,size,searchObj,true);
               },
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
//                console.log(edit);
                body.find(".edit").hide();
                if(edit){
               body.find("#id").val(edit.id);
               body.find("#jobNumber").val(edit.jobNumber);
               body.find("#email").val(edit.email);
               body.find("#password").val(edit.password);
               body.find("#realName").val(edit.realName);
               body.find("#contactInformation").val(edit.contactInformation);
               body.find("#departments").val(edit.departments);

                    body.find(".edit").show();
                    body.find(".add").hide();
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('Click here to return to the user list.', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addDoctor();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('doctorList'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('Are you sure you want to delete the selected user?？', {icon: 3, title: 'prompting message'}, function (index) {
                 $.post("/doctor/batchDelete",{
                	 ids:JSON.stringify(newsId)  //将需要删除的newsId作为参数传入
                 },function(res){
                   	 if(res.code == 200){
                      	 queryData(page,size,searchObj,true);
                         layer.close(index);
                         layer.msg("Batch deletion succeeded.");
                   	 }
                 })
            })
        }else{
            layer.msg("Please select the user account to delete.");
        }
    })

    //列表操作
    table.on('tool(doctorList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addDoctor(data);
        }
        else if(layEvent === 'del'){ //删除
             layer.confirm('Are you sure you want to delete this user account？',{icon:3, title:'prompting message'},function(index){
                 $.get("/doctor/delete",{
                     id : data.id  //将需要删除的newsId作为参数传入
                 },function(res){
                	 if(res.code == 200){
                		 queryData(page,size,searchObj,true);
                		 layer.msg("Delete succeeded.");
                         layer.close(index);
                	 }
                 })
            });
        }
    });

})
