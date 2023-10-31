const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

document.querySelectorAll('.btn-primary').forEach(button => {
    button.addEventListener('click', function(event) {
        // event.preventDefault();

        const form = this.closest('form');
        const orderId = form.querySelector('input[name="id"]').value;

        fetch(`/api/admin/cancel-order?id=` + orderId,
            {
                method: "POST",
                headers: {
                    [csrfHeaderName]: csrfHeaderValue,
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                redirect: "follow"
            })
            .then(response => {
                if (response.ok) {
                    console.log('The order has been cancelled');
                    refreshPage()
                } else {
                    console.error('Error canceling order');
                    refreshPage()
                }
                refreshPage()
            })
            .catch(error => {
                console.error('Error executing the request: ' + error);
                refreshPage()
            });
    });
});

function refreshPage() {
    window.location.reload();
}