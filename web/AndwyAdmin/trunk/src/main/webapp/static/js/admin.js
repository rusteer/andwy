window.context="";

var entityStatusList=['Y','N'];
window.formWidth=720;

var getWidth=function(){
	var width=window.screen.width-45;
	if(width<1000) width=1000;
	return width;
};
window.windowWidth=getWidth();
Ext.define('Task', {extend: 'Ext.data.Model',idProperty: 'id',fields: [ 
	{name: 'installSyncCount', type: 'int'},
    {name: 'status', type: 'string'}
]});

Ext.tip.QuickTipManager.init();
var groupFeature={id: 'group', ftype: 'groupingsummary', groupHeaderTpl: '{status}', hideGroupedHeader: false, enableGroupingMenu: true};
var activeHanlder=function(tab){
	document.getElementById("tabId").value=tab.id;
};
var loadData=function(cmpId){
	try{
		if(win) win.close();
	}catch(err){}
	if(!window.moduleLoad) window.moduleLoad={};
	if(!window.moduleLoad[cmpId]){
		eval(cmpId+".refresh();");
		window.moduleLoad[cmpId]=true;
	}
};

var getList=function(module,callBack){
	var url=module.serverUrl;
	if(module.composeListParameters){
		url=url+"?"+module.composeListParameters();
	}
	Ext.Ajax.request({url:url,method:"GET",success : function(response) {
		callBack(Ext.decode(response.responseText));
	}});
};

var remove=function(module,id){
	var serverUrl=module.serverUrl+"/"+id;
	Ext.MessageBox.confirm('警告', '确认要删除吗?', function(btn){
		if("yes"==btn)  {
			 Ext.Ajax.request({
					url:serverUrl,
					method:"DELETE",
					success : function(response) {
						module.refresh();
					}
			 });			
		}
	});
};

var getGrid=function(module,moduleViewConfig){
	var store= Ext.create('Ext.data.Store', { model: 'Task',sorters: [{property: 'status', direction: 'desc'},{property: 'id', direction: 'asc'}],groupField: 'status'});
	var dockList=["-",{text: '刷新',handler:function(){module.refresh();}},"-"];
	if(!(module.showEditFormFlag===false)){
		dockList.push({text: '添加'+module.displayName,handler: function(){module.showForm(0)}});
		dockList.push("-");
	}
	if(module.getDockItems) {
		var items=module.getDockItems();
		for(var i in items){
			dockList.push(items[i]);
		}
	}
	var gridProp={
		id:module.name,
		dockedItems: [{ dock: 'top', xtype: 'toolbar', items:dockList}],
		width: '100%',
		frame: true, 
		//columnLines:true,
		title:module.displayName,
		iconCls: 'icon-grid',
		store:store,
		listeners: {
			activate: activeHanlder,
			rowcontextmenu: function(grid, index, event) {
		        alert(1);
			}
		},
		//viewConfig: viewConfig,
		features: [groupFeature],
		selModel: Ext.create('Ext.selection.Model', { listeners: {} }),
    	columns:module.getColumns()
    };
	if(moduleViewConfig) gridProp.viewConfig=moduleViewConfig;
	return Ext.create('Ext.grid.Panel', gridProp); 
 };
 
var getColumns=function(module,columns){
	var result=[];
	result.push({text: 'ID',dataIndex: 'id',  width: 50,
		summaryType: 'count',
		summaryRenderer: function(value, summaryData, dataIndex) {
			return value+"个";
		},
		renderer:function(value, metaData, record){
			return "<a href=\"javascript:"+module.name+".showForm("+record.get('id')+")\">"+value+"</a>";
		}
	});
	for(var i in columns){
		result.push(columns[i]);
	}
	if(window.isSuper)  result.push({header: '操作', width: 50,  sortable: false, dataIndex: 'actions',renderer:function(value, metaData, record){
		return "<a href=\"javascript:"+module.name+".remove("+record.get('id')+")\">删除</a>";
	}});
	result.push({header: '状态', dataIndex: 'status',width: 50});
	return {items:result,defaults:{sortable: true,align:"left"}};
};

var getFormItems=function(module,items){
	var result=[];
	for(var i in items){
		result.push(items[i]);
	}
	var modulePrefix=module.name+"-";
	result.push({id: modulePrefix+'id',xtype :'hidden',name :'id',value:0});
	result.push({id: modulePrefix+'createTime', xtype : 'hidden',name : 'createTime'});
	result.push({id: modulePrefix+'updateTime', xtype : 'hidden',name : 'updateTime'});
	result.push({id: modulePrefix+'status',name : 'status',fieldLabel:'状态',xtype:'checkbox', allowBlank:true,inputValue:'E',checked:true});
	return [{ xtype : 'container', layout : 'fit', margin : '0 0 10', items : {xtype : 'fieldset', flex : 1, defaultType : 'textfield', layout : 'column',defaults : {anchor : '100%',hideEmptyLabel : true},items:result }}];
	//return [{ xtype : 'fieldset', layout : 'fit', margin : '0 0 10', items :result}];
};

var refresh=function(module,callBack){
	var loading = new Ext.LoadMask(Ext.get(Ext.getBody()),{msg : 'Loading...',removeMask : true});	
	loading.show();
	module.getList(function(list){
		Ext.getCmp(module.name).getStore().loadData(list);
		loading.hide();
		if(callBack) callBack();
	});
};

var save=function(module,postData){
	if(window.fp.getForm().isValid()){
		 var obj=postData?postData:window.fp.getForm().getValues();
		 Ext.Ajax.request({
                url:obj.id>0?(module.serverUrl+"/"+obj.id):module.serverUrl,
	        method: obj.id>0? "PUT":"POST",
                waitMsg: "Please wait while saving",
                waitTitle: "Please wait",
                headers: {'Content-Type': 'application/json'},
                params: Ext.encode(obj),
		    success:function(){
		    	win.close();
		    	module.refresh();
		    },
		    failure:function(){alert("Save Failed"); }
        });			
	}	
};

var showForm=function(module,formWidth,formHeight,editId,getEntityDataCallBack,list1,list2,list3,list4,list5,list6,list7){
	try{ win.close();}catch(err){}	
	window.fp = Ext.create('Ext.FormPanel', { border : false, height : "100%", buttons : [ { text : '保存', handler : function(){module.save();}}], frame : true, fieldDefaults : {labelWidth : 100,width:650}, width : '100%',items : module.getFormItems(list1,list2,list3,list4,list5,list6,list7)});
	window.win = Ext.create('Ext.window.Window', { title : ((editId==0)?'添加':"更新")+module.displayName, constrain : true, autoScroll : true, layout : 'fit', width : formWidth, height : formHeight, minWidth : formWidth, minHeight : formHeight, layout : 'fit', plain : true});
	window.win.show();
	win.add(fp);
	if(module.afterFormLoaded) module.afterFormLoaded();	
	if(editId>0){
		var url=module.serverUrl+'/'+editId
		Ext.Ajax.request({ url :url,method:"GET", success : function(response) {
			var obj=Ext.decode(response.responseText);
			getEntityDataCallBack(obj);
			var modulePrefix=module.name+"-";
			Ext.getCmp(modulePrefix+'id').setValue(obj.id);
			Ext.getCmp(modulePrefix+'status').setValue(obj.status);
			Ext.getCmp(modulePrefix+'updateTime').setValue(obj.updateTime);
			Ext.getCmp(modulePrefix+'createTime').setValue(obj.createTime);
		}});  					
	} 
};

Ext.onReady(function(){
	window.loadingFinished=false;
	var tabId=document.getElementById("tabId").value;
	if(window.location.hash){
		tabId=window.location.hash.substring(1);
	}
	Ext.History.init();
	window.tokenDelimiter = ':';	
	window.tabPanel=Ext.createWidget('tabpanel', {tabPosition: 'top',width:window.windowWidth,renderTo: 'reportGrid',defaults :{bodyPadding: 10},listeners: {tabchange: function(tabPanel, tab) {
		if(window.loadingFinished){ 
			Ext.History.add(tab.id);
			loadData(tab.id);	
		}
    }}});
	window.tabPanel.add(developer.getGrid());
	window.tabPanel.add(market.getGrid());
	window.tabPanel.add(marketAccount.getGrid());
	window.tabPanel.add(product.getGrid());
	window.tabPanel.add(package.getGrid());
	window.tabPanel.add(app.getGrid());
	//window.tabPanel.add(category.getGrid());
	window.tabPanel.add(appCount.getGrid());
	window.tabPanel.add(config.getGrid());
	window.tabPanel.add(rank.getGrid());
	window.loadingFinished=true;
	if(!tabId) tabId='app';
	window.tabPanel.setActiveTab(Ext.getCmp(tabId));
	loadData(tabId);
});