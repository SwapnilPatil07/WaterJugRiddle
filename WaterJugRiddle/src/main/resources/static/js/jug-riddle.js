  
  /* Add custom validator to allow only integer and not float or mix of integer and text */
  Validator.prototype.mustNotContainCharacter = function () {
	  if(this.val.match(/^\d+$/)){
	        return true;
	  }	    
	  return false;
  };
	
  /* Initialize validator */
  $(document).on('blur', '[data-validator]', function(){
	  new Validator($(this));
  });
  
  $('input').on('change', function(){
	  $(".alert").alert('close');
  });
  
  /*Validate form fields*/ 
  function validateForm(){
	  var isValidForm = true;
	  if(!$('#jugZ').hasClass('is-valid')){
		  isValidForm=false;
		  if(!$('#jugZ').hasClass('is-invalid')){
			  $('#jugZ').addClass('is-invalid');
	    	  $('#jugZ-div').addClass('invalid-feedback');
	    	  $('#jugZ-label').addClass('text-danger');
	    	  if ($('#jugZ-div').data('html') !== undefined) {
	    		  $('#jugZ-div').data('html', $('#jugZ-div').html());
	          }
	    	  $('#jugZ-div').html("The 'jug Z' field is required");
		  }    	 
      }
	  
	  if(!$('#jugY').hasClass('is-valid')){
		  isValidForm=false;
		  if(!$('#jugY').hasClass('is-invalid')){
			  $('#jugY').addClass('is-invalid')
	    	  $('#jugY-div').addClass('invalid-feedback');
	    	  $('#jugY-label').addClass('text-danger');
	    	  if ($('#jugY-div').data('html') !== undefined) {
	    		  $('#jugY-div').data('html', $('#jugY-div').html());
	          }
	    	  $('#jugY-div').html("The 'jug Y' field is required");
		  }    	
      }
	  
	  if(!$('#jugX').hasClass('is-valid')){
		  isValidForm=false;
		  if(!$('#jugX').hasClass('is-invalid')){
			  $('#jugX').addClass('is-invalid');
	    	  $('#jugX-div').addClass('invalid-feedback');
	    	  $('#jugX-label').addClass('text-danger');
	    	  if ($('#jugX-div').data('html') !== undefined) {
	    		  $('#jugX-div').data('html', $('#jugX-div').html());
	          }
	    	  $('#jugX-div').html("The 'jug X' field is required");
		  }    
      }
	  
	  return isValidForm;
  }
  
  /*
   *Form submit 
   * Step 1 - Validate all fields
   * Step 2 - Disable button and show spinner
   * Step 3 - Submit ajax request
   * Step 4 - Attached thymeleaf fragement from response to page 
   * */
  $('#riddle-form').on('submit', function(event) {
	  event.preventDefault();
      var $submit = $('#getSteps');            
      var isValid = validateForm();
      
      if(isValid){
    	  var loadingText = "<span class='fas fa-spinner fa-spin'></span> Getting Steps ...";
    	  $submit.prop('disabled', true);        
    	  $('#instructionMsg').fadeOut();
          if ($submit.html() !== loadingText) {
        	  $submit.data('original-text', $submit.html());
        	  $submit.html(loadingText);
          }

          setTimeout(function() {
        	  $submit.html($submit.data('original-text'));
              $submit.prop('disabled', false);
          }, 5000);	  
          
          $.ajax({
      	    type:'post',
      	    contentType: 'application/json; charset=utf-8',
      	    dataType: 'html',
      	    data: JSON.stringify({
              jugX: $('#jugX').val(),
              jugY: $('#jugY').val(),
              jugZ: $('#jugZ').val()
      	    }),
      	    url:"/getSteps",
      	    success: function(result){
      	       $submit.html($submit.data('original-text'));
      	       $submit.prop('disabled', false);
      	       $('#steps').html(result);
      	    },
      	    error: function () {
      	    	$submit.html($submit.data('original-text'));
       	        $submit.prop('disabled', false);
      	    	$('#steps').html('<div class="row justify-content-center">'
      	    			+ '<label class="text-danger"> Error occurred.' 
      	    			+' Please try again </label></div>');
            },
        });
      }     
  });

  
