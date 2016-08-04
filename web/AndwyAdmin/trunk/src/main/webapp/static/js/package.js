 var package={
	 serverUrl:window.context+'/api/v1/package',
	 displayName:"软件包",
	 name:"package",
	 publishStatusList:[{label:"未提交",value:"1"},{label:"已提交",value:"2"},{label:"已上架",value:"3"},{label:"更新中",value:"5"},{label:"被拒绝",value:"4"}],
	 getColumns:function(){
		var module=this;
		var render=function(value, metaData, record, rowIdx, colIdx, store, view) {
			return value.productName;
		};
		var flexIndex=1;
		return getColumns(this,[
         {header:'UID',dataIndex:'uid',width:50},
		 {header: '产品信息',    dataIndex:"product", width:150,renderer:function(value,meta,record){
				 var priority=record.data.product.priority;
				 var priorityName= priority>0?product.priorityList[priority-1].name:'';
				 return value.productName+"("+value.batchId+","+priorityName+""+")";
			 }
		 },
		 {header: '开发者信息',flex:1,dataIndex:"marketAccount",renderer:function(value,meta,record) {
			 	return value.market.name+":"+value.developer.name;
			 }
		 },		 
		 {header: "软件包信息",flex:1,renderer: function (value, meta, record) {
			 	 var obj=record.data;
				 var packageUrl=module.getImageUrl(obj,'icon1');
				 var productUrl=getIconPath(obj.product);
				 var hrefId="packageicon"+obj.id;
				 var result="<a id='"+hrefId+"' href='"+packageUrl+"' target='_blank'><img src='";

				 result+=packageUrl+"' width=12 height=12 onError=\"this.onerror=null;document.getElementById('"+hrefId+"').href='";
				 result+=productUrl+"';this.src='"+productUrl+"';\"/></a> ";

                //result+=packageUrl+"' width=12 height=12 onError=\"this.onerror=null;document.getElementById('"+hrefId+"').href='";
                //result+=packageUrl+"';this.src='"+packageUrl+"';\"/></a> ";
				 result+=obj.packageProductName+"("+obj.packageName+")";
				 //console.log(result);
				 return result;
		 	} 
		 },
		 {header:'植入广告',dataIndex:'injectAds',width:55},
		 {header:'检测出广告',dataIndex:'adsDetected',width:60},
		 //{header: '需要打包?',  dataIndex: 'needBuild', width: 50},
		 /* {header: '构建次数',  dataIndex: 'buildCount', width: 80},*/
		 {header: "打包状态",renderer: function (value, meta, record) {
				 if(record.data.buildStatus!='Success') return record.data.buildStatus;
				 var product=record.data.product;
				 var marketAccount=record.data.marketAccount;
				 var packageName=record.data.packageName;
				 var versionDate=product.versionDate;
				 var versionCount=product.versionCount;
				 var url=packageRoot+ "/" + product.projectName+"/"
				 	+marketAccount.market.shortName+"/"
				 	+marketAccount.developer.shortName+"/"
				 	+packageName+"."+versionDate+"-"+versionCount+".apk";
				 return "<div class='controlBtn'><a href='"+url+"' target='_blank'>完成</a></div>";  
			 },  
			 sortable: false  
		 }, 
		 {header: "版本",   renderer: function (value, meta, record) {
			 var product=record.data.product;
             var versionDate=product.versionDate;
			 var versionCount=product.versionCount;
			 if(window['versionType']==1){
                return versionDate.substring(3, 4) + "." + parseInt(versionDate.substring(4, 6)) + "." + parseInt(versionDate.substring(6, 8)) + "." + versionCount;
             }
             return  versionDate;
		 }},
		 {header: "版本号",   renderer: function (value, meta, record) {
			 var product=record.data.product;
			 var versionDate=product.versionDate;
			 var versionCount=product.versionCount;
             if(window['versionType']==1){
                 var result=versionDate.substring(3,8);
                 if (versionCount < 100) result += "0";
                 if (versionCount < 10) result += "0";
                 result += versionCount;
                 return result;
             }
             return  versionCount;
		 }},		 
		 /*{header: '需要发布?',  dataIndex: 'needPublish', width: 50},*/
		 {header: '发布状态',  dataIndex: 'publishStatus', width: 60,renderer:function(value){
			 for(var i in module.publishStatusList){
				 var obj=module.publishStatusList[i];
				 if(obj.value==value) return obj.label;
			 }
			 return '';
		 }},
		 {header: '提交日期',  dataIndex: 'submitDate', width: 80},
		 {header: '提交版本号',  dataIndex: 'publishingVersionCode', width: 80},
		 {header: '上架日期',  dataIndex: 'publishDate', width: 80},
		 {header: '发布次数',  dataIndex: 'publishSuccessCount', width: 60},
		 {header: '市场版本',  dataIndex: 'marketVersion', width: 60},
		 {header: '查看地址',  dataIndex: 'publishUrl', width: 60,renderer:function(value){
			 if(value) return "<div class='controlBtn'><a href='"+value+"' target='_blank'>查看</a></div>";  
			 return '';
		 }},
		 {header: '下载地址',  dataIndex: 'downloadUrl', width: 60,renderer:function(value){
			 if(value) return "<div class='controlBtn'><a href='"+value+"' target='_blank'>下载</a></div>";  
			 return '';
		 }},		 
		 {header: '失败说明',  dataIndex: 'failureDescription',flex: 1},
		 {header: '广告设置',flex:1,dataIndex:"config",renderer:function(value,meta,record) {
			 	return value?value.name:"";
			 }
		 }
		]);
	},
	addEvent:function(){
		var modulePrefix=this.name+"-";
		Ext.getCmp(modulePrefix+"marketAccount").getStore().filterBy(function(record) {return false;}); 
		Ext.getCmp(modulePrefix+"product").getStore().filterBy(function(record) {return false;}); 
		var buildCmp=Ext.getCmp(modulePrefix+"needBuild");
		Ext.getCmp(modulePrefix+"batchId").on('change',function(cmb,record,index){  
            var batchId = cmb.getValue();
            var cmp=Ext.getCmp(modulePrefix+"product");
            cmp.clearValue();
            cmp.getStore().clearFilter();
            cmp.getStore().filterBy(function(record) {   
    			return record.get("batchId")==batchId;   
    		}); 
            buildCmp.setValue(true);
		});
		Ext.getCmp(modulePrefix+"market").on('change',function(cmb,record,index){  
            var marketId = cmb.getValue();
            document.getElementById('package-form-market').value=marketId;
            var cmp=Ext.getCmp(modulePrefix+"marketAccount");
            cmp.clearValue();
            cmp.getStore().clearFilter();
            cmp.getStore().filterBy(function(record) {   
    			return record.get("marketId")==marketId;   
    		}); 
            buildCmp.setValue(true);
		});
		
		Ext.getCmp(modulePrefix+"product").on('select',function(cmb,records,index){  
            if(records.length==1){
            	var packageName=records[0].data.basePackage;
            	console.log(Ext.getCmp(modulePrefix+"market").value);
            	Ext.getCmp(modulePrefix+"packageName").setValue(records[0].data.basePackage);
            	Ext.getCmp(modulePrefix+"packageProductName").setValue(records[0].data.productName);
            }
            buildCmp.setValue(true);
		});		
		
		Ext.getCmp(modulePrefix+"packageName").on('change',function(){ buildCmp.setValue(true);});
		Ext.getCmp(modulePrefix+"marketAccount").on('change',function(){buildCmp.setValue(true);});
		Ext.getCmp(modulePrefix+"packageProductName").on('change',function(){buildCmp.setValue(true);});
		Ext.getCmp(modulePrefix+"injectAds").on('change',function(){ buildCmp.setValue(true);});
	},
	
	getFormItems:function(productList,marketList,marketAccountList,configList){
		var module=this;
		var modulePrefix=module.name+"-";
		return getFormItems(this,[ 
		   {fieldLabel:'批次',id : modulePrefix+'batchId',xtype:'combo',forceSelection: true,editable:false,name:'batchId', displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:product.batchList})},
		   {fieldLabel:'产品',id:modulePrefix+'product',xtype:'combo',lastQuery:"",forceSelection:true,editable:false,name:'product',displayField:'productName',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id','productName','batchId','basePackage'],data:productList})},
		   {fieldLabel:'市场',id:modulePrefix+'market',xtype:'combo',forceSelection:true,editable:false,name:'market',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id','name'],data:marketList})},
		   {fieldLabel:'开发者',id:modulePrefix+'marketAccount',xtype:'combo',lastQuery:"",forceSelection:true,editable:false,name:'marketAccount',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id',{name:'name',mapping:'developer.name'},{name:"marketId",mapping:'market.id'}],data:marketAccountList})},
		   {fieldLabel:'植入广告', id : modulePrefix+'injectAds', xtype:'checkbox',  name : 'injectAds', allowBlank : true ,inputValue:'Y' ,checked:true},
		   {fieldLabel:'广告参数设置',id:modulePrefix+'config',xtype:'combo',forceSelection:true,editable:false,name:'config',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id','name'],data:configList})},
		   {fieldLabel:'包名',  id : modulePrefix+'packageName', name : 'packageName', allowBlank : false  }, 
		   {fieldLabel:'版本产品名称',id : modulePrefix+'packageProductName', name : 'packageProductName',allowBlank : true  }, 
		   /*{fieldLabel:'版本说明',id:modulePrefix+'versionDescription',name:'versionDescription',allowBlank:true  },*/
	       {fieldLabel:'图标文件',id : modulePrefix+'icon1',name : 'icon1',inputType : 'file',height:20 },
	       {id : 'icon1Show',xtype:'box',fieldLabel:"预览",width:15,autoEl:{width:15,height:15,tag:'img'}},	       
	       /*{fieldLabel : '截图1', id : modulePrefix+'screenshot1',name : 'screenshot1',inputType : 'file'},
	       {id : 'screenshot1Show',xtype : 'box',fieldLabel : "预览", width :30,autoEl:{width:30,height : 30,tag : 'img' }},	
	       {fieldLabel : '截图2', id : modulePrefix+'screenshot2', name : 'screenshot2',inputType : 'file'},
	       {id : 'screenshot2Show',xtype : 'box',fieldLabel : "预览", width :30,autoEl:{width:30,height : 30,tag : 'img' }},	
	       {fieldLabel : '截图3', id : modulePrefix+'screenshot3',name : 'screenshot3',inputType : 'file'},
	       {id : 'screenshot3Show',xtype : 'box',fieldLabel : "预览", width :30,autoEl:{width:30,height : 30,tag : 'img' }},
	       {fieldLabel : '截图4', id : modulePrefix+'screenshot4',name : 'screenshot4',inputType : 'file'},
	       {id : 'screenshot4Show',xtype : 'box',fieldLabel : "预览", width :30,autoEl:{width:30,height : 30,tag : 'img' }},
	       {fieldLabel : '截图5', id : modulePrefix+'screenshot5',name : 'screenshot5',inputType : 'file'},
	       {id : 'screenshot5Show',xtype : 'box',fieldLabel : "预览", width :30,autoEl:{width:30,height : 30,tag : 'img' }},*/
	       {fieldLabel:'需要打包', id : modulePrefix+'needBuild', xtype:'checkbox',  name : 'needBuild', allowBlank : true ,inputValue:'Y' },
	       /*{fieldLabel:'需要发布', id : modulePrefix+'needPublish',  xtype:'checkbox', name : 'needPublish', allowBlank : true,inputValue:'Y' },*/	
		   {fieldLabel:'发布状态',id:modulePrefix+'publishStatus',name : 'publishStatus',allowBlank:true,xtype:'combo',forceSelection: true,editable:false,displayField:'label',valueField:'value',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['label','value'],data:module.publishStatusList})}, 
		   {fieldLabel:'提交日期',id:modulePrefix+'submitDate',name:'submitDate',allowBlank:true,format: 'Y-m-d',xtype: 'datefield'},
		   {fieldLabel:'提交版本号',id:modulePrefix+'publishingVersionCode',name:'publishingVersionCode',allowBlank:true,xtype: 'numberfield'},
		   {fieldLabel:'上架日期',id:modulePrefix+'publishDate',name:'publishDate',allowBlank:true,format: 'Y-m-d',xtype: 'datefield'},
		   {fieldLabel:'发布次数',id:modulePrefix+'publishSuccessCount',name : 'publishSuccessCount',value:0}, 
		   {fieldLabel:'市场版本',id:modulePrefix+'marketVersion',name:'marketVersion',allowBlank:true},
		   {fieldLabel:'查看地址',id:modulePrefix+'publishUrl',name:'publishUrl',allowBlank:true},
		   {fieldLabel:'下载地址',id:modulePrefix+'downloadUrl',name:'downloadUrl',allowBlank:true},
		   {fieldLabel:'检测出广告', id : modulePrefix+'adsDetected', xtype:'checkbox',  name : 'adsDetected', allowBlank : true ,inputValue:'Y' },
	       {fieldLabel:'失败原因', id:modulePrefix+'failureDescription', name : 'failureDescription', xtype:'textarea',allowBlank : true} ,
		   {id:modulePrefix+'publishTryCount', xtype : 'hidden',name : 'publishTryCount',value:0}, 		   
		   {id:modulePrefix+'buildCount', xtype : 'hidden',name : 'buildCount',value:0}, 
		   {id:modulePrefix+'buildStatus', xtype : 'hidden',name : 'buildStatus'}		   
       ]);	
	},
	getImageUrl:function(obj,item){
		return window.serverIp+'/static/resources/'+obj.product.projectName+"/"+obj.marketAccount.market.shortName+"/"+obj.marketAccount.developer.shortName+"/images/"+item+".png";
	},
	showForm:function(entityId){
		var module=this;
		var modulePrefix=module.name+"-";
		product.getList(function(productList){
			market.getList(function(marketList){
				marketAccount.getList(function(marketAccountList){
					config.getList(function(configList){
						var filteredConfigList=[];
						for(var i=0;i<configList.length;i++){
							if(configList[i].level==4) filteredConfigList[filteredConfigList.length]=configList[i];
						}
						showForm(module,window.formWidth,720,entityId,function(obj){
							var batchIdCmp=Ext.getCmp(modulePrefix+'batchId');
							batchIdCmp.setValue(obj.product.batchId);
							batchIdCmp.setEditable(false);
							Ext.getCmp(modulePrefix+'product').setValue(obj.product.id);
							Ext.getCmp(modulePrefix+'market').setValue(obj.marketAccount.market.id);
							Ext.getCmp(modulePrefix+'marketAccount').setValue(obj.marketAccount.id);
							
							if(obj.config) Ext.getCmp(modulePrefix+'config').setValue(obj.config.id);
							
							Ext.getCmp(modulePrefix+'packageName').setValue(obj.packageName);
							Ext.getCmp(modulePrefix+'packageProductName').setValue(obj.packageProductName);
							/*Ext.getCmp(modulePrefix+'versionDescription').setValue(obj.versionDescription);*/
							Ext.getCmp(modulePrefix+'adsDetected').setValue(obj.adsDetected);
							Ext.getCmp(modulePrefix+'injectAds').setValue(obj.injectAds);
							Ext.getCmp(modulePrefix+'needBuild').setValue(obj.needBuild);
							/*Ext.getCmp(modulePrefix+'needPublish').setValue(obj.needPublish);*/
							Ext.getCmp(modulePrefix+'marketVersion').setValue(obj.marketVersion);
							
							Ext.getCmp(modulePrefix+'buildCount').setValue(obj.buildCount);
							Ext.getCmp(modulePrefix+'buildStatus').setValue(obj.buildStatus);
							Ext.getCmp(modulePrefix+'submitDate').setValue(obj.submitDate);
							Ext.getCmp(modulePrefix+'publishingVersionCode').setValue(obj.publishingVersionCode);
							Ext.getCmp(modulePrefix+'publishDate').setValue(obj.publishDate);
							Ext.getCmp(modulePrefix+'publishUrl').setValue(obj.publishUrl);
							Ext.getCmp(modulePrefix+'downloadUrl').setValue(obj.downloadUrl);
							Ext.getCmp(modulePrefix+'publishTryCount').setValue(obj.publishTryCount);
							Ext.getCmp(modulePrefix+'publishSuccessCount').setValue(obj.publishSuccessCount);
							Ext.getCmp(modulePrefix+'publishStatus').setValue(obj.publishStatus);
							Ext.getCmp(modulePrefix+'failureDescription').setValue(obj.failureDescription);
							Ext.each(["icon1","screenshot1","screenshot2","screenshot3","screenshot4","screenshot5"],function(item){
								var cmp=Ext.getCmp(item+"Show");
								if(cmp) cmp.el.dom.src=module.getImageUrl(obj,item);
							});						
						},productList,marketList,marketAccountList,filteredConfigList);	
						module.addEvent();
					});	
				});
			});
		});
	},
	getDockItems:function(){
		var module=this;
		return [
		    {text: '批次'},{id :module.name+'FilterByBatchId',xtype:'combo',lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:product.batchList})}
		    ,"-"
		    ,{text: product.displayName+''},{id :module.name+'FilterByProductId',xtype:'combo',lastQuery:"",displayField:'productName',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','productName']})}
		    ,"-"
		    ,{text: '优先级'},{id :module.name+'FilterByPriority',xtype:'combo',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:product.priorityList})}
		    ,"-"		    
		    ,{text:developer.displayName+''},{id :module.name+'FilterByDeveloperId',xtype:'combo',lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name']})}
		    ,"-"
		    ,{text:market.displayName+''},{id :module.name+'FilterByMarketId',xtype:'combo',lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name']})}
		    ,"-"
		   ];
	},	
	filterChanged:function(){
		var module=this;
		var gridStore=Ext.getCmp(module.name).getStore();
		var batchIdComb=Ext.getCmp(module.name+"FilterByBatchId");
		var marketComb=Ext.getCmp(module.name+"FilterByMarketId");
		var productCombo=Ext.getCmp(module.name+"FilterByProductId");
		var developerComb=Ext.getCmp(module.name+"FilterByDeveloperId");
		var priorityComb=Ext.getCmp(module.name+"FilterByPriority");
		
		var batchId=batchIdComb.getValue();
		var productId=productCombo.getValue();
		var marketId=marketComb.getValue();
		var developerId=developerComb.getValue();
		var priority=priorityComb.getValue();
		var gridFiltered=false;
		productCombo.getStore().clearFilter();
		gridStore.clearFilter();
		
		gridStore.filterBy(function(record) {
			var productFilter=function(){
				return !productId || record.data.product.id==productId;
			};
			var batchIdFilter=function(){
				return !batchId || record.data.product.batchId==batchId;
			};
			var priorityFilter=function(){
				return !priority || record.data.product.priority==priority;
			};			
			var developerIdFilter=function(){
				return !developerId || record.data.marketAccount.developer.id==developerId;
			};			
			var marketIdFilter=function(){
				return !marketId || record.data.marketAccount.market.id==marketId;
			};
			return productFilter() && batchIdFilter() && marketIdFilter() &&developerIdFilter()&&priorityFilter();
		});
		
		productCombo.getStore().filterBy(function(record) {
			return !batchId || record.data.batchId==batchId;
		});
	},
	getGrid: function(){
		var module=this;
		var viewConfig={getRowClass: function(record, rowIndex, rowParams, store){
				console.log(record);
				var status=record.data.publishStatus;
				return status==2||status==3 ||status==5?null:"disabledEntity";
			}
		};
		var grid= getGrid(module,viewConfig);
		var productComb=Ext.getCmp(module.name+"FilterByProductId");
		var batchIdComb=Ext.getCmp(module.name+"FilterByBatchId");
		var marketComb=Ext.getCmp(module.name+"FilterByMarketId");
		var developerComb=Ext.getCmp(module.name+"FilterByDeveloperId");
		var priorityComb=Ext.getCmp(module.name+"FilterByPriority");
		batchIdComb.on('change',function(){module.refresh();});
		productComb.on('change',function(){module.filterChanged();});  
		marketComb.on('change',function(){module.filterChanged();});
		developerComb.on('change',function(){module.filterChanged();});
		priorityComb.on('change',function(){module.filterChanged();});
		return grid;
	},
	save: function() {
		var module=this;
		if(window.fp.getForm().isValid()){
			window.fp.getForm().submit({ url : window.context+'/package/save', method : 'POST',
				success : function(form, action) {
					win.close();
					module.refresh();
				},
				failure : function() {
					alert("提交失败");
				}
	        });
		}
	},
	composeListParameters:function(){
		var module=this;
		var batchIdComb=Ext.getCmp(module.name+"FilterByBatchId");
		var batchId=batchIdComb.getValue();
		if(!batchId) batchId=0;
		return  "batchId="+batchId;
	},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){
		getList(this,callBack);
	},	
	refresh:function(){
		var module=this;
		var f1=function(){
			refresh(module,function(){
				module.filterChanged();		
			});			
		}
		if(!module.filterLoaded){
			product.getList(function(productList){
				developer.getList(function(developerList){
					for(var i=0;i<developerList.length;i++){
						var devloper=developerList[i];
						if(devloper.nickName) devloper.name=devloper.name+"-"+devloper.nickName;
					}
					market.getList(function(marketList){
						Ext.getCmp(module.name+"FilterByProductId").getStore().loadData(productList);
						Ext.getCmp(module.name+"FilterByDeveloperId").getStore().loadData(developerList);
						Ext.getCmp(module.name+"FilterByMarketId").getStore().loadData(marketList);
						module.filterLoaded=true;
						f1();
					});
				});
			});
		}else{
			f1();
		}
	}
};

/*
 * var module=this;
		grid.on('itemcontextmenu',function(view,record,item,index,e,eOpts){
			 e.preventDefault(); 
             e.stopEvent(); 
             var menu = new Ext.menu.Menu({ float:true, items:[
                 {text:"修改记录", iconCls:'leaf',handler:function(){ 
	                	 this.up("menu").hide(); 
	                	 module.showForm(record.data.id);
                	 }
                 },
            	 {text:"添加记录", iconCls:'leaf',handler:function(){
            		 	this.up("menu").hide();
            		 	module.showForm(0);
            		 }
                 },
            	 {text:"删除记录", iconCls:'leaf',handler:function(){ 
        				this.up("menu").hide(); 
        				Ext.MessageBox.confirm('警告', '确认要删除吗?', function(btn){
        					if("yes"==btn)  module.remove(record.data.id);
        				});
            		} 
            	 },
            	 {text:"添加图标", iconCls:'leaf',handler:function(){
            		 module.updatefile();
            		 }
            	 }
            	 ,
            	 {text:"添加截图", iconCls:'leaf',handler:function(){
            		 
            		 }
            	 }            	 
            	] 
            	}).showAt(e.getXY());//让右键菜单跟随鼠标位置 
		});
 */
 
