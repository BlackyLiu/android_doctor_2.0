layui.use(['flow','form','layer','upload'],function(){
    var flow = layui.flow,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        upload = layui.upload,
        $ = layui.jquery;


    var commonImgStyle = 'style="width:188px;height:81px !important;"'
//    	var commonImgStyle = 'style="display:inline-block;width:95%;height:auto;"'
    //流加载图片
    var size = 10;  //单页显示图片数量
	var total = 0;
    var dir = sessionStorage.getItem("dir")
    flow.load({
        elem: '#Images', //流加载容器
        done: function(page, next){ //加载下一页

            $.get("/minio/list",{dir:dir},res=>{
                   if(res.code == 200){
                	   var imgList = []
                	   total = res.data.length
                	   data = res.data
                	   setTimeout(() => {
                            for(var i=0; i<data.length; i++){
                                let item = data[i];
                                imgList.push('<li><img '+commonImgStyle+'   src="'+ item +'" alt="图片" title="图片"><div class="operate"><div class="check">' +
                                    '<input type="checkbox" name="belle" lay-filter="choose" lay-skin="primary" title="图片">' +
                                    '</div><i data-id='+item+' class="layui-icon img_del">&#xe640;</i></div></li>');
                             }
                            //这个 page < (total/size) 判断是否最后一页
                            //imgList.join('') 会直接拼接到 挂载元素上
                            next(imgList.join(''), page < (total/size));
                	   }, 500);
                   }
            })

        }
    });


    //设置图片的高度
//    $(window).resize(function(){
//        $("#Images li img").height($("#Images li img").width());
//    })

    //多图片上传
    upload.render({
        elem: '.uploadNewImg',
        url: '/minio/upload',
        data:{name:dir},
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
            	//result 和file是 七牛拿到的文件信息 默认的不要管
//            	console.log(result)
//            	console.log(file)
//                 $('#Images').prepend('<li><img '+commonImgStyle+' src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img"><div class="operate"><div class="check"><input type="checkbox" name="belle" lay-filter="choose" lay-skin="primary" title="'+file.name+'"></div><i class="layui-icon img_del">&#xe640;</i></div></li>')
//                 //设置图片的高度
//                 $("#Images li img").height($("#Images li img").width());
//                 form.render("checkbox");
            });
        },
        done: function(res){
            //上传完毕 上传接口返回的imgPath 不需要处理 不是上传返回的接口 就需要处理 才能回显
        	// console.log("done")
        	// console.log(res);
        	if(res.code == 200){
        	     let item = res.data.minFileUrl
                $('#Images').prepend('<li><img '+commonImgStyle+'  ' +
                    ' src="'+ item +'" alt="图片" ' +
                    'title="图片">' +
                    '<div class="operate">' +
                    '<div class="check">' +
                    '<input type="checkbox" name="belle" ' +
                    'lay-filter="choose" ' +
                    'lay-skin="primary" title="图片">' +
                    '</div><i data-id="'+item+'" class="layui-icon img_del">&#xe640;</i></div></li>');

                form.render("checkbox");  //渲染单选框,会渲染出对应的文字
            }else{
        	     layer.msg("服务器错误");
            }
        }
    });

    //弹出层 点击图片
    $("body").on("click","#Images img",function(){
    	 let imgPath = $(this).prop("src")
		  console.log(imgPath);
    	 var mediaContent = "<img src='"+imgPath+"' style='display:inline-block;max-width:10%;height:auto'>"
//    	  $(parent.document.body).find("iframe").contents().eq(1).find("body").append(mediaContent)
//     	 if(layer.selectHm){
//     		  if(layer.selectHm == 1){
//     			  layer.aContent.append(mediaContent)
//     		  }else{
//     			  layer.imgContent.append(mediaContent)
//     		  }
//     	 }else{
//     		 layer.imgContent.append(mediaContent)
//     	 }
        layer.clickImgDo(imgPath);
        console.log(layer)
		 layer.close(layer.selectQiNiuImgIndex)
//		 layer.selectQiNiuImg = imgPath
//        parent.showImg();
    })


    //删除单张图片
    $("body").on("click",".img_del",function(){
        var _this = $(this);
        layer.confirm('确定删除图片"'+_this.siblings().find("input").attr("title")+'"吗？',{icon:3, title:'提示信息'},function(index){
//            _this.parents("li").hide(1000);
//            setTimeout(function(){_this.parents("li").remove();},950);
//            layer.close(index);
        	  let id= _this.attr("data-id");
        	  console.log(id);
        	  $.get("/minio/delete",{objectName:id},(res)=>{
        		   if(res.code == 200){
        	            _this.parents("li").hide(1000);
        	            setTimeout(function(){_this.parents("li").remove();},950);
        			    layer.msg("删除成功");
        		   }else{
        			   layer.msg("服务器错误");
        		   }
        	  });
        });
    })

    //全选
    form.on('checkbox(selectAll)', function(data){
        var child = $("#Images li input[type='checkbox']");
        child.each(function(index, item){
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)",function(data){
        var child = $(data.elem).parents('#Images').find('li input[type="checkbox"]');
        var childChecked = $(data.elem).parents('#Images').find('li input[type="checkbox"]:checked');
        if(childChecked.length == child.length){
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = true;
        }else{
            $(data.elem).parents('#Images').siblings("blockquote").find('input#selectAll').get(0).checked = false;
        }
        form.render('checkbox');
    })

    //批量删除
    $(".batchDel").click(function(){
        var $checkbox = $('#Images li input[type="checkbox"]');
        var $checked = $('#Images li input[type="checkbox"]:checked');
        if($checkbox.is(":checked")){
            layer.confirm('确定删除选中的图片？',{icon:3, title:'提示信息'},function(index){
                var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                setTimeout(function(){
                    //删除数据
                	var ids = [];
                    $checked.each(function(){
                    	   let id = $(this).parents("li").find("i.img_del").attr("data-id")
                    	   ids.push(id);
                    })
                    $.post("/minio/batchDelete",{ids:JSON.stringify(ids)},(res)=>{
                    	  if(res.code == 200){
                    		  $checked.each(function(){
                                $(this).parents("li").hide(1000);
                                setTimeout(function(){$(this).parents("li").remove();},950);
                    		  })
                              $('#Images li input[type="checkbox"],#selectAll').prop("checked",false);
                              form.render();
                              layer.msg("删除成功");
                    	  }else{
                    		  layer.msg("服务器错误");
                    	  }
                    	  layer.close(index);
                    });

                },2000);
            })
        }else{
            layer.msg("请选择需要删除的图片");
        }
    })

})
