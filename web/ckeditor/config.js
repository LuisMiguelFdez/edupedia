CKEDITOR.editorConfig = function( config ) {
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'paragraph', groups: [ 'indent', 'blocks', 'list', 'align', 'bidi', 'paragraph' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		'/',
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'About,ShowBlocks,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Iframe,PageBreak,Flash,Smiley,Anchor,Language,BidiRtl,BidiLtr,CreateDiv,Subscript,Superscript,Strike,CopyFormatting,SelectAll,PasteFromWord,Source,Save,NewPage,BGColor';
};