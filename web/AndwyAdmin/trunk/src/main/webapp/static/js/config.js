var config={
	serverUrl: window.context+'/api/v1/client/config',
	displayName:"广告设置",
	name:"config",
	save:function(){save(this);},
	getGrid: function(){return getGrid(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){refresh(this);},
	levelList:[{id:1,name:"全局"},{id:2,name:"市场"},{id:3,name:"产品"},{id:4,name:"软件包"}],
	recommendTypeList:[{id:1,name:"单一列表"},/*{id:2,name:"应用/游戏"},*/{id:2,name:"多分类"}],
	pushTypeList:[{id:1,name:"显示应用详情"},{id:2,name:"预先下载APK"},{id:3,name:"点击安装"},{id:4,name:"直接安装"}],
    pushStrategyList:[{id:1,name:"概率推送"},{id:2,name:"顺序推送"}],
	getColumns:function(){
		var module=this;
		 return getColumns(this,[
			{header:'名称',dataIndex:'name',flex:1},
			{header:'级别',dataIndex:'level',flex:1,renderer: function (value){return value>0?module.levelList[value-1].name:'';}},
			{header:'主机1',dataIndex:'host1',flex:1},
			{header:'主机2',dataIndex:'host2',flex:1},
			{header:'开启推送',dataIndex:'enablePush',flex:1},
			{header:'推送开始时间',dataIndex:'pushStartHour',flex:1},
			{header:'推送结束时间',dataIndex:'pushEndHour',flex:1},
			{header:'广告推送间隔',dataIndex:'devicePushInterval',flex:1},
			{header:'应用推送间隔',dataIndex:'appPushInterval',flex:1},
			{header:'上行推送间隔',dataIndex:'pushRequestInterval',flex:1},
			{header:'推送类型',dataIndex:'pushType',flex:1,renderer: function (value){return value>0?module.pushTypeList[value-1].name:'';}},
            {header:'推送策略',dataIndex:'pushStrategy',flex:1,renderer: function (value){return value>0?module.pushStrategyList[value-1].name:'';}}
			//{header:'推荐:开启功能',dataIndex:'enableRecommend',flex:1},
			//{header:'推荐:上行间隔',dataIndex:'recommendRequestInterval',flex:1},
			//{header:'推荐:过期时间',dataIndex:'recommendExpireTime',flex:1},
			//{header:'推荐:列表类型',dataIndex:'recommendType',flex:1,renderer: function (value){return value>0?module.recommendTypeList[value-1].name:'';}}
		]);
	},
	getFormItems:function(){
		var module=this;
		var modulePrefix=this.name+"-";
		return getFormItems(this,[
  	       {id:modulePrefix+'name',fieldLabel:'名称',name:'name',flex:1},
  	       {id:modulePrefix+'host1',fieldLabel:'主机1',name:'host1',flex:1},
  	       {id:modulePrefix+'host2',fieldLabel:'主机2',name:'host2',flex:1},
  	       {id:modulePrefix+'level',name:'level', fieldLabel:'级别',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:module.levelList})},
  	       {xtype:'fieldset',title: '推送',defaultType:'textfield',layout: 'anchor',defaults: {anchor: '100%',width:630,labelWidth : 80},items :[ 
	  	       {id:modulePrefix+'enablePush', fieldLabel:'开启', xtype:'checkbox',  name : 'enablePush', allowBlank : true,inputValue:'Y'},
	  	       {id:modulePrefix+'devicePushInterval',fieldLabel:'广告推送间隔',name:'devicePushInterval'},
	  	       {id:modulePrefix+'appPushInterval',fieldLabel:'应用间隔',name:'appPushInterval'},
	  	       {id:modulePrefix+'pushRequestInterval',fieldLabel:'上行间隔',name:'pushRequestInterval'},
	  	       {id:modulePrefix+'pushStartHour',fieldLabel:'开始时间',name:'pushStartHour'},
	  	       {id:modulePrefix+'pushEndHour',fieldLabel:'结束时间',name:'pushEndHour'},
	  	       {id:modulePrefix+'pushType',name:'pushType', fieldLabel:'推送类型',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:module.pushTypeList})},
               {id:modulePrefix+'pushStrategy',name:'pushStrategy', fieldLabel:'推送策略',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:module.pushStrategyList})}
  	      ]},
  	      {xtype:'fieldset',title: '推荐列表',defaultType:'textfield',layout: 'anchor',defaults: {anchor: '100%',width:630,labelWidth : 80},items :[ 
	  	       {id:modulePrefix+'enableRecommend', fieldLabel:'开启', xtype:'checkbox',name :'enableRecommend',allowBlank:true,inputValue:'Y'  },
	  	       {id:modulePrefix+'recommendRequestInterval', fieldLabel:'上行间隔',name :'recommendRequestInterval',allowBlank:true },
	  	       {id:modulePrefix+'recommendExpireTime', fieldLabel:'过期时间',name :'recommendExpireTime',allowBlank:true },
	  	       {id:modulePrefix+'recommendType',name:'recommendType', fieldLabel:'列表类型',xtype:'combo',forceSelection: true,editable:false,displayField:'name',valueField:'id',queryMode:'local',store:Ext.create('Ext.data.Store', {fields : ['id','name'],data:module.recommendTypeList}) }
  	     ]}
       ]);	
	},
	showForm:function(developId){
		var module=this;
		var modulePrefix=module.name+"-";
		showForm(this,window.formWidth,600,developId,function(obj){
			Ext.each(["name","host1","host2","level",//Global
			          "enablePush","devicePushInterval","appPushInterval","pushRequestInterval",'pushStartHour','pushEndHour','pushType','pushStrategy',//Push
			          "enableRecommend","recommendRequestInterval","recommendType","recommendExpireTime"//Recommend
			          ],function(item){
				Ext.getCmp(modulePrefix+item).setValue(obj[item]);
			});
		});
	}
};


 
