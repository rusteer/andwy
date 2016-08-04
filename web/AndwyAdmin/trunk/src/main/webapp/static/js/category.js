var category={
	serverUrl:window.context+'/api/v1/client/category',
	displayName:"分类",
	name:"category",
	save:function(){save(this);},
	getGrid: function(){return getGrid(this);},
	remove: function(id){remove(this,id);},	
	getList:function(callBack){getList(this,callBack);},
	refresh:function(){refresh(this);},
	getColumns:function(){
		var module=this;
		 return getColumns(this,[
			{header:'名称',dataIndex:'name',flex:1},
			{header:"图标",flex:1,renderer: function (value, meta, record) {
			 	var url= record.data.iconUrl;
			 	var result="<a  href='"+url+"' target='_blank'><img src='"+url+"' width=12 height=12/></a>";
				return result;			 	
		 		}	
			}
		]);
	},
	getFormItems:function(){
		var modulePrefix=this.name+"-";
		return getFormItems(this,[
  	       {id : modulePrefix+'name',fieldLabel:'名称',name:'name'},
	       {id : modulePrefix+'iconUrl',fieldLabel:'图标文件',name : 'iconUrl'},
	       {id : modulePrefix+'uid', xtype : 'hidden',name : 'uid'}
       ]);	
	},
	showForm:function(developId){
		var module=this;
		var modulePrefix=module.name+"-";
		showForm(this,window.formWidth,200,developId,function(obj){
			Ext.each(["name","uid",'iconUrl'],function(item){
				Ext.getCmp(modulePrefix+item).setValue(obj[item]);
			});
			var cmp=Ext.getCmp(modulePrefix+"iconShow");
			if(cmp) cmp.el.dom.src=module.getIconUrl(obj);
		});
	}
};


 
