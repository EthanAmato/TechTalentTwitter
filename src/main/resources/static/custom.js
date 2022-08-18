/* BAsically saying

let element = document.querySelector(".unfollow_btn");
element.addEventListener("hover",hoverFunction);

function hoverFunction(){
    //removes class of button that makes it primary (blue) and makes it read
    
}

*/
$(document).ready(function() {
    $('.unfollow_btn').hover(function(){
      $(this).removeClass('btn-primary');
      $(this).addClass('btn-danger');
      $(this).html("Unfollow");
    }, function(){
      $(this).html("Following");
      $(this).removeClass('btn-danger');
      $(this).addClass('btn-primary');
    });
  })