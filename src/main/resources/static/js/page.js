
   document.addEventListener("DOMContentLoaded", function() {
   const addIngredientBtn = document.getElementById('addIngredient');
   let ingredientInput = document.getElementById('ingredient');
   let ingredientsList = []; // Track added ingredients

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
    const ingredientsContainer = document.getElementById('ingredientsContainer');
    ingredientsContainer.innerHTML = ''; // Clear previous content

    ingredientsList.forEach(function(ingredient) {
    let ingredientItem = document.createElement('li');
    ingredientItem.textContent = ingredient;
    ingredientsContainer.appendChild(ingredientItem);
});
}



   // Form submission handling for ingredients (adding to form on submit)
   const recipeForm = document.getElementById('recipePage');
   recipeForm.addEventListener('submit', function(event) {
       // Example: Add ingredients list to form data
       ingredientsList.forEach(function(ingredient, index) {
           let hiddenInput = document.createElement('input');
           hiddenInput.type = 'hidden';
           hiddenInput.name = 'ingredients[' + index + ']'; // Array notation for Spring MVC binding
           hiddenInput.value = ingredient;
           recipeForm.appendChild(hiddenInput);
       });
   });

   });

