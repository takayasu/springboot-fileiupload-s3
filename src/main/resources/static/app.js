
var url = 'http://localhost/aws/upload';
var imageUrl = 'http://localhost/images/';
var dataAwsUrl ='http://cloud-btc.s3.amazonaws.com/org_images/';

var addThumbnail = function(filename){
  console.log("Add:" + filename);
  $('#thumbnail').append('<span><img class="thumbnail-window" data-target="'+filename+'"src="'+imageUrl+filename+'"><br/>'+ filename +'<br/></span>');
  addElementEvent();
};

var success = function(data){
  console.log(data);
  var obj = JSON.parse(data);
  for(var file of obj.fileList){
       setTimeout(addThumbnail, 5000,file);
  }

}

var imageUpload = function(f){
  var formData = new FormData();
    formData.append("files",f);

  var postData = {
    type : "POST",
    dataType : "text",
    data : formData,
    processData : false,
    contentType : false
  };

$.ajax(url,postData).done(function(data){success(data);});
}

var addElementEvent = function(){
  $('.thumbnail-window').on('click',function(){
    
    $('body').append('<div class="modal-overlay"></div>');
    $('.modal-overlay').fadeIn('slow');
    $('body').append('<div class="modal-overlay"></div>');
    
    var datafile = $(this).attr('data-target');
    console.log(datafile);
    
    var dataurl = dataAwsUrl+datafile;
        
    $('#target-img').attr('src',dataurl);
    
    
    $('#datawindow').fadeIn('slow');
    

    $('.modal-overlay').on('click',function(){
      $('#datawindow').fadeOut('slow');
      $('.modal-overlay').fadeOut('slow',function(){
        $('.modal-overlay').remove();
      });
    });
    
    
  });
}

var modalResize = function(){
  var w = $(window).width();
  var h = $(window).height();
  
  $('#datawindow').css({
    'width' : w - 40 +'px','height' : h -40 + 'px'
  });
  
}

$(document).ready(function(){

  $('#drop').on('dragover',function(event){
    event.preventDefault();
  });
  
  $('#drop').on('drop',function(_event){
    var event = _event;
    
    if(_event.originalEvent){
      event = _event.originalEvent;
    }
    
    event.preventDefault();
    var uploadfiles = event.dataTransfer.files;
    for (var i=0; i<uploadfiles.length; i++) {
        imageUpload(uploadfiles[i]);
    }

  });
  
});


