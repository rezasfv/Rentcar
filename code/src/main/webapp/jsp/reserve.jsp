<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reserve Car Form</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <script src="https://js.stripe.com/v3/"></script>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.2/css/all.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/reserve.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/footer.css" type="text/css" rel="stylesheet">
</head>

<!-- Include the header -->
<c:import url="header.jsp" />

<body>
  <%
      response.setHeader("cache-control","no-store");
      if (session.getAttribute("userFullName") == null) {
          response.sendRedirect(request.getContextPath()+"/login");
      }
  %>
    <div id="loading-overlay">
      <div class="spinner"></div>
    </div>
    <div class="payment-container">
       <div class="reserve-info d-flex flex-column m-sm-5 p-2 px-sm-5 container-sm">
            <div class="container d-flex flex-column">
                <p>Dear <b><c:out value="${userFullName}"/></b>,</p>
                <p>Please check the car details and the dates, then proceed with the payment to secure your reservation.</p>
            </div>
            <div class="container d-flex flex-column" id="reservation-table">
                <div class="row">
                    <div class="col border text-danger bg-light">
                      License Plate
                    </div>
                    <div class="col border bg-light">
                      <c:out value="${availableCar.getLicensePlate()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger">
                      Capacity
                    </div>
                    <div class="col border">
                       <c:out value="${availableCar.getCapacity()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger bg-light">
                      Model Name
                    </div>
                    <div class="col border bg-light">
                       <c:out value="${availableCar.getModelName()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger">
                      Category
                    </div>
                    <div class="col border">
                       <c:out value="${availableCar.getCategory()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger bg-light">
                      Brand Name
                    </div>
                    <div class="col border bg-light">
                       <c:out value="${availableCar.getBrandName()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger">
                      Start Date
                    </div>
                    <div class="col border">
                       <c:out value="${availableFromDate}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col border text-danger bg-light">
                      End Date
                    </div>
                    <div class="col border bg-light">
                       <c:out value="${availableToDate}"/>
                    </div>
                </div>

            </div>
       </div>
      <form id="payment-form" class='payment-form'>
        <div class="row d-flex flex-column justify-content-end p-3 ">
          <div class="col font-italic font-weight-bold" style='color:#eee114;'>
            Total Amount
          </div>
          <div class="col font-weight-bold">
             <c:out value="${amount}"/> Euros
          </div>
        </div>
        <div id="card-element">
            <!-- A Stripe Element will be inserted here. -->
        </div>
        <div id="error" class="container my-3">
            <!-- Error list will be inserted here -->
         </div>
        <!-- Used to display form errors. -->
        <div id="card-errors" role="alert"></div>

        <button class="btn btn-primary" id="submit">Pay</button>
      </form>
    </div>
</body>

<!-- Include the footer -->
<c:import url="footer.jsp" />

<script>
  function showLoadingOverlay() {
    document.getElementById('loading-overlay').style.display = 'flex';
  }

  function hideLoadingOverlay() {
    document.getElementById('loading-overlay').style.display = 'none';
  }
  function createErrorList(result) {
    var errorListHTML = '<div class="row">';

    // Iterate over object properties
    for (const key in result) {
      if (result.hasOwnProperty(key)) {
        // Create a column for each key-value pair
        errorListHTML += `
          <div class="col text-danger">${result[key]}</div>
        `;
      }
    }

    errorListHTML += '</div>'; // Close the row
    return errorListHTML;
  }


const stripe = Stripe('pk_test_51PJzXqRxoQCZUBajcLUxdVxTwrqs6s1nR8myTgu3RYCpWBUT5BnSzwRA54wGGoprbhsBxH3CrdMdxj5LTS4bNesM00gF8dvOsq');

// Create an instance of Elements.
const elements = stripe.elements();

// Create an instance of the card Element.
const card = elements.create('card');

// Add an instance of the card Element into the `card-element` div.
card.mount('#card-element');
const form = document.getElementById('payment-form');
form.addEventListener('submit', async (event) =>
  {
    event.preventDefault();
    showLoadingOverlay();
    // Disable the submit button to prevent multiple clicks
    document.getElementById('submit').disabled = true;

    await fetch('/rentcar/create-payment-intent', {
        method: 'POST',
    })
    .then(response => response.json())
    .then(data => {
        stripe.confirmCardPayment(data.client_secret, {
            payment_method: {
                card: card,
                billing_details: {
                    name: "<c:out value="${userFullName}"/>" // Embedding the user's name
                }
            }
        }).then(function(result) {
            if (result.error) {
                // Display error.message in your UI.
                hideLoadingOverlay()
                document.getElementById('card-errors').textContent = result.error.message;
                document.getElementById('submit').disabled = false;
            } else {
                // The payment has been processed
                // send request to make rentalTransaction in DB
                showLoadingOverlay();
                if (result.paymentIntent.status === 'succeeded') {
                     fetch('/rentcar/reserve', {
                       method: 'POST',
                       headers: {'Content-Type': 'application/json'},
                       body: JSON.stringify({
                                paymentId: result.paymentIntent.id
                            })
                       })
                       .then(result => result.json())
                       .then(result => {
                                console.log('after reserve post: result=',result)
                                if(result.status === "success"){
                                    window.location.href= "/rentcar/payment-success";
                                }else{
                                    document.getElementById('error').innerHTML = createErrorList(result);
                                }
                       })
                       .finally(() => {
                           hideLoadingOverlay();
                       });
                }
            }
        });
    });
  }
)

</script>


<!-- <script src="${pageContext.request.contextPath}/js/reserve.js"></script> -->
<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>
