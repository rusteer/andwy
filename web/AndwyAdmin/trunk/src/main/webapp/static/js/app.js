var app={
	serverUrl:window.context+'/api/v1/client/app',
	displayName:"广告列表",
	name:"app",
	hotList:[{id:0},{id:1},{id:2},{id:3},{id:4},{id:5},{id:6},{id:7},{id:8},{id:9},{id:10}],
	typeList:[{id:1,name:"应用"},{id:2,name:"游戏"}],
	save:function(){save(this);},
	getGrid: function(){
		var viewConfig={getRowClass: function(record, rowIndex, rowParams, store){
			var result=null;
			if("D"==record.data.status) return "classRed";
			if("Y"==record.data.push) return "classBlue";
			return null;
		}};		
		var module=this;
		var grid= getGrid(this,viewConfig);
		Ext.each(["from",'to'],function(item){
			Ext.getCmp(module.name+"-"+item).on('change',function(){module.refresh();});
		});
		return grid;
	},
	remove: function(id){remove(this,id);},	
	getDockItems:function(){
		var dateString=Ext.util.Format.date(new Date(),'Y-m-d')
		var module=this;
		return [
		    {format: 'Y-m-d',id: module.name+'-from',xtype: 'datefield',value:dateString},
		    {format: 'Y-m-d',id: module.name+'-to',xtype: 'datefield',value:dateString},
		    "-"
		   ];
	},	
	getList:function(callBack){
		var module=this;
		var url=module.serverUrl;
		if(module.composeListParameters){
			url=url+"?"+module.composeListParameters();
		}
		Ext.Ajax.request({url:url,method:"GET",success : function(response) {
			var appArray=Ext.decode(response.responseText);
			if(!window.showPrivateApp){
				var tempArray=[];
				for(var i in appArray){
					var app=appArray[i];
					if(!app.privateApp){
						tempArray.push(app);
					}
				}
				appArray=tempArray;
			}
			var fromValue=Ext.util.Format.date(Ext.getCmp(module.name+"-from").value,"Y-m-d");
			var toValue=Ext.util.Format.date(Ext.getCmp(module.name+"-to").value,"Y-m-d");
			//console.log(fromValue);
			//console.log(toValue);
			Ext.Ajax.request({url:window.context+'/client/app/count/',method:"GET",params:{from:fromValue,to:toValue},success : function(response2) {
				var countArray=Ext.decode(response2.responseText);
				//console.log(countArray);
				//console.log(response2.responseText);
				for(var i in appArray){
					var app=appArray[i];
					var countObj=countArray[app.id];
					//console.log(countObj);
					app.countObj=countObj;
				}
				callBack(appArray);
			}});
		}});
	},
	refresh:function(){refresh(this);},
	getColumns:function(){
		var module=this;
		 return getColumns(this,[
           
		    {header:'名称',dataIndex:'name',width:120},
		    {header:'广告商',dataIndex:"advertiser",width:120},
		    {header:'备注',dataIndex:"comments",width:120},
		    {header:'价格',dataIndex:"price",width:50},
		    {header:'分类',dataIndex:"type",width:50,renderer:function(value){
		    	if(value==1) return "应用";
		    	if(value==2) return "游戏";
		    }},
/*			{header:'推送收益率',width:90,
				renderer:function(value,meta,record){
					var profit=0;
					var pushCount=0;
					var data=record.data.countObj;
					var price=record.data.price;
					if(data.pushCount) pushCount=data.pushCount;
					if(data.installSyncCount) profit=data.installSyncCount*price;
					console.log(record.data)
					console.log(pushCount+","+data.installSyncCount+","+price);
					if(pushCount>0 &&profit>0 ){
						return (profit*1000.00/pushCount).toFixed(3)+'‰';
					}
				},
				summaryType: function(records) {
					var pushSum=0;
					var profitSum=0;
					for(var i=0;i<records.length;i++){
						var data=records[i].data.countObj;
						var price=records[i].data.price;
						var pushCount=data.pushCount;
						var installSyncCount=data.installSyncCount;
						if(installSyncCount){
							pushSum+=pushCount;
							profitSum+=installSyncCount*price;
						}
					}
					if(pushSum>0&&profitSum>0) return (profitSum*1000.00/pushSum).toFixed(3)+'‰';
				}
			},	*/
			{header:'安装率',width:90,
				renderer:function(value,meta,record){
					var pushCount=0;
					var installCount=0;
					var count=record.data.countObj;
					if(count.pushCount) pushCount=count.pushCount;
					if(count.installCount) installCount=count.installCount;
					if(installCount>0 && pushCount>0){
						return (installCount*1000.00/pushCount).toFixed(3)+'‰'
					}
				},
				summaryType: function(records) {
					var installSum=0;
					var pushSum=0;
					for(var i=0;i<records.length;i++){
						var installCount=records[i].data.countObj.installCount;
						var pushCount=records[i].data.countObj.pushCount;
						if(installCount) installSum+=installCount;
						if(pushCount) pushSum+=pushCount;
					}
					if(installSum>0&&pushSum>0) return (installSum*1000.00/pushSum).toFixed(3)+"‰"
				}
			},	
/*			{header:'转化率',width:70,
				renderer:function(value,meta,record){
					var installSyncCount=0;
					var installCount=0;
					var count=record.data.countObj;
					if(count.installCount) installCount=count.installCount;
					if(count.installSyncCount) installSyncCount=count.installSyncCount;
					if(installCount>0 &&installSyncCount>0 ){
						return (installSyncCount*100.00/installCount).toFixed(2)+'%';
					}
				},
				summaryType: function(records) {
					var installSum=0;
					var installSyncSum=0;
					for(var i=0;i<records.length;i++){
						var installCount=records[i].data.countObj.installCount;
						var installSyncCount=records[i].data.countObj.installSyncCount;
						if(installSyncCount){
							installSum+=installCount;
							installSyncSum+=installSyncCount;
						}
					}
					if(installSum>0&&installSyncSum>0) return (installSyncSum*100.00/installSum).toFixed(2)+"%";
				}
			},	*/			
			{header:'推送数',flex:1,
				renderer:function(value,meta,record){return record.data.countObj.pushCount;},
				summaryType: function(records) {
					var sum=0;
					for(var i=0;i<records.length;i++){
						var pushCount=records[i].data.countObj.pushCount;
						if(pushCount)sum+=pushCount;
					}
					return sum;
				}
			},
			{header:'安装数',width:50,
				renderer:function(value,meta,record){
					return record.data.countObj.installCount;
				},
				summaryType: function(records) {
					var sum=0;
					for(var i=0;i<records.length;i++){
						var count=records[i].data.countObj.installCount;
						if(count)sum+=count;
					}
					return sum;
				}
			},
		
/*			{header:'同步数',width:70,
				renderer:function(value,meta,record){
					return record.data.countObj.installSyncCount;
				},
				summaryType: function(records) {
					var sum=0; for(var i=0;i<records.length;i++){ var count=records[i].data.countObj.installSyncCount; if(count)sum+=count; } return sum;
				}
			},	
			{header:'收益',width:70,
				renderer:function(value,meta,record){
					var count=record.data.countObj.installSyncCount;
					if(count) return "￥"+(count*record.data.price).toFixed(0);
				},
				summaryType: function(records) {
					var sum=0.0;
					for(var i=0;i<records.length;i++){
						var record=records[i];
						var count=record.data.countObj.installSyncCount;
						if(count)sum+=count*record.data.price;
					}
					return "￥"+sum.toFixed(0);
				}
			},	*/			
			{header:'热度',dataIndex:"hot",flex:1},
			{header:'开通推送',dataIndex:"push",flex:1},
			{header:'开始时间',dataIndex:"pushStartHour",flex:1},
			{header:'结束时间',dataIndex:"pushEndHour",flex:1},
			{header:'推送间隔',dataIndex:"pushInterval",flex:2},
			{header:'大小',dataIndex:'size',width:100},
			{header:'UID',dataIndex:'uid',width:50},
			{header:'分成方式',dataIndex:"sharingType",flex:1}
		]);
	},
	getFormItems:function(categoryList){
		//console.log(categoryList);
		var module=this;
		var modulePrefix=module.name+"-";
		var result=[
  	        {id:modulePrefix+'name',fieldLabel:'名称',name:'name',allowBlank:false,width:350},
  	        {id:modulePrefix+'hot',name:'hot', width:250,style:'text-align:right',fieldLabel:'热度',xtype:'combo',forceSelection: true,editable:false,displayField:'id',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id'],data:module.hotList})},
  	        //
  	        {id:modulePrefix+'sharingType',fieldLabel:'分成方式',name:'sharingType',allowBlank:false,width:350},
  	        {id:modulePrefix+'price',fieldLabel:'价格',name:'price',allowBlank:false,width:250,style:'text-align:right'},
  	        //
  	        {id:modulePrefix+'type',name:'type',width:350, fieldLabel:'一级分类',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:module.typeList})},
  	        {id:modulePrefix+'category',width:250,style:'text-align:right',fieldLabel:'二级分类',xtype:'combo',forceSelection:true,editable:false,name:'category',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:categoryList})},
  	        //
  	        {id:modulePrefix+'push',fieldLabel:'开通推送',xtype:'checkbox', name:'push',allowBlank:true,inputValue:'Y',width:350},
  	        {id:modulePrefix+'pushInterval',fieldLabel:'推送间隔',name:'pushInterval',allowBlank:false,width:250,style:'text-align:right'},
  	        //
  	        {id:modulePrefix+'pushStartHour',fieldLabel:'推送开始时间',name:'pushStartHour',allowBlank:false,width:350},
  	        {id:modulePrefix+'pushEndHour',fieldLabel:'推送结束时间',name:'pushEndHour',allowBlank:false,width:250,style:'text-align:right'},
  	        //
  	        {id:modulePrefix+'pkgName',fieldLabel:'包名',name:'pkgName',allowBlank:false,width:350},
  	        {id:modulePrefix+'size',fieldLabel:'大小',name:'size',allowBlank:false,width:250,style:'text-align:right'},
	        //
	        {id:modulePrefix+'pkgVersionName',fieldLabel:'版本',name:'pkgVersionName',allowBlank:false,width:350},
	        {id:modulePrefix+'pkgVersionCode',fieldLabel:'版本号',name:'pkgVersionCode',allowBlank:false,width:250,style:'text-align:right'},
	        //
	        {id:modulePrefix+'advertiser',fieldLabel:'广告商',name:'advertiser',allowBlank:false,width:350},
	        {id:modulePrefix+'comments',fieldLabel:'备注',name:'comments',allowBlank:true,width:250,style:'text-align:right'},
	        
	        /////
	        ,
	        
	        {xtype:'fieldset',title: '连接地址',defaultType:'textfield',layout: 'column',defaults: {labelWidth:50,width:325},items :[ 
		        {id:modulePrefix+'apkUrl',fieldLabel:'软件',name:'apkUrl',allowBlank:false},
		        {id:modulePrefix+'iconUrl',fieldLabel:'图标',name:'iconUrl',allowBlank:false,style:'text-align:right'},
		        {id:modulePrefix+'screen1Url',fieldLabel:'截图',name:'screen1Url'},
		        {id:modulePrefix+'screen2Url',fieldLabel:'截图',name:'screen2Url',style:'text-align:right'},
		        {id:modulePrefix+'screen3Url',fieldLabel:'截图',name:'screen3Url'},
		        {id:modulePrefix+'screen4Url',fieldLabel:'截图',name:'screen4Url',style:'text-align:right'}
	        ]},
	        {id:modulePrefix+'uid', xtype : 'hidden',name : 'uid'},
	        {xtype:'fieldset',title: '介绍',defaultType:'textfield',layout: 'column',defaults: {labelWidth:50},items :[ 
		        {id:modulePrefix+'hint',fieldLabel:'标题',name:'hint',allowBlank:false},
		        {id:modulePrefix+'description',fieldLabel:'详情',name:'description',xtype:'textarea',allowBlank:false,height:100}
	        ]}
       ];
		if(window.showPrivateApp){
			result.push({id:modulePrefix+'dailyInstallLimit',fieldLabel:'安装日限',name:'dailyInstallLimit',allowBlank:true,width:350 });
			result.push({id:modulePrefix+'privateApp',fieldLabel:'私有',name:'privateApp',allowBlank:true,width:250,style:'text-align:right'});
		}else{
			result.push({id:modulePrefix+'dailyInstallLimit',xtype :'hidden',name:'dailyInstallLimit',allowBlank:true });
			result.push({id:modulePrefix+'privateApp',xtype :'hidden',name:'privateApp',allowBlank:true });
			
		}
	   	
	   return getFormItems(module,result);
	},
	showForm:function(developId){
		var thisModule=this;
		var modulePrefix=thisModule.name+"-";
		var height=590;
		if(window.showPrivateApp){
			height=height+30;
		}
		category.getList(function(categoryList){
			showForm(thisModule,window.formWidth,height,developId,function(obj){
				//console.log(obj);
				Ext.getCmp(modulePrefix+'category').setValue(obj.category.id);
				Ext.each(['dailyInstallLimit','privateApp',"comments","advertiser","sharingType","price","name","description","hint","size","pkgName","pkgVersionName","pkgVersionCode",
				          "type","uid","hot","push","pushStartHour","pushEndHour","pushInterval","apkUrl","iconUrl","screen1Url","screen2Url","screen3Url","screen4Url"],function(item){
					var cmp=Ext.getCmp(modulePrefix+item);
					if(cmp) cmp.setValue(obj[item]);
				});
		},categoryList);});
	}
};


 
