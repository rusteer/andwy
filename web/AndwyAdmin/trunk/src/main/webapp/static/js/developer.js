var developer={
	serverUrl:window.context+'/api/v1/developer',
	displayName:"开发者",
	name:"developer",
	save:function(){save(this);},
	getGrid: function(){return getGrid(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){refresh(this);},
	getFields:function(){
		return [
	        {label:'名称',name:'name',width:150,allowBlank:false,flex:false},
            {label:'昵称',name:'nickName',width:100,allowBlank:false,flex:false},
	        {label:'简称',name:'shortName',width:100,allowBlank:false,flex:false},
	        {label:'开发者名称',name:'developerName',width:70,allowBlank:false,flex:false},
	        {label:'邮件地址',name:'email',width:250,allowBlank:true,flex:false},
	        {label:'邮箱密码',name:'emailPassword',width:150,allowBlank:true,flex:false},
	        {label:'QQ',name:'qq',width:150,allowBlank:true,flex:true},
	        {label:'手机号码',name:'mobile',width:150,allowBlank:true,flex:false} 
		];
	},
	 getColumns:function(){
		var fields=this.getFields();
		var arrayList=[];
		for(var i=0;i<fields.length;i++){
			var field=fields[i];
			var obj={header: field.label,dataIndex:field.name, width: field.width};
			if(field.flex) obj.flex=1;
			arrayList.push(obj);
		} 
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
		showForm(this,window.formWidth,400,entityId,function(obj){
			var fields=module.getFields();
			Ext.each(module.getFields(),function(field){
				Ext.getCmp(modulePrefix+field.name).setValue(obj[field.name]);
			});
		});
	}
};


 
