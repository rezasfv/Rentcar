// Function to switch between tabs
function openTab(tabId) {
    const tabs = document.querySelectorAll('.tab-content');
    const tabLinks = document.querySelectorAll('.tab-link');
    tabs.forEach(tab => {
        if (tab.id === tabId) {
            tab.style.display = 'block';
        } else {
            tab.style.display = 'none';
        }
    });

    // Highlight the active tab button
    tabLinks.forEach(link => {
        if (link.getAttribute('data-tab') === tabId) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });
}

// Open the first tab by default
openTab('personal-info-tab');
