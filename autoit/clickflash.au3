#Include <IE.au3>
Local $windowTitle="用户基本信息";
Local $flashId="SWFUpload_0";
WinActivate($windowTitle);

Local $oIE =_IEAttach($windowTitle);
$parwin=$oIE.document.parentwindow;
;_HideScroll($oIE)
Local $uploadButton = _IEGetObjById($oIE,$flashId)
$x=_IEPropertyGet($uploadButton,"screenx")
$y=_IEPropertyGet($uploadButton,"screeny")
$scrollX=Execute('$parwin.document.documentElement.scrollLeft')
$scrollY=Execute('$parwin.document.documentElement.scrollTop')
;_IEAction($uploadButton,"focus")
MouseClick("left",$x-$scrollX,$y-$scrollY);


;$parwin=$oIE.document.parentwindow;
;$q=$parwin.execscript("var q=document.documentElement.scrollTop  ;alert(q)")
;ConsoleWrite($q);

;ConsoleWrite(Execute('$parwin.document.documentElement.scrollTop'));


;(0,"Screen",_IEPropertyGet($uploadButton,"screenx")&"/"&_IEPropertyGet($uploadButton,"screeny")&"\n"&_IEPropertyGet($uploadButton,"browserx")&"/"&_IEPropertyGet($uploadButton,"browsery"))
;MsgBox(0,"Test",_IEPropertyGet($uploadButton,"innerhtml"))
;MsgBox(0,"", $uploadButton.offsetParent);


Func _HideScroll($Temp_Object)
Local $hText = 'var temp_h1 = document.body.clientHeight;'& _
'var temp_h2 = document.documentElement.clientHeight;'& _
'var isXhtml = (temp_h2<=temp_h1&&temp_h2!=0)?true:false;'& _ 
'var htmlbody = isXhtml?document.documentElement:document.body;'& _
'htmlbody.style.overflow = "hidden";'
$Temp_Object.document.parentwindow.execscript($hText,"javascript")
EndFunc