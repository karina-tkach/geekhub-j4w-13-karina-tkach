const stripe = Stripe(publicKey);

window.onload = function () {
    checkStatus();
};

function showLoading() {
    document.getElementById('loading').style.display = 'flex';
}

function hideLoading() {
    document.getElementById('loading').style.display = 'none';
}

async function checkStatus() {
    // Show loading indicator before making the request
    const clientSecret = new URLSearchParams(window.location.search).get(
        "payment_intent_client_secret"
    );

    if (!clientSecret) {
        return;
    }

    const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
        case "succeeded":
            showLoading();
            try {
                const booking = localStorage.getItem('booking');
                const response = await fetch("/save-booking", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: booking,
                });
                localStorage.removeItem('booking');
                if (response.ok) {
                    displayMessage("Success! Payment received.", true);
                } else {
                    displayMessage("Failed to check payment status.", false);
                }
            } catch (error) {
                displayMessage("Error checking payment status: " + error, false);
            } finally {
                hideLoading();
            }
            break;
        case "processing":
            displayMessage("Payment processing. Wait on the page.", false);
            break;
        case "requires_payment_method":
            displayMessage("Payment failed. Please try another payment method.", false);
            break;
        default:
            displayMessage("Something went wrong.", false);
            break;
    }
}

function displayMessage(message, isSuccess) {
    const errorDiv = document.querySelector('.message');
    errorDiv.textContent = message;
    errorDiv.style.backgroundColor = isSuccess ? 'green' : 'red';
}
