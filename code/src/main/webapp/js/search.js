/*
    Copyright 2018-2023 University of Padua, Italy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and limitations under the License.

    Author: Luca Pellegrini
    Version: 1.0
    Since: 1.0
*/

document.getElementById("filter").addEventListener("click", updateCarCards);

//Get cars using REST
getCars();

let resourceList;

let brandNames = [];
let categories = [];
let minRentalRate = 0;
let maxRentalRate = 1000;
let minCapacity = 1;
let maxCapacity = 9;

/**
 * Searches for employee above the given salary.
 *
 * @returns {boolean} true if the HTTP request was successful; false otherwise.
 */
function getCars() {

    const url = window.location.href.substring(0,window.location.href.lastIndexOf("/")) + "/rest/car";

    console.log("Request URL: %s.", url)

    // the XMLHttpRequest object
    const xhr = new XMLHttpRequest();

    if (!xhr) {
        console.log("Cannot create an XMLHttpRequest instance.")

        alert("Giving up :( Cannot create an XMLHttpRequest instance");
        return false;
    }

    // set up the call back for handling the request
    xhr.onreadystatechange = function () {
        processResponse(this);
    };

    // perform the request
    console.log("Performing the HTTP GET request.");

    xhr.open("GET", url, true);
    xhr.send();

    console.log("HTTP GET request sent.");
}

/**
 * Processes the HTTP response and writes the results back to the HTML page.
 *
 * @param xhr the XMLHttpRequest object performing the request.
 */
function processResponse(xhr) {

    // not finished yet
    if (xhr.readyState !== XMLHttpRequest.DONE) {
        console.log("Request state: %d. [0 = UNSENT; 1 = OPENED; 2 = HEADERS_RECEIVED; 3 = LOADING]",
            xhr.readyState);
        return;
    }

    const div = document.getElementById("results");

    // remove all the children of the result div, appended by a previous call, if any
    div.replaceChildren();

    if (xhr.status !== 200) {
        console.log("Request unsuccessful: HTTP status = %d.", xhr.status);
        console.log(xhr.response);

        div.appendChild(document.createTextNode("Unable to perform the AJAX request."));

        return;
    }

    console.log(xhr.responseText);

    // parse the response as JSON and extract the resource-list array
    resourceList = JSON.parse(xhr.responseText)["resource-list"];

    for (let i = 0; i < resourceList.length; i++)
    {
        let car = resourceList[i].car;

        if (!brandNames.includes(car["brandname"]))
            brandNames.push(car["brandname"]);
        if (!categories.includes(car["category"]))
            categories.push(car["category"]);
        if (minCapacity > car["capacity"])
            minCapacity = car["capacity"];
        if (maxCapacity < car["capacity"])
            minCapacity = car["capacity"];
        if (minRentalRate > car["rentalrate"])
            minRentalRate = car["rentalrate"];
        if (maxRentalRate < car["rentalrate"])
            maxRentalRate = car["rentalrate"];
    }

    let select;

    select = document.getElementById("brandname");
    brandNames.forEach(function (element, index){
       let option = document.createElement("option");
       option.value = element;
       option.appendChild(document.createTextNode(element));
       select.appendChild(option);
    });

    select = document.getElementById("category");
    categories.forEach(function (element, index){
        let option = document.createElement("option");
        option.value = element;
        option.appendChild(document.createTextNode(element));
        select.appendChild(option);
    });

    console.log("HTTP GET request successfully performed and processed.");

    updateCarCards();

}

function updateCarCards() {

    var selectedList = [];
    resourceList.forEach(function (element, index){
        if ((element.car.brandname===document.getElementById('brandname').options[document.getElementById('brandname').selectedIndex].value || "all"===document.getElementById('brandname').options[document.getElementById('brandname').selectedIndex].value)
           && (element.car.category===document.getElementById('category').options[document.getElementById('category').selectedIndex].value || "all"===document.getElementById('category').options[document.getElementById('category').selectedIndex].value)
           && (element.car.currentstatus===document.getElementById('currentstatus').options[document.getElementById('currentstatus').selectedIndex].value || "all"===document.getElementById('currentstatus').options[document.getElementById('currentstatus').selectedIndex].value)
           && element.car.rentalrate<=document.getElementById('maxrentalrate').value
           && element.car.rentalrate>=document.getElementById('minrentalrate').value
           && element.car.capacity<=document.getElementById('maxcapacity').value
           && element.car.capacity>=document.getElementById('mincapacity').value
           && (element.car.modelname.toLowerCase().indexOf(document.getElementById('modelname').value.toLowerCase()) > -1 || document.getElementById('modelname').value==="")
       )
       {selectedList.push(element);}
    });

    const div = document.getElementById("results");

    div.replaceChildren();

    if (selectedList.length===0)
    {
        let col = document.createElement("div");
        col.classList.add("col-sm-12","p-2","text-center","alert","alert-warning");
        col.role = "alert";
        col.appendChild(document.createTextNode("No car available using the selected parameters"));
        div.appendChild(col); // append the row to the table body
        return;
    }

    let col, card, e, ee, eee;

    for (let i = 0; i < selectedList.length; i++)
    {
        // extract the i-th employee and create a table row for it
        let car = selectedList[i].car;

        //retrive car image from Wikipedia
        imageCar(car["brandname"]+" "+car["modelname"], car["licenseplate"]);

        col = document.createElement("div");
        col.classList.add("col-sm-3","p-2");
        div.appendChild(col); // append the row to the table body

        card = document.createElement("div");
        card.classList.add("card");
        card.id = car["licenseplate"];
        col.appendChild(card); // append the row to the table body

        // create a cell for the badge of the employee
        e = document.createElement("div");
        e.classList.add("card-body");
        card.appendChild(e); // append the cell to the row

        ee = document.createElement("h5");
        ee.classList.add("card-title","px-3");
        ee.appendChild(document.createTextNode(car["brandname"]+" "+car["modelname"]));
        e.appendChild(ee); // append the cell to the row

        ee = document.createElement("h6");
        ee.classList.add("card-subtitle","px-3","mb-2");
        ee.appendChild(document.createTextNode(car["licenseplate"]));
        e.appendChild(ee); // append the cell to the row

        ee = document.createElement("ul");
        ee.classList.add("list-group","list-group-flush");
        e.appendChild(ee); // append the cell to the row

        eee = document.createElement("li");
        eee.classList.add("list-group-item");
        eee.appendChild(document.createTextNode("Capacity: "+car["capacity"]));
        ee.appendChild(eee); // append the cell to the row

        eee = document.createElement("li");
        eee.classList.add("list-group-item");
        eee.appendChild(document.createTextNode("Category: "+car["category"]));
        ee.appendChild(eee); // append the cell to the row

        eee = document.createElement("li");
        eee.classList.add("list-group-item");
        eee.appendChild(document.createTextNode("Rate: "+car["rentalrate"]));
        ee.appendChild(eee); // append the cell to the row

        ee = document.createElement("button");
        ee.classList.add("btn","btn-primary","px-3");
        ee.onclick = () => window.location = "check-availability?licencePlate="+car["licenseplate"];
        ee.appendChild(document.createTextNode("Reserve"));
        e.appendChild(ee); // append the cell to the row

    }

    console.log("Updated Car Cards");

}

function imageCar(param, licenceplate) {

    var url = "https://en.wikipedia.org/w/api.php";

    var params = {
        action: "query",
        prop: "images",
        titles: param,
        format: "json",
        imlimit: 1
    };

    url = url + "?origin=*";
    Object.keys(params).forEach(function (key) {
        url += "&" + key + "=" + params[key];
    });

    // the XMLHttpRequest object
    const xhr = new XMLHttpRequest();

    if (!xhr) {
        console.log("Cannot create an XMLHttpRequest instance.")

        alert("Giving up :( Cannot create an XMLHttpRequest instance");
        return false;
    }

    // set up the call back for handling the request
    xhr.onreadystatechange = function () {
        imageCarResponse(this, licenceplate);
    };

    // perform the request
    //console.log("Performing the HTTP GET request.");

    xhr.open("GET", url, true);
    xhr.send();

    //console.log("HTTP GET request sent.");

}

function imageCarResponse(xhr, licenceplate) {

    // not finished yet
    if (xhr.readyState !== XMLHttpRequest.DONE) {
        //console.log("Request state: %d. [0 = UNSENT; 1 = OPENED; 2 = HEADERS_RECEIVED; 3 = LOADING]", xhr.readyState);
        return;
    }

    if (xhr.status !== 200) {
        //console.log("Request unsuccessful: HTTP status = %d.", xhr.status);
        console.log(xhr.response);

        return;
    }

    console.log(xhr.responseText);

    // parse the response as JSON and extract the resource-list array
    let pages = JSON.parse(xhr.responseText).query.pages;

    for (var page in pages) {
        const image_page_url = "https://en.wikipedia.org/wiki/" + encodeURIComponent(pages[page].images[0].title);
        console.log(image_page_url);
        imageCarUrl(pages[page].images[0].title, licenceplate);
    }

}

function imageCarUrl(param, licenceplate)  {

    var url = "https://en.wikipedia.org/w/api.php";

    var params = {
        action: "query",
        format: "json",
        prop: "imageinfo",
        iiprop: "url",
        titles: param
    };

    url = url + "?origin=*";
    Object.keys(params).forEach(function(key){url += "&" + key + "=" + params[key];});

    // the XMLHttpRequest object
    const xhr = new XMLHttpRequest();

    if (!xhr) {
        console.log("Cannot create an XMLHttpRequest instance.")

        alert("Giving up :( Cannot create an XMLHttpRequest instance");
        return false;
    }

    // set up the call back for handling the request
    xhr.onreadystatechange = function () {
        imageCarUrlResponse(this,licenceplate);
    };

    // perform the request
    //console.log("Performing the HTTP GET request.");

    xhr.open("GET", url, true);
    xhr.send();

    //console.log("HTTP GET request sent.");

}

function imageCarUrlResponse(xhr,licenceplate) {

    // not finished yet
    if (xhr.readyState !== XMLHttpRequest.DONE) {
        //console.log("Request state: %d. [0 = UNSENT; 1 = OPENED; 2 = HEADERS_RECEIVED; 3 = LOADING]", xhr.readyState);
        return;
    }

    if (xhr.status !== 200) {
        //console.log("Request unsuccessful: HTTP status = %d.", xhr.status);
        //console.log(xhr.response);

        return;
    }

    //console.log(xhr.responseText);

    // parse the response as JSON and extract the resource-list array
    let pages = JSON.parse(xhr.responseText).query.pages;

    for (var page in pages) {
        const image_url = pages[page]["imageinfo"][0]["url"];
        //console.log(image_url);

        let card = document.getElementById(licenceplate);

        let img = document.createElement("img");
        img.src = image_url;
        img.classList.add("card-img-top");
        card.prepend(img); // append the row to the table body

    }
}
