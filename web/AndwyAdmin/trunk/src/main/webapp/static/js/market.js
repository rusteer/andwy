var market={
	serverUrl:window.context+'/api/v1/market',
	displayName:"市场",
	name:"market",
	save:function(){save(this);},
	getGrid: function(){return getGrid(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){refresh(this);},
	 getColumns:function(){
		 return getColumns(this,[
             {header: '名称',  dataIndex: 'name',  width: 150},
             {header: '简称',  dataIndex: 'shortName',  width: 150},
             {header: '连接地址',  dataIndex: 'url',  flex: 1},
			 {header: '广告设置',flex:1,dataIndex:"config",renderer:function(value,meta,record) {
				 	return value?value.name:"";
				 }
			 }             
		]);
	},
	getFormItems:function(configList){
		var modulePrefix=this.name+"-";
		return getFormItems(this,[
		   {id:modulePrefix+'name',name :'name',fieldLabel:'名称',allowBlank :false}, 
		   {id:modulePrefix+'shortName',name :'shortName',fieldLabel:'简称',allowBlank :false},
		   {id:modulePrefix+'url',name :'url',fieldLabel :'连接地址',allowBlank :false},
		   {id:modulePrefix+'config',name:'config',fieldLabel:'广告参数设置',xtype:'combo',forceSelection:true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id','name'],data:configList})}
       ]);	
	},
	showForm:function(developId){
		var modulePrefix=this.name+"-";
		var module=this;
		config.getList(function(configList){
			var filteredConfigList=[];
			for(var i=0;i<configList.length;i++){
				if(configList[i].level==2) filteredConfigList[filteredConfigList.length]=configList[i];
			}				
			showForm(module,window.formWidth,240,developId,function(obj){
				Ext.each(["name","shortName","url" ],function(item){
					Ext.getCmp(modulePrefix+item).setValue(obj[item]);
				});
				if(obj.config) Ext.getCmp(modulePrefix+'config').setValue(obj.config.id);
			},filteredConfigList);
		});
	}
};


 
