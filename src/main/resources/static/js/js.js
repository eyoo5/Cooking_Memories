
//edit recipe and create new recipe dynamic ingredients list making:
   document.addEventListener("DOMContentLoaded", function() {
   const addIngredientBtn = document.getElementById('addIngredient');
   const ingredientsContainer = document.getElementById('ingredientsContainer');
   let ingredientInput = document.getElementById('ingredient');
   let ingredientsList = []; // Track added ingredients

       //adding existing ingredients to list:
       if(ingredientsContainer.hasChildNodes()){
           ingredientsContainer.childNodes.forEach(function(childNode){
               if(childNode.nodeName === "li"){
                   // let deleteButton = document.createElement('button')
                   // deleteButton.setAttribute('class','deleteButton')
                   // deleteButton.innerText= "delete";
                   // childNode.appendChild(deleteButton)
                   ingredientsList.push(childNode.textContent.trim())
               }
           });


           // Query all <li> elements inside ingredientsContainer
           const listItems = ingredientsContainer.querySelectorAll('li');
           // Iterating through each <li> element and collecting text content
           listItems.forEach(function(li) {
               ingredientsList.push(li.textContent.trim());
           });

           renderIngredients();

       }

    addIngredientBtn.addEventListener('click', function(event) {
    event.preventDefault(); // Prevent form submission

    let newIngredient = ingredientInput.value.trim();
    if (newIngredient !== '') {
    ingredientsList.push(newIngredient); // Add ingredient to list
    renderIngredients(); // Update displayed ingredients
    ingredientInput.value = ''; // Clear input field
}
});

    function renderIngredients() {
        //clear list in container:
        ingredientsContainer.innerHTML=" ";
    ingredientsList.forEach(function(ingredient) {
    let ingredientItem = document.createElement('li');
    ingredientItem.setAttribute('class','ingredient_li')
    ingredientItem.textContent = ingredient;
    ingredientsContainer.appendChild(ingredientItem);

    //add delete buttons to each li being generated
    let deleteButton = document.createElement('button')
        deleteButton.setAttribute('class','deleteButton')
        deleteButton.innerText= "delete";
        ingredientItem.appendChild(deleteButton)


        //delete button functionality:
            deleteButton.addEventListener('click', function() {
            // Remove the list item when delete button is clicked
            ingredientItem.remove();
            // Update ingredientsList by filtering out the deleted ingredient
            ingredientsList = ingredientsList.filter(item => item !== ingredient);
        });
});
}



   // Form submission handling for ingredients (adding to form on submit)
   const recipeForm = document.getElementById('recipePage');
   recipeForm.addEventListener('submit', function(event) {
       // Adding ingredients list to form data
       ingredientsList.forEach(function(ingredient, index) {
           let hiddenInput = document.createElement('input');
           hiddenInput.type = 'hidden';
           hiddenInput.name = 'ingredients[' + index + ']'; // Array notation for Spring MVC binding
           hiddenInput.value = ingredient;
           recipeForm.appendChild(hiddenInput);
       });
   });

   });


//open delete modal for delete book and delete page
document.getElementById('showModalBtn').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.show();
});

document.getElementById('xButton').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.hide();
});

document.getElementById('closeBtn').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.hide();
});



//open upload image modal
document.getElementById('showUploadModalBtn').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('uploadModal'));
    myModal.show();
});

document.getElementById('closeButton').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.hide();
});

document.getElementById('cancelBtn').addEventListener('click', function () {
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    myModal.hide();
});

//file upload validations
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('uploadForm');
    const fileInput = document.getElementById('file');
    const errorContainer = document.getElementById('errorContainer');

    form.addEventListener('submit', function(event) {
        let file = fileInput.files[0];
        const maxSize = 2 * 1024 * 1024; // 2MB in bytes

        if (file && file.size > maxSize) {
            event.preventDefault(); // Prevent the form submission
            errorContainer.innerHTML = 'File size exceeds the limit (2MB)';
        } else if(file.size === 0){
            errorContainer.innerHTML = "Please select a photo to upload."
        } else{
            errorContainer.innerHTML = ''; // Clear any previous error messages
        }

    });

        fileInput.addEventListener('click',function(event){
            errorContainer.innerHTML=" ";
        })

});
