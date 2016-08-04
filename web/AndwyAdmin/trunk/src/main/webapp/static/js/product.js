var product={
	serverUrl:window.context+'/api/v1/product',
	displayName:"产品",
	name:"product",
	save:function(){save(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){
		var module=this;
		refresh(module,function(){
			module.filterChanged();		
		});	
	},
	batchList:[
        {id:1,name:"01"},{id:2,name:"02"},{id:3,name:"03"},{id:4,name:"04"},{id:5,name:"05"}
        ,{id:10,name:"10"},{id:11,name:"11"},{id:12,name:"12"},{id:13,name:"13"},{id:14,name:"14"},{id:15,name:"15"},
        {id:50,name:"50"},
        {id:51,name:"51"},
        //{id:52,name:"52"},
        //{id:53,name:"53"},
        //{id:54,name:"54"},
        //{id:55,name:"55"},
    ],
	priorityList:[{id:1,name:"A"},{id:2,name:"B"},{id:3,name:"C"},{id:4,name:"D"}],
    projectTypeList:[{id:"source",name:"source"},{id:"binary",name:"binary"}],
    thirdPartyAdsDropdown:[{id:"Y",name:"有"},{id:"N",name:"无"}],
	getColumns:function(){
		var module=this;
		return getColumns(this,[
			{header: '批量号',  dataIndex: 'batchId',width: 40},
			{header: '项目名称',  dataIndex: 'projectName',  width: 90},
			{header: '产品名称',  dataIndex: 'productName',  width: 90},
			{header: '主包名',  dataIndex: 'basePackage',  width: 160},
			{header: '版本',  dataIndex: 'versionDate',  width: 75},
			{header: '版本号',  dataIndex: 'versionCount',  width: 45},
            {header: 'type',  dataIndex: 'projectType',  flex:1},
            {header: 'icon',  dataIndex: 'iconResource',  flex:1},
            {header: 'appName',  dataIndex: 'appNameResource',flex:1},
            //{header: 'otherAds',  dataIndex: 'haveThirdPartyAds',flex:1},
			{header: '优先级',  dataIndex: 'priority',  width: 45,renderer: function (value){return value>0?module.priorityList[value-1].name:'';}},
			{header: "图标",width:40,renderer: function (value, meta, record) {
					 var url=getIconPath(record.data); 
					 return "<a href='"+url+"' target='_blank'><img src='"+url+"' width=13 height=13 /></a>"
		 		}
			 },				
			{header: '友盟ID',  dataIndex: 'umengId',  width: 100},
			 {header: '广告设置',width:100,dataIndex:"config",renderer:function(value,meta,record) {
				 	return value?value.name:"";
				 }
			 },					
			{header: '产品说明',  dataIndex: 'description',flex: 2}
		]);
	},
	getDockItems:function(){
		return [
		    {fieldLabel:'按批次过滤',id:'productFilterByBatchId',xtype:'combo',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:this.batchList})}
		    ,"-"
		    ,{fieldLabel:'按优先级过滤',id :'productFilterByPriority',xtype:'combo',displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:this.priorityList})}
		    ,"-"		    
		];
	},
	filterChanged:function(){
		var module=this;
		var gridStore=Ext.getCmp(module.name).getStore();
		gridStore.clearFilter();
		var batchId = Ext.getCmp("productFilterByBatchId").getValue();
		var priority= Ext.getCmp("productFilterByPriority").getValue();
		gridStore.filterBy(function(record) {
			var batchIdFilter=function(){
				return !batchId || record.get("batchId")==batchId;
			};
			var priorityFilter=function(){
				return !priority || record.get("priority")==priority;
			}
			return batchIdFilter()&&priorityFilter();
		});
		 
	},
	getGrid: function(){
		var module=this;
		var grid= getGrid(module);
		Ext.getCmp("productFilterByBatchId").on('change',function(cmb,record,index){
			module.filterChanged();
		});  
		Ext.getCmp("productFilterByPriority").on('change',function(cmb,record,index){
			module.filterChanged();
		});  		
		return grid;
	},	
	getFormItems:function(configList){
		var modulePrefix=this.name+"-";
        var dateString=Ext.util.Format.date(new Date(),'Ymd');
		return getFormItems(this,[ 
		    {id:modulePrefix+'batchId',name:'batchId', fieldLabel:'批次',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:product.batchList})},
			{id:modulePrefix+'projectName', name : 'projectName', fieldLabel : '项目名称', allowBlank : false }, 
			{id:modulePrefix+'productName', name : 'productName', fieldLabel : '产品名称', allowBlank : false  }, 
			{id:modulePrefix+'basePackage', name : 'basePackage', fieldLabel : '主包名', allowBlank : false},
			{id:modulePrefix+'versionDate', name : 'versionDate', fieldLabel : '版本', allowBlank : false,value:dateString},
			{id:modulePrefix+'versionCount', name : 'versionCount', fieldLabel : '版本号', allowBlank : false,value:1  },

            {id:modulePrefix+'projectType',name:'projectType',value:"binary", fieldLabel:'type',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:this.projectTypeList})},
            {id:modulePrefix+'iconResource', name : 'iconResource', fieldLabel : 'icon', allowBlank : true,value:"icon" },
            {id:modulePrefix+'appNameResource', name : 'appNameResource', fieldLabel : 'name', allowBlank : true,value:"app_name"  },
            {id:modulePrefix+'haveThirdPartyAds',value:'N',name:'haveThirdPartyAds', fieldLabel:'第三方广告',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:this.thirdPartyAdsDropdown})},


			{id:modulePrefix+'priority',value:'1',name:'priority', fieldLabel:'优先级',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:product.priorityList})},
			{id:modulePrefix+'publishInterval', name : 'publishInterval', fieldLabel : '更新周期(天)', allowBlank : false,value:10  },
			{id:modulePrefix+'category1', name : 'category1', fieldLabel : '类别一',  xtype : 'hidden',allowBlank : true,value:"a"  },
			{id:modulePrefix+'category2', name : 'category2', fieldLabel : '类别二',  xtype : 'hidden',allowBlank : true,value:"b"  }, 
			{id:modulePrefix+'category3', name : 'category3', fieldLabel : '类别三',  xtype : 'hidden',allowBlank : true,value:"c"  }, 
			{id:modulePrefix+'language', name : 'language', fieldLabel : '语言', allowBlank : false , xtype : 'hidden',value:"简体中文" }, 
			{id:modulePrefix+'feeType', name : 'feeType', fieldLabel : '收费类型', xtype : 'hidden',allowBlank : false ,value:"免费软件" }, 
			{id:modulePrefix+'config',name:'config',fieldLabel:'广告参数设置',xtype:'combo',forceSelection:true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store',{fields:['id','name'],data:configList})},
			{id:modulePrefix+'umengId',name:'umengId', fieldLabel:'友盟ID', allowBlank:true},
			{id:modulePrefix+'description', name : 'description', fieldLabel : '产品说明', xtype:'textarea',allowBlank : false ,value:"todo" }
		]);	
	},
	showForm:function(entityId){
		var module=this;
		var modulePrefix=module.name+"-";
		config.getList(function(configList){
			var filteredConfigList=[];
			for(var i=0;i<configList.length;i++){
				if(configList[i].level==3) filteredConfigList[filteredConfigList.length]=configList[i];
			}		
			showForm(module,window.formWidth,540,entityId,function(obj){
					Ext.each(['haveThirdPartyAds','projectType','iconResource','appNameResource','umengId','priority',"projectName","productName","basePackage","versionDate","versionCount","batchId","publishInterval","category1","category2","category3","language","feeType","description"],function(item){
						Ext.getCmp(modulePrefix+item).setValue(obj[item]);
					});
					if(obj.config) Ext.getCmp(modulePrefix+'config').setValue(obj.config.id);
			},filteredConfigList);	
		});
	}
};