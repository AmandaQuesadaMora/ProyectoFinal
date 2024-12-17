/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#blah')
                    .attr('src', e.target.result)
                    .height(200);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

/* Llamado AJAX para agregar al carrito */
function addCard(formulario) {
    var valor = formulario.elements[0].value;
    var url = '/carrito/agregar';
    url = url + '/' + valor;
    $("#resultsBlock").load(url);
}

function confirmarCompra(event) {
    event.preventDefault(); // Evita el envío del formulario
    alert("¡Compra realizada con éxito! Generando factura...");
    event.target.closest('form').submit(); // Envía el formulario manualmente después del alert
}

