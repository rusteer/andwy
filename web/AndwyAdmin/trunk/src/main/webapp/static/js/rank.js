var rank={
	serverUrl:window.context+'/api/v1/rank',
	displayName:"刷量",
	name:"rank",
	runingTasks:{},
	save:function(){save(this);},
	getGrid: function(){return getGrid(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){refresh(this);this.runingTasks={};},
	getFields:function(){
		return [
	        {label:'产品名称',name:'name',allowBlank:false,flex:true},
	        {label:'详情链接',name:'detailUrl',width:300,allowBlank:false,excludeInGrid:true},
	        {label:'下载链接',name:'downloadUrl',allowBlank:false,excludeInGrid:true} ,
	        {label:'下载超时',name:'downloadTimeout',width:70,allowBlank:false},
	        {label:'下载间隔',name:'downloadInterval',width:70,allowBlank:false},
		];
	},
	doRank:function(rankObj){
		var module=this;
		if(!module.runingTasks[rankObj.id]) return;
		//alert(rankObj.downloadUrl)
		var count=4;
		while((count--)>0){
			Ext.Ajax.request({url:rankObj.downloadUrl,method:"GET",timeout:rankObj.downloadTimeout});
		}
		window.setTimeout(function(){module.doRank(rankObj)},rankObj.downloadInterval);
	},
	checkRank: function(cbox){
		var module=this;
		var rankId=cbox.value;
		module.runingTasks[rankId]=cbox.checked;
		if(cbox.checked){
			var id=cbox.value;
			Ext.Ajax.request({url:module.serverUrl+"/"+id,method:"GET",success : function(response) {
				var rankObj=Ext.decode(response.responseText);
				module.doRank(rankObj);
			}});			
		} 
	},
	 getColumns:function(){
		 var fields=this.getFields();
		var arrayList=[];
		for(var i=0;i<fields.length;i++){
			var field=fields[i];
			if(field.excludeInGrid) continue;
			var obj={header: field.label,dataIndex:field.name, width: field.width};
			if(field.flex) obj.flex=1;
			arrayList.push(obj);
		} 	
		arrayList.push({header: '刷量', width:50,renderer: function (value, meta, record) {return '<input type="checkbox" value="'+record.data.id+'" onchange="rank.checkRank(this)"/>';}});
		arrayList.push({header:'详情链接',flex:1,renderer:function (value,meta,record) {return '<a href="'+record.data.detailUrl+'" target="_blank"/>查看'+record.data.name+'</a>'}});
		arrayList.push({header: '下载链接',flex:1,renderer: function (value, meta, record) {return '<a href="'+record.data.downloadUrl+'" target="_blank"/>下载</a>';}});
		return getColumns(this,arrayList);
	},
	getFormItems:function(){
		var modulePrefix=this.name+"-";
		var arrayList=[];
		var fields=this.getFields();
		for(var i=0;i<fields.length;i++){
			var field=fields[i];
			var obj={id:modulePrefix+field.name,name:field.name,fieldLabel:field.label,allowBlank:field.allowBlank};
			arrayList.push(obj);
		}
		return getFormItems(this,arrayList);
	},
	showForm:function(entityId){
		var module=this;
		var modulePrefix=module.name+"-";
		showForm(this,window.formWidth,240,entityId,function(obj){
			var fields=module.getFields();
			Ext.each(module.getFields(),function(field){
				Ext.getCmp(modulePrefix+field.name).setValue(obj[field.name]);
			});
		});
	}
};


 
