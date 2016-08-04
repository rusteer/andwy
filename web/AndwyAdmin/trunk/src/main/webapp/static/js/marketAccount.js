var marketAccount={
	serverUrl:window.context+'/api/v1/mc',
	displayName:"账户",
	name:"marketAccount",
	save:function(){save(this);},
	getGrid: function(){
		var module=this;
		var grid= getGrid(module);
		var marketComb=Ext.getCmp(module.name+"FilterByMarketId");
		var developerComb=Ext.getCmp(module.name+"FilterByDeveloperId");
		marketComb.on('change',function(){module.filterChanged();});
		developerComb.on('change',function(){module.filterChanged();});		
		return grid;
	},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){
		var module=this;
		var f1=function(){
			refresh(module,function(){
				module.filterChanged();		
			});			
		}
		if(!module.filterLoaded){
			market.getList(function(marketList){
				developer.getList(function(developerList){
					Ext.getCmp(module.name+"FilterByDeveloperId").getStore().loadData(developerList);
					Ext.getCmp(module.name+"FilterByMarketId").getStore().loadData(marketList);
					module.filterLoaded=true;
					f1();
			});});
		}else{
			f1();
		}
	},
	getDockItems:function(){
		var module=this;
		return [
		    ,{text: '按'+developer.displayName+'过滤:'},{id :module.name+'FilterByDeveloperId',xtype:'combo',lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name']})}
		    ,"-"
		    ,{text: '按'+market.displayName+'过滤:'},{id :module.name+'FilterByMarketId',xtype:'combo',lastQuery:"",displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name']})}
		    ,"-"		    
		   ];
	},
	filterChanged:function(){
		var module=this;
		var gridStore=Ext.getCmp(module.name).getStore();
		var developerComb=Ext.getCmp(module.name+"FilterByDeveloperId");
		var marketComb=Ext.getCmp(module.name+"FilterByMarketId");
		var marketId=marketComb.getValue();
		var developerId=developerComb.getValue();
		//console.log(developerComb);
		gridStore.clearFilter();
		gridStore.filterBy(function(record) {
			var developerIdFilter=function(){
				return !developerId || record.data.developer.id==developerId;
			};	
			var marketIdFilter=function(){
				return !marketId || record.data.market.id==marketId;
			};			
			return developerIdFilter()&&marketIdFilter();
		});
	},	
	getColumns:function(){
		var render=function(value, metaData, record, rowIdx, colIdx, store, view) {
			return value.name;
		};
		return getColumns(this,[
		 {header: '手机市场',dataIndex:"market",width:100,renderer:render},
		 {header: '网址',dataIndex:"market",flex:1,renderer:function(data){return data.url}},
		 {header: '开发者名称',  dataIndex: 'developer',width: 250,renderer:render},
		 {header: '登录账号',  dataIndex: 'loginName',width: 250},
		 {header: '登录密码', width: 70,dataIndex: 'loginPassword',  width: 250},
		]);
	},
	getFormItems:function(marketList,developerList){
		var module=this;
		var modulePrefix=module.name+"-";
		return getFormItems(this,[
		   {fieldLabel:'手机市场',id:modulePrefix+'market',xtype:'combo',forceSelection:true,editable:false,name:'market',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name', 'url'],data:marketList})},
		   {fieldLabel:'手机市场',id:modulePrefix+'developer',xtype:'combo',forceSelection: true,editable:false,name:'developer',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:developerList})},           
	         {fieldLabel:'登录账号',id:modulePrefix+'loginName', name : 'loginName', allowBlank : false }, 
		   {fieldLabel:'登录密码',id:modulePrefix+'loginPassword', name : 'loginPassword', allowBlank:true}        
	     ]);	
	},
	showForm:function(entityId){
		var modulePrefix=this.name+"-";
		var module=this;
		market.getList(function(marketList){
			developer.getList(function(developerList){
				showForm(module,window.formWidth,220,entityId,function(obj){
					Ext.getCmp(modulePrefix+'market').setValue(obj.market.id);
					Ext.getCmp(modulePrefix+'developer').setValue(obj.developer.id);
					Ext.getCmp(modulePrefix+'loginName').setValue(obj.loginName);
					Ext.getCmp(modulePrefix+'loginPassword').setValue(obj.loginPassword);
				},marketList,developerList);
			})
		})
	}
};



