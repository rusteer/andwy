var appCount={
	serverUrl:window.context+'/client/app/syncCount/',
	displayName:"广告统计",
	name:"appCount",
	showEditFormFlag:false,
	save:function(){
		var module=this;
		if(window.fp.getForm().isValid()){
			 Ext.Ajax.request({ url:window.context+'/client/app/syncCount/',method: "POST", waitMsg: "Please wait while saving", waitTitle: "Please wait",params: window.fp.getForm().getValues(),
			    success:function(){
			    	win.close();
			    	module.refresh();
			    },
			    failure:function(){alert("Save Failed"); }
	        });			
		}		
	},
	getGrid: function(){
		var module=this;
		var grid= getGrid(this);
		Ext.each(["FilterByApp","from",'to'],function(item){
			Ext.getCmp(module.name+"-"+item).on('change',function(){module.refresh();});
		});
		Ext.getCmp(module.name+"-FilterAppByStatus").on('change',function(){module.refresh();});
		return grid;
	},
	remove: function(id){remove(this,id);},	
	getDockItems:function(){
		var toDate=Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.DAY, -1),'Y-m-d');
		var fromDate = Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.DAY, -10),'Y-m-d');
		var module=this;
		var modulePrefix=module.name+"-";
		return [
		    {id: modulePrefix+'FilterAppByStatus',fieldLabel:'显示所有',xtype:'checkbox',inputValue:'E',checked:false,labelWidth:60},"-",
		    {id :modulePrefix+'FilterByApp',xtype:'combo',selectOnFocus:true,lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name','status']})},
		    "-",
		    {format: 'Y-m-d',id: modulePrefix+'from',xtype: 'datefield',value:fromDate},
		    {format: 'Y-m-d',id: modulePrefix+'to',xtype: 'datefield',value:toDate},
		    "-"
		   ];
	},		
	getList:function(callBack){
		var module=this;
		var url=module.serverUrl;
		var fromValue=Ext.util.Format.date(Ext.getCmp(module.name+"-from").value,"Y-m-d");
		var toValue=Ext.util.Format.date(Ext.getCmp(module.name+"-to").value,"Y-m-d");
		var appComb=Ext.getCmp(module.name+"-FilterByApp");
		var appId=appComb.getValue();
		if(appId && fromValue && toValue && fromValue.length==10 && toValue.length==10){
			Ext.Ajax.request({url:window.context+'/client/app/dailyCount/',method:"GET",params:{from:fromValue,to:toValue,appId:appId},success : function(response) {
				callBack(Ext.decode(response.responseText));
			}});
		}else{
			callBack({});
		}
	},
	filterApp:function(){
		var module=this;
		var modulePrefix=module.name+"-";		
		var showDisalbed=Ext.getCmp(modulePrefix+"FilterAppByStatus").checked;
		var cmp=Ext.getCmp(modulePrefix+"FilterByApp");
		var store=cmp.getStore();
		store.filterBy(function(record) {
			return showDisalbed?true:record.get("status")=="E";
		});
	},
	refresh:function(){
		var module=this;
		var f1=function(){
			refresh(module,function(){
				module.filterApp();
			});			
		}
		if(!module.filterLoaded){
			app.getList(function(appList){
				var cmp=Ext.getCmp(module.name+"-FilterByApp");
				var store=cmp.getStore();
				store.loadData(appList);
				store.sort([{sorterFn: function(v1, v2) {
					return v1.get("status")==v2.get("status")?(v1.get("id")>v2.get("id")?1:-1):(v1.get("status")<v2.get("status")?1:-1);
				}}]);				
				//sorters: [{property: 'status', direction: 'desc'}],
				module.filterLoaded=true;
				if(store.getCount()>0){
					var recordSelected = store.getAt(0);                     
			        cmp.setValue(recordSelected.get('id'));
				}
				f1();
			});
		}else{
			f1();
		}
	},
	getColumns:function(){
		var module=this;
		return [
			{header:'日期',dataIndex:'statDate',flex:1,summaryType: 'count',
				renderer:function(value,meta,record){
					return "<a href=\"javascript:"+module.name+".showForm("+record.get('id')+","+record.get('installSyncCount')+",'"+record.get('statDate')+"')\">"+value+"</a>";
			 	},
				summaryRenderer: function(value, summaryData, dataIndex) {
					return value+"天";
				}
			},
			{header:'价格',dataIndex:'price',flex:1},
			{header:'推送收益率',flex:1,
				renderer:function(value,meta,record){
					var profit=0;
					var pushCount=0;
					var data=record.data;
					if(data.pushCount) pushCount=data.pushCount;
					if(data.installSyncCount) profit=data.installSyncCount*data.price;
					if(pushCount>0 &&profit>0 ){
						return (profit*1000.00/pushCount).toFixed(3)+'‰';
					}
				},
				summaryType: function(records) {
					var pushSum=0;
					var profitSum=0;
					for(var i=0;i<records.length;i++){
						var data=records[i].data;
						var pushCount=data.pushCount;
						var installSyncCount=data.installSyncCount;
						if(installSyncCount){
							pushSum+=pushCount;
							profitSum+=installSyncCount*data.price;
						}
					}
					if(pushSum>0&&profitSum>0) return (profitSum*1000.00/pushSum).toFixed(3)+'‰';
				}
			},	
			{header:'转化率',flex:1,
				renderer:function(value,meta,record){
					var installSyncCount=0;
					var installCount=0;
					var count=record.data;
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
						var installCount=records[i].data.installCount;
						var installSyncCount=records[i].data.installSyncCount;
						if(installSyncCount){
							installSum+=installCount;
							installSyncSum+=installSyncCount;
						}
					}
					if(installSum>0&&installSyncSum>0) return (installSyncSum*100.00/installSum).toFixed(2)+"%";
				}
			},			
			{header:'安装率',flex:1,
				renderer:function(value,meta,record){
					var pushCount=0;
					var installCount=0;
					var count=record.data;
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
						var installCount=records[i].data.installCount;
						var pushCount=records[i].data.pushCount;
						if(installCount) installSum+=installCount;
						if(pushCount) pushSum+=pushCount;
					}
					if(installSum>0&&pushSum>0) return (installSum*1000.00/pushSum).toFixed(2)+"‰"
				}
			},				
			{header:'推送数',dataIndex:'pushCount',flex:1,summaryType: function(records) {
				var sum=0;
				for(var i=0;i<records.length;i++){
					var pushCount=records[i].get('pushCount');
					if(pushCount)sum+=pushCount;
				}
				return sum;
			}},
			{header:'安装数',dataIndex:'installCount',flex:1,summaryType: function(records) {
				var sum=0;
				for(var i=0;i<records.length;i++){
					var installCount=records[i].get('installCount');
					if(installCount)sum+=installCount;
				}
				return sum;
			}},
			
			{header:'同步数',dataIndex:'installSyncCount',flex:1,summaryType: function(records) {
				var sum=0;
				for(var i=0;i<records.length;i++){
					var installSyncCount=records[i].get('installSyncCount');
					if(installSyncCount)sum+=installSyncCount;
				}
				return sum;
			} },
	
			{header:'收益',dataIndex:'appDate',flex:1,
				summaryType: function(records) {
					var sum=0;
					for(var i=0;i<records.length;i++){
						var installSyncCount=records[i].get('installSyncCount');
						var price=records[i].get('price');
						if(installSyncCount && price)sum+=installSyncCount*price;
					}
					return "￥"+sum.toFixed(0);
				} ,
				renderer:function(value,meta,record){
					return "￥"+(record.get('price')*record.get('installSyncCount')).toFixed(0);
				}
			}
		];
	},
	getFormItems:function(){
		var module=this;
		var modulePrefix=this.name+"-";
		var cmp=Ext.getCmp(module.name+"-FilterByApp");
		var v = cmp.getValue();
		var record = cmp.findRecord(cmp.valueField, v);	
		return getFormItems(this,[
		   {fieldLabel:'名称',value:record.get("name"),xtype: 'displayfield'}, 
		   {fieldLabel:'同步日期',value:module.syncDate,xtype: 'displayfield'},
		   {id:modulePrefix+'syncCount',name :'syncCount',fieldLabel :'同步个数',allowBlank :false,value:module.syncCount},
		   {xtype : 'hidden',name : 'app',value:module.appId},
		   {xtype : 'hidden',name : 'syncDate',value:module.syncDate}
       ]);
	},
	showForm:function(appId,syncCount,syncDate){
		var module=this;
		module.appId=appId,
		module.syncDate=syncDate;
		module.syncCount=syncCount;
		var modulePrefix=module.name+"-";
		showForm(this,window.formWidth,170,0,function(obj){
		});
	}
};


 
